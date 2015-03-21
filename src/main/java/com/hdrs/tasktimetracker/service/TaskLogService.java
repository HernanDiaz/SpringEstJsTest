/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.service;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.TaskLog;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hernan
 */
public interface TaskLogService {

    public Result<TaskLog> store(
            Integer idTaskLog,
            Integer idTask,
            String username,
            String taskDescription,
            Date taskLogDate,
            int taskMinutes,
            String actionUsername);

    public Result<TaskLog> remove(Integer idTaskLog, String actionUsername);

    public Result<TaskLog> find(Integer idTaskLog, String actionUsername);

    public Result<List<TaskLog>> findByUser(String username, Date startDate, Date endDate, String actionUsername);
}
