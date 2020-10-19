# 开发时外部docker资源
# nacos
docker run -d -e MODE=standalone -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=172.17.0.1 -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=root --net=host --name=nacos nacos/nacos-server
# elasticsearch
docker run -d --net=host --name=elasticsearch -e discovery.type=single-node -v /root/docker/elasticsearch/config/:/usr/share/elasticsearch/config/ -v /root/docker/elasticsearch/plugins/:/usr/share/elasticsearch/plugins/ -v /root/docker/elasticsearch/data/:/usr/share/elasticsearch/data/ elasticsearch:7.6.2
# fastdfs
docker run -d --network=host --name tracker -v /root/docker/fastdfs/tracker:/var/fdfs delron/fastdfs tracker
docker run -d --network=host --name storage -e TRACKER_SERVER=172.17.0.1:22122 -v /docker/fastdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage
# rabbitmq
docker run -d --net=host --name=rabbit rabbitmq:3-management
# redis
docker run -d --net=host --name=redis redis
# zipkin
docker run -d --net=host --name=zipkin openzipkin/zipkin-slim