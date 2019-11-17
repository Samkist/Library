package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Main;

import javax.swing.*;

public class SearchByTitleGUI extends GBDialog {
    private Main gui;
    private JLabel searchLabel   = addLabel("Search Books: ", 1, 1, 1, 1);
    private JTextField searchBox = addTextField("", 1, 2, 2, 1);
    private JButton searchButton = addButton("Search", 2, 1, 2, 1);
    private JList searchResults = addList(1, 4, 1, 2);
    private JTextArea searchDetails = addTextArea("", 1, 6, 3, 2);
    public SearchByTitleGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
        setSize(500, 500);
    }
}
