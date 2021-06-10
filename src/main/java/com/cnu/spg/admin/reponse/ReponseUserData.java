package com.cnu.spg.admin.reponse;

import com.cnu.spg.login.domain.Role;
import com.cnu.spg.login.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReponseUserData {
    private Long id;
    private String username;
    private String name;
    private Set<Role> roles;

    public ReponseUserData(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.name = user.getName();
        this.roles = user.getRoles();
    }
}
