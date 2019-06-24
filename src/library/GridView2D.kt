package library

import java.lang.IllegalStateException


class GridView2D(
    private val x: Int,
    private val y: Int,
    private val neighbourhoods: Map<Neighbourhood2D, LocalNeighbourhood2D>
) {
    fun get(neighbourhood: Neighbourhood2D): LocalNeighbourhood2D {
        return neighbourhoods[neighbourhood] ?: throw IllegalStateException("Unregistered neighbourhood.")
    }
}