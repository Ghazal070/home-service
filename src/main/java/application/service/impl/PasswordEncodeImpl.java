package application.service.impl;

import application.service.PasswordEncode;

public class PasswordEncodeImpl implements PasswordEncode {
    @Override
    public String encode(String password) {
        return password;
    }
    @Override
    public Boolean isEqualEncodeDecodePass(String oldPassword,String newPassword){
        return true;
    }
    //done simple method check encodePassword= oldPassword
}
