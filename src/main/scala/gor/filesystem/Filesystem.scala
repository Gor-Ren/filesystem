package gor.filesystem

import java.util.Scanner

import gor.commands.Command
import gor.files.Directory

/**
  * The entry point and main control loop of the filesystem application.
  */
object Filesystem extends App {

  val root = Directory.ROOT
  var state = State(root, root)
  val scanner = new Scanner(System.in)

  while (true) {
    state.show()
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
