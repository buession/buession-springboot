# buession-springboot-cli 参考手册


SpringBoot 命令行应用。


---


### 安装

```xml
<dependency>
    <groupId>com.buession.springboot</groupId>
    <artifactId>buession-springboot-cli</artifactId>
    <version>x.x.x</version>
</dependency>
```

该模块定义了命令模式下的 SpringBootApplication 抽象类。通过该类，简化了您在编写命令行模式下的 SpringBoot 应用代码。

```java
import com.buession.springboot.cli.application.AbstractCliApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.buession.springboot.demo"})
@EnableAutoConfiguration(exclude = {})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DemoCliApplication extends AbstractCliApplication {

	protected DemoCliApplication(){

	}

	public final static void main(final String[] args){
		DemoCliApplication application = new DemoCliApplication();
		application.startup(args);
	}

}
```

他强制将 webApplicationType 设置成：`WebApplicationType.NONE`。


### [API 参考手册>>](https://javadoc.io/static/com.buession.springboot/buession-springboot-cli/2.3.0/)