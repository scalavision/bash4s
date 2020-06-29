package workflow

import zio._

package object terminal {

  type Terminal = Has[Terminal.Service]  

  object Terminal {
    
    trait Service {
      val getUserInput: UIO[String]
      def display(frame: String): UIO[Unit]
    }

    val ansiClearScreen: String = "\u001b[H\u001b[2J"
    
    val live: URLayer[console.Console, Terminal] = ZLayer.fromManaged {
      ZIO.environment[console.Console].map { console =>
        new Service {

          override val getUserInput: zio.UIO[String] = console.get.getStrLn.orDie

          override def display(frame: String): zio.UIO[Unit] = 
            for {
              _ <- console.get.putStr(ansiClearScreen)
              _ <- console.get.putStr(frame)
            } yield ()
        }

      }.toManaged(_ => console.putStrLn("Exiting, Goodbye .. !"))
    }

    /* Could also use fromEffect
    val live: URLayer[console.Console, Terminal] = ZLayer.fromEffect {
      ZIO.environment[console.Console].map { console =>
        new Service {

          override val getUserInput: zio.UIO[String] = console.get.getStrLn.orDie

          override def display(frame: String): zio.UIO[Unit] = 
            for {
              _ <- console.get.putStr(ansiClearScreen)
              _ <- console.get.putStr(frame)
            } yield ()
        }

      }
    }
    */
  }

  val getUserInput: URIO[Terminal, String]    = ZIO.accessM(_.get.getUserInput)
  def display(frame: String): URIO[Terminal, Unit]       = ZIO.accessM(_.get.display(frame))
  
}
