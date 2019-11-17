package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.ArrayList;

public class BorrowedBooksGUI extends CustomDialog {
    private Main gui;
    private JFrame frame;
    private JList bookList = addList(1, 1,1, 1);
    private JTextArea bookDetails = addTextArea("No books borrowed", 1, 3, 2, 1);

    public BorrowedBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.frame = jFrame;
        this.gui = gui;
        setTitle("Borrowed Books");
        this.setSize(500, 500);
        bookDetails.setEditable(false);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        DefaultListModel model = (DefaultListModel) bookList.getModel();
        bookDetails.setText("No books borrowed");
        model.removeAllElements();
        gui.getLibrary().getBorrowed().forEach(book -> {
                model.addElement(book.getTitle());
        });
        try {
            bookList.setSelectedIndex(0);
            updateBookDetails(gui.getLibrary().getBorrowed().get(0));
        } catch (Exception ignored) {

        }
    }
    private void updateBookDetails(Book b) {
        bookDetails.setText(""
                + "Title: " + b.getTitle() + "\n"
                + "Author: " + b.getAuthor() + "\n"
                + "Borrower: " + b.getBorrower() + "\n"
                + "Date Borrowed: " + b.getDateBorrowed().toString() + "\n"
                + "Double click to return this book"
        );
    }
    public void listItemSelected(JList listObj) {
        if(listObj.equals(bookList)) {
            updateBookDetails(gui.getLibrary().getBorrowed().get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(bookList)) {
            Book b = gui.getLibrary().getBorrowed().get(listObj.getSelectedIndex());
            ReturnLoanDialog loanDialog = new ReturnLoanDialog(frame, this, gui);
            loanDialog.init(b);
            loanDialog.setVisible(true);
        }
    }
}
