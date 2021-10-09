# Getting Started

### Install ClamAV via Docker
1. create docker volume to persist database in docker ```docker volume create clam_db```
2. Run docker image ```docker run -it --publish 3310:3310 --name "clam_container_01" --mount source=clam_db,target=/var/lib/clamav -d clamav/clamav:latest```

### Run clamav integration
```mvn spring-boot:run```