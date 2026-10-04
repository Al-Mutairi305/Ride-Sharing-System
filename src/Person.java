public abstract class Person implements IPerson {

    protected String name;
    protected String phoneNumber;
    protected int ID;
    protected RideList rideHistory; // TODO: Implement Ride, and RideList classes.

    public Person(String name, String phoneNumber, int ID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
    }

    public int getID() {
        return ID;
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

    public LinkedList<Ride> getRideHistory() { // TODO: Implement Ride, and RideList classes.

    }

    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", ID: " + ID;
    }

}
