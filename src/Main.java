import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {

// Standard number of hyphens: 64 (----------------------------------------------------------------)
     
    private static VehicleType proccessVehicleTypeInput(String vehicleTypeInput) throws IllegalArgumentException {
        if (vehicleTypeInput.toLowerCase().contains("luxury"))
            return VehicleType.LUXURY_SEDAN;

        return VehicleType.valueOf(vehicleTypeInput.toUpperCase());
    }

    private static <T> void printList(LinkedList<T> list) {
         int size = list.getSize();
        if (size == 0) {
            System.out.println("No results");
            return;
        }
        
        Node<T> current = list.getHead();

        for (int i = 1; i <= size; i++) {
            System.out.println("#"+ i + "\n" + current.getData());
            if (i != size)
                System.out.println("----------------------------------------------------------------");
            current = current.getNext();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IRideSharingSystem rideSharingSystem = new RideSharingSystem();
        System.out.println("Starting the Ride Sharing System...");
        System.out.println("Loading 30 drivers...");
        rideSharingSystem.loadDriversFromCSV("Phase 1\\drivers_30.csv");
        System.out.println("Loading 100 riders...");
        rideSharingSystem.loadRidersFromCSV("Phase 1\\riders_100.csv");
        System.out.println("Loading 40 rides...");
        rideSharingSystem.loadRidesFromCSV("Phase 1\\rides_40.csv");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Welcome to the Ride Sharing System!");
        System.out.println("----------------------------------------------------------------");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("---(Main Menu)---\n");                      // Main menu.
            System.out.println("1. List all riders.\n\n2. Search for riders by home city.\n\n3. Search for riders by name."
            +"\n\n4. Add a rider.\n\n5. List all drivers.\n\n6. Search for drivers by vehicle type."
            +"\n\n7. Add a driver.\n\n8. List all rides alphabetically by pickup location.\n\n9. Search for rides by pickup location."
            +"\n\n10. Search for rides by rider name.\n\n11. List the participants of a shared ride by pickup location.\n\n12. Add a ride."
            +"\n\n0. Exit the program.");
            System.out.println("----------------------------------------------------------------");

            System.out.print("Please select an option: ");
            int option = -1;

            try {
            option = scanner.nextInt();
            scanner.nextLine();                 // clear buffer
            System.out.println("----------------------------------------------------------------");
            
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Invalid input; Input must be an integer.");
                System.out.println("----------------------------------------------------------------");
                scanner.nextLine(); 
                continue;                // Display the main menu again and let user try again.
            }
            switch (option) {
                
                default: System.out.println("Invalid input; Input must be between 0-12");
                System.out.println("----------------------------------------------------------------");
                break;

                case 0:  // Exit.
                isRunning = false;
                break;

                case 1:  // List all riders.
                System.out.println("---(All Riders)---\n");
                printList(rideSharingSystem.getAllRiders());
                System.out.println("----------------------------------------------------------------");
                break;

                case 2:  // Search for riders from a specific home city.
                System.out.print("Please select a home city: ");
                String homeCity = scanner.nextLine();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---(All riders from: "+homeCity+")---\n");
                printList(rideSharingSystem.searchRidersByHomeCity(homeCity));
                System.out.println("----------------------------------------------------------------");
                break;

                case 3:  // Search for riders by name.
                System.out.print("Please enter a name: ");
                String name = scanner.nextLine();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---(All riders with the name: "+name+")---\n");
                printList(rideSharingSystem.searchRidersByName(name));
                System.out.println("----------------------------------------------------------------");
                break;

                case 4: // Add rider.
                System.out.print("Please enter the name of the rider: ");
                String riderName = scanner.nextLine();
                System.out.println();
                
                int riderId = 0;
                System.out.print("Please enter ID of the rider: ");
                try {
                riderId = scanner.nextInt();                         // Possible input mismatch.
                scanner.nextLine();
                System.out.println();

                } catch (InputMismatchException e) {
                    System.out.println();
                    System.out.println("Fail to add rider "+riderName+"; Invalid ID, must be an integer.");
                    scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    break;
                }
               
                System.out.print("Please enter the phone number of the rider: ");
                String riderPhoneNumber = scanner.nextLine();
                System.out.println();
                
                System.out.print("Please enter the home city of the rider: ");
                String riderHomeCity = scanner.nextLine();
                System.out.println();

                System.out.print("Please enter the email of the rider: ");
                String riderEmail = scanner.nextLine();
                System.out.println();

                try {
                    IRider rider = new Rider(riderId, riderName, riderPhoneNumber, riderEmail, riderHomeCity);
                    if (!rideSharingSystem.addRider(rider)) {
                        System.out.println("Fail to add rider "+riderName+"; A rider with the same ID already exists.");
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Fail to add rider "+riderName+"; "+e.getMessage());
                    System.out.println("----------------------------------------------------------------");
                    break;
                }

                System.out.println("Rider "+riderName+" has been added successfully!");
                System.out.println("----------------------------------------------------------------");
                break;

                case 5: // List all drivers.
                System.out.println("---(All Drivers)---\n");
                printList(rideSharingSystem.getAllDrivers());
                System.out.println("----------------------------------------------------------------");
                break;

                case 6: // List all drivers with a specific vehicle type.
                System.out.print("Please enter the vehicle type: ");
                String vehicleTypeInput = scanner.nextLine();
                VehicleType vehicleType = null;
                
                try {
                    vehicleType = proccessVehicleTypeInput(vehicleTypeInput);

                } catch (IllegalArgumentException e) {      // valueOf(String s) throws an Illegal arguement exception if s is not a VehicleType.
                    System.out.println();
                    System.out.println("Fail to list all drivers; "+vehicleTypeInput+" is not a registered vehicle type.");
                    System.out.println("----------------------------------------------------------------");
                    break;
                }

                System.out.println("----------------------------------------------------------------");
                System.out.println("---(All Drivers with the vehicle type: "+vehicleType+")---\n");
                printList(rideSharingSystem.searchDriversByVehicleType(vehicleType));
                System.out.println("----------------------------------------------------------------");
                break;

                case 7: // Add driver.
                System.out.print("Please enter the name of the driver: ");
                String driverName = scanner.nextLine();
                System.out.println();

                int driverId = 0;
                System.out.print("Please enter ID of the driver: ");
                try {
                driverId = scanner.nextInt();                         // Possible input mismatch.
                scanner.nextLine();
                System.out.println();
                
                } catch (InputMismatchException e) {
                    System.out.println();
                    System.out.println("Fail to add driver "+driverName+"; Invalid ID, must be an integer.");
                    scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    break;
                }

                System.out.print("Please enter the phone number of the driver: ");
                String driverPhoneNumber = scanner.nextLine();
                System.out.println();

                System.out.print("Please enter the vehicle plate of the driver: ");
                String driverVehiclePlate = scanner.nextLine();
                System.out.println();

                System.out.print("Please enter the vehicle type of the driver: ");
                String driverVehicleTypeInput = scanner.nextLine();
                VehicleType driverVehicleType = null;
                System.out.println();

                try {
                    driverVehicleType = proccessVehicleTypeInput(driverVehicleTypeInput);
                } catch (IllegalArgumentException e) {      // valueOf(String s) throws an Illegal arguement exception if s is not a VehicleType.
                    System.out.println("Fail to add driver; "+driverVehicleTypeInput+" is not a registered vehicle type.");
                    System.out.println("----------------------------------------------------------------");
                    break;
                }

                try {
                    IDriver driver = new Driver(driverId, driverName, driverPhoneNumber, driverVehiclePlate, driverVehicleType);
                    if (!rideSharingSystem.addDriver(driver)) {
                        System.out.println("Fail to add driver "+driverName+"; A driver with the same ID or vehicle plate already exists.");
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Fail to add driver; "+e.getMessage());
                    System.out.println("----------------------------------------------------------------");
                    break;
                }

                System.out.println("Driver "+driverName+" has been added successfully!");
                System.out.println("----------------------------------------------------------------");
                break;

                case 8:
                System.out.println("---(All rides alphabetically by pickup location)---\n");
                printList(rideSharingSystem.getAllRidesAlphabetically());
                System.out.println("----------------------------------------------------------------");
                break;

                case 9:
                System.out.print("Please enter the pickup location: ");
                String pickupLocation = scanner.nextLine();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---(All rides with the pickup location: "+pickupLocation+")---\n");
                printList(rideSharingSystem.searchRidesByPickupLocation(pickupLocation));
                System.out.println("----------------------------------------------------------------");
                break;

                case 10:
                System.out.print("Please enter the name of the rider: ");
                String searchRideName = scanner.nextLine();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---(All rides with the rider name: "+searchRideName+")---\n");
                printList(rideSharingSystem.searchRidesByRiderName(searchRideName));
                System.out.println("----------------------------------------------------------------");
                break;

                
                

                


                




            }
        }
        

    }

}
