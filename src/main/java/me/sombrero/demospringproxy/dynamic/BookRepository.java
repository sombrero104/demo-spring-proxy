package me.sombrero.demospringproxy.dynamic;

public class BookRepository {

    public void save(Book book) {
        System.out.println("save a book!!");
    }

    public void findAll() {
        System.out.println("find all books!!");
    }

}
