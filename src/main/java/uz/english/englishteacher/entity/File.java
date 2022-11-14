package uz.english.englishteacher.entity;

import uz.english.englishteacher.entity.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class File extends BaseEntity {

    private String name;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

    public File(String name, String contentType, Long size, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.data = data;
    }

    public File() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}