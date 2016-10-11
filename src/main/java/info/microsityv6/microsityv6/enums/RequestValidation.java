package info.microsityv6.microsityv6.enums;

public enum RequestValidation {
    PASSWORD_RECOVERY("Восстановление пароля"),USER_AUTORISATION("Регистрация пользователя");
    private String message;

    private RequestValidation(String message) {
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
}
