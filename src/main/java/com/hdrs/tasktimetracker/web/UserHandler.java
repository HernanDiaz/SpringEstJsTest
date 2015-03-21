/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.web;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.User;
import com.hdrs.tasktimetracker.service.UserService;
import static com.hdrs.tasktimetracker.web.AbstractHandler.getJsonErrorMsg;
import static com.hdrs.tasktimetracker.web.AbstractHandler.getJsonSuccessData;
import static com.hdrs.tasktimetracker.web.SecurityHelper.getSessionUser;
import java.text.ParseException;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Hernan
 */
@Controller
@RequestMapping("/user")
public class UserHandler  extends AbstractHandler {
    @Autowired
    protected UserService userService;

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "username", required = true) String username,
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<User> ar = userService.find(username, sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) throws ParseException {
        User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<User> ar = userService.store(
                jsonObj.getString("username"),
                jsonObj.getString("firstName"),
                jsonObj.getString("lastName"),
                jsonObj.getString("email"),
                jsonObj.getString("password"),
                jsonObj.getString("adminRole").charAt(0),
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
         

    @RequestMapping(value = "/remove", method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ) {
        User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<User> ar = userService.remove(
                jsonObj.getString("username"),
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<List<User>> ar = userService.findAll(sessionUser.
                getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}
