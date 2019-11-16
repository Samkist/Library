package me.Samkist.Library;

import BreezySwing.GBFrame;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;

import java.util.Calendar;

public class LibraryGUI extends GBFrame {

    public static void main(String[] args) {
        Library library = new Library();
        for (int i = 0; i < 10; i++)
            library.addBook(new Book("Book " + i, "Author"));
        library.printAvailableBooks();
        try {
            library.borrowBook("Book 1", "Sam", Calendar.getInstance().getTime());
        } catch (BookNotFoundException | BookUnavailableException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        try {
            library.returnBook("Book 1");
        } catch (BookNotFoundException | BookUnavailableException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }
}
