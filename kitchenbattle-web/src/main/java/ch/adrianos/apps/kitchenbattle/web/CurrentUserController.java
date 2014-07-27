package ch.adrianos.apps.kitchenbattle.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/current-user")
public class CurrentUserController {

    @RequestMapping(method = RequestMethod.GET)
    public UserResource getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            return new UserResource(user.getUsername(), AuthorityUtils.authorityListToSet(user.getAuthorities()));
        }
        return null;
    }

    private class UserResource {
        private final String username;

        private Set<String> roles;

        public UserResource(String username, Set<String> roles) {

            this.username = username;
            this.roles = roles;
        }

        public String getUsername() {
            return username;
        }

        public Set<String> getRoles() {
            return roles;
        }
    }
}
