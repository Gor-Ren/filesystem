package gor.files

import org.scalatest.{FlatSpec, Matchers}

class DirectoryTest extends FlatSpec with Matchers {
  val PARENT_PATH = "/parentDir"
  val DIR_NAME = "testDir"
  val SUBDIR_NAME = "subDir"

  behavior of "empty Directory factory method"

  it should "create a directory with no contents" in {
    val emptyDir = Directory.empty(PARENT_PATH, DIR_NAME)
    emptyDir.contents shouldBe empty
  }

  behavior of "an empty directory"

  it should "return false when tested if it contains an entry" in {
    val emptyDir = Directory.empty("/", "test")
    emptyDir.hasEntry("entry") shouldBe false
  }

  behavior of "a directory containing one subdirectory"

  val dirWithOneSubdir: Directory = new Directory(
    PARENT_PATH,
    DIR_NAME,
    List(Directory.empty(PARENT_PATH, SUBDIR_NAME))
  )

  it should "return true when tested if it contains subdir name" in {
    dirWithOneSubdir.hasEntry(SUBDIR_NAME) shouldBe true
  }

  it should "return false when tested if it contains non-subdir name" in {
    dirWithOneSubdir.hasEntry("incorrectName") shouldBe false
  }
}
