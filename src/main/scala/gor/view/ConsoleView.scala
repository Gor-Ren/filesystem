package gor.view
import java.util.Scanner

import gor.filesystem.State

/** A view which outputs to, and receives input from, the System console. */
class ConsoleView extends FilesystemView {
  val scanner = new Scanner(System.in)

  override def readInput(): String = scanner.nextLine()

  /** @inheritdoc
    *
    * <p>The output associated with the state is printed, and a new input token
    * (`$`) is printed to prompt the next user input.
    */
  override def update(state: State): Unit = {
    println(state)
    print(ConsoleView.SHELL_TOKEN)
  }
}

object ConsoleView {
  val SHELL_TOKEN = "$ "
}
