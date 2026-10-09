package rider;
import ride.IRide;
import system.LinkedList;
import system.Person;

public class Rider extends Person implements IRider {

    private String email;
    private String homeCity;

    public Rider(String name, String phoneNumber, int id, String email, String homeCity) {
        super(name, phoneNumber, id);
        this.email = email;
        this.homeCity = homeCity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public int compareTo(IRider other) {
        if (id < other.getId())
            return -1;
        else if (id > other.getId())
            return 1;
        else
            return 0;
    }

    public LinkedList<IRide> getRideHistory() {
        return null;
    }


}
