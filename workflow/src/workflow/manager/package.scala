package workflow

import zio._
import domain._
import workflow.cli.CLIParser

package object manager {

  type WorkflowManager = Has[WorkflowManager.Service]

  object WorkflowManager {

    trait Service {
      def manage(input: String, state: State.ManageWorkflow): UIO[State]
      def render(state: State.ManageWorkflow): UIO[String]
    }

    val live: ZLayer[CLIParser, Nothing, WorkflowManager] = ZLayer.fromFunction { env =>
      val cliParserService = env.get[CLIParser.Service]
      println(cliParserService)
      new Service {

        override def manage(input: String, state: State.ManageWorkflow): zio.UIO[State] = ???

        override def render(state: State.ManageWorkflow): zio.UIO[String] = ???

        

      }
    }

  }


}
