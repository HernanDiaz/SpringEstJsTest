/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.dao;

import com.hdrs.tasktimetracker.domain.Task;
import java.util.List;

/**
 *
 * @author Hernan
 */
public interface TaskDao extends GenericDao<Task, Integer>{
public List<Task> findAll();
}
