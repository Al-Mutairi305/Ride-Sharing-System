class RideList implements IRideList {

	private Node<IRide> head;
	private Node<IRide> tail;
	private int size;

	public RideList() {
		head = tail = null;
		size = 0;
	}

	//Adds a ride and maintains alphabetical ordering by pickup location.
	public boolean addRide(IRide ride) {
		if (ride == null) {
			return false;
		}
		Node<IRide> newNode = new Node<IRide>(ride);
		if (size() == 0) {
			head = tail = newNode;
			size++;
			return true;
		}
		Node<IRide> current = head;
		Node<IRide> prev = null;
		while(ride.getPickupLocation().compareToIgnoreCase(current.getData().getPickupLocation()) >= 0 && current.getNext() != null) {
			prev = current;
			current = current.getNext();
		}
		// Check if pickup location comes alphabetically before to current node's pickup location.
		if (ride.getPickupLocation().compareToIgnoreCase(current.getData().getPickupLocation()) < 0) {
			if (current.equals(head)) {
				newNode.setNext(current);
				head = newNode;
			} else {
				newNode.setNext(current);
				prev.setNext(newNode);
			}
		} else {
			// Reached end of list without finding anything that is alphabetically after the pickup location given.
			current.setNext(newNode);
			tail = newNode;
		}
		size++;
		return true;
	}

	//Removes a ride by its unique ride ID.
	public boolean removeRideById(int rideId) {
		if (size() == 0) {
			return false;
		}
		Node<IRide> current = head;
		Node<IRide> prev = null;
		while(current.getData().getRideId() != rideId && current.getNext() != null) {
			prev = current;
			current = current.getNext();
		}
		if (current.getData().getRideId() == rideId) {
			if (current.equals(head)) {
				head = tail = null;
			} else if (current.equals(tail)) {
				prev.setNext(null);
				tail = prev;
			} else {
				prev.setNext(current.getNext());
			}
			size--;
			return true;
		}
		return false;
	}

	//Returns all rides alphabetically ordered by pickup location.
	public LinkedList<IRide> getAllAlphabetically() {
		LinkedList<IRide> returnList = new LinkedList<IRide>();
		Node<IRide> current = head;
		// We add elements according to alphabetical order already, so all we need to do is return the list.
		while (current != null) {
			returnList.insertAtBack(current.getData());
			current = current.getNext();
		}
		return returnList;
	}

	//Returns all rides whose pickup location matches the given location.
	public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
		LinkedList<IRide> matchingList = new LinkedList<IRide>();
		Node<IRide> current = head;
		while(current != null) {
			if (current.getData().getPickupLocation().equals(pickupLocation)) {
				matchingList.insertAtBack(current.getData());
			}
			current = current.getNext();
		}
		return matchingList;
	}

	//Returns all rides that involve a rider with the given full name.
	public LinkedList<IRide> findByRiderName(String riderFullName) {
		LinkedList<IRide> matchingList = new LinkedList<IRide>();
		Node<IRide> current = head;
		while (current != null) {

			if (current.getData() instanceof PrivateRide) {

				if (((PrivateRide) current.getData()).getRider().getName().equals(riderFullName)) {
					matchingList.insertAtBack(current.getData());
				}
			
			} else if (current.getData() instanceof SharedRide) {

				LinkedList<IRider> participants = ((SharedRide)current.getData()).getParticipants();
				Node<IRider> currentRiderNode = participants.getHead();
				while (currentRiderNode != null)  {

					if (currentRiderNode.getData().getName().equals(riderFullName)) {
						matchingList.insertAtBack(current.getData());
						break;                                            // break from inner loop to prevent adding the same ride twice in the case of matching riders.
					}
					
					currentRiderNode = currentRiderNode.getNext();
					
				}
				
			}
			current = current.getNext();
		}
		return matchingList;
	}

	//return number of rides stored.
	public int size() {
		return size;
	}

}