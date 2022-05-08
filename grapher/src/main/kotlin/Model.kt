
class Model {

    // views
    private val views:ArrayList<IView> = ArrayList()
    // current dataset being displayed
    var displayDataset:DataSet? = null
    var changed:Boolean = false
    // list of all datasets
    private val allDataset = mutableListOf<DataSet>()
    private val mapDataset = mutableMapOf<String, DataSet>()
    var numNew = 0
    var ifNew = false

    val fadeData = mutableListOf<Int>()

    // add view in
    fun addView(view:IView){
        views.add(view)
        view.updateView()
    }

    // notify observers and updates view
    private fun notifyObservers(){
        for (view in views){
            view.updateView()
        }
    }

    //

    fun fadeData(index:Int){
        fadeData.add(index)
        changed = false
        ifNew = false
        notifyObservers()
    }

    // adjust the scene size
    fun updateSize(){
        notifyObservers()
        changed = false
        ifNew = false
    }

    // click New button and add new dataset
    fun addNewDataset(dataSet: DataSet){
        numNew += 1
        allDataset.add(dataSet)
        mapDataset["New${numNew}"] = dataSet
        displayDataset = dataSet
        changed = true
        ifNew = true
        fadeData.clear()
        notifyObservers()
    }

    // adjust the spinner and update data
    fun updateData(index:Int, value:Int){
        if (displayDataset != null){
            displayDataset!!.data[index] = value
        }
        changed = false
        ifNew = false
        notifyObservers()
    }

    // update dataset
    fun updateDataset(name:String){
        debug(name)
        var dataset = mapDataset[name]
        if (dataset == null) {
            dataset = createTestDataSet(name)
            if (dataset != null) {
                print("updated")
                allDataset.add(dataset) // add it too all Dataset
                mapDataset[name] = dataset // insert in the map
                displayDataset = dataset
            }
        }
        else {
            displayDataset = dataset
        }
        ifNew = false
        changed = true
        fadeData.clear()
        notifyObservers()
    }

    // change title
    fun updateTitle(titleStr:String){

        if (displayDataset == null){
            print("is null\n")
        }
        else if (displayDataset != null){
            debug("updated title\n")
            displayDataset!!.title = titleStr
            debug(displayDataset!!.title + "\n")
        }
        ifNew = false
        changed = false
        notifyObservers()
    }

    // change x axis title
    fun updateXAxis(xStr:String){
        if (displayDataset != null){
            displayDataset!!.xAxis = xStr
        }
        ifNew = false
        changed = false
        notifyObservers()
    }

    // change y axis title
    fun updateYAxis(yStr:String){
        if (displayDataset != null){
            displayDataset!!.yAxis = yStr
        }
        ifNew = false
        changed = false
        notifyObservers()
    }
}