package bio

import bash4s.domain._

final case class Cores(nrOfCores: Int)

object biomodel {

  type BamSorted = BiologyFileType[BamFile with Sorted]
  type BamSortedIndexed = BiologyFileType[BamFile with Sorted with Indexed]
  type FastqGz = BiologyFileType[Fastq with Gz]
//  type FastaBwaIndexed = BiologyFileType[Fasta with BwaIndexed]
  type MarkdupMetrics = BiologyFileType[Markdup with Metrics]


  sealed trait BiologyFileTypeMark extends Product with Serializable
  sealed trait BamFile extends BiologyFileTypeMark
  sealed trait VcfFile extends BiologyFileTypeMark
  sealed trait G extends BiologyFileTypeMark
  sealed trait Bed extends BiologyFileTypeMark
  sealed trait FastaFile extends BiologyFileTypeMark
  sealed trait Fastq extends BiologyFileTypeMark
  sealed trait BwaIndexed extends BiologyFileTypeMark
  sealed trait DictFile extends BiologyFileTypeMark
  sealed trait Tar extends BiologyFileTypeMark
  sealed trait Txt extends BiologyFileTypeMark
  sealed trait Indexed extends BiologyFileTypeMark
  sealed trait Sorted extends BiologyFileTypeMark
  sealed trait Markdup extends BiologyFileTypeMark
  sealed trait Tbi extends BiologyFileTypeMark
  sealed trait Gz extends BiologyFileTypeMark
  sealed trait Metrics extends BiologyFileTypeMark

  sealed trait BiologyFileType[+T] {
    def e[T2]: String => BiologyFileType[T2]
    def bam = e[T with BamFile]("bam")
    def vcf = e[T with VcfFile]("vcf")
    def g = e[T with G]("g")
    def bed = e[T with Bed]("bed")
    def fasta = e[T with bio.dsl.Fasta]("fasta")
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

  final case class BiologyFilePath[T](
    fileType: FileHandle
  ) extends BiologyFileType[T] { self =>

    def e[T2]: String => BiologyFilePath[T2] =
      s =>
        self.fileType match {
          case f: FileName => copy( fileType = f.copy(extension = f.extension :+ s))
          case f: RelPath => copy( fileType = f.copy(fileName = f.fileName.copy(extension = f.fileName.extension :+ s)))
          case f: FilePath => copy(fileType = f.copy(fileName = f.fileName.copy(extension = f.fileName.extension :+ s)))
          case _ => copy(fileType = fileType)
        }

  }


}