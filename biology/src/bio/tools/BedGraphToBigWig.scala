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

  val LC_COLLATE = Var
  override def setup = init(
    BEDGRAPH_REGION_GZ o
    BEDGRAPH_SORT_TMP_FOLDER
  )

  def op = 
    LC_COLLATE `=` txt"C"             o
    LC_COLLATE.export                 o
    rm"-f $BEDGRAPH_SORT_TMP_FOLDER"  o
    mkdir"-p $BEDGRAPH_SORT_TMP_FOLDER" o
    pushd"$BEDGRAPH_SORT_TMP_FOLDER" o
    popd
    
//    bedGraphToBigWig"$BEDGRAPH"
  
}