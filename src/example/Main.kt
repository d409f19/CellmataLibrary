package example

import library.CellularAutomata2D
import library.State
import java.awt.Color

val Alive = State(Color.BLACK, ::aliveLogic)
val Dead = State(Color.WHITE, ::deadLogic)

fun aliveLogic(): State {
    return Dead
}

fun deadLogic(): State {
    return Alive
}

fun main() {
    val ca = CellularAutomata2D(120, 80)
    ca.register(Alive)
    ca.register(Dead)
    ca.start()
}