# update 06/16/24 log
## add logback access in auto configure
---
# update 06/10/24 log
## upgrade mybatis-plus dependence for spring boot3
## support reset client exception
---

# update 06/19/24 log
## spring rest client with http interface 
- it's recommended to use rest client by apache http5 client
## move example package to non-hao start named
- if an application is defined in hao.xxx liked package, component scan will auto include starter's component. This will make auto-configure not working
## support validate
## `TimedAspect` configure remove
- `You can now use Micrometer’s @Timed, @Counted, @NewSpan, @ContinueSpan and @Observed annotations. The aspects for them are now auto-configured if you have AspectJ on the classpath.`
- now use property to enable those annotations `management.observations.annotations.enabled=true`

---

# update 06/18/24 log
## upgrade spring boot 3.2
## mybatis-plus incompatible version
- mybatis-plus(without spring boot3) import mybatis-spring itself but this mybatis-spring version is not compatible with  spring boot 3.2. So we need set dependencies by our self
- see the issue blow `https://github.com/mybatis/spring/issues/855`
```
java.lang.IllegalArgumentException: Invalid value type for attribute 'factoryBeanObjectType': java.lang.String
```
## mvnw remove
- spring initializer use maven 3.9.7 for spring boot 3.3.0 demo and some maven plugin are updated
- use maven@latest to build project (also working for IDE, and when you specify a maven@latest it works automatically)