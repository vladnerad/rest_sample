package com.priselkov.rest_sample.repository;

import com.priselkov.rest_sample.model.Role;
import com.priselkov.rest_sample.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
