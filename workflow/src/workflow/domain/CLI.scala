package workflow.domain

sealed trait WorkflowCommand

object WorkflowCommand {
  final case object Menu extends WorkflowCommand
  final case object ListProcesses extends WorkflowCommand
  
  def changeView(view: String): Option[WorkflowCommand] = view match {
    case "ps" => Some(ListProcesses)
    case _ => None
  }
}

sealed trait AppError
case object ParseError                extends AppError
case object IllegalStateError         extends AppError

/*
sealed trait WorkflowView
object WorkflowView {
  final case object ListProcesses extends WorkflowView

  def changeView(view: String) = view match {
    case "ps" => Some(ListProcesses)
  }

}

sealed trait WorkflowManager
object WorkflowManager {
}*/