package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookUnavailableException;
import me.Samkist.Library.Main;

import javax.swing.*;

public class ReturnMenuGUI extends GBDialog {
    private Main gui;
    private JList bookList = addList(1, 4, 1, 4);
    private JTextArea bookDetails = addTextArea("No books borrowed", 1, 6, 2, 4);
    private JButton returnBook = addButton("Return Book", 2, 1, 1, 1);

    public ReturnMenuGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        returnBook.setEnabled(true);
        DefaultListModel model = (DefaultListModel) bookList.getModel();
        model.removeAllElements();
        bookDetails.setText("No books borrowed");
        gui.getLibrary().getBorrowed().forEach(book -> {
            model.addElement(book.getTitle());
        });
        try {
            bookList.setSelectedIndex(0);
            updateBookDetails(gui.getLibrary().getBorrowed().get(0));
        } catch (Exception ignored) {
            returnBook.setEnabled(false);
        }

    }
    private void updateBookDetails(Book b) {
        bookDetails.setText(""
                + "Title: " + b.getTitle() + "\n"
                + "Author: " + b.getAuthor() + "\n"
                + "Borrower: " + b.getBorrower() + "\n"
                + "Date Borrowed: " + b.getDateBorrowed().toString()
        );
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(returnBook)) {
            returnBook();
        }
    }

    public void listItemSelected(JList listObj) {
        if(listObj.equals(bookList)) {
            updateBookDetails(gui.getLibrary().getBorrowed().get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(bookList)) {
            returnBook();
        }
    }

    private void returnBook() {
        try {
            gui.getLibrary().returnBook(gui.getLibrary().getBorrowed().get(bookList.getSelectedIndex()));
        } catch (BookUnavailableException e) {
            messageBox(e.getMessage());
        }
        init();
    }
}
