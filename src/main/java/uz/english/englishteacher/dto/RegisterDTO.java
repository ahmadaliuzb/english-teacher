package uz.english.englishteacher.dto;


import javax.validation.constraints.NotNull;


public class RegisterDTO {

    @NotNull
    private String username;

    @NotNull
    private String fullName;

    @NotNull
    private String password;

    public RegisterDTO(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    public RegisterDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
