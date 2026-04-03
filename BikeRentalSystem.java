import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// ======================= POJO: ERyderLogs =======================
class ERyderLogs {
    private static int counter = 1;          // 用于生成唯一日志ID
    private String logId;
    private String eventDescription;
    private LocalDateTime timestamp;

    public ERyderLogs(String eventDescription) {
        this.logId = "LOG" + String.format("%03d", counter++);
        this.eventDescription = eventDescription;
        this.timestamp = LocalDateTime.now();
    }

    // 按指定格式输出：ID - Event description - timestamp
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return logId + " - " + eventDescription + " - " + timestamp.format(formatter);
    }
}

// ======================= POJO: BikeRequest =======================
class BikeRequest {
    private String userEmail;
    private String location;
    private LocalDateTime requestTime;

    public BikeRequest(String userEmail, String location) {
        this.userEmail = userEmail;
        this.location = location;
        this.requestTime = LocalDateTime.now();
    }

    // Getters (按要求提供)
    public String getUserEmail() { return userEmail; }
    public String getLocation() { return location; }
    public LocalDateTime getRequestTime() { return requestTime; }

    // 显示用户邮箱、位置和请求时间
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "User: " + userEmail + ", Location: " + location +
               ", Request Time: " + requestTime.format(formatter);
    }
}

// ======================= BikeService 核心业务类 =======================
class BikeService {
    // 自行车状态
    private boolean bikeAvailable;          // true = 可租，false = 已租出
    private String currentBikeId;           // 固定自行车ID（示例使用 B001）
    private String currentLocation;         // 当前租车所在位置
    private String currentUserEmail;        // 当前租用用户

    // 日志栈 和 请求队列
    private Stack<ERyderLogs> logsStack;
    private Queue<BikeRequest> bikeRequestQueue;

    public BikeService() {
        bikeAvailable = true;
        currentBikeId = "B001";              // 模拟唯一自行车ID
        logsStack = new Stack<>();
        bikeRequestQueue = new ArrayDeque<>();
    }

    // 获取日志栈（供管理员查看）
    public Stack<ERyderLogs> getLogsStack() { return logsStack; }
    // 获取请求队列（供管理员查看）
    public Queue<BikeRequest> getBikeRequestQueue() { return bikeRequestQueue; }

    // 添加一条日志到栈中
    private void addLog(String eventDescription) {
        ERyderLogs log = new ERyderLogs(eventDescription);
        logsStack.push(log);
    }

    // 核心方法：用户请求租车
    public void reserveBike(String userEmail, String location) {
        if (bikeAvailable) {
            // 自行车可用 -> 成功租车
            bikeAvailable = false;
            currentUserEmail = userEmail;
            currentLocation = location;

            // 记录“租车成功”事件
            addLog("Bike with " + currentBikeId + " was rented from " + location);
            // 记录“旅程开始”事件
            addLog("Trip started for bike " + currentBikeId + " at location " + location);

            System.out.println("[System] Bike reserved successfully by " + userEmail +
                               " at " + location);
        } else {
            // 自行车不可用 -> 将请求加入队列
            BikeRequest request = new BikeRequest(userEmail, location);
            bikeRequestQueue.offer(request);   // Queue 方法 offer()
            System.out.println("[System] Bike unavailable. Request from " + userEmail +
                               " added to queue. Queue size: " + bikeRequestQueue.size());
        }
    }

    // 结束当前旅程（当用户归还自行车时调用）
    public void removeTrip() {
        if (!bikeAvailable) {
            // 记录“旅程结束”事件
            addLog("Trip ended for bike " + currentBikeId + " at location " + currentLocation);

            // 释放自行车
            bikeAvailable = true;
            System.out.println("[System] Trip ended. Bike " + currentBikeId +
                               " is now available at " + currentLocation);

            // 清除当前租用信息
            currentUserEmail = null;
            currentLocation = null;

            // 检查等待队列，如果有请求则自动分配给下一个用户
            if (!bikeRequestQueue.isEmpty()) {
                BikeRequest nextRequest = bikeRequestQueue.poll();  // Queue 方法 poll()
                assignNextRequest(nextRequest);
            }
        } else {
            System.out.println("[System] No active trip to end.");
        }
    }

    // 将队列中的下一个请求分配给刚释放的自行车
    private void assignNextRequest(BikeRequest request) {
        // 此时 bikeAvailable 一定为 true
        bikeAvailable = false;
        currentUserEmail = request.getUserEmail();
        currentLocation = request.getLocation();

        // 记录租车成功和旅程开始日志
        addLog("Bike with " + currentBikeId + " was rented from " + currentLocation);
        addLog("Trip started for bike " + currentBikeId + " at location " + currentLocation);

        System.out.println("[System] Assigned bike to next user: " + currentUserEmail +
                           " at " + currentLocation);
    }
}

// ======================= 管理员面板 =======================
class AdminPanel {
    private Scanner scanner;

    public AdminPanel() {
        scanner = new Scanner(System.in);
    }

    // 显示管理员主菜单
    public void showMenu(BikeService bikeService) {
        while (true) {
            System.out.println("\n===== Admin Panel =====");
            System.out.println("1. View System Logs");
            System.out.println("2. Manage Pending Bike Requests");
            System.out.println("3. Exit Admin Panel");
            System.out.print("Choose an option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewSystemLogs(bikeService.getLogsStack());
                    break;
                case 2:
                    managePendingRequests(bikeService.getBikeRequestQueue());
                    break;
                case 3:
                    System.out.println("Exiting Admin Panel.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // 方法：遍历栈并显示所有日志
    private void viewSystemLogs(Stack<ERyderLogs> logsStack) {
        if (logsStack.isEmpty()) {
            System.out.println("No logs available.");
            return;
        }
        System.out.println("\n----- System Logs -----");
        // 注意：栈是 LIFO，遍历时从栈底到栈顶（不弹出）
        for (ERyderLogs log : logsStack) {
            System.out.println(log);
        }
    }

    // 管理请求队列的子菜单
    private void managePendingRequests(Queue<BikeRequest> queue) {
        while (true) {
            System.out.println("\n--- Manage Pending Bike Requests ---");
            System.out.println("1. View Queue");
            System.out.println("2. Update Queue (remove first element)");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    viewQueue(queue);
                    break;
                case 2:
                    updateQueue(queue);
                    break;
                case 3:
                    System.out.println("Returning to main admin menu.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // 查看队列中的所有请求（只读，不改变队列）
    private void viewQueue(Queue<BikeRequest> queue) {
        if (queue.isEmpty()) {
            System.out.println("The pending requests queue is empty.");
            return;
        }
        System.out.println("\n----- Pending Bike Requests -----");
        int index = 1;
        for (BikeRequest req : queue) {
            System.out.println(index++ + ". " + req);
        }
    }

    // 更新队列：移除第一个元素（使用 Queue 的 poll() 方法）
    private void updateQueue(Queue<BikeRequest> queue) {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty. Nothing to remove.");
            return;
        }
        BikeRequest removed = queue.poll();   // Queue 特定方法
        System.out.println("Removed first request: " + removed);
    }

    // 安全读取整数输入
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}

// ======================= 主程序（演示所有功能）=======================
public class BikeRentalSystem {
    public static void main(String[] args) {
        BikeService service = new BikeService();
        AdminPanel admin = new AdminPanel();

        // 模拟用户操作流程
        System.out.println("========== 模拟租车流程 ==========");

        // 1. 用户A成功租车（自行车可用）
        System.out.println("\n[User A] Requesting bike at 'Central Park'...");
        service.reserveBike("alice@example.com", "Central Park");

        // 2. 用户B请求租车，但自行车已被租出 -> 进入队列
        System.out.println("\n[User B] Requesting bike at 'Downtown'...");
        service.reserveBike("bob@example.com", "Downtown");

        // 3. 用户C请求租车 -> 进入队列
        System.out.println("\n[User C] Requesting bike at 'Uptown'...");
        service.reserveBike("charlie@example.com", "Uptown");

        // 4. 管理员查看系统日志和队列
        System.out.println("\n---------- 管理员操作 1 ----------");
        admin.showMenu(service);   // 管理员可在此查看日志和队列

        // 5. 用户A结束旅程 -> 自动分配下一个请求给用户B
        System.out.println("\n[User A] Ending trip...");
        service.removeTrip();

        // 6. 管理员再次查看日志和队列（验证用户B已自动租车）
        System.out.println("\n---------- 管理员操作 2 ----------");
        admin.showMenu(service);

        // 7. 用户B结束旅程 -> 自动分配用户C
        System.out.println("\n[User B] Ending trip...");
        service.removeTrip();

        // 8. 最终查看队列应为空
        System.out.println("\n---------- 最终队列状态 ----------");
        System.out.println("Queue empty? " + service.getBikeRequestQueue().isEmpty());
        System.out.println("\n---------- 最终所有日志 ----------");
        for (ERyderLogs log : service.getLogsStack()) {
            System.out.println(log);
        }
    }
}
