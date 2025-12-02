package org.flashcard.application.mapper;

import org.flashcard.application.dto.TagDTO;
import org.flashcard.models.dataclasses.Tag;

public class TagMapper {
    public static TagDTO toDTO(Tag tag) {
        if (tag == null) {
            return null; // If the deck does not have any tag
        }
        return new TagDTO(
                tag.getId(),
                tag.getTitle(),
                tag.getColor()
        );
    }
}
