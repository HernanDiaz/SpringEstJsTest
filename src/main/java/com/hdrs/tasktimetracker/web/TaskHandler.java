/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.web;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.Company;
import com.hdrs.tasktimetracker.domain.Task;
import com.hdrs.tasktimetracker.domain.TaskLog;
import com.hdrs.tasktimetracker.domain.User;
import com.hdrs.tasktimetracker.service.TaskLogService;
import com.hdrs.tasktimetracker.service.TaskService;
import static com.hdrs.tasktimetracker.web.AbstractHandler.getJsonErrorMsg;
import static com.hdrs.tasktimetracker.web.AbstractHandler.getJsonSuccessData;
import static com.hdrs.tasktimetracker.web.SecurityHelper.getSessionUser;
import static com.hdrs.tasktimetracker.web.TaskLogHandler.DATE_FORMAT_yyyyMMdd;
import java.text.ParseException;
import java.util.Date;
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
@RequestMapping("/task")
public class TaskHandler extends AbstractHandler {
    @Autowired
    protected TaskService taskService;

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTask", required = true) Integer idTask,
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<Task> ar = taskService.find(idTask,
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
        Result<Task> ar = taskService.store(
                getIntegerValue(jsonObj.get("idTask")),
                getIntegerValue(jsonObj.get("idProject")),
                jsonObj.getString("taskName"),
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
        Result<Task> ar = taskService.remove(
                getIntegerValue(jsonObj.get("idTask")),
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
        Result<List<Task>> ar = taskService.findAll(sessionUser.
                getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}
