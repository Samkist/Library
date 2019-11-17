package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Main;

import javax.swing.*;

public class AllBooksGUI extends GBDialog {
    Main gui;
    private JList bookList = addList(1, 1, 1, 1);
    private JTextArea bookDetails = addTextArea("No books available.", 1, 3, 2, 1);

    public AllBooksGUI(JFrame jFrame, Main gui) {
        super(jFrame);
    }

    public void init() {

    }
}
