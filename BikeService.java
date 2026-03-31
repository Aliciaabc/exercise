public class BikeService {
    private BikeDatabase db;

    public BikeService(BikeDatabase db) { this.db = db; }

    public List<Bike> findAvailableBikes() {
        return db.getAllBikes().stream()
                .filter(b -> b.isAvailable() && !b.isReserved())
                .collect(Collectors.toList());
    }

    public boolean validateLocation(String location) {
        return db.getAllBikes().stream()
                .anyMatch(b -> b.getLocation().equalsIgnoreCase(location));
    }

    public boolean reserveBike(String bikeId) {
        Bike bike = db.getBike(bikeId);
        if (bike != null && bike.isAvailable() && !bike.isReserved()) {
            bike.setReserved(true);
            db.updateBike(bike);
            return true;
        }
        return false;
    }

    public boolean releaseBike(String bikeId) {
        Bike bike = db.getBike(bikeId);
        if (bike != null && bike.isReserved()) {
            bike.setReserved(false);
            bike.setAvailable(true);
            db.updateBike(bike);
            return true;
        }
        return false;
    }
}