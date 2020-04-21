package com.dragon3.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zbwang on 16/9/4.
 */
public class MyPasswordEncoder {
    private static PasswordEncoder bCryptPasswordEncoder;

    static {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        bCryptPasswordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    public static String encoder(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public static PasswordEncoder getEncoder() {
        return bCryptPasswordEncoder;
    }
}
