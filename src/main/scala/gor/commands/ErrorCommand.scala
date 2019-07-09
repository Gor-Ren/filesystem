package gor.commands

object ErrorCommand {
  def empty: Command = state => state

  def unknownCommand: Command =
    state => state.setMessage("Command not found.")

  def tooFewArgs(name: String): Command =
    state => state.setMessage(name + ": too few arguments.")

  def tooManyArgs(name: String): Command =
    state => state.setMessage(name + ": too many arguments.")
}
