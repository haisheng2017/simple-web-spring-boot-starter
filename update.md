spring boot 3.2
update 06/18/24 log
# mybatis-plus bugs
- mybatis-plus import mybatis-spring itself but this mybatis-spring version is not compatible with  spring boot 3.2. So we need set dependencies by our self
- see the issue blow `https://github.com/mybatis/spring/issues/855`
```
java.lang.IllegalArgumentException: Invalid value type for attribute 'factoryBeanObjectType': java.lang.String
```

# mvnw remove
spring initializer use maven 3.9.7 for spring boot 3.3.0 demo and some maven plugin are updated
use maven@latest to build project (also working for IDE, and when you specify a maven@latest it works automatically)
