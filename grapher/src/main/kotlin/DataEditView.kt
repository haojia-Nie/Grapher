import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class DataEditView(
    private val model:Model,
    private val controller:Main) : HBox(), IView{

    // parameters
    var titleT = TextField("Increasing from 0 to 100")
    var xAxisT = TextField("Datapoint")
    var yAxisT = TextField("Value")

    // update view
    override fun updateView() {
        if (model.changed)
        {
            val displayDataset = model.displayDataset
            if (displayDataset != null){
                titleT.text = displayDataset.title
                xAxisT.text = displayDataset.xAxis
                yAxisT.text = displayDataset.yAxis
            }
        }
    }

    init {
        // initialize
        val titleL = Label("Title:")
        val xAxisL = Label("X-Axis:")
        val yAxisL = Label("Y-Axis:")

        titleL.translateY = 5.0
        xAxisL.translateY = 5.0
        yAxisL.translateY = 5.0

        titleT.isEditable = true
        xAxisT.isEditable = true
        yAxisT.isEditable = true

        children.addAll(titleL, titleT, xAxisL, xAxisT, yAxisL, yAxisT)

        spacing = 10.0
        padding = Insets(10.0)
        model.addView(this)

        // set event
        titleT.onKeyReleased = EventHandler {
            controller.saveTitle(titleT.text)
        }

        xAxisT.onKeyReleased = EventHandler {
            controller.saveXAxis(xAxisT.text)
        }

        yAxisT.onKeyReleased = EventHandler {
            controller.saveYAxis(yAxisT.text)
        }

    }
}