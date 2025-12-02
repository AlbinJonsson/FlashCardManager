// 1. EntityCreator.java  (abstract – exactly as in the diagram)
package org.Flashcard.models.factory;

public abstract class EntityCreator {
    protected EntityCreator() {}
    public abstract Object create();
}