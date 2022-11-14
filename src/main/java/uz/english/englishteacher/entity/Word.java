package uz.english.englishteacher.entity;

import uz.english.englishteacher.entity.User;
import uz.english.englishteacher.entity.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "words")
@Entity
public class Word extends BaseEntity {

    @NotBlank(message = "Email is mandatory")
    private String uz;

    @NotBlank(message = "Email is mandatory")
    private String english;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Word(String uz, String english) {
        this.uz = uz;
        this.english = english;
    }

    public Word() {
    }

    public String getUz() {
        return uz;
    }

    public void setUz(String uz) {
        this.uz = uz;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }


}
