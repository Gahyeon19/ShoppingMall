package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.MemberCreateDto;
import com.example.shoppingmall.dto.MemberInquiryDto;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.entity.MemberStatus;
import com.example.shoppingmall.exception.NotUniqueUserIdException;
import com.example.shoppingmall.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 전체회원정보조회() {
        // Given
        Member member = new Member(0, "test1", "user1", "1111", "user@naver.com", "01011111111",
                "오리역", MemberStatus.A, LocalDate.now(), null);
        memberRepository.save(member);

        // When
        List<MemberInquiryDto> allMembers = memberService.getAllMembers();

        // Then
        org.assertj.core.api.Assertions.assertThat(allMembers.size()).isEqualTo(1);

    }

    @Test
    public void 회원등록테스트() {
        //Given
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "test1",
                "user1",
                "1111",
                "user@naver.com",
                "010111111",
                "오리역"
        );
        //when
        memberService.addMember(memberCreateDto);
        org.assertj.core.api.Assertions.assertThat(memberService.getAllMembers().size()).isEqualTo(1);
        //then
        assertThrows(NotUniqueUserIdException.class, () -> {
            memberService.addMember(memberCreateDto);
        });
    }
}