/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.domain;

import java.io.Serializable;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Hernan
 */
@Entity
@Table(name = "ttt_task")
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t ORDER BY t.taskName"),
    @NamedQuery(name = "Task.findByIdTask", query = "SELECT t FROM Task t WHERE t.idTask = :idTask"),
    @NamedQuery(name = "Task.findByTaskName", query = "SELECT t FROM Task t WHERE t.taskName = :taskName")})
public class Task extends AbstractEntity implements EntityItem<Integer> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_task")
    private Integer idTask;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "task_name")
    private String taskName;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "idTask")
    //private List<TaskLog> taskLogList;
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    @ManyToOne(optional = false)
    private Project project;

    public Task() {
    }

    public Task(Integer idTask) {
        this.idTask = idTask;
    }

    public Task(Integer idTask, String taskName) {
        this.idTask = idTask;
        this.taskName = taskName;
    }

    @Override
    public Integer getId() {
        return idTask;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTask != null ? idTask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.idTask == null && other.idTask != null) || (this.idTask != null && !this.idTask.equals(other.idTask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hdrs.tasktimetracker.domain.Task[ idTask=" + idTask + " ]";
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idTask", idTask)
                .add("taskName", taskName);
        if (project != null) {
            project.addJson(builder);
            Company company = project.getCompany();
            company.addJson(builder);
        }
    }

}