package gor.files

import scala.annotation.tailrec

class Directory(override val parentPath: String,
                override val name: String,
                val contents: List[DirectoryEntry])
    extends DirectoryEntry(parentPath, name) {

  def getAllFoldersInPath: List[String] = {
    path.substring(Directory.ROOT_PATH.length).split(Directory.SEPARATOR).toList
  }

  /** Traverses through this directory's contents using the input entry names.
    *
    * @param path a list of entry names to be traversed
    * @return the directory obtained by traversing the input path
    * @throws NoSuchElementException if no directory can be found for any name
    *                                in the provided path
    */
  @tailrec
  final def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else
      findEntry(path.head)
        .getOrElse(throw new NoSuchElementException(s"${path.head}: not found"))
        .asDirectory
        .findDescendant(path.tail)
  }

  /** Returns a new directory, with the input entry added to its contents.
    *
    * @param newEntry a directory entry to be added
    * @return a new directory, with the input entry added to its contents
    */
  def addEntry(newEntry: DirectoryEntry): Directory = {
    if (this.hasEntry(newEntry.name)) {
      throw new IllegalArgumentException(
        s"Entry with name ${newEntry.name} already exists."
      )
    } else new Directory(this.path, this.name, contents :+ newEntry)
  }

  /** Returns true if this directory contains an entry with the input name,
    * else false.
    */
  def hasEntry(name: String): Boolean = findEntry(name).nonEmpty

  def findEntry(searchName: String): Option[DirectoryEntry] = {
    val found = contents.filter(e => e.name.equals(searchName))
    if (found.size == 1) Some(found.head)
    else if (found.isEmpty) None
    else {
      throw new IllegalStateException(
        "multiple entries with same name found: " + searchName
      )
    }
  }

  /** Removes the identified entry and adds the input entry.
    *
    * @param removeName the name of the entry to be removed
    * @param newEntry the entry to be added to the directory contents
    * @return a new directory, with the entry identified by `removeName`
    *         removed and the input entry added to its contents
    * @throws NoSuchElementException if no entry of the input name is in this
    *                                directory's contents
    */
  def replaceEntry(removeName: String, newEntry: DirectoryEntry): Directory =
    if (!this.hasEntry(removeName))
      throw new NoSuchElementException(s"$removeName: not found")
    else
      new Directory(
        parentPath,
        removeName,
        contents.filterNot(e => e.name.equals(removeName)) :+ newEntry
      )

  override def asDirectory: Directory = this
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory = {
    new Directory(parentPath, name, List.empty)
  }
}
