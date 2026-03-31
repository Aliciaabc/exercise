public class RentalService {
    private ActiveRental activeRentals;
    private BikeService bikeService;

    public RentalService(ActiveRental activeRentals, BikeService bikeService) {
        this.activeRentals = activeRentals;
        this.bikeService = bikeService;
    }

    public String startRental(String bikeId, String userId) {
        if (!bikeService.findAvailableBikes().stream().anyMatch(b -> b.getId().equals(bikeId)))
            return null;
        if (!bikeService.reserveBike(bikeId))
            return null;
        String rentalId = UUID.randomUUID().toString();
        activeRentals.add(new Rental(rentalId, userId, bikeId, LocalDateTime.now()));
        return rentalId;
    }

    public double endRental(String rentalId) {
        Rental rental = activeRentals.get(rentalId);
        if (rental == null || !rental.isActive()) return -1;
        rental.setEndTime(LocalDateTime.now());
        rental.setActive(false);
        bikeService.releaseBike(rental.getBikeId());
        long hours = Duration.between(rental.getStartTime(), rental.getEndTime()).toHours();
        return Math.max(hours, 1) * 5.0;
    }

    public boolean cancelRental(String rentalId) {
        Rental rental = activeRentals.get(rentalId);
        if (rental == null || !rental.isActive()) return false;
        rental.setActive(false);
        bikeService.releaseBike(rental.getBikeId());
        activeRentals.remove(rentalId);
        return true;
    }

    public List<Rental> getActiveRentalsForUser(String userId) {
        return activeRentals.getAll().stream()
                .filter(r -> r.isActive() && r.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}