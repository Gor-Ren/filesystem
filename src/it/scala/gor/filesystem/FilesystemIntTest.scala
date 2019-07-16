package gor.filesystem

import gor.commands.Command
import gor.mocks.MockView
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class FilesystemIntTest extends FlatSpec with Matchers with BeforeAndAfter {

  def mockViewWithInputs(userInputs: Seq[String]): (MockView, Filesystem) = {
    val mockView = MockView(userInputs)
    (mockView, new Filesystem(mockView))
  }

  def mockViewWithInput(userInput: String): (MockView, Filesystem) = {
    mockViewWithInputs(Seq(userInput))
  }

  behavior of "the filesystem program"

  it should "output an empty string on start" in {
    val (view, filesystem) = mockViewWithInputs(Seq.empty)
    filesystem.run()
    view.getOutputs should contain theSameElementsInOrderAs Seq("")
  }

  it should "complain about an unrecognised command" in {
    val (view, filesystem) = mockViewWithInput("bad_command")
    filesystem.run()
    view.getOutputs should contain theSameElementsInOrderAs Seq(
      "",
      "Command not found."
    )
  }

  it should "complain about too few args" in {
    val (view, filesystem) = mockViewWithInput(Command.MKDIR)
    filesystem.run()
    view.getOutputs should contain theSameElementsInOrderAs Seq(
      "",
      "mkdir: too few arguments."
    )
  }

  it should "complain about too many args" in {
    val (view, filesystem) = mockViewWithInput(s"${Command.MKDIR} arg1 arg2")
    filesystem.run()
    view.getOutputs should contain theSameElementsInOrderAs Seq(
      "",
      "mkdir: too many arguments."
    )
  }

  it should "create and navigate to a new directory" in {
    val dirName = "myTestDir"
    val (view, filesystem) = mockViewWithInput(s"${Command.MKDIR} $dirName")
    filesystem.run()
    view.getOutputs should contain theSameElementsInOrderAs Seq(
      "",
      "/" + dirName + "/"
    )
  }
}
