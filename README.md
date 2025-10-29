# Library-management-System-By-Raman
Library Management System User Guide
Welcome to the Library Management System! This guide will help you understand and use the system effectively. The system is built in Java and allows librarians to manage books and users to issue/return books.

Prerequisites
Java Development Kit (JDK): Ensure you have JDK 8 or higher installed on your system. You can download it from Oracle's website or use OpenJDK.
Text Editor or IDE: Use any text editor (e.g., Notepad++, VS Code) or IDE (e.g., Eclipse, IntelliJ IDEA) to edit and compile the code.
Operating System: The code runs on Windows, macOS, or Linux.
How to Compile and Run the Program
Download the Code: Save the provided Java code as library_management.java in a folder (e.g., C:\LibrarySystem or /home/user/LibrarySystem).

Compile the Code:

Open a command prompt or terminal.
Navigate to the folder where library_management.java is saved.
Run the command: javac library_management.java
This will generate library_management.class and other class files.
Run the Program:

In the same terminal/command prompt, run: java library_management
The program will start, and you'll see the main menu.
Data Files: The program uses three text files for persistence:

books.txt: Stores book names.
total_qty.txt: Stores total quantities.
avail_qty.txt: Stores available quantities.
These files are created automatically if they don't exist. Do not delete them manually while the program is running, as it may cause data loss.
Main Menu
After running the program, you'll see:


Copy code
.....................................
1. Librarian Login.
2. User Login.
3. Exit.
.....................................
Enter your choice:
Choose 1 for librarian functions.
Choose 2 for user functions.
Choose 3 to exit and save data.
Librarian Login
Credentials: User ID: dsa@1, Password: abc123.
Enter the ID and password when prompted.
If correct, you'll access the librarian menu.
Librarian Menu Options
Add Book:

Enter the book name (case-insensitive).
Enter the quantity.
The book is added to the system if it doesn't exist.
Delete Book:

Enter the book name.
The book is removed from the system if it exists.
Update Book:

Enter the book name.
Enter the additional quantity to add.
Increases total and available quantities.
Print Books Details:

Displays all books with their total and available quantities.
Print Books in-order:

Prints book names in alphabetical order.
Print Tree:

Displays the binary search tree structure of books.
Exit:

Returns to the main menu.
User Login
No login credentials required; directly access user functions.
Users are predefined: Rajvi (ID: 1741078), Krushna (ID: 1741086), Kalagee (ID: 1741052).
User Menu Options
Issue Book:

Enter your student ID.
Enter the book name.
If available and you have less than 2 books issued, the book is issued for 7 days.
Displays current date/time and due date.
Return Book:

Enter your student ID.
Enter the book name.
If overdue, calculates and displays fine (5 Rs. per day).
Book is returned, and availability increases.
Exit:

Returns to the main menu.
Rules for Users
Each user can issue up to 2 books.
Books are issued for 7 days.
Overdue returns incur a fine of 5 Rs. per day.
Book names are case-insensitive.
File Handling and Data Persistence
Data is saved automatically when you exit the program (option 3 in main menu).
If files are missing, the system starts with an empty library.
Do not edit the text files manually, as it may corrupt data.
Troubleshooting
Compilation Errors: Ensure JDK is installed and javac is in your PATH. Check for syntax errors in the code.
Runtime Errors: If files are missing, the program handles it gracefully. For input errors, it prompts for valid input.
Book Not Found: Ensure the book name is spelled correctly (case-insensitive).
Invalid ID: Use one of the predefined student IDs.
Program Crashes: Close and restart. If persistent, check Java version.
Data Loss: Always exit properly to save data.
Notes
The system uses a binary search tree for efficient book searches.
Dates are handled using Java's Calendar and Date classes.
For any issues, ensure you have write permissions in the folder.
This is a console-based application; no GUI is provided.
