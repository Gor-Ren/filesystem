package gor.view

import gor.filesystem.State

/** Provides input-output for the Filesystem program. */
abstract class FilesystemView {

  /** Returns the next user input, blocking until one is received. */
  def readInput(): String

  /** Notifies this view that the program state has changed to the input value. */
  def update(state: State): Unit
}
