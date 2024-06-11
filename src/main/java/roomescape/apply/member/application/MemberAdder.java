package roomescape.apply.member.application;

import org.springframework.stereotype.Service;
import roomescape.apply.member.domain.Member;
import roomescape.apply.member.domain.repository.MemberRepository;
import roomescape.apply.member.ui.dto.MemberRequest;
import roomescape.apply.member.ui.dto.MemberResponse;
import roomescape.apply.reservation.application.excpetion.DuplicateReservationException;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.reservation.ui.dto.ReservationResponse;
import roomescape.apply.reservationtime.application.ReservationTimeFinder;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.theme.application.ThemeFinder;
import roomescape.apply.theme.domain.Theme;

@Service
public class MemberAdder {

    private final MemberRepository memberRepository;
    private final MemberFinder memberFinder;

    public MemberAdder(MemberRepository memberRepository, MemberFinder memberFinder) {
        this.memberRepository = memberRepository;
        this.memberFinder = memberFinder;
    }

    public MemberResponse addNewMember(MemberRequest request) {
        boolean isDuplicated = memberFinder.isDuplicateEmail(request.email());
        if (isDuplicated) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        Member member = Member.of(request.name(), request.email(), request.password());
        Member saved = memberRepository.save(member);
        return MemberResponse.from(saved);
    }

}
