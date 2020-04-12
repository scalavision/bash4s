package bio.tools

import bio.dsl._
import bash4s.dsl._
import bash4s.domain._
import bash4s.ScriptGenerator
import bash4s.scripts._

case class BedGraphToBigWig(
  mosdepthRegionFile: RegionsBedGz,
  sortTmpFolder: FolderPath = dirPath"/tmp/extractBedGraph"
) extends Script {

  def param = ScriptGenerator.gen[BedGraphToBigWig](this.asInstanceOf[BedGraphToBigWig])

  val BEDGRAPH_REGION_GZ = Arg(param.$1(mosdepthRegionFile))
  val BEDGRAPH_SORT_TMP_FOLDER = Arg(param.$2(sortTmpFolder))
  val SAMPLE_NAME = Var

  val LC_COLLATE = Var

  val env = 
    BEDGRAPH_REGION_GZ o
    BEDGRAPH_SORT_TMP_FOLDER

  override def setup = init(env)

  def op = 
    LC_COLLATE `=` txt"C"                                   o
    LC_COLLATE.export                                     o
    SAMPLE_NAME `=$`(basename"-- ${BEDGRAPH_REGION_GZ}")    o
    rm"-f $BEDGRAPH_SORT_TMP_FOLDER"                       && 
    mkdir"-p $BEDGRAPH_SORT_TMP_FOLDER"                    && 
    pushd"$BEDGRAPH_SORT_TMP_FOLDER" || exit(1)             o 
    zgrep""" -E "^[1-9XY]" ${BEDGRAPH_REGION_GZ}""" | awk"""'{print $$0 >> $$1".bed"}'""".- o
    (ls | sort"-k1,1 -k2,2n" | xargs"${cat.name}" >> SAMPLE_NAME.$) &&
    mv"${SAMPLE_NAME} ../${SAMPLE_NAME}.bigWig" &&
    rm"-rf ${BEDGRAPH_SORT_TMP_FOLDER}" o
    popd || exit(1)
  
}