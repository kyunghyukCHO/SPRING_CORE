package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
//    DIP, OCP 위반 발생 !! -> 구체(구현) 클래스에도 의존 ,
//    지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다! 따라서 OCP를 위반
//    해결방안 : 이 문제를 해결하려면 누군가가 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를 대신 생성하고 주입해주어야 한다.


    // Final 의 이유 ??
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    // 수정자 주입 --- 생성자 의존관계 주입 이후 2차로 수정자 주입이 진행됩니다.
    // 수정자 의존관계 주입이 있다면 생성자는 필요하지 않습니다.
    // 선택,변경 가능성이 있는 의존관계에 사용합니다.

//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired(required = false) // 주입할 대상이 없어도 동작하게 하기 위해 required 문을 작성하면 됩니다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    // 필드주입
    // 간결하고 좋지만 권장하지 않습니다. 왜냐하면 외부에서 변경이 불가능하므로 테스트가 불가능 합니다.
    // 외부 수정을 윙해 setter getter 를 필요로 합니다.
//    @Autowired private final MemberRepository memberRepository;=
//    @Autowired private final DiscountPolicy discountPolicy;

    // 매서드주입
    // 한번에 여러 필드를 주입 받을 수 있습니다 하지만, 마찬가지로 일반적으로 잘 사용하지 않습니다.
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }


    // ######################################################################################
    // 의존관계 자동 주입은 스프링 컨테이너가 관리하는 스프링 빈이어야 동작합니다.
    // 스프링 빈이 아닌 Member 같은 클래스에서 @Autowired 코드를 적용해도 아무 기능도 동작하지 않습니다.
    // ######################################################################################
    @Autowired
    // 생성자가 오직 1개라면 @Autowired 를 생략할 수 있다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // Configuration 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
