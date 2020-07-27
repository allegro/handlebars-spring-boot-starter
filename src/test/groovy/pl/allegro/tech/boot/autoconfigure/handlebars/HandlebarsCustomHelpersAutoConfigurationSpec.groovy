package pl.allegro.tech.boot.autoconfigure.handlebars

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification

class HandlebarsCustomHelpersAutoConfigurationSpec extends Specification {

    def context = new AnnotationConfigWebApplicationContext()

    def setup() {
        context.servletContext = new MockServletContext()
        TestPropertyValues.empty().applyTo(context)
        context.register(HandlebarsAutoConfiguration)
        context.register(HandlebarsHelpersAutoConfiguration)
        context.register(CustomHelper)
        context.refresh()
    }

    def cleanup() {
        context?.close()
    }

    def 'should register helper'() {
        given:
        def resolver = context.getBean(HandlebarsViewResolver)

        expect:
        resolver.helper('foo')
    }
}

@HandlebarsHelper
class CustomHelper {
    CharSequence foo() {
        'bar'
    }
}
