package cs349.a3battleship.ui
import cs349.a3battleship.Battleship
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment

class UserView (
    private val model: Game,
    private val controller: Battleship
) : VBox(), IView{


    override fun updateView() {
        val board = model.getBoard(Player.Human)
        updateBoard(board, gc)

        if (model.gameState == Game.GameState.WonHuman || model.gameState == Game.GameState.WonAI){
            endBoard(board)
        }
    }

    fun endBoard(board:Array<Array<CellState>>){
        val len = 10
        for (i in (0 until len)) {
            for (j in (0 until len)) {
                if (board[i][j] != CellState.ShipSunk) {
                    updateCell(i, j, Color.LIGHTBLUE, gc)
                }
                else if (board[i][j] == CellState.ShipSunk) {
                    updateCell(i, j, Color.DARKGRAY, gc)
                }
            }
        }
    }

    val lab = Label("My Formation")
    var bPane = BorderPane()
    var canvas = Canvas(300.0, 300.0)
    val gc = canvas.graphicsContext2D

    var digitLabT = HBox()
    var digitLabB = HBox()
    var charLabL = VBox()
    var charLabR = VBox()

    init {

        height = 375.0
        width = 350.0

        // title formation
        lab.prefHeight = 25.0
        lab.prefWidth = width
        lab.font = Font.font(null, FontWeight.BOLD, 16.0)
        lab.alignment = Pos.CENTER
        children.add(lab)

        // middle board display
        gc.fill = Color.LIGHTBLUE
        gc.fillRect(0.0, 0.0, 300.0, 300.0)


        // draw grid
        renderDisplayList(gc, canvas)
        bPane.center = canvas

        // create labels
        createDigitLab(digitLabT)
        createDigitLab(digitLabB)
        createCharLab(charLabL)
        createCharLab(charLabR)

        // digit label
        bPane.top = digitLabT
        bPane.bottom = digitLabB

        // character label
        bPane.left = charLabL
        bPane.right = charLabR

        children.add(bPane)
        bPane.prefHeight = 350.0
        bPane.prefWidth = 350.0
        bPane.maxHeight = 350.0
        bPane.maxWidth = 350.0

    }
}