package gor.commands

import gor.files.Directory
import org.scalatest.{FlatSpec, Matchers}
import gor.filesystem.State

class UnknownCommandTest extends FlatSpec with Matchers {

  val UNKNWON_COMMAND_MESSAGE: String = "Command not found."
  val directory: Directory = Directory.empty("/dev/", "testDir")
  val state: State = State(Directory.ROOT, directory, "message")

  /** Class under test */
  val command: Command = new UnknownCommand

  behavior of "Unknown Command"

  it should "produce a state outputting unknown command message" in {
    command.apply(state).output should be (UNKNWON_COMMAND_MESSAGE)
  }

  it should "not change the root dir" in {
    command.apply(state).root should be (state.root)
  }

  it should "not change the working dir" in {
    command.apply(state).wd should be (state.wd)
  }
}
