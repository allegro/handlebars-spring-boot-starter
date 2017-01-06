package pl.allegro.tech.boot.autoconfigure.handlebars;

import com.github.jknack.handlebars.ValueResolver;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.isInstanceOf;

@ConfigurationProperties(prefix = "handlebars")
@EnableConfigurationProperties(HandlebarsValueResolversProperties.class)
public class HandlebarsProperties extends AbstractTemplateViewResolverProperties {

    static final String DEFAULT_PREFIX = "classpath:templates/";
    static final String DEFAULT_SUFFIX = ".hbs";

    private Boolean registerMessageHelper = true;
    private Boolean failOnMissingFile = false;
    private Boolean bindI18nToMessageSource = false;

    private final HandlebarsValueResolversProperties valueResolversProperties;

    @Autowired
    protected HandlebarsProperties(HandlebarsValueResolversProperties valueResolversProperties) {
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
        this.valueResolversProperties = valueResolversProperties;
        this.setCache(true);
    }

    @Override
    public void applyToViewResolver(Object viewResolver) {
        super.applyToViewResolver(viewResolver);
        isInstanceOf(HandlebarsViewResolver.class, viewResolver,
                "ViewResolver is not an instance of HandlebarsViewResolver :" + viewResolver);
        HandlebarsViewResolver resolver = (HandlebarsViewResolver) viewResolver;

        List<ValueResolver> valueResolvers = new ArrayList<ValueResolver>();

        addValueResolverIfNeeded(valueResolvers, valueResolversProperties.isJavaBean(), JavaBeanValueResolver.INSTANCE);
        addValueResolverIfNeeded(valueResolvers, valueResolversProperties.isMap(), MapValueResolver.INSTANCE);
        addValueResolverIfNeeded(valueResolvers, valueResolversProperties.isField(), FieldValueResolver.INSTANCE);
        addValueResolverIfNeeded(valueResolvers, valueResolversProperties.isMethod(), MethodValueResolver.INSTANCE);

        resolver.setValueResolvers(listToArray(valueResolvers));
        resolver.setRegisterMessageHelper(registerMessageHelper);
        resolver.setFailOnMissingFile(failOnMissingFile);
        resolver.setBindI18nToMessageSource(bindI18nToMessageSource);
    }

    public void setRegisterMessageHelper(Boolean registerMessageHelper) {
        this.registerMessageHelper = registerMessageHelper;
    }

    public void setFailOnMissingFile(Boolean failOnMissingFile) {
        this.failOnMissingFile = failOnMissingFile;
    }

    public void setBindI18nToMessageSource(Boolean bindI18nToMessageSource) {
        this.bindI18nToMessageSource = bindI18nToMessageSource;
    }

    private void addValueResolverIfNeeded(List<ValueResolver> resolvers, boolean property, ValueResolver resolver) {
        if (property) {
            resolvers.add(resolver);
        }
    }

    private ValueResolver[] listToArray(List<ValueResolver> resolvers) {
        return resolvers.toArray(new ValueResolver[resolvers.size()]);
    }
}
