package models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * Tests for the DeckOfCards data class
 */
class DeckOfCardsTest {

    companion object {
        const val STANDARD_CARD_COUNT = 52
    }

    @Nested
    inner class DealingTests {
        
    }
    @Test
    fun testDealing() {
        val deckOfCards = DeckOfCards()

        assertEquals(
            STANDARD_CARD_COUNT,
            deckOfCards.unDealtCards.size,
            "A new deck should start with $STANDARD_CARD_COUNT cards when created using the no argument constructor"
        )
        assertTrue(deckOfCards.dealtCards.isEmpty(), "A new deck should not have any cards dealt")

        var dealCount = 0
        CardSuit.entries.forEach { suit ->
            FaceValue.entries.forEach { faceValue ->
                val expectedCard = Card(suit, faceValue)
                assertTrue(
                    deckOfCards.unDealtCards.contains(expectedCard),
                    "Deck is missing standard card $expectedCard"
                )

                val dealtCard = deckOfCards.deal_card()
                assertFalse(
                    deckOfCards.unDealtCards.contains(dealtCard),
                    "A dealt card should be removed from the un-dealt list"
                )

                assertTrue(deckOfCards.dealtCards.contains(dealtCard), "A dealt card should be in the dealt list")
                dealCount++
            }
        }

        assertEquals(
            STANDARD_CARD_COUNT,
            dealCount,
            "All combinations of CardSuit and FaceValue should be $STANDARD_CARD_COUNT"
        )
        assertTrue(
            deckOfCards.unDealtCards.isEmpty(),
            "The deck should be empty after $STANDARD_CARD_COUNT cards are dealt"
        )
        assertEquals(dealCount, deckOfCards.dealtCards.size, "All dealt cards should be in dealtCards")

        val extraCard = deckOfCards.deal_card()
        assertNull(extraCard, "No card should be returned after dealing a full standard deck")
    }

    @Test
    fun testShuffle() {
        val deckOfCards = DeckOfCards()
        val copy = ArrayList(deckOfCards.unDealtCards)

        for (i in 1..5) {
            deckOfCards.deal_card()
        }

        assertEquals(47, deckOfCards.unDealtCards.size, "Number of un-dealt cards was unexpected")
        assertEquals(5, deckOfCards.dealtCards.size, "Number of dealt cards was unexpected")

        deckOfCards.shuffle()
        assertEquals(
            STANDARD_CARD_COUNT,
            deckOfCards.unDealtCards.size,
            "All cards should be back in the un-dealt list after a shuffle"
        )
        assertTrue(deckOfCards.dealtCards.isEmpty(), "Dealt cards list should be empty after a shuffle")
        assertNotEquals(deckOfCards.unDealtCards, copy, "The order of un-dealt cards should be shuffled")

        val shuffledCopy = ArrayList(deckOfCards.unDealtCards)
        deckOfCards.shuffle()
        assertEquals(
            STANDARD_CARD_COUNT,
            deckOfCards.unDealtCards.size,
            "All cards should be in the un-dealt list after a shuffle"
        )
        assertNotEquals(deckOfCards.unDealtCards, copy, "The order of un-dealt cards should be shuffled")
        assertNotEquals(deckOfCards.unDealtCards, shuffledCopy, "The order of un-dealt cards should be shuffled")
    }

    @Test
    fun testCustomDeck() {
        val customCards = ArrayList<Card>()
        FaceValue.entries.forEach {
            customCards.add(Card(CardSuit.SPADES, it))
        }

        val customDeck = DeckOfCards(customCards)
        assertEquals(customCards, customDeck.unDealtCards, "DeckOfCards should allow a custom deck")
        assertTrue(customDeck.dealtCards.isEmpty(), "Expected dealt cards to be empty")

        val duplicateCard = Card(CardSuit.SPADES, FaceValue.FIVE)
        customDeck.unDealtCards.add(duplicateCard)
        val fiveOfSpadesCount = customDeck.unDealtCards.count { it == duplicateCard }
        assertEquals(2, fiveOfSpadesCount, "Custom decks should allow duplicate cards")
    }
}