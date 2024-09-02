package models

import kotlin.random.Random

/**
 * Data class to represent a standard deck of playing cards with capabilities to deal and shuffle
 *
 * @param unDealtCards The ordered collection of cards that have not yet been dealt. If {@code null}, this will be an empty list
 * @param dealtCards The ordered collection of cards that have already been dealt. If {@code null}, this will be an empty list
 */
data class DeckOfCards(
    val unDealtCards: MutableList<Card> = ArrayList(),
    val dealtCards: MutableList<Card> = ArrayList()
) {
    /**
     * No argument constructor to create a new standard deck of 52 playing cards containing all combinations of
     * card suit and face value
     */
    constructor() : this(ArrayList(), ArrayList()) {
        CardSuit.entries.forEach { suit ->
            FaceValue.entries.forEach { faceValue ->
                unDealtCards.add(Card(suit, faceValue))
            }
        }
    }

    /**
     * Returns all cards to unDealtCards and shuffles their order
     */
    fun shuffle() {
        dealtCards.addAll(unDealtCards)
        unDealtCards.clear()
        for (i in 1..dealtCards.size) {
            unDealtCards.add(dealtCards.removeAt(Random.nextInt(0, dealtCards.size)))
        }
        dealtCards.clear()
    }

    /**
     * Deals a new card from the un-dealt list. The dealt card is returned and will not be returned again until shuffle is called
     */
    fun deal_card(): Card? {
        if (unDealtCards.isEmpty()) {
            return null
        }

        val dealtCard = unDealtCards.first()
        unDealtCards.remove(dealtCard)
        dealtCards.add(dealtCard)
        return dealtCard
    }
}
