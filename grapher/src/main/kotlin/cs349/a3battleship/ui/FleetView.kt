package cs349.a3battleship.ui

import cs349.a3battleship.Battleship
import cs349.a3battleship.MovableManager
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight


class FleetView (
    private val model: Game,
    private val controller: Battleship,
    private val mover: MovableManager
) : BorderPane(), IView{

    // title
    val lab = Label("My Fleet")
    var labText = SimpleStringProperty("My Fleet")

    // button
    val exit = Button("Exit Game")
    val start = Button("Start Game")
    var clicked = false

    // ship
    var displayShip = mutableMapOf<ShipType, Boolean>()
    var shipXLocation = mutableMapOf<ShipType, Double>()
    var YLocation = 35.0
    var centerYLocation = mutableMapOf<ShipType, Double>()
    var leftPadding = 10.0
    var shipWidth = 30.0
    var initialized = false
    var colors = mutableListOf<Color>(Color.BLUE, Color.LIGHTGREEN, Color.YELLOW, Color.LIGHTSALMON, Color.LIGHTPINK)

    var buttonBox = VBox()

    // fleet
    var fleet = HBox()
    var fleetRec = mutableMapOf<ShipType, Rectangle>()
    var placedAll = SimpleBooleanProperty(true)
    override fun updateView() {

        if (model.gameState != Game.GameState.SetupHuman &&
                model.gameState != Game.GameState.Init){
            // freeze the movement of node
            mover.trigger = false
        }

        // update displayable
        if (model.placedShip != null) {
            displayShip[model.placedShip!!] = false
        }

        if (model.recoverShip != null){
            displayShip[model.recoverShip!!] = true
        }

        // human won, change display text, bring ship back
        if (model.gameState == Game.GameState.WonHuman){
            labText.value = "You Won!"
            val floatShips = model.getFloatShips(Player.Human)
            for (ship in floatShips){
                println(ship.shipType)
                displayShip[ship.shipType] = true
            }
        }
        // AI won, change display text
        else if (model.gameState == Game.GameState.WonAI){
            labText.value = "You were defeated!"
        }

        // update button
        var allSet = true
        for (i in displayShip.values){
            if (i == true){
                allSet = false
                break
            }
        }

        placedAll.value = !(allSet && !clicked)

        drawFleet()
    }

    fun drawRec(color: Color, x:Double, h:Double, type: ShipType){
        // draw single rectangle fleet
        val rec = Rectangle(x, 60.0, 25.0, h)
        with (rec) {
            fill = color
            mover.makeMovable(rec, type)
        }
        centerYLocation[type] = h.div(2.0)
        fleet.children.add(rec)
        fleetRec[type] = rec
    }
    fun drawSingleShip(k: ShipType, i:Int){
        // draw single ship
        val start = 7.5
        val width = 31.0
        val lengh = model.getShipLength(k)

        if (displayShip[k] == true){
            // draw
            drawRec(colors[i], start + i * width, 30.0 * lengh - 5.0, k)
            shipXLocation[k] = leftPadding + shipWidth * i + 350.0
        }

        else if (displayShip[k] == false){
            fleet.children.add(fleetRec[k])
        }
    }
    // draw the fleet
    fun drawFleet(){
        fleet.children.clear()

        println(displayShip)
        // one by one drawing
        var index = 0
        for (k in model.getShipsToPlace()){
            drawSingleShip(k, index)
            index += 1
        }

        // initialize the mover if not initialized yet
        if (!initialized) {

            var shipYLocation = mutableMapOf<ShipType, Double>()
            for (i in ShipType.values()) {
                shipYLocation[i] = YLocation
            }

            mover.addLocationData(shipXLocation, shipYLocation, centerYLocation)
            initialized = true
        }
    }


    init {
        height = 375.0
        width = 170.0

        // title formation
        lab.prefHeight = 25.0
        lab.prefWidth = width
        lab.font = Font.font(null, FontWeight.BOLD, 16.0)
        lab.alignment = Pos.CENTER
        lab.textProperty().bind(labText)

        // add displayable
        for (k in model.getShipsToPlace()){
            displayShip[k] = true
        }

        // canvas -- ships
        drawFleet()
        fleet.spacing = 5.0
        fleet.padding = Insets(10.0, 12.5, 10.0, 12.50)

        // button setting
        start.prefWidth = 150.0
        start.prefHeight = 20.0
        start.translateX = 7.50
        start.disableProperty().bind(placedAll)

        exit.prefWidth = 150.0
        exit.prefHeight = 20.0
        exit.translateX = 7.50

        buttonBox.prefHeight = 50.0
        buttonBox.spacing = 5.0

        start.onAction = EventHandler {
            model.startGame()
            clicked = true
            print("CLICKED")
            placedAll.value = true
        }

        exit.onAction = EventHandler {
            controller.closeWindow()
        }

        buttonBox.children.add(start)
        buttonBox.children.add(exit)
        bottom = buttonBox

        top = lab
        center = fleet
        padding = Insets(0.0, 0.0, 20.0, 0.0)
        model.startGame()

    }
}