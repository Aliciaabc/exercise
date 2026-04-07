public class RegularUser extends RegisteredUsers {

    // 构造函数，参数与父类相同，使用 super 调用父类构造器
    public RegularUser(String fullName, String emailAddress, String dateOfBirth,
                       long cardNumber, String cardExpiryDate, String cardProvider,
                       int cvv, String userType, String[] lastThreeTrips) {
        super(fullName, emailAddress, dateOfBirth, cardNumber, cardExpiryDate,
              cardProvider, cvv, userType, lastThreeTrips);
    }

    @Override
    public double calculateFare(double baseFare) {
        // 复用父类逻辑，直接返回 baseFare
        return super.calculateFare(baseFare);
    }

    @Override
    public void displayUserType() {
        System.out.println("Regular User");
    }
}
