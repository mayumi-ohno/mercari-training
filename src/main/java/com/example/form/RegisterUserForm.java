package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 新規ユーザー情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class RegisterUserForm {

	/** メールアドレス */
	@NotBlank(message = "error: may not be empty")
	@Email(message = "error: invalid format")
	private String email;
	/** パスワード */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "error: password must contain 8-20 characters, using all of uppercase, lowercase and number")
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
