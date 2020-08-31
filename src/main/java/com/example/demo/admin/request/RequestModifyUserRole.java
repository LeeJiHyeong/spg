package com.example.demo.admin.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestModifyUserRole {
    private Long id;
    private String username;
    private List<String> roles;
}
