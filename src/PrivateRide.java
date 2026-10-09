public class PrivateRide extends Ride implements IPrivateRide{
	private IRider rider;

	public PrivateRide(int rideID, String pickupLocation, String dropoffLocation, IDriver driver, DateTime pickupTime, DateTime dropoffTime, IRider rider) {
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
}
