package com.willbsoon.na.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.willbsoon.na.api.entity.User;

@Repository
public interface UserJpaRepo extends JpaRepository<User, Long>{
	Optional<User> findByUid(String email);
	Optional<User> findByUidAndProvider(String uid, String provider);
}
