package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Main;

import javax.swing.*;

public class LateBooksGUI extends GBDialog {
    private Main gui;
    private JList bookList = addList(1, 1,1 ,1);
    private JTextArea bookDetails = addTextArea("No late books", 1, 3 ,2 ,1);

    public LateBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setTitle("Late Books");
        setSize(500, 500);
        bookDetails.setEditable(false);
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public void init() {
        DefaultListModel model = (DefaultListModel) bookList.getModel();
        bookDetails.setText("No books borrowed");
        model.removeAllElements();
        gui.getLibrary().getLateBooks().forEach(book -> {
            model.addElement(book.getTitle());
        });
        try {
            bookList.setSelectedIndex(0);
            updateBookDetails(gui.getLibrary().getLateBooks().get(0));
        } catch (Exception ignored) {

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

    public void listItemSelected(JList listObj) {
        if(listObj.equals(bookList)) {
            updateBookDetails(gui.getLibrary().getLateBooks().get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(bookList)) {

        }
    }
}
