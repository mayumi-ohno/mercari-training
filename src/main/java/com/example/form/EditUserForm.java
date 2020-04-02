package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ユーザー更新情報を表すフォーム.
 * 
 * @author mayumiono
 *
 */
public class EditUserForm {

	/** ユーザーID */
	private String id;
	/** メールアドレス */
	@Email(message = "error:enter email address")
	private String email;
	/** 権限 */
	@NotBlank(message = "error:may not be empty")
	private String authority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "EditUserForm [id=" + id + ", email=" + email + ", authority=" + authority + "]";
	}

}
