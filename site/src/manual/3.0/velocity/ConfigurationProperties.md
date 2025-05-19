# buession-springboot-velocity 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| spring.velocity.enabled                     	 | boolean                | true                  | 是否启用     				|
| spring.velocity.prefix                     	 | String                 | ""                    | 模板前缀     				|
| spring.velocity.suffix                    	 | String                 | .vm                   | 模板后缀     				|
| spring.velocity.date-tool-attribute            | String                 | --                    | 日期时间工具属性     		|
| spring.velocity.number-tool-attribute          | String                 | --                    | 数字工具属性    			|
| spring.velocity.properties             		 | Map<String, String>    | --                    | Velocity 属性     		|
| spring.velocity.resource-loader-path           | String                 | classpath:/Templates/ | 模板目录路径             |
| spring.velocity.toolbox-config-location        | String                 | --                    | toolbox 配置文件路径     |
| spring.velocity.prefer-file-system-access      | boolean                | true                  | 是否预检文件权限          |
| spring.velocity.cache                     	 | boolean                | false                 | 是否缓存     				|
| spring.velocity.content-type                   | org.springframework.util.MimeType              | text/html                | Content-Type     				|
| spring.velocity.charset                   	 | java.nio.charset.Charset              | UTF-8                | 字符集     				|
| spring.velocity.velocity-macro.library         | String                 | --                    | 宏文件路径               |