package pl.allegro.tech.boot.autoconfigure.handlebars

import org.springframework.core.io.DefaultResourceLoader
import org.springframework.mock.env.MockEnvironment
import spock.lang.Specification

class HandlebarsTemplateAvailabilityProviderSpec extends Specification {

    def provider = new HandlebarsTemplateAvailabilityProvider()
    def resourceLoader = new DefaultResourceLoader()
    def environment = new MockEnvironment()

    def 'should find template in default location'() {
        expect:
        provider.isTemplateAvailable('hello', environment, this.class.classLoader, resourceLoader)
    }

    def 'should not find template that does not exist'() {
        expect:
        !provider.isTemplateAvailable('whatever', environment, this.class.classLoader, resourceLoader)
    }

    def 'should find template with custom prefix'() {
        given:
        environment.setProperty('handlebars.prefix', 'classpath:views/')

        expect:
        provider.isTemplateAvailable('prefixed', environment, this.class.classLoader, resourceLoader)
    }

    def 'should find template with custom suffix'() {
        given:
        environment.setProperty('handlebars.suffix', '.html')

        expect:
        provider.isTemplateAvailable('suffixed', environment, this.class.classLoader, resourceLoader)
    }
}
