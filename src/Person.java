public abstract class Person implements IPerson {

    protected String name;
    protected String phoneNumber;
    protected final int id;
    protected IRideList rideHistory;

    public Person(int id, String name, String phoneNumber) throws IllegalArgumentException {
        this.id = id;
        this.name = name;
        setPhoneNumber(phoneNumber);
        rideHistory = new RideList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber.length() != 10)
            throw new IllegalArgumentException("Invalid phone number!");

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (Character.isDigit(phoneNumber.charAt(i)))
                continue;
            else
                throw new IllegalArgumentException("Invalid phone number!");
        }

        this.phoneNumber = phoneNumber;
    }

    
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber;
    }

    public LinkedList<IRide> getRideHistory() {
        return rideHistory.getAllAlphabetically();
    }

    /* The interface IPerson does not provide any methods that populates or depopulates the rideHistory object each person has
    such as "add" or "remove". Therefore, we will add the following methods to cover this issue.*/
    public boolean addToRideHistory(IRide ride) {
        return rideHistory.addRide(ride);
    }

    public boolean removeFromRideHistory(int rideId) {
        return rideHistory.removeRideById(rideId);
    }
    

}
