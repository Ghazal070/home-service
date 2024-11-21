package application.service.impl;

import application.service.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.build.Plugin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
    @Override
    public Boolean isEqualEncodeDecodePass(String rawPassword,String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
    }
}
