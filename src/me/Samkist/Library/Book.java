package me.Samkist.Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String borrower = null;
    private ArrayList<String> keywords;
    private Date dateBorrowed = null;
    private boolean isBorrowed = false;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        keywords = new ArrayList<>(Arrays.asList(title.split(" ")));
    }

    public void borrowBook(String borrower, Date d) {
        this.borrower = borrower;
        this.dateBorrowed = d;
        isBorrowed = true;
    }

    public void returnBook() {
        this.borrower = null;
        this.dateBorrowed = null;
        isBorrowed = false;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrower() {
        return borrower;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }
}
