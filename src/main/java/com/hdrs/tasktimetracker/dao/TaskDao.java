package com.hdrs.tasktimetracker.dao;


import com.hdrs.tasktimetracker.domain.Task;
import java.util.List;

public interface TaskDao extends GenericDao<Task, Integer>{
   
    public List<Task> findAll();    
}
