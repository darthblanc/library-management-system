package finalproject.entities;

import java.time.LocalDateTime;
import java.util.Date;

public class LendingInfo {

    private String lendingReference;
    private String borrowerID;
    private String bookID;
    private LocalDateTime lendDate;
    private LocalDateTime expirationDate;

    public LendingInfo(String lendingReference, String borrowerID, String bookID, LocalDateTime lendDate, LocalDateTime expirationDate){
        this.lendingReference = lendingReference;
        this.borrowerID = borrowerID;
        this.bookID = bookID;
        //this.librarian = librarian;
        this.lendDate = lendDate;
        this.expirationDate = expirationDate;
    }

    public String getLendingReference() {
        return lendingReference;
    }

    public void setLendingReference(String lendingReference) {
        this.lendingReference = lendingReference;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBook(String book) {
        this.bookID = bookID;
    }

    //    public Librarian getLibrarian() {
//        return librarian;
//    }

//    public void setLibrarian(Librarian librarian) {
//        this.librarian = librarian;
//    }

    public LocalDateTime getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDateTime lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString(){
        return " "+lendingReference+" "+borrowerID+" "+bookID+"  "+lendDate+" "+expirationDate;
    }
}
