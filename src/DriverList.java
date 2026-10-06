public class DriverList implements IDriverList {

    private Node<IDriver> head;
    private Node<IDriver> tail;
    private int size;

    public DriverList() {
        head = tail = null;
        size = 0;
    }

    public boolean add(IDriver driver) {

        // Check if the driver does not exist in the list first, or if its null.
        if (driver == null || findById(driver.getId()) != null)
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

}
