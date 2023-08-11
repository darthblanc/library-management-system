package finalproject.entities;

import java.time.LocalDateTime;

public class LibrarianActivityInfo {
    private String librarianID;

    public LibrarianActivityInfo(String librarianID){
        this.librarianID =librarianID;
    }

    public String getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(String librarianID) {
        this.librarianID = librarianID;
    }

}
