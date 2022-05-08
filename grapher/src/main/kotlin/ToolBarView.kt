import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.event.*
import javafx.scene.paint.Color
import kotlin.random.Random

class ToolBarView(
    private val model:Model,
    private val controller:Main): HBox(), IView {

    private val dataText = Label("Dataset:")
    var cBox:ChoiceBox<String> = ChoiceBox(FXCollections.observableArrayList(
        "Increasing", "Large", "Middle", "Single", "Range", "Percentage"))

    private val newButton = Button("New")
    private val spinner:Spinner<Int> = Spinner(1, 20, 1)
    private val sep1 = Separator()

    override fun updateView() {
        // nothing needed I guess
       if (model.ifNew){
           val numNew = model.numNew
           val curDataset = model.displayDataset
           if (curDataset != null) {
               cBox.getItems().add("New${numNew}")
               cBox.value = "New${numNew}"
           }
       }
    }

    init {
        // default initialization
        cBox.value = "Increasing"
        sep1.orientation = Orientation.VERTICAL
        border = simpleStroke(Color.LIGHTGRAY)

        dataText.translateY = 5.0
        newButton.prefWidth = 80.0
        spinner.prefWidth = 70.0

        // property setting
        spacing = 10.0
        height = 50.0
        padding = Insets(10.0)
        children.addAll(dataText, cBox, sep1, newButton, spinner)
        model.addView(this)

        cBox.onAction = EventHandler { event: ActionEvent ->
            model.updateDataset(cBox.selectionModel.selectedItem)
        }

        newButton.onAction = EventHandler {
            var newDataset = createNewDataset(spinner.value)

            model.addNewDataset(newDataset)
        }
    }

    // create new dataset
    private fun createNewDataset(num: Int): DataSet {

        // generate title and x axis title
        val title = LoremIpsum.getRandomSequence(3)
            .split(" ")
            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }

        val word = LoremIpsum.getRandomSequence(1)
        val xAxis = word.substring(0, 1).uppercase() + word.substring(1).lowercase()
        val yAxis = "Value"
        // generate data
        val data = mutableListOf<Int>()
        for (i in (0 until num)) {
            val n = Random.nextInt(1, 100)
            data.add(n)
        }

        return DataSet(title, xAxis, yAxis, data)
    }

}