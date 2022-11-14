package uz.english.englishteacher.dto;

public class EmailDto {
    private String email;

    public EmailDto(String email) {
        this.email = email;
    }

    public EmailDto() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
