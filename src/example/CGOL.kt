package example

import library.*
import java.awt.Color


val ALIVE = State(Color.BLACK, ::aliveLogic)
val DEAD = State(Color.WHITE, ::deadLogic)

fun aliveLogic(grid: GridView2D): State {
    val n = grid.get(Neighbourhood2D.MOORE).count(ALIVE)
    if (n !in 2..3) {
        return DEAD
    }
    return ALIVE
}

fun deadLogic(grid: GridView2D): State {
    val n = grid.get(Neighbourhood2D.MOORE).count(ALIVE)
    if (n == 3) {
        return ALIVE
    }
    return DEAD
}

fun main() {
    val ca = CellularAutomata2D(80, 60, tickdelay = 70)
    ca.register(Neighbourhood2D.MOORE)
    ca.register(ALIVE)
    ca.register(DEAD)
    ca.start()
}