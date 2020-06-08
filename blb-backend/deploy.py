# coding=UTF-8
import os
import sys

args = sys.argv
if len(args) < 3 :
    print ('请按照 deploy.py <服务名> <版本号> 的方式传递参数')


serviceName = args[1]
version = args[2]
name = serviceName +'-' + version
print ("正在构建微服务"+name+"\n")
str = os.popen("mvn package -f ./parent/pom.xml -pl :"+serviceName+"-impl -am").read()
print (str)
print ("微服务构建完成\n")

print ("生成Dockerfile\n")
str = 'FROM openjdk:11\n\
COPY ./artifact/'+name+'.jar ./\n\
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prd", "'+name+'.jar"]'

f = open('Dockerfile','w')
f.write(str)
f.close()
print ("生成Dockerfile完成\n")

print ('正在构建镜像\n')
str = os.popen("docker build -t='"+name+"' .").read()
print (str)
print ('构建镜像完成\n')