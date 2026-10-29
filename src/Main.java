import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Standard number of hyphens: 64
    // (----------------------------------------------------------------)

    // Private method that proccesses date inputs in the main menu option 12 (Add ride).
    private static IDateTime proccessDateInput() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Please enter the year: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            if (year < 0) {
                System.out.println("\nFail to add ride; Year cannot be negative.");
                return null;
            }

            System.out.print("\nPlease enter the month: ");
            int month = scanner.nextInt();
            scanner.nextLine();

            if (month < 1 || month > 12) {
                System.out.println("\nFail to add ride; Month must be between 1-12");
                return null;
            }

            System.out.print("\nPlease enter the day: ");
            int day = scanner.nextInt();
            scanner.nextLine();

            if (day < 1 || day > 31) {
                System.out.println("\nFail to add ride; Day must be between 1-31");
                return null;
            }

            System.out.print("\nPlease enter the hour: ");
            int hour = scanner.nextInt();
            scanner.nextLine();

            if (hour < 0 || hour > 23) {
                System.out.println("\nFail to add ride; Hour must be between 0-23");
                return null;
            }

            System.out.print("\nPlease enter the minute: ");
            int minute = scanner.nextInt();
            scanner.nextLine();

            if (minute < 0 || minute > 59) {
                System.out.println("\nFail to add ride; Minute must be between 0-59");
                return null;
            }

            IDateTime date = new DateTime(year, month, day, hour, minute);
            return date;

        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("\nFail to add ride; Input must be an integer.");
            return null;
        }
    }

    // Private method that proccesses VehicleType input for main menu options 6 and 7.
    private static VehicleType proccessVehicleTypeInput() {
        Scanner scanner = new Scanner(System.in);

        int choice = -1;
        boolean tryAgain = true;
        while (tryAgain) {
            System.out.println("1. Sedan.\n2. Luxury Sedan.\n3. SUV.\n4. Van.\n0. Cancel.");
            System.out.println("----------------------------------------------------------------");
            System.out.print("Please select a vehicle type: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice <= 4 && choice >= 0)
                    tryAgain = false;

                else {
                    System.out.println("\nInvalid Input; Input must be between 0-4");
                    System.out.println("----------------------------------------------------------------");
                }

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\nInvalid Input; Input must be an integer.");
                System.out.println("----------------------------------------------------------------");
            }

        }
        switch (choice) {
            default:
                return null;

            case 1:
                return VehicleType.SEDAN;

            case 2:
                return VehicleType.LUXURY_SEDAN;

            case 3:
                return VehicleType.SUV;

            case 4:
                return VehicleType.VAN;

        }
    }

    private static <T> void printList(LinkedList<T> list) {
        int size = list.getSize();
        if (size == 0) {
            System.out.println("No results");
            return;
        }

        Node<T> current = list.getHead();

        for (int i = 1; i <= size; i++) {
            System.out.println("#" + i + "\n" + current.getData());
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
            System.out.println("---(Main Menu)---\n"); // Main menu.
            System.out.println(
                    "1. List all riders.\n\n2. Search for riders by home city.\n\n3. Search for riders by name."
                            + "\n\n4. Add a rider.\n\n5. List all drivers.\n\n6. Search for drivers by vehicle type."
                            + "\n\n7. Add a driver.\n\n8. List all rides alphabetically by pickup location.\n\n9. Search for rides by pickup location."
                            + "\n\n10. Search for rides by rider name.\n\n11. List the participants of a shared ride by pickup location.\n\n12. Add a ride."
                            + "\n\n0. Exit the program.");
            System.out.println("----------------------------------------------------------------");

            System.out.print("Please select an option: ");
            int option = -1;

            try {
                option = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                if (option < 0 || option > 12) {
                    System.out.println("\nInvalid input; Input must be between 0-12");
                    System.out.println("----------------------------------------------------------------");
                    continue;
                }

            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Invalid input; Input must be an integer.");
                System.out.println("----------------------------------------------------------------");
                scanner.nextLine();
                continue; // Display the main menu again and let user try again.
            }

            System.out.println("----------------------------------------------------------------");
            switch (option) {

                case 0: // Exit.
                    isRunning = false;
                    break;

                case 1: // List all riders.
                    System.out.println("---(All Riders)---\n");
                    printList(rideSharingSystem.getAllRiders());
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 2: // Search for riders from a specific home city.
                    System.out.print("Please select a home city: ");
                    String homeCity = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All Riders From: " + homeCity + ")---\n");
                    printList(rideSharingSystem.searchRidersByHomeCity(homeCity));
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 3: // Search for riders by name.
                    System.out.print("Please enter a name: ");
                    String name = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All Riders With The Name: " + name + ")---\n");
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
                        riderId = scanner.nextInt(); // Possible input mismatch.
                        scanner.nextLine();
                        System.out.println();

                    } catch (InputMismatchException e) {
                        System.out.println();
                        System.out.println("Fail to add rider " + riderName + "; Invalid ID, must be an integer.");
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
                            System.out.println("Fail to add rider " + riderName + "; A rider with the same ID already exists.");
                            System.out.println("----------------------------------------------------------------");
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fail to add rider " + riderName + "; " + e.getMessage());
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("Rider " + riderName + " has been added successfully!");
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 5: // List all drivers.
                    System.out.println("---(All Drivers)---\n");
                    printList(rideSharingSystem.getAllDrivers());
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 6: // List all drivers with a specific vehicle type.
                    VehicleType vehicleType = proccessVehicleTypeInput();

                    if (vehicleType == null) {
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All Drivers With The Vehicle Type: " + vehicleType + ")---\n");
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
                        driverId = scanner.nextInt(); // Possible input mismatch.
                        scanner.nextLine();
                        System.out.println();

                    } catch (InputMismatchException e) {
                        System.out.println();
                        System.out.println("Fail to add driver " + driverName + "; Invalid ID, must be an integer.");
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

                    VehicleType driverVehicleType = proccessVehicleTypeInput();
                    if (driverVehicleType == null) {
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }
                    System.out.println();

                    try {
                        IDriver driver = new Driver(driverId, driverName, driverPhoneNumber, driverVehiclePlate,
                                driverVehicleType);
                        if (!rideSharingSystem.addDriver(driver)) {
                            System.out.println("Fail to add driver " + driverName
                                    + "; A driver with the same ID or vehicle plate already exists.");
                            System.out.println("----------------------------------------------------------------");
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Fail to add driver; " + e.getMessage());
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("Driver " + driverName + " has been added successfully!");
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 8: // List all rides.
                    System.out.println("---(All Rides Alphabetically by Pickup Location)---\n");
                    printList(rideSharingSystem.getAllRidesAlphabetically());
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 9: // Search for all rides with a specific pickup location.
                    System.out.print("Please enter the pickup location: ");
                    String pickupLocation = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All Rides With The Pickup Location: " + pickupLocation + ")---\n");
                    printList(rideSharingSystem.searchRidesByPickupLocation(pickupLocation));
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 10: // Search for all rides by rider name.
                    System.out.print("Please enter the name of the rider: ");
                    String searchRideName = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All rides with the rider name: " + searchRideName + ")---\n");
                    printList(rideSharingSystem.searchRidesByRiderName(searchRideName));
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 11: // List all participants ofr a shared ride by pickup Location
                    System.out.print("Please enter the pickup location: ");
                    String sharedRidePickupLocation = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(All Shared Ride Participants With The Pickup Location: "
                            + sharedRidePickupLocation + ")---\n");
                    printList(rideSharingSystem.getSharedRideParticipants(sharedRidePickupLocation));
                    System.out.println("----------------------------------------------------------------");
                    break;

                case 12:
                    boolean tryAgain = true;
                    int choice = -1;

                    while (tryAgain) {
                        System.out.println("1. Private Ride\n2. Shared Ride\n0. Cancel");
                        System.out.println("----------------------------------------------------------------");
                        System.out.print("Please select the type of ride: ");

                        try {
                            choice = scanner.nextInt();
                            scanner.nextLine();

                            if (choice >= 0 && choice <= 2)
                                tryAgain = false;

                            else {
                                System.out.println("\nInvalid input; Input must be between 0-2");
                                System.out.println("----------------------------------------------------------------");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("\nInvalid input; Input must be an integer.");
                            scanner.nextLine();
                            System.out.println("----------------------------------------------------------------");
                        }

                    }

                    if (choice == 0) {
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("----------------------------------------------------------------");

                    System.out.print("Please enter the pickup location: ");
                    String ridePickupLocation = scanner.nextLine();
                    System.out.println("----------------------------------------------------------------");

                    System.out.println("---(Pickup time)---\n");
                    IDateTime ridePickUpTime = proccessDateInput();

                    if (ridePickUpTime == null) {
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("----------------------------------------------------------------");
                    System.out.println("---(Dropoff Time)---\n");
                    IDateTime rideDropoffTime = proccessDateInput();

                    if (rideDropoffTime == null) {
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    if (rideDropoffTime.compareTo(ridePickUpTime) < 1) {
                        System.out.println("\nFail to add ride; Drop off time must be later than pick up time.");
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    System.out.println("----------------------------------------------------------------");

                    System.out.print("Please enter the dropoff location: ");
                    String rideDropOffLocation = scanner.nextLine();
                    System.out.println();

                    int rideDriverId = 0;
                    System.out.print("Please enter ID of the driver: ");
                    try {
                        rideDriverId = scanner.nextInt(); // Possible input mismatch.
                        scanner.nextLine();
                        System.out.println();

                        if (rideSharingSystem.searchDriverById(rideDriverId) == null) {    
                            System.out.println("Fail to add ride; Driver with the ID "+rideDriverId+" Does not exist.");
                            System.out.println("----------------------------------------------------------------");
                            break;
                        }

                    } catch (InputMismatchException e) {
                        System.out.println();
                        System.out.println("Fail to add ride; driver ID must be an integer.");
                        scanner.nextLine();
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    switch (choice) {
                    
                        case 1:

                        int privateRiderId = 0;
                        System.out.print("Please enter the ID of the rider: ");
                        try {
                            privateRiderId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println();

                            if (rideSharingSystem.searchRiderById(privateRiderId) == null) {    
                                System.out.println("Fail to add ride; Rider with the ID "+privateRiderId+" Does not exist.");
                                System.out.println("----------------------------------------------------------------");
                                break;
                            }

                        } catch (InputMismatchException e) {
                            scanner.nextLine();
                            System.out.println("\nFail to add ride; rider ID must be an integer.");
                            System.out.println("----------------------------------------------------------------");
                            break;
                        }

                        boolean privateRideResult = rideSharingSystem.schedulePrivateRide(ridePickupLocation, ridePickUpTime, rideDropoffTime, rideDropOffLocation, privateRiderId, rideDriverId);
                        
                        if (privateRideResult == false)  
                            System.out.println("Fail to add ride; Ride time is conflicting with existing ride list.");

                        else
                            System.out.println("Ride has been added successfully!");

                        System.out.println("----------------------------------------------------------------");
                        break;

                        case 2:
                        int numberOfRiders = 0;
                        System.out.print("Please enter the number of riders: ");

                        try {
                            numberOfRiders = scanner.nextInt();
                            scanner.nextLine();
                            int riderListSize = rideSharingSystem.getAllRiders().getSize();
                            if (numberOfRiders < 1 || numberOfRiders > riderListSize) {
                                System.out.println("\nFail to add ride; Number of riders must be between 1-"+riderListSize);
                                System.out.println("----------------------------------------------------------------");
                                break;
                            }

                        } catch (InputMismatchException e) {
                            scanner.nextLine();
                            System.out.println("\nFail to add ride; Number of riders must be an integer.");
                            System.out.println("----------------------------------------------------------------");
                            break;
                        }

                        System.out.println("----------------------------------------------------------------");
                        System.out.println("---(Rider IDs)---\n");
                        boolean error = false;                           // To check whether to break from inner switch or not.
                        int[] riderIds = new int[numberOfRiders];
                        for (int i = 1; i <= numberOfRiders; i++) {
                            
                            int sharedRiderId = 0;
                            System.out.print("Please enter the ID of #"+i+" rider: ");

                            try {
                                sharedRiderId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println();

                                if (rideSharingSystem.searchRiderById(sharedRiderId) == null) {    
                                    System.out.println("Fail to add ride; Rider with the ID "+sharedRiderId+" Does not exist.");
                                    error = true;
                                    System.out.println("----------------------------------------------------------------");
                                    break;
                                }

                            } catch (InputMismatchException e) {
                                scanner.nextLine();
                                System.out.println("\nFail to add ride; rider ID must be an integer.");
                                error = true;
                                System.out.println("----------------------------------------------------------------");
                                break;
                            }

                            riderIds[i-1] = sharedRiderId;

                        }

                        if (error)
                            break;

                        boolean sharedRideResult = rideSharingSystem.scheduleSharedRide(ridePickupLocation, ridePickUpTime, rideDropoffTime, rideDropOffLocation, riderIds, rideDriverId);

                         if (sharedRideResult == false) 
                            System.out.println("Fail to add ride; Ride time is conflicting with existing ride list.");
                        else
                            System.out.println("Ride has been added successfully!");
                        
                        System.out.println("----------------------------------------------------------------");
                        break;
                    }

                    break;
            }
        }

    }

}
