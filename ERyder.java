public class ERyder {
    public static final String COMPANY_NAME = "ERyder";
    public static final double BASE_FARE = 1.0;
    public static final double PER_MINUTE_FARE = 0.5;

    private String bikeID;
    private int batteryLevel;
    private double kmDriven;

    private final String LINKED_ACCOUNT;
    private final String LINKED_PHONE_NUMBER;

    private int totalUsageInMinutes;
    private double totalFare;

    public ERyder(String bikeID, int batteryLevel, double kmDriven) {
        this.bikeID = bikeID;
        this.batteryLevel = batteryLevel;
        this.kmDriven = kmDriven;
        this.LINKED_ACCOUNT = "default_user";
        this.LINKED_PHONE_NUMBER = "000-000-0000";
        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    public ERyder(String bikeID, int batteryLevel, double kmDriven,
                  String linkedAccount, String linkedPhoneNumber) {
        this.bikeID = bikeID;
        this.batteryLevel = batteryLevel;
        this.kmDriven = kmDriven;
        this.LINKED_ACCOUNT = linkedAccount;
        this.LINKED_PHONE_NUMBER = linkedPhoneNumber;
        this.totalUsageInMinutes = 0;
        this.totalFare = 0.0;
    }

    public void printRideDetails(int usageInMinutes) {
        this.totalUsageInMinutes = usageInMinutes;
        this.totalFare = calculateFare(usageInMinutes);

        System.out.println("Company Name: " + COMPANY_NAME);
        System.out.println("Linked Account: " + LINKED_ACCOUNT);
        System.out.println("Linked Phone: " + LINKED_PHONE_NUMBER);
        System.out.println("Bike ID: " + bikeID);
        System.out.println("Usage (minutes): " + usageInMinutes);
        System.out.println("Total Fare: " + totalFare);
        System.out.println("------------------------");
    }

    private double calculateFare(int usageInMinutes) {
        return BASE_FARE + (PER_MINUTE_FARE * usageInMinutes);
    }

    public static void main(String[] args) {
        ERyder obj1 = new ERyder("B001", 80, 15.5);
        ERyder obj2 = new ERyder("B002", 90, 20.0, "user123", "555-1234");

        obj1.printRideDetails(30);
        obj2.printRideDetails(45);

        System.out.println("What happens if you try to call calculateFare() directly?");
        System.out.println("Answer: Because calculateFare is a private method, it cannot be called directly from outside the class; compilation will fail.");
        System.out.println("How to call it properly?");
        System.out.println("Answer: It can only be called indirectly through a public method (like printRideDetails) from within the class, or from other methods inside the class.");
    }
}
