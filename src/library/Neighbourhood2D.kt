package library


class Neighbourhood2D(val relCoords: List<Pair<Int, Int>>) {
    companion object {
        val MOORE = Neighbourhood2D(
            listOf(
                -1 to 1, 0 to 1, 1 to 1,
                -1 to 0, /* <> */ 1 to 0,
                -1 to -1, 0 to -1, 1 to -1
            )
        )

        val VON_NEUMAN = Neighbourhood2D(
            listOf(
                /* -- */ 0 to 1, /* -- */
                -1 to 0, /* <> */ 1 to 0,
                /* -- */0 to -1 /* -- */
            )
        )
    }
}

class LocalNeighbourhood2D(private val neighbourStates: List<State>) {
    fun count(state: State): Int {
        return neighbourStates.count { it == state }
    }

    operator fun get(n: Int): State = neighbourStates[n]
}