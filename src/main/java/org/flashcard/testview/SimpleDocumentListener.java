package org.flashcard.testview;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleDocumentListener implements DocumentListener {

    private final Runnable callback;

    public SimpleDocumentListener(Runnable callback) {
        this.callback = callback;
    }

    private void changed() {
        callback.run();
    }

    @Override public void insertUpdate(DocumentEvent e) { changed(); }
    @Override public void removeUpdate(DocumentEvent e) { changed(); }
    @Override public void changedUpdate(DocumentEvent e) { changed(); }
}
