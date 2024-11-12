package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.member.MemberCreateDto;
import com.example.shoppingmall.dto.member.MemberInquiryDto;
import com.example.shoppingmall.entity.Member;
import com.example.shoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping
    public List<MemberInquiryDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{memberId}")
    public MemberInquiryDto getOneMember(@PathVariable("memberId") String memberId) {
        return memberService.getOneMember(memberId);
    }

    @PostMapping
    public MemberInquiryDto createNewMember(@RequestBody MemberCreateDto memberDto) {
        return memberService.addMember(memberDto);
    }

    //회원 가입
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("member", new Member());
        return "member/userForm";
    }

    @PostMapping("/add")
    public String addMember(@RequestBody MemberCreateDto memberDto) {
        memberService.addMember(memberDto);
        return "redirect:/members";
    }

    //로그인
    @GetMapping("/login")
    public String login() {
        return "member/loginForm";
    }

    //회원탈퇴


    //회원정보수정

}
