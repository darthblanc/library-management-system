package finalproject.entities;

import finalproject.datastructures.DSArrayList;

public class Book {
    private String bookID;
    private int bookQuantity;
    private int originalBookQuantity;
    private String bookName;
    private String author;
    private DSArrayList<String> categoryList;

    public Book(String bookID, int bookQuantity, int originalBookQuantity, String bookName, String author, DSArrayList<String> categoryList){
        this.bookID = bookID;
        this.bookQuantity = bookQuantity;
        this.originalBookQuantity = originalBookQuantity;
        this.bookName = bookName;
        this.author = author;
        this.categoryList = categoryList;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

   public int getOriginalBookQuantity() {
        return originalBookQuantity;
    }

    public void setOriginalBookQuantity(int originalBookQuantity) {
        this.originalBookQuantity = originalBookQuantity;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DSArrayList<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(DSArrayList<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString(){
        return "ID: "+bookID+"| Quantity: "+bookQuantity+"| Name: "+bookName+"| Author: "+author+"| Category List: "+categoryList;
    }
}
