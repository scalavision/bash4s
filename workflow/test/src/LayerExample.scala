package workflow

//import zio._

import zio.{ Has, ZLayer }
import zio.IO

object ModuelExample {

  case class UserId(id: Int)
  case class DBError(msg: String)
  case class User(id: UserId, name: String)

  type Logger = Has[Logger.Service] 
  type UserRepo = Has[UserRepo.Service]
 
  object Logger {
    trait Service {
      def log(txt: String) : IO[java.io.IOException, Unit]
    }
  }
  object UserRepo {
    trait Service {
      def getUser(userId: UserId): IO[DBError, Option[User]]
      def createUser(user: User): IO[DBError, Unit]
    }

    val testRepo: ZLayer[Any, Nothing, UserRepo] = ZLayer.succeed(???)
  }

  // Has[A] represents a dependency on a service of type A
  val repo: Has[UserRepo.Service] = Has(new UserRepo.Service {
      def getUser(userId: UserId): IO[DBError, Option[User]] = IO.effectTotal(Some(User(UserId(1000), "Tor")))
      def createUser(user: User): IO[DBError, Unit] = IO.effectTotal(println("created user"))
  } )
  val logger: Has[Logger.Service] = Has(new Logger.Service{
      def log(txt: String) : IO[java.io.IOException, Unit] = 
        IO.effectTotal{ println(txt)}
  })

  // Can be combined together
  // The resulting data structure is backed by a hetergeneous map from
  // service type to service implementation
  val mix: Has[UserRepo.Service] with Has[Logger.Service] = repo ++ logger

  // get back the logger service from the mixed value:
  val log = mix.get[Logger.Service].log("Hello modules!")


}

/*
object LayerExample {

  case class UserId(id: Int)

  trait DBConnection {
    def getUser(userId: UserId): ZIO[DBConnection, Nothing, Option[User]]
    def createUser(user: User): ZIO[DBConnection, Nothing, Unit]
  }  

  case class User(id: UserId, name: String)

  def getUser(userId: UserId): ZIO[DBConnection, Nothing, Option[User]] = UIO(???)
  def createUser(user: User): ZIO[DBConnection, Nothing, Unit] = UIO(???)

  val user: User = User(UserId(1234), "Chet")

  val created: ZIO[DBConnection, Nothing, Boolean] = for {
    maybeUser <- getUser(user.id)
    res       <- maybeUser.fold(createUser(user).as(true))(_ => ZIO.succeed(false))
  } yield res

  val dbConnection: DBConnection = ???

  val runnable: ZIO[Any, Nothing, Boolean] = created.provide(dbConnection)
  
}
*/