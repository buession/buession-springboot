# buession-springboot-web 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----   |
| server.response-headers                     	      | LinkedHashMap<String, String>                | --                  | 自定义响应头     				|
| server.server-info-name                     	      | String                 | --                    | Server 信息响应头名称     				|
| server.server-info-prefix                    	      | String                 | --                   | Server 信息响应头前缀     				|
| server.server-info-suffix                    	      | String                 | --                   | Server 信息响应头后缀     				|
| server.strip-server-info-prefix                     | String                 | --                   | 删除 Server 信息响应头前缀     				|
| server.strip-server-info-suffix                     | String                 | --                   | 删除 Server 信息响应头后缀     				|
| server.poweredby.enabled                            | boolean                 | true                   | 响应头中是否输出 PoweredBy 信息     				|
| server.server-info.enabled                          | boolean                 | true                   | 响应头中是否输出 Server 信息     				|
| server.print-url.enabled                            | boolean                 | false                   | 是否打印当前 URL     				|
| spring.security.enabled                             | boolean                 | true                   | 是否启用安全设置     				|
| spring.security.disable-defaults                    | boolean                 | false                   | 是否禁用 spring security 默认配置     				|
| spring.security.http-basic.enabled                  | boolean                 | false                   | 是否启用 Http Basic 验证     				|
| spring.security.csrf.enabled                        | boolean                 | true                   | 是否启用 Csrf     				|
| spring.security.csrf.mode                           | com.buession.security.web.config.CsrfMode                 | --                   | Csrf 模式     				|
| spring.security.csrf.cookie.parameter-name          | String                 | _csrf                   | Csrf 请求参数名     				|
| spring.security.csrf.cookie.header-name             | String                 | X-Xsrf-Token                   | Csrf 请求头名称     				|
| spring.security.csrf.cookie.cookie-name             | String                 | XSRF-TOKEN                   | Csrf Cookie 名称     				|
| spring.security.csrf.cookie.cookie-domain           | String                 | --                   | Csrf Cookie 作用域     				|
| spring.security.csrf.cookie.cookie-path             | String                 | --                   | Csrf Cookie 作用路径    				|
| spring.security.csrf.cookie.cookie-http-only        | boolean                 | true                   | Csrf Cookie 是否可通过客户端脚本访问     				|
| spring.security.csrf.session.parameter-name         | String                 | _csrf                   | Csrf 请求参数名     				|
| spring.security.csrf.session.header-name            | String                 | X-Xsrf-Token                   | Csrf 请求头名称     				|
| spring.security.csrf.session.session-attribute-name | String                 | org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN                   | Csrf Session 属性名称     				|
| spring.security.csrf.frame-options.enabled          | boolean                 | true                   | 是否启用 Frame Options     				|
| spring.security.csrf.frame-options.mode             | com.buession.security.web.config.XFrameOptionsMode                 | XFrameOptionsMode.DENY                   | Frame Options 模式     				|
| spring.security.hsts.enabled                        | boolean                 | true                   | 是否启用 Hsts     				|
| spring.security.hsts.matcher                        | Class<org.springframework.security.web.util.matcher.RequestMatcher>                 | --                   | Request 匹配器     				|
| spring.security.hsts.max-age                        | long                 | 31536000                   | 缓存时间，在浏览器收到这个请求后的 maxAge 秒的时间内凡是访问这个域名下的请求都使用HTTPS请求     				|
| spring.security.hsts.include-sub-domains            | Boolean                 | --                   | 配置此规则也适用于该网站的所有子域名     				|
| spring.security.hsts.preload      | Boolean                 | --                   | 是否预加载 HSTS     				|
| spring.security.hpkp.enabled      | boolean                 | true                   | 是否启用 Hpkp     				|
| spring.security.hpkp.pins      	| Map<String, String>     | --                     |          	     				|
| spring.security.hpkp.sha256-pins  | String[]                | --                     |   			     				|
| spring.security.hpkp.max-age      | Long                 	  | --                      | 浏览器应记住仅使用其中一个已定义的密钥访问此站点的时间     				|
| spring.security.hpkp.include-sub-domains      | Boolean                 | --                   | 配置此规则也适用于该网站的所有子域名     				|
| spring.security.hpkp.report-only      | Boolean                 | --                   |      				|
| spring.security.hpkp.report-uri      | String                 | --                   |      				|
| spring.security.content-security-policy.enabled      | boolean                 | true                   | 是否启用 ContentSecurityPolicy     				|
| spring.security.content-security-policy.report-only      | Boolean                 | --                   |      				|
| spring.security.content-security-policy.policy-directives      | String                 | --                   |  ContentSecurityPolicy 策略    				|
| spring.security.referrer-policy.enabled      | boolean                 | true                   | 是否启用 ReferrerPolic     				|
| spring.security.referrer-policy.policy      | com.buession.security.web.config.ReferrerPolicy.Policy                 | ReferrerPolicy.Policy.NO_REFERRER                   |      				|
| spring.security.xss.enabled      | boolean                 | true                   | 是否启用 XSS 防护     				|
| spring.security.xss.block      | Boolean                 | --                   |      				|
| spring.security.xss.enabled-protection      | Boolean                 | --                   |     				|
| spring.security.xss.policy-config-location      | String                 | --                   | XSS 策略配置文件路径				|
| spring.security.cors.enabled      | boolean                 | true                   | 是否启用 Cors     				|
| spring.security.cors.origins      | Set<String>                 | --                   | 允许请求的域    				|
| spring.security.cors.allowed-methods      | Set<com.buession.web.http.HttpMethod>                 | --                   | 允许请求的方法     				|
| spring.security.cors.allowed-headers      | Set<String>                 | --                   | 实际请求中允许携带的首部字段     				|
| spring.security.cors.exposed-headers      | Set<String>                 | --                   | 允许浏览器访问的头     				|
| spring.security.cors.allow-credentials      | Boolean                 | --                   | 当浏览器的 credentials 设置为 true 时是否允许浏览器读取 response 的内容     				|
| spring.security.cors.max-age      | Long                 | --                   | 指定了 preflight 请求的结果能够被缓存时间（单位：秒     				|
| spring.security.form-login.enabled      | boolean                 | false                   | 是否启动登录表单     				|
| spring.security.form-login.login-page      | String                 | --                   | 登录页地址     				|