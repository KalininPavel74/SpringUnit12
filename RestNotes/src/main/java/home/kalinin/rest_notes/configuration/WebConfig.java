package home.kalinin.rest_notes.configuration;

import home.kalinin.rest_notes.aspect.HTTPInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HTTPInterceptor());
    }

}
