package com.example.demo.config;

import com.example.demo.entity.MyUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// create session
@Component
public class UserUtils {
	@Autowired
	private UserService userDAO;
	
	public MyUser getUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		MyUser user = userDAO.getByUsername(username);
		return user;
	}
	
	/**
	 * check if there is roleCode permission
	 * @param roleCode
	 * @return
	 */
	public Boolean hasRole(String roleCode) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> roleCodes=new ArrayList<>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roleCodes.add(authority.getAuthority());
		}
		return roleCodes.contains(roleCode);
	}
	
//	public String getName() {
//		if(hasRole("ROLE_ADMIN")) {
//			return "developer";
//		}
//		return getUser().getNickname();
//	}
}
