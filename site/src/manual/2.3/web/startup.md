# buession-springboot-web 参考手册


Spring Boot Web 应用启动类。


---


```java
package com.example;

import com.buession.springboot.boot.application.Application;
import com.buession.springboot.web.application.AbstractWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.example"}, exclude = {})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ExampleWebApplication extends AbstractWebApplication {

    protected ExampleWebApplication() {
        super(new ExampleBanner());
    }

    public static void main(final String[] args) {
        Application application = new ExampleWebApplication();
        application.startup(args);
    }

}

```

初始化 Servlet 应用：

```java
package com.example;

import com.buession.springboot.boot.application.Application;
import com.buession.springboot.web.application.AbstractWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.example"}, exclude = {})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServletExampleWebApplication extends AbstractWebApplication {

    protected ServletExampleWebApplication() {
        super(WebApplicationType.SERVLET, new ServletExampleBanner());
    }

    public static void main(final String[] args) {
        Application application = new ServletExampleWebApplication();
        application.startup(args);
    }

}

```


初始化 Reactive 应用：

```java
package com.example;

import com.buession.springboot.boot.application.Application;
import com.buession.springboot.web.application.AbstractWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.example"}, exclude = {})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ReactiveExampleWebApplication extends AbstractWebApplication {

    protected ReactiveExampleWebApplication() {
        super(WebApplicationType.REACTIVE, new ServletExampleBanner());
    }

    public static void main(final String[] args) {
        Application application = new ReactiveExampleWebApplication();
        application.startup(args);
    }

}

```


### [API 参考手册>>](https://javadoc.io/static/com.buession.springboot/buession-springboot-web/2.3.0/com/buession/springboot/web/application/AbstractWebApplication.html)