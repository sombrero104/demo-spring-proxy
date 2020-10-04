package me.sombrero.demospringproxy.dynamic;

public class DefaultBookService implements BookService {

    BookRepository bookRepository;

    public void rent(Book book) {
        System.out.println("rent: " + book.getTitle());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("return: " + book.getTitle());
    }

}
