package com.jpa.basic.domain;

import com.jpa.basic.domain.type.MemberGender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Slf4j
@Transactional // JPA는 반드시 트랜잭션이 필요.
@Commit // Test쪽에는 자동 롤백됨. 그러므로 @Commit 어노테이션 사용.
class MemberTest {

    @PersistenceContext // Autowired가 아닌 PersistenceContext라는 전용 어노테이션 사용
    EntityManager entityManager; // EntityManager는 JPA의 대부분의 CRUD 기능을 메소드로 가지고 있는 객체.
    // EntityManager는 EntityManagerFactory를 통해서 가져오기
    // 스프링부트에서 JPA를 사용하는 경우 EntityManagerFactory를 Bean으로 등록하여 가지므로 EntityManager를 스프링 컨테이너에 주입받을 수 있다

    @Test
    void memberTest() {
        Member member = new Member();
        member.setAge(22);
        member.setEmail("aaa@naver.com");
        member.setGender(MemberGender.MALE);
        member.setPassword("1234");
        member.setName("김철수");

        entityManager.persist(member); // member객체를 entityManager에 넘겨줌
        // entityManager는 Entity를 넘겨받으면 영속성 컨텍스트의 내부의 1차 캐시에 저장.
        // 이후 entityManager는 쿼리 생성, 쿼리를 생성하면 영속성 컨텍스트 내부의 쓰기 지연 SQL 저장소에 SQL를 저장.
        // entityManager에 Entity를 넘겨주고 insert쿼리를 생성하는 메소드가 persist()
        // 바로 DB에 저장하지는 않으며 영속성 컨텍스트에 저장한것으로, 쿼리를 적용하기 위해 flush가 필요.
        // flush후, DB에 쿼리를 반영하고, commit 하여야 반영 내용을 확정짓는다. (Transactional, Commit(내부에 flush 포함) 어노테이션 사용)
    }

    @Test
    void insertAndSelect() {
        // Entity객체가 있어도 영속 컨텍스트에 등록되지 않은 비영속 상태
        Member member = new Member();
        member.setAge(22);
        member.setEmail("aaa@naver.com");
        member.setGender(MemberGender.MALE);
        member.setPassword("1234");
        member.setName("김철수");

        // 영속성 컨텍스트에 저장했으므로 영속상태로 전환
        entityManager.persist(member);
        
        // EntityManager를 이용하여 조회하는 find()메소드
        // find를 통해 entity클래스와 pk를 알려주면 해당 엔티티와 매핑된 테이블에서 pk를 이용하여 조회함.
        Member member1 = entityManager.find(Member.class, 1L);
        // select쿼리는 commit을 하기 전에는 DB에 저장되지 않은 상태이므로 select쿼리가 보이지 않음.
        // 현재 Entity는 1차 캐시에 존재하며, 엔티티매니저는 find를 할 시 바로 DB를 조회하는것이 아닌, 우선 영속성 컨텍스트에 존재하는지 확인을 한 후 없을시 DB조회.
        log.info("result : {}", member1); // gradle의 testAnnotationProcessor 'org.projectlombok:lombok' 필요
        log.info("equalstatus : {}", member == member1);
    }

    @Test
    void insertAndSelect2() {
        Member member = new Member();
        member.setAge(22);
        member.setEmail("aaa@naver.com");
        member.setGender(MemberGender.MALE);
        member.setPassword("1234");
        member.setName("김철수");
        entityManager.persist(member);
        // select쿼리를 보기위해서는 persist()로 영속성 컨텍스트에 보낸 Entity와 Insert쿼리를 실행하여 DB에 반영하면 된다
        // 즉, 강제로 flush 필요 (flush가 영속성 컨텍스트를 비워주지 않는다)
        entityManager.flush(); // 그래도 1차 캐시에 존재함. select쿼리는 보이지 않으며 Entity는 여전히 영속성 컨텍스트에 남아있기 때문
        //entityManager.clear(); // 영속성 컨텍스트에 등록된 엔티티를 전부 비우기
        entityManager.detach(member); // 특정한 Entity만 비우기. 저장되었다가 분리된 Entity의 준영속 상태로 전환

        Member member1 = entityManager.find(Member.class, 1L);
        log.info("result : {}", member1);
        log.info("equalstatus : {}", member == member1);
    }

    @Test
    void update() {
        Member member = entityManager.find(Member.class, 1L);
        member.setName("홍길동"); // update 쿼리 실행
    }

    @Test
    void delete() {
        Member member = entityManager.find(Member.class, 1L);
        entityManager.remove(member); // delete 쿼리 실행
    }

    @Test
    void merge() {
        Member foundMember = entityManager.find(Member.class, 3L); // 영속상태
        entityManager.detach(foundMember); // 준영속 상태로 전환
        
        foundMember.setName("홍길동");
        Member mergedMember = entityManager.merge(foundMember); // 영속 상태로 전환

        mergedMember.setName("김영희"); // 최종적으로 바뀌어야 할 값만 update문이 이뤄짐
    }
}