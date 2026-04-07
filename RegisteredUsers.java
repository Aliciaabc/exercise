import java.util.Arrays;

public class RegisteredUsers {
    private String fullName;
    private String emailAddress;
    private String dateOfBirth;
    private long cardNumber;
    private String cardExpiryDate;
    private String cardProvider;
    private int cvv;
    private String userType;
    private String[] lastThreeTrips;

    // 构造器
    public RegisteredUsers(String fullName, String emailAddress, String dateOfBirth,
                           long cardNumber, String cardExpiryDate, String cardProvider,
                           int cvv, String userType, String[] lastThreeTrips) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardProvider = cardProvider;
        this.cvv = cvv;
        this.userType = userType;
        this.lastThreeTrips = lastThreeTrips;
    }

    // 新增：计算基础费用（供子类重写）
    public double calculateFare(double baseFare) {
        return baseFare;
    }

    // 新增：显示用户类型
    public void displayUserType() {
        System.out.println("Regular User");
    }

    // ---------- getters / setters (保持不变) ----------
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public long getCardNumber() { return cardNumber; }
    public void setCardNumber(long cardNumber) { this.cardNumber = cardNumber; }

    public String getCardExpiryDate() { return cardExpiryDate; }
    public void setCardExpiryDate(String cardExpiryDate) { this.cardExpiryDate = cardExpiryDate; }

    public String getCardProvider() { return cardProvider; }
    public void setCardProvider(String cardProvider) { this.cardProvider = cardProvider; }

    public int getCvv() { return cvv; }
    public void setCvv(int cvv) { this.cvv = cvv; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String[] getLastThreeTrips() { return lastThreeTrips; }
    public void setLastThreeTrips(String[] lastThreeTrips) { this.lastThreeTrips = lastThreeTrips; }

    @Override
    public String toString() {
        return "RegisteredUsers{" +
                "fullName='" + fullName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", cardNumber=" + cardNumber +
                ", cardExpiryDate='" + cardExpiryDate + '\'' +
                ", cardProvider='" + cardProvider + '\'' +
                ", cvv=" + cvv +
                ", userType='" + userType + '\'' +
                ", lastThreeTrips=" + Arrays.toString(lastThreeTrips) +
                '}';
    }
}