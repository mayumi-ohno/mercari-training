package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.form.AddUserForm;
import com.example.form.EditUserForm;
import com.example.repository.UserRepository;

/**
 * ユーザー情報を処理するサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ShowAndEditUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 全ユーザー情報を取得する.
	 * 
	 * @return ユーザー情報一覧
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * ユーザー情報更新時、他の登録済ユーザーとのメールアドレス重複確認をする.
	 * 
	 * @param form メールアドレス含むフォーム
	 * @return 重複あり：true, なし：false
	 */
	public boolean checkEmailDuplication(EditUserForm form) {
		User user = userRepository.findByEmail(form.getEmail());
		if (user != null && !String.valueOf(user.getId()).equals(form.getId())) {
			return true;
		}
		return false;
	}

	/**
	 * ユーザー追加時、既存ユーザーとのメールアドレス重複確認をする.
	 * 
	 * @param form メールアドレス含むフォーム
	 * @return 重複あり：true, なし：false
	 */
	public boolean checkEmailDuplication(AddUserForm form) {
		User user = userRepository.findByEmail(form.getEmail());
		if (user != null) {
			return true;
		}
		return false;
	}

	/**
	 * 既存ユーザー情報を更新する.
	 * 
	 * @param form ユーザー情報更新内容
	 */
	public void updateUserInfo(EditUserForm form) {
		User user = new User();
		user.setId(Integer.parseInt(form.getId()));
		user.setEmail(form.getEmail());
		user.setAuthority(Integer.parseInt(form.getAuthority()));
		userRepository.update(user);
	}

	/**
	 * 既存ユーザーを削除する.
	 * 
	 * @param form ユーザー情報
	 */
	public void deleteUser(Integer userId) {
		userRepository.delete(userId);
	}

	/**
	 * 新規ユーザー登録.
	 * 
	 * @param form ユーザー情報
	 */
	public void addUser(AddUserForm form) {
		User user = new User();
		user.setEmail(form.getEmail());
		user.setAuthority(Integer.parseInt(form.getAuthority()));
		// パスワードをハッシュ化
		String encode = passwordEncoder.encode(form.getPassword());
		user.setPassword(encode);
		userRepository.insert(user);
	}
}
