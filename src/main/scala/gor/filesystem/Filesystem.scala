package gor.filesystem

import gor.commands.Command
import gor.files.Directory
import gor.filesystem.Filesystem.root
import gor.view.{ConsoleView, FilesystemView}

/** The entry point and main control loop of the filesystem application. */
class Filesystem(view: FilesystemView) {

  var state: State = State(root, root, "")

  def run(): Unit = {
    while (!state.exit) {
      view.update(state)
      val userCommand = view.readInput()
      state = Command.from(userCommand).apply(state)
    }
  }
}

object Filesystem {

  val root: Directory = Directory.ROOT

  def apply(view: FilesystemView) = new Filesystem(view)

  def main(args: Array[String]): Unit = {
    Filesystem(new ConsoleView).run()
  }
}
