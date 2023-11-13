package com.jpa.basic.domain;

import com.jpa.basic.domain.type.MemberGender;
import com.jpa.basic.domain.type.ReplyStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // JPA는 반드시 트랜잭션이 필요.
@Commit // Test쪽에는 자동 롤백됨. 그러므로 @Commit 어노테이션 사용.
class ReplyTest {

    @PersistenceContext // Autowired가 아닌 PersistenceContext라는 전용 어노테이션 사용
    EntityManager entityManager; // EntityManager는 JPA의 대부분의 CRUD 기능을 메소드로 가지고 있는 객체.
    // EntityManager는 EntityManagerFactory를 통해서 가져오기
    // 스프링부트에서 JPA를 사용하는 경우 EntityManagerFactory를 Bean으로 등록하여 가지므로 EntityManager를 스프링 컨테이너에 주입받을 수 있다
    @Test
    void insert() {
        Reply reply = new Reply();
        reply.setContent("내용1");
        reply.setWriter("작성자1");
        reply.setStatus(ReplyStatus.PUBLIC);

        entityManager.persist(reply);
    }

    @Test
    void select() {
        Reply foundReply = entityManager.find(Reply.class, 5L);
        log.info("result : {}", foundReply);
    }

    @Test
    void update() {
        Reply foundReply = entityManager.find(Reply.class, 5L);
        foundReply.setContent("내용1 수정테스트"); // update 쿼리 실행
    }

    @Test
    void delete() {
        entityManager.remove(entityManager.find(Reply.class, 5L)); // delete 쿼리 실행
    }

    @Test
    void selectAll() {
        // getResultList : 여러 행 조회
        // getSingleResult : 단일 행 조회
        // createQuery로 JPQL을 SQL로 변환하여 getResultList를 통해 SQL을 실행하여 List를 반환.
        // sql이 아닌 jpql로, from절 뒤에 별칭 필수. 전체 조회 시 * 이 아닌 별칭을 사용, 특정컬럼 조회시 r.content 와 같은 방식으로 지정
        // JPQL은 테이블을 알 수 없으므로 from Reply는 Entity를 의미, Entity의 이름은 별도로 설정하지 않을 시 클래스 이름으로 자동 설정
        List<Reply> replyList =  entityManager.createQuery("select r from Reply r", Reply.class).getResultList(); // Reply.class -> 결과의 타입을 결정
    }
}