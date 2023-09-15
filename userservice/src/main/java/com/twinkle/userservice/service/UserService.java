package com.twinkle.userservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.twinkle.userservice.data.User;
import com.twinkle.userservice.data.UserRepository;
import com.twinkle.userservice.model.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private PasswordEncoder passwordEncoder; // mã hóa password
	
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	
	public UserDTO saveUser(UserDTO userDTO) {
		
		try {
			User userDB = userRepository.findByUsername(userDTO.getUsername());
			if(userDB != null)  throw new Exception("Tên tài khoản đã tồn tại!");
			// mã hóa password ms lưu vào database
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			
			return UserDTO.entityToDTO(userRepository.save(UserDTO.dtoToEntity(userDTO)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public UserDTO login(String username, String password) {
		User user = userRepository.findByUsername(username);
		UserDTO dto = new UserDTO();
		if(user !=null) {
			BeanUtils.copyProperties(user, dto);
			// Kiểm tra password user nhập có giống password trong db hay k?
			if(passwordEncoder.matches(password, dto.getPassword())) {
				
				// Tạo chuỗi token (kiểu mã hóa HMAC256)
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				
				String accessToken = JWT.create()
						.withSubject(user.getUsername()) // mã hóa payload data
						// set token hết hạn khoảng 8 phút
						.withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000))) 
						.sign(algorithm); // mã hóa chữ ký
				
				String refreshtoken = JWT.create()
						.withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
						.sign(algorithm);
				
				dto.setToken(accessToken);
				dto.setRefreshtoken(refreshtoken);
			}
		}
		return dto;
	}
	
	
}
