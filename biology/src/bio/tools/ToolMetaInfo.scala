package bio.tools

abstract class ToolMetaInfo (implicit n: sourcecode.Name) {
  val packageName: String
  val version: String
  val className = n.value
}