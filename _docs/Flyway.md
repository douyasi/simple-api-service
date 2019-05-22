Flyway教程
------

>   Flyway 官网：https://flywaydb.org/ 。


### 使用 `Flyway 命令行工具` 方式迁移数据库

安装命令行工具： https://flywaydb.org/documentation/commandline/ 。

`Linux` 系统下，可以使用下列命令安装 `flyway` 。

```bash
wget -qO- https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/5.2.4/flyway-commandline-5.2.4-linux-x64.tar.gz | tar xvz && sudo ln -s `pwd`/flyway-5.2.4/flyway /usr/local/bin 
```

然后，书写 `flyway.conf` ，如下示例：

```conf
flyway.url=jdbc:mysql://localhost:3306/flyway_test
flyway.user=root
flyway.password=root
```

编写迁移脚本文件 `V1__Create_person_table.sql` ，如下示例内容：

```sql
create table PERSON (
    ID int not null,
    NAME varchar(100) not null
);
```

然后执行迁移命令：

```bash
# 使用绝对路径位置
flyway migrate -locations=filesystem:/path/to/your/project/examples/src/main/resources/db/migration/ -X
# 如果已经位于当前迁移数据文件目录，可快捷执行下面命令
flyway migrate -locations=filesystem:`pwd`
# 使用更多选项参数方式来执行
flyway -configFiles=/path/to/flyway/config/flyway.conf migrate
# 为防止中文乱码, 最好在url后面加?characterEncoding=utf8, 注意?后不能加过多参数
flyway -user=root -password=root -url=jdbc:mysql://localhost:3306/flyway_test?characterEncoding=utf8 -locations=filesystem:`pwd` migrate
```

>   注：
`--locations=filesystem:` 指令是自定义的本地迁移文件所在的路径，不指定的话会查找 `flyway` 程序默认的位置（即程序安装目录下 `sql` 文件夹）；当然，你也可将迁移脚本放置在此默认位置下，以便执行成功。

回显 `Successfully applied 1 migration to schema ...` 字眼，就说明数据库结构变更已经完成了，可以登录数据库查看。


### 使用 `Flyway Maven 插件` 方式迁移数据库

>   参考官方文档：https://flywaydb.org/getstarted/firststeps/maven 以及 https://flywaydb.org/documentation/maven/ 。

在项目 `pom.xml` 中，添加依赖插件：

```xml
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>5.2.4</version>
    <configuration>
        <url>jdbc:mysql://127.0.0.1:3306/flyway_test</url>
        <user>root</user>
        <password>root</password>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
    </dependencies>
</plugin>
```

编写迁移数据文件：

```bash
mkdir -p src/main/resources/db/migration/
cat << EOF > src/main/resources/db/migration/V1__Create_person_table.sql
create table PERSON (
    ID int not null,
    NAME varchar(100) not null
);
EOF
```

然后执行下列命令：

```bash
mvn flyway:migrate
```

出现 `BUILD SUCCESS` 字眼，就说明数据库结构变更已经完成了，可以登录数据库查看。

### 使用 `FLyway API` 方式迁移数据库

在 `pom.xml` 添加依赖：

```xml
    <dependencies>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
            <version>5.2.4</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
    </dependencies>
``` 

同上面编写迁移数据文件（不再赘述）。

结合 `Flyway API` 编写迁移代码：

本项目中示例代码位于 [MigrationApp](../main/java/com/raoyc/flyway/MigrationApp.java)。

```java
package com.raoyc.flyway;

import org.flywaydb.core.Flyway;

public class MigrationApp {

    public static void main(String[] args) {
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost:3306/flyway_test", "root", "root").load();

        // Start the migration
        flyway.migrate();
    }

}
```

如果您当前使用可以右键执行或者使用 maven 命令：

```bash
mvn package exec:java -Dexec.mainClass=com.raoyc.flyway.MigrationApp
```

回显 ` INFO  o.f.core.internal.command.DbMigrate - Successfully applied 1 migration to schema ...` 字眼，就说明数据库结构变更已经完成了，可以登录数据库查看。

### Flyway 常用命令

```text
migrate  : Migrates the database
clean    : Drops all objects in the configured schemas
info     : Prints the information about applied, current and pending migrations
validate : Validates the applied migrations against the ones on the classpath
undo     : [pro] Undoes the most recently applied versioned migration
baseline : Baselines an existing database at the baselineVersion
repair   : Repairs the schema history table
```

### 参考资料

- [快速掌握和使用Flyway](https://blog.waterstrong.me/flyway-in-practice/)
- [轻松上手数据库版本管理工具Flyway](http://qinghua.github.io/flyway/)
- [Flyway的简单介绍及使用](https://blog.csdn.net/AinGates/article/details/78063246)
