public abstract class Ride implements IRide {
	
	protected int rideID;
	protected String pickupLocation;
	protected String dropoffLocation;
	// TODO: Implement Driver and DateTime
	// protected Driver driver;
	// protected DateTime pickupTime;
	// protected DateTime dropoffTime;

	// public Ride(int rideID, String pickupLocation, String dropoffLocation, IDriver driver, DateTime pickupTime, DateTime dropoffTime) {
	// 	this.rideID = rideID;
	// 	this.pickupLocation = pickupLocation;
	// 	this.dropoffLocation = dropoffLocation;
	// 	this.driver = driver;
	// 	this.pickupTime = pickupTime;
	// 	this.dropoffTime = dropoffTime;
	// }

		// Returns the unique internal ride ID.
	public int getRideId() {
		return rideID; 
	}

	// Returns the pickup location of the ride.
	public String getPickupLocation() {
		return pickupLocation;
	}

	// Sets the pickup location of the ride.
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	// TODO: Implement Driver and DateTime
	// Returns the pickup date/time of the ride.
	// public IDateTime getPickupTime() {
	// 	return pickupTime;
	// }

	// // Returns the drop-off date/time of the ride.
	// public IDateTime getDropoffTime() {
	// 	return dropoffTime;
	// }

	// Returns the drop-off location of the ride.
	public String getDropoffLocation() {
		return dropoffLocation;
	}

	// Sets the drop-off location of the ride.
	public void setDropoffLocation(String dropoffLocation) {
		this.dropoffLocation = dropoffLocation;
	}

	// TODO: Implement Driver and DateTime
	// // Returns the driver assigned to this ride.
	// public IDriver getDriver() {
	// 	return driver;
	// } 

	// // Sets the driver assigned to this ride.
	// public void setDriver(IDriver driver) {
	// 	this.driver = driver;
	// }

	// Checks whether a rider participates in this ride.
	public abstract boolean hasRider(int riderId);

	// Returns a formatted string describing the ride.
	@Override
	public String toString() {
		return "";
	}

	// Compares rides alphabetically by pickup location.
	@Override
	public int compareTo(IRide other) {
		return 0;
	}
}