package me.Samkist.Library;

import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class Library {
    private ArrayList<Book> borrowed = new ArrayList<>();
    private ArrayList<Book> available = new ArrayList<>();

    public void addBook(Book b) {
        available.add(b);
    }

    public void borrowBook(String title, String borrower, Date d) throws BookNotFoundException, BookUnavailableException {
        ArrayList<Book> books = getBooksByName(title);
        AtomicBoolean successful = new AtomicBoolean(false);
        books.forEach(book -> {
            if(!book.isBorrowed()) {
                available.remove(book);
                book.borrowBook(borrower, d);
                borrowed.add(book);
                successful.set(true);
            }
        });
        if(!successful.get())
        throw new BookUnavailableException("All books with this title have been borrowed.");
    }

    public ArrayList<Book> returnBook(String title) throws BookNotFoundException, BookUnavailableException {
        ArrayList<Book> books = getBooksByName(title);
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        books.forEach(book -> {
            if(book.isBorrowed())
                borrowedBooks.add(book);
        });
        if(borrowedBooks.size() == 0)
            throw new BookUnavailableException("All books with this title are returned.");
        return borrowedBooks;
    }

    public ArrayList<Book> getLateBooks() throws BookUnavailableException {
        ArrayList<Book> lateBooks = new ArrayList<>();
        borrowed.forEach(book -> {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -14);
            if(book.getDateBorrowed().before(cal.getTime())) {
                lateBooks.add(book);
            }
        });
        if(lateBooks.size() == 0)
            throw new BookUnavailableException("There are no late books.");
        return lateBooks;
    }


    public void printAvailableBooks() {
        available.forEach(b -> System.out.println(b.getTitle()));
    }

    public void printBorrowedBooks() {
        borrowed.forEach(b -> System.out.println(b.getTitle()));
    }

    public ArrayList<Book> getBooksByName(String title) throws BookNotFoundException {
        ArrayList<Book> books = new ArrayList<>();
        borrowed.forEach(book -> {
            if(book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        });
        available.forEach(book -> {
            if(book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        });
        if(books.size() == 0) {
            throw new BookNotFoundException("Library does not contain any books with this title, please try another title");
        }
        return books;
    }

    //APPLIES TO BOTH BORROWED AND AVAILABLE
    public boolean hasBook(Book b) {
        return borrowed.contains(b) || available.contains(b);
    }

}
