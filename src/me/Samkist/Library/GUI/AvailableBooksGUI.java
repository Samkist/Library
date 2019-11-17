package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.ArrayList;

public class AvailableBooksGUI extends GBDialog {

    private Main gui;
    private JList bookList = addList(1, 1, 1, 1);
    private JTextArea bookDetails = addTextArea("No books available.", 1, 3, 2, 1);
    private ArrayList<Book> uniqueBooks = new ArrayList<>();

    public AvailableBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setTitle("Available Books");
        this.setSize(500, 500);
        bookDetails.setEditable(false);
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
            for (int i = 0; i < model.size(); i++) {
                uniqueBooks.add(gui.getLibrary().getFirstAvailableBookByName((String) model.getElementAt(i)));
            }
        } catch(BookNotFoundException e) {
            messageBox(e.getMessage());
        }
        try {
            bookList.setSelectedIndex(0);
            updateBookDetails(uniqueBooks.get(0));
        } catch(Exception ignored) {

        }
    }

    private void updateBookDetails(Book b) {
        int bookAmt = (int) gui.getLibrary().getAvailable().stream().filter(book -> book.getTitle().equals(b.getTitle())).count();
        bookDetails.setText(""
                + "Title: " + b.getTitle() + "\n"
                + "Author: " + b.getAuthor() + "\n"
                + "Available: " + bookAmt
        );
    }

    public void listItemSelected(JList listObj) {
        if(listObj.equals(bookList)) {
            updateBookDetails(uniqueBooks.get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(bookList)) {

        }
    }


}
