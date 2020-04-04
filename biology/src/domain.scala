package bio

import bash4s.domain._

case class Cores(nrOfCores: Int)

object domain {

  type BamSorted = BiologyFileType[Bam with Sorted]
  type BamSortedIndexed = BiologyFileType[Bam with Sorted with Indexed]
  type BamSortedIndexedMarkDup =
    BiologyFileType[Bam with Sorted with Indexed with Markdup]
  type FastqGz = BiologyFileType[Fastq with Gz]
//  type FastaBwaIndexed = BiologyFileType[Fasta with BwaIndexed]
  type MarkdupMetrics = BiologyFileType[Markdup with Metrics]


  sealed trait Bam extends Product with Serializable
  sealed trait Vcf extends Product with Serializable
  sealed trait G extends Product with Serializable
  sealed trait Bed extends Product with Serializable
  sealed trait FastaFile extends Product with Serializable
  sealed trait Fastq extends Product with Serializable
  sealed trait BwaIndexed extends Product with Serializable
  sealed trait Tar extends Product with Serializable
  sealed trait Txt extends Product with Serializable
  sealed trait Indexed extends Product with Serializable
  sealed trait Sorted extends Product with Serializable
  sealed trait Markdup extends Product with Serializable
  sealed trait Tbi extends Product with Serializable
  sealed trait Gz extends Product with Serializable
  sealed trait Metrics extends Product with Serializable

  sealed trait BiologyFileType[+T] {
    def e[T2]: String => BiologyFileType[T2]
    def bam = e[T with Bam]("bam")
    def vcf = e[T with Vcf]("vcf")
    def g = e[T with G]("g")
    def bed = e[T with Bed]("bed")
    def fasta = e[T with bio.Fasta]("fasta")
    def fastq = e[T with Fastq]("fastq")
    def bwaIndexed = e[T with BwaIndexed]("bwaIndexed")
    def tar = e[T with Tar]("tar")
    def txt = e[T with Txt]("txt")
    def indexed = e[T with Indexed]("indexed")
    def sorted = e[T with Sorted]("sorted")
    def markdup = e[T with Markdup]("markdup")
    def tbi = e[T with Tbi]("tbi")
    def gz = e[T with Gz]("gz")
    def metrics = e[T with Metrics]("metrics")
  }

   final case class BiologyFile[T](name: BaseName, extension: Vector[String])
      extends BiologyFileType[T] { self =>
    def e[T2]: String => BiologyFile[T2] =
      s =>
        copy(extension =
          self.extension :+ s
        )
  }

  final case class BiologyFilePath[T](
      folderPath: FolderPath,
      fileName: FileName
  ) extends BiologyFileType[T] { self =>

    // Scala really needs lenses
    def e[T2]: String => BiologyFilePath[T2] =
      s =>
        copy(
          fileName = 
            self.fileName.copy(extension = self.fileName.extension :+ s)
          )

  }


}