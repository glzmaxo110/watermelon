# FROM 表示使用 Jdk8 环境 为基础镜像，如果镜像不是本地的会从 DockerHub 进行下载
FROM frolvlad/alpine-oraclejdk8:slim
#VOLUME 指定了临时文件目录为/tmp。其效果是在主机/var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp.(Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录 )
VOLUME /tmp
#ADD 将项目的jar包作为app.jar 添加到容器
ADD melon-user-0.0.1-SNAPSHOT.jar melon-use.jar
# 执行项目 app.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 “/dev/urandom” 作为 Entropy Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/melon-use.jar"]