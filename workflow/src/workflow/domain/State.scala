package workflow.domain

sealed trait State
object State {
  final case class ManageWorkflow()
  final case class ManageProcesses()
}