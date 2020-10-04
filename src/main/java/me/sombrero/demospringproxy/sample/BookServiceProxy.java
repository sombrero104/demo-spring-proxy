package me.sombrero.demospringproxy.sample;

public class BookServiceProxy implements BookService {

    BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(Book book) {
        System.out.println("aaaaa");
        bookService.rent(book);
        System.out.println("bbbbb");

        // System.out.println("rent: hibernate");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("aaaaa"); // 중복해서 같은 코드를 또 써야한다.
        bookService.returnBook(book);
        System.out.println("bbbbb");

        /**
         * 위 처럼 중복해서 같은 코드를 추가하지 않고(매번 프록시에 해당하는 클래스를 만들지 않고)
         * 동적으로 런타임에 생성해내는 방법을 다이나믹 프록시라고 부르며
         * 자바 리플렉션에서 제공하는 기능이 있다.
         */
    }

}
