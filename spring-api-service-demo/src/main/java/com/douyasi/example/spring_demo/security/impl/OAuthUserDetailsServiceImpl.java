package com.douyasi.example.spring_demo.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.douyasi.example.spring_demo.domain.Token;
import com.douyasi.example.spring_demo.domain.User;
import com.douyasi.example.spring_demo.domain.dao.TokenDao;
import com.douyasi.example.spring_demo.domain.dao.UserDao;
import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.example.spring_demo.security.AuthUser;
import com.douyasi.example.spring_demo.security.TokenUserDetailsService;

@Service
public class OAuthUserDetailsServiceImpl implements TokenUserDetailsService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            userDetails = this.loadUserByToken(username);
        } catch (AppException e) {
            // throw e;
           throw new UsernameNotFoundException(e.getCode() + ":" + e.getMessage());
        }
        return userDetails;
    }
    
    @Override
    public UserDetails loadUserByToken(String token) throws AppException {
        Token oToken = tokenDao.getTokenByToken(token);
        if (oToken == null) {
            throw new AppException("403", "fail to authenticate access token!");
        } else {
            Long uid = oToken.getUid();
            Long expiredAt = oToken.getExpiredAt();
            Long time = System.currentTimeMillis();
            System.out.println("time: " + time + "expiredAt: " + expiredAt);
            if (time < expiredAt*1000) {  // not expired
                 User user = userDao.getUser(uid);
                 if (user == null) {
                     return null;
                 }
                 AuthUser authUser = AuthUser.create(user);
                 return authUser;
            } else {
                throw new AppException("401", "access token already expired, please recall `/api/login` !");
            }
        }
    }
}
