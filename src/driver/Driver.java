package driver;

import ride.IRide;
import system.LinkedList;
import system.Person;

public class Driver extends Person implements IDriver {

    private String vehiclePlate;
    private VehicleType vehicleType;

    public Driver(int id, String name, String phoneNumber, String vehiclePlate, VehicleType vehicleType) {
        super(id, name, phoneNumber);
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) throws IllegalArgumentException {
        if (vehiclePlate.length() != 7)
            throw new IllegalArgumentException("Invalid vehicle plate!");

        int i;
        for (i = 0; i < 3; i++) {
            if (Character.isUpperCase(vehiclePlate.charAt(i)))
                continue;
            else
                throw new IllegalArgumentException("Invalid vehicle plate!");
        }

        for (i = 3; i < 7; i++) {
            if (Character.isDigit(vehiclePlate.charAt(i)))
                continue;
            else
                throw new IllegalArgumentException("Invalid vehicle plate!");
        }

        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int compareTo(IDriver other) {
        if (id < other.getId())
            return -1;
        else if (id > other.getId())
            return 1;
        else
            return 0;
    }

    public LinkedList<IRide> getRideHistory() { // Find list of rides and check the ones the driver is assigned to them.
                                                // RideSharingSystem object?

    }

    public String toString() {
        return super.toString() + ", Vehicle plate: " + vehiclePlate + ", Vehicle type: " + vehicleType;
    }

}
