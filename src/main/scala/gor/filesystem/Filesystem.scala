package gor.filesystem

import java.io.InputStream
import java.util.Scanner

import gor.commands.Command
import gor.files.Directory

/**
  * The entry point and main control loop of the filesystem application.
  */
object Filesystem {

  val root: Directory = Directory.ROOT
  var state: State = State(root, root)

  def run(input: InputStream): Unit = {
    val scanner = new Scanner(input)

    while (true) {
      state.show()
      val input = scanner.nextLine()
      state = Command.from(input).apply(state)
    }
  }

  def main(args: Array[String]): Unit = {
    run(System.in)
  }
}
