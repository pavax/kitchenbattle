package ch.adrianos.apps.kitchenbattle.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import javax.servlet.MultipartConfigElement;

@Configuration
public class CustomMvcConfiguration {

    @Bean
    public ObjectMapper buildObjectMapper() {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setFindModulesViaServiceLoader(true);
        jackson2ObjectMapperFactoryBean.setFeaturesToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        jackson2ObjectMapperFactoryBean.setFeaturesToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        jackson2ObjectMapperFactoryBean.afterPropertiesSet();
        return jackson2ObjectMapperFactoryBean.getObject();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("1024KB");
        factory.setMaxRequestSize("1024KB");
        return factory.createMultipartConfig();
    }

}
