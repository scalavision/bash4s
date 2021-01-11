package bio.tools

import bio._
import bash4s._
import bash4s.domain._
import bash4s.ScriptGenerator
import bash4s.scripts._

case class BedGraphToBigWig(
  mosdepthRegionFile: RegionsBedGz,
  humanGenome: Genome,
  sortTmpFolder: FolderPath = dirPath"/tmp/extractBedGraph"
) extends Script {
  def param = ScriptGenerator.gen[BedGraphToBigWig](this.asInstanceOf[BedGraphToBigWig])

  val BEDGRAPH_REGION_GZ = Arg(param.$1(mosdepthRegionFile))
  val BEDGRAPH_SORT_TMP_FOLDER = Arg(param.$2(sortTmpFolder))
  val BEDGRAPH_GENOME_FILE = Arg(param.$3(humanGenome))

  val BEDGRAPH_FILE = Var
  val BEDGRAPH_SAMPLE_NAME = Var
  val LC_COLLATE = Var
  
  override def args = 
    BEDGRAPH_REGION_GZ o
    BEDGRAPH_GENOME_FILE o
    BEDGRAPH_SORT_TMP_FOLDER

  def op = 
    LC_COLLATE `=` txt"C"                                   o
    LC_COLLATE.export                                       o
    BEDGRAPH_FILE `=$`(basename"-- ${BEDGRAPH_REGION_GZ}")  o
    BEDGRAPH_SAMPLE_NAME `=` $"{$BEDGRAPH_FILE%%.*}"        o
    rm"-rf $BEDGRAPH_SORT_TMP_FOLDER"                       && 
    mkdir"-p $BEDGRAPH_SORT_TMP_FOLDER"                     o
    pushd"$BEDGRAPH_SORT_TMP_FOLDER" || exit(1)             o 
    zgrep""" -E "^[1-9XY]" ${BEDGRAPH_REGION_GZ}""" | awk"""'{print $$0 >> $$1".bed"}'""".- o
    ls | sort"-k1,1 -k2,2n" | grep"-v ${BEDGRAPH_SAMPLE_NAME}" | xargs"${cat.name}" >> txt"./${BEDGRAPH_SAMPLE_NAME}.bedGraph"  o
    bedGraphToBigWig"./${BEDGRAPH_SAMPLE_NAME}.bedGraph ${BEDGRAPH_GENOME_FILE} ${BEDGRAPH_SAMPLE_NAME}.bigWig" o
    mv"${BEDGRAPH_SAMPLE_NAME}.bigWig ../${BEDGRAPH_SAMPLE_NAME}.bigWig"  o 
    popd || exit(1) o
    rm"-rf ${BEDGRAPH_SORT_TMP_FOLDER}"                                 
  
}