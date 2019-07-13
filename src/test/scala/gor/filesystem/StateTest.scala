package gor.filesystem

import gor.files.Directory
import org.scalatest.{FlatSpec, Matchers}

class StateTest extends FlatSpec with Matchers {
  val directory: Directory = Directory.empty("/dev/", "testDir")
  val state: State = State(Directory.ROOT, directory, "message")

  behavior of "setMessage"

  it should "produce a new state" in {
    state.setMessage("test message") shouldNot be theSameInstanceAs state
  }

  it should "output the given message" in {
    val message = "test"
    state.setMessage(message).output shouldEqual message
  }

  it should "produce a state with the same root dir" in {
    val newState = state.setMessage("")
    newState.root shouldEqual state.root
  }

  it should "produce a state with the same working dir" in {
    val newState = state.setMessage("")
    newState.wd shouldEqual state.wd
  }

  "quit" should "produce an exit state" in {
    state.quit.exit shouldBe true
  }
}
