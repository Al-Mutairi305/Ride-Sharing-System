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
		return String.format("Ride Type: %s, Ride ID: %s, Rider: %s, Driver: %s, Pickup Location: %s, Dropoff Location: %s, Pickup Time: %s, Dropoff Time: %s"
		, "Private", rideID, rider.getName(), driver.getName(), pickupLocation, dropoffLocation, pickupTime.format(), dropoffTime.format());
	}
}
