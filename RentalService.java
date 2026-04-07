public class RentalService {
    public static final double BASE_FARE = 3.0;   // 基础费率常量

    // 接收用户对象，模拟租车流程
    public void simulateApplicationInput(RegisteredUsers user) {
        // 假设用户进行了一次行程，行程结束后调用 removeTrip
        System.out.println("模拟租车服务开始，用户类型：");
        user.displayUserType();   // 多态调用

        // 模拟行程结束，计算费用
        removeTrip(user);
    }

    // 行程结束时计算费用
    private void removeTrip(RegisteredUsers user) {
        // 通过多态调用 calculateFare：VIP 有折扣，Regular 无折扣
        double fare = user.calculateFare(BASE_FARE);
        System.out.println("本次行程费用: $" + fare);
        // 其他结束行程的业务逻辑...
    }
}