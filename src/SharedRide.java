public class SharedRide extends Ride implements ISharedRide {

    private IRiderList participants;

    public SharedRide(int rideID, String pickupLocation, String dropoffLocation, IDriver driver, IDateTime pickupTime,
            IDateTime dropoffTime) {
        super(rideID, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime);
        participants = new RiderList();
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
        String sharedRide = "Ride Type: Shared" + "\nRide ID: " + rideID + "\nDriver: " + driver.getName()
                + "\nPickup Location: " + pickupLocation + "\nDropoff Location: " + dropoffLocation;
        sharedRide += "\nPickup Time: " + pickupTime.format() + "\nDropoff Time: " + dropoffTime.format();

        sharedRide += "\nRider(s): ";
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
