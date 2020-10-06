package me.sombrero.demospringproxy.dynamic;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
public class Book {

    @Id @GeneratedValue
    private Integer id;

    private String title;

    /**
     * @~Many 는 기본 패칭 전략이 lazy initialzation.
     * ex) Book 조회 시 notes 까지 같이 가져오진 않는다.
     *     Book 데이터를 읽은 다음 notes 는 가짜 객체인 프록시 만들어짐.
     *     프록시 객체를 가지고 있다가 실제로 notes 에 접근할 일이 발생하면 그때 쿼리가 발생.
     */
    @OneToMany
    private List<Note> notes;

}
