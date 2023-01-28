package com.takenoko.asset;

import static org.assertj.core.api.Assertions.*;

import com.takenoko.layers.tile.ImprovementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ImprovementDeckTest {

    @Nested
    @DisplayName("Class Constructor")
    class TestConstructor {
        @Test
        @DisplayName("When instantiated, deck has 3 times the number of improvement types")
        void constructor_WhenInstantiated_ThenDeckHas3TimesTheNumberOfImprovementTypes() {
            ImprovementDeck deck = new ImprovementDeck();
            assertThat(deck.values()).hasSize(ImprovementType.values().length);
        }
    }

    @Nested
    @DisplayName("Method draw")
    class TestDraw {
        @Test
        @DisplayName("When improvement is drawn, it is removed from the deck")
        void draw_WhenImprovementIsDrawn_ThenItIsRemovedFromTheDeck() {
            ImprovementDeck deck = new ImprovementDeck();
            ImprovementType imp = deck.draw(ImprovementType.FERTILIZER);
            assertThat(imp).isEqualTo(ImprovementType.FERTILIZER);
            assertThat(deck).containsEntry(ImprovementType.FERTILIZER, 2);
        }

        @Test
        @DisplayName("When improvement is exhausted, an exception is thrown")
        void draw_WhenImprovementIsExhausted_ThenAnExceptionIsThrown() {
            ImprovementDeck deck = new ImprovementDeck();
            deck.draw(ImprovementType.FERTILIZER);
            deck.draw(ImprovementType.FERTILIZER);
            deck.draw(ImprovementType.FERTILIZER);
            assertThatThrownBy(() -> deck.draw(ImprovementType.FERTILIZER))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("No more improvement of type FERTILIZER");
        }
    }

    @Nested
    @DisplayName("Method hasImprovement")
    class TestHasImprovement {
        @Test
        @DisplayName("When instantiated, deck has 3 times the number of improvement types")
        void hasImprovement_WhenImprovementIsDrawn_ThenItIsRemovedFromTheDeck() {
            ImprovementDeck deck = new ImprovementDeck();
            deck.draw(ImprovementType.FERTILIZER);
            assertThat(deck.hasImprovement(ImprovementType.FERTILIZER)).isTrue();
        }

        @Test
        @DisplayName("When the deck runs out of improvement, hasImprovement returns false")
        void hasImprovement_WhenDeckRunsOutOfImprovement_ThenReturnsFalse() {
            ImprovementDeck deck = new ImprovementDeck();
            deck.draw(ImprovementType.FERTILIZER);
            deck.draw(ImprovementType.FERTILIZER);
            deck.draw(ImprovementType.FERTILIZER);
            assertThat(deck.hasImprovement(ImprovementType.FERTILIZER)).isFalse();
        }
    }

    @Nested
    @DisplayName("Method equals")
    class TestEquals {
        @Test
        @DisplayName("When called on self, returns true")
        void equals_WhenCalledOnSelf_ThenReturnsTrue() {
            ImprovementDeck deck = new ImprovementDeck();
            assertThat(deck).isEqualTo(deck);
        }

        @Test
        @DisplayName("When called on a different object, returns false")
        void equals_WhenCalledOnDifferentObject_ThenReturnsFalse() {
            ImprovementDeck deck = new ImprovementDeck();
            assertThat(deck).isNotEqualTo(new Object());
        }

        @Test
        @DisplayName("When two decks are instantiated, they should be equals")
        void equals_WhenTwoDecksAreInstantiated_ThenTheyAreEquals() {
            ImprovementDeck deck1 = new ImprovementDeck();
            ImprovementDeck deck2 = new ImprovementDeck();
            assertThat(deck1).isEqualTo(deck2);
        }

        @Test
        @DisplayName("When two decks are different, they should not be equals")
        void equals_WhenTwoDecksAreDifferent_ThenTheyAreNotEquals() {
            ImprovementDeck deck1 = new ImprovementDeck();
            deck1.draw(ImprovementType.FERTILIZER);
            ImprovementDeck deck2 = new ImprovementDeck();
            assertThat(deck1).isNotEqualTo(deck2);
        }
    }

    @Nested
    @DisplayName("Method hashCode")
    class TestHashCode {
        @Test
        @DisplayName("When two decks are instantiated, they should have the same hashcode")
        void hashCode_WhenTwoDecksAreInstantiated_ThenTheyHaveTheSameHashcode() {
            ImprovementDeck deck1 = new ImprovementDeck();
            ImprovementDeck deck2 = new ImprovementDeck();
            assertThat(deck1).hasSameHashCodeAs(deck2);
        }

        @Test
        @DisplayName("When two decks are different, they should have different hashcode")
        void hashCode_WhenTwoDecksAreDifferent_ThenTheyHaveDifferentHashcode() {
            ImprovementDeck deck1 = new ImprovementDeck();
            deck1.draw(ImprovementType.FERTILIZER);
            ImprovementDeck deck2 = new ImprovementDeck();
            assertThat(deck1).doesNotHaveSameHashCodeAs(deck2);
        }
    }
}
