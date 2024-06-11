package roomescape.apply.member.ui.dto;

public record MemberRequest(
        String name,
        String email,
        String password
) {
}
