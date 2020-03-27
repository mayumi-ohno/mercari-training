package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

	
/**
 * SpringSecurity各種設定.
 * 
 * @author mayumiono
 *
 */
@Configuration
public class MrcariConfig  extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					// ログイン前にアクセス可とするパス群
					.mvcMatchers("/").permitAll()
					.mvcMatchers("/login_error").permitAll()
					.mvcMatchers("/register").permitAll()
					.mvcMatchers("/register/**").permitAll()
					
					// 上記以外のパスは、ログイン以前のアクセス不可とする
					.anyRequest().authenticated().and();
			// LOGIN
			http.formLogin()
					.loginPage("/") // ログイン画面を表示するパス
					.loginProcessingUrl("/login") // ログイン可否判定するパス（HTMLの入力フォームでth:action()内に指定）
					.failureUrl("/login_error") // ログイン失敗時に遷移させるパス
					.defaultSuccessUrl("/items", true) //ログイン成功時に遷移させるパス
					.usernameParameter("email") //ログインユーザー名（ログイン画面のHTML上の<input name="**">とそろえる）
					.passwordParameter("password")//ログインパスワード（ログイン画面のHTML上の<input name="**">とそろえる）
				.and()
			// LOGOUT
					.logout() 
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //ログアウト処理をするパス
						.logoutSuccessUrl("/") //ログアウト成功時に遷移させるパス
//	                .deleteCookies("JSESSIONID")
						.invalidateHttpSession(true).permitAll();
			
			// end
		}


}
