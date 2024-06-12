package roomescape.apply.member.domain;

import java.util.Set;
import java.util.stream.Collectors;

public class MemberRoleNames {

    private static final String DELIMITER = ",";
    private final Set<MemberRoleName> roleNames;

    private MemberRoleNames(Set<MemberRoleName> roleNames) {
        this.roleNames = roleNames;
    }

    public static MemberRoleNames of(Set<MemberRoleName> rolesInMember) {
        return new MemberRoleNames(rolesInMember);
    }

    public String getJoinedNames() {
        Set<String> nameValues = this.roleNames.stream().map(MemberRoleName::getValue).collect(Collectors.toSet());
        return String.join(DELIMITER, nameValues);
    }

}
