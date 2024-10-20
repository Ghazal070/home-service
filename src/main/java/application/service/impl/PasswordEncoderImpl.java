package application.service.impl;

import application.service.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }
    @Override
    public Boolean isEqualEncodeDecodePass(String oldPassword,String encodedPassword){
        return oldPassword.equals(encodedPassword);
    }
}
