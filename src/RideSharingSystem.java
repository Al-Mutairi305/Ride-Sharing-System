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
            System.out.println(e.getMessage())
            return false;

        } finally {
            
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage())
                return false;
            }
            
        }
        
        return true;
    }

	public boolean loadRidesFromCSV(String ridesFilePath) {

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



}
