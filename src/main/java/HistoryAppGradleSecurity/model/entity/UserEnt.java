package HistoryAppGradleSecurity.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class UserEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Column(unique = true,nullable = false)
    private String username;

    private String country;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRoleEnt> roles = new ArrayList<>();

    public UserEnt() {
    }

    public String getUsername() {
        return username;
    }

    public UserEnt setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEnt setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserEnt setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserEnt setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEnt setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<UserRoleEnt> getRoles() {
        return roles;
    }

    public UserEnt setRoles(List<UserRoleEnt> roles) {
        this.roles = roles;
        return this;
    }
    public UserEnt addRole(UserRoleEnt role) {
        this.roles.add(role);
        return this;
    }
    public Long getId() {
        return id;
    }

    public UserEnt setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", password='" + (password != null ? "[PROVIDED]" : "[N/A]") + '\'' +
                ", roles=" + roles +
                '}';
    }
}
