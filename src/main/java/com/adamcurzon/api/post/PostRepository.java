package com.adamcurzon.api.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostRepository {
    private static final String CACHE_KEY = "post_";

    private final JdbcClient jdbcClient;

    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    private final JdbcTemplate jdbcTemplate;

    public PostRepository(JdbcClient jdbcClient,
                          JdbcTemplate jdbcTemplate,
                          RedisTemplate<String, Object> redisTemplate
    ){
        this.jdbcClient = jdbcClient;
        this.redisTemplate = redisTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> findAll()
    {
        // TODO: implement cache strategy
        return this.jdbcClient.sql("select * from posts").query(Post.class).list();
    }

    public Post findById(BigInteger id)
    {
        Post cachedPost = (Post) this.redisTemplate.opsForValue().get(CACHE_KEY+id);

        if (cachedPost != null) {
            return cachedPost;
        }

        Post dbPost = this.jdbcClient.sql("select * from posts WHERE id = ?")
                .params(List.of(id))
                .query(Post.class)
                .single();

        this.redisTemplate.opsForValue().set(CACHE_KEY+dbPost.getId().toString(), dbPost);

        return dbPost;
    }

    public void create(Post post)
    {
        String insertSql = "INSERT INTO posts(message,created_at) VALUES (?, NOW());";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getMessage());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();

        assert key != null : "Post not created";

        this.redisTemplate.opsForValue().set(CACHE_KEY+key.toString(), this.findById((BigInteger) key));
    }
}
