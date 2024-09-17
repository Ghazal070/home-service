package service.impl;

import service.PasswordEncode;

public class PasswordEncodeImpl implements PasswordEncode {
    @Override
    public String encode(String password) {
        return password;
    }
}
