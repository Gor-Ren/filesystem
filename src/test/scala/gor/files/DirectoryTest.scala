package gor.files

import org.scalatest.{FlatSpec, Matchers}

class DirectoryTest extends FlatSpec with Matchers {
  val PARENT_PATH = "/parentDir"
  val DIR_NAME = "testDir"
  val SUBDIR_NAME = "subDir"
  val testDir: Directory = Directory.empty(PARENT_PATH, DIR_NAME)

  behavior of "empty Directory factory method"

  it should "create a directory with no contents" in {
    val emptyDir = Directory.empty(PARENT_PATH, DIR_NAME)
    emptyDir.contents shouldBe empty
  }

  behavior of "an empty directory"
  val emptyDir: Directory = Directory.empty("/", "test")

  it should "return false when tested if it contains an entry" in {
    emptyDir.hasEntry("entry") shouldBe false
  }

  it should "complain when asked to replace a directory" in {
    intercept[NoSuchElementException] {
      emptyDir.replaceEntry("name", testDir)
    }
  }

  it should "return empty when searching for a subdir by name" in {
    emptyDir.findEntry("name") shouldBe Option.empty
  }

  it should "complain when searching for a subdir by path" in {
    intercept[NoSuchElementException] {
      emptyDir.findDescendant(List("name"))
    }
  }

  behavior of "a directory containing one subdirectory"

  val dirWithOneSubdir: Directory =
    testDir.addEntry(Directory.empty(PARENT_PATH, SUBDIR_NAME))

  it should "return true when tested if it contains subdir name" in {
    dirWithOneSubdir.hasEntry(SUBDIR_NAME) shouldBe true
  }

  it should "return false when tested if it contains non-subdir name" in {
    dirWithOneSubdir.hasEntry("incorrectName") shouldBe false
  }
}
