package gor.filesystem

import gor.files.Directory

/** A class encapsulating the state of the file system. */
class State private (val root: Directory,
                     val wd: Directory,
                     val output: String,
                     val exit: Boolean) {

  /** Return a copy of this state with output set to the input message. */
  def setMessage(message: String): State = State(root, wd, message, exit)

  /** Return a new exit state with the same root, working dir and output as
    * this state.
    */
  def quit: State = State(root, wd, output, exit = true)
}

object State {

  /** Construct a new non-exit state with an empty output message.
    *
    * @param root the root directory
    * @param wd the current working directory
    * @return a new state, with given root, working dir, empty output and
    *         `exit=false`
    */
  def apply(root: Directory, wd: Directory): State = {
    new State(root, wd, "", false)
  }

  /** Construct a new non-exit state with input values.
    *
    * @param root the root directory
    * @param wd the current working directory
    * @param output the most recent user output from the file system
    * @return a new state, with given root, working dir, output and
    *         `exit=false`
    */
  def apply(root: Directory, wd: Directory, output: String): State = {
    new State(root, wd, output, false)
  }

  /** Construct a new state with input values
    *
    * @param root the root directory
    * @param wd the current working directory
    * @param output the most recent user output from the file system
    * @param exit true if this state indicates the program should terminate,
    *             else false
    * @return a new state
    */
  private def apply(root: Directory,
                    wd: Directory,
                    output: String,
                    exit: Boolean): State = {
    new State(root, wd, output, exit)
  }
}
