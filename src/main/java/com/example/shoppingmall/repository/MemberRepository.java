package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String phone);
}
