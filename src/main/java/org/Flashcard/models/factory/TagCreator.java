// 5. TagCreator.java
package org.Flashcard.models.factory;

import org.Flashcard.models.dataClasses.Tag;

public class TagCreator extends EntityCreator {
    private final String title;
    private final String color;

    public TagCreator(String title, String color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public Tag create() {
        return new Tag(title, color);
    }
}