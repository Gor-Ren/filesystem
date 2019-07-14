package gor.mocks

import gor.commands.Command
import gor.files.Directory
import gor.filesystem.State
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

/** Unit tests ensuring correct behaviour of the mock view. */
class MockViewTest extends FlatSpec with Matchers {
  val testState = State(Directory.empty("/", "/"), Directory.empty("/", "/"))

  behavior of "any mock view"

  it should "record outputs when updated" in {
    val view = MockView(Seq.empty)
    val message = "test-message"
    view.update(testState.setMessage(message))
    view.getOutputs should contain theSameElementsInOrderAs Seq(message)
  }

  it should "record outputs in the order of updates" in {
    val view = MockView(Seq.empty)
    val message1 = "test-message-one"
    val message2 = "test-message-two"
    view.update(testState.setMessage(message1))
    view.update(testState.setMessage(message2))
    view.getOutputs should contain theSameElementsInOrderAs Seq(message1,
                                                                message2)
  }

  it should "show empty outputs when never updated" in {
    val view = MockView(Seq.empty)
    view.getOutputs shouldBe empty
  }

  behavior of "mock view (when initialised with no commands)"

  it should "show it contains a command" in {
    val view = MockView(Seq.empty)
    view.hasCommands shouldBe true
  }

  it should "show it does not contain any commands after reading one" in {
    val view = MockView(Seq.empty)
    view.readInput()
    view.hasCommands shouldBe false
  }

  it should "complain when reading input when empty" in {
    intercept[NoSuchElementException] {
      val view = MockView(Seq.empty)
      view.readInput() // reads 'quit' command
      view.readInput() // should throw
    }
  }

  it should "return one 'quit' command" in {
    val view = MockView(Seq.empty)
    var inputs = new ListBuffer[String]
    while (view.hasCommands) inputs += view.readInput()
    inputs.result() should contain theSameElementsInOrderAs Seq(Command.QUIT)
  }

  behavior of "mock view (when initialised with a single command)"

  it should "show it contains a command" in {
    val view = MockView(Seq("test"))
    view.hasCommands shouldBe true
  }

  it should "show it contains a command after reading one " in {
    val view = MockView(Seq("test"))
    view.hasCommands shouldBe true
  }

  it should "show it does not contain any commands after reading two" in {
    val view = MockView(Seq("test"))
    view.readInput()
    view.readInput()
    view.hasCommands shouldBe false
  }

  it should "return the input command, followed by `quit`" in {
    val command = "test"
    val view = MockView(Seq(command))
    view.readInput() shouldBe command
    view.readInput() shouldBe Command.QUIT
  }

  behavior of "mock view (when initialised with multiple commands)"

  it should "return the input commands in order, followed by `quit`" in {
    val commands = Seq("1", "2", "3")
    val view = MockView(commands)

    val expectedOutput = commands ++ Seq(Command.QUIT)
    expectedOutput.foreach(
      cmd => view.readInput() shouldBe cmd
    )
  }
}
