package pl.allegro.tech.boot.autoconfigure.handlebars

import com.github.jknack.handlebars.context.FieldValueResolver
import com.github.jknack.handlebars.context.JavaBeanValueResolver
import com.github.jknack.handlebars.context.MapValueResolver
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import spock.lang.Specification

class HandlebarsPropertiesSpec extends Specification {

    def viewResolver = new HandlebarsViewResolver()

    def 'should set default value resolvers'() {
        given:
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties())

        when:
        properties.applyToViewResolver(viewResolver)

        then:
        viewResolver.valueResolvers.length == 2
        viewResolver.valueResolvers.contains(JavaBeanValueResolver.INSTANCE)
        viewResolver.valueResolvers.contains(MapValueResolver.INSTANCE)
    }

    def 'should set value resolvers based on configuration'() {
        given:
        def configuration = [field: true, javaBean: false, map: false, method: false]
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties(configuration))

        when:
        properties.applyToViewResolver(viewResolver)

        then:
        viewResolver.valueResolvers.size() == 1
        viewResolver.valueResolvers.contains(FieldValueResolver.INSTANCE)
    }
}
