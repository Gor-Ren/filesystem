package gor.files

class Directory(
    override val parentPath: String,
    override val name: String,
    val contents: List[DirectoryEntry]
) extends DirectoryEntry(parentPath, name) {

  /** Tests if this directory contains an entry with the input name. */
  def hasEntry(name: String): Boolean =
    contents.exists(e => e.name.equals(name))
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")
  def empty(parentPath: String, name: String): Directory = {
    new Directory(parentPath, name, List.empty)
  }
}
