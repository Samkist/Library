package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import BreezySwing.IntegerField;
import me.Samkist.Library.Book;
import me.Samkist.Library.Main;

import javax.swing.*;

@SuppressWarnings("unused")
public class AddBooksGUI extends CustomDialog {
    private Main gui;
    private JLabel bookNameLabel = addLabel("Book Name:", 1, 1, 1, 1);
    private JLabel authorNameLabel = addLabel("Author: ", 2, 1, 1, 1);
    private JLabel bookQuantityLabel = addLabel("Quantity: ", 3, 1, 1, 1);
    private JTextField bookNameField = addTextField("", 1, 2, 1, 1);
    private JTextField authorNameField = addTextField("", 2, 2, 1, 1);
    private IntegerField bookQuantityField = addIntegerField(0, 3, 2, 1,1);
    private JButton addBooksButton = addButton("Add Book(s)", 4, 1, 2, 1);
    public AddBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setSize(500, 500);
        setTitle("Add Books");
    }

    public void buttonClicked(JButton jButton) {
        if(jButton.equals(addBooksButton)) {
            for(int i = 0; i < bookQuantityField.getNumber(); i++) {
                gui.getLibrary().addBook(new Book(bookNameField.getText(), authorNameField.getText()));
            }
            messageBox("Added " + bookQuantityField.getNumber() + " book(s) with title " +
                    bookNameField.getText() + " and author " + authorNameField.getText());
            bookNameField.setText("");
            authorNameField.setText("");
            bookQuantityField.setNumber(0);
        }
    }


    @Override
    public void init() {

    }
}
