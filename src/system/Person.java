package system;
public abstract class Person implements IPerson {

    protected String name;
    protected String phoneNumber;
    protected int id;

    public Person(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber.length() != 10)
            throw new IllegalArgumentException("Invalid phone number!");

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (Character.isDigit(phoneNumber.charAt(i)))
                continue;
            else
                throw new IllegalArgumentException("Invalid phone number!");
        }

        this.phoneNumber = phoneNumber;
    }

    
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Phone Number: " + phoneNumber;
    }

}
