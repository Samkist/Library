package me.Samkist.Library;

import BreezySwing.GBFrame;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;
import me.Samkist.Library.GUI.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Main extends GBFrame {
    private static JFrame frame = new Main();
    private AddBooksGUI addBooksGUI = new AddBooksGUI(frame, this);
    private AvailableBooksGUI availableBooksGUI = new AvailableBooksGUI(frame, this);
    private BorrowedBooksGUI borrowedBooksGUI = new BorrowedBooksGUI(frame, this);
    private LateBooksGUI lateBooksGUI = new LateBooksGUI(frame, this);
    private LoanMenuGUI loanMenuGUI = new LoanMenuGUI(frame, this);
    private ReturnMenuGUI returnMenuGUI = new ReturnMenuGUI(frame, this);
    private SearchByTitleGUI searchByTitleGUI = new SearchByTitleGUI(frame, this);
    private AllBooksGUI allBooksGUI = new AllBooksGUI(frame, this);
    private JButton openAvailableBooksButton = addButton("View Available Books", 1, 1, 1, 1);
    private JButton openSearchByTitleButton = addButton("Search by Title", 1, 2, 1, 1);
    private JButton openLoanMenuButton = addButton("Loan a Book", 2, 1, 1, 1);
    private JButton openReturnMenuButton = addButton("Return a Book", 2, 2, 1, 1);
    private JButton openBorrowedBooksMenuButton = addButton("View Borrowed Books", 3, 1, 1, 1);
    private JButton openLateBooksMenuButton = addButton("View Late Books", 3, 2, 1, 1);
    private JButton openAddBooksMenuButton = addButton("Add Books", 4, 1, 1, 1);
    private JButton openAllBooksMenuButton = addButton("Loan/Return", 4, 2, 1, 1);
    private Library library;

    public static void main(String[] args) {
        frame.setTitle("Library Program");
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public Main() {
        library = new Library();
    }
    @SuppressWarnings("Duplicates")
    public void buttonClicked(JButton jButton) {
        if(jButton.equals(openAddBooksMenuButton)) {
            addBooksGUI.setVisible(true);
        }
        if (jButton.equals(openAvailableBooksButton)) {
            availableBooksGUI.init();
            availableBooksGUI.setVisible(true);
        }
        if(jButton.equals(openLoanMenuButton)) {
            loanMenuGUI.init();
            loanMenuGUI.setVisible(true);
        }
        if(jButton.equals(openReturnMenuButton)) {
            returnMenuGUI.init();
            returnMenuGUI.setVisible(true);
        }
        if(jButton.equals(openBorrowedBooksMenuButton)) {
            borrowedBooksGUI.init();
            borrowedBooksGUI.setVisible(true);
        }
        if(jButton.equals(openLateBooksMenuButton)) {
            lateBooksGUI.init();
            lateBooksGUI.setVisible(true);
        }
        if(jButton.equals(openSearchByTitleButton)) {
            searchByTitleGUI.init();
            searchByTitleGUI.setVisible(true);
        }
        if(jButton.equals(openAllBooksMenuButton)) {
            allBooksGUI.init();
            allBooksGUI.setVisible(true);
        }
    }

    public Library getLibrary() {
        return library;
    }
}
