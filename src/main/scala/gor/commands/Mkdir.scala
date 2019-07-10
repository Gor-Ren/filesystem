package gor.commands

import java.util.regex.Pattern

import gor.files.Directory
import gor.filesystem.State

class Mkdir(val dirName: String) extends Command {
  /** The legal format of a directory name; alphanumeric, dash or underscore */
  val legalDirPattern: Pattern = Pattern.compile("^[a-zA-Z0-9\-_]*$")


  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(dirName)) {
      state.setMessage(s"Entry $dirName already exists.")
    } else if (dirName.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$dirName must not contain separators.")
    } else if (!isLegalDirName(dirName)) {
      state.setMessage(s"$dirName: illegal directory name.")
    } else {
      doMkdir(state, dirName)
    }
  }

  private def isLegalDirName(proposedName: String): Boolean = {
    legalDirPattern.matcher(proposedName).matches()
  }

  def doMkdir(state: State, dirName: String): State = {
    ???
  }
}
