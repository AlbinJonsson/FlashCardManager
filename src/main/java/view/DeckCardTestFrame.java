package view;




import javax.swing.*;
import java.awt.*;

public class DeckCardTestFrame extends JFrame {

    public DeckCardTestFrame() {
        setTitle("DeckCard Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setBackground(Theme.BG);

        DeckCard cardView = new DeckCard();

        // TEMP controller som matar in data (observer-liknande)
        DeckCardTempController controller = new DeckCardTempController(cardView);
        controller.loadDummyData();

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        add(cardView);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeckCardTestFrame().setVisible(true);
        });
    }
}
