Spring Boot Starter Handlebars
====

Spring Boot Starter support for
[Handlebars.java](https://github.com/jknack/handlebars.java)
(logic-less templates).

## Usage

Add `spring-boot-starter-handlebars` as dependency:
```gradle
dependencies {
    compile 'pl.allegro.tech.boot:spring-boot-starter-handlebars:0.1.0'
}
```
## Helpers

Spring Boot Starter Handlebars will automatically register handlebars helpers based on project dependencies.
Add any handlebars helper to dependencies and you can start using it.
```gradle
dependencies {
    compile 'com.github.jknack:handlebars-helpers:2.0.0'
    compile 'com.github.jknack:handlebars-jackson2:2.0.0'
    compile 'com.github.jknack:handlebars-humanize:2.0.0'
    compile 'com.github.jknack:handlebars-markdown:2.0.0'
}
```
NOTE: Jackson2Helper and MarkdownHelper will register with name `json` and `md` respectively.
Every other helper will register with its default name.

More information about helpers can be found on
[Handlebars.java](https://github.com/jknack/handlebars.java).

## Configuration

Properties space is: `spring.handlebars`. All basic properties of
[AbstractTemplateViewResolverProperties.java](http://docs.spring.io/autorepo/docs/spring-boot/current/api/org/springframework/boot/autoconfigure/template/AbstractTemplateViewResolverProperties.html)
are available.

Default configuration:
```yaml
spring.handlebars.prefix: classpath:templates/
spring.handlebars.suffix: .hbs
spring.handlebars.cache: true
```
NOTE: `handlebars-guava-cache` is used as template cache implementation.

`valueResolver` configuration allows on/off available handlebars value resolvers.
Here goes default configuration:
```yaml
spring.handlebars.valueResolver.javaBean: true
spring.handlebars.valueResolver.map: true
spring.handlebars.valueResolver.method: false
spring.handlebars.valueResolver.field: false
```
More information about value resolvers can be found on
[Using the ValueResolver](https://github.com/jknack/handlebars.java#using-the-valueresolver).


## License

**spring-boot-starter-handlebars** is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
