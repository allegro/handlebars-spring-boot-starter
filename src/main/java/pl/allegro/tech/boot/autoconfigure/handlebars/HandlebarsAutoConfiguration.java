package pl.allegro.tech.boot.autoconfigure.handlebars;

import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.google.common.cache.CacheBuilder.newBuilder;

@Configuration
@EnableConfigurationProperties(HandlebarsProperties.class)
@ConditionalOnWebApplication
public class HandlebarsAutoConfiguration {

    @Configuration
    protected static class HandlebarsViewResolverConfiguration {
        @Autowired
        private HandlebarsProperties handlebars;

        @Bean
        public HandlebarsViewResolver handlebarsViewResolver() {
            HandlebarsViewResolver handlebarsViewResolver = new HandlebarsViewResolver();
            handlebars.applyToViewResolver(handlebarsViewResolver);
            return handlebarsViewResolver;
        }
    }

    @Configuration
    protected static class HandlebarsCacheConfiguration {
        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void setCachingStrategy() {
            if (handlebarsViewResolver.isCache()) {
                handlebarsViewResolver.getHandlebars().with(new GuavaTemplateCache(newBuilder().<TemplateSource, Template>build()));
            }
        }
    }
}
