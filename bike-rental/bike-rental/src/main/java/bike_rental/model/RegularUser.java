package bike_rental.model;

public class RegularUser extends Person {

    private String membershipType;

    public RegularUser(String userId, String name, String email,
                       String password, String membershipType) {
        super(userId, name, email, password);
        this.membershipType = membershipType;
    }

    public RegularUser() {
        super();
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    @Override
    public String getRole() {
        return "regular";
    }

    public String getDisplayInfo(boolean showMembership) {
        if (showMembership) {
            // super.getDisplayInfo() calls Person's version first
            // then adds the membership type at the end
            return super.getDisplayInfo() + " | Membership: " + membershipType;
        } else {
            // just returns Person's version without membership info
            return super.getDisplayInfo();
        }
    }
}