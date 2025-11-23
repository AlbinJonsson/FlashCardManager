package org.Flashcard.view;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    public AppWindow() {
        super("Flashcard Manager");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Add main panel
        add(new UserSelectionPanel(), BorderLayout.CENTER);

        setLocationRelativeTo(null); // center
        setVisible(true);
    }
}
