package gor.commands

import gor.files.Directory
import gor.filesystem.State
import org.scalatest.{FlatSpec, Matchers}

class QuitTest extends FlatSpec with Matchers {
  val directory: Directory = Directory.empty("/dev/", "testDir")
  val state: State = State(Directory.ROOT, directory, "message")

  assume(!state.exit, "starting test state should not be an exit state")

  "quit" should "transition to an exit state" in {
    Quit(state).exit shouldBe true
  }
}
