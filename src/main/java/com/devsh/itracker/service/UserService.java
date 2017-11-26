package com.devsh.itracker.service;

import com.devsh.itracker.model.User;
import com.devsh.itracker.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRespository;

    public User findByUsername(String username) {
        return userRespository.findByUsername(username);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRespository.findAll(pageable);
    }

    public List<User> findAll() {
        return userRespository.findAll();
    }

    public User findOne(Long id) {
        return userRespository.findOne(id);
    }

    public User create(User user) {
        return userRespository.save(user);
    }

    public User update(User user) {
        return userRespository.save(user);
    }

    public void delete(Long id) {
        userRespository.delete(id);
    }
}
