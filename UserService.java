import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<RegisteredUsers> registeredUsersList = new ArrayList<>();

    // 添加新用户，返回创建的用户对象（多态）
    public RegisteredUsers addNewUsers(String fullName, String emailAddress, String dateOfBirth,
                                       long cardNumber, String cardExpiryDate, String cardProvider,
                                       int cvv, String userType, String[] lastThreeTrips) {

        RegisteredUsers newUser;
        if (userType.equalsIgnoreCase("VIP")) {
            newUser = new VIPUser(fullName, emailAddress, dateOfBirth, cardNumber,
                    cardExpiryDate, cardProvider, cvv, userType, lastThreeTrips);
        } else {
            newUser = new RegularUser(fullName, emailAddress, dateOfBirth, cardNumber,
                    cardExpiryDate, cardProvider, cvv, userType, lastThreeTrips);
        }
        registeredUsersList.add(newUser);
        return newUser;   // 返回创建的对象供其他类使用
    }

    // 其他已有方法（如查询用户等）保持不变...
}
