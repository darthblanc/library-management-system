package finalproject.entities;

public class Librarian {

    private String librarianID;
    private String librarianPin;
    private String librarianFirstName;
    private String librarianLastName;
    private String librarianEmail;

    public Librarian(String librarianID, String librarianPin, String librarianFirstName, String librarianLastName, String librarianEmail){
        this.librarianID = librarianID;
        this.librarianPin = librarianPin;
        this.librarianFirstName = librarianFirstName;
        this.librarianLastName = librarianLastName;
        this.librarianEmail = librarianEmail;
    }

    public String getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(String librarianID) {
        this.librarianID = librarianID;
    }

    public String getLibrarianPin() {
        return librarianPin;
    }

    public void setLibrarianPin(String librarianPin) {
        this.librarianPin = librarianPin;
    }

    public String getLibrarianFirstName() {
        return librarianFirstName;
    }

    public void setLibrarianFirstName(String librarianFirstName) {
        this.librarianFirstName = librarianFirstName;
    }

    public String getLibrarianLastName() {
        return librarianLastName;
    }

    public void setLibrarianLastName(String librarianLastName) {
        this.librarianLastName = librarianLastName;
    }

    public String getLibrarianEmail() {
        return librarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }
}
