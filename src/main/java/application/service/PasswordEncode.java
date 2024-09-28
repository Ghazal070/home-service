package application.service;

public interface PasswordEncode {
    String encode (String password);
    Boolean isEqualEncodeDecodePass(String oldPassword,String newPassword);
}
