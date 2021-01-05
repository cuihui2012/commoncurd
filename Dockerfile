# SpringBoot应用容器化部署
# Author: cuihui

#alpine-linux版本,主要是小
ARG VERSION=8-jre-alpine
FROM openjdk:${VERSION}

# 标签信息
LABEL version="base"
LABEL author="cuihui" email="751670441@qq.com" date="2020-12-31"
LABEL description="commoncurd应用镜像"

# 修改镜像源,时区设置,或在run命令设置参数 -e TZ=Asia/Shanghai,删除缓存包
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
   && apk add --no-cache tzdata \
   && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
   && echo 'Asia/Shanghai' > /etc/timezone \
   && rm -rf /var/cache/apk/*.tar.gz

# 指定挂载目录,此处不作声明,在run命令直接设置参数 -v
#VOLUME /tmp
# 将jar包添加到容器中
COPY commoncurd-0.0.1-SNAPSHOT.jar /tmp
COPY application.properties /tmp

#切换工作目录
WORKDIR /tmp

#端口声明,此处不作声明,配置文件可变,在run镜像时全部映射到宿主机即可
#EXPOSE 8080

# 运行jar包,加快tomcat启动时间
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","commoncurd-0.0.1-SNAPSHOT.jar","-c","application.properties"]