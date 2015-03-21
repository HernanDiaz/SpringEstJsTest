/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdrs.tasktimetracker.dao;

import com.hdrs.tasktimetracker.domain.Company;
import java.util.List;

/**
 *
 * @author Hernan
 */
public interface CompanyDao extends GenericDao<Company, Integer> {

    public List<Company> findAll();
}
