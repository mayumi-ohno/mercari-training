package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * SpringSecurityによるログイン認証処理を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger( UserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = null;
		try {
			user = userRepository.findByEmail(email);
		} catch (Exception e) {
			throw new UsernameNotFoundException("not found : " + email);
		}

		// 権限付与
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		int authority = user.getAuthority();
		if (authority == 1) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
		} else {
			authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
		}
		
		logger.info("【ログイン認証】email:" + user.getEmail() + " | pass:" + user.getPassword());

		return new LoginUser(user, authorityList);
	}

}
