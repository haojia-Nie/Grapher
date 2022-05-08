# A2 Grapher
Haojia Nie (h7nie 20770220)

## Setup
* macOS 11.2.3 Big Sur 
* IntelliJ IDEA 2021.3.1 (Community Edition)
* kotlin.jvm 1.5.21
* JavaFX 17.0.1
* Java SDK 16.0.2 (Amazon Corretto verssion 16.0.2)
* Target JVM Version 1.8

## Enhancement 
I added an new functionality that makes the graph be interactive. When you put the mouse inside the rectangle bar and click, that bar will fade out from the graph. There is no way to bring the bar back to the graph unless the user click on other dataset and re-enter this dataset again. 

This allows the user to compare specific datapoints. Other functionalities stay the same as before. It is just that the clicked bar will not be showing to the canvas anymore.

For example, at Increasing Dataset, if the user click right most rectangle bar in the bargraph. That bar will fade out, the others remain. After that, the user choose another dataset from the choice box. Then, when the user return back to the Increasing Dataset, all the bars will show again. 

