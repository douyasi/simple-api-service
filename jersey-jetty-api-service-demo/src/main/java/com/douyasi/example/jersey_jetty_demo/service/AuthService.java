package com.douyasi.example.jersey_jetty_demo.service;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.douyasi.example.jersey_jetty_demo.domain.Token;
import com.douyasi.example.jersey_jetty_demo.domain.User;
import com.douyasi.example.jersey_jetty_demo.domain.dao.TokenDao;
import com.douyasi.example.jersey_jetty_demo.domain.dao.UserDao;
import com.douyasi.example.jersey_jetty_demo.exception.AppException;
import com.douyasi.tinyme.common.util.RandomUtil;

public class AuthService {
    
    static SqlSessionFactory sqlSessionFactory = null;
    static {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    public User validateCredentials(String email, String password) throws AppException
    {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        String saltedPassword = encryptPassword(password, null);
        User user = userDao.getUserByCredentials(email, saltedPassword);
        sqlSession.close();
        if (user == null) {
            throw new AppException("403", "illegal or incorrect credentials!");
        }
        return user;
    }
    
    public String encryptPassword(String password, String salt) {
        if (salt == null) {
            salt = "TinyMe&168";
        }
        String newPassword = md5(password).substring(6, 24) + salt;
        return md5(newPassword);
    }
    
    public String md5(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] arrPlain = md.digest(plain.getBytes(Charset.forName("UTF-8")));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arrPlain.length; ++ i) {
                sb.append(Integer.toHexString((arrPlain[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            
        }
        return null;
    }
    
    public Token generateToken(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TokenDao tokenDao = sqlSession.getMapper(TokenDao.class);
            Token token = new Token();
            token.setToken(RandomUtil.randomString(42));
            Long time = System.currentTimeMillis();
            Long expiredAt = time + 3600*1000;
            // Timestamp ts = new Timestamp(System.currentTimeMillis());
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
            token.setExpiredAt(expiredAt/1000);
            token.setUid(user.getId());
            token.setCreatedAt(now);
            token.setUpdatedAt(now);
            tokenDao.insert(token);
            sqlSession.commit();
            return token;
        } catch (Exception e) {
            
        } finally {
            sqlSession.close();
        }
        return null;
    }
}
