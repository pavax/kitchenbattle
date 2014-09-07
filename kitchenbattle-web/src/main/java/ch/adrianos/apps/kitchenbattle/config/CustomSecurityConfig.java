package ch.adrianos.apps.kitchenbattle.config;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CustomSecurityConfig implements InitializingBean {

    @Value("${kitchenbattle.users.file}")
    private Resource users;


    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(users);
    }

    @Bean
    public AuthenticationSecurity authenticationSecurity() {
        return new AuthenticationSecurity(users);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

        private final Resource users;

        public AuthenticationSecurity(Resource users) {
            this.users = users;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(new InMemoryUserDetailsManager(PropertiesLoaderUtils.loadProperties(users)));
        }
    }

    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private SecurityProperties security;

        @Override
        public void configure(WebSecurity webSecurity) throws Exception {
            webSecurity
                    .ignoring()
                    .antMatchers("/images/**")
                    .antMatchers("/styles/**")
                    .antMatchers("/vendor/**");
        }

        //        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().fullyAuthenticated()
                    .and().httpBasic().realmName("KB APP");
            http.csrf().disable();
        }

    }
}
