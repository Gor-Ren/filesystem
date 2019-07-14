package gor.commands

import java.util.regex.Pattern

import gor.files.{Directory, DirectoryEntry}
import gor.filesystem.State

class Mkdir(val dirName: String) extends Command {

  /** The legal format of a directory name; alphanumeric, dash or underscore */
  val legalDirPattern: Pattern = Pattern.compile("^[a-zA-Z0-9\\-_]*$")

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(dirName)) {
      state.setMessage(s"Entry $dirName already exists.")
    } else if (!isLegalDirName(dirName)) {
      state.setMessage(s"$dirName: illegal directory name.")
    } else {
      doMkdir(state, dirName)
    }
  }

  private def isLegalDirName(proposedName: String): Boolean = {
//    legalDirPattern.matcher(proposedName).matches()
    true
  }

  def updateStructure(currentDirectory: Directory,
                      path: List[String],
                      newEntry: DirectoryEntry): Directory = ???

  def doMkdir(state: State, dirName: String): State = {
    val wd: Directory = state.wd

    // 1. get all directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    // 2. create new directory entry in the wd
    val newDir = Directory.empty(wd.path, dirName)

    // 3. update the whole directory structure starting from the root
    // (dir structure is immutable => we will create new one)
    val newRoot: Directory = updateStructure(state.root, allDirsInPath, newDir)

    // 4. find new working directory instance given wd's full path in new structure
    val newWd: Directory = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)
  }
}
