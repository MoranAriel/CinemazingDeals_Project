package com.cinemazing.dao;

import java.util.List;

import com.cinemazing.beans.Company;

public interface CompaniesDAO {


    
    public boolean isCompanyExistsById (int id);

    public boolean isCompanyExistsByEmailAndPassword (String email, String password);

    public boolean isCompanyExistsByEmail (String email);

    public boolean isCompanyExistsByName (String name);

    public void addCompany (Company company);

    public void updateCompany (Company company);

    public void deleteCompany (int companyID);

    public List<Company> getAllCompanies();

    public Company getOneCompany (int companyID);


}
