package com.example.shoppingmall.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {
    private String memberName;
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;
}
