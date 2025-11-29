package view;

import javax.swing.*;

public class NavbarView extends JPanel {
    NavigationListener listener;

    public void setOnNavigate(NavigationListener listener) {
            this.listener = listener;
    }
}
