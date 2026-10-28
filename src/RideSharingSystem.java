/* Each team memmber must implement the RideSharingSystem class's methods that correspond to their designated list class as follows:
Khaled --> DriverList
Omar --> RiderList
Abdulelah --> RideList
*/
import java.io.*;

public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    public IRideList rideList;

    public RideSharingSystem() {
        riderList = new RiderList();
        driverList = new DriverList();
        rideList = new RideList();
    }

    public boolean loadRidersFromCSV(String ridersFilePath) throws IllegalArgumentException {
        BufferedReader bufferedReader = null;
        String lineRead = "";

        try {
            FileReader fileReader = new FileReader(ridersFilePath);
            bufferedReader = new BufferedReader(fileReader);

            while ((lineRead = bufferedReader.readLine()) != null) {
                String[] row = lineRead.split(",");
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String email = row[2];
                String phoneNumber = row[3];
                String homeCity = row[4];
                IRider rider = new Rider(id, name, phoneNumber, email, homeCity);
                addRider(rider);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
            
        }
        
        return true;
    
    }

    public boolean loadDriversFromCSV(String driversFilePath) throws IllegalArgumentException {
        BufferedReader bufferedReader = null;
        String lineRead = "";

        try {
            FileReader fileReader = new FileReader(driversFilePath);
            bufferedReader = new BufferedReader(fileReader);

            while ((lineRead = bufferedReader.readLine()) != null) {
                String[] row = lineRead.split(",");
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String phoneNumber = row[2];
                String vehiclePlate = row[3];
                VehicleType vehicleType = VehicleType.valueOf(row[4]);
                IDriver driver = new Driver(id, name, phoneNumber, vehiclePlate, vehicleType);
                addDriver(driver);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
            
        }
        
        return true;
    }

    public boolean loadRidesFromCSV(String ridesFilePath) {
        BufferedReader bufferedReader = null;
        String lineRead = "";

        try {
            FileReader fileReader = new FileReader(ridesFilePath);
            bufferedReader = new BufferedReader(fileReader);

            while ((lineRead = bufferedReader.readLine()) != null) {
                // Format of each line is "RIDETYPE, PICKUPLOCATION, PICKUPDATE, DROPOFFDATE, DROPOFFLOCATION, DRIVERID, RIDERID(s)"
                String[] row = lineRead.split(",");
                String rideType = row[0];
                String pickupLocation = row[1];
                String pickupTimeString = row[2];
                int[] pickupTimeArray = dateTimeRetrieve(pickupTimeString);
                IDateTime pickupTime = new DateTime(pickupTimeArray[0], pickupTimeArray[1], pickupTimeArray[2], pickupTimeArray[3], pickupTimeArray[4]);
                String dropoffTimeString = row[3];
                int[] dropoffTimeArray = dateTimeRetrieve(dropoffTimeString);
                IDateTime dropoffTime = new DateTime(dropoffTimeArray[0], dropoffTimeArray[1], dropoffTimeArray[2], dropoffTimeArray[3], dropoffTimeArray[4]);
                String dropoffLocation = row[4];
                int driverId = Integer.parseInt(row[5]);
                
                if (rideType.equals("PRIVATE")) {
                    int riderId = Integer.parseInt(row[6]);
                    schedulePrivateRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderId, driverId);
                    
            
                } else {
                    String[] riders = row[6].split(";");           // Split the 7th column which contains the riders' ids, each separated by ";", then store them in an array.
                    int[] riderIds = new int[riders.length];

                    for (int i = 0; i < riderIds.length; i++)
                        riderIds[i] = Integer.parseInt(riders[i]);

                    scheduleSharedRide(pickupLocation, pickupTime, dropoffTime, dropoffLocation, riderIds, driverId);

                }
                
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
            
        }
        
        return true;
    }

    public boolean addRider(IRider rider) {  // We will assume that "Invalid" refers to invalid phone number.
        try {
            rider.setPhoneNumber(rider.getPhoneNumber());  // Check if phone number is valid.

        } catch (IllegalArgumentException e) {
            return false;
        }

        return riderList.add(rider);

    }

    public boolean addDriver(IDriver driver) {   // We will assume that "Invalid" refers to invalid phone number or vehicle plate
        try {
            driver.setPhoneNumber(driver.getPhoneNumber());    // Check if information are valid inside the try-catch block.
            driver.setVehiclePlate(driver.getVehiclePlate());

        } catch (IllegalArgumentException e) {
            return false;
        }

        return driverList.add(driver);                 // DriverList.add() will check for uniqueness.
    }

    public IRider searchRiderById(int riderId) {
        return riderList.findById(riderId);
    }

    public IRider searchRiderByEmail(String email) {
        return riderList.findByEmail(email);
    }

    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {
        return riderList.findByHomeCity(homeCity);
    }

    public LinkedList<IRider> getAllRiders() {
        return riderList.getAll();
    }

    public IDriver searchDriverById(int driverId) {
        return driverList.findById(driverId);
    }

    public IDriver searchDriverByVehiclePlate(String vehiclePlate) {
        return driverList.findByVehiclePlate(vehiclePlate);
    }

    public LinkedList<IDriver> searchDriversByVehicleType(VehicleType vehicleType) {
        return driverList.findByVehicleType(vehicleType);
    }

    public LinkedList<IDriver> getAllDrivers() {
        return driverList.getAll();
    }

    public boolean removeRider(int riderId) {
        IRider rider = searchRiderById(riderId);
        if (rider == null)                          // Check if the rider does not exist.
        return false;
        
        LinkedList<IRide> rideHistory = rider.getRideHistory();             // Get all rides associated with the rider to remove them.
        Node<IRide> currentRideNode = rideHistory.getHead();

        while (currentRideNode != null) {
            IRide ride = currentRideNode.getData();
            IDriver driver = currentRideNode.getData().getDriver();

            if (ride instanceof PrivateRide) {                 // Rides associated with the rider can be a mix of private and shared rides.
                rideList.removeRideById(ride.getRideId());
                ((Person)driver).removeFromRideHistory(ride.getRideId());
                ((Person)rider).removeFromRideHistory(ride.getRideId());

            } else {
                ((SharedRide)ride).removeParticipantById(riderId);
                ((Person)rider).removeFromRideHistory(ride.getRideId());

                if (((SharedRide)ride).isEmpty()) {
                    rideList.removeRideById(ride.getRideId());          // Remove the shared ride if it no longer has any participants.
                    ((Person)driver).removeFromRideHistory(ride.getRideId());  // Remove the shared ride from the driver's ride history.
                }
            }

            currentRideNode = currentRideNode.getNext();
        }

        riderList.removeById(riderId);               // Finally, remove the rider from RideSharingSystem ride history.
        return true;

    }

    public boolean removeDriver(int driverId) {
        IDriver driver = searchDriverById(driverId);
        if (driver == null)                              // Check if the driver does not exist.
            return false;
        
        LinkedList<IRide> rideHistory = driver.getRideHistory();          // Get all rides associated with this driver.
        Node<IRide> currentRideNode = rideHistory.getHead();

        while (currentRideNode != null) {       // Traverse the rides and remove them from RideSharingSystem's rideList.
            IRide ride = currentRideNode.getData();
            ((Person)driver).removeFromRideHistory(ride.getRideId());    // Remove the ride from the driver's ride history
            this.rideList.removeRideById(ride.getRideId());

            if (ride instanceof PrivateRide) {
                IRider rider = ((PrivateRide)ride).getRider();
                ((Person)rider).removeFromRideHistory(ride.getRideId());   // Remove the private ride from the rider's ride history.

            } else {
                LinkedList<IRider> riderList = ((SharedRide)ride).getParticipants();          // Traverse the shared ride's riders list and remove the ride from each rider's ride history.
                Node<IRider> currentRiderNode = riderList.getHead();

                while (currentRiderNode != null) {
                    IRider rider = currentRiderNode.getData();
                    ((Person)rider).removeFromRideHistory(ride.getRideId());

                    currentRiderNode = currentRiderNode.getNext();
                }

            }

            currentRideNode = currentRideNode.getNext();

        }

        driverList.removeById(driverId);        // Finally, remove the driver from RideSharingSystem's driverList.
        return true;

    }

    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){
        // Checks if rider and driver exist.
        if (riderList.findById(riderId) != null && driverList.findById(driverId) != null) {
            int rideID = generateUniqueRideId();
            IDriver driver = driverList.findById(driverId);
            IRider rider = riderList.findById(riderId);
            IRide ride = new PrivateRide(rideID, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime, rider);
            // Checks that there is no conflict in schedule.
            if (!hasConflict(ride)) {
                // Adds ride to driver and rider's ride histories.
                ((Person) driver).addToRideHistory(ride);
                ((Person) rider).addToRideHistory(ride);
                rideList.addRide(ride);
                return true;
            }
        }
        return false;
    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {
        IDriver driver = searchDriverById(driverId);
        if (driver == null)
            return false;
        int rideId = generateUniqueRideId();
        IRide sharedRide = new SharedRide(rideId, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime);

        for (int i = 0; i < riderIds.length; i++) {
                if (riderIds[i] == 0)                         // No rider can have an ID of 0. Therefore, riderIds[i] == 0 indicates that the array has been exhausted.
                    break;

                int riderId = riderIds[i];
                IRider rider = searchRiderById(riderId);
                if (rider == null)
                    return false;
                ((SharedRide)sharedRide).addParticipant(rider);
                
        }

        if (hasConflict(sharedRide))
            return false;

        ((Person)driver).addToRideHistory(sharedRide);                 // Add the ride to the system's list, driver's ride history, and each rider's ride history.
        
        LinkedList<IRider> sharedRiderList = ((SharedRide)sharedRide).getParticipants();
        Node<IRider> currentRiderNode = sharedRiderList.getHead();

        while (currentRiderNode != null) {        // Same iterations as previous for loop.
            IRider rider = currentRiderNode.getData();
            ((Person)rider).addToRideHistory(sharedRide);

            currentRiderNode = currentRiderNode.getNext();
        }
        
        rideList.addRide(sharedRide);
        return true;
    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {
        return rideList.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {
        return rideList.findByRiderName(riderName);
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {   // PickupLocation is not unique. Therefore, we will traverse every shared ride associated with that pick up location instead.
        LinkedList<IRider> result = new LinkedList<IRider>();
        LinkedList<IRide> rideList = this.rideList.findByPickupLocation(pickupLocation);
        Node<IRide> currentRideNode = rideList.getHead();

        while (currentRideNode != null) {
            IRide ride = currentRideNode.getData();
            
            if (ride instanceof SharedRide) {         // Traverse all shared rides from rideList.
                LinkedList<IRider> participants = ((SharedRide)ride).getParticipants();
                Node<IRider> currentParticipantNode = participants.getHead();

                while (currentParticipantNode != null) {              // Traverse each participant from each shared ride.
                    IRider rider = currentParticipantNode.getData();
                    result.insertAtBack(rider);

                    currentParticipantNode = currentParticipantNode.getNext();
                }
            }
            currentRideNode = currentRideNode.getNext();
        }
        return result;
    }

    public LinkedList<IRide> getAllRidesAlphabetically() {
        return rideList.getAllAlphabetically();
    }

    // Takes string which contains all date time data ("MM/DD/YYYY HH:MM") in CSV file and puts each part into an integer array
    private int[] dateTimeRetrieve(String dateTime) {
        int[] proccessedDateTime = new int[5];
        // Splits string into ("MM", "DD", "YYYY HH:MM")
        String[] date = dateTime.split("/");
        // DateTime contructor takes (YYYY, MM, DD, HH, MM) so Month is index 1
        proccessedDateTime[1] = Integer.parseInt(date[0]);
        // Day is the third parameter so index 2
        proccessedDateTime[2] = Integer.parseInt(date[1]);
        // Splits the ("YYYY HH:MM") string into ("YYYY", "HH:MM")
        String[] yearTime = date[2].split(" ");
        // Year is the first parameter so index 0
        proccessedDateTime[0] = Integer.parseInt(yearTime[0]);
        // Splits the ("HH:MM") string into ("HH", "MM")
        String[] time = yearTime[1].split(":");
        // Hours
        proccessedDateTime[3] = Integer.parseInt(time[0]);
        // Minutes
        proccessedDateTime[4] = Integer.parseInt(time[1]);
        // Returns array containing (YYYY, MM, DD, HH, MM) as integers to easily make a DateTime object
        return proccessedDateTime;
    }

    private boolean hasConflict(IRide ride) {
        LinkedList<IRide> list = rideList.getAllAlphabetically();
        Node<IRide> current = list.getHead();

        while (current != null) {
            IRide currentRide = current.getData();

            if (currentRide instanceof PrivateRide) {         // Compare the private ride with the passed ride.
                int riderId = ((PrivateRide)currentRide).getRider().getId();
                
                if (currentRide.getDriver().getId() == ride.getDriver().getId()    // Check if both rides have common driver or rider.
                || ride.hasRider(riderId)) {

                    if (ride.getPickupTime().compareTo(currentRide.getDropoffTime()) <0 && currentRide.getPickupTime().compareTo(ride.getDropoffTime()) < 0 )  // The overlap condition (From requirements).
                        return true;
                }

            } else if (currentRide instanceof SharedRide) {         // Compare the shared ride with the passed ride.
                LinkedList<IRider> sharedList = ((SharedRide)currentRide).getParticipants();   // Get the shared ride's list of riders.
                Node<IRider> currentRiderNode = sharedList.getHead();

                while (currentRiderNode != null) {             // Traverse the list of riders and check if each rider is associated with passed ride or not.
                    int riderId = currentRiderNode.getData().getId();

                    if (currentRide.getDriver().getId() == ride.getDriver().getId()
                    || ride.hasRider(riderId))  {

                        if (ride.getPickupTime().compareTo(currentRide.getDropoffTime()) <0 && currentRide.getPickupTime().compareTo(ride.getDropoffTime()) < 0 )
                        return true;
                    }

                    currentRiderNode = currentRiderNode.getNext();
                }
            }
            

            current = current.getNext();
        }

        return false;
         
         
    }

    // Temporary method for testing purposes.
    public LinkedList<IRide> test(int driverId) {
        LinkedList<IRide> l1 = rideList.getAllAlphabetically();
        LinkedList<IRide> l2 = new LinkedList<IRide>();
        Node<IRide> current = l1.getHead();

        while (current != null) {
            if (current.getData().getDriver().getId() == driverId)
                l2.insertAtBack(current.getData());
            
            current = current.getNext();
        }

        return l2;
    }

    // Method that generates a unique ride id for every ride scheduled. No risk of duplicate ids since there is 1 generation per insertion.
    private int generateUniqueRideId() {
        if (this.rideList.size() == 0)
            return 1;

        LinkedList<IRide> rideList = this.rideList.getAllAlphabetically();
        int maxId = rideList.getHead().getData().getRideId();
        Node<IRide> currentRideNode = rideList.getHead().getNext();

        while (currentRideNode != null) {          // Traverse the current ride list to find the maximum ID.
            IRide ride = currentRideNode.getData();

            if (maxId < ride.getRideId())
                maxId = ride.getRideId();

            currentRideNode = currentRideNode.getNext();
        }

        return maxId+1;

    }

}
