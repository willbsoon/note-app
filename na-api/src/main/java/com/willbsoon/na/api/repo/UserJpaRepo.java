package com.willbsoon.na.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.willbsoon.na.api.entity.User;

@Repository
public interface UserJpaRepo extends JpaRepository<User, Long>{

}
