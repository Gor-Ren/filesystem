package gor.mocks

import gor.commands.Command
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

/** Unit tests ensuring correct behaviour of the mock view. */
class MockViewTest extends FlatSpec with Matchers {

  behavior of "empty initialised mock view"

  it should "show it contains a command" in {
    val view = MockView(Seq.empty)
    view.hasCommands shouldBe true
  }

  it should "show it does not contain any commands after reading one" in {
    val view = MockView(Seq.empty)
    view.readInput()
    view.hasCommands shouldBe false
  }

  it should "return one 'quit' command" in {
    val view = MockView(Seq.empty)
    var inputs = new ListBuffer[String]
    while (view.hasCommands) inputs += view.readInput()
    inputs.result() should contain theSameElementsInOrderAs Seq(Command.QUIT)
  }

  behavior of "single command initialised mock view"

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
}
