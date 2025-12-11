package org.flashcard.testview;


import org.flashcard.controllers.observer.Observer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

public class Navbar extends JPanel{
    private final Consumer<String> navigationCallback;
    private JTextField searchBar;
    public Navbar(Consumer<String> navigationCallback) {
        this.navigationCallback = navigationCallback;


        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(50, 50, 50)); // Mörkgrå

        add(createNavButton("Home", "Home"));
        add(createNavButton("My Decks", "MyDecks"));
    }

    private JButton createNavButton(String label, String viewName) {
        JButton btn = new JButton(label);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.addActionListener(e -> navigationCallback.accept(viewName));
        return btn;
    }
    private JTextField createSearchBar(){
        searchBar = new JTextField();
        searchBar.setText("Search...");
        searchBar.addActionListener(e -> {});
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {onTextChanged();}

            @Override
            public void removeUpdate(DocumentEvent e) {onTextChanged();}

            @Override
            public void changedUpdate(DocumentEvent e) {onTextChanged();}
        });
        return searchBar;
    }
    private String onTextChanged(){
        return searchBar.getText();
    }
    private void onFocusLost(){

    }
    private void onEnterPressed(){

    }
}
