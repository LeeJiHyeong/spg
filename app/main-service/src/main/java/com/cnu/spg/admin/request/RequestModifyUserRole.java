package com.cnu.spg.admin.request;

import lombok.Data;

import java.util.Set;

@Data
public class RequestModifyUserRole {
    private String id;
    private String username;
    private Set<String> roles;
}
