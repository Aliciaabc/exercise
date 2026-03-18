import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class UserRegistration {
    // Constants
    private static final double VIP_DISCOUNT_UNDER_18_BIRTHDAY = 25.0;
    private static final double VIP_DISCOUNT_UNDER_18 = 20.0;
    private static final double VIP_BASE_FEE = 100.0;

    // Instance variables
    private String fullName;
    private String emailAddress;
    private String dateOfBirth;
    private long cardNumber;
    private String cardProvider;
    private String cardExpiryDate;
    private double feeToCharge;
    private int cvv;
    private String userType;
    private boolean emailValid;
    private boolean minorAndBirthday;
    private boolean minor;
    private boolean ageValid;
    private boolean cardNumberValid;
    private boolean cardStillValid;
    private boolean validCVV;

    // Public registration method
    public void registration() {
        Scanner scanner = new Scanner(System.in);

        try {
            // Welcome message and choice
            System.out.println("Welcome to the ERyder Registration.\n");
            System.out.println("Here are your two options:");
            System.out.println("1. Register as a Regular User");
            System.out.println("2. Register as a VIP User");
            System.out.print("Please enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                userType = "Regular User";
            } else {
                userType = "VIP User";
            }

            // Full name
            System.out.print("Enter your full name: ");
            fullName = scanner.nextLine();

            // Email
            System.out.print("Enter your email address: ");
            emailAddress = scanner.nextLine();
            emailValid = analyseEmail(emailAddress);
            if (!emailValid) {
                return; // analyseEmail already called registration, so exit this instance
            }

            // Date of birth
            System.out.print("Enter your date of birth (YYYY-MM-DD): ");
            dateOfBirth = scanner.nextLine();
            LocalDate dob = LocalDate.parse(dateOfBirth);
            ageValid = analyseAge(dob);
            if (!ageValid) {
                return; // should not happen as analyseAge exits on invalid age, but for safety
            }

            // Card number
            System.out.print("Enter your card number (VISA, MasterCard, American Express only): ");
            cardNumber = scanner.nextLong();
            scanner.nextLine(); // consume newline
            cardNumberValid = analyseCardNumber(cardNumber);
            if (!cardNumberValid) {
                return;
            }

            // Card expiry date
            System.out.print("Enter your card expiry date (MM/YY): ");
            cardExpiryDate = scanner.nextLine();
            cardStillValid = analyseCardExpiryDate(cardExpiryDate);
            if (!cardStillValid) {
                return;
            }

            // CVV
            System.out.print("Enter your card CVV: ");
            cvv = scanner.nextInt();
            scanner.nextLine(); // consume newline
            validCVV = analyseCVV(cvv);
            if (!validCVV) {
                return;
            }

            // Final checkpoint
            finalCheckpoint();

        } finally {
            scanner.close();
        }
    }

    // Email validation
    private boolean analyseEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            System.out.println("Email is valid");
            return true;
        } else {
            System.out.println("Invalid email address. Going back to the start of the registration");
            registration();
            return false;
        }
    }

    // Age validation
    private boolean analyseAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        boolean isBirthday = (dob.getMonth() == currentDate.getMonth() &&
                              dob.getDayOfMonth() == currentDate.getDayOfMonth());

        if (userType.equals("VIP User")) {
            if (isBirthday && age <= 18 && age > 12) {
                System.out.println("Happy Birthday!");
                System.out.println("You get 25% discount on the VIP subscription fee for being born today and being under 18!");
                minorAndBirthday = true;
            } else if (!isBirthday && age <= 18 && age > 12) {
                System.out.println("You get 20% discount on the VIP subscription fee for being under 18!");
                minor = true;
            }
        }

        if (age <= 12 || age > 120) {
            System.out.println("Looks like you are either too young or already dead. Sorry, you can't be our user. Have a nice day");
            System.exit(0);
        }
        return true; // age is valid
    }

    // Card number validation
    private boolean analyseCardNumber(long cardNumber) {
        String cardNumStr = Long.toString(cardNumber);
        int length = cardNumStr.length();

        // Extract first two and first four digits as integers
        int firstTwoDigits = Integer.parseInt(cardNumStr.substring(0, 2));
        int firstFourDigits = 0;
        if (length >= 4) {
            firstFourDigits = Integer.parseInt(cardNumStr.substring(0, 4));
        }

        // VISA
        if ((length == 13 || length == 15) && cardNumStr.startsWith("4")) {
            cardProvider = "VISA";
            return true;
        }
        // MasterCard
        else if (length == 16 &&
                ((firstTwoDigits >= 51 && firstTwoDigits <= 55) ||
                 (firstFourDigits >= 2221 && firstFourDigits <= 2720))) {
            cardProvider = "MasterCard";
            return true;
        }
        // American Express
        else if (length == 15 && (cardNumStr.startsWith("34") || cardNumStr.startsWith("37"))) {
            cardProvider = "American Express";
            return true;
        }
        // Invalid
        else {
            System.out.println("Sorry, but we accept only VISA, MasterCard, or American Express cards. Please try again with a valid card.");
            System.out.println("Going back to the start of the registration.");
            registration();
            return false;
        }
    }

    // Card expiry date validation
    private boolean analyseCardExpiryDate(String cardExpiryDate) {
        int month = Integer.parseInt(cardExpiryDate.substring(0, 2));
        int year = Integer.parseInt(cardExpiryDate.substring(3, 5)) + 2000;

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        if (year > currentYear || (year == currentYear && month >= currentMonth)) {
            System.out.println("The card is still valid");
            return true;
        } else {
            System.out.println("Sorry, your card has expired. Please use a different card.");
            System.out.println("Going back to the start to the registration process...");
            registration();
            return false;
        }
    }

    // CVV validation
    private boolean analyseCVV(int cvv) {
        String cvvStr = Integer.toString(cvv);
        if ((cardProvider.equals("American Express") && cvvStr.length() == 4) ||
            (cardProvider.equals("VISA") && cvvStr.length() == 3) ||
            (cardProvider.equals("MasterCard") && cvvStr.length() == 3)) {
            System.out.println("Card CVV is valid.");
            return true;
        } else {
            System.out.println("Invalid CVV for the given card.");
            System.out.println("Going back to the start of the registration process.");
            registration();
            return false;
        }
    }

    // Charge fees
    private void chargeFees() {
        if (minorAndBirthday) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18_BIRTHDAY / 100);
        } else if (minor) {
            feeToCharge = VIP_BASE_FEE * (1 - VIP_DISCOUNT_UNDER_18 / 100);
        } else {
            feeToCharge = VIP_BASE_FEE;
        }

        String cardNumberStr = Long.toString(cardNumber);
        String lastFour = cardNumberStr.substring(cardNumberStr.length() - 4);

        System.out.println("Thank you for your payment.");
        System.out.println("A fee of " + feeToCharge + " has been charged to your card ending with " + lastFour);
    }

    // Final checkpoint
    private void finalCheckpoint() {
        if (emailValid && ageValid && cardNumberValid && cardStillValid && validCVV) {
            chargeFees();
        } else {
            System.out.println("Sorry, your registration was unsuccessful due to the following reason(s)");
            if (!emailValid) System.out.println("Invalid email address");
            if (!ageValid) System.out.println("Invalid age");
            if (!cardNumberValid) System.out.println("Invalid card number");
            if (!cardStillValid) System.out.println("Card has expired");
            if (!validCVV) System.out.println("Invalid CVV");
            System.out.println("Going back to the start of the registration process.");
            registration();
        }
    }

    // toString method
    @Override
    public String toString() {
        String cardNumberStr = Long.toString(cardNumber);
        String censoredPart = cardNumberStr.substring(0, cardNumberStr.length() - 4).replaceAll(".", "*");
        String lastFourDigits = cardNumberStr.substring(cardNumberStr.length() - 4);
        String censoredNumber = censoredPart + lastFourDigits;

        return "Registration successful! Here are your details:\n" +
               "User Type: " + userType + "\n" +
               "Full Name: " + fullName + "\n" +
               "Email Address: " + emailAddress + "\n" +
               "Date of Birth: " + dateOfBirth + "\n" +
               "Card Number: " + censoredNumber + "\n" +
               "Card Provider: " + cardProvider + "\n" +
               "Card Expiry Date: " + cardExpiryDate + "\n";
    }
}
