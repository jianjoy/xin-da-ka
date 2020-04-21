package com.dragon3.user.service;

import com.dragon3.infrastructure.exception.MobileAlreadyExistException;
import com.dragon3.security.util.MyPasswordEncoder;
import com.dragon3.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public void create(User user) throws MobileAlreadyExistException {
        long mobileCount = (long) em.createQuery("select count(id) from User where mobile=:mobile")
                .setParameter("mobile", user.getMobile()).getSingleResult();
        if(mobileCount>0){
            throw new MobileAlreadyExistException();
        }

        user.setPassword(MyPasswordEncoder.encoder(user.getPassword()));
        user.setRegisterTime(new Date());
        em.persist(user);
    }
}
