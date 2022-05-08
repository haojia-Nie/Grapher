package cs349.a3battleship

import cs349.a3battleship.model.Game
import cs349.a3battleship.ui.AIView
import cs349.a3battleship.ui.FleetView
import cs349.a3battleship.ui.UserView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class Battleship : Application() {

    val model = Game(10, true)

    var myStage:Stage? = null

    override fun start(stage: Stage) {


        myStage = stage

        // scene setting
        val root = BorderPane()
        val userView = UserView(model, this)
        val mover = MovableManager(root, model)
        val aiView = AIView(model, this)
        val fleetView = FleetView(model, this, mover)

        val ai = AI(model)

        model.addView(userView)
        model.addView(fleetView)
        model.addView(aiView)


        root.left = userView
        root.right = aiView
        root.center = fleetView

        // construct scene
        val scene = Scene(root, 875.0, 375.0)
        stage.minWidth = 875.0
        stage.minHeight = 375.0
        stage.title = "A3 BattleShip (h7nie)"
        stage.scene = scene
        stage.show()
    }

    // close window
    fun closeWindow(){
        myStage!!.close()
    }
}