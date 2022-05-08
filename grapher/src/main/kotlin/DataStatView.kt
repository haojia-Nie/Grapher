import javafx.geometry.*
import javafx.scene.control.*
import javafx.scene.layout.*

class DataStatView (
    private val model:Model,
    private val controller:Main) : HBox(), IView{

    // parameter initialization
    var num = Label("10")
    var min = Label("10")
    var max = Label("100")
    var avg = Label("55.0")
    var sum = Label("550")
    private val strBox = VBox()
    private val numBox = VBox()

    override fun updateView() {
        // updaete view
        val displayDataset = model.displayDataset
        if (displayDataset != null){
            num.text = displayDataset.data.size.toString()
            var minVal = 100
            var maxVal = 0
            var sumVal = 0
            for (i in displayDataset.data){
                if (i < minVal){
                    minVal = i
                }
                if (i > maxVal){
                    maxVal = i
                }
                sumVal += i
            }

            min.text = minVal.toString()
            max.text = maxVal.toString()
            sum.text = sumVal.toString()
            avg.text = String.format("%.1f", sumVal.toDouble() / displayDataset.data.size.toDouble())
        }
    }

    init {
        // initialization
        val numT = Label("Number")
        val minT = Label("Minimum")
        val maxT = Label("Maximum")
        val avgT = Label("Average")
        val sumT = Label("Sum")

        // set property
        numBox.spacing = 10.0
        strBox.spacing = 10.0
        numBox.children.addAll(num, min, max, avg, sum)
        strBox.children.addAll(numT, minT, maxT, avgT, sumT)
        children.addAll(strBox, numBox)
        spacing = 10.0
        padding = Insets(10.0)
        minWidth = 125.0
        maxWidth = 125.0

        model.addView(this)
    }
}