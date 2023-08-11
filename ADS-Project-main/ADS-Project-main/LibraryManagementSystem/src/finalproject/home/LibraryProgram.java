package finalproject.home;

import finalproject.datastructures.DSArrayList;
import finalproject.datastructures.*;
import finalproject.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

public class LibraryProgram {
    private static DSArrayList<Management> centralManager = new DSArrayList<>(); //holds data for manager entity
    private static DSHashMap<Borrower> allBorrowers = new DSHashMap<>(); // holds all borrower data
    private static DSHashMap<LendingInfo> allLendingInfo = new DSHashMap<>(); // holds lending information data
    private static DSArrayList<Book> allBooks = new DSArrayList<>(); // holds all books
    private static DSHashMap<Librarian> allLibrarians = new DSHashMap<>(); //holds all librarian data

    private static DSHashMap<DSArrayList<String>> allBorrowerActivityInfo = new DSHashMap<>(); // holds all borrower activity information

    public static void main(String[] args) throws Exception {
        mainMenu(); //directs to main menu
    }

    private static void mainMenu() throws Exception {
        LocalDateTime today = LocalDateTime.now(); // getting the today's date
        String key = today.getDayOfMonth() + "-" + today.getMonth() + "-" + today.getYear(); //generating a key for the day for borrower activity
        allBorrowerActivityInfo.put(key,new DSArrayList<>());
        loadBorrowerLog(); //load borrower activity info from file
        loadLendingInfo();//load lending info from file
        loadBooks();// load books from file
        loadBorrowers(); // load borrowers from file
        loadManager(); // load manager from file
        loadLibrarians(); //load librarian from file
        LibraryProgram libraryProgram = new LibraryProgram();
        System.out.println("Select your role: \n1 Management \n2 Librarian \n3 Borrower");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option==1) libraryProgram.management(0);
        else libraryProgram.start();
    }

    private static void loadLendingInfo() throws Exception { //read lending info file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LendingInfo.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                LendingInfo lendingInfo = new LendingInfo(arr[0],arr[1],arr[2],LocalDateTime.parse(arr[3]),LocalDateTime.parse(arr[4]));
                allLendingInfo.put(arr[0],lendingInfo);
            }
        }
        System.out.println("Lending Info Loaded...");
    }

    private static void loadLibrarians() throws IOException { // read librarian info file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LibrarianInfo.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                Librarian librarian = new Librarian(arr[0],arr[1],arr[2],arr[3],arr[4]);
                allLibrarians.put(arr[0]+arr[1],librarian);
            }
        }
        System.out.println("Librarian Info Loaded...");
    }

    private static void loadManager() throws Exception{ // read manager file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/ManagementInfo.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                Management management = new Management(arr[0],arr[1]);
                centralManager.add(management);
            }
        }
        System.out.println("Manager Info Loaded...");
    }

    private static void loadBorrowers() throws IOException { // read borrower file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/BorrowerInfo.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                DSArrayList<String> lending = new DSArrayList<>();
                for (int i = 5; i < arr.length; i++) {
                    lending.add(arr[i]);
                }
                Borrower borrower = new Borrower(arr[0],arr[1],arr[2],arr[3],arr[4],lending);
                allBorrowers.put(borrower.getBorrowerID()+borrower.getBorrowerPassword(),borrower);
            }
        }
        System.out.println("Borrower Info Loaded...");
    }

    private static void loadBooks() throws IOException { // read book file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LibraryBooks.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                DSArrayList<String> categoryList = new DSArrayList<>();
                for (int i = 5; i < arr.length; i++) {
                    categoryList.add(arr[i]);
                }
                Book book = new Book(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), arr[3],arr[4], categoryList);
                allBooks.add(book);
            }
        }
        System.out.println("All books loaded...");
    }

    private static void loadBorrowerLog() throws IOException { // read borrower activity file
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/BorrowerActivity.txt"))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                DSArrayList<String> borrowerIdList = new DSArrayList<>();
                for (int i = 1; i < arr.length; i++) {
                    borrowerIdList.add(arr[i]);
                }
                allBorrowerActivityInfo.put(arr[0],borrowerIdList);
            }
        }
    }

    /**
     * @return bookID
     * **/
    private static String generateBookID() { // generate a new book Id
        return "RLSB000" + allBooks.length();
    }

    /**
     * @param i - checks number of attempts of sign-in to management
     * function validates user attempting to access management space
     * **/
    private void management(int i) throws Exception {
        if (i == 2) { // redirection if user cannot sign in 3 tries
            System.out.println("You are probably in the wrong place\n I will redirect you to another location");
            start();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a Username");
        String userName = scanner.next();
        System.out.println("Enter a password");
        String password = scanner.next();
        if ((centralManager.get(0).getUserName()+centralManager.get(0).getPassword()).equals(userName+password)) managementLobby();
        else management(i+1);
    }

    /**
     * lobby allows user to make choices between available menus
     * **/
    private void managementLobby() throws Exception {
        System.out.println("What do you want to do? \n1 Create Librarian Account \n2 Change Username \n3 Change Password\n4 Add Books \n5 Check Active Users\n0 Main Menu");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option==1) createLibrarianAccount(); // create a librarian account
        if (option==2) changeUsername(); // change username for management
        if (option==3) changePassword(); // change password for management
        if (option==4) addBooks(); // add new books to library
        if (option==5) checkActiveUsers(); // check activity of users
        if (option==0) mainMenu();
    }

    /**
     * check active users by year; month; day
     * **/
    private void checkActiveUsers() throws Exception {
        System.out.println("How would you want to check active users: ");
        System.out.println("1 By Year\n2 By Month\n3 By Day");
        Scanner scan = new Scanner(System.in);
        int option = scan.nextInt();
        if (option == 1) checkByYear(); //check user activity by year
        if (option == 2) checkByMonth();// check user activity by month
        if (option == 3) checkByDay(); // check user activity by day
    }

    /**
     * finds the number of active users on a given day
     * **/
    private void checkByDay() throws Exception {
        System.out.println("Enter the day, month and year you are looking for:");
        System.out.println("Form should be DD-MM-YYYY");
        Scanner scan = new Scanner(System.in);
        String dayMonthAndYear = scan.next();
        int count = 0;
        int totalCount = 0;
        for (String b: allBorrowerActivityInfo) {
            if (b.equals(dayMonthAndYear)) count += allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Active Users: "+ count);
        for (String b: allBorrowerActivityInfo) {
            totalCount += allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Percentage of Active Users: " + count/totalCount*100+"%");
        managementLobby();
    }

    /**
     * finds the  number of active users in a given month in a particular year
     * **/
    private void checkByMonth() throws Exception {
        System.out.println("Enter the month and year you are looking for:");
        System.out.println("Form should be Month-Year");
        Scanner scan = new Scanner(System.in);
        String monthAndYear = scan.next();
        int count = 0;
        int totalCount = 0;
        for (String b: allBorrowerActivityInfo) {
            if (b.substring(3).equals(monthAndYear)) count += allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Active Users: " +count);
        for (String b: allBorrowerActivityInfo) {
            totalCount += allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Percentage of Active Users: " + count/totalCount*100+"%");
        managementLobby();
    }

    /***
     * finds the number of active users in a given year
     * */
    private void checkByYear() throws Exception {
        System.out.println("Enter the year you are looking for: ");
        Scanner scan = new Scanner(System.in);
        String year = scan.next();
        int count = 0;
        int totalCount = 0;
        for (String b: allBorrowerActivityInfo) {
            if (b.substring(b.length()-4).equals(year)) count+=allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Active Users: " +count);
        for (String b: allBorrowerActivityInfo) {
            totalCount += allBorrowerActivityInfo.get(b).length;
        }
        System.out.println("Percentage of Active Users: " + count/totalCount*100 +"%");
        managementLobby();
    }

    /**
     * allows addition of new books
     * **/
    private void addBooks() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the quantity of the book: ");
        int bookQuantity = scanner.nextInt();
        System.out.println("Enter the book name: ");
        String bookName = scanner.next();
        System.out.println("Enter the book author: ");
        String bookAuthor = scanner.next();
        System.out.println("Enter the number of categories of the book: ");
        int numCategories = scanner.nextInt();
        System.out.println("Enter the categories of the book: ");
        DSArrayList<String> categoryList = new DSArrayList<>();
        for (int i = 0; i < numCategories; i++) {
            categoryList.add(scanner.next());
        }
        Book book = new Book(generateBookID(),bookQuantity,bookQuantity,bookName,bookAuthor,categoryList);
        for (Book b: allBooks) {
            if (b.getBookName().equals(book.getBookName()) && b.getAuthor().equals(book.getAuthor())) {
                System.out.println("This book already exists");
                System.out.println("Try another book");
                addBooks();
            }
        }
        allBooks.add(book);
        saveBook();
        managementLobby();
    }

    /**
     * saves all the books at run time to the library books file
     * **/
    private void saveBook() throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (Book s:allBooks) {
            toBeWritten.append(s.getBookID()).append(" ").append(s.getBookQuantity()).append(" ").append(s.getBookQuantity()).append(" ").append(s.getBookName()).append(" ").append(s.getAuthor()).append(" ").append(stringify(s.getCategoryList())).append("\n");
        }
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LibraryBooks.txt"), toBeWritten.toString());
    }

    /**
     * @param categoryList
     * @return rv
     * produces a string from a DSArrayList
     * **/
    private String loadCategories(DSArrayList<String> categoryList) {
        StringBuilder rv = new StringBuilder();
        for (String s: categoryList) {
            rv.append(s).append(" ");
        }
        return rv.toString();
    }

    /**
     * allows change of password
     * **/
    private void changePassword() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the initial password");
        String initialPassword = scanner.next();
        System.out.println("Confirm the initial password");
        String confirmPassword = scanner.next();
        if (confirmPassword.equals(initialPassword) && confirmPassword.equals(centralManager.get(0).getPassword())) {
            System.out.println("Input your new password");
            String newPassword = scanner.next();
            centralManager.get(0).setPassword(newPassword);
            String toBeWritten = centralManager.get(0).getUserName() + " "+ centralManager.get(0).getPassword();
            Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/ManagementInfo.txt"), toBeWritten);
        }
        else {
            System.out.println("Invalid Password");
            changePassword();
        }
    }

    /**
     * allows change of username
     * **/
    private void changeUsername() throws IOException {
        System.out.println("Enter your new username");
        Scanner scanner = new Scanner(System.in);
        String newUserName = scanner.next();
        centralManager.get(0).setUserName(newUserName);
        String toBeWritten = centralManager.get(0).getUserName() + " "+ centralManager.get(0).getPassword();
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/ManagementInfo.txt"), toBeWritten);
    }

    /**
     * creates new librarian account
     * **/
    private void createLibrarianAccount() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String Id = generateLibrarianId();
        System.out.println("Enter your first name: ");
        String firstName = scanner.next();
        System.out.println("Enter your last name: ");
        String lastName = scanner.next();
        String email = createEmail(lastName,Id);
        String pin = createPin();
        Librarian librarianDetails = new Librarian(Id,pin, firstName,lastName,email);
        saveLibrarianAccount(librarianDetails);
        allLibrarians.put(librarianDetails.getLibrarianID()+librarianDetails.getLibrarianPin(),librarianDetails);
        managementLobby();
    }

    /**
     * save all librarian accounts to librarianInfo file
     * **/
    private void saveLibrarianAccount(Librarian librarianDetails) throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (String s:allLibrarians) {
            toBeWritten.append(allLibrarians.get(s).getLibrarianID()).append(" ").append(allLibrarians.get(s).getLibrarianPin()).append(" ").append(allLibrarians.get(s).getLibrarianFirstName()).append(" ").append(allLibrarians.get(s).getLibrarianLastName()).append(" ").append(allLibrarians.get(s).getLibrarianEmail()).append("\n");
        }
        toBeWritten.append(librarianDetails.getLibrarianID()).append(" ").append(librarianDetails.getLibrarianPin()).append(" ").append(librarianDetails.getLibrarianFirstName()).append(" ").append(librarianDetails.getLibrarianLastName()).append(" ").append(librarianDetails.getLibrarianEmail());
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LibrarianInfo.txt"), toBeWritten.toString());
    }

    /**
     * @return String
     * facilitates creation of pin
     * under given constraints
     * **/
    private String createPin() {
        System.out.println("Enter a pin: ");
        Scanner scanner = new Scanner(System.in);
        String pin = scanner.next();
        if (pin.length()>3) return pin;
        System.out.println("Invalid Pin");
        return createPin();
    }

    /**
     * creates a new librarianId for a new Librarian
     * **/
    private String generateLibrarianId() {
        return "RLS000"+allLibrarians.size();
    }

    /**
     * offers a bunch of menus useful to user
     * **/
    private void start() throws Exception {
        System.out.println("""
                Hello, I am De Bruyne how may I assist you
                1 Create Borrower Account
                2 Access Borrower Space
                3 Access Librarian Space
                4 Search Books
                0 Main Menu""");
        Scanner scan = new Scanner(System.in);
        int option = scan.nextInt();
        if (option==1) createBorrowerAccount();
        if (option==2) accessBorrowerSpace();
        if (option==3) accessLibrarianSpace();
        if (option==4) findBooks();
        if (option==0) mainMenu();
        else {
            start();
        }
    }

    /**
     * verifies a user trying to access the librarian space
     * **/
    private void accessLibrarianSpace() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Id: ");
        String Id = scanner.next();
        System.out.println("Enter your pin: ");
        String pin = scanner.next();
        if (allLibrarians.containsKey(Id+pin)) librarianSpace(Id+pin);
        else accessLibrarianSpace();
    }


    /**
     * displays menus available to librarians
     * **/
    private void librarianSpace(String LibDet) throws Exception {
        System.out.println("What would you like to do today: \n1 Find books\n2 Verify Returned Book\n0 Previous page");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option==1) findBooks();
        if (option==2) verifyReturnedBook(LibDet);
        if (option==0) accessLibrarianSpace();
        start();
    }

    /**
     * verifies if user is a borrower
     * **/
    private void accessBorrowerSpace() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Id: ");
        String Id = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        if (!allBorrowers.containsKey(Id+password)) {
            throw new Exception("Invalid Id or Password");
        }
        choiceHandler(Id+password);
    }

    /**
     * displays and allows selection of menus available to borrowers
     * **/
    private void choiceHandler(String s) throws Exception {
        LocalDateTime today = LocalDateTime.now();
        String key = today.getDayOfMonth() + "-" + today.getMonth() + "-" + today.getYear();
        if (!allBorrowerActivityInfo.get(key).contains(allBorrowers.get(s).getBorrowerID())) logBorrowerActivity(s);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Access:\n1 Edit my profile \n2 View my borrowing history\n3 Borrow books\n4 Find Books\n0 Home Page");
        int option = scanner.nextInt();
        if (option==1) editProfile(s);
        if (option==2) viewBorrowHistory(s);
        if (option==3) borrowBooks(s);
        if (option==4) findBooks();
        if (option==0) start();
        else choiceHandler(s);
    }

    /**
     * @param s
     * saves borrower activity
     * **/
    private void logBorrowerActivity(String s) throws IOException {
        LocalDateTime today = LocalDateTime.now();
        String key = today.getDayOfMonth() + "-" + today.getMonth() + "-" + today.getYear();
        String borrowerID = allBorrowers.get(s).getBorrowerID();
        allBorrowerActivityInfo.get(key).add(borrowerID);
        saveBorrowerActivity();
    }

    /**
     * saves borrower activities to a file
     * @throws IOException
     */
    private void saveBorrowerActivity() throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (String key:allBorrowerActivityInfo) {
            toBeWritten.append(key).append(" ").append(allBorrowersInKey(allBorrowerActivityInfo.get(key))).append("\n");
        }
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/BorrowerActivity.txt"), toBeWritten.toString());
    }

    /**
     *
     * @param borrowerActivityInfo
     * @return
     * produces a string from a DSArrayList
     */
    private String allBorrowersInKey(DSArrayList<String> borrowerActivityInfo) {
        StringBuilder allBorrowersOnDay = new StringBuilder();
        for (String s: borrowerActivityInfo) {
            allBorrowersOnDay.append(s).append(" ");
        }
        return allBorrowersOnDay.toString();
    }

    /**
     * allows user to find books via various methods
     * @throws Exception
     */
    private void findBooks() throws Exception {
        System.out.println("Select your preferred search option: ");
        System.out.println("1 Book Reference\n2 Author Name\n3 Book Name\n4 Availability\n5 Genre");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option==1) System.out.println(findBookByRef());
        if (option==2) System.out.println(findBookByAuthor());
        if (option==3) System.out.println(findBookByName());
        if (option==4) System.out.println(findBookByAvailability());
        if (option==5) showAllBooks((findBookByGenre()));
        start();
    }

    /**
     * @param bookByGenre
     * displays all books from a certain genre
     */
    private void showAllBooks(DSArrayList<Book> bookByGenre) {
        for (Book b:bookByGenre) {
            System.out.println(b);
        }
    }

    /**
     * finds book by genre
     * @return DSArrayList of books
     */
    private DSArrayList<Book> findBookByGenre() {
        DSArrayList<Book> local = new DSArrayList<>();
        System.out.println("Please enter the genre of the book you seek");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        for (Book b: allBooks) {
            if (b.getCategoryList().contains(option.toLowerCase())) local.add(b);
        }
        return local;
    }

    /**
     * finds books by availability
     * @return DSArrayList of Books
     */
    private DSArrayList<Book> findBookByAvailability() {
        System.out.println("Would you want to check for:\n1 Available Books\n2 Unavailable Books");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        if (option==1) return findAvailableBooks();
        if (option==2) return findUnavailableBooks();
        System.out.println("Invalid Entry");
        return findBookByAvailability();
    }

    /**
     * finds all unavailable books
     * @return DSArrayList of books
     */
    private DSArrayList<Book> findUnavailableBooks() {
        DSArrayList<Book> local = new DSArrayList<>();
        for (Book b: allBooks) {
            if (b.getBookQuantity()>0) local.add(b);
        }
        if (local.length>0) return local;
        System.out.println("All books are available");
        return findBookByAvailability();
    }

    /**
     * finds all available books
     * @return DSArrayList of books
     */
    private DSArrayList<Book> findAvailableBooks() {
        DSArrayList<Book> local = new DSArrayList<>();
        for (Book b: allBooks) {
            if (b.getBookQuantity()==0) local.add(b);
        }
        if (local.length>0) return local;
        System.out.println("All books are unavailable");
        return findBookByAvailability();
    }

    /**
     * finds all books with a similar name
     * @return DSArrayList of books
     */
    private DSArrayList<Book> findBookByName() {
        DSArrayList<Book> local = new DSArrayList<>();
        System.out.println("Please enter the name of the book you seek");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        for (Book b: allBooks) {
            if (b.getBookName().contains(option)) local.add(b);
        }
        if (local.length>0) return local;
        System.out.println("Book does not exist in our directory");
        return findBookByName();
    }

    /**
     * finds all books written by an author
     * @return DSArrayList of books
     */
    private DSArrayList<Book> findBookByAuthor() {
        DSArrayList<Book> local = new DSArrayList<>();
        System.out.println("Please enter the Author you seek");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        for (Book b: allBooks) {
            if (b.getAuthor().equals(option)) local.add(b);
        }
        if (local.length>0) return local;
        System.out.println("We do not have a record of such authors");
        return findBookByAuthor();
    }

    /**
     * finds books by reference
     * @return a book
     */
    private Book findBookByRef() {
        System.out.println("Please enter the reference of the book you seek");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        for (Book b: allBooks) {
            if (b.getBookID().equals(option)) return b;
        }
        System.out.println("Book not found\n Try again");
        return findBookByRef();
    }

    /**
     * finds book using reference by utilising reference in parameter
     * @param ref
     * @return
     */
    private Book findBookByRef(String ref) {
        for (Book b: allBooks) {
            if (b.getBookID().equals(ref)) return b;
        }
        System.out.println("Book not found\n Try again");
        return findBookByRef();
    }

    /**
     * produces a list of books
     * then redirects user to select books
     * @param s
     * @throws Exception
     */
    private void borrowBooks(String s) throws Exception {
        System.out.println("To borrow a book, type the corresponding book reference");
        for (Book p: allBooks) {
            System.out.println(p.getBookID() + " "+ p.getBookName()+" "+p.getAuthor());
        }
        selectBook(s);
    }

    /**
     * allows users to select books
     * does not allow if there are no books left
     * @param s
     * @throws Exception
     */
    private void selectBook(String s) throws Exception {
        Book book = findBookByRef();
        if (book.getBookQuantity()==0) {
            System.out.println("Book Unavailable");
            selectBook(s);
        }
        lendBook(s,book);
    }

    /**
     * verifies if a returned books is valid
     * @param libDet
     * @throws Exception
     */
    private void verifyReturnedBook(String libDet) throws Exception {
        Book book = findBookByRef();
        if (allBooks.contains(book))
            for (Book b: allBooks) {
                if (b.equals(book) && b.getBookQuantity() < b.getOriginalBookQuantity()) book.setBookQuantity(book.getBookQuantity()+1);
                else {
                    System.out.println("Invalid return\n We currently have all copies of the book ");
                    System.out.print(b.getBookQuantity());
                }
            }
        librarianSpace(libDet);
    }

    /**
     * saves all necessary information on a book once lent
     * @param s
     * @param book
     * @throws Exception
     */
    private void lendBook(String s, Book book) throws Exception {
        updateBookQuantity(s,book);
        String lendingRef = generateLendingRef();
        LocalDateTime today = LocalDateTime.now();
        LendingInfo lendingInfo = new LendingInfo(lendingRef,s.substring(0,8),book.getBookID(),today,expirationDate());
        allBorrowers.get(s).getLendingID().add(lendingRef);
        allLendingInfo.put(lendingInfo.getLendingReference(),lendingInfo);
        saveBorrowerAccount(allBorrowers.get(s));
        saveBookLend();
        saveLendingInfo();
        System.out.println("Book has been lent...");
        choiceHandler(s);
    }

    private void updateBookQuantity(String s, Book book) {
        for (Book b:allBooks ) {
            if (b.equals(book)) b.setBookQuantity(book.getBookQuantity()-1);
        }
    }

    /**
     * saves lending info to the lending info file
     * @throws IOException
     */
    private void saveLendingInfo() throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (String l:allLendingInfo) {
            toBeWritten.append(allLendingInfo.get(l).getLendingReference()).append(" ").append(allLendingInfo.get(l).getBorrowerID()).append(" ").append(allLendingInfo.get(l).getBookID()).append(" ").append(allLendingInfo.get(l).getLendDate()).append(" ").append(allLendingInfo.get(l).getExpirationDate()).append("\n");
        }
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LendingInfo.txt"), toBeWritten.toString());
    }

    /**
     * updates book status of learnt book in a file
     * @throws IOException
     */
    private void saveBookLend() throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (Book s:allBooks) {
            toBeWritten.append(s.getBookID()).append(" ").append(s.getBookQuantity()).append(" ").append(s.getOriginalBookQuantity()).append(" ").append(s.getBookName()).append(" ").append(s.getAuthor()).append(" ").append(stringify(s.getCategoryList())).append("\n");
        }
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/LibraryBooks.txt"), toBeWritten.toString());
    }

    /**
     * creates a string from a DSArrayList
     * @param categoryList
     * @return
     */
    private String stringify(DSArrayList<String> categoryList) {
        String rv = "";
        for (String s:categoryList) {
            rv+= s + " ";
        }
        return rv;
    }

    /**
     * produces the expiration date of a book
     * @return LocalDateTime
     */
    private LocalDateTime expirationDate() {
        LocalDateTime today = LocalDateTime.now();
        return today.plusWeeks(2);
    }

    /**
     * produces a new lending reference for a transaction
     * @return lending reference
     */
    private String generateLendingRef() {
        return "RLL000" + allLendingInfo.size()+1;
    }

    /**
     * allows borrower to see their own borrowing history
     * @param s
     */
    private void viewBorrowHistory(String s) {
        Borrower borrower = allBorrowers.get(s);
        for (String l: borrower.getLendingID()) {
            Book book = findBookByRef(allLendingInfo.get(l).getBookID());
            System.out.println("Borrower Details: "+borrower.getBorrowerID()+" "+borrower.getBorrowerFirstName()+" "+borrower.getBorrowerLastName()+ "\n " +"Book Details "+ book.getBookID()+ " " +book.getBookName()+" "+ book.getAuthor()+ "\n Lend Date: "+ allLendingInfo.get(l).getLendDate().getDayOfMonth()+"-"+allLendingInfo.get(l).getLendDate().getMonth()+"-"+allLendingInfo.get(l).getLendDate().getYear()+"\n Expiry Date "+
                    allLendingInfo.get(l).getExpirationDate().getDayOfMonth()+"-"+allLendingInfo.get(l).getExpirationDate().getMonth()+"-"+allLendingInfo.get(l).getExpirationDate().getYear());
        }
    }

    /**
     * allows borrower to their edit profile
     * @param s
     * @throws Exception
     */
    private void editProfile(String s) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Here are your details: ");

        System.out.println("Id: " + allBorrowers.get(s).getBorrowerID());
        System.out.println("First name: " + allBorrowers.get(s).getBorrowerFirstName());
        System.out.println("Last name: " + allBorrowers.get(s).getBorrowerLastName());
        System.out.println("Email: " + allBorrowers.get(s).getBorrowerEmail());
        System.out.println("Password: " + allBorrowers.get(s).getBorrowerPassword());

        System.out.println("Select what you would want to edit: \n1 Password");
        int option = scanner.nextInt();

        if (option==1) {
            allBorrowers.get(s).setBorrowerPassword(createPassword());
        }
    }

    /**
     * allows creation of new borrower accounts
     * @throws Exception
     */
    private void createBorrowerAccount() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your First Name");
        String firstName = scanner.next();
        System.out.println("Enter your Last name");
        String lastName = scanner.next();
        String Id = generateBorrowerId();
        String email = createEmail(lastName,Id);
        String password = createPassword();

        Borrower borrowerDetails = new Borrower(Id,firstName,lastName,email,password,new DSArrayList<>());
        allBorrowers.put(Id+password, borrowerDetails);
        saveBorrowerAccount(allBorrowers.get(Id+password));
        System.out.println("Here are your details: "+ allBorrowers.get(Id+password));
    }

    /**
     * saves borrower accounts to borrower info file
     * @param borrower
     * @throws IOException
     */
    private void saveBorrowerAccount(Borrower borrower) throws IOException {
        StringBuilder toBeWritten = new StringBuilder();
        for (String s:allBorrowers) {
            toBeWritten.append(allBorrowers.get(s).getBorrowerID()).append(" ").append(allBorrowers.get(s).getBorrowerFirstName()).append(" ").append(allBorrowers.get(s).getBorrowerLastName()).append(" ").append(allBorrowers.get(s).getBorrowerEmail()).append(" ").append(allBorrowers.get(s).getBorrowerPassword()).append(" ").append(lenderIDs(borrower)).append("\n");
        }
        Files.writeString(Path.of("C:/Users/Andi/OneDrive/Desktop/LibraryManagementSystem/BorrowerInfo.txt"), toBeWritten.toString());
    }

    /**
     * adds lenderIds to the borrower
     * useful to keep track of their borrowing history
     * @param borrower
     * @return
     */
    private StringBuilder lenderIDs(Borrower borrower) {
        StringBuilder toBeWritten = new StringBuilder();
        for (int i = 0; i < borrower.getLendingID().length; i++) {
            toBeWritten.append(borrower.getLendingID().get(i)).append(" ");
        }
        return toBeWritten;
    }

    /**
     * allows borrower to create a password
     * @return password
     * @throws Exception
     */
    private String createPassword() throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your password\nA valid password must have: \nat least 5 characters");
        String password = scanner.next();
        if (checkIfPasswordValid(password)) {
            throw new Exception("Invalid password");
        }
        return password;
    }

    /**
     * checks if a password is of valid length
     * @param password
     * @return
     */
    private Boolean checkIfPasswordValid(String password) {
        return password.length()<5;
    }

    /**
     * creates an email for the user
     * @param lastName
     * @param Id
     * @return
     */
    private String createEmail(String lastName, String Id) {
        return lastName+Id.substring(0,2)+Id.substring(Id.length()-3)+"@randomLibrary.lib";
    }

    /**
     * generate a borrower Id for a new borrower
     * @return
     */
    private String generateBorrowerId() {
        return "RLB000" + allBorrowers.size()+1;
    }
}