package gor.commands
import gor.files.Directory
import gor.filesystem.State

class Mkdir(val dirName: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(dirName)) {
      state.setMessage("Entry " + dirName + "already exists.")
    } else if (dirName.contains(Directory.SEPARATOR)) {
      state.setMessage(dirName + " must not contain separators.")
    } else if (!isLegalDirName(dirName)) {
      state.setMessage(dirName + ": illegal directory name!")
    } else {
      doMkdir(state, dirName)
    }
  }

  def isLegalDirName(str: String): Boolean = {
    ???
  }

  def doMkdir(state: State, dirName: String): State = {
    ???
  }
}
