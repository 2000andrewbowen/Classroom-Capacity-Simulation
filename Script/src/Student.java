public class Student {

    public enum Communities{
       JOHNPAUL,
       MOTHERTERESA,
       JUANDIEGO,
       CATHERINEOFSIENA,
       PADREPIO
    }

    private String name;
    private String lastName;
    private Communities community;
    private boolean online;
    private boolean inperson;
    private boolean alwaysInClass;

    public Student(String name,String lastName, String communityName){
        this.name = name;
        this.lastName = lastName;
        this.online = false;
        this.inperson = false;
        this.alwaysInClass = false;

        switch (communityName){
            case "John Paul II":
                this.community = Communities.JOHNPAUL;
                break;
            case "Mother Teresa":
                this.community = Communities.MOTHERTERESA;
                break;
            case "Juan Diego":
                this.community = Communities.JUANDIEGO;
                break;
            case "Catherine of Siena":
                this.community = Communities.CATHERINEOFSIENA;
                break;
            case "Padre Pio":
                this.community = Communities.PADREPIO;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public Communities getCommunity() {
        return community;
    }

    public String getLastName() {
        return lastName;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isInperson() {
        return inperson;
    }

    public void setInperson(boolean inperson) {
        this.inperson = inperson;
    }


    public boolean isAlwaysInClass() {
        return alwaysInClass;
    }

    public void setAlwaysInClass(boolean alwaysInClass) {
        this.alwaysInClass = alwaysInClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", community=" + community.toString() +
                '}';
    }
}
