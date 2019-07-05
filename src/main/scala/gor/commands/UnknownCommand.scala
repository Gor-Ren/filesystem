package gor.commands
import gor.filesystem.State

class UnknownCommand extends Command {

  override def apply(state: State): State = {
    state.setMessage("Command not found.")
  }
}
