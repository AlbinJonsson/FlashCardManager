package view;

import org.flashcard.application.dto.TagDTO;
import org.flashcard.application.dto.UserDTO;
import org.flashcard.application.mapper.DeckMapper;
import org.flashcard.application.mapper.TagMapper;
import org.flashcard.application.mapper.UserMapper;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.models.dataclasses.Tag;


import javax.swing.*;
import java.awt.*;

public class CreateDeckView extends JPanel {

    private final DeckController deckController;
    private final UserController userController;

    private JPanel headerPanel;
    private JLabel pageTitle;
    private JPanel decksPanel;
    private JLabel deckTitle;
    private JLabel tagTitle;
    private JPanel boxWrapper;
    private JPanel buttonWrapper;
    private JPanel deckTitleWrapper;
    private JPanel tagTitleWrapper;
    private JButton backButton;
    private JButton createDeckButton;
    private ButtonListener listener;
    private JTextField titleField;
    private JTextField tagField;
    private JColorChooser tagColorChooser;

    public CreateDeckView(DeckController deckController, UserController userController){

        this.deckController = deckController;
        this.userController = userController;

        initComponents();
        layoutComponents();
        styleComponents();
        addListeners();

    }
    private void initComponents(){

        // Header
        headerPanel = new JPanel();
        pageTitle = new JLabel("Create New Deck");


        // Decks container
        decksPanel = new JPanel();
        titleField = new JTextField(30);
        tagField = new JTextField(30);
        deckTitle = new JLabel("Title");
        tagTitle = new JLabel("Tag");
        tagColorChooser = new JColorChooser(Color.WHITE);
        boxWrapper = new JPanel(new FlowLayout());
        buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        deckTitleWrapper = new JPanel(new BorderLayout());
        tagTitleWrapper = new JPanel(new BorderLayout());
        createDeckButton = new JButton("Create Deck");
        backButton = new JButton("Back");



    }
    private void layoutComponents(){
        setLayout(new BorderLayout());
        //Header
        headerPanel.add(pageTitle);

        //DecksPanel
        decksPanel.setLayout(new BoxLayout(decksPanel, BoxLayout.PAGE_AXIS));
        decksPanel.add(Box.createVerticalStrut(100));
        deckTitleWrapper.add(deckTitle, BorderLayout.WEST);
        tagTitleWrapper.add(tagTitle, BorderLayout.WEST);

        decksPanel.add(deckTitleWrapper);
        decksPanel.add(titleField);
        decksPanel.add(Box.createVerticalStrut(40));
        decksPanel.add(tagTitleWrapper);
        decksPanel.add(tagField);
        decksPanel.add(Box.createVerticalStrut(20));
        decksPanel.add(tagColorChooser);
        decksPanel.add(Box.createVerticalStrut(20));
        buttonWrapper.add(backButton);
        buttonWrapper.add(createDeckButton);
        decksPanel.add(buttonWrapper);
        boxWrapper.add(decksPanel);
        //Add to view
        add(headerPanel, BorderLayout.NORTH);
        add(boxWrapper, BorderLayout.CENTER);
    }

    public void setButtonListener(ButtonListener listener){
        this.listener = listener;
    }
    private void styleComponents() {
        //Title
        pageTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pageTitle.setForeground(Color.WHITE);

        //TextField titles
        deckTitle.setFont(new Font("Arial", Font.BOLD, 16));
        tagTitle.setFont(new Font("Arial", Font.BOLD, 16));

        //TextFields
        titleField.setPreferredSize(new Dimension(200, 40));
//        titleField.setMaximumSize(new Dimension(200, 40));
        tagField.setPreferredSize(new Dimension(200, 40));
//        tagField.setMaximumSize(new Dimension(200, 40));

        // Header
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBackground(Theme.BG);
        headerPanel.setOpaque(true);

        boxWrapper.setOpaque(true);
        boxWrapper.setBackground(Theme.BG);
        buttonWrapper.setOpaque(false);

        // Decks container
        decksPanel.setOpaque(true);
        decksPanel.setBackground(Theme.BG);

        //Buttons
        createDeckButton.setBackground(new Color(230, 230, 230));
        createDeckButton.setForeground(Color.BLACK);
        createDeckButton.setFocusPainted(false);
        backButton.setBackground(new Color(230, 230, 230));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
    }
    private void addListeners() {
        createDeckButton.addActionListener(e -> {
            UserDTO userDTO = UserMapper.toDTO(userController.getCurrentUser());

            String title = titleField.getText().trim();
            String tagName = tagField.getText().trim();

            // Build TagDTO from the text field.
            TagDTO tagDTO = TagMapper.toDTO(new Tag(tagName, tagColorChooser.getColor().toString(), userController.getCurrentUser()));

            deckController.createDeck(userDTO.getId(), titleField.getText(), tagDTO.getId());
            listener.buttonPressed("CreateFlashcard");

        });
        backButton.addActionListener(e -> listener.buttonPressed("MyDecks"));
    }
    // Clear fields
    public void clear() {
        titleField.setText("");
        tagField.setText("");

    }


}
