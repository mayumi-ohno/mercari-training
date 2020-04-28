package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.service.UserDetailsServiceImpl;

/**
 * SpringSecurity各種設定.
 * 
 * @author mayumiono
 *
 */
@Configuration
@EnableWebSecurity
public class MrcariConfig extends WebSecurityConfigurerAdapter {

	/**
	 * <pre>
	 * bcryptアルゴリズムでハッシュ化する実装を返します.
	 * これを指定することでパスワードハッシュ化やマッチ確認する際に
	 * &#64;Autowired
	 * private PasswordEncoder passwordEncoder;
	 * と記載するとDIされるようになります。
	 * </pre>
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/**
	 * このメソッドをオーバーライドすることで、 特定のリクエストに対して「セキュリティ設定」を 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().mvcMatchers("/", "/login-error", "/register", "/register/**").permitAll() // ログイン前にアクセス可とするパス群
				.anyRequest().authenticated(); // 上記以外のパスは、ログイン以前のアクセス不可とする
		// LOGIN
		http.csrf().disable() // CSRF対策無効化
				.formLogin().loginPage("/") // ログイン画面を表示するパス
				.loginProcessingUrl("/login") // ログイン可否判定するパス（HTMLの入力フォームでth:action()内に指定）
				.failureUrl("/login-error") // ログイン失敗時に遷移させるパス
				.defaultSuccessUrl("/items", true) // ログイン成功時に遷移させるパス
				.usernameParameter("email") // ログインユーザー名（ログイン画面のHTML上の<input name="**">とそろえる）
				.passwordParameter("password")// ログインパスワード（ログイン画面のHTML上の<input name="**">とそろえる）
				.and()
		// LOGOUT
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウト処理をするパス
				.logoutSuccessUrl("/") // ログアウト成功時に遷移させるパス
				.deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll();
	}

	/**
	 * 「認証」に関する設定.<br>
	 * 認証ユーザを取得する「UserDetailsService」の設定や<br>
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
