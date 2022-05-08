package cs349.a3battleship.ui
import cs349.a3battleship.AI
import cs349.a3battleship.Battleship
import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import kotlin.math.floor


class AIView (
    private val model:Game,
    private val controller:Battleship) : VBox(), IView{

    override fun updateView() {

        // update board
        val board = model.getBoard(Player.AI)
        updateBoard(board, gc)

        // decide on clickable
        if (model.gameState == Game.GameState.WonAI ||
            model.gameState == Game.GameState.WonHuman){
            clickable = false
        }
    }

    // properties
    val lab = Label("Opponent's Formation")
    var bPane = BorderPane()
    var canvas = Canvas(300.0, 300.0)
    val gc = canvas.graphicsContext2D
    var clickable = true

    var digitLabT = HBox()
    var digitLabB = HBox()
    var charLabL = VBox()
    var charLabR = VBox()


    // click on cell/attack on AI
    fun clickCell(event: javafx.scene.input.MouseEvent){

        if (clickable) {
            var x = event.x
            var y = event.y

            // get cell
            val cellY = floor(event.y / 30.0)
            val cellX = floor(event.x / 30.0)

            if (model.gameState == Game.GameState.FireHuman) {
                model.attackCell(Cell(cellX.toInt(), cellY.toInt()))
            }
        }
    }


    init {
        height = 375.0
        width = 355.0

        canvas.setOnMouseClicked { event -> clickCell(event) }

        // title formation
        lab.prefHeight = 25.0
        lab.prefWidth = width
        lab.font = Font.font(null, FontWeight.BOLD, 16.0)
        lab.alignment = Pos.CENTER
        children.add(lab)

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

        // middle board display
        gc.fill = Color.LIGHTBLUE
        gc.fillRect(0.0, 0.0, 300.0, 300.0)

        // draw grid
        renderDisplayList(gc, canvas)
        bPane.center = canvas


        children.add(bPane)
        bPane.prefHeight = 350.0
        bPane.prefWidth = 355.0
        bPane.maxHeight = 350.0
        bPane.maxWidth = 355.0

    }

}