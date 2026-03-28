import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class BikeRental {
    
    private boolean isRegisteredUser;
    private String emailAddress;
    private String location;
    private LocalDateTime tripStartTime;
    private String bikeID;
    private boolean locationValid;

    
    private UserRegistration userRegistration = new UserRegistration();
    private ActiveRental activeRental; 
    private LinkedList<ActiveRental> activeRentalsList = new LinkedList<>();

    
    public void simulateApplicationInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("This is the simulation of the e-bike rental process.");

        // Accept inputs
        System.out.print("Is registered user? (true/false): ");
        isRegisteredUser = scanner.nextBoolean();
        scanner.nextLine(); // consume newline
        System.out.print("Email address: ");
        emailAddress = scanner.nextLine();
        System.out.print("Location: ");
        location = scanner.nextLine();

        System.out.println("Simulating the analysis of the rental request.");
        
        bikeID = analyseRequest(isRegisteredUser, emailAddress, location);

        if (!locationValid) {
            System.out.println("Location invalid or no bike available. Exiting simulation.");
            return;
        }

        System.out.println("Simulating e-bike reservation...");
        reserveBike(bikeID);

        System.out.println("Displaying the active rentals...");
        viewActiveRentals();

        System.out.println("Simulating the end of the trip...");
        removeTrip(bikeID);

        System.out.println("Displaying the active rentals after trip end...");
        viewActiveRentals();

        scanner.close();
    }

    
    private String analyseRequest(boolean isRegisteredUser, String emailAddress, String location) {
        if (isRegisteredUser) {
            System.out.println("Welcome back, " + emailAddress + "!");
        } else {
            System.out.println("You're not our registered user. Please consider registering.");
            userRegistration.registration();
        }
        
        return validateLocation(location);
    }

    
    private String validateLocation(String location) {
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getLocation().equals(location) && bike.isAvailable()) {
                System.out.println("A bike is available at the location you requested.");
                locationValid = true;
                return bike.getBikeID();
            }
        }
        System.out.println("Sorry, no bikes are available at the location you requested. Please try again later.");
        locationValid = false;
        return null;
    }

    
    private void reserveBike(String bikeID) {
        if (bikeID == null) {
            System.out.println("Sorry, we’re unable to reserve a bike at this time. Please try again later.");
            return;
        }

        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                tripStartTime = LocalDateTime.now();
                bike.setIsAvailable(false);
                bike.setLastUsedTime(tripStartTime);
                System.out.println("Reserving the bike with the " + bikeID + ". Please following the on-screen instructions to locate the bike and start your pleasant journey.");

                // Create ActiveRental object and add to list
                ActiveRental rental = new ActiveRental(bikeID, emailAddress, tripStartTime);
                activeRentalsList.add(rental);
                break;
            }
        }
    }

    
    private void viewActiveRentals() {
        if (activeRentalsList.isEmpty()) {
            System.out.println("No active rentals at the moment.");
        } else {
            for (ActiveRental rental : activeRentalsList) {
                System.out.println(rental);
            }
        }
    }

    
    private void removeTrip(String bikeID) {
        
        Iterator<ActiveRental> iterator = activeRentalsList.iterator();
        while (iterator.hasNext()) {
            ActiveRental rental = iterator.next();
            if (rental.getBikeID().equals(bikeID)) {
                iterator.remove();
                break;
            }
        }

        
        for (Bike bike : BikeDatabase.bikes) {
            if (bike.getBikeID().equals(bikeID)) {
                bike.setIsAvailable(true);
                bike.setLastUsedTime(LocalDateTime.now());
                System.out.println("Your trip has ended. Thank you for riding with us.");
                break;
            }
        }
    }
}