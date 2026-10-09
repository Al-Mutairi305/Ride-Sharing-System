package ride;
import system.LinkedList;
import system.Node;

class RideList implements IRideList {

	private LinkedList<IRide> list;
	private int size;

	public RideList() {
		list = new LinkedList<IRide>();
		size = 0;
	}

	//Adds a ride and maintains alphabetical ordering by pickup location.
	public boolean addRide(IRide ride) {
		if (list.isEmpty()) {
			list.insertAtFront(ride);
			size++;
			return true;
		}
		Node<IRide> rideNode = new Node<IRide>(ride);
		Node<IRide> current = list.getHead();
		Node<IRide> prev = null;
		while(ride.getPickupLocation().compareToIgnoreCase(current.getData().getPickupLocation()) >= 0 && current.getNext() != null) {
			prev = current;
			current = current.getNext();
		}
		if (current.equals(list.getHead())) {
			if (ride.getPickupLocation().compareToIgnoreCase(current.getData().getPickupLocation()) < 0) {
				list.insertAtFront(ride);
			} else {
				list.insertAtBack(ride);
			}
		}
		if (!current.equals(list.getHead())) {
			if (ride.getPickupLocation().compareToIgnoreCase(current.getData().getPickupLocation()) < 0) {
				prev.setNext(rideNode);
				rideNode.setNext(current);
			} else {
				list.insertAtBack(ride);
			}
		}
		size++;
		return true;
	}

	//Removes a ride by its unique ride ID.
	public boolean removeRideById(int rideId) {
		if (list.isEmpty()) {
			return false;
		}
		Node<IRide> current = list.getHead();
		while(current.getData().getRideId() != rideId && current.getNext() != null) {
			current = current.getNext();
		}
		if (current.getNext() == null && current.getData().getRideId() != rideId) {
			return false;
		}
		list.remove(rideId);
		size--;
		return true;
	}

	//Returns all rides alphabetically ordered by pickup location.
	public LinkedList<IRide> getAllAlphabetically() {
		return list;
	}

	//Returns all rides whose pickup location matches the given location.
	public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
		LinkedList<IRide> matchingList = new LinkedList<IRide>();
		if (list.isEmpty()) {
			return matchingList;
		}
		Node<IRide> current = list.getHead();
		while(current.getNext() != null) {
			if (pickupLocation.equals(current.getData().getPickupLocation())) {
				matchingList.insertAtBack(current.getData());
			}
			current = current.getNext();
		}
		if (pickupLocation.equals(current.getData().getPickupLocation())) {
			matchingList.insertAtBack(current.getData());
		}
		return matchingList;
	}

	//Returns all rides that involve a rider with the given full name.
	public LinkedList<IRide> findByRiderName(String riderFullName) {
		LinkedList<IRide> matchingList = new LinkedList<IRide>();
		if (list.isEmpty()) {
			return matchingList;
		}
		Node<IRide> current = list.getHead();
		// TODO: Implement PrivateRide and SharedRide classes
		// if (current.getData() instanceof PrivateRide){
		// 	while(current.getNext() != null) {
		// 		if (riderFullName.equals(current.getData().getName())) {
		// 			matchingList.insertAtBack(current.getData());
		// 		}
		// 		current = current.getNext();
		// 	}
		// 	if (riderFullName.equals(current.getData().getName())) {
		// 		matchingList.insertAtBack(current.getData());
		// 	}
		// } else if (current.getData() instanceof SharedRide) {

		// }
		return matchingList;
	}

	//return number of rides stored.
	public int size() {
		return size;
	}
}