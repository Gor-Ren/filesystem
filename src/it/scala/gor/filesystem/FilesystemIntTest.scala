package gor.filesystem

import java.io.{ByteArrayInputStream, PipedOutputStream}
import java.util.concurrent.{Executor, Executors}

import org.scalatest.{BeforeAndAfter, FeatureSpec, GivenWhenThen}

import scala.concurrent.{ExecutionContext, Future}

class FilesystemIntTest
    extends FeatureSpec
    with GivenWhenThen
    with BeforeAndAfter {

  feature("The user is given a suitable response on invalid command") {
    scenario("User enters an empty command") {
      Given("the user is on the command line")
//      val userInput = new PipedOutputStream()
//      val filesystemThread = ExecutionContext.fromExecutorService(
//        Executors.newSingleThreadExecutor()
//      )
//      Future(Filesystem.run(userInput))(filesystemThread)

      When("they hit ENTER without having entered a command")
      Then("a blank output is given and the state is unchanged")
    }
  }
}
