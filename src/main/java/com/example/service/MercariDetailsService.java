package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class MercariDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("unused")
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
//		if ("mayumi.ono@rakus-partners.co.jp".equals(email)) {
//			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
//		}

		return new LoginUser(user, authorityList);
	}

}
