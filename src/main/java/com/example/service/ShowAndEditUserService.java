package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
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
}
