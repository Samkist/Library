package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.Date;

public class ReturnLoanDialog extends CustomDialog {
    private CustomDialog parentDialog;
    private Main gui;
    private JButton funct;
    private JTextField borrowerField;
    private JTextField dateField;
    private Book b;
    public ReturnLoanDialog(JFrame jFrame, CustomDialog parentDialog, Main mainGui) {
        super(jFrame);
        this.parentDialog = parentDialog;
        gui = mainGui;
    }

    public void init(Book b) {
        this.b = b;
        if(b.isBorrowed()) {
            funct = addButton("Return " + b.getTitle(), 2, 1, 2, 1);
            setTitle("Return " + b.getTitle());
        } else {
            funct = addButton("Loan " + b.getTitle(), 3, 1, 2, 1);
            borrowerField = addTextField("Borrower Name", 1, 1, 2, 1);
            dateField = addTextField("mm/dd/yyyy", 2, 1, 2, 1);
            setTitle("Loan " + b.getTitle());
        }
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(funct)) {
            if(b.isBorrowed()) {
                try {
                    gui.getLibrary().returnBook(b);
                } catch (BookUnavailableException e) {
                    messageBox(e.getMessage());
                    return;
                }
                messageBox(b.getTitle() + " returned successfully.");
                parentDialog.init();
                setVisible(false);
            } else {
                Date d;
                try {
                    d = new Date(dateField.getText());
                    gui.getLibrary().borrowBook(b.getTitle(), borrowerField.getText(), d);
                } catch(BookUnavailableException | BookNotFoundException e) {
                    messageBox(e.getMessage());
                    return;
                } catch(IllegalArgumentException e) {
                    messageBox("Invalid date.");
                    return;
                }
                messageBox(b.getTitle() + " loaned to " + borrowerField.getText() + " on " + d.toString() + " successfully");
                parentDialog.init();
                setVisible(false);
            }
        }
    }

    @Override
    public void init() {

    }
}
