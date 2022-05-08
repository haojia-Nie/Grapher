import javafx.geometry.VPos
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.scene.transform.Affine
import javafx.scene.transform.Rotate


class DataDisplayView (
    private val model:Model,
    private val controller:Main): Pane(), IView {

    private val canvas = Canvas(525.0, 456.0)
    private val gc = canvas.graphicsContext2D

    private var num_data = 0
    private var currWidth = 0.0

    override fun updateView() {
        val curDataset = model.displayDataset
        if (curDataset != null) {
            gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
            createText(curDataset) // the x and y axis text
            createStrokes() // the x and y axis black stroke
            val data = curDataset.data
            val fadeData = model.fadeData
            createRectangles(data, fadeData) // draw the bar chart
        }
    }

    private fun createText(dataset:DataSet){

        val max: Int = dataset.data.maxOrNull() ?: 0
        gc.fill = Color.BLACK

        // creat number axis 0 and max
        gc.fillText("0", 30.0, canvas.height - 50.0)
        gc.fillText("${max}", 30.0, 50.0 )

        // create y axis
        gc.transform(Affine(Rotate(-90.0, 25.0, canvas.height.div(2))))
        gc.textAlign = TextAlignment.CENTER
        gc.textBaseline = VPos.CENTER
        gc.fillText(dataset.yAxis, 25.0,  canvas.height.div(2))
        gc.transform(Affine(Rotate(90.0, 25.0, canvas.height.div(2))))

        // x axis and title
        gc.textAlign = TextAlignment.CENTER
        gc.textBaseline = VPos.CENTER
        gc.fillText(dataset.title, canvas.width.div(2), 25.0)
        gc.fillText(dataset.xAxis, canvas.width.div(2), canvas.height.minus(25.0))

    }

    // draw axis stroke
    private fun createStrokes(){
        // initialize canvas
        gc.stroke = Color.LIGHTGRAY

        // stroke the light gray rec
        gc.strokeRect(50.0, 50.0, canvas.width - 100.0, canvas.height - 100.0)

        // axis line stroke
        gc.stroke = Color.BLACK
        gc.strokeLine(50.0, 50.0, 50.0, canvas.height - 50.0)
        gc.strokeLine(50.0, canvas.height - 50.0, canvas.width - 50.0, canvas.height - 50.0)
    }

    // create colors
    private fun createColors(num:Int): MutableList<Color> {

        var res = mutableListOf<Color>()
        var degreeStep = (360.0).div(num.toDouble())
        for (i in (0 until num)){
            val c:Color = Color.hsb(i.times(degreeStep),1.0,1.0)
            res.add(c)
        }
        return res
    }

    // draw rectangles
    private fun createRectangles(data:List<Int>, fadeData:List<Int>){

        val num = data.size
        var colors = createColors(num) // get rainbow colors correpond to each data
        val max: Int = data.maxOrNull() ?: 0
        // calculate the width for all bar
        val cWidth = (canvas.width  - 100.0 - (num +1) * 5.0).div(num.toDouble())
        currWidth = cWidth
        gc.lineWidth = 1.0


        for (i in (0 until num)){

            if( i in fadeData){
                continue
            }
            // calculate position of x, space by 5.0
            val x = 5.0 + i.toDouble().times(5.0 + cWidth) + 50.0
            // calculate the height
            val h = (data[i].toDouble().div(max.toDouble())).times(canvas.height - 100.0)
            gc.fill = colors[i]
            gc.stroke = Color.BLACK
            gc.fillRect(x, canvas.height - 50.0 - h, cWidth, h)
            gc.strokeRect(x, canvas.height - 50.0 - h, cWidth, h)
        }
    }

    private fun fadeEvent(event: javafx.scene.input.MouseEvent){

        print("fade\n")
        print(event.x)
        print("\n")
        print(event.y.toString())
        var x = event.x
        var y = event.y

        if (y <= (canvas.height-50.0) && y >= 50.0
            && x >= 55.0 && x <= (canvas.width - 55.0)){

            // test if it is inside rectange
            x -= 55
            y = canvas.height - 50.0 - y
            val curDataset = model.displayDataset
            val data = curDataset!!.data
            val num = data.size
            val max: Int = data.maxOrNull() ?: 0

            // find out which x
            val i =  (x.toDouble().div(currWidth + 5.0)).toInt()

            print(i)
            val h = (data[i].toDouble().div(max.toDouble())).times(canvas.height - 100.0)
            print("\n ${y} \n ${h}")

            if (y <= h){
                // within the range, trigger click event
                model.fadeData(i)
            }
        }
    }

    init {

        // set property and initialization
        canvas.heightProperty().bind(heightProperty())
        canvas.widthProperty().bind(widthProperty())


        canvas.setOnMouseClicked { event -> fadeEvent(event) }

        // call model when the height and width change
        heightProperty().addListener { observable, oldValue, newValue ->
            model.updateSize()
        }

        widthProperty().addListener { observable, oldValue, newValue ->
            model.updateSize()
        }

        children.add(canvas)
        background = simpleFill(Color.WHITE)
        model.addView(this)
    }
}
