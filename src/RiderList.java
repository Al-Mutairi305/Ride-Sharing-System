public class RiderList implements IRiderList {

    private Node<IRider> head;
    private Node<IRider> tail;
    private int size;

    public RiderList() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean add(IRider rider) {

        // Check if rider already exists in the list first, or if its null.
        if (rider == null || findById(rider.getId()) != null)
            return false;

        Node<IRider> newNode = new Node<IRider>(rider);
        // Add at first if list is empty (edge case).
        if (head == null) {
            head = tail = newNode;
            size++;
            return true;
        }

        // Compare with head first, check if the rider's ID is less than the rider in
        // head.
        if (rider.compareTo(head.getData()) < 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
            return true;
        }

        Node<IRider> current = head;
        while (current.getNext() != null) {

            if (rider.compareTo(current.getNext().getData()) < 0) {
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

    public IRider findById(int riderId) {
        IRider result = null;
        Node<IRider> current = head;

        while (current != null) {
            if (current.getData().getId() == riderId)
                result = current.getData();
            current = current.getNext();
        }

        return result;
    }

    public LinkedList<IRider> findByName(String fullName) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        Node<IRider> current = head;

        while (current != null) {
            if (current.getData().getName().equals(fullName))
                result.insertAtBack(current.getData());
            current = current.getNext();
        }

        return result;
    }

    public IRider findByEmail(String email) {
        IRider result = null;
        Node<IRider> current = head;

        while (current != null) {
            if (current.getData().getEmail().equals(email))
                result = current.getData();
            current = current.getNext();
        }

        return result;
    }

    public LinkedList<IRider> findByHomeCity(String homeCity) {
        LinkedList<IRider> result = new LinkedList<IRider>();
        Node<IRider> current = head;

        while (current != null) {
            if (current.getData().getHomeCity().equals(homeCity))
                result.insertAtBack(current.getData());
            current = current.getNext();
        }

        return result;
    }

    public LinkedList<IRider> getAll() {
        LinkedList<IRider> result = new LinkedList<IRider>();
        Node<IRider> current = head;

        while (current != null) {
            result.insertAtBack(current.getData());
            current = current.getNext();
        }

        return result;
    }

    public boolean removeById(int riderId) {

        if (findById(riderId) == null)
            return false;

        Node<IRider> current = head;
        Node<IRider> previous = null;

        while (current != null) {
            if (current.getData().getId() == riderId) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        return false;
    }

    public boolean removeByEmail(String email) {
        if (findByEmail(email) == null)
            return false;

        Node<IRider> current = head;
        Node<IRider> previous = null;

        while (current != null) {
            if (current.getData().getEmail().equals(email)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        return false;
    }

    public int removeByName(String fullName) {
        int count = 0;
        Node<IRider> current = head;
        Node<IRider> previous = null;

        while (current != null) {
            if (current.getData().getName().equals(fullName)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                count++;
            } else {
                previous = current;
            }
            current = current.getNext();
        }

        return count;
    }

    public int removeByHomeCity(String homeCity) {
        int count = 0;
        Node<IRider> current = head;
        Node<IRider> previous = null;

        while (current != null) {
            if (current.getData().getHomeCity().equals(homeCity)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                size--;
                count++;
            } else {
                previous = current;
            }
            current = current.getNext();
        }

        return count;
    }

    
}
