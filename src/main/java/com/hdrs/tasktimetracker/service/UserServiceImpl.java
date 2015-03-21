/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.service;

import com.hdrs.tasktimetracker.dao.TaskDao;
import com.hdrs.tasktimetracker.dao.TaskLogDao;
import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.dao.vo.ResultFactory;
import com.hdrs.tasktimetracker.domain.Company;
import com.hdrs.tasktimetracker.domain.TaskLog;
import com.hdrs.tasktimetracker.domain.User;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hernan
 */
@Transactional
@Service("userService")
public class UserServiceImpl extends AbstractService implements
        UserService {

    @Autowired
    protected TaskLogDao taskLogDao;

    @Override
    public Result<User> store(String username,
            String firstName,
            String lastName,
            String email,
            String password,
            Character adminRole,
            String actionUsername) {
        User actionUser = userDao.find(actionUsername);
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }
        User usuario;
        usuario = userDao.find(username);

        if (usuario == null) {
            usuario = new User();
        }

        if (email == null || email.length()<3) {
            return ResultFactory.getFailResult("Email can not be empty");
        }
        
        if (password == null || password.length()<1) {
            return ResultFactory.getFailResult("Password can not be empty");
        }

        User emailUser = userDao.findByEmail(email);
        if (emailUser != null && (emailUser.getUsername() == null ? username != null : !emailUser.getUsername().equals(username))) {
            return ResultFactory.getFailResult("Email is already registered");
        }

        if (adminRole != 'Y' && adminRole != 'N') {
            return ResultFactory.getFailResult("Admin role must be Y or N");
        }

        usuario.setAdminRole(adminRole);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setFirstName(firstName);
        usuario.setLastName(lastName);

        if (usuario.getUsername() == null) {
            usuario.setUsername(username);
            userDao.persist(usuario);
        } else {
            usuario = userDao.merge(usuario);
        }
        return ResultFactory.getSuccessResult(usuario);
    }

    @Override
    public Result<User> remove(String username, String actionUsername) {
        User actionUser = userDao.find(actionUsername);
        if (actionUser == null) {
            return ResultFactory.getFailResult(USER_INVALID);
        }

        if (username == null) {
            return ResultFactory.getFailResult("Unable to remove User [null username]");
        }

        if (actionUser.getUsername().equals(username)) {
            return ResultFactory.getFailResult("User can not delete himselve");
        }

        User usuario = userDao.find(username);
        if (usuario == null) {
            return ResultFactory.getFailResult("Unable to load User for removal with username=" + username);
        }

        if (taskLogDao.findTaskLogCountByUser(usuario)==0) {
            userDao.remove(usuario);
            return ResultFactory.getSuccessResultMsg("User removed successfully");
        } else {
            return ResultFactory.getFailResult("User can not be deleted because he has logtasks");
        }

    }

    @Override
    public Result<User> find(String username, String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(userDao.find(username));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    public Result<List<User>> findAll(String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(userDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    public Result<User> findByUsernamePassword(String username, String password) {
        User usuario = userDao.findByUsernamePassword(username, password);
        if (usuario == null) {
            return ResultFactory.getFailResult("Unable to verify user/password combination!");
        }
        return ResultFactory.getSuccessResult(usuario);
    }

}
