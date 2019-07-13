package gor.commands
import gor.filesystem.State

/** A command which transitions to an exit state. */
object Quit extends Command {
  override def apply(state: State): State = state.quit
}
