package view;

import javax.swing.*;
import java.awt.*;

public class DeckCard extends JPanel {

    // View håller bara på med data den fått utifrån
    private int cardCount;      // antal flashcards
    private int trophyCount;    // 0–3 pokaler
    private String deckTitle = "";  // deck-namn
    private String tagTitle = "";    // t.ex. "history"
    private Color tagColor = new Color(240, 180, 120);

    public DeckCard() {
        setPreferredSize(new Dimension(340, 210));
        setOpaque(false); // vi ritar allt själva
    }

    /**
     * Kallas av CONTROLLER när den vill uppdatera vyn.
     * View vet inte var datan kommer ifrån.
     */
    public void setCardData(int cardCount,
                            int trophyCount,
                            String deckTitle,
                            String tagTitle,
                            Color tagColor) {
        this.cardCount = cardCount;
        this.trophyCount = trophyCount;
        this.deckTitle = deckTitle;
        this.tagTitle = tagTitle;
        this.tagColor = tagColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // ========== STACK EFFECT ==========
        g2.setColor(new Color(225, 225, 225));
        g2.fillRoundRect(12, 12, w - 12, h - 12, 12, 12);

        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(6, 6, w - 12, h - 12, 12, 12);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w - 12, h - 12, 12, 12);

        // ========== TOP LEFT CIRCLE (antal kort) ==========
        g2.setColor(tagColor);
        g2.fillOval(20, 15, 45, 45);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        String num = String.valueOf(cardCount);
        int tw = g2.getFontMetrics().stringWidth(num);
        g2.drawString(num, 42 - tw / 2, 43);

        // ========== TROPHIES ==========
        g2.setColor(new Color(120, 120, 120));
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        String trophy = "🏆";
        for (int i = 0; i < trophyCount; i++) {
            g2.drawString(trophy, w - 120 + (i * 30), 40);
        }

        // ========== TITLE (CENTER) ==========
        g2.setFont(new Font("SansSerif", Font.BOLD, 36));
        g2.setColor(new Color(70, 70, 70));
        int titleW = g2.getFontMetrics().stringWidth(deckTitle);
        g2.drawString(deckTitle, (w - titleW) / 2, h / 2 + 10);

        // ========== TAG BOX (#tag) ==========
        if (tagTitle != null && !tagTitle.isEmpty()) {
            String tagText = "#" + tagTitle;
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));

            int tagW = g2.getFontMetrics().stringWidth(tagText);
            int tagH = g2.getFontMetrics().getHeight();

            int tagX = w - tagW - 40;
            int tagY = h - 35;

            g2.setColor(tagColor);
            g2.fillRoundRect(tagX - 10, tagY - tagH,
                    tagW + 20, tagH + 10, 10, 10);

            g2.setColor(Color.WHITE);
            g2.drawString(tagText, tagX, tagY);
        }
    }
}
