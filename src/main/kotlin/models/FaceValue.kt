package models

/**
 * An enum to represent the face values in a standard deck of playing cards, i.e. Ace, 2-10, Jack, Queen, King
 */
enum class FaceValue(
    val numericValue:Int
) {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    companion object {
        fun fromName(name: String): FaceValue? {
            for (faceValue: FaceValue in FaceValue.entries) {
                if (faceValue.name.lowercase() == name.lowercase()) {
                    return faceValue
                }
            }
            return null
        }

        fun fromNumericValue(value: Int): FaceValue? {
            for (faceValue: FaceValue in FaceValue.entries) {
                if (faceValue.numericValue == value) {
                    return faceValue
                }
            }
            return null
        }
    }
}