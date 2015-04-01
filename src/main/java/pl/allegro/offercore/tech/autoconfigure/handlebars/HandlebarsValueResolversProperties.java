package pl.allegro.offercore.tech.autoconfigure.handlebars;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.handlebars.valueResolver")
public class HandlebarsValueResolversProperties {

    private Boolean map = true;

    private Boolean javaBean = true;

    private Boolean field = false;

    private Boolean method = false;

    public Boolean isMap() {
        return map;
    }

    public void setMap(Boolean map) {
        this.map = map;
    }

    public Boolean isJavaBean() {
        return javaBean;
    }

    public void setJavaBean(Boolean javaBean) {
        this.javaBean = javaBean;
    }

    public Boolean isField() {
        return field;
    }

    public void setField(Boolean field) {
        this.field = field;
    }

    public Boolean isMethod() {
        return method;
    }

    public void setMethod(Boolean method) {
        this.method = method;
    }


}
