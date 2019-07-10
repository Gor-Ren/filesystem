package gor.filesystem

import gor.files.{Directory, DirectoryEntry}

/**
  * Contains the state of the file system.
  *
  * @param root the root directory
  * @param wd the current working directory
  * @param output the most recent user output from the file system
  */
class State(val root: Directory, val wd: Directory, val output: String) {
  def show(): Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State = State(root, wd, message)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State = {
    new State(root, wd, output)
  }
}
