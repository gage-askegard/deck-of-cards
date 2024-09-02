package models

/**
 * A representation of a card in a standard deck of playing cards
 *
 * @param suit the suit of the card
 * @param faceValue the face value of the card
 */
data class Card(
    val suit: CardSuit,
    val faceValue: FaceValue
)
