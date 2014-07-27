package ch.adrianos.apps.kitchenbattle.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin appApi() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(appApiInfo())
                .swaggerGroup("api")
                .includePatterns(".*api.*");
    }

//    @Bean
//    public SwaggerSpringMvcPlugin manageApi() {
//        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
//                .apiInfo(manageApiInfo())
//                .swaggerGroup("manage")
//                .includePatterns(".*manage.*");
//    }

    private ApiInfo appApiInfo() {
        return new ApiInfo(
                "Kitchenbattle Public-API",
                "Kitchenbattle (c) by adrianos",
                "My Apps API terms of service",
                "info@adrianos.ch",
                "My Apps API Licence Type",
                "My Apps API License URL"
        );
    }

    private ApiInfo manageApiInfo() {
        return new ApiInfo(
                "Kitchenbattle Manage App-API",
                "Kitchenbattle (c) by adrianos",
                "My Apps API terms of service",
                "info@adrianos.ch",
                "My Apps API Licence Type",
                "My Apps API License URL"
        );
    }
}