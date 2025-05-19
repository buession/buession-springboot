# buession-springboot-captcha 参考手册


## 配置属性


#### 通用

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.captcha.javascript   | String[]    | --  | 前端 JavaScript 库地址      |


#### 阿里云

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.captcha.aliyun.enabled           | boolean                                              | true                   | 是否启用阿里云行为验证码      |
| spring.captcha.aliyun.access-key-id     | String                                               | --                     | AccessKey ID      |
| spring.captcha.aliyun.access-key-secret | String                                               | --                      | AccessKey Secret      |
| spring.captcha.aliyun.app-key           | String                                               | --                      | 服务使用的 App Key      |
| spring.captcha.aliyun.region-id         | String                                               | --                      | 区域 ID      |
| spring.captcha.aliyun.parameter         | [AliyunParameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/aliyun/AliyunParameter.html) | AliyunParameter 默认值   | 前端提交参数名称      |


#### 极验

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.captcha.geetest.enabled       | boolean                                                          | true                   | 是否启极验行为验证码      |
| spring.captcha.geetest.app-id        | String                                                           | --                     | 应用 ID      |
| spring.captcha.geetest.secret-key    | String                                                           | --                     | 密钥      |
| spring.captcha.geetest.version       | String                                                           | v4                    | 版本      |
| spring.captcha.geetest.v3.parameter  | [GeetestV3Parameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/geetest/api/v3/GeetestV3Parameter.html)  | GeetestV3Parameter 默认值                     | 前端提交参数名称      |
| spring.captcha.geetest.v4.parameter  | [GeetestV4Parameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/geetest/api/v4/GeetestV4Parameter.html)  | GeetestV4Parameter 默认值                     | 前端提交参数名称      |


#### 腾讯

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.captcha.tencent.enabled    | boolean                                                | true                   | 是否启腾讯云行为验证码      |
| spring.captcha.tencent.app-id     | String                                                 | --                     | 应用 ID      |
| spring.captcha.tencent.secret-key | String                                                 | --                     | 密钥      |
| spring.captcha.tencent.parameter  | [TencentParameter](https://javadoc.io/doc/com.buession.security/buession-security-captcha/2.3.0/com/buession/security/captcha/tencent/TencentParameter.html) | TencentParameter 默认值               | 前端提交参数名称      |