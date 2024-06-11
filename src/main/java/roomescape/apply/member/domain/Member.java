package roomescape.apply.member.domain;

public class Member {

    private String name;
    private String email;
    private String password;

    protected Member() {

    }

    public static Member of(String name, String email, String password) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.password = password;
        return member;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
