# buession-springboot-mongodb 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.mongodb.type-mapper  | Class<org.springframework.data.mongodb.core.convert.MongoTypeMapper> | --      | MongoTypeMapper，优先级高于默认的 MongoTypeMapper；当配置不为 null 时，type-key 无效     |
| spring.mongodb.type-key     | String                                                               | _class  | Type Key     |