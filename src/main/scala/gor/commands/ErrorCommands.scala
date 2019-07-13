package gor.commands

object ErrorCommands {
  def empty: Command = state => state.setMessage("")

  def unknownCommand: Command =
    state => state.setMessage("Command not found.")

  def tooFewArgs(name: String): Command =
    state => state.setMessage(name + ": too few arguments.")

  def tooManyArgs(name: String): Command =
    state => state.setMessage(name + ": too many arguments.")
}
