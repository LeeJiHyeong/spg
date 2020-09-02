package com.example.demo.admin.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RequestModifyUserRole {
    private String id;
    private String username;
    private Set<String> roles;
    
    public RequestModifyUserRole() {
    	
    }
    
    public RequestModifyUserRole(String id, String username, Set<String> roles) {
    	this.id = id;
    	this.username = username;
    	this.roles = roles;
    }
    
    public String getId() {
    	return id;
    }
    
    public String getUserName() {
    	return username;
    }
}
