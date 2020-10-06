package me.sombrero.demospringproxy.dynamic;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


    /**
     * 이 프로젝트 주제인 Proxy와는 관련이 없지만..
     * Lombok을 사용하여 빌드 후 클래스 파일을 열어보면..
     * 아래와 같이 컴파일이 된 것을 확인할 수 있다.
     *
     *
     * //
     * // Source code recreated from a .class file by IntelliJ IDEA
     * // (powered by FernFlower decompiler)
     * //
     *
     * package me.sombrero.demospringproxy.dynamic;
     *
     * import java.util.List;
     * import javax.persistence.Entity;
     * import javax.persistence.GeneratedValue;
     * import javax.persistence.Id;
     * import javax.persistence.OneToMany;
     *
     * @Entity
     * public class Book {
     *     @Id
     *     @GeneratedValue
     *     private Integer id;
     *     private String title;
     *     @OneToMany
     *     private List<Note> notes;
     *
     *     public static Book.BookBuilder builder() {
     *         return new Book.BookBuilder();
     *     }
     *
     *     public Integer getId() {
     *         return this.id;
     *     }
     *
     *     public String getTitle() {
     *         return this.title;
     *     }
     *
     *     public List<Note> getNotes() {
     *         return this.notes;
     *     }
     *
     *     public void setId(final Integer id) {
     *         this.id = id;
     *     }
     *
     *     public void setTitle(final String title) {
     *         this.title = title;
     *     }
     *
     *     public void setNotes(final List<Note> notes) {
     *         this.notes = notes;
     *     }
     *
     *     public Book() {
     *     }
     *
     *     public Book(final Integer id, final String title, final List<Note> notes) {
     *         this.id = id;
     *         this.title = title;
     *         this.notes = notes;
     *     }
     *
     *     public static class BookBuilder {
     *         private Integer id;
     *         private String title;
     *         private List<Note> notes;
     *
     *         BookBuilder() {
     *         }
     *
     *         public Book.BookBuilder id(final Integer id) {
     *             this.id = id;
     *             return this;
     *         }
     *
     *         public Book.BookBuilder title(final String title) {
     *             this.title = title;
     *             return this;
     *         }
     *
     *         public Book.BookBuilder notes(final List<Note> notes) {
     *             this.notes = notes;
     *             return this;
     *         }
     *
     *         public Book build() {
     *             return new Book(this.id, this.title, this.notes);
     *         }
     *
     *         public String toString() {
     *             return "Book.BookBuilder(id=" + this.id + ", title=" + this.title + ", notes=" + this.notes + ")";
     *         }
     *     }
     * }
     */

}
