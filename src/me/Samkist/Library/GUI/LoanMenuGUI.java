package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LoanMenuGUI extends GBDialog {
    private Main gui;
    private JList bookList = addList(1, 4, 1, 5);
    private JLabel borrowerLabel = addLabel("Borrower Name: ", 1, 1, 1, 1);
    private JLabel dateLabel = addLabel("Date: ", 4, 1, 1,1);
    private JButton loanButton = addButton("Loan Book", 5, 1 ,1 ,1);
    private JTextField borrowerField = addTextField("", 2, 1, 1, 1);
    private JTextField dateField = addTextField("mm/dd/yyyy", 3, 1, 1,1);
    private ArrayList<Book> uniqueBooks = new ArrayList<>();

    public LoanMenuGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setTitle("Loan A Book");
        setSize(500, 500);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        DefaultListModel model = (DefaultListModel)bookList.getModel();
        model.removeAllElements();
        gui.getLibrary().getAvailable().forEach(book -> {
            if(!model.contains(book.getTitle()))
                model.addElement(book.getTitle());
        });
        try {
            for(int i = 0; i < model.size(); i++) {
                uniqueBooks.add(gui.getLibrary().getFirstAvailableBookByName((String) model.getElementAt(i)));
            }
        } catch (BookNotFoundException e) {
            messageBox(e.getMessage());
        }
        try {
            bookList.setSelectedIndex(0);
        } catch (Exception ignored) {

        }
    }

    private void loanBook(String borrower, String date) {
        Book b;
        try {
            b = uniqueBooks.get(bookList.getSelectedIndex());
        } catch (Exception e) {
            return;
        }
        try {
            gui.getLibrary().borrowBook(b.getTitle(), borrower, new Date(date));
        } catch(BookNotFoundException | BookUnavailableException e) {
            messageBox(e.getMessage());
        }
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(loanButton)) {
            loanBook(borrowerField.getText(), dateField.getText());
        }
    }
}
