package roomescape.apply.member.domain;

public class MemberRole {

    private Long id;
    private MemberRoleName memberRoleName;
    private String email;

    protected MemberRole() {

    }

    public static MemberRole of(String roleName, String email) {
        MemberRole member = new MemberRole();
        member.memberRoleName = MemberRoleName.findRoleByValue(roleName);
        member.email = email;
        return member;
    }

    public void changeId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public MemberRoleName getMemberRoleName() {
        return memberRoleName;
    }

    public String getEmail() {
        return email;
    }
}
