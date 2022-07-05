 Buession SpringBoot Changelog
===========================


## [2.0.0](https://github.com/buession/buession-springboot/releases/tag/v2.0.0) (2022-02-21)

### 🔨依赖升级

-


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