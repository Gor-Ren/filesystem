package gor.filesystem

import java.io._
import java.nio.charset.StandardCharsets
import java.util.Scanner

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class FilesystemIntTest extends FlatSpec with Matchers with BeforeAndAfter {

  var userInput: Writer = _
  var userOutput: Scanner = _
  var filesystemThread: Thread = _

  before {
    val filesystemInput = new PipedInputStream()
    val filesystemOutput = new PipedOutputStream()

    userInput = new OutputStreamWriter(new PipedOutputStream(filesystemInput))
    userOutput = new Scanner(
      new PipedInputStream(filesystemOutput),
      StandardCharsets.UTF_8.name()
    ).useDelimiter("\\A") // \A is the start of any string

    filesystemThread =
      new Thread(() => Filesystem(filesystemInput, filesystemOutput).run())
    filesystemThread.start()
  }

  after {
    userInput.close()
    userOutput.close()
    filesystemThread.stop()
  }

  behavior of "the filesystem program"

  ignore should "return an empty output when user makes empty input" in {
    userInput.write("\n")
    val output: String = userOutput.next()
    output shouldBe "\n$ "
  }
}
