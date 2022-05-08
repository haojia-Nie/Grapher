import javafx.scene.layout.*
import javafx.scene.paint.Color

// debug message
fun debug(msg: String, show: Boolean = true) {
    if (show) println("DBG: $msg \n")
}

// return the border
fun simpleStroke(color: Color, width: Double = 1.0): Border {
    return Border(BorderStroke(color, BorderStrokeStyle.SOLID, null, BorderWidths(width)))
}

// return the background
fun simpleFill(color: Color): Background {
    return Background(BackgroundFill(color, null, null))
}