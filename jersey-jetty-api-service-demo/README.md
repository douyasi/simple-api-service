jersey-jetty-api-service-demo
-----

### 编译运行

执行下列命令：

```bash
# 修改数据库配置
cd jersey-jetty-api-service-demo/src/main/mybatis-config.xml
# 打包安装
mvn clean install
# 执行 jar
java -jar target/jersey-jetty-api-service-demo.jar
```

参考 [_docs/api](../_docs/api.md) 文档，请求 `REST` 接口。