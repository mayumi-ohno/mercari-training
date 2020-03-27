package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 新規ユーザー情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class RegisterUserForm {

	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [email=" + email + ", password=" + password + "]";
	}

}
