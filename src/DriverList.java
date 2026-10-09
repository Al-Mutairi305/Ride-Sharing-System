public class DriverList implements IDriverList {

    private Node<IDriver> head;
    private Node<IDriver> tail;
    private int size;

    public DriverList() {
        head = tail = null;
        size = 0;
    }

    public boolean add(IDriver driver) {

        // Check if a driver with the same ID or vehicle plate exists in the list first, or if the parameter is null.
        if (driver == null || findById(driver.getId()) != null || findByVehiclePlate(driver.getVehiclePlate()) != null)
            return false;

        Node<IDriver> newNode = new Node<IDriver>(driver);
        // Add at first if list is empty (edge case).
        if (head == null) {
            head = tail = newNode;
            size++;
            return true;
        }

        // Compare with head first, check if the driver's ID is less than the driver in
        // head.
        if (driver.compareTo(head.getData()) < 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
            return true;
        }

        Node<IDriver> current = head;
        while (current.getNext() != null) {

            if (driver.compareTo(current.getNext().getData()) < 0) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                size++;
                return true;
            }
            current = current.getNext();
        }

        tail.setNext(newNode);
        tail = newNode;
        size++;
        return true;

    }

    public IDriver findById(int driverId) {
        IDriver result = null;
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getId() == driverId)
                result = current.getData();

            current = current.getNext();
        }

        return result;

    }

    public LinkedList<IDriver> findByName(String fullName) {
        LinkedList<IDriver> resultList = new LinkedList<IDriver>();
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getName().equals(fullName))
                resultList.insertAtBack(current.getData());
            current = current.getNext();
        }

        return resultList;
    }

    public IDriver findByVehiclePlate(String vehiclePlate) {
        IDriver result = null;
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getVehiclePlate().equals(vehiclePlate))
                result = current.getData();

            current = current.getNext();
        }

        return result;
    }

    public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
        LinkedList<IDriver> resultList = new LinkedList<IDriver>();
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getVehicleType() == vehicleType)
                resultList.insertAtBack(current.getData());

            current = current.getNext();
        }

        return resultList;
    }

    public LinkedList<IDriver> getAll() {
        LinkedList<IDriver> resultList = new LinkedList<IDriver>();
        Node<IDriver> current = head;

        while (current != null) {
            resultList.insertAtBack(current.getData());
            current = current.getNext();
        }

        return resultList;
    }

    public boolean removeById(int driverId) {
        if (findById(driverId) == null)
            return false;

        Node<IDriver> current = head;
        Node<IDriver> previous = null;
        
        // Check if the element to remove is at head (edge case).
        if (head.getData().getId() == driverId) {
            head = head.getNext();
            if (head == null)
                tail = null;
            size--;
            return true;
        }

        while (current.getNext() != null) {
            if (current.getData().getId() == driverId) {
                previous.setNext(current.getNext());
                size--;
                return true;
            }

            previous = current;
            current = current.getNext();
        }

        // Element to be removed is 100% the last element at this point
        previous.setNext(current.getNext());
        tail = previous;
        size--;
        return true;

    }

    public boolean removeByVehiclePlate(String vehiclePlate) {
        if (findByVehiclePlate(vehiclePlate) == null)
            return false;

        Node<IDriver> current = head;
        Node<IDriver> previous = null;
        
        // Check if the element to remove is at head (edge case).
        if (head.getData().getVehiclePlate().equals(vehiclePlate)) {
            head = head.getNext();
            if (head == null)
                tail = null;
            size--;
            return true;
        }

        while (current.getNext() != null) {
            if (current.getData().getVehiclePlate().equals(vehiclePlate)) {
                previous.setNext(current.getNext());
                size--;
                return true;
            }

            previous = current;
            current = current.getNext();
        }

        // Element to be removed is 100% the last element at this point
        previous.setNext(current.getNext());
        tail = previous;
        size--;
        return true;

    }

    // Simpler implementation by using the existing removeById method, but has a worst-case time complexity of O(n²) as a compromise.
    public int removeByName(String fullName) {
        int count = 0;
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getName().equals(fullName)) {      
                removeById(current.getData().getId());
                count++;
            }

            current = current.getNext();
    
        }

        return count;
    }

    public int removeByVehicleType(VehicleType vehicleType) {
        int count = 0;
        Node<IDriver> current = head;

        while (current != null) {
            if (current.getData().getVehicleType() == vehicleType) {      
                removeById(current.getData().getId());
                count++;
            }

            current = current.getNext();
    
        }

        return count;
    }

    public int size() {
        return size;
    }

    
}
