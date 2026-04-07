public class AdminPanel {
    private UserService userService;
    private RentalService rentalService;

    public AdminPanel() {
        userService = new UserService();
        rentalService = new RentalService();
    }

    // 模拟添加新用户并启动租车流程
    public void addNewUserAndStartRental() {
        // 假设从界面获取的输入
        String fullName = "John Doe";
        String email = "john@example.com";
        String dob = "1990-01-01";
        long cardNum = 1234567812345678L;
        String expiry = "12/28";
        String provider = "Visa";
        int cvv = 123;
        String userType = "VIP";   // 或 "Regular"
        String[] trips = new String[3];

        // 调用 UserService 创建用户（多态对象）
        RegisteredUsers newUser = userService.addNewUsers(fullName, email, dob,
                cardNum, expiry, provider, cvv, userType, trips);

        // 将用户对象传递给 RentalService 进行模拟
        rentalService.simulateApplicationInput(newUser);
    }
}
