import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.scene.text.TextFlow

class DataTableView(
    private val model:Model,
    private val controller:Main): HBox(), IView {

    override fun updateView() {
        if (model.changed) {
            val curDataset = model.displayDataset
            if (curDataset != null) {
                val data = curDataset.data
                createSpinner(data)
            }
        }
    }

    val numBox = VBox()
    val spinBox = VBox()
    val spinners = mutableListOf<Spinner<Int>>()

    private fun createSpinner(list: List<Int>){

        if (model.changed) {
            // initialize
            numBox.children.clear()
            spinBox.children.clear()

            val num = list.size
            for (i in (0..(num - 1))) {

                var spinner = Spinner<Int>(0, 100, list[i])
                spinner.maxWidth = 70.0
                var numT = Text("${i + 1}: ")

                spinner.onMouseClicked = EventHandler {
                    model.updateData(i, spinner.value)
                }

                numBox.children.add(numT)
                spinBox.children.add(spinner)
                spinners.add(spinner)
            }
        }
    }

    init {

        // property setting
        numBox.padding = Insets(6.0)
        numBox.spacing = 10.25

        // property for hbox
        children.addAll(numBox, spinBox)
        padding = Insets(10.0, 10.0, 10.0, 10.0)
        minWidth = 150.0
        prefWidth = 150.0
        border = simpleStroke(Color.LIGHTGRAY)
        model.addView(this)
    }

}