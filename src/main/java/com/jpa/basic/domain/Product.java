package com.jpa.basic.domain;

import com.jpa.basic.domain.type.ProductStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity // Entity로 등록한다. 테이블 이름은 Entity의 이름과 동일하게 만들어진다.
// Entity를 만드는 순간 오류가 나는 이유는 테이블을 만들 때 반드시 필요한 PK가 없기 때문.
// Entity는 기본생성자 필수
@Table(name = "JPA_PRODUCT") // 테이블 이름을 별도로 설정 가능.
public class Product {

    @Id // 해당 필드를 PK로 지정.
    @GeneratedValue // 자동 증갓값 지정
    @Column(name = "ID") // 컬럼 속성 지정. name을 따로 지정한다.
    private Long id;
    private String brand;
    private String name;
    private Long price;
    @Enumerated(EnumType.STRING) // ORDINAL이 아닌 STRING으로 지정
    private ProductStatus status;
    private LocalDate releaseDate; // (HH:MM)까지 필요할시 LocalDateTime으로

}
