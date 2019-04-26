spring-api-service-demo
-----

### 编译运行

参考 [Flyway](../_docs/Flyway.md) 及 [README](../README.md#数据迁移) 迁移数据，然后编译运行：

```bash
# 根据实际修改数据库配置
vim spring-api-service-demo/src/main/resources/application.properties
# 打包
mvn clean install
# 执行 jar
java -jar target/spring-api-service-demo-1.0-SNAPSHOT.jar
```

参考 [api](../_docs/api.md) 文档，请求 `REST` 接口。

### 参考资料

- https://stackoverflow.com/questions/8986593/how-to-customize-parameter-names-when-binding-spring-mvc-command-objects
- https://logback.qos.ch/
- https://spring.io/
- http://zetcode.com/articles/jerseyembeddedjetty/
- https://projectlombok.org/
- https://juejin.im/entry/5a390ba76fb9a0451e3fed7c
- https://github.com/FasterXML/jackson
- https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
- https://freemarker.apache.org/
- http://www.kerneler.com/freemarker2.3.23/index.html
- https://github.com/json-path/JsonPath
