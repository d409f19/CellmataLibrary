package library

import java.awt.Color
import kotlin.reflect.KFunction1


class State(val color: Color, val transitionLogic: KFunction1<GridView2D, State?>)