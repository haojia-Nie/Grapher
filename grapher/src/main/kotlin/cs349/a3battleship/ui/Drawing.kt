package cs349.a3battleship.ui

// imported from demo code
import cs349.a3battleship.model.CellState
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

// return the border
fun simpleStroke(color: Color, width: Double = 1.0): Border {
    return Border(BorderStroke(color, BorderStrokeStyle.SOLID, null, BorderWidths(width)))
}

// return the background
fun simpleFill(color: Color): Background {
    return Background(BackgroundFill(color, null, null))
}

// grid GraphicsContext extension
fun GraphicsContext.grid(x: Double, y: Double, wh: Double, s: Double) {
    val gc = this
    var i = 0.0
    while (i <= wh) {
        gc.strokeLine(x + i, y, x + i, y + wh)
        gc.strokeLine(x, y + i, x + wh, y + i )
        i += s
    }
}

// bar GraphicsContext extension
fun GraphicsContext.bar(x1: Double, y1: Double, x2: Double, y2:Double) {
    val gc = this
    val d = 5.0
    gc.strokeLine(x1, y1, x2, y2)
    gc.fillOval(x1 - d/2, y1 - d/2, d, d)
    gc.fillOval(x2 - d/2, y2 - d/2, d, d)
}


fun renderDisplayList(gc:GraphicsContext, canvas:Canvas) {
    // draw the grid for reference
    gc.stroke = Color.BLACK
    gc.lineWidth = 1.0
    gc.grid(0.0, 0.0, 300.0, 30.0)
}

// create digit labels 1-10
fun createDigitLab(dLab: HBox){
    dLab.prefHeight = 25.0
    dLab.spacing = 22.6
    dLab.padding = Insets(2.0, 30.0, 2.0, 39.0)
    for (i in (1 until 11)) {
        val l = Label("${i}")
        // label setting
        l.font = Font.font(null, 12.0)
        l.prefHeight = 25.0
        l.isWrapText = false
        l.alignment = Pos.CENTER

        dLab.children.add(l)
    }
}

// create letter label A-J
fun createCharLab(cLab: VBox){
    cLab.prefWidth = 25.0
    cLab.prefHeight = 300.0
    cLab.spacing = 15.0
    cLab.padding = Insets(6.0, 0.0, 4.0, 0.0)
    for (i in (65 until  75)){
        val c = i.toInt().toChar()
        val l = Label("${c}")
        // label setting
        l.font = Font.font(null, 12.0)
        l.prefWidth = 25.0
        l.alignment = Pos.CENTER
        l.textAlignment = TextAlignment.CENTER
        cLab.children.add(l)
    }
}

// draw cell color
fun updateCell(i:Int, j:Int, color: Color, gc:GraphicsContext){
    gc.fill = color
    gc.fillRect(j * 30.0, i * 30.0, 30.0, 30.0)
    gc.stroke = Color.BLACK
    gc.strokeRect(j * 30.0, i*30.0, 30.0, 30.0)
}

// update board color
fun updateBoard(board:Array<Array<CellState>>, gc: GraphicsContext) {
    val len = 10
    for (i in (0 until len)) {
        for (j in (0 until len)) {
            if (board[i][j] == CellState.ShipHit) {
                updateCell(i, j, Color.BLACK, gc)
            } else if (board[i][j] == CellState.ShipSunk){
                updateCell(i, j, Color.DARKGRAY, gc)
            } else if (board[i][j] == CellState.Attacked) {
                updateCell(i, j, Color.LIGHTGRAY, gc)
            }
        }
    }
}




