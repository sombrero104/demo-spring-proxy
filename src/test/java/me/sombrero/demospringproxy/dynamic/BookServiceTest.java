package me.sombrero.demospringproxy.dynamic;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 다이나믹 프록시
 * 런타임에(애플리케이션을 실행하는 도중에) 특정 인터페이스들을 구현하는 클래스 또는 인스턴스를 만드는 기술.
 * “an application can use a dynamic proxy class to create an object
 *  that implements multiple arbitrary event listener interfaces”
 *
 * 다이나믹 프록시 사용처
 * - 스프링 데이터 JPA
 * - 스프링 AOP
 * - Mockito
 * - 하이버네이트 lazy initialization
 * - ...
 */
public class BookServiceTest {

    /**
     * 1. 자바 프록시를 사용하는 방법.
     *
     * 이렇게 만들면 sample 패키지에서 처럼 프록시 클래스를 매번 만들어야 하는 수고는 덜어지지만
     * InvocationHandler 자체가 별로 유연하지가 않다.
     * 여러가지 부가기능을 적용해야 할 때 코드가 계속 커질수도 있고
     * 하나의 프록시를 감싸는 또 다른 프록시를 만들어야 하는 경우가 생길수도 있기 때문에 코드가 복잡해진다.
     * => 때문에 이 구조를 스프링이 정의한 인터페이스로 뜯어고친 스프링 AOP를 사용.
     * (그래서 스프링 AOP 프록 시 기반 AOP라고 부른다.)
     *
     * 또한 클래스인 DefaultBookService로는 사용 불가능.
     * 반드시 인터페이스로만 넘겨줘야 한다.
     */
    /*BookService bookService = (BookService) Proxy.newProxyInstance(
            BookService.class.getClassLoader(), new Class[]{BookService.class}
            , new InvocationHandler() {
                BookService bookService = new DefaultBookService();
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("rent".equals(method.getName())) {
                        System.out.println("aaaaa");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("bbbbb");
                        return invoke;
                    }
                        return method.invoke(bookService, args);
                }
            });

    @Test
    public void proxy_test() {
        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
        bookService.returnBook(book);
    }*/

    /**
     * 2. cglib을 사용하는 방법.
     * 위에서 사용한 자바 프록시와는 다르게 클래스로도 사용 가능한 방법.
     * 단, 상속을 이용하여 프록시를 만드는 방법이기 때문에
     * 상속을 제한한 클래스로는 이 방법을 쓸 수 없다는 단점이 있다.
     * 만약, 생성자가 private한 생성자 단 하나만 존재할 경우에도 사용이 불가능하다.
     * (상속을 막는 방법 중 하나이기 때문.. 상속을 하면 하위 클래스에서 항상 부모의 생성자를 호출하므로..)
     * 때문에 가능하면 인터페이스를 만들어서 프록시를 사용하는 것을 권장..
     */
    /*@Test
    public void cglib() {
        MethodInterceptor handler = new MethodInterceptor() {
            DefaultBookService defaultBookService = new DefaultBookService();
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if("rent".equals(method.getName())) {
                    System.out.println("aaaaa");
                    Object invoke = method.invoke(defaultBookService, objects);
                    System.out.println("bbbbb");
                    return invoke;
                }
                return method.invoke(defaultBookService, objects);
            }
        };
        DefaultBookService defaultBookService = (DefaultBookService) Enhancer.create(DefaultBookService.class, handler);

        Book book = new Book();
        book.setTitle("spring");
        defaultBookService.rent(book);
        defaultBookService.returnBook(book);
    }*/

    /**
     * 3. bytebuddy를 사용하는 방법.
     * 상속을 이용하여 프록시를 만드는 방법이기 때문에
     * 상속을 제한한 클래스로는 이 방법을 쓸 수 없다는 단점이 있다.
     * 만약, 생성자가 private한 생성자 단 하나만 존재할 경우에도 사용이 불가능하다.
     * (상속을 막는 방법 중 하나이기 때문.. 상속을 하면 하위 클래스에서는 항상 부모의 생성자를 호출하므로..)
     * 때문에 가능하면 인터페이스를 만들어서 프록시를 사용하는 것을 권장..
     */
    /*@Test
    public void bytebuddy() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends DefaultBookService> proxyClass = new ByteBuddy().subclass(DefaultBookService.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    DefaultBookService defaultBookService = new DefaultBookService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("aaaaa");
                        Object invoke = method.invoke(defaultBookService, args);
                        System.out.println("bbbbb");
                        return invoke;
                    }
                }))
                .make().load(DefaultBookService.class.getClassLoader()).getLoaded();
        DefaultBookService defaultBookService = proxyClass.getConstructor(null).newInstance();

        Book book = new Book();
        book.setTitle("spring");
        defaultBookService.rent(book);
        defaultBookService.returnBook(book);
    }*/

    /**
     * Mock(Mockito라는 테스트 라이브러리가 만들어주는 가짜 객체)은 다이나믹 프록시를 사용한다.
     * 아래는 Mock을 사용하는 방법.
     */
    @Test
    public void test_mock() {
        BookRepository bookRepositoryMock = mock(BookRepository.class);
        Book hibernateBook = new Book();
        hibernateBook.setTitle("Hibernate");
        when(bookRepositoryMock.save(any())).thenReturn(hibernateBook);
        DefaultBookService defaultBookService = new DefaultBookService(bookRepositoryMock);
        Book book = new Book();
        book.setTitle("spring");
        defaultBookService.rent(book);
        defaultBookService.returnBook(book);
    }

}