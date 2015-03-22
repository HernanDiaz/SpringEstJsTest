package com.hdrs.tasktimetracker.dao;


import com.hdrs.tasktimetracker.domain.Task;
import com.hdrs.tasktimetracker.domain.TaskLog;
import com.hdrs.tasktimetracker.domain.User;
import java.util.Date;
import java.util.List;

public interface TaskLogDao extends GenericDao<TaskLog, Integer>{

    public List<TaskLog> findByUser(User user, Date startDate, Date endDate);
    
    public long findTaskLogCountByTask(Task task);
    
    public long findTaskLogCountByUser(User user);
}
