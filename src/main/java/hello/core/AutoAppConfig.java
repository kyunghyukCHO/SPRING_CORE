package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/*
컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다. @Component 를 붙여주자.
@Configuration 이 컴포넌트 스캔의 대상이 된 이유도 @Configuration 소스코드를 열어보면 @Component 애노테이션이 붙어있기 때문이다.
@Autowired 를 이용해 자동 의존관계를 주입시킵니다.
 */

@Configuration
@ComponentScan(
        //basePackages = "hello.core", //  스캔할 패키지의 시작 위치를 지정한다. 디폴트의 경우 : hello.core 하위 프로젝트 스캔
        // 권장 : 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 둡니다.
        // 즉, AutoAppConfig 를 core 패키지에 두는게 좋습니다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) // 해당 방법은 단지 예제코드를 살리기 위해 쓴 것 뿐임!!
public class AutoAppConfig {

//    자동 빈 등록 vs 수동 빈 등록 --> 충돌!!!
//    하지만 수동 빈 등록이 우선권을 가져갑니다. 즉, 수동 빈이 자동 빈을 오버라이딩 해버립니다
//    물론 개발자가 의도적으로 이런 결과를 기대했다면, 자동 보다는 수동이 우선권을 가지는 것이 좋습니다. 하지만 현실은 개발자가 의도적으로 설정해서
//    이런 결과가 만들어지기 보다는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분 입니다. 이러한 경우 버그를 잡기 어려워지므로
//    최근에는 스트링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 기본 값을 바꾸었습니다.


    // 해당 코드가 존재한다면, MemoryMemberRepository 의 충돌이 발생합니다 !!
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }



}
