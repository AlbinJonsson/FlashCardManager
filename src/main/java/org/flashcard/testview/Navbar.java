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
import org.flashcard.controllers.DeckController;

public class Navbar extends JPanel{
    private final Consumer<String> navigationCallback;
    private JTextField searchBar;
    private DeckController deckController;
    private final String hint = "Search...";
    private final Font lostFont = new Font("Tahoma", Font.ITALIC, 11);
    public Navbar(DeckController deckController, Consumer<String> navigationCallback) {
        this.deckController = deckController;
        this.navigationCallback = navigationCallback;


        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(50, 50, 50)); // Mörkgrå

        add(createNavButton("Home", "Home"));
        add(createNavButton("My Decks", "MyDecks"));
        add(createSearchBar());
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
        searchBar.setPreferredSize(new Dimension(400, 20));
        searchBar.setText(hint);
        searchBar.addActionListener(e -> {});
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals(hint)) {
                    searchBar.setText("");

                } else {
                    searchBar.setText(searchBar.getText());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().equals(hint)|| searchBar.getText().isEmpty()) {
                    searchBar.setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                } else {
                    searchBar.setText(searchBar.getText());

                    setForeground(Color.BLACK);
                }
            }
        });
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {deckController.onTextChanged(searchBar.getText());}

            @Override
            public void removeUpdate(DocumentEvent e) {deckController.onTextChanged(searchBar.getText());}

            @Override
            public void changedUpdate(DocumentEvent e) {deckController.onTextChanged(searchBar.getText());}
        });
        return searchBar;
    }
    public void resetSearchBar(){
        searchBar.setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
    }
    public void onFocusLost(){

    }
}
