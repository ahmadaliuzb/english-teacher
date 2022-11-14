package uz.english.englishteacher.dto;


import java.util.List;
import java.util.UUID;

public class JwtResponse {
    private String jwt;
    private UUID id;
    private String username;
    private String email;

    public JwtResponse(String jwt, UUID id, String username, String email) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public JwtResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
