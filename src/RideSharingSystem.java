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

    public RideSharingSystem(IRiderList riderList, IDriverList driverList, IRideList rideList) {
        this.riderList = riderList;
        this.driverList = driverList;
        this.rideList = rideList;
    }

    public boolean loadRidersFromCSV(String ridersFilePath) {     

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
                IDriver d = new Driver(id, name, phoneNumber, vehiclePlate, vehicleType);
                driverList.add(d);
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
                IDateTime pickupTime = new DateTime(pickupTimeArray[2], pickupTimeArray[0], pickupTimeArray[1], pickupTimeArray[3], pickupTimeArray[4]);
                String dropoffTimeString = row[3];
                int[] dropoffTimeArray = dateTimeRetrieve(dropoffTimeString);
                IDateTime dropoffTime = new DateTime(dropoffTimeArray[2], dropoffTimeArray[0], dropoffTimeArray[1], dropoffTimeArray[3], dropoffTimeArray[4]);
                String dropoffLocation = row[4];
                int driverId = Integer.parseInt(row[5]);
                if (rideType.equals("PRIVATE")) {
                    int riderId = Integer.parseInt(row[6]);
                    IDriver driver = driverList.findById(driverId);
                    IRider rider = riderList.findById(riderId);
                    IRide r = new PrivateRide(rideId, pickupLocation, dropoffLocation, driver, pickupTime, dropoffTime, rider);
                    rideList.addRide(r);
                    rideId++;
                // TODO: Implement SharedRide Class
                // } else {

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

    public boolean addRider(IRider rider) {

    }

    public boolean addDriver(IDriver driver) {   // Invalid ID or VehiclePlate?
        return driverList.add(driver);
    }

    public IRider searchRiderById(int riderId) {

    }

    public IRider searchRiderByEmail(String email) {

    }

    public LinkedList<IRider> searchRidersByName(String fullName) {

    }

    public LinkedList<IRider> searchRidersByHomeCity(String homeCity) {

    }

    public LinkedList<IRider> getAllRiders() {

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

    }

    public boolean schedulePrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int riderId, int driverId){

    }

    public boolean scheduleSharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, int[] riderIds, int driverId) {

    }

    public LinkedList<IRide> searchRidesByPickupLocation(String pickupLocation) {  

    }

    public LinkedList<IRide> searchRidesByRiderName(String riderName) {   

    }

    public LinkedList<IRider> getSharedRideParticipants(String pickupLocation) {  

    }

    public LinkedList<IRide> getAllRidesAlphabetically() {  

    }

    // Retrieves all DateTime parameters from DateTime column into an int array
    public int[] dateTimeRetrieve(String dateTime) {
        // Format is MM/DD/YYYY HH:MM
        int[] proccessedDateTime = new int[5];
        String[] date = dateTime.split("/");
        proccessedDateTime[0] = Integer.parseInt(date[0]);
        proccessedDateTime[1] = Integer.parseInt(date[1]);
        String[] yearTime = date[2].split(" ");
        proccessedDateTime[2] = Integer.parseInt(yearTime[0]);
        String[] time = yearTime[1].split(":");
        proccessedDateTime[3] = Integer.parseInt(time[0]);
        proccessedDateTime[4] = Integer.parseInt(time[1]);
        return proccessedDateTime;
    }

}
