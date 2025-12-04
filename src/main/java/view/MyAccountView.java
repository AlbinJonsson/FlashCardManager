package view;

import javax.swing.*;
import java.awt.*;

public class MyAccountView extends JPanel {
    public MyAccountView() {
        setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("My Account (coming soon)");
        label.setForeground(Theme.TEXT);
        label.setFont(Theme.MEDIUM);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
    }
}
