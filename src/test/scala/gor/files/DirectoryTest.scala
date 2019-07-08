package gor.files

import org.scalatest.{FlatSpec, Matchers}

class DirectoryTest extends FlatSpec with Matchers {

  behavior of "empty Directory factory method"

  it should "have no contents" in {
    val emptyDir = Directory.empty("/", "test")
    emptyDir.contents shouldBe empty
  }

}
