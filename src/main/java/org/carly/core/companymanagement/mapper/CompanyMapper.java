package org.carly.core.companymanagement.mapper;

import org.carly.api.rest.CompanyRest;
import org.carly.core.companymanagement.model.Company;
import org.carly.core.shared.utils.MapperService;
import org.carly.core.vehiclemanagement.mapper.CarMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements MapperService<CompanyRest, Company> {


    private CarMapper carMapper;

    //todo: Fields in mapper commented temporarily.

    @Override
    public CompanyRest mapFromDomainObject(Company domain, CompanyRest rest) {
        if (domain.getId() != null) {
            rest.setId(rest.getId());
        }
        rest.setAddress(domain.getAddress());
        rest.setBrand(domain.getBrand());
//        rest.setCars(domain.getCars().stream().map(e -> carMapper.simplifyRestObject(e)).collect(Collectors.toList()));
//        rest.setLogoId(domain.getLogoId());
        rest.setName(domain.getName());
//        rest.setYearOfEstablishment(domain.getYearOfEstablishment());
        return rest;
    }

    @Override
    public Company mapToDomainObject(Company domain, CompanyRest rest) {
        if (rest.getId() != null) {
            domain.setId(rest.getId());
        }
        domain.setAddress(domain.getAddress());
//        domain.setCars(rest.getCars().stream().map(e -> carMapper.simplifyDomainObject(e)).collect(Collectors.toList()));
        domain.setName(rest.getName());
        domain.setBrand(rest.getBrand());
//        domain.setYearOfEstablishment(rest.getYearOfEstablishment());
        return domain;
    }

    @Override
    public CompanyRest simplifyRestObject(Company domain) {
        CompanyRest rest = new CompanyRest();
        return mapFromDomainObject(domain, rest);
    }

    @Override
    public Company simplifyDomainObject(CompanyRest rest) {
        Company domain = new Company();
        return mapToDomainObject(domain, rest);
    }
}