package com.example.domain;

/**
 * ユーザー情報を表すドメイン.
 * 
 * @author mayumiono
 *
 */
public class User {

	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;
	/** 権限 */
	private Integer authority;

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

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", authority=" + authority + "]";
	}

}
