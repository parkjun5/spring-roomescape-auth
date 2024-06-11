package roomescape.apply.member.application;

import org.springframework.stereotype.Service;
import roomescape.apply.member.domain.Member;
import roomescape.apply.member.domain.repository.MemberRepository;
import roomescape.apply.member.ui.dto.MemberResponse;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberFinder {

    private final MemberRepository memberRepository;

    public MemberFinder(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findByEmailAndPassword(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(NoSuchElementException::new);
        return MemberResponse.from(member);
    }

    public boolean isDuplicateEmail(String email) {
        return memberRepository.countByEmail(email) != 0;
    }
}
