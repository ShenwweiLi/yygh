package edu.zjsr.yygh.service;

public interface PwdService {
    // 本系统的用户密码加密方式
    String encrypt(String password, String salt);
}
