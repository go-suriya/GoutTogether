package dev.gosuriya.gout_backend.user.repositories;

import dev.gosuriya.gout_backend.user.model.User;
import dev.gosuriya.gout_backend.user.model.UserRole;
import java.util.Optional;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

    Optional<UserRole> findOneByUserId(AggregateReference<User, Integer> userId);
}