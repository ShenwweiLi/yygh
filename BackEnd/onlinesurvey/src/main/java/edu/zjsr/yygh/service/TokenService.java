package edu.zjsr.yygh.service;

import edu.zjsr.yygh.entity.User;

public interface TokenService {
    // 生成Token
    String getToken(User user);
}
