package com.example.demo.security;

import java.util.Set;

import com.google.common.collect.Sets;
import static com.example.demo.security.ApplicationUserPermission.*;
public enum ApplicationUserRole {
	
	// Student Access
	STUDENT(Sets.newHashSet()),
	// Admin Access
	ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),	
	ADMINTRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));
	
	
	private final Set<ApplicationUserPermission> permission;
	
	private ApplicationUserRole(Set<ApplicationUserPermission> permission) {
		this.permission = permission;				
	}

	public Set<ApplicationUserPermission> getPermission() {
		return permission;
	}
		
	
}
