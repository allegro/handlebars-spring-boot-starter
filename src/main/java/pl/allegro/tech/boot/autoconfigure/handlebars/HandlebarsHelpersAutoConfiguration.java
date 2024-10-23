package pl.allegro.tech.boot.autoconfigure.handlebars;

import com.github.jknack.handlebars.helper.ext.AssignHelper;
import com.github.jknack.handlebars.helper.ext.IncludeHelper;
import com.github.jknack.handlebars.helper.ext.JodaHelper;
import com.github.jknack.handlebars.helper.ext.NumberHelper;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.jackson.JacksonHelper;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

@AutoConfiguration
@ConditionalOnClass(HandlebarsViewResolver.class)
@ConditionalOnWebApplication
public class HandlebarsHelpersAutoConfiguration {

    @Configuration
    @ConditionalOnClass(JacksonHelper.class)
    static class JsonHelperAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelper() {
            handlebarsViewResolver.registerHelper("json", JacksonHelper.INSTANCE);
        }
    }

    @Configuration
    @ConditionalOnClass(AssignHelper.class)
    static class AssignHelperAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelper() {
            handlebarsViewResolver.registerHelper("assign", AssignHelper.INSTANCE);
        }
    }

    @Configuration
    @ConditionalOnClass(IncludeHelper.class)
    static class IncludeHelperAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelper() {
            handlebarsViewResolver.registerHelper("include", IncludeHelper.INSTANCE);
        }
    }

    @Configuration
    @ConditionalOnClass(NumberHelper.class)
    static class NumberHelpersAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelpers() {
            NumberHelper.register(handlebarsViewResolver.getHandlebars());
        }
    }

    @Configuration
    @ConditionalOnClass({JodaHelper.class, DateTimeFormat.class})
    static class JodaHelpersAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelpers() {
            handlebarsViewResolver.registerHelpers(JodaHelper.class);
        }
    }

    @Configuration
    @ConditionalOnClass(StringHelpers.class)
    static class StringHelpersAutoConfiguration {

        @Autowired
        private HandlebarsViewResolver handlebarsViewResolver;

        @PostConstruct
        public void registerHelpers() {
            StringHelpers.register(handlebarsViewResolver.getHandlebars());
        }
    }

    @Bean
    public BeanPostProcessor handlebarsBeanPostProcessor(final HandlebarsViewResolver handlebarsViewResolver) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                HandlebarsHelper annotation = findAnnotation(bean.getClass(), HandlebarsHelper.class);
                if (annotation != null) {
                    handlebarsViewResolver.registerHelpers(bean);
                }
                return bean;
            }
        };
    }
}
