# buession-springboot-mybatis 参考手册


## 配置属性


#### 通用配置

|  属性   | 类型   | 默认值    | 说明    |
|  ----  | ----   | ----     | ----  |
| spring.mybatis.config-location            | String                                  | --      | 配置文件路径     |
| spring.mybatis.check-config-location      | boolean                                 | false      | 启动时是否检查 MyBatis XML 文件的存在     |
| spring.mybatis.mapper-locations           | String[]                                | --      | Mapper 路径     |
| spring.mybatis.type-aliases-package       | String                                  | --      | MyBatis 映射类型别名包，通过该属性可以给包中的类注册别名，注册后在 Mapper 对应的 XML 文件中可以直接使用类名，而不用使用全限定的类名，即： XML 中调用的时候不用包含包名     |
| spring.mybatis.type-aliases-super-type    | Class<?>                                | --      | 该配置请和 typeAliasesPackage 一起使用，如果配置了该属性，则仅仅会扫描路径下以该类作为父类的域对象     |
| spring.mybatis.type-aliases               | Class<?>[]                                   | --      | 类型别名包     |
| spring.mybatis.type-handlers-package      | String                                  | --      | TypeHandler 扫描路径，如果配置了该属性，SqlSessionFactoryBean 会把该包下面的类注册为对应的 TypeHandler     |
| spring.mybatis.type-handlers              | TypeHandler<?>[]                        | --      | TypeHandler     |
| spring.mybatis.default-enum-type-handler  | Class<? extends TypeHandler<Enum<?>>>   | --      | TypeHandler     |
| spring.mybatis.executor-type              | org.apache.ibatis.session.ExecutorType  | --      | 执行器类型     |
| spring.mybatis.configuration-properties   | Properties                              | --      | 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署     |
| spring.mybatis.configuration              | org.apache.ibatis.session.Configuration                                   | --      | 原生 MyBatis 所支持的配置     |
| spring.mybatis.fail-fast                  | boolean                                 | false    | 是否启用快速失败机制     |