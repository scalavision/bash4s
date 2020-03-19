package example

//import zio.{ Has, ZLayer }
import zio._
import zio.console._

case class UserId(id: Int)
case class DBError(msg: String)
case class User(id: UserId, name: String)

package object example {


type UserRepo = Has[UserRepo.Service]

object UserRepo {
  trait Service {
    def getUser(userId: UserId): IO[DBError, Option[User]]
    def createUser(user: User): IO[DBError, Unit]
  }


  // This simple live version depends only on a DB Connection
  val inMemory: Layer[Nothing, UserRepo] = ZLayer.succeed(
    new Service {
      def getUser(userId: UserId): IO[DBError, Option[User]] = UIO(???)
      def createUser(user: User): IO[DBError, Unit] = UIO(???)
    }
  )

  //accessor methods
  def getUser(userId: UserId): ZIO[UserRepo, DBError, Option[User]] =
    ZIO.accessM(_.get.getUser(userId))

  def createUser(user: User): ZIO[UserRepo, DBError, Unit] =
    ZIO.accessM(_.get.createUser(user))
  } 


type Logging = Has[Logging.Service]

object Logging {

  trait Service {
    def info(s: String): UIO[Unit]
    def error(s: String): UIO[Unit]
  }

  val consoleLogger: ZLayer[Console, Nothing, Logging] = ZLayer.fromFunction( console =>
    new Service {
      def info(s: String): UIO[Unit]  = console.get.putStrLn(s"info - $s")
      def error(s: String): UIO[Unit] = console.get.putStrLn(s"error - $s")
    }
  )

  //accessor methods
  def info(s: String): ZIO[Logging, Nothing, Unit] =
    ZIO.accessM(_.get.info(s))

  def error(s: String): ZIO[Logging, Nothing, Unit] =
    ZIO.accessM(_.get.error(s))
  }



}

object ExampleProgram {
  import example._

  // compose horizontally
  val horizontal: ZLayer[Console, Nothing, Logging with UserRepo] = Logging.consoleLogger ++ UserRepo.inMemory

  // fulfill missing deps, composing vertically
  val fullLayer: Layer[Nothing, Logging with UserRepo] = Console.live >>> horizontal

  // provide the layer to the program

  def apply() = {
    val user2: User = User(UserId(123), "Tommy")
    val makeUser: ZIO[Logging with UserRepo, DBError, Unit] = for {
      _ <- Logging.info(s"inserting user")  // ZIO[Logging, Nothing, Unit]
      _ <- UserRepo.createUser(user2)       // ZIO[UserRepo, DBError, Unit]
      _ <- Logging.info(s"user inserted")   // ZIO[Logging, Nothing, Unit]
    } yield ()
  
    makeUser.provideLayer(fullLayer)
  }
  
}