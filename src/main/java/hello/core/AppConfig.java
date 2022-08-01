package hello.core;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 팩토리 메소드를 통해 스프링 빈을 등록하는 방법.

// @Bean memberService -> new MemoryMemberRepository()
// @Bean orderService -> new MemoryMemberRepository() -> 싱글톤 위반 ? NO !
// @Configuration

// 호출 로그를 붙였을 때,
// 스프링 컨테이너가 각각 @Bean을 호출해서 스프링 빈을 생성한다.
// 그래서 memberRepository() 는 다음과 같이 총 3번이 호출되어야 하는 것 아닐까? NO !
// 실제로 ConfigurationTest 를 돌려보면 모두 한번씩 호출된다!

// DIP 완성: MemberServiceImpl 은 MemberRepository 인 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다.

/*
기존 프로그램은 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성하고, 연결하고, 실행했다.
한마디로 구현 객체가 프로그램의 제어 흐름을 스스로 조종했다. 개발자 입장에서는 자연스러운 흐름이다.
반면에 AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다. 프로그램의 제어 흐름은 이제 AppConfig가 가져간다.
예를 들어서 OrderServiceImpl 은 필요한 인터페이스들을 호출하지만 어떤 구현 객체들이 실행될지 모른다.
프로그램에 대한 제어 흐름에 대한 권한은 모두 AppConfig가 가지고 있다.
심지어 OrderServiceImpl 도 AppConfig가 생성한다.
그리고 AppConfig는 OrderServiceImpl 이 아닌 OrderService 인터페이스의 다른 구현 객체를 생성하고 실행할 수 도 있다.
그런 사실도 모른체 OrderServiceImpl 은 묵묵히 자신의 로직을 실행할 뿐이다.
이렇듯 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다.
 */
@Configuration
public class AppConfig {

    @Bean
    // 생성자 주입
    public MemberService memberService() {
        System.out.println("Call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("Call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("Call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() { // 인터페이스 참조.. 자식 구현체 참조는 how ??
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}


// 정적인 다이어그램을 손대지 않는다 : 애플리케이션 코드를 손대지 않는다 -> 의존관계를 바꾸지 않음
// 동적 다이어그을 쉽게 바꿀 수 있음
