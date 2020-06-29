package workflow

import zio._
import zio.stream._
import java.nio.file.Path
import java.nio.file.WatchEvent
import java.nio.file.FileSystems
import java.nio.file.Paths
import scala.jdk.CollectionConverters._

package object monitor {
 
  type FileWatch = Has[FileWatch.Service]

  object FileWatch {

    trait Service {
      def monitorFileEvent(path: String, watchEventKind: WatchEvent.Kind[Path]) : ZStream[Any, Throwable, Path] 
    }

    val live: ZLayer[Any, Throwable, FileWatch] = ZLayer.succeed {
      new Service {

        def monitorFileEvent(path: String, watchEventKind: WatchEvent.Kind[Path]): ZStream[Any, Throwable, Path] =
          ZStream.managed(ZManaged.fromAutoCloseable(ZIO(FileSystems.getDefault().newWatchService()))).flatMap { watcher =>
            ZStream.fromEffect(ZIO(Paths.get(path).register(watcher, watchEventKind))).drain ++
              ZStream.repeatEffectChunkOption {
                for {
                  key <- ZIO(watcher.take()).mapError(Some(_))
                  _   = console.putStrLn("READING EVENTS...")
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
                  _        = console.putStrLn("HANDLING EVENT...")
                  _        <- ZIO.fail(None).when(!continue)
                } yield Chunk.fromIterable(events)
              }
          }
      }

    }

    /*
    def monitorFileEvent(
      path: String,
      watchEventKind: WatchEvent.Kind[Path]
    ) : ZStream[FileWatch, Throwable, Path] = ZIO.accessM(_.get.monitorFileEvent(path, watchEventKind))
    */

  }

  
}
