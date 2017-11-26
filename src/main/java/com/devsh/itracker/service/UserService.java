package com.devsh.itracker.service;

import com.devsh.itracker.dao.UserRepository;
import com.devsh.itracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }
}
