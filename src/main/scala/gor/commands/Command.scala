package gor.commands

import gor.filesystem.State

trait Command {

  def apply(state: State): State
}

object Command {
  val MKDIR = "mkdir"

  /** Returns the command identified by the input argument. */
  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if (input.isEmpty || tokens.isEmpty) ErrorCommand.empty
    else if (tokens(0).equals(MKDIR)) {
      if (tokens.length < 2) ErrorCommand.tooFewArgs(MKDIR)
      else if (tokens.length > 2) ErrorCommand.tooManyArgs(MKDIR)
      else new Mkdir(tokens(1))
    } else ErrorCommand.unknownCommand
  }
}
