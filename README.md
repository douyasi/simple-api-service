simple-api-service
-----

>   最小化的 `RESTFUL API` 服务示例源码，无缓存、队列等组件支持，仅 `MySQL` 数据库（使用 `MyBatis`）支持。 


### 文档

- [Java代码风格](_docs/CodeStyle.md)
- [Flyway教程](_docs/Flyway.md)
- [接口文档](_docs/api.md)

### 项目说明

#### 数据迁移

参考上面 `Flyway` 教程，这里使用它二进制来迁移数据库。

```bash
cd _migration/db/dev
cp -r flyway.conf.example flyway.cnf
flyway migrate -locations=filesystem:`pwd`
flyway -user=root -password=root -url=jdbc:mysql://localhost:3306/tm_demo_dev -locations=filesystem:`pwd` migrate
```

#### `common` 类库说明

`common` 项目主要提供了基础工具类、异常、接口与模型等定义，供调用方使用。

#### `jersey-jetty-api-service-demo` 项目说明

此项目主要结合 `JAX-RS` 、`Jersey` 与 `Jetty` 来完成的轻量级 `restfull api` 服务。`Jersey` 可视为支持 `JAX-RS` 轻型 `REST` 框架，`Jetty` 可视为比 `Tomcat` 更轻便，可嵌入的服务器。

#### `spring-api-service-demo` 项目说明

此项目主要使用 `Spring` 框架，来完成 `RESTFUL API` ，相比上面 `jersey-jetty-api-service-demo` 项目较为复杂一些。

### 参考资料

- https://github.com/DocsHome/microservices
- https://jersey.github.io/
- https://www.eclipse.org/jetty/
- https://spring.io/
- https://waylau.com/netty-4-user-guide/
- https://howtodoinjava.com/jersey-jax-rs-tutorials/
- http://www.mybatis.org/mybatis-3/zh/index.html