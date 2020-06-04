package tutorial.webapp

import org.scalajs.dom
import dom.{CanvasRenderingContext2D, document, html}
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas

object TutorialApp {
  val cellCountPerRow = 200
  val cellSizeInPixels = 2
  var x = 0
  var y = 0
  var lineColor = "#000000"
  var time = 0
  var pointerSize = cellSizeInPixels

  def main(args: Array[String]): Unit =  {
    getCanvas(document.body)
//    dom.window.setInterval(increaseSize _, 1000)
  }

  def pointer(canvas: Canvas): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.fillStyle = lineColor
    ctx.fillRect(x, y, pointerSize, pointerSize)
  }

//  def increaseSize (): Unit = {
//    time += 1
//
//    if (time % 10 == 0) {
//      pointerSize += 1
//    }
//  }

  def getCanvas(targetNode: dom.Node): Unit = {
    val simulationCanvas = document.createElement("canvas")
    simulationCanvas.id = "simulation-canvas"
    simulationCanvas.setAttribute("width", cellCountPerRow * cellSizeInPixels + "px")
    simulationCanvas.setAttribute("height", cellCountPerRow * cellSizeInPixels +"px")
    targetNode.appendChild(simulationCanvas)

    val canvas = document.getElementById("simulation-canvas").asInstanceOf[html.Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.fillStyle = "grey"
    ctx.fillRect(0, 0, cellCountPerRow * cellSizeInPixels, cellCountPerRow * cellSizeInPixels)

    pointer(canvas)
    draw(canvas)
  }

  def draw (canvas: Canvas): Unit = {
    var down = false
    document.onkeydown =
      (e: dom.KeyboardEvent) => {
        e.keyCode match {
          case KeyCode.S => {
            if (y < cellCountPerRow * cellSizeInPixels - pointerSize) {
              y += pointerSize
            }
            down = true
          }
          case KeyCode.W => {
            if (y > 0) y -= pointerSize
            down = true
          }
          case KeyCode.D => {
            if (x < cellCountPerRow * cellSizeInPixels - pointerSize) x += pointerSize
            down = true
          }
          case KeyCode.A => {
            if (x > 0) x -= pointerSize
            down = true
          }
          case KeyCode.Space => {
            lineColor = "#%06x".format(scala.util.Random.nextInt(1<<24))
          }
          case KeyCode.E => {
            pointerSize += 1
          }
          case KeyCode.Q => {
            if (pointerSize > cellSizeInPixels) pointerSize -= 1
          }
          case _ => None
        }
      }

    document.onkeyup = (e: dom.KeyboardEvent) => down = false
    document.onkeypress = (e: dom.KeyboardEvent) => if (down) pointer(canvas)
  }
}
