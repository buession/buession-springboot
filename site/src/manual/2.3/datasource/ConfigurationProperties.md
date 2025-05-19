# buession-springboot-datasource 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.datasource.type               | Class<? extends javax.sql.DataSource>                                   | --      | DataSource 类型     |
| spring.datasource.driver-class-name  | String                                   | --      | 数据库驱动类名       |
| spring.datasource.master                    | org.springframework.boot.autoconfigure.jdbc.DataSourceProperties                                      | --       | Master 数据源配置        |
| spring.datasource.slaves             | List<org.springframework.boot.autoconfigure.jdbc.DataSourceProperties>                                   | --      | Slave 数据源配置列表        |


#### Hikari 数据源连接池

配置属性前缀：spring.datasource.hikari

详细配置参见：[https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/HikariPoolConfiguration.html](https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/HikariPoolConfiguration.html)


#### DHCP2 数据源连接池

配置属性前缀：spring.datasource.dbcp2

详细配置参见：[https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/Dbcp2PoolConfiguration.html](https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/Dbcp2PoolConfiguration.html)


#### Druid 数据源连接池

配置属性前缀：spring.datasource.druid

详细配置参见：[https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/DruidPoolConfiguration.html](https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/DruidPoolConfiguration.html)


#### Tomcat 数据源连接池

配置属性前缀：spring.datasource.tomcat

详细配置参见：[https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/TomcatPoolConfiguration.html](https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/TomcatPoolConfiguration.html)


#### 一般的数据源连接池

配置属性前缀：spring.datasource.generic

详细配置参见：[https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/GenericPoolConfiguration.html](https://javadoc.io/static/com.buession/buession-jdbc/2.3.0/com/buession/jdbc/datasource/config/GenericPoolConfiguration.html)