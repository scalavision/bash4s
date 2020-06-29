package workflow

import zio._
import zio.stream._
import java.nio.file.Path
import java.nio.file.WatchEvent
import java.nio.file.FileSystems
import java.nio.file.Paths
import scala.jdk.CollectionConverters._
import java.nio.file.StandardWatchEventKinds


object WorkflowApp extends App {

   def monitorFileEvent(path: String, watchEventKind: WatchEvent.Kind[Path]): ZStream[console.Console, Throwable, Path] =
    ZStream.managed(ZManaged.fromAutoCloseable(ZIO(FileSystems.getDefault().newWatchService()))).flatMap { watcher =>
      ZStream.fromEffect(ZIO(Paths.get(path).register(watcher, watchEventKind))).drain ++
        ZStream.repeatEffectChunkOption {
          for {
            key <- ZIO(watcher.take()).mapError(Some(_))
            _   <- console.putStrLn("READING EVENTS...")
            events <- ZIO(
                      key
                        .pollEvents()
                        .asScala
                        .filter(_.kind() == watchEventKind)
                        .map { watchEvent =>
                          val pathEv   = watchEvent.asInstanceOf[WatchEvent[Path]]
                          val filename = pathEv.context()

                          filename
                        }
                    ).mapError(Some(_))
            continue <- ZIO(key.reset()).mapError(Some(_))
            _        <- console.putStrLn("HANDLING EVENT...")
            _        <- ZIO.fail(None).when(!continue)
          } yield Chunk.fromIterable(events)
        }
    }
 
  val program =  
    for {
      _       <- Stream.fromEffect(console.putStrLn("Starting file monitor")).runDrain
      _   <- monitorFileEvent("/tmp/file-events", StandardWatchEventKinds.ENTRY_CREATE).tap( p =>
              console.putStrLn("file: " + p.toString())
             ).runDrain
    } yield ()
  override def run(args: List[String]): URIO[ZEnv,ExitCode] =
    program.run.map(_ => ExitCode.success)

}