/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.service;

import com.hdrs.tasktimetracker.dao.CompanyDao;
import com.hdrs.tasktimetracker.dao.ProjectDao;
import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.dao.vo.ResultFactory;
import com.hdrs.tasktimetracker.domain.Company;
import com.hdrs.tasktimetracker.domain.Project;
import com.hdrs.tasktimetracker.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hernan
 */
@Transactional
@Service("projectService")
public class ProjectServiceImpl extends AbstractService implements
        ProjectService {
    
    @Autowired
    protected ProjectDao projectDao;
    @Autowired
    protected CompanyDao companyDao;

    @Override
    public Result<Project> store(Integer idProject, Integer idCompany, String projectName, String actionUsername) {
        User actionUser = userDao.find(actionUsername);
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }
               
        Project proyecto;
        if (idProject == null) {
            proyecto = new Project();
        } else {
            proyecto = projectDao.find(idProject);
            if (proyecto == null) {
                return ResultFactory.getFailResult("Unable to find company instance with ID=" + idCompany);
            }
        }
        
        Company c = companyDao.find(idCompany);
        if (c!= null) proyecto.setCompany(c);
        proyecto.setProjectName(projectName);
        
        if (proyecto.getId() == null) {
            projectDao.persist(proyecto);
        } else {
            proyecto = projectDao.merge(proyecto);
        }
        return ResultFactory.getSuccessResult(proyecto);
    }

    @Override
    public Result<Project> remove(Integer idProject, String actionUsername) {
         User actionUser = userDao.find(actionUsername);
        if (!actionUser.isAdmin()) {
            return ResultFactory.getFailResult(USER_NOT_ADMIN);
        }
        if (idProject == null) {
            return ResultFactory.getFailResult("Unable to remove Project [null idProject]");
        }
        Project proyecto = projectDao.find(idProject);
        if (proyecto == null) {
            return ResultFactory.getFailResult("Unable to load Project for removal with idProject=" + idProject);
        } else {
            projectDao.remove(proyecto);
            String msg = "Project " + proyecto.getId()
                    + " was deleted by " + actionUsername;
                logger.info(msg);
                return ResultFactory.getSuccessResultMsg(msg);
               }
    }

    @Override
    public Result<Project> find(Integer idProject, String actionUsername) {
         if (isValidUser(actionUsername)) {
            Project proyecto = projectDao.find(idProject);
            return ResultFactory.getSuccessResult(proyecto);
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    public Result<List<Project>> findAll(String actionUsername) {
        if (isValidUser(actionUsername)) {
            return ResultFactory.getSuccessResult(projectDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
    
}
