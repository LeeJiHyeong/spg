package com.example.demo.admin.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModifyUserRole {
    private Long id;
    private String username;
    private String roleStr;
}
