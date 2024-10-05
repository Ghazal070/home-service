package application.service.impl;

import application.service.PasswordEncodeService;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncodeServiceImpl implements PasswordEncodeService {
    @Override
    public String encode(String password) {
        return password;
    }
    @Override
    public Boolean isEqualEncodeDecodePass(String oldPassword,String encodedPassword){
        return oldPassword.equals(encodedPassword);
    }
    //done simple method check encodePassword= oldPassword
}
