package gor.commands

import gor.files.Directory
import gor.filesystem.State
import org.scalatest.{FlatSpec, Matchers}

class CommandTest extends FlatSpec with Matchers {

  val directory: Directory = Directory.empty("/dev/", "testDir")
  val state: State = State(Directory.ROOT, directory, "message")
  val MKDIR = "mkdir"
  val QUIT = "quit"

  behavior of "Command 'from' factory method"

  it should "return empty command when input is empty" in {
    Command.from("") shouldBe ErrorCommands.empty
  }

  it should "return empty command when input is spaces" in {
    Command.from("   ") shouldBe ErrorCommands.empty
  }

  ignore should "return empty command when input is whitespace" in {
    Command.from("\t \r") shouldBe ErrorCommands.empty
  }

  it should "return mkdir command with name of first arg" in {
    val name = "myName"
    val command = Command.from(MKDIR + " " + name)
    command shouldBe a[Mkdir]
    command.asInstanceOf[Mkdir].dirName shouldEqual name
  }

  it should "return unknown command when not recognised" in {
    Command.from("NOT_VALID_COMMAND") shouldBe ErrorCommands.unknownCommand
  }

  it should "return quit command" in {
    Command.from(QUIT) shouldBe Quit
  }

  ignore should "return too few args when no mkdir arg given" in {
    Command.from(MKDIR) shouldBe ErrorCommands.tooFewArgs(MKDIR)
  }

  ignore should "return too many args when two mkdir args given" in {
    Command.from(MKDIR + " arg1 arg2") shouldBe ErrorCommands.tooManyArgs(MKDIR)
  }
}
