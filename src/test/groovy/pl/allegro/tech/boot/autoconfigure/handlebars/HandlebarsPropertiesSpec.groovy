package pl.allegro.tech.boot.autoconfigure.handlebars

import com.github.jknack.handlebars.context.FieldValueResolver
import com.github.jknack.handlebars.context.JavaBeanValueResolver
import com.github.jknack.handlebars.context.MapValueResolver
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import spock.lang.Specification

class HandlebarsPropertiesSpec extends Specification {

    def viewResolver = new HandlebarsViewResolver()

    def 'should configure handlebars'() {
        given:
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties())

        when:
        properties.applyToMvcViewResolver(viewResolver)

        then:
        viewResolver.valueResolvers.size() == 2
        viewResolver.valueResolvers.contains(JavaBeanValueResolver.INSTANCE)
        viewResolver.valueResolvers.contains(MapValueResolver.INSTANCE)
        viewResolver.registerMessageHelper
        !viewResolver.failOnMissingFile
        !viewResolver.bindI18nToMessageSource
    }

    def 'should not register message helper'() {
        given:
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties())

        and:
        properties.registerMessageHelper = false

        when:
        properties.applyToMvcViewResolver(viewResolver)

        then:
        !viewResolver.helper('message')
    }

    def 'should set fail on missing file'() {
        given:
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties())

        and:
        properties.failOnMissingFile = true

        when:
        properties.applyToMvcViewResolver(viewResolver)

        then:
        viewResolver.failOnMissingFile
    }

    def 'should bind i18n to message source'() {
        given:
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties())

        and:
        properties.bindI18nToMessageSource = true

        when:
        properties.applyToMvcViewResolver(viewResolver)

        then:
        viewResolver.bindI18nToMessageSource
    }

    def 'should set value resolvers based on configuration'() {
        given:
        def configuration = [field: true, javaBean: false, map: false, method: false]
        def properties = new HandlebarsProperties(new HandlebarsValueResolversProperties(configuration))

        when:
        properties.applyToMvcViewResolver(viewResolver)

        then:
        viewResolver.valueResolvers.size() == 1
        viewResolver.valueResolvers.contains(FieldValueResolver.INSTANCE)
    }
}
