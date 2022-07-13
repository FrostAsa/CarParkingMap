package com.example.demo.config;

import com.example.demo.util.MD5Util;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * encryption method
     *
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.getMD5(rawPassword.toString());
    }

    /**
     * match method
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) throws AuthenticationServiceException {
        String encode = encode(rawPassword);
        boolean equals = encodedPassword.equals(encode);
        return true;

    }
}


