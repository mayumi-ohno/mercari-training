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
public class AddUserForm {

	/** メールアドレス */
	@NotBlank(message = "error: Email may not be empty")
	@Email(message = "error: invalid Email format")
	private String email;
	/** パスワード */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$", message = "error: Password must contain 8-20 characters, using all of uppercase, lowercase and number")
	private String password;
//	@NotBlank(message = "error: Select Authority")
	@Pattern(regexp = "^[0-9]$", message = "error: Select Authority")
	private String authority;

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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "AddUserForm [email=" + email + ", password=" + password + ", authority=" + authority + "]";
	}

}
