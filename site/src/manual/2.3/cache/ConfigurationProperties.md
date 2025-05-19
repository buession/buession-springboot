# buession-springboot-cache 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.redis.username                    | String                                   | --      | Redis 用户名     |
| spring.redis.password                    | String                                   | --      | Redis 密码       |
| spring.redis.database                    | int                                      | 0       | Redis DB        |
| spring.redis.client-name                 | String                                   | --      | 客户端名称        |
| spring.redis.connect-timeout             | Duration                                 | 2.30 ms | 连接超时时间      |
| spring.redis.so-timeout                  | Duration                                 | 5000 ms | 读取超时时间      |
| spring.redis.infinite-so-timeout         | Duration                                 | 5000 ms | Infinite 读取超时 |
| spring.redis.key-prefix                  | String                                   | --      | Key 前缀         |
| spring.redis.serializer                  | [Serializer](https://javadoc.io/static/com.buession/buession-redis/2.3.0/com/buession/redis/serializer/Serializer.html) | --      | 序列化方式        |
| spring.redis.enable-transaction-support  | boolean                                  | false   | 是否开启事务       |


#### 单机模式

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.redis.uri   | String | --   | Redis URI，Redis 连接串      |
| spring.redis.host  | String | --   | Redis 主机地址，优先级高于 uri |
| spring.redis.port  | int    | 6379 | Redis 端口                   |


#### 哨兵模式

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.redis.sentinel.masterName       | String      | --      | Master 名称                |
| spring.redis.sentinel.nodes            | Set<String> | --      | 'ip:port' 形式的哨兵节点列表 |
| spring.redis.sentinel.connect-timeout  | Duration    | 2.30 ms | 哨兵节点连接超时时间         |
| spring.redis.sentinel.so-timeout       | Duration    | 5000 ms | 哨兵节点读取超时时间         |
| spring.redis.sentinel.client-name      | String      | --      | 连接哨兵节点的客户端名称      |


#### 集群模式

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.redis.cluster.nodes                       | Set<String> | -- | 'ip:port' 形式的节点列表 |
| spring.redis.cluster.max-redirects               | Integer     | -- | 最大重定向次数           |
| spring.redis.cluster.max-total-retries-duration  | Duration    | -- | 最大重定向时长持续时长    |