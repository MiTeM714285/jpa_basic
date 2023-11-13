package com.jpa.basic.domain;

import com.jpa.basic.domain.type.ReplyStatus;
import lombok.Data;

import javax.persistence.*;

@Entity // Entity로 등록한다. 테이블 이름은 Entity의 이름과 동일하게 만들어진다.
// Entity를 만드는 순간 오류가 나는 이유는 테이블을 만들 때 반드시 필요한 PK가 없기 때문.
// Entity는 기본생성자 필수
@Data
@Table(name = "JPA_REPLY") // 테이블 이름을 별도로 설정 가능.
public class Reply {

    @Id // 해당 필드를 PK로 지정.
    @GeneratedValue // 자동 증갓값 지정
    @Column(name = "REPLY_ID") // 컬럼 속성 지정. name을 따로 지정한다.
    private Long replyId;
    private String content;
    private String writer;
    @Enumerated(EnumType.STRING) // ORDINAL이 아닌 STRING으로 지정
    private ReplyStatus status;

    // FK 지정
    @ManyToOne
    private Member member; // reply가 Many, member가 one
}
