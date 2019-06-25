package library


class GridView2D(
    private val x: Int,
    private val y: Int,
    private val grid: Array<Array<State>>,
    private val ca: CellularAutomata2D
) {
    fun get(neighbourhood: Neighbourhood2D): LocalNeighbourhood2D {
        return LocalNeighbourhood2D(neighbourhood.relCoords.map { (rx, ry) ->
            get(x + rx, y + ry)
        })
    }

    fun get(x: Int, y: Int): State {
        if (ca.horizontalEdgeOption == EdgeOption.FINITE && x !in 0..ca.width) return ca.edgeState!!
        if (ca.verticalEdgeOption == EdgeOption.FINITE && y !in 0..ca.height) return ca.edgeState!!
        return grid[x wrap ca.width][y wrap ca.height]
    }

    private infix fun Int.wrap(divisor: Int) = (this % divisor).let { if (it < 0) it + divisor else it }
}