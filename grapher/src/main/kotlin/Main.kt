import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage

class Main : Application() {
    val model = Model()

    override fun start(stage: Stage) {

        // create root
        val root = BorderPane()
        root.top = ToolBarView(model, this) // tool bar
        root.bottom = StatusView(model, this) // status

        // create the inner border pane
        val main = BorderPane()
        root.center = main
        main.top = DataEditView(model, this)
        main.right = DataStatView(model, this)
        val sp = ScrollPane(DataTableView(model, this))
        sp.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        main.left = sp
        main.center = DataDisplayView(model, this)

        // construct scene
        val scene = Scene(root, 800.0, 600.0)
        stage.minWidth = 600.0
        stage.minHeight = 400.0
        stage.title = "A1 Notes (h7nie)"
        stage.scene = scene
        stage.show()

        // display the first dataset -- initialization to default view
        model.updateDataset("Increasing")
    }

    fun saveTitle(titleStr:String){
        model.updateTitle(titleStr)
    }

    fun saveXAxis(xStr:String){
        model.updateXAxis(xStr)
    }

    fun saveYAxis(yStr:String){
        model.updateYAxis(yStr)
    }
}
