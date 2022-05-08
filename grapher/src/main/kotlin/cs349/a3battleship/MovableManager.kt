package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.Ship
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent


class MovableManager(parent: Node, private val model:Game) {

    private var movingNode: Node? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0
    private var type: ShipType = ShipType.Battleship // default

    private var Xloc = mutableMapOf<ShipType, Double>()
    private var Yloc = mutableMapOf<ShipType, Double>()

    var transformer: transformer? = null
    var orientation:Orientation = Orientation.VERTICAL
    var placedShip = mutableMapOf<ShipType, Cell>()
    var placedOrientation = mutableMapOf<ShipType, Orientation>()
    var placedBefore = mutableListOf<ShipType>()

    var trigger = true

    init {

        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->

            if (trigger) {
                val node = movingNode
                if (node != null) {
                    node.translateX = e.sceneX + offsetX
                    node.translateY = e.sceneY + offsetY
                    // we don't want to drag the background too
                    e.consume()
                }
            }
        }

        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            if (trigger) {

                // right click
                if (e.button == MouseButton.PRIMARY) {
                    val node = movingNode
                    if (node != null) {

                        transformer!!.clear()
                        val inside = transformer?.inGrid(type, e.sceneX, e.sceneY)
                        if (inside == false) {
                            model.deselectShip(type)
                            movingNode = null
                        } else {
                            val pos: Cell? = transformer?.inCell(orientation, type, e.sceneX, e.sceneY)

                            node.translateX = e.sceneX + offsetX + transformer!!.offsetX
                            node.translateY = e.sceneY + offsetY + transformer!!.offsetY
                            e.consume()

                            model.humanPlaceShip(Player.Human, type, orientation, pos!!)
                            placedOrientation[type] = orientation

                            movingNode = null
                            orientation = Orientation.VERTICAL
                            placedShip[type] = pos!!
                            placedBefore.add(type)

                        }
                    }
                    // left click
                } else if (e.button == MouseButton.SECONDARY) {

                    val node = movingNode
                    if (node != null) {

                        if (type in placedBefore){
                            orientation = placedOrientation[type]!!
                        }

                        // rotation
                        node.rotate = 270.0 + node.rotate
                        if (orientation == Orientation.VERTICAL) {
                            orientation = Orientation.HORIZONTAL
                        } else {
                            orientation = Orientation.VERTICAL
                        }
                    }
                }
            }
        }
    }

    // make object movable
    fun makeMovable(node: Node, shipType: ShipType) {

        node.onMouseClicked = EventHandler { e ->

            if (trigger) {
                if (movingNode == null) {
                    println("click '$node'")
                    this.movingNode = node
                    offsetX = node.translateX - e.sceneX
                    offsetY = node.translateY - e.sceneY
                    type = shipType

                    if (placedShip[type] != null) {
                        model.removeShip(Player.Human, placedShip[type]!!)
                        model.addShip(type)
                        placedShip.remove(type)
                        println("recovered")
                        println(transformer!!.Xloc[type])
                        println(transformer!!.Xloc_orig[type])
                    }

                    else if (type in placedBefore){
                        placedBefore.remove(type)
                        transformer!!.Xloc[type] = transformer!!.Xloc_orig[type]!!
                        transformer!!.Yloc[type] = transformer!!.Yloc_orig[type]!!
                    }

                    // get the mouse position relative to the rectangle
                    Xloc[type] = e.sceneX
                    Yloc[type] = e.sceneY

                    transformer!!.mXloc[type] = e.sceneX
                    transformer!!.mYloc[type] = e.sceneY

                    // we don't want to drag the background too
                    e.consume()

                }
            }
        }
    }

    // ad in location data
    fun addLocationData(shipXLocation: MutableMap<ShipType, Double>, shipYLocation: MutableMap<ShipType, Double>, centerYLocation:Map<ShipType, Double> ){
        transformer = transformer(shipXLocation, shipYLocation, centerYLocation, Xloc, Yloc)
    }
}



