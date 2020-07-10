package pl.allegro.tech.boot.autoconfigure.handlebars

import static org.springframework.web.servlet.support.RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE

import com.github.jknack.handlebars.cache.GuavaTemplateCache
import com.github.jknack.handlebars.cache.HighConcurrencyTemplateCache
import com.github.jknack.handlebars.cache.NullTemplateCache
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver
import com.github.jknack.handlebars.springmvc.SpringTemplateLoader
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.Bean
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import spock.lang.Specification

class HandlebarsAutoConfigurationSpec extends Specification {

    def context = new AnnotationConfigWebApplicationContext()

    def setup() {
        context.servletContext = new MockServletContext()
    }

    def cleanup() {
        context?.close()
    }

    def 'should configure handlebars'() {
        given:
        'register and refresh context'()

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.cache instanceof GuavaTemplateCache
        resolver.handlebars.loader instanceof SpringTemplateLoader
        resolver.helper('message')
        !resolver.failOnMissingFile
        !resolver.bindI18nToMessageSource
    }

    def 'not enabled handlebars'() {
        given:
        'register and refresh context'('handlebars.enabled=false')

        when:
        context.getBean(HandlebarsViewResolver)

        then:
        thrown NoSuchBeanDefinitionException
    }

    def 'not enabled handlebars with wrong property'() {
        given:
        'register and refresh context'('handlebars.enabled=')

        when:
        context.getBean(HandlebarsViewResolver)

        then:
        thrown NoSuchBeanDefinitionException
    }

    def 'enabled handlebars with property'() {
        given:
        'register and refresh context'('handlebars.enabled=true')

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver instanceof HandlebarsViewResolver
    }

    def 'enabled handlebars'() {
        given:
        'register and refresh context'()

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver instanceof HandlebarsViewResolver
    }

    def 'should configure handlebars without cache'() {
        given:
        'register and refresh context'('handlebars.cache:false')

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.cache instanceof NullTemplateCache
    }

    def 'should configure handlebars with custom template cache'() {
        given:
        context.register(CustomConfiguration)
        'register and refresh context'()

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.cache instanceof HighConcurrencyTemplateCache
    }

    def 'should configure handlebars with pretty print'() {
        given:
        'register and refresh context'('handlebars.prettyPrint:true')

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.prettyPrint
    }

    def 'should configure handlebars with infinite loops'() {
        given:
        'register and refresh context'('handlebars.infiniteLoops:true')

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.infiniteLoops
    }

    def 'should configure handlebars with custom template loader'() {
        given:
        context.register(CustomConfiguration)
        'register and refresh context'()

        when:
        def resolver = context.getBean(HandlebarsViewResolver)

        then:
        resolver.handlebars.loader instanceof ClassPathTemplateLoader
    }

    def 'should resolve view'() {
        given:
        'register and refresh context'()

        expect:
        render('hello').contentAsString == 'hello world'
    }

    def 'should resolve view from custom classpath'() {
        given:
        'register and refresh context'('handlebars.prefix:classpath:views')

        expect:
        render('prefixed').contentAsString == 'prefixed body'
    }

    def 'should resolve view with custom suffix'() {
        given:
        'register and refresh context'('handlebars.suffix:.html')

        expect:
        render('suffixed').contentAsString == 'suffixed body'
    }

    def 'register and refresh context'(String... env) {
        TestPropertyValues.of(env).applyTo(context)
        context.register(HandlebarsAutoConfiguration)
        context.refresh()
    }

    def render(String viewName) throws Exception {
        def resolver = context.getBean(HandlebarsViewResolver)
        def view = resolver.resolveViewName(viewName, Locale.UK)
        assert view
        def request = new MockHttpServletRequest()
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, context)
        def response = new MockHttpServletResponse()
        view.render(null, request, response)
        response
    }
}

class CustomConfiguration {
    @Bean
    HighConcurrencyTemplateCache cache() {
        new HighConcurrencyTemplateCache()
    }

    @Bean
    ClassPathTemplateLoader loader() {
        new ClassPathTemplateLoader()
    }
}
