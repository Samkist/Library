package me.Samkist.Library;

import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Library {
    private ArrayList<Book> borrowed = new ArrayList<>();
    private ArrayList<Book> available = new ArrayList<>();
    private ArrayList<Book> allBooks = new ArrayList<>();

    public void addBook(Book b) {
        available.add(b);
        allBooks.add(b);
    }

    public void borrowBook(String title, String borrower, Date d) throws BookNotFoundException, BookUnavailableException {
        ArrayList<Book> books = getBooksByName(title);
        for(Book book : books) {
            if(!book.isBorrowed()) {
                book.borrowBook(borrower, d);
                borrowed.add(book);
                available.remove(book);
                return;
            }
        }
        throw new BookUnavailableException("All books with this title have been borrowed.");
    }

    public void returnBook(Book b) throws BookUnavailableException {
        if(borrowed.remove(b)) {
            b.returnBook();
            available.add(b);
            return;
        }
        throw new BookUnavailableException("Book is not in borrowed list");
    }

    public ArrayList<Book> getBorrowedBooksByName(String title) throws BookNotFoundException, BookUnavailableException {
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

    public ArrayList<Book> getLateBooks() {
        ArrayList<Book> lateBooks = new ArrayList<>();
        borrowed.forEach(book -> {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -14);
            if(book.getDateBorrowed().before(cal.getTime())) {
                lateBooks.add(book);
            }
        });
        return lateBooks;
    }

    public ArrayList<Book> getAllBooksByKeywords(ArrayList<String> keywords) {
        ArrayList<Book> books = new ArrayList<>();
        allBooks.forEach(book -> book.getKeywords().forEach(keyword -> {
           keywords.forEach(kw -> {
               if(Pattern.compile(Pattern.quote(kw), Pattern.CASE_INSENSITIVE).matcher(keyword).find())
                   books.add(book);
           });
        }));
        return books;
    }


    public void printAvailableBooks() {
        available.forEach(b -> System.out.println(b.getTitle()));
    }

    public void printBorrowedBooks() {
        borrowed.forEach(b -> System.out.println(b.getTitle()));
    }

    public ArrayList<Book> getBooksByName(String title) throws BookNotFoundException {
        ArrayList<Book> books = new ArrayList<>();
        allBooks.forEach(book -> {
            if(book.getTitle().equalsIgnoreCase(title)) {
                books.add(book);
            }
        });
        if(books.size() == 0) {
            throw new BookNotFoundException("Library does not contain any books with this title, please try another title");
        }
        return books;
    }

    public Book getFirstAvailableBookByName(String title) throws BookNotFoundException {
        for(Book b : available) {
            if(b.getTitle().equalsIgnoreCase(title))
                return b;
        }
        throw new BookNotFoundException("No book was found by that title.");
    }

    public Book getFirstBookByName(String title) throws BookNotFoundException {
        for(Book b : allBooks) {
            if(b.getTitle().equalsIgnoreCase(title))
                return b;
        }
        throw new BookNotFoundException("No book was found by that title.");
    }

    public ArrayList<Book> getBorrowed() {
        return borrowed;
    }

    public ArrayList<Book> getAvailable() {
        return available;
    }

    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

}
