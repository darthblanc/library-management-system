package finalproject.entities;

import finalproject.datastructures.DSArrayList;

import java.time.LocalDateTime;

public class BorrowerActivityInfo {
    private DSArrayList<String> borrowerID;

    public BorrowerActivityInfo(DSArrayList<String> borrowerID){
        this.borrowerID =borrowerID;
    }

    public DSArrayList<String> getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(String borrowerName) {
        this.borrowerID = borrowerID;
    }
}
