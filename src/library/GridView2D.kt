package library


class GridView2D(
    private val x: Int,
    private val y: Int,
    private val grid: Array<Array<State>>,
    private val ca: CellularAutomata2D
) {
    fun get(neighbourhood: Neighbourhood2D): LocalNeighbourhood2D {
        return LocalNeighbourhood2D(neighbourhood.relCoords.map { (rx, ry) ->
            get(rx, ry)
        })
    }

    fun get(relX: Int, relY: Int): State {
        val absX = x + relX
        val absY = y + relY
        if (ca.horizontalEdgeOption == EdgeOption.FINITE && absX !in 0..ca.width) return ca.edgeState!!
        if (ca.verticalEdgeOption == EdgeOption.FINITE && absY !in 0..ca.height) return ca.edgeState!!
        return grid[absX wrap ca.width][absY wrap ca.height]
    }

    private infix fun Int.wrap(divisor: Int) = (this % divisor).let { if (it < 0) it + divisor else it }
}