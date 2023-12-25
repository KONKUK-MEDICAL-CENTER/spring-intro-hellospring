package hello.hellospring.service;

import hello.hellospring.domain.Member;

import static org.assertj.core.api.Assertions.*;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("홍길동");

        //when
        Long joinedId = memberService.join(member);

        //then
        Member finedMember = memberService.findOne(joinedId).orElse(null);

        assert finedMember != null;
        assertThat(member.getName()).isEqualTo(finedMember.getName());
    }

    @Test
    public void 중복회원예외처리검증() {
        //given
        Member member1 = new Member();
        member1.setName("김태희");

        Member member2 = new Member();
        member2.setName("김태희");

        //when
        memberService.join(member1);

        //then
        IllegalStateException e = Assertions.assertThrows(
            IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 전체회원조회() {
        //given
        Member member1 = new Member();
        member1.setName("김태희");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("홍길동");
        memberService.join(member2);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void ID로회원조회() {
        //given
        Member member1 = new Member();
        member1.setName("김태희");
        Long Id = memberService.join(member1);

        //when
        Member result = memberService.findOne(Id).orElse(null);

        //then
        assert result != null;
        assertThat(result.getName()).isEqualTo(member1.getName());
    }
}