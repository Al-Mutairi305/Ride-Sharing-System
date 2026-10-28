public class PrivateRide extends Ride implements IPrivateRide{
    private IRider rider;

    public PrivateRide(int rideID, String pickupLocation, String dropoffLocation, IDriver driver, IDateTime pickupTime, IDateTime dropoffTime, IRider rider) {
        super(rideID, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime);
        this.rider = rider;
    }

    public boolean hasRider(int riderId) {
        return (rider.getId() == riderId);
    }

    public IRider getRider() {
        return rider;
    }

    public void setRider(IRider rider) {
        this.rider = rider;
    }

    @Override
    public String toString() {
        return "Ride Type: Private" + "\nRide ID: " + rideID + "\nDriver: " + driver.getName()
                + "\nPickup Location: " + pickupLocation + "\nDropoff Location: " + dropoffLocation
        + "\nPickup Time: " + pickupTime.format() + "\nDropoff Time: " + dropoffTime.format()+ "\nRider: "+rider.getName();
    }
}
