/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.web;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.Company;
import com.hdrs.tasktimetracker.domain.User;
import com.hdrs.tasktimetracker.service.CompanyService;
import com.hdrs.tasktimetracker.service.ProjectService;
import static com.hdrs.tasktimetracker.web.SecurityHelper.getSessionUser;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Hernan
 */
@Controller
@RequestMapping("/company")
public class CompanyHandler extends AbstractHandler {

    @Autowired
    protected CompanyService companyService;
    @Autowired
    protected ProjectService projectService;

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(@RequestParam(value = "idCompany", required = true) Integer idCompany,
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<Company> ar = companyService.find(idCompany,
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<Company> ar = companyService.store(
                getIntegerValue(jsonObj.get("idCompany")),
                jsonObj.getString("companyName"),
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
        Result<List<Company>> ar = companyService.findAll(sessionUser.
                getUsername());
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
            HttpServletRequest request) {
        User sessionUser = getSessionUser(request);
       
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<Company> ar = companyService.remove(
                getIntegerValue(jsonObj.get("idCompany")),
                sessionUser.getUsername());
        if (ar.isSuccess()) {
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}
