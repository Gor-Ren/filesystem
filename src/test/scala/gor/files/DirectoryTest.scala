package gor.files

import org.scalatest.{FlatSpec, Matchers}

class DirectoryTest extends FlatSpec with Matchers {
  val DIR_NAME = "testDir"
  val SUBDIR_NAME = "subDir"
  val testDir: Directory = Directory.empty(Directory.ROOT_PATH, DIR_NAME)

  behavior of "empty Directory factory method"

  it should "create a directory with no contents" in {
    val emptyDir = Directory.empty(Directory.ROOT_PATH, DIR_NAME)
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

  val subDir: Directory = Directory.empty(testDir.path, SUBDIR_NAME)
  val parentDir: Directory = testDir.addEntry(subDir)

  it should "contain the subdir in its contents" in {
    parentDir.contents should contain theSameElementsInOrderAs Seq(subDir)
  }

  it should "return true when tested if it contains subdir name" in {
    parentDir.hasEntry(SUBDIR_NAME) shouldBe true
  }

  it should "return false when tested if it contains non-subdir name" in {
    parentDir.hasEntry("incorrectName") shouldBe false
  }

  it should "return its subdir when searched by name" in {
    parentDir.findEntry(SUBDIR_NAME) shouldBe Some(subDir)
  }

  it should "remove the subdir when replaced by name" in {
    val updatedDir =
      parentDir.replaceEntry(subDir.name,
                             Directory.empty(parentDir.path, "otherDir"))
    updatedDir.contents should contain noElementsOf Seq(subDir)
  }

  it should "contain the replacement dir when subdir is replaced" in {
    val replaceDir = Directory.empty(parentDir.path, "otherDir")
    val updatedDir = parentDir.replaceEntry(subDir.name, replaceDir)
    updatedDir.contents should contain theSameElementsInOrderAs Seq(replaceDir)
  }

  behavior of "a directory containing multiple subdirs"
  val numOfSubDirs = 5

  val subDirs: List[Directory] =
    (for (i <- 0 until numOfSubDirs)
      yield Directory.empty(parentDir.path, "subdir" + i)).toList
}
