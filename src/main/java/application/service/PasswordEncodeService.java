package application.service;

public interface PasswordEncodeService {
    String encode (String password);
    Boolean isEqualEncodeDecodePass(String oldPassword,String encodedPassword);
}
