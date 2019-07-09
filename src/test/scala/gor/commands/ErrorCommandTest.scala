package gor.commands

import gor.files.Directory
import org.scalatest.{FlatSpec, Matchers}
import gor.filesystem.State

class ErrorCommandTest extends FlatSpec with Matchers {

  val directory: Directory = Directory.empty("/dev/", "testDir")
  val state: State = State(Directory.ROOT, directory, "message")
  val UNKNOWN_COMMAND_MESSAGE: String = "Command not found."
  val TOO_FEW_ARGS_MESSAGE: String = ": too few arguments."
  val TOO_MANY_ARGS_MESSAGE: String = ": too many arguments."
  val TEST_COMMAND_NAME: String = "testCommand"

  behavior of "Empty Command"

  it should "return the same state" in {
    ErrorCommand.empty
      .apply(state) should be theSameInstanceAs state
  }

  behavior of "Unknown Command"

  it should "produce a state outputting unknown command message" in {
    ErrorCommand.unknownCommand
      .apply(state)
      .output shouldEqual UNKNOWN_COMMAND_MESSAGE
  }

  it should "not change the root dir" in {
    ErrorCommand.unknownCommand
      .apply(state)
      .root shouldEqual state.root
  }

  it should "not change the working dir" in {
    ErrorCommand.unknownCommand
      .apply(state)
      .wd shouldEqual state.wd
  }

  behavior of "Too Few Arguments Command"

  it should "produce a state outputting too few args message" in {
    ErrorCommand
      .tooFewArgs(TEST_COMMAND_NAME)
      .apply(state)
      .output should endWith(TOO_FEW_ARGS_MESSAGE)
  }

  it should "produce a state identifying the command with too few args" in {
    ErrorCommand
      .tooFewArgs(TEST_COMMAND_NAME)
      .apply(state)
      .output should startWith(TEST_COMMAND_NAME)
  }

  it should "not change the root dir" in {
    ErrorCommand
      .tooFewArgs(TEST_COMMAND_NAME)
      .apply(state)
      .root shouldEqual state.root
  }

  it should "not change the working dir" in {
    ErrorCommand
      .tooFewArgs(TEST_COMMAND_NAME)
      .apply(state)
      .wd shouldEqual state.wd
  }

  behavior of "Too Many Arguments Command"

  it should "produce a state outputting too many args message" in {
    ErrorCommand
      .tooManyArgs(TEST_COMMAND_NAME)
      .apply(state)
      .output should endWith(TOO_MANY_ARGS_MESSAGE)
  }

  it should "produce a state identifying the command with too many args" in {
    ErrorCommand
      .tooManyArgs(TEST_COMMAND_NAME)
      .apply(state)
      .output should startWith(TEST_COMMAND_NAME)
  }

  it should "not change the root dir" in {
    ErrorCommand
      .tooManyArgs(TEST_COMMAND_NAME)
      .apply(state)
      .root shouldEqual state.root
  }

  it should "not change the working dir" in {
    ErrorCommand
      .tooManyArgs(TEST_COMMAND_NAME)
      .apply(state)
      .wd shouldEqual state.wd
  }
}
