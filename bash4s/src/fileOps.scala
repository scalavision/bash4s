package bash

import domain._

case class RegexFileSearchApi() {

  def txt = 
    RegexFileSearch("*.txt")
  
  def bam = 
    RegexFileSearch("*.bam")
}