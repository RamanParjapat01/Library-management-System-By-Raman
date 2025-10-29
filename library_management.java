import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Student {
    String name;
    int id_no;
    String stream;
    String book1, book2;
    Date dueDate1, dueDate2; // Separate due dates for each book
    int book_no;

    Student(String name, int id_no, String stream) {
        this.name = name;
        this.id_no = id_no;
        this.stream = stream;
        this.book1 = null;
        this.book2 = null;
        this.dueDate1 = null;
        this.dueDate2 = null;
        this.book_no = 0;
    }
}

public class library_management {
    static class Node {
        String key;
        Node left, right;

        public Node(String item) {
            key = item;
            left = null;
            right = null;
        }
    }

    Node root;
    private static Scanner input = new Scanner(System.in);

    library_management() {
        root = null;
    }

    // Insert Book
    void insert(String key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key.compareToIgnoreCase(root.key) < 0)
            root.left = insertRec(root.left, key);
        else if (key.compareToIgnoreCase(root.key) > 0)
            root.right = insertRec(root.right, key);
        else
            System.out.println("Book already exists.");

        return root;
    }

    // Search Book
    public boolean containsNode(String value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, String key) {
        if (current == null) {
            return false;
        }
        if (key.equalsIgnoreCase(current.key)) {
            return true;
        }
        return key.compareToIgnoreCase(current.key) < 0
                ? containsNodeRecursive(current.left, key)
                : containsNodeRecursive(current.right, key);
    }

    // Print tree in 2D
    void printTree() {
        printTreeRec(root, 0);
    }

    void printTreeRec(Node t, int space) {
        if (t == null) return;

        space += 5;
        printTreeRec(t.right, space);
        System.out.println();
        for (int i = 5; i < space; i++) System.out.print(" ");
        System.out.print("[" + t.key + "]");
        printTreeRec(t.left, space);
    }

    void deleteKey(String key) {
        root = deleteRec(root, key);
    }

    Node deleteRec(Node root, String key) {
        if (root == null) return root;

        if (key.compareToIgnoreCase(root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (key.compareToIgnoreCase(root.key) > 0)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    String minValue(Node root) {
        String minv = root.key;
        while (root.left != null) {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    // Print Books Inorder
    void printInorder() {
        printInorderRec(root);
    }

    void printInorderRec(Node node) {
        if (node == null) return;
        printInorderRec(node.left);
        System.out.print(node.key + " ");
        printInorderRec(node.right);
    }

    public static void main(String[] args) {
        library_management tree = new library_management();
        HashMap<String, Integer> hashmapping = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Student[] array = new Student[3];
        array[0] = new Student("Rajvi", 1741078, "B.Tech-ICT");
        array[1] = new Student("Krushna", 1741086, "B.Tech-ICT");
        array[2] = new Student("Kalagee", 1741052, "B.Tech-ICT");

        List<String> books = new ArrayList<>();
        List<Integer> totalQty = new ArrayList<>();
        List<Integer> availQty = new ArrayList<>();

        // Load data from files
        loadBooks(tree, hashmapping, books, totalQty, availQty);

        boolean running = true;
        while (running) {
            System.out.println("\n.....................................");
            System.out.println("1. Librarian Login.");
            System.out.println("2. User Login.");
            System.out.println("3. Exit.");
            System.out.println(".....................................");
            System.out.print("Enter your choice: ");
            int ch1 = getIntInput();

            switch (ch1) {
                case 1:
                    librarianLogin(tree, hashmapping, books, totalQty, availQty);
                    break;
                case 2:
                    userLogin(tree, hashmapping, array, availQty, formatter);
                    break;
                case 3:
                    running = false;
                    saveData(books, totalQty, availQty);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        input.close();
    }

    static void loadBooks(library_management tree, HashMap<String, Integer> hashmapping, List<String> books, List<Integer> totalQty, List<Integer> availQty) {
        try (BufferedReader brBooks = new BufferedReader(new FileReader("books.txt"));
             BufferedReader brTotal = new BufferedReader(new FileReader("total_qty.txt"));
             BufferedReader brAvail = new BufferedReader(new FileReader("avail_qty.txt"))) {
            String line;
            int i = 0;
            while ((line = brBooks.readLine()) != null) {
                tree.insert(line);
                hashmapping.put(line, i);
                books.add(line);
                i++;
            }
            for (int j = 0; j < i; j++) {
                totalQty.add(Integer.parseInt(brTotal.readLine()));
                availQty.add(Integer.parseInt(brAvail.readLine()));
            }
        } catch (IOException e) {
            System.out.println("No existing data found, starting fresh.");
        }
    }

    static void saveData(List<String> books, List<Integer> totalQty, List<Integer> availQty) {
        try (BufferedWriter bwBooks = new BufferedWriter(new FileWriter("books.txt"));
             BufferedWriter bwTotal = new BufferedWriter(new FileWriter("total_qty.txt"));
             BufferedWriter bwAvail = new BufferedWriter(new FileWriter("avail_qty.txt"))) {
            for (String book : books) bwBooks.write(book + "\n");
            for (Integer qty : totalQty) bwTotal.write(qty + "\n");
            for (Integer qty : availQty) bwAvail.write(qty + "\n");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    static void librarianLogin(library_management tree, HashMap<String, Integer> hashmapping, List<String> books, List<Integer> totalQty, List<Integer> availQty) {
        System.out.print("Enter UserId: ");
        String id = input.next();
        System.out.print("Enter Password: ");
        String pwd = input.next();

        if (!"dsa@1".equals(id) || !"abc123".equals(pwd)) {
            System.out.println("Invalid credentials.");
            return;
        }

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n.....................................");
            System.out.println("1. Add book.");
            System.out.println("2. Delete book.");
            System.out.println("3. Update book.");
            System.out.println("4. Print Books Details.");
            System.out.println("5. Print Books in-order.");
            System.out.println("6. Print tree.");
            System.out.println("7. Exit.");
            System.out.println(".....................................");
            System.out.print("Enter your choice: ");
            int ch = getIntInput();

            switch (ch) {
                case 1:
                    System.out.print("Enter name of book: ");
                    String name = input.next();
                    if (tree.containsNode(name)) {
                        System.out.println("Book already exists.");
                    } else {
                        System.out.print("Enter quantity: ");
                        int qty = getIntInput();
                        tree.insert(name);
                        hashmapping.put(name, books.size());
                        books.add(name);
                        totalQty.add(qty);
                        availQty.add(qty);
                    }
                    break;
                case 2:
                    System.out.print("Enter name of book: ");
                    String b1 = input.next();
                    if (tree.containsNode(b1)) {
                        tree.deleteKey(b1);
                        int idx = hashmapping.remove(b1);
                        books.remove(idx);
                        totalQty.remove(idx);
                        availQty.remove(idx);
                        // Reindex hashmap
                        hashmapping.clear();
                        for (int i = 0; i < books.size(); i++) hashmapping.put(books.get(i), i);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter name of book: ");
                    String b2 = input.next();
                    if (tree.containsNode(b2)) {
                        int idx = hashmapping.get(b2);
                        System.out.print("Enter additional quantity: ");
                        int q = getIntInput();
                        totalQty.set(idx, totalQty.get(idx) + q);
                        availQty.set(idx, availQty.get(idx) + q);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    for (int i = 0; i < books.size(); i++) {
                        System.out.println("Book: " + books.get(i) + ", Total: " + totalQty.get(i) + ", Available: " + availQty.get(i));
                    }
                    break;
                case 5:
                    tree.printInorder();
                    System.out.println();
                    break;
                case 6:
                    tree.printTree();
                    break;
                case 7:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void userLogin(library_management tree, HashMap<String, Integer> hashmapping, Student[] array, List<Integer> availQty, SimpleDateFormat formatter) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n.....................................");
            System.out.println("1. Issue book.");
            System.out.println("2. Return book.");
            System.out.println("3. Exit.");
            System.out.println(".....................................");
            System.out.print("Enter your choice: ");
            int ch = getIntInput();

            switch (ch) {
                case 1:
                    System.out.print("Enter your ID: ");
                    int id = getIntInput();
                    int index = -1;
                    for (int k = 0; k < array.length; k++) {
                        if (array[k].id_no == id) index = k;
                    }
                    if (index == -1) {
                        System.out.println("Invalid ID.");
                        break;
                    }
                    if (array[index].book_no >= 2) {
                        System.out.println("You can't issue more than two books.");
                        break;
                    }
                    System.out.print("Enter book name: ");
                    String book = input.next();
                    if (!tree.containsNode(book)) {
                        System.out.println("Book not available.");
                        break;
                    }
                    int idx = hashmapping.get(book);
                    if (availQty.get(idx) <= 0) {
                        System.out.println("Book not available.");
                        break;
                    }
                    availQty.set(idx, availQty.get(idx) - 1);
                    if (array[index].book1 == null) {
                        array[index].book1 = book;
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_MONTH, 7);
                        array[index].dueDate1 = cal.getTime();
                    } else {
                        array[index].book2 = book;
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_MONTH, 7);
                        array[index].dueDate2 = cal.getTime();
                    }
                    array[index].book_no++;
                    System.out.println("Book issued. Due: " + formatter.format(array[index].dueDate1 != null ? array[index].dueDate1 : array[index].dueDate2));
                    break;
                case 2:
                    System.out.print("Enter your ID: ");
                    int sid = getIntInput();
                    int ind = -1;
                    for (int k = 0; k < array.length; k++) {
                        if (array[k].id_no == sid) ind = k;
                    }
                    if (ind == -1) {
                        System.out.println("Invalid ID.");
                        break;
                    }
                    System.out.print("Enter book name: ");
                    String rbook = input.next();
                    boolean issued = (array[ind].book1 != null && array[ind].book1.equalsIgnoreCase(rbook)) ||
                                     (array[ind].book2 != null && array[ind].book2.equalsIgnoreCase(rbook));
                    if (!issued) {
                        System.out.println("Book not issued to you.");
                        break;
                    }
                    Date dueDate = array[ind].book1 != null && array[ind].book1.equalsIgnoreCase(rbook) ? array[ind].dueDate1 : array[ind].dueDate2;
                    Date now = new Date();
                    if (now.after(dueDate)) {
                        long diff = now.getTime() - dueDate.getTime();
                        int days = (int) (diff / (1000 * 60 * 60 * 24));
                        double fine = days * 5.0;
                        System.out.println("Overdue by " + days + " days. Fine: " + fine + " Rs.");
                    } else {
                        System.out.println("Book returned successfully.");
                    }
                    int ridx = hashmapping.get(rbook);
                    availQty.set(ridx, availQty.get(ridx) + 1);
                    if (array[ind].book1 != null && array[ind].book1.equalsIgnoreCase(rbook)) {
                        array[ind].book1 = null;
                        array[ind].dueDate1 = null;
                    } else {
                        array[ind].book2 = null;
                        array[ind].dueDate2 = null;
                    }
                    array[ind].book_no--;
                    break;
                case 3:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static int getIntInput() {
        while (!input.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            input.next();
        }
        return input.nextInt();
    }
}
