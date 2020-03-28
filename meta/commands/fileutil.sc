val wd = os.pwd

val text = os.read(wd / "file_utils.dat")
  .lines.toList.filterNot(_.isEmpty)

case class Command(name: String, description: String)

val data = 
  text.sliding(size=2, step=2).toList.map{ l =>
    Command(l.head, l.last)
  }


pprint.pprintln(data)
