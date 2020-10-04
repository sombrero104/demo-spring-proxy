package me.sombrero.demospringproxy.sample;

import me.sombrero.demospringproxy.sample.Book;
import me.sombrero.demospringproxy.sample.BookService;
import me.sombrero.demospringproxy.sample.BookServiceProxy;
import me.sombrero.demospringproxy.sample.DefaultBookService;
import org.junit.Test;

public class BookServiceTest {

    BookService bookService = new BookServiceProxy(new DefaultBookService());

    @Test
    public void proxy_test() {
        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
        bookService.returnBook(book);
    }

}