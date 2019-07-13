package gor.mocks

import gor.commands.Command
import gor.filesystem.State
import gor.view.FilesystemView

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/** A mock view for testing Filesystem functionality.
  *
  * The mock view is initialised with a sequence of input commands, which it
  * returns in order when `readInput()` is called. A `quit` command is
  * automatically appended to the sequence to ensure program termination.
  *
  * The output of all state updates received are stored and may be retrieved
  * using `getOutputs`.
  */
class MockView private (userCommands: Seq[String]) extends FilesystemView {
  private val userInputs: mutable.Queue[String] = userCommands.to[mutable.Queue]
  private val allOutput: ListBuffer[String] = new ListBuffer[String]

  /** Returns the next user input.
    *
    * Inputs are returned in the same order as provided when initialising this
    * view, including the `quit` command automatically appended to the end of
    * the inputs.
    *
    * @return the next input this view was mocked to return
    * @throws NoSuchElementException if the input queue is empty. This should
    *                                be impossible if the program terminates in
    *                                response to the `quit` command.
    */
  override def readInput(): String = {
    userInputs.dequeue()
  }

  /** Notifies this view that the program state has changed to the input value.
    *
    * @see getOutputs to retrieve all outputs received by this mock
    */
  override def update(state: State): Unit = allOutput += state.output

  /** Returns all outputs received by this view in the order received. */
  def getOutputs: List[String] = allOutput.result()

  /** Returns true if this mock view has commands in its queue, else false. */
  def hasCommands: Boolean = userInputs.nonEmpty
}

object MockView {

  /** Returns a mock view configured to provide the input commands.
    *
    * When `readInput()` is called, the mock will return each command in order.
    * A `quit` command is automatically appended to the end of the commands
    * in the returned mock, to terminate the program.
    *
    * @param commands the commands, in order, which this mock will return when
    *                 queried for input
    */
  def apply(commands: Seq[String]): MockView =
    new MockView(commands ++ Seq(Command.QUIT))
}
