package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;

/**
 * ユーザー登録処理を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * ユーザー登録をする.
	 * 
	 * @param form 新規ユーザー情報
	 */
	public void register(RegisterUserForm form) {
		User user = new User();
		user.setEmail(form.getEmail());
		// パスワードを暗号化
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String encode = bcrypt.encode(form.getPassword());
		user.setPassword(encode);
		userRepository.insert(user);
	}

}
