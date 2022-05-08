import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

class StatusView
    (private val model:Model,
     private val controller:Main):HBox(), IView {

    // default parameters
    var number = Label("6")
    private val dataset = Label("datasets")

    override fun updateView() {
        // indicates the current number
        val new = model.numNew
        number.text = "${6 + new}"
    }

     init {
        // property settings
         children.addAll(number, dataset)
         spacing = 5.0
         background = simpleFill(Color.LIGHTGRAY)
         border = simpleStroke(Color.LIGHTGRAY)
         padding = Insets(10.0)
         height = 50.0
         model.addView(this)

     }
}