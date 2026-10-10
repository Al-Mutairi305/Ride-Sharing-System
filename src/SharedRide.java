public class SharedRide extends Ride implements ISharedRide {

    private IRiderList participants;

    public SharedRide(int rideID, String pickupLocation, String dropoffLocation, IDriver driver, IDateTime pickupTime,
            IDateTime dropoffTime, IRiderList participants) {
        super(rideID, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime);
        this.participants = participants;
    }

    public boolean hasRider(int riderId) {
        return participants.findById(riderId) != null;
    }

    public LinkedList<IRider> getParticipants() {
        return participants.getAll();
    }

    public boolean addParticipant(IRider rider) {
        return participants.add(rider);
    }

    public boolean removeParticipantById(int riderId) {
        return participants.removeById(riderId);
    }

    public boolean isEmpty() {
        return participants.size() == 0;
    }

    public String toString() {
        String sharedRide = "Ride Type: Shared" + ", Ride ID: " + rideID + ", Driver: " + driver.getName()
                + ", Pickup Location: " + pickupLocation + ", Dropoff Location: " + dropoffLocation;
        sharedRide += ", Pickup Time: " + pickupTime.format() + ", Dropoff Time: " + dropoffTime.format();

        sharedRide += ", Rider(s): ";
        if (participants.size() == 0) {
            sharedRide += "None";
            return sharedRide;
        }

        LinkedList<IRider> list = participants.getAll();
        Node<IRider> current = list.getHead();

        while (current.getNext() != null) {
            sharedRide += current.getData().getName() + ", ";
            current = current.getNext();
        }

        sharedRide += current.getData().getName();
        return sharedRide;
    }

}
