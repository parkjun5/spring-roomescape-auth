package roomescape.apply.member.application;

import org.springframework.stereotype.Service;
import roomescape.apply.auth.application.PasswordHasher;
import roomescape.apply.member.domain.Member;
import roomescape.apply.member.domain.MemberRole;
import roomescape.apply.member.domain.repository.MemberRepository;
import roomescape.apply.member.ui.dto.CreatedMemberRequest;
import roomescape.apply.member.ui.dto.MemberResponse;

import java.util.stream.Collectors;

@Service
public class MemberAdder {

    private final MemberRepository memberRepository;
    private final PasswordHasher passwordHasher;
    private final MemberFinder memberFinder;
    private final MemberRoleSaver memberRoleSaver;

    public MemberAdder(MemberRepository memberRepository, PasswordHasher passwordHasher,
                       MemberFinder memberFinder, MemberRoleSaver memberRoleSaver) {
        this.memberRepository = memberRepository;
        this.passwordHasher = passwordHasher;
        this.memberFinder = memberFinder;
        this.memberRoleSaver = memberRoleSaver;
    }

    public MemberResponse addNewMember(CreatedMemberRequest request) {
        String email = request.email();
        boolean isDuplicated = memberFinder.isDuplicateEmail(email);
        if (isDuplicated) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        String hashPassword = passwordHasher.hashPassword(request.password());
        Member member = Member.of(request.name(), email, hashPassword);
        Member saved = memberRepository.save(member);

        var memberRoles = request.roleNamesWithDefaultValue()
                .stream()
                .map(it -> MemberRole.of(it , email))
                .collect(Collectors.toSet());
        memberRoleSaver.saveAll(memberRoles);

        return MemberResponse.from(saved);
    }

}
