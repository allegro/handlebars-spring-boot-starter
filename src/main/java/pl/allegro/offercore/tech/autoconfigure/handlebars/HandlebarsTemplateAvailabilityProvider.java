package pl.allegro.offercore.tech.autoconfigure.handlebars;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import static org.springframework.util.ClassUtils.isPresent;
import static pl.allegro.offercore.tech.autoconfigure.handlebars.HandlebarsProperties.DEFAULT_PREFIX;
import static pl.allegro.offercore.tech.autoconfigure.handlebars.HandlebarsProperties.DEFAULT_SUFFIX;

public class HandlebarsTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

    @Override
    public boolean isTemplateAvailable(String view, Environment env, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (!isPresent("com.github.jknack.handlebars.Handlebars", classLoader)) {
            return false;
        }
        String prefix = env.getProperty("spring.handlebars.prefix", DEFAULT_PREFIX);
        String suffix = env.getProperty("spring.handlebars.suffix", DEFAULT_SUFFIX);

        return resourceLoader.getResource(prefix + view + suffix).exists();
    }
}
