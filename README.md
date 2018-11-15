# Bean Alias

Aliasing a bean outside the bean definition.

> Specifying all aliases where the bean is actually defined is not always adequate. It is sometimes desirable to introduce an alias for a bean that is defined elsewhere. This is commonly the case in large systems where configuration is split amongst each subsystem, each subsystem having its own set of object definitions.

## Getting started

In your `build.gradle`:

```groovy
compile 'com.seavus.beanalias:bean-alias:1.0'
```

or your `pom.xml`:

```xml
<dependency>
  <groupId>com.seavus.beanalias</groupId>
  <artifactId>bean-alias</artifactId>
  <version>1.0</version>
</dependency>
```

Define a bean alias in a `@Configuration` class:

```java
@Configuration
@BeanAlias(name = "fromName", alias = "toName")
public class ExampleConfiguration {
}
```

That's it. Bean will be automatically aliased during `ApplicationContext` initialization.
