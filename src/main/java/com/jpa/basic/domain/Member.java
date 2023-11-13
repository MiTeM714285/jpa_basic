package com.jpa.basic.domain;

import com.jpa.basic.domain.type.MemberGender;
import lombok.Data;

import javax.persistence.*;

@Entity // Entity로 등록한다. 테이블 이름은 Entity의 이름과 동일하게 만들어진다.
// Entity를 만드는 순간 오류가 나는 이유는 테이블을 만들 때 반드시 필요한 PK가 없기 때문.
// Entity는 기본생성자 필수
@Data
@Table(name = "JPA_MEMBER") // 테이블 이름을 별도로 설정 가능.
public class Member {

    @Id // 해당 필드를 PK로 지정.
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 자동 증갓값 지정
            // AUTO : 자동으로 Hibernate가 해당 DBMS에 맞게 설정해주며, 이 값이 디폴트
            // IDENTITY : AUTO_INCREMENT를 사용하는 MySQL 사용, IDENTITY를 사용하는 MS-SQL 사용시 사용
            // SEQUENCE : ORACLE 및 PostgreSQL 등 Sequence 객체 사용하는 기본키 생성 전략
    @Column(name = "MEMBER_ID") // 컬럼 속성 지정. name을 따로 지정한다.
    private Long id;
    private String name;
    @Column(unique = true) // 컬럼 속성 지정. 테이블 수정으로 unique를 지정.
    private String email;
    private String password;
    private Integer age;
    @Enumerated(EnumType.STRING) // ORDINAL이 아닌 STRING으로 지정
    private MemberGender gender; // Enum사용으로 컬럼 값 따로 지정

}
