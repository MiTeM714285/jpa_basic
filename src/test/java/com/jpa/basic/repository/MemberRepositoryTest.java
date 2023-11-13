package com.jpa.basic.repository;

import com.jpa.basic.domain.Member;
import com.jpa.basic.domain.type.MemberGender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // JPA는 반드시 트랜잭션이 필요.
@Commit // Test쪽에는 자동 롤백됨. 그러므로 @Commit 어노테이션 사용.
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setName("홍길동");
        member.setGender(MemberGender.MALE);
        member.setPassword("1234");
        member.setEmail("test@naver.com");
        member.setAge(34);
        // member.setId(3L); ID를 직접 넣기는 불가능

        memberRepository.save(member);
    }

    @Test
    void findOne() {
        Member foundMember = memberRepository.findOne(1L);
        foundMember.setEmail("asdf@naver.com");
    }

    @Test
    void findAll() {
        List<Member> memberList = memberRepository.findAll();
        memberList.forEach(System.out::println);
        memberList.get(0).setPassword("9999");
    }

    @Test
    void delete() {
        Member foundMember = memberRepository.findOne(1L);
        memberRepository.delete(foundMember);
    }

}