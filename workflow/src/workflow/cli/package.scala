package workflow

import zio._
import atto.Atto._
import atto.Parser
import domain._

package object cli {

  type CLIParser = Has[CLIParser.Service]

  object CLIParser {

    trait Service {
      def parse(input: String): IO[AppError, WorkflowCommand]
    }

    val live: ULayer[CLIParser] = ZLayer.succeed {
      new Service {
        def parse(input: String): IO[AppError, WorkflowCommand] = 
          ZIO.fromOption(command.parse(input).done.option).orElseFail(ParseError)

        private lazy val command: Parser[WorkflowCommand] =
          choice(menu, ps)

        private lazy val menu: Parser[WorkflowCommand] =
          (string("menu") <~ endOfInput) >| WorkflowCommand.Menu

        private lazy val ps: Parser[WorkflowCommand] =
          (string("ps") <~ endOfInput) >| WorkflowCommand.ListProcesses
          
         
          /*
          .flatMap { value =>
            WorkflowCommand
              .changeView(value)
              .fold(err[WorkflowCommand](s"Invalid field value: $value"))(field => ok(field).map(WorkflowCommand.ListProcesses))
          }*/
      }
    }

    val dummy: ULayer[CLIParser] = ZLayer.succeed {
      new Service {
        override def parse(input: String): IO[AppError, WorkflowCommand] = IO.fail(ParseError)
      }
    }

    // accessors
    def parse(input: String): ZIO[CLIParser, AppError, WorkflowCommand] = ZIO.accessM(_.get.parse(input))

  }

}
