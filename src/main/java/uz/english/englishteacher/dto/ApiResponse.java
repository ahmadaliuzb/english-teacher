package uz.english.englishteacher.dto;


public class ApiResponse {
    private Object message;
    private Boolean success;

    public ApiResponse(Object message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse() {
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
