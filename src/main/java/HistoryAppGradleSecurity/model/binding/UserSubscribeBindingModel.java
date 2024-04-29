package HistoryAppGradleSecurity.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserSubscribeBindingModel {
    @Size(min = 3,max = 20)
    @NotNull
    private String username;
    @NotNull
    private String fullName;
    @Email
    private String email;
    @Size(min = 3,max = 20)
    @NotNull
    private String password;
    @Size(min = 3,max = 20)
    @NotNull
    private String confirmPassword;
    @Min(value = 18,message = "Over 18 years! ")
    private Integer age;

    public UserSubscribeBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
