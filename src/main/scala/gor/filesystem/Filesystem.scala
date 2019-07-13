package gor.filesystem

import java.io.{InputStream, OutputStream}
import java.util.Scanner

import gor.commands.Command
import gor.files.Directory
import gor.filesystem.Filesystem.state

/** The entry point and main control loop of the filesystem application. */
class Filesystem(input: InputStream, output: OutputStream) {

  def run(): Unit = {
    val scanner = new Scanner(input)

    Console.withOut(output) {
      while (true) {
        state.show()
        val input = scanner.nextLine()
        state = Command.from(input).apply(state)
      }
    }
  }
}

object Filesystem {

  val root: Directory = Directory.ROOT
  var state: State = State(root, root)

  def apply(in: InputStream, out: OutputStream): Filesystem =
    new Filesystem(in, out)

  def main(args: Array[String]): Unit = {
    Filesystem(System.in, System.out).run()
  }
}
