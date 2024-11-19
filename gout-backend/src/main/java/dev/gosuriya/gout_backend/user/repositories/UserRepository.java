package dev.gosuriya.gout_backend.user.repositories;

import dev.gosuriya.gout_backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {

    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);
}
