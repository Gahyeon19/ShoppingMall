package com.example.shoppingmall.dto.member;

import com.example.shoppingmall.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInquiryDto {
    private int memberId;
    private String memberName;
    private String userId;

    public static MemberInquiryDto of(Member member) {
        MemberInquiryDto dto = new MemberInquiryDto(
                member.getMemberId(),
                member.getMemberName(),
                member.getUserId());
        return dto;
    }
}
