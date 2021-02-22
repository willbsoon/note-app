package com.willbsoon.na.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.willbsoon.na.api.advice.exception.CUserNotFoundException;
import com.willbsoon.na.api.repo.UserJpaRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserJpaRepo userJpaRepo;
	
	@Override
	public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
		return (UserDetails) userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);
	}

}
