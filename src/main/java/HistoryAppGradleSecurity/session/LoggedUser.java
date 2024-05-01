package HistoryAppGradleSecurity.session;

import HistoryAppGradleSecurity.model.entity.UserRoleEnt;
import HistoryAppGradleSecurity.model.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Component
@SessionScope
public class LoggedUser {
    private String username;
    private Set<UserRoleEnt> roles;
    private boolean isLogged;

    public LoggedUser () {

        this.roles = new HashSet<>();
    }

    public void reset() {
        this
                .setUsername(null)
                .setRoles(Collections.emptySet())
                .setLogged(false);
    }

    public String getUsername () {

        return username;
    }

    public LoggedUser setUsername (String username) {

        this.username = username;
        return this;
    }

    public Set<UserRoleEnt> getRoles () {

        return roles;
    }

    public LoggedUser setRoles (Set<UserRoleEnt> roles) {

        this.roles = roles;
        return this;
    }

    public boolean isLogged () {

        return isLogged;
    }

    public LoggedUser setLogged (boolean logged) {

        isLogged = logged;
        return this;
    }

    public boolean isAdmin () {

        return this.roles.stream()
                .anyMatch(role -> role.getRole().equals(UserRoleEnum.ADMIN));
    }
}
