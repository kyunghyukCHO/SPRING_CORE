package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    // SOLID 위반!
    private final MemberRepository memberRepository;

    // appConfig 를 통해 생성자 주입 코드
    @Autowired // = ac.getBean(MemberRepository.class)
    // 자동 의존관계 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }



    // Configuration 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
