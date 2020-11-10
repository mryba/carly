package org.carly.core.companymanagement.repository;

import org.bson.types.ObjectId;
import org.carly.core.companymanagement.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, ObjectId> {
    Boolean existsByEmail(String email);
}
