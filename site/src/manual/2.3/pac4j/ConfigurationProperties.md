# buession-springboot-pac4j 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| spring.pac4j.clients                      | Set<String>                                  | --      | 启用认证的客户端类型名称，需要和各配置 [BaseConfig.BaseClientConfig](https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/BaseConfig.BaseClientConfig.html) 类型中的名称保持一致     |
| spring.pac4j.default-client               | String                                 | --      | 默认客户端类型名称     |
| spring.pac4j.ajax-request-resolver-class  | Class<? extends org.pac4j.core.http.ajax.AjaxRequestResolver>                                  | com.buession.security.pac4j.http.JsonAjaxRequestResolver      |  Compute if a HTTP request is an AJAX one and the appropriate response.     |
| spring.pac4j.multi-profile                | boolean                                | false      | 是否允许多个 Profile     |
| spring.pac4j.save-in-session              | boolean                                | true   | 是否保存到 SESSION 中     |


#### 客户端配置

##### CAS 配置

1. spring.pac4j.client.cas.proxy.enabled：表示 pac4j `CasConfiguration` 是否启用 `CasProxyReceptor`

详细配置参考 [https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Cas.html](https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Cas.html)


##### HTTP 配置

详细配置参考 [https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Http.html](https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Http.html)


##### JWT 配置

详细配置参考 [https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Jwt.html](https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/Jwt.html)


##### OAuth 配置

详细配置参考 [https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/OAuth.html](https://javadoc.io/static/com.buession.springboot/buession-springboot-pac4j/2.3.0/com/buession/springboot/pac4j/config/OAuth.html)

* 注：每类客户端均有 `enabled` 属性（如：spring.pac4j.client.cas.enabled），默认值为：false，表示是否启用该类客户端；每个客户端均有 `enabled` 属性（如：spring.pac4j.client.jwt.header.enabled），默认值为：false，表示是否启用该客户端.


#### Filter 配置

##### SecurityFilter 配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| spring.pac4j.filter.security.name         | String         | --      | 过滤器名称        |
| spring.pac4j.filter.security.authorizers  | Set<String>    | --      | 认证器名称列表     |
| spring.pac4j.filter.security.matchers     | Set<String>    | --      | 匹配器名称列表     |


##### CallbackFilter 配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| spring.pac4j.filter.callback.name         | String    | --      | 过滤器名称       |
| spring.pac4j.filter.callback.default-url  | String    | --      | 默认跳转地址     |


##### LogoutFilter 配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| spring.pac4j.filter.logout.name                | String    | --      | 过滤器名称             |
| spring.pac4j.filter.logout.default-url         | String    | --      | 登出成功默认跳转地址     |
| spring.pac4j.filter.logout.logout-url-pattern  | String    | --      | 登出地址模式            |
| spring.pac4j.filter.logout.local-logout        | boolean   | true    | 本地是否退出登录         |
| spring.pac4j.filter.logout.central-logout      | boolean   | true    | 认证中心是否退出登录     |