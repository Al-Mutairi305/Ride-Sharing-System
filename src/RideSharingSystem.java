/* Each team memmber must implement the RideSharingSystem class's methods that correspond to their designated list class as follows:
Khaled --> DriverList 
Omar --> RiderList
Abdulelah --> RideList
*/
import java.io.*;

public class RideSharingSystem implements IRideSharingSystem {

    private IRiderList riderList;
    private IDriverList driverList;
    private IRideList rideList;

    public RideSharingSystem() {
        riderList = new RiderList();
        driverList = new DriverList();
        rideList = new RideList();
    }

    public boolean loadRidersFromCSV(String ridersFilePath) {   
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
                riderList.add(rider);
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

    public boolean loadDriversFromCSV(String driversFilePath) {
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
        int rideId = 1;

        try {
            FileReader fileReader = new FileReader(ridesFilePath);
            bufferedReader = new BufferedReader(fileReader);

            while ((lineRead = bufferedReader.readLine()) != null) {
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
                IDriver driver = driverList.findById(driverId);
                
                if (rideType.equals("PRIVATE")) {
                    int riderId = Integer.parseInt(row[6]);
                    IRider rider = riderList.findById(riderId);
                    IRide privateRide = new PrivateRide(rideId, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime, rider);
                    
                    if (!hasConflict(privateRide)) {
                        ((Person)driver).addToRideHistory(privateRide);
                        ((Person)rider).addToRideHistory(privateRide);
                        rideList.addRide(privateRide);
                    }
                    
            
                } else {
                    String[] riders = row[6].split(";");           // Split the 7th column which contains the riders' ids, each separated by ";", then store them in an array. 
                    IRide sharedRide = new SharedRide(rideId, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime);
                    try {
                        int i = 0;
                        while (true) {                                    
                            int riderId = Integer.parseInt(riders[i]);
                            IRider rider = this.riderList.findById(riderId);     // This line will not throw NullPointerException if all riders from the "riders_100.csv" file have been loaded beforehand.
                            ((SharedRide)sharedRide).addParticipant(rider);
                            i++;
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                        // Finish reading all of the riders. Automatically breaks from the loop.
                    }

                    if (!hasConflict(sharedRide)) {   // If the ride does not have any conflicts,  and add it to the rideHistory of each person associated with it then add it to list.
                        ((Person)driver).addToRideHistory(sharedRide);  // Method is not visible if reference is IDriver. No risk of a ClassCastException here because every Driver instantiated is a Person.

                        LinkedList<IRider> ridersList = ((SharedRide)sharedRide).getParticipants();
                        Node<IRider> currentRiderNode = ridersList.getHead();
                        while (currentRiderNode != null) {
                            IRider rider = currentRiderNode.getData();
                            ((Person)rider).addToRideHistory(sharedRide);
                            currentRiderNode = currentRiderNode.getNext();
                        }

                        rideList.addRide(sharedRide);
                    }
                }
                rideId++;
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

    public boolean addRider(IRider rider) {

    }

    public boolean addDriver(IDriver driver) {   // We will assume that "Invalid" means invalid phone number or vehicle plate
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

    }

    public LinkedList<IRider> searchRidersByName(String fullName) {
        return riderList.findByName(fullName);
    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {

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

    }

    public boolean removeDriver(int driverId) {
        IDriver driver = searchDriverById(driverId);
        if (driver == null)
            return false;
        
        LinkedList<IRide> rideList = driver.getRideHistory();          // Get all rides associated with this driver.
        Node<IRide> currentRideNode = rideList.getHead();

        while (currentRideNode != null) {       // Traverse the rides and remove them from RideSharingSystem's rideList.
            IRide ride = currentRideNode.getData();
            this.rideList.removeRideById(ride.getRideId());

            currentRideNode = currentRideNode.getNext();

        }

        driverList.removeById(driverId);        // Finally, remove the driver from RideSharingSystem's driverList.
        return true;

    }

    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){

    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {

    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {  
        return rideList.findByPickupLocation(pickupLocation);
    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {   
        return rideList.findByRiderName(riderName);
    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {  

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

                while (currentRiderNode != null) {             // Traverse the list of riders and compare each with passed rider.
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

}
