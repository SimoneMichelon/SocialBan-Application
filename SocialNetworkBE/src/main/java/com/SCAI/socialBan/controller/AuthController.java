package com.SCAI.socialBan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCAI.socialBan.configuration.JwtProvider;
import com.SCAI.socialBan.model.User;
import com.SCAI.socialBan.repository.UserRepository;
import com.SCAI.socialBan.request.LoginRequest;
import com.SCAI.socialBan.response.AuthResponse;
import com.SCAI.socialBan.service.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {

		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String dob = user.getDob();

		User isExistEmail = userRepository.findByEmail(email);
		if (isExistEmail != null) {
			throw new Exception("Email is already used with another account");
		}

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFullName(fullName);
		createdUser.setDob(dob);
		createdUser.setImgProfile("https://cdn3d.iconscout.com/3d/free/thumb/free-user-3814118-3187499.png?f=webp");
		createdUser.setRole(User.Role.GUEST);

		User savedUser = userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(token);
		authResponse.setMessage("signup success");

		return authResponse;
	}

	@PostMapping("/signin")
	public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(token);
		authResponse.setMessage("login success");

		return authResponse;

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetail = customUserDetailService.loadUserByUsername(username);

		if (userDetail == null) {
			throw new BadCredentialsException("User not found");
		}
		if (!passwordEncoder.matches(password, userDetail.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}

		return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
	}

}
