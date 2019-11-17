package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Exceptions.BookUnavailableException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.Date;

public class AllBooksGUI extends CustomDialog {
    private Main gui;
    private JFrame frame;
    private JList bookList = addList(1, 1, 1, 1);
    private JTextArea bookDetails = addTextArea("No books available.", 1, 3, 2, 1);

    public AllBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.frame = jFrame;
        this.gui = gui;
        setTitle("All Books");
        setSize(500, 500);
        bookDetails.setEditable(false);
    }
    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        DefaultListModel model = (DefaultListModel)bookList.getModel();
        model.removeAllElements();
        gui.getLibrary().getAllBooks().forEach(book -> {
            if(book.isBorrowed()) {
                model.addElement(book.getTitle() + " - Borrowed");
            } else {
                model.addElement(book.getTitle() + " - Available");
            }
        });
        try {
            bookList.setSelectedIndex(0);
            updateBookDetails(gui.getLibrary().getAllBooks().get(0));
        } catch (Exception e) {
            bookDetails.setText("No books.");
        }
    }

    private void updateBookDetails(Book b) {
        if(b.isBorrowed()) {
            bookDetails.setText(""
                    + "Title: " + b.getTitle() + "\n"
                    + "Author: " + b.getAuthor() + "\n"
                    + "Borrower: " + b.getBorrower() + "\n"
                    + "Date Borrowed: " + b.getDateBorrowed().toString() + "\n"
                    + "Double click to return this book."
            );
        } else {
            bookDetails.setText(""
                    + "Title: " + b.getTitle() + "\n"
                    + "Author: " + b.getAuthor() + "\n"
                    + "Double click to loan out this book"
            );
        }
    }

    public void listItemSelected(JList listObj) {
        if(listObj.equals(bookList)) {
            updateBookDetails(gui.getLibrary().getAllBooks().get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(bookList)) {
            Book b = gui.getLibrary().getAllBooks().get(listObj.getSelectedIndex());
            ReturnLoanDialog loanDialog = new ReturnLoanDialog(frame, this, gui);
            loanDialog.init(b);
            loanDialog.setVisible(true);
        }
    }
}
