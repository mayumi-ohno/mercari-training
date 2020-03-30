package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * ユーザー登録をする.
	 * 
	 * @param form 新規ユーザー情報
	 * @return 登録完了：true, 登録失敗：false
	 */
	public boolean register(RegisterUserForm form) {
		if (userRepository.findByEmail(form.getEmail()) != null) {
			return false;
		}

		User user = new User();
		user.setEmail(form.getEmail());
		// パスワードをハッシュ化
		String encode = passwordEncoder.encode(form.getPassword());
		user.setPassword(encode);
		userRepository.insert(user);
		return true;
	}

}
