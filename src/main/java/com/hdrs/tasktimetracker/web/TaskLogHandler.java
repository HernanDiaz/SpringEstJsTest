/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.web;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.TaskLog;
import com.hdrs.tasktimetracker.domain.User;
import com.hdrs.tasktimetracker.service.TaskLogService;
import static com.hdrs.tasktimetracker.web.SecurityHelper.getSessionUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Hernan
 */
@Controller
@RequestMapping("/taskLog")
public class TaskLogHandler extends AbstractHandler {

    static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    @Autowired
    protected TaskLogService taskLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT_yyyyMMdd, true));
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTaskLog", required = true) Integer idTaskLog,
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<TaskLog> ar = taskLogService.find(idTaskLog,
                sessionUser.getUsername());
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
        String dateVal = jsonObj.getString("taskLogDate");
        Result<TaskLog> ar = taskLogService.store(
                getIntegerValue(jsonObj.get("idTaskLog")),
                getIntegerValue(jsonObj.get("idTask")),
                jsonObj.getString("username"),
                jsonObj.getString("taskDescription"),
                DATE_FORMAT_yyyyMMdd.parse(dateVal),
                jsonObj.getInt("taskMinutes"),
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
        Result<TaskLog> ar = taskLogService.remove(
                getIntegerValue(jsonObj.get("idTaskLog")),
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findByUser", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public String findByUser(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "startDate", required = true) Date startDate,
            @RequestParam(value = "endDate", required = true) Date endDate,
            HttpServletRequest request
    ) {
        User sessionUser = getSessionUser(request);
        Result<List<TaskLog>> ar = taskLogService.findByUser(
                username,
                startDate,
                endDate,
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}