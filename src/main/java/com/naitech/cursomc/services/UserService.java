package com.naitech.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.naitech.cursomc.security.User;

public class UserService {

	public static User authenticated() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
