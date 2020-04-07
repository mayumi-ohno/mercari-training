package com.example.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** ユーザー情報を生成するローマッパー */
	private final static RowMapper<User> ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setAuthority(rs.getInt("authority"));
		return user;
	};

	/**
	 * 全ユーザー情報をid昇順で取得する.
	 * 
	 * @return 全ユーザー情報
	 */
	public List<User> findAll() {
		String sql = "SELECT id, name, password, authority FROM users ORDER BY id;";
		List<User> userList = template.query(sql, ROW_MAPPER);
		return userList;
	}

	/**
	 * メールアドレスからユーザー情報を検索する.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報(該当なしの場合null)
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, password, authority FROM users WHERE name=:email;";
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
		if (user.getAuthority() == null) {
			sql.append("INSERT INTO users (name, password, authority) ");
			sql.append("SELECT :email, :password, 2 ");
		} else {
			sql.append("INSERT INTO users (name, password, authority) ");
			sql.append("SELECT :email, :password, :authority ");
		}
		sql.append("WHERE NOT EXISTS (SELECT name FROM users WHERE name=:email);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql.toString(), param);
		logger.info("【新規ユーザー追加】email:" + user.getEmail() + ", authority:" + user.getAuthority());

	}

	/**
	 * 既存ユーザー情報を更新する.
	 * 
	 * @param user ユーザー情報
	 */
	public void update(User user) {
		String sql = "UPDATE users SET name=:email,authority=:authority WHERE id=:id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
		logger.info("【既存ユーザー更新】email:" + user.getEmail() + ", authority:" + user.getAuthority());
	}

	/**
	 * 既存ユーザーを削除する.
	 * 
	 * @param userId ユーザーID
	 */
	public void delete(Integer userId) {
		String sql = "DELETE FROM users WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", userId);
		template.update(sql, param);
		logger.info("【既存ユーザー削除】userId:" + userId);
	}

}
