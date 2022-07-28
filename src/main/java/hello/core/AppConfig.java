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

@Configuration
public class AppConfig {

    @Bean
    // 생성자 주입
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() { // 인터페이스 참조.. 자식 구현체 참조는 how ??
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}


// 정적인 다이어그램을 손대지 않는다 : 애플리케이션 코드를 손대지 않는다 -> 의존관계를 바꾸찌 않음
// 동적 다이어그을 쉽게 바꿀 수 있음
