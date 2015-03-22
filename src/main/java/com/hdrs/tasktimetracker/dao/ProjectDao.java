package com.hdrs.tasktimetracker.dao;


import com.hdrs.tasktimetracker.domain.Project;
import java.util.List;

public interface ProjectDao extends GenericDao<Project, Integer>{

    public List<Project> findAll();

}
