package com.hdrs.tasktimetracker.service;

import java.util.List;
import com.hdrs.tasktimetracker.domain.Company;
import com.hdrs.tasktimetracker.dao.vo.Result;

public interface CompanyService {

    public Result<Company> store(
        Integer idCompany,
        String companyName,
        String actionUsername);

    public Result<Company> remove(Integer idCompany, String actionUsername);
    public Result<Company> find(Integer idCompany, String actionUsername);
    public Result<List<Company>> findAll(String actionUsername);

}