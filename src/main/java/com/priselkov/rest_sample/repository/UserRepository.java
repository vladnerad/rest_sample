package com.priselkov.rest_sample.repository;

import com.priselkov.rest_sample.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, String> {
}
