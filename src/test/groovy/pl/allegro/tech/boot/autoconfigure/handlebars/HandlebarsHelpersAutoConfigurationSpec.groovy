package pl.allegro.tech.boot.autoconfigure.handlebars

import static org.springframework.boot.test.EnvironmentTestUtils.addEnvironment

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification

class HandlebarsHelpersAutoConfigurationSpec extends Specification {

    def context = new AnnotationConfigWebApplicationContext()

    def setup() {
        context.servletContext = new MockServletContext()
        addEnvironment(context)
        context.register(HandlebarsAutoConfiguration)
        context.register(HandlebarsHelpersAutoConfiguration)
        context.refresh()
    }

    def cleanup() {
        context?.close()
    }

    def 'should register helpers'() {
        given:
        def resolver = context.getBean(HandlebarsViewResolver)

        expect:
        resolver.helper('json')
        resolver.helper('assign')
        resolver.helper('camelize')
        resolver.helper('md')
        resolver.helper('include')
    }
}
