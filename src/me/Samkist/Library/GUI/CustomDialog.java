package me.Samkist.Library.GUI;

import BreezySwing.GBDialog;

import javax.swing.*;

public abstract class CustomDialog extends GBDialog {

    public CustomDialog(JFrame jFrame) {
        super(jFrame);
    }

    public abstract void init();
}
