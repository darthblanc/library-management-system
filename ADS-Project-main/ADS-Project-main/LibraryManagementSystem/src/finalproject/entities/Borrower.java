package finalproject.entities;

import finalproject.datastructures.DSArrayList;
import finalproject.datastructures.DSHashMap;

public class Borrower {

    private String borrowerID;
    private String borrowerFirstName;
    private String borrowerLastName;
    private String borrowerEmail;
    private String borrowerPassword;
    private DSArrayList<String> lendingID;

    public Borrower(String borrowerID, String borrowerFirstName, String borrowerLastName, String borrowerEmail, String borrowerPassword, DSArrayList<String> lendingID){
        this.borrowerID = borrowerID;
        this.borrowerFirstName = borrowerFirstName;
        this.borrowerLastName = borrowerLastName;
        this.borrowerEmail = borrowerEmail;
        this.borrowerPassword = borrowerPassword;
        this.lendingID = lendingID;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getBorrowerFirstName() {
        return borrowerFirstName;
    }

    public void setBorrowerFirstName(String borrowerFirstName) {
        this.borrowerFirstName = borrowerFirstName;
    }

    public String getBorrowerLastName() {
        return borrowerLastName;
    }

    public void setBorrowerLastName(String borrowerLastName) {
        this.borrowerLastName = borrowerLastName;
    }

    public String getBorrowerEmail() {
        return borrowerEmail;
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail = borrowerEmail;
    }

    public String getBorrowerPassword() {
        return borrowerPassword;
    }

    public void setBorrowerPassword(String borrowerPassword) {
        this.borrowerPassword = borrowerPassword;
    }

    public DSArrayList<String> getLendingID() {
        return lendingID;
    }

    public void setLendingID(DSArrayList<String> lendingID) {
        this.lendingID = lendingID;
    }

    @Override
    public String toString(){
        return "ID:"+borrowerID+" FName:"+borrowerFirstName+" LName:"+borrowerLastName+" Email:"+borrowerEmail+" Password:"+borrowerPassword + "LendingIDs: "+lendingID;
    }
}
