# spring-docker

To run locally add `docker-compose.override.yml`

```
version: '3.8'

services:
  web:
    ports:
      - "1234:8080"

```
