package view;

import javax.swing.*;
import java.awt.*;

public class ScheduleView extends JPanel {
    public ScheduleView() {
        setBackground(Theme.BG);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Schedule (coming soon)");
        label.setForeground(Theme.TEXT);
        label.setFont(Theme.MEDIUM);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label, BorderLayout.CENTER);
    }
}
