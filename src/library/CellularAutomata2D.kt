package library

import java.awt.Dimension
import java.awt.Graphics2D
import java.lang.IllegalStateException
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.collections.HashMap


class CellularAutomata2D(
    val width: Int,
    val height: Int,
    val horizontalEdgeOption: EdgeOption = EdgeOption.WRAPPING,
    val verticalEdgeOption: EdgeOption = EdgeOption.WRAPPING,
    val edgeState: State? = null,
    val cellSize: Int = 8,
    val tickdelay: Long = 200
) {
    private val states: MutableList<State> = mutableListOf()
    private val neighbourhoods: MutableList<Neighbourhood2D> = mutableListOf()

    init {
        if (width <= 0) throw IllegalArgumentException("Width must be positive.")
        if (height <= 0) throw IllegalArgumentException("Height must be positive.")
        if (cellSize <= 0) throw IllegalArgumentException("Cell size must be positive.")
        if (tickdelay <= 0) throw IllegalArgumentException("Tick delay size must be positive.")
    }

    fun register(state: State) = states.add(state)
    fun register(neighbourhood2D: Neighbourhood2D) = neighbourhoods.add(neighbourhood2D)

    fun start() {

        if (states.size == 0) throw IllegalStateException("No states registered.")

        var grid = Array(width) { Array(height) { states.random() } }
        var nextGrid = Array(width) { Array(height) { states[0] } }

        // Setup GUI
        val frame = JFrame("Cellmata")
        val panel = frame.add(JPanel())
        panel.preferredSize = Dimension(width * cellSize, height * cellSize)
        frame.isResizable = false
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.pack()
        frame.isVisible = true
        val g = panel.graphics as Graphics2D

        // Driver
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                // Iterate over all cells, find their new state, and draw the new state
                for ((x, row) in grid.withIndex()) {
                    for ((y, state) in row.withIndex()) {

                        val currentState = grid[x][y]
                        val gridView = evaluateGridView(grid, x, y)
                        val newState = (currentState.transitionLogic.call(gridView).takeIf { it is State } ?: state)

                        nextGrid[x][y] = newState

                        // Draw
                        g.color = newState.color
                        g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize)
                    }
                }

                // Swap the grids
                val tmp = grid
                grid = nextGrid
                nextGrid = tmp
            }
        }, 0, tickdelay)
    }

    private fun evaluateGridView(grid: Array<Array<State>>, x: Int, y: Int): GridView2D {
        val localNeighbours = HashMap<Neighbourhood2D, LocalNeighbourhood2D>()
        for (neighbourhood in neighbourhoods) {
            localNeighbours[neighbourhood] = LocalNeighbourhood2D(neighbourhood.relCoords.map { (rx, ry) ->
                getState(grid, x + rx, y + ry)
            })
        }
        return GridView2D(x, y, localNeighbours)
    }

    private fun getState(grid: Array<Array<State>>, x: Int, y: Int): State {
        if (horizontalEdgeOption == EdgeOption.FINITE && x !in 0..width) return edgeState!!
        if (verticalEdgeOption == EdgeOption.FINITE && y !in 0..height) return edgeState!!
        return grid[x wrap width][y wrap height]
    }

    private infix fun Int.wrap(divisor: Int) = (this % divisor).let { if (it < 0) it + divisor else it }
}