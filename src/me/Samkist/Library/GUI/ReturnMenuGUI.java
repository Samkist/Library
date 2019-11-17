package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;
import me.Samkist.Library.Main;

import javax.swing.*;

public class ReturnMenuGUI extends GBDialog {
    private Main gui;
    public ReturnMenuGUI(JFrame jFrame, Main gui) {
        super(jFrame);
        this.gui = gui;
    }
}
