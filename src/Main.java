import java.util.Scanner;
public class Main {

// Standard number of hyphens: 64 (----------------------------------------------------------------)
     
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
            int option = scanner.nextInt();
            System.out.println("----------------------------------------------------------------");

            switch (option) {
                
                default: System.out.println("Invalid input! Please try again.");
                System.out.println("----------------------------------------------------------------");
                break;

                case 0:  // Exit.
                isRunning = false;
                break;

                case 1:  // List all riders.
                System.out.println("---All Riders---\n");
                printList(rideSharingSystem.getAllRiders());
                System.out.println("----------------------------------------------------------------");
                break;

                case 2:  // Search for riders from a specific home city.
                System.out.print("Please select a home city: ");
                String homeCity = scanner.next();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---All riders from: "+homeCity+"---\n");
                printList(rideSharingSystem.searchRidersByHomeCity(homeCity));
                System.out.println("----------------------------------------------------------------");
                break;

                case 3:  // Search for riders by name.
                System.out.print("Please enter a name: ");
                String name = scanner.next();
                System.out.println("----------------------------------------------------------------");
                System.out.println("---All riders with the name: "+name+"--\n");
                printList(rideSharingSystem.searchRidesByRiderName(name));
                System.out.println("----------------------------------------------------------------");
                break;

                case 4: // Add rider.
                System.out.print("Please enter ID of the rider: ");
                int riderId = scanner.nextInt();                         // Possible input mismatch.
                System.out.println();

                System.out.print("Please enter the name of the rider: ");
                String riderName = scanner.next();
                System.out.println();

                System.out.print("Please enter the phone number of the rider: ");
                String riderPhoneNumber = scanner.next();
                System.out.println();
                
                System.out.print("Please enter the home city of the rider: ");
                String riderHomeCity = scanner.next();
                System.out.println();

                System.out.print("Please enter the email of the rider: ");
                String riderEmail = scanner.next();
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

                // TODO: Implement rest of options.
                
            }
        }
        

    }

}
