public class VIPUser extends RegisteredUsers {

    public VIPUser(String fullName, String emailAddress, String dateOfBirth,
                   long cardNumber, String cardExpiryDate, String cardProvider,
                   int cvv, String userType, String[] lastThreeTrips) {
        super(fullName, emailAddress, dateOfBirth, cardNumber, cardExpiryDate,
              cardProvider, cvv, userType, lastThreeTrips);
    }

    @Override
    public double calculateFare(double baseFare) {
        // VIP 用户享受 20% 折扣
        return baseFare * 0.8;
    }

    @Override
    public void displayUserType() {
        System.out.println("VIP User");
    }
}