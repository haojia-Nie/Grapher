
package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.ships.ShipType
import kotlin.math.ceil


class transformer(
    shipXLocation: MutableMap<ShipType, Double>, shipYLocation: MutableMap<ShipType, Double>,
    centerYLocation:Map<ShipType, Double>, mouseXlocation:MutableMap<ShipType, Double>,
    mouseYLocation:MutableMap<ShipType, Double>) {

    var Xloc = shipXLocation
    var Yloc = shipYLocation

    var Xloc_orig = mutableMapOf<ShipType, Double>()
    var Yloc_orig = mutableMapOf<ShipType, Double>()

    val YcenterLoc = centerYLocation

    var mXloc = mouseXlocation
    var mYloc = mouseYLocation

    var curX = 0.0
    var curY = 0.0

    var mdiffX = 0.0
    var mdiffY = 0.0

    var offsetX = 0.0
    var offsetY = 0.0

    init {
        Xloc_orig.putAll(Xloc)
        Yloc_orig.putAll(Yloc)
    }
    fun clear(){
        offsetX = 0.0
        offsetY = 0.0
        mdiffX = 0.0
        mdiffY = 0.0
        curX = 0.0
        curY = 0.0
    }
    fun inGrid(type: ShipType, sX: Double, sY: Double): Boolean {

        // get mouse relative position on rectangle
        mdiffX = mXloc[type]?.minus(Xloc[type]!!)!!
        mdiffY = mYloc[type]?.minus(Yloc[type]!!)!!

        // determine the difference between mouse click rec place and rec central point
        val cX = 12.5 - mdiffX!!
        val cY = YcenterLoc[type]?.minus(mdiffY!!)

        var s_X = sX + cX
        var s_Y = sY + cY!!

        // in the grid
        if (s_X in 25.0..325.0 && s_Y <= 350.0 && s_Y >= 50.0) {
            curX = s_X
            curY = s_Y
            return true
        }
        return false
    }

    fun inCell(direction: Orientation, type: ShipType, sX:Double, sY:Double): Cell {
        // left corner
        val lx_V = sX - mdiffX - 25.0
        val ly_V = sY - mdiffY - 50.0

        val cX = lx_V + 12.5
        val cY = ly_V + YcenterLoc[type]!!

        // determine grid position
        val gX = curX - 25.0

        var x = -1
        var y = -1

        offsetX = 0.0
        offsetY = 0.0

        if (direction == Orientation.VERTICAL) {
            x = ceil(gX / 30.0).toInt()
            y = ceil(ly_V / 30.0).toInt()

            if ((30.0 * y - ly_V) < 15.0){
                y = y + 1
            }

            offsetX = (x-1) * 30.0 - lx_V
            offsetY = (y-1) * 30 - ly_V + 2.5
        } else {
            x = ceil((cX - YcenterLoc[type]!!) / 30.0).toInt()
            if (30.0 * x - (cX - YcenterLoc[type]!!) < 15.0){
                x += 1
            }

            y = ceil((cY - 12.5) / 30.0).toInt()
            if (30.0 * y - (cY - 12.5) < 15.0){
                y += 1
            }

            offsetX = (x-1) * 30.0 - (cX - YcenterLoc[type]!!)
            offsetY = (y-1) * 30.0 - (cY - 12.5) + 2.5
        }

        Xloc[type] = (x-1) * 30.0 + 25.0
        Yloc[type] = (y-1) * 30.0 + 50.0 + 2.5

        // set back
        curX = 0.0
        curY = 0.0

        return Cell(x - 1, y - 1)

    }
}

