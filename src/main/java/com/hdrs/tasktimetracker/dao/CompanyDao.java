package com.hdrs.tasktimetracker.dao;


import com.hdrs.tasktimetracker.domain.Company;
import java.util.List;

public interface CompanyDao extends GenericDao<Company, Integer>{

    public List<Company> findAll();
    
}
