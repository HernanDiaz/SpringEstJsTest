/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.service;

import com.hdrs.tasktimetracker.dao.vo.Result;
import com.hdrs.tasktimetracker.domain.Company;
import java.util.List;

/**
 *
 * @author Hernan
 */
public interface CompanyService {

    public Result<Company> store(
            Integer idCompany,
            String companyName,
            String actionUsername);

    public Result<Company> remove(Integer idCompany, String actionUsername);

    public Result<Company> find(Integer idCompany, String actionUsername);

    public Result<List<Company>> findAll(String actionUsername);
}
