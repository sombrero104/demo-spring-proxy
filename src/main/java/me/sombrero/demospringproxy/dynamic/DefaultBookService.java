package me.sombrero.demospringproxy.dynamic;

public class DefaultBookService implements BookService {

    BookRepository bookRepository;

    public DefaultBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void rent(Book book) {
        Book save = bookRepository.save(book);
        System.out.println("rent: " + save.getTitle());
    }

    @Override
    public void returnBook(Book book) {
        bookRepository.save(book);
    }

}
