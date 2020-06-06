package tutorial.webapp

import org.scalajs.dom
import dom.{CanvasRenderingContext2D, document, html}
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas

object TutorialApp {
  val cellCountPerRow = 200
  val cellSizeInPixels = 3
  val canvasLength = cellCountPerRow * cellSizeInPixels
  var x = 0
  var y = 0
  var lineColor = "#000000"
  var time = 0
  var pointerSize = cellSizeInPixels

  def main(args: Array[String]): Unit =  {
    getCanvas(document.body)
  }

  def pointer(canvas: Canvas): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.fillStyle = lineColor
    ctx.fillRect(x, y, pointerSize, pointerSize)
  }

  def getCanvas(targetNode: dom.Node): Unit = {
    val simulationCanvas = document.createElement("canvas")
    simulationCanvas.id = "simulation-canvas"
    simulationCanvas.setAttribute("width", canvasLength + "px")
    simulationCanvas.setAttribute("height", canvasLength +"px")
    targetNode.appendChild(simulationCanvas)

    val canvas = document.getElementById("simulation-canvas").asInstanceOf[html.Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    ctx.fillStyle = "grey"
    ctx.fillRect(0, 0, canvasLength, canvasLength)

    pointer(canvas)
    draw(canvas)
  }

  def draw (canvas: Canvas): Unit = {
    var down = false
    document.onkeydown =
      (e: dom.KeyboardEvent) => {
        e.keyCode match {
          case KeyCode.S => {
            if (canvasLength - y >= pointerSize) y += pointerSize
            else y = (canvasLength - pointerSize)
            println(y)
            down = true
          }
          case KeyCode.W => {
            if (y >= pointerSize) y -= pointerSize
            else y = 0
            down = true
          }
          case KeyCode.D => {
            if (canvasLength - x > pointerSize) x += pointerSize
            else x = (canvasLength - pointerSize)
            down = true
          }
          case KeyCode.A => {
            if (x >= pointerSize) x -= pointerSize
            else x = 0
            down = true
          }
          case KeyCode.Space => {
            lineColor = "#%06x".format(scala.util.Random.nextInt(1<<24))
          }
          case _ => None
        }
      }

    document.onkeyup = (e: dom.KeyboardEvent) => down = false
    document.onkeypress = (e: dom.KeyboardEvent) => if (down) pointer(canvas)
  }
}
