package com.cnu.spg.domain.login;

import com.cnu.spg.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity { // date type extends 하기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NaturalId
    @Size(max = 10)
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Column
    @Size(max = 70)
    private String password;

    @Column
    private String name;

    @Column
    private Calendar activeDate;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    protected User(String name, String username, String password) {
        this.name = name;
        this.userName = username;
        this.password = password;
    }

    public static User createUser(String name, String username, String password, Role... roles) {
        User user = new User(name, username, password);
        Arrays.stream(roles).forEach(user::addRole);

        return user;
    }
}
