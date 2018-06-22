package me.rajput.practice.it.security;

//@EnableWebSecurity
public class SecurityConfig {//extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.antMatchers("/addUser", "/deleteUser").hasRole(UserType.ADMIN.name())			
//				.antMatchers("/**").permitAll()		
//				.and().httpBasic();
//	}
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("drajput").password("ishi").roles(UserType.ADMIN.name());
//	}
}
