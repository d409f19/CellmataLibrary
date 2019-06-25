package example

import library.*
import java.awt.Color

val ROCK = State(Color.RED, ::rockLogic)
val PAPER = State(Color.GREEN, ::paperLogic)
val SCISSORS = State(Color.BLUE, ::scissorsLogic)

fun rockLogic(grid: GridView2D): State {
    val n = grid.get(Neighbourhood2D.MOORE).count(PAPER)
    if (n >= 3) {
        return PAPER
    }
    return ROCK
}

fun paperLogic(grid: GridView2D): State {
    val n = grid.get(Neighbourhood2D.MOORE).count(SCISSORS)
    if (n >= 3) {
        return SCISSORS
    }
    return PAPER
}

fun scissorsLogic(grid: GridView2D): State {
    val n = grid.get(Neighbourhood2D.MOORE).count(ROCK)
    if (n >= 3) {
        return ROCK
    }
    return SCISSORS
}

fun main() {
    val ca = CellularAutomata2D(80, 80, tickdelay = 50)
    ca.start(ROCK, PAPER, SCISSORS)
}