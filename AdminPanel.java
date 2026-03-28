import java.util.Scanner;

public class AdminPanel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BikeRental bikeRental = new BikeRental();

        int choice;
        do {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. Demo the Bike Rental System");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bikeRental.simulateApplicationInput();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 2);

        scanner.close();
    }
}
