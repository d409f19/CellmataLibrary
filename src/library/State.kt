package library

import java.awt.Color
import kotlin.reflect.KFunction


class State(val color: Color, val transitionLogic: KFunction<State?>)