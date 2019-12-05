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

public class LoanMenuGUI extends CustomDialog {
    private Main gui;
    private JList bookList = addList(1, 4, 1, 5);
    private JLabel borrowerLabel = addLabel("Borrower Name: ", 1, 1, 1, 1);
    private JLabel dateLabel = addLabel("Date: ", 3, 1, 1,1);
    private JButton loanButton = addButton("Loan Book", 5, 1 ,1 ,1);
    private JTextField borrowerField = addTextField("", 2, 1, 1, 1);
    private JTextField dateField = addTextField("mm/dd/yyyy", 4, 1, 1,1);
    private ArrayList<Book> uniqueBooks = new ArrayList<>();

    public LoanMenuGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setTitle("Loan a Book");
        setSize(500, 500);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        DefaultListModel model = (DefaultListModel)bookList.getModel();
        loanButton.setEnabled(true);
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
        if(model.size() == 0)
            loanButton.setEnabled(false);
    }

    private void loanBook(String borrower, String date) {
        Book b;
        try {
            b = uniqueBooks.get(bookList.getSelectedIndex());
        } catch (Exception e) {
            return;
        }
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] split = date.split("/");
        int[] dateVal = new int[split.length];
        try {
            int x = 0;
            for(String s : split) {
                dateVal[x++] = Integer.parseInt(s);
            }
            final String s = "Date corrected to " + dateVal[0] + "/" + dateVal[1] + "/" + dateVal[2];
            if(dateVal[0] == 2) {
                if(dateVal[2] % 4 == 0 || (dateVal[2] % 100 == 0 && dateVal[2] % 400 == 0) && dateVal[1] > 29) {
                    dateVal[1] = 29;
                    messageBox(s);
                } else if(dateVal[1] > 28){
                    dateVal[1] = 28;
                    messageBox(s);
                }
            }
            if(dateVal[1] > months[dateVal[0] - 1]) {
                dateVal[1] = months[dateVal[0] - 1];
                messageBox(s);
            }
        } catch(Exception e) {

        }
        try {
            String s = dateVal[0] + "/" + dateVal[1] + "/" + dateVal[2];
            gui.getLibrary().borrowBook(b.getTitle(), borrower, new Date(s));
        } catch(BookNotFoundException | BookUnavailableException e) {
            messageBox(e.getMessage());
        } catch (IllegalArgumentException e) {
            messageBox("Invalid date.");
            return;
        }
        messageBox(b.getTitle() + " loaned to " + borrower + " on " + date + " successfully");
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(loanButton)) {
            loanBook(borrowerField.getText(), dateField.getText());
        }
    }
}
