package org.carly.user_management.core.repository;

import org.bson.types.ObjectId;
import org.carly.user_management.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
}
