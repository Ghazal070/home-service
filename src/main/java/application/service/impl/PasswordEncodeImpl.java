package application.service.impl;

import application.service.PasswordEncode;

public class PasswordEncodeImpl implements PasswordEncode {
    @Override
    public String encode(String password) {
        return password;
    }
    //todo check encodePassword= oldPassword
}
