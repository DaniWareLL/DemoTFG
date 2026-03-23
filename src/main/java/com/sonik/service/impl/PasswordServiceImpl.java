package com.sonik.service.impl;


import com.sonik.service.PasswordService;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordServiceImpl implements PasswordService {

    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
