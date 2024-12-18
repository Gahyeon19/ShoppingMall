package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.member.MemberCreateDto;
import com.example.shoppingmall.dto.member.MemberInquiryDto;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.entity.MemberStatus;
import com.example.shoppingmall.exception.NotUniqueUserIdException;
import com.example.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInquiryDto getOneMember(String userId) {                          // 회원 한명 조회
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isPresent()) {
            Member member = byUserId.get();
            return MemberInquiryDto.of(member);
        }
        return null;
    }

    public List<MemberInquiryDto> getAllMembers() {                              // 모든 회원 조회
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(m -> new MemberInquiryDto(
                        m.getMemberId(),
                        m.getMemberName(),
                        m.getUserId()))
                .collect(Collectors.toList());
    }

    public MemberInquiryDto addMember(MemberCreateDto memberDto) {                 // 회원가입
        if (checkUniqueUserId(memberDto.getUserId())) {
            Member member = new Member(
                    0,
                    memberDto.getMemberName(),
                    memberDto.getUserId(),
                    memberDto.getPassword(),
                    memberDto.getEmail(),
                    memberDto.getPhone(),
                    memberDto.getAddress(),
                    MemberStatus.A,
                    LocalDate.now(),
                    null);
            Member save = memberRepository.save(member);
            return MemberInquiryDto.of(save);
        }
        return null;
    }

    boolean checkUniqueUserId(String userId) {                                  // 회원ID 중복확인
        Optional<Member> findUser = memberRepository.findByUserId(userId);
        if (findUser.isPresent()) {
            throw new NotUniqueUserIdException("동일한 ID가 존재합니다.");
        }
        return true;
    }
}
