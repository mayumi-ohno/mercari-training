package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ユーザー情報を扱うリポジトリ.
 * 
 * @author mayumiono
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** ユーザー情報を生成するローマッパー */
	private final static RowMapper<User> ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setEmail(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("authority"));
		return user;
	};

	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報(該当なしの場合null)
	 */
	public User findByEmail(String email) {
		String sql = "SELECT name, password, authority FROM users WHERE name=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		User user;
		try {
			user = template.queryForObject(sql, param, ROW_MAPPER);
		} catch (Exception e) {
			return null;
		}
		return user;
	}

	/**
	 * ユーザー情報を追加する.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO users (name, password) ");
		sql.append("SELECT :email, :password ");
		sql.append("WHERE NOT EXISTS (SELECT name FROM users WHERE name=:email);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql.toString(), param);

	}

}
