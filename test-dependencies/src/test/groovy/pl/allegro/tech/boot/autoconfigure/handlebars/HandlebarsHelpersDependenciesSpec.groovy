package pl.allegro.tech.boot.autoconfigure.handlebars

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification

class HandlebarsHelpersDependenciesSpec extends Specification {

    def context = new AnnotationConfigWebApplicationContext()

    def setup() {
        context.servletContext = new MockServletContext()
        TestPropertyValues.empty().applyTo(context)
        context.register(HandlebarsAutoConfiguration)
        context.register(HandlebarsHelpersAutoConfiguration)
        context.refresh()
    }

    def cleanup() {
        context?.close()
    }

    def 'should register helpers based on dependencies'() {
        given:
        def resolver = context.getBean(HandlebarsViewResolver)

        expect:
        !resolver.helper('jodaPattern')
        resolver.helper('json')
        resolver.helper('assign')
        resolver.helper('include')
    }
}
