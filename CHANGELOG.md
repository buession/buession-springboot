 Buession SpringBoot Changelog
===========================


## [5.0.0](https://github.com/buession/buessionframework/releases/tag/v5.0.0) (2026-09-08)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v5.0.0)


### 🔔 变化

- DataSourceConfiguration 删除 GenericDataSource 初始化
- 新增 DataSourceConsumer 在 DataSource bean 初始化时，可定制 DataSourceProperties


---


## [4.0.0](https://github.com/buession/buessionframework/releases/tag/v4.0.0) (2026-07-17)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v4.0.0)


### ⭐ 新特性

- Application 支持设置父级上下文
- Application 方法 customize、applicationStartedHook 支持接收命令行参数
- 支持动态构建 JDBC URL
- Pac4j Configuration 支持 Kerberos
- Pac4j Configuration 支持 OIDC
- Pac4j Configuration 支持 SAML
- Pac4j 支持配置 isAlwaysUse401ForUnauthenticated
- Pac4j Config bean 增加定制器


### 🔔 变化

- Pac4j JWT 认证不再依赖 HTTP Client 配置，使用独立的 HeaderClient 或 ParameterClient
- Pac4j OAuth 独立指定 key 和 secret
- 不再生成 pac4j JwtGenerator bean


### ⏪ 优化

- **buession-springboot-pac4j：** com.buession.springboot.shiro.core.ShiroFilter
- 删除模块 buession-springboot-velocity
- Pac4j 中 Customizer&lt;Client&gt; 优先级提至最高
- Pac4j Client AutoConfiguration 优化


### 🐞 Bug 修复

- Pac4j JWT 中不恰当的配置
- Pac4j Client internalInit 中有对配置属性进行赋值的，放到 super.internalInit() 后，导致赋值未起作用的 BUG
- 修复 RedisTemplate 序列化/反序列化配置错误的问题


---


## [3.0.1](https://github.com/buession/buession-springboot/releases/tag/v3.0.1) (2025-05-20)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.1)


### ⭐ 新特性

- Pac4j Client 支持 com.buession.core.Customizer 定制
- 增加 IdGenerator 自动配置


### ⏪ 优化

- **buession-springboot-shiro：** SessionDAO bean 初始化优化


### 🐞 Bug 修复

- 修复 org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$EnableWebFluxConfiguration 和 com.buession.springboot.web.reactive.autoconfigure.ReactiveWebFluxConfiguration 循环依赖的 BUG


---


## [3.0.0](https://github.com/buession/buession-springboot/releases/tag/v3.0.0) (2024-11-07)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v3.0.0)


### ⭐ 新特性

- httpclient 新增代理支持
- httpclient 新增支持 apache httpcomponents 5
- buession-springboot-cache 新增支持 lettuce 支持
- AbstractApplication 新增 applicationStartedHook 便于应用启动后，可增加钩子用于扩展程序


### 🔔 变化

- buession-springboot-canal 迁移至 com.buession.canal:buession-canal-springboot
- 拆分 cache DataSource AutoConfiguration 类为 JedisDataSourceConfiguration 和 LettuceDataSourceConfiguration
- 删除 com.buession.springboot.httpclient.autoconfigure.HttpClientConfiguration
- 删除 buession-springboot-canal、buession-springboot-session
- Application 增加 springboot 更多原生配置
- MyBatis 废弃 masterTemplate、slaveSqlSessionTemplate bean，通过其它数据库本身或其它中间件实现读写分离
- Pac4jJwtConfiguration 不再初始化 Client Bean ，移至 Pac4jHttpConfiguration


### 🐞 Bug 修复

- JDBC AutoConfiguration 修复连接池配置不生效的问题


### ⏪ 优化


---


## [2.3.3](https://github.com/buession/buession-springboot/releases/tag/v2.3.3) (2024-05-06)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.3)


### ⭐ 新特性

- **buession-springboot-boot：** 新增线程池自动配置类 ThreadPoolConfiguration
- **buession-springboot-mybatis：** ConfiguredMapperScannerRegistrar，增加 spring.mybatis.scanner.enabled 开关配置


### ⏪ 优化

- **buession-springboot-pac4j：** 优化 Pac4jFilter 类
- **buession-springboot-shiro：** 优化 Shiro Filter 初始化及处理流程
- **buession-springboot-mybatis：** SqlSessionFactoryBean 初始化速度优化


---


## [2.3.2](https://github.com/buession/buession-springboot/releases/tag/v2.3.2) (2023-12-27)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.2)


### ⭐ 新特性

- **buession-springboot-httpclient：** OKHTTP client 增加可设置 maxRequests


### 🐞 Bug 修复

- **buession-springboot-mybatis：** 修复 Environment 获取 spring.mybatis.annotation-class 转换成 Class 异常 BUG
- **buession-springboot-mybatis：** 修复无法获取 spring.mybatis.scanner.base-package 的 BUG


### ⏪ 优化

- **buession-springboot-cache：** RedisTemplate 初始化时不手动调用 afterPropertiesSet 方法
- **buession-springboot-cache：** 优化 AbstractDataSourceFactoryBean 多次调用 afterPropertiesSet 时，重复初始化 dataSource
- **buession-springboot-boot：** 代码质量优化


---


## [2.3.1](https://github.com/buession/buession-springboot/releases/tag/v2.3.1) (2023-11-17)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.1)


### 🔔 变化

- **buession-springboot-boot：** 移除 org.bouncycastle 依赖


### ⭐ 新特性

- **buession-springboot-boot：** 新增 BaseOnPropertyExistCondition、BaseMapOnPropertyExistCondition
- **buession-springboot-canal：** 新增 canal 自动配置类
- **buession-springboot-mybatis：** 新增 Mybatis scanner
- **buession-springboot-mybatis：** 新增 Mybatis LanguageDriver bean
- **buession-springboot-web：** 新增 sitemesh 自动配置类


### 🐞 Bug 修复

- **buession-springboot-mybatis：** 修复配置多个 mapper location 时 location 丢失的问题


### ⏪ 优化

- **buession-springboot-datasource：** DataSource 初始化优化
- **buession-springboot-mybatis：** 优化 SqlSessionFactoryBean 初始化


---


## [2.3.0](https://github.com/buession/buession-springboot/releases/tag/v2.3.0) (2023-08-17)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.3.0)


### ⭐ 新特性

- **buession-springboot-boot：** Application 支持可设置 Banner Mode、lazyInitialization
- **buession-springboot-cli：** CliApplication 支持可设置 addCommandLineProperties
- **buession-springboot-httpclient：** 新增 HTTP 异步请求客户端
- **buession-springboot-captcha：** 新增对 WebFlux 的支持
- **buession-springboot-web：** webflux 下创建 HttpMessageConverters bean


### 🔔 变化

- **buession-springboot-boot：** AbstractApplication 允许 Banner 传 null
- **buession-springboot-boot：** Banner 不再使用 jfiglet
- **buession-springboot-web：** AbstractWebApplication webApplicationType 属性默认值由 WebApplicationType.SERVLET 改为 null


### ⏪ 优化

- 其它优化


---


## [2.2.1](https://github.com/buession/buession-springboot/releases/tag/v2.2.1) (2023-03-31)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.2.1)


---


## [2.2.0](https://github.com/buession/buession-springboot/releases/tag/v2.2.0) (2023-03-11)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.2.0)


### ⭐ 新特性

- **buession-springboot-shiro：** Session Cookie 和 RememberMe Cookie 支持配置 HttpOnly
- **buession-springboot-geoip：** 增加可支持设置 asn 库地址或流
- **buession-springboot-web：** 新增实验性 jackson HttpMessageConverter 支持 XSS 过滤


### 🔔 变化

- **buession-springboot-shiro：** 最大程度化，使用 shiro 自带 API
- **buession-springboot-session：** 废弃该模块
- **buession-springboot-pac4j：** jwt 配置属性 secretSignatureAlgorithm、secretEncryptionAlgorithm、encryptionMethod 使用 com.nimbusds.jose 库的原生质值


---


## [2.1.2](https://github.com/buession/buession-springboot/releases/tag/v2.1.2) (2022-11-13)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.2)


### ⭐ 新特性

- **buession-springboot-pac4j：** 增加配置属性 spring.pac4j.http-action-adapter-class ，允许配置 HttpActionAdapter


### 🐞 Bug 修复

- **buession-springboot-cache：** 修复单机模式未设置 user 的 BUG


### 📔 文档
- **buession-redis：** 修正错误的注释


---


## [2.1.1](https://github.com/buession/buession-springboot/releases/tag/v2.1.1) (2022-08-18)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.1)


### 🔔 变化

- **buession-springboot-shiro：** 配置 spring.shiro.session.session-id-url-rewriting-enabled 默认值改为 false


### 🐞 Bug 修复

- **buession-springboot-shiro：** 修复 spring.shiro.session、spring.shiro.remember-me 配置不生效的 BUG


---


## [2.1.0](https://github.com/buession/buession-springboot/releases/tag/v2.1.0) (2022-08-08)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.1.0)


### ⭐ 新特性

- **buession-springboot-pac4j：** 注解 @Principal 支持 webflux 环境
- **buession-springboot-pac4j：** 增加配置属性 spring.pac4j.client.cas.profile-definition 可覆盖 CasAuthenticator 中默认的 ProfileDefinition 用于转换 CAS Server 返回的字段


### 🔔 变化

- **buession-springboot-shiro：** 调整 Pac4j Filter 注入 shiro 中的方式


### 🐞 Bug 修复

- **buession-springboot-redis：** 修复集群模式下错误设置 username 和 password 的 BUG
- **buession-springboot-pac4j：** 修复 Pac4j Filter 注册成为了全局 Filter 导致，请求任何 URL 都会执行每个 pac4j Filter 的 BUG


---


## [2.0.2](https://github.com/buession/buession-springboot/releases/tag/v2.0.2) (2022-07-27)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.2)


### 🐞 Bug 修复

- **buession-springboot-datasource：** 修复未创建 master DataSource 的 BUG


---


## [2.0.1](https://github.com/buession/buession-springboot/releases/tag/v2.0.1) (2022-07-18)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.1)
- [jfiglet](http://lalyos.github.io/jfiglet/) 版本升级至 0.0.9


### 🔔 变化

- **buession-springboot-captcha：** 废弃 spring.captcha.enabled 属性，引用该包始终自动加载验证码自动配置类，且目前该属性未实际使用
- **buession-springboot-datasource：** 修复各驱动配置中的驱动类名、连接 URL、用户名和密码能够覆盖全局配置的问题，此种方式是不安全的
- **buession-springboot-mongodb：** 废弃 spring.mongodb.read-preference 通过 spring-data-mongodb 原生方式设置
- **buession-springboot-mybatis：** 严格约束了 spring.mybatis.defaultEnumTypeHandler 的类型
- **buession-springboot-pac4j：** spring.pac4j.save-in-session 默认值设置为 true


### 🐞 Bug 修复

- **buession-springboot-datasource：** 修复 spring.datasource.driver-class-name 配置未起作用的 BUG
- **buession-springboot-mongodb：** 修复 spring.mongodb.type-key 为空时，未删掉实体中 "_class" 字段的 BUG


---


## [2.0.0](https://github.com/buession/buession-springboot/releases/tag/v2.0.0) (2022-07-08)

### 🔨依赖升级

- [依赖库版本升级和安全漏洞修复](https://github.com/buession/buession-parent/releases/tag/v2.0.0)


### ⭐ 新特性

- **buession-springboot-captcha：** 新增极验 V4 版本支持，阿里云、腾讯云行为验证码
- **buession-springboot-web：** 新增 CorsFilter Bean 配置


### 🔔 变化

- **buession-springboot-boot：** 移除 AopConfiguration
- **buession-springboot-cache：** 移除 redis 前缀的 ConfigurationProperties
- **buession-springboot-cache：** RedisProperties 配置项中，时间值由 int 类型变更为 java.time.Duration
- **buession-springboot-cache：** 移除 jedis 兼容旧版本 @Bean 方法
- **buession-springboot-cache：** 移除 RedisProperties 配置项中，关于时间（*Millis）配置项
- **buession-springboot-cache：** 默认不启用连接池
- **buession-springboot-captcha：** 移除 captcha 前缀的 ConfigurationProperties
- **buession-springboot-captcha：** 移除类 com.buession.springboot.captcha.Geetest
- **buession-springboot-cas：** 移除 buession-springboot-cas 模块，整合进 buession-springboot-pac4j
- **buession-springboot-geoip：** 移除 geoip 前缀的 ConfigurationProperties
- **buession-springboot-httpclient：** 移除 httpclient 前缀的 ConfigurationProperties
- **buession-springboot-jwt：** 移除 buession-springboot-jwt 模块，整合进 buession-springboot-pac4j
- **buession-springboot-mongodb：** MongoDBProperties typeMapper、typeKey 均为 null，修改 spring-data-mongodb 生成 typeKey 的行为
- **buession-springboot-mongodb：** 移除 spring.data.mongodb 前缀的 ConfigurationProperties
- **buession-springboot-mybatis：** 移除 mybatis 前缀的 ConfigurationProperties
- **buession-springboot-pac4j：** 重构 pac4j 与 Client 集成逻辑，与 cas 解耦
- **buession-springboot-shiro：** shiro 前缀的 ConfigurationProperties
- **buession-springboot-shiro：** 支持通过 shiro.enabled 配置项，控制 bean Auto Configuration
- **bbuession-springboot-velocity：** 移除 velocity 前缀的 ConfigurationProperties
- **buession-springboot-velocity：** 移除 spring.velocity.enable-cache 配置项，使用 spring.velocity.cache 替代