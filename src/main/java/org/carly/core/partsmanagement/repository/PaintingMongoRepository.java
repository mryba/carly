package org.carly.core.partsmanagement.repository;

import org.carly.api.rest.criteria.PaintingSearchCriteriaRequest;
import org.carly.core.partsmanagement.model.entity.Painting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.carly.core.shared.utils.criteria.CriteriaBuilder.criteria;
import static org.carly.core.shared.utils.criteria.CriteriaBuilder.regexCriteria;

@Repository
public class PaintingMongoRepository {

    private final MongoTemplate mongoTemplate;


    public PaintingMongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Page<Painting> findWithFilters(PaintingSearchCriteriaRequest searchCriteria, Pageable pageable) {

        Criteria criteria =  criteria(new Criteria(), Criteria::andOperator,
                regexCriteria(Painting.NAME, searchCriteria.getNameToSearch()));

        Query query = new Query();
        query.addCriteria(criteria);

        long count = mongoTemplate.count(query, Painting.class);
        List<Painting> paintings = mongoTemplate.find(query.with(pageable), Painting.class);

        return new PageImpl<>(paintings, pageable, count);

    }


}
