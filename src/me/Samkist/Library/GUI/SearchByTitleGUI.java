package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Book;
import me.Samkist.Library.Exceptions.BookNotFoundException;
import me.Samkist.Library.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchByTitleGUI extends GBDialog {
    private Main gui;
    private JLabel searchLabel = addLabel("Search: ", 1, 1, 1, 1);
    private JTextField searchField = addTextField("", 1, 2, 2, 1);
    private JButton searchButton = addButton("Search", 1, 3, 1, 1);
    private JList searchResults = addList(2, 1, 1, 1);
    private JTextArea searchResultDetails = addTextArea("", 2, 3, 2, 1);
    private ArrayList<Book> uniqueBooks = new ArrayList<>();
    public SearchByTitleGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setSize(500, 500);
        setTitle("Search by Title");
        searchResultDetails.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    public void search(ArrayList<String> keywords) {
        ArrayList<Book> results = gui.getLibrary().getAllBooksByKeywords(keywords);
        uniqueBooks = new ArrayList<>();
        DefaultListModel model = (DefaultListModel)searchResults.getModel();
        for(int i = 0; i < model.getSize(); i++) {
            model.remove(i);
        }
        results.forEach(book -> {
            if(!model.contains(book.getTitle()))
                model.addElement(book.getTitle());
        });
        try {
            for(int i = 0; i < model.size(); i++) {
                uniqueBooks.add(gui.getLibrary().getFirstBookByName((String) model.getElementAt(i)));
            }
        } catch (BookNotFoundException e) {
            messageBox(e.getMessage());
        }
        try {
            searchResults.setSelectedIndex(0);
            updateBookDetails(results.get(0));
        } catch(Exception ignored) {

        }
    }
    @SuppressWarnings("Duplicates")
    private void updateBookDetails(Book b) {
        int bookAmt = (int) gui.getLibrary().getAvailable().stream().filter(book -> book.getTitle().equals(b.getTitle())).count();
        int borrAmt = (int) gui.getLibrary().getBorrowed().stream().filter(book -> book.getTitle().equalsIgnoreCase(b.getTitle())).count();
        searchResultDetails.setText(""
                + "Title: " + b.getTitle() + "\n"
                + "Author: " + b.getAuthor() + "\n"
                + "Available: " + bookAmt + "\n"
                + "Borrowed: " + borrAmt
        );
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(searchButton)) {
            search(new ArrayList<>(Arrays.asList(searchField.getText().split(" "))));
        }
    }
    public void listItemSelected(JList listObj) {
        if(listObj.equals(searchResults)) {
            updateBookDetails(uniqueBooks.get(listObj.getSelectedIndex()));
        }
    }

    public void listDoubleClicked(JList listObj, String itemClicked) {
        if(listObj.equals(searchResults)) {

        }
    }
}
