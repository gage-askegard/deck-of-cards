package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource

/**
 * Test class to demo JUnit 5 features
 */
class DeckOfCardsDemoTest {
    @Nested
    @DisplayName("When dealing cards")
    inner class DealCardTests {
        @Test
        @DisplayName("an empty deck should not return a card")
        fun testDealEmptyDeck() {
            val deck = DeckOfCards(ArrayList(), ArrayList())

            assertTrue(deck.unDealtCards.isEmpty(), "Deck should be empty")
            assertNull(deck.deal_card(), "Expected null when deal is called on an empty deck")
        }

        @Test
        @DisplayName("a full deck can deal all cards")
        fun testFullDeck() {
            val deckOfCards = DeckOfCards()
            assertTrue(deckOfCards.dealtCards.isEmpty(), "A new deck should not have any cards dealt")

            var dealCount = 0
            CardSuit.entries.forEach { suit ->
                FaceValue.entries.forEach { faceValue ->

                    val dealtCard = deckOfCards.deal_card()
                    assertFalse(
                        deckOfCards.unDealtCards.contains(dealtCard),
                        "A dealt card should be removed from the un-dealt list"
                    )

                    assertTrue(deckOfCards.dealtCards.contains(dealtCard), "A dealt card should be in the dealt list")
                    dealCount++
                }
            }

            assertTrue(
                deckOfCards.unDealtCards.isEmpty(),
                "The deck should be empty after ${DeckOfCardsTest.STANDARD_CARD_COUNT} cards are dealt"
            )
            assertEquals(dealCount, deckOfCards.dealtCards.size, "All dealt cards should be in dealtCards")
        }
    }

    @Nested
    inner class ParameterizedTests {
        @ParameterizedTest
        @EnumSource(value = CardSuit::class, names = ["HEARTS", "SPADES"])
        fun `testing suit to not be hearts`(suit: CardSuit) {
            assertNotEquals(CardSuit.HEARTS, suit, "I didn't want hearts!")
        }

        @ParameterizedTest
        @CsvSource(
            "1, ACE",
            "2, TWO",
            "3, THREE",
            "12, QUEEN"
        )
        fun `fromNumericValue should return face values by their numeric value`
                    (numericValue: Int, expectedValue: FaceValue) {
            assertEquals(expectedValue, FaceValue.fromNumericValue(numericValue), "Unexpected face value returned")
        }
    }
}