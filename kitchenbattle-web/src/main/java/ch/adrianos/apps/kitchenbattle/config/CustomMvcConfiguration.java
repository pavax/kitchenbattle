package ch.adrianos.apps.kitchenbattle.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class CustomMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public ObjectMapper buildObjectMapper() {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setFindModulesViaServiceLoader(true);
        jackson2ObjectMapperFactoryBean.setFeaturesToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        jackson2ObjectMapperFactoryBean.setFeaturesToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        jackson2ObjectMapperFactoryBean.afterPropertiesSet();
        return jackson2ObjectMapperFactoryBean.getObject();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds(0);
        webContentInterceptor.setUseExpiresHeader(true);
        webContentInterceptor.setUseCacheControlHeader(true);
        webContentInterceptor.setUseCacheControlNoStore(true);
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(webContentInterceptor);
        //interceptorRegistration.excludePathPatterns("**/image/**");
        interceptorRegistration.addPathPatterns("/api/**/*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
