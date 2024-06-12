package roomescape.apply.member.ui.dto;

import roomescape.support.checker.MemberRequestValidator;

public record MemberRequest(
        String name,
        String email,
        String password
) {
    public MemberRequest {
        MemberRequestValidator.validateValues(name, email, password);
    }
}
