jersey-jetty-api-service-demo
-----

### 编译运行

```bash
# 修改数据库配置
cd jersey-jetty-api-service-demo/src/main/mybatis-config.xml
# 打包安装
mvn clean install
# 执行 jar
java -jar target/jersey-jetty-api-service-demo.jar
```

参考 [_docs/api](../_docs/api.md) 文档，请求 `REST` 接口。

### 参考资料

- https://stackoverflow.com/questions/10935135/maven-and-adding-jars-to-system-scope
- https://github.com/whzhaochao/spring-jetty-jersey-mybatis
- https://nikgrozev.com/2014/10/16/rest-with-embedded-jetty-and-jersey-in-a-single-jar-step-by-step/
- http://zetcode.com/articles/jerseyembeddedjetty/
- https://www.javacodegeeks.com/2015/03/creating-web-services-and-a-rest-server-with-jax-rs-and-jetty.html
- https://www.dovydasvenckus.com/rest/2017/08/20/jersey-on-embedded-jetty/
- https://crunchify.com/how-to-build-restful-service-with-java-using-jax-rs-and-jersey/
- https://www.jianshu.com/p/764fcdffc28a
- https://waylau.com/jersey-2-spring-4-rest/
- https://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/
- https://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
- https://github.com/shalousun/ApplicationPower
- https://waylau.gitbooks.io/rest-in-action/