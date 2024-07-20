package dev.gosuriya.gout_backend.user.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.gosuriya.gout_backend.user.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer>{

}