package application.service;


public interface PasswordEncoder {
    String encode (String password);
    Boolean isEqualEncodeDecodePass(String oldPassword,String encodedPassword);
}
