package dev.gosuriya.gout_backend.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_role")
public record UserRole(
        @Id
        Integer id,
        AggregateReference<User, Integer> userId,
        AggregateReference<Role, Integer> roleId) {

}
