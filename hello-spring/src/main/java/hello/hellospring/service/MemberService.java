package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     *
     * @param member - 가입할 회원정보 객체
     * @return - 회원가입 후 부여된 ID값
     */
    public Long join(Member member) {
        //같은 이름의 회원이 이미 있는 경우 회원가입 불가
        validateDuplicateMember(member);//중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     *
     * @return - 전체 회원 정보 객체들의 List
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * Id로 회원 조회
     *
     * @param Id - 회원정보 Id
     * @return
     */
    public Optional<Member> findOne(Long Id) {
        return memberRepository.findById(Id);
    }
}
