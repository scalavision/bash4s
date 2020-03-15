package bash4s

import domain._

trait BashCommandAdapter {
  def toCmd: SimpleCommand
}

package object bash4s {

  implicit def cmdAliasConverter: BashCommandAdapter => SimpleCommand = _.toCmd

  object Until {
    def `[[`(op: CommandOp) = CUntil(Vector(OpenDoubleSquareBracket(), op))
  }

  object While {
    def `[[`(op: CommandOp) = CWhile(Vector(OpenDoubleSquareBracket(), op))
  }

  object If {
    def `[[`(op: CommandOp) = CIf(Vector(OpenDoubleSquareBracket(), op))
  }

  def `[[`(op: CommandOp) =
    CommandListBuilder(Vector(OpenDoubleSquareBracket(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  def R = clitools.RWrapper()
  def arp = clitools.ArpWrapper()
  def b2sum = clitools.B2sumWrapper()
  def base32 = clitools.Base32Wrapper()
  def base64 = clitools.Base64Wrapper()
  def basename = clitools.BasenameWrapper()
  def basenc = clitools.BasencWrapper()
  def bash = clitools.BashWrapper()
  def cat = clitools.CatWrapper()
  def chcon = clitools.ChconWrapper()
  def chgrp = clitools.ChgrpWrapper()
  def chmod = clitools.ChmodWrapper()
  def chown = clitools.ChownWrapper()
  def chroot = clitools.ChrootWrapper()
  def cksum = clitools.CksumWrapper()
  def comm = clitools.CommWrapper()
  def cp = clitools.CpWrapper()
  def csplit = clitools.CsplitWrapper()
  def cut = clitools.CutWrapper()
  def date = clitools.DateWrapper()
  def dd = clitools.DdWrapper()
  def df = clitools.DfWrapper()
  def dir = clitools.DirWrapper()
  def dircolors = clitools.DircolorsWrapper()
  def dirname = clitools.DirnameWrapper()
  def du = clitools.DuWrapper()
  def echo = clitools.EchoWrapper()
  def env = clitools.EnvWrapper()
  def expand = clitools.ExpandWrapper()
  def expr = clitools.ExprWrapper()
  def factor = clitools.FactorWrapper()
  def fmt = clitools.FmtWrapper()
  def fold = clitools.FoldWrapper()
  def grep = clitools.GrepWrapper()
  def groups = clitools.GroupsWrapper()
  def head = clitools.HeadWrapper()
  def hostid = clitools.HostidWrapper()
  def id = clitools.IdWrapper()
  def ifconfig = clitools.IfconfigWrapper()
  def install = clitools.InstallWrapper()
  def join = clitools.JoinWrapper()
  def kill = clitools.KillWrapper()
  def link = clitools.LinkWrapper()
  def ln = clitools.LnWrapper()
  def logname = clitools.LognameWrapper()
  def ls = clitools.LsWrapper()
  def md5sum = clitools.Md5sumWrapper()
  def mkdir = clitools.MkdirWrapper()
  def mkfifo = clitools.MkfifoWrapper()
  def mknod = clitools.MknodWrapper()
  def mktemp = clitools.MktempWrapper()
  def mv = clitools.MvWrapper()
  def nice = clitools.NiceWrapper()
  def nl = clitools.NlWrapper()
  def nohup = clitools.NohupWrapper()
  def nproc = clitools.NprocWrapper()
  def numfmt = clitools.NumfmtWrapper()
  def od = clitools.OdWrapper()
  def paste = clitools.PasteWrapper()
  def pathchk = clitools.PathchkWrapper()
  def ping = clitools.PingWrapper()
  def pinky = clitools.PinkyWrapper()
  def pr = clitools.PrWrapper()
  def printenv = clitools.PrintenvWrapper()
  def printf = clitools.PrintfWrapper()
  def ptx = clitools.PtxWrapper()
  def pwd = clitools.PwdWrapper()
  def py = clitools.PyWrapper()
  def readlink = clitools.ReadlinkWrapper()
  def realpath = clitools.RealpathWrapper()
  def rm = clitools.RmWrapper()
  def rmdir = clitools.RmdirWrapper()
  def rsync = clitools.RsyncWrapper()
  def runcon = clitools.RunconWrapper()
  def scala = clitools.ScalaWrapper()
  def seq = clitools.SeqWrapper()
  def sh = clitools.ShWrapper()
  def sha1sum = clitools.Sha1sumWrapper()
  def sha224sum = clitools.Sha224sumWrapper()
  def sha256sum = clitools.Sha256sumWrapper()
  def sha384sum = clitools.Sha384sumWrapper()
  def sha512sum = clitools.Sha512sumWrapper()
  def shred = clitools.ShredWrapper()
  def shuf = clitools.ShufWrapper()
  def sleep = clitools.SleepWrapper()
  def sort = clitools.SortWrapper()
  def split = clitools.SplitWrapper()
  def ssh = clitools.SshWrapper()
  def stat = clitools.StatWrapper()
  def stdbuf = clitools.StdbufWrapper()
  def stty = clitools.SttyWrapper()
  def sum = clitools.SumWrapper()
  def sync = clitools.SyncWrapper()
  def tac = clitools.TacWrapper()
  def tail = clitools.TailWrapper()
  def tee = clitools.TeeWrapper()
  def test = clitools.TestWrapper()
  def timeout = clitools.TimeoutWrapper()
  def touch = clitools.TouchWrapper()
  def tr = clitools.TrWrapper()
  def truncate = clitools.TruncateWrapper()
  def tsort = clitools.TsortWrapper()
  def tty = clitools.TtyWrapper()
  def uname = clitools.UnameWrapper()
  def unexpand = clitools.UnexpandWrapper()
  def uniq = clitools.UniqWrapper()
  def unlink = clitools.UnlinkWrapper()
  def uptime = clitools.UptimeWrapper()
  def users = clitools.UsersWrapper()
  def vdir = clitools.VdirWrapper()
  def wc = clitools.WcWrapper()
  def who = clitools.WhoWrapper()
  def whoami = clitools.WhoamiWrapper()
  def yes = clitools.YesWrapper()
  def zsh = clitools.ZshWrapper()
  implicit class CmdSyntax(s: StringContext) {

    def R(args: Any*) =
      SimpleCommand("R", CmdArgCtx(args.toVector, s))

    def arp(args: Any*) =
      SimpleCommand("arp", CmdArgCtx(args.toVector, s))

    def b2sum(args: Any*) =
      SimpleCommand("b2sum", CmdArgCtx(args.toVector, s))

    def base32(args: Any*) =
      SimpleCommand("base32", CmdArgCtx(args.toVector, s))

    def base64(args: Any*) =
      SimpleCommand("base64", CmdArgCtx(args.toVector, s))

    def basename(args: Any*) =
      SimpleCommand("basename", CmdArgCtx(args.toVector, s))

    def basenc(args: Any*) =
      SimpleCommand("basenc", CmdArgCtx(args.toVector, s))

    def bash(args: Any*) =
      SimpleCommand("bash", CmdArgCtx(args.toVector, s))

    def cat(args: Any*) =
      SimpleCommand("cat", CmdArgCtx(args.toVector, s))

    def chcon(args: Any*) =
      SimpleCommand("chcon", CmdArgCtx(args.toVector, s))

    def chgrp(args: Any*) =
      SimpleCommand("chgrp", CmdArgCtx(args.toVector, s))

    def chmod(args: Any*) =
      SimpleCommand("chmod", CmdArgCtx(args.toVector, s))

    def chown(args: Any*) =
      SimpleCommand("chown", CmdArgCtx(args.toVector, s))

    def chroot(args: Any*) =
      SimpleCommand("chroot", CmdArgCtx(args.toVector, s))

    def cksum(args: Any*) =
      SimpleCommand("cksum", CmdArgCtx(args.toVector, s))

    def comm(args: Any*) =
      SimpleCommand("comm", CmdArgCtx(args.toVector, s))

    def cp(args: Any*) =
      SimpleCommand("cp", CmdArgCtx(args.toVector, s))

    def csplit(args: Any*) =
      SimpleCommand("csplit", CmdArgCtx(args.toVector, s))

    def cut(args: Any*) =
      SimpleCommand("cut", CmdArgCtx(args.toVector, s))

    def date(args: Any*) =
      SimpleCommand("date", CmdArgCtx(args.toVector, s))

    def dd(args: Any*) =
      SimpleCommand("dd", CmdArgCtx(args.toVector, s))

    def df(args: Any*) =
      SimpleCommand("df", CmdArgCtx(args.toVector, s))

    def dir(args: Any*) =
      SimpleCommand("dir", CmdArgCtx(args.toVector, s))

    def dircolors(args: Any*) =
      SimpleCommand("dircolors", CmdArgCtx(args.toVector, s))

    def dirname(args: Any*) =
      SimpleCommand("dirname", CmdArgCtx(args.toVector, s))

    def du(args: Any*) =
      SimpleCommand("du", CmdArgCtx(args.toVector, s))

    def echo(args: Any*) =
      SimpleCommand("echo", CmdArgCtx(args.toVector, s))

    def env(args: Any*) =
      SimpleCommand("env", CmdArgCtx(args.toVector, s))

    def expand(args: Any*) =
      SimpleCommand("expand", CmdArgCtx(args.toVector, s))

    def expr(args: Any*) =
      SimpleCommand("expr", CmdArgCtx(args.toVector, s))

    def factor(args: Any*) =
      SimpleCommand("factor", CmdArgCtx(args.toVector, s))

    def fmt(args: Any*) =
      SimpleCommand("fmt", CmdArgCtx(args.toVector, s))

    def fold(args: Any*) =
      SimpleCommand("fold", CmdArgCtx(args.toVector, s))

    def grep(args: Any*) =
      SimpleCommand("grep", CmdArgCtx(args.toVector, s))

    def groups(args: Any*) =
      SimpleCommand("groups", CmdArgCtx(args.toVector, s))

    def head(args: Any*) =
      SimpleCommand("head", CmdArgCtx(args.toVector, s))

    def hostid(args: Any*) =
      SimpleCommand("hostid", CmdArgCtx(args.toVector, s))

    def id(args: Any*) =
      SimpleCommand("id", CmdArgCtx(args.toVector, s))

    def ifconfig(args: Any*) =
      SimpleCommand("ifconfig", CmdArgCtx(args.toVector, s))

    def install(args: Any*) =
      SimpleCommand("install", CmdArgCtx(args.toVector, s))

    def join(args: Any*) =
      SimpleCommand("join", CmdArgCtx(args.toVector, s))

    def kill(args: Any*) =
      SimpleCommand("kill", CmdArgCtx(args.toVector, s))

    def link(args: Any*) =
      SimpleCommand("link", CmdArgCtx(args.toVector, s))

    def ln(args: Any*) =
      SimpleCommand("ln", CmdArgCtx(args.toVector, s))

    def logname(args: Any*) =
      SimpleCommand("logname", CmdArgCtx(args.toVector, s))

    def ls(args: Any*) =
      SimpleCommand("ls", CmdArgCtx(args.toVector, s))

    def md5sum(args: Any*) =
      SimpleCommand("md5sum", CmdArgCtx(args.toVector, s))

    def mkdir(args: Any*) =
      SimpleCommand("mkdir", CmdArgCtx(args.toVector, s))

    def mkfifo(args: Any*) =
      SimpleCommand("mkfifo", CmdArgCtx(args.toVector, s))

    def mknod(args: Any*) =
      SimpleCommand("mknod", CmdArgCtx(args.toVector, s))

    def mktemp(args: Any*) =
      SimpleCommand("mktemp", CmdArgCtx(args.toVector, s))

    def mv(args: Any*) =
      SimpleCommand("mv", CmdArgCtx(args.toVector, s))

    def nice(args: Any*) =
      SimpleCommand("nice", CmdArgCtx(args.toVector, s))

    def nl(args: Any*) =
      SimpleCommand("nl", CmdArgCtx(args.toVector, s))

    def nohup(args: Any*) =
      SimpleCommand("nohup", CmdArgCtx(args.toVector, s))

    def nproc(args: Any*) =
      SimpleCommand("nproc", CmdArgCtx(args.toVector, s))

    def numfmt(args: Any*) =
      SimpleCommand("numfmt", CmdArgCtx(args.toVector, s))

    def od(args: Any*) =
      SimpleCommand("od", CmdArgCtx(args.toVector, s))

    def paste(args: Any*) =
      SimpleCommand("paste", CmdArgCtx(args.toVector, s))

    def pathchk(args: Any*) =
      SimpleCommand("pathchk", CmdArgCtx(args.toVector, s))

    def ping(args: Any*) =
      SimpleCommand("ping", CmdArgCtx(args.toVector, s))

    def pinky(args: Any*) =
      SimpleCommand("pinky", CmdArgCtx(args.toVector, s))

    def pr(args: Any*) =
      SimpleCommand("pr", CmdArgCtx(args.toVector, s))

    def printenv(args: Any*) =
      SimpleCommand("printenv", CmdArgCtx(args.toVector, s))

    def printf(args: Any*) =
      SimpleCommand("printf", CmdArgCtx(args.toVector, s))

    def ptx(args: Any*) =
      SimpleCommand("ptx", CmdArgCtx(args.toVector, s))

    def pwd(args: Any*) =
      SimpleCommand("pwd", CmdArgCtx(args.toVector, s))

    def py(args: Any*) =
      SimpleCommand("py", CmdArgCtx(args.toVector, s))

    def readlink(args: Any*) =
      SimpleCommand("readlink", CmdArgCtx(args.toVector, s))

    def realpath(args: Any*) =
      SimpleCommand("realpath", CmdArgCtx(args.toVector, s))

    def rm(args: Any*) =
      SimpleCommand("rm", CmdArgCtx(args.toVector, s))

    def rmdir(args: Any*) =
      SimpleCommand("rmdir", CmdArgCtx(args.toVector, s))

    def rsync(args: Any*) =
      SimpleCommand("rsync", CmdArgCtx(args.toVector, s))

    def runcon(args: Any*) =
      SimpleCommand("runcon", CmdArgCtx(args.toVector, s))

    def scala(args: Any*) =
      SimpleCommand("scala", CmdArgCtx(args.toVector, s))

    def seq(args: Any*) =
      SimpleCommand("seq", CmdArgCtx(args.toVector, s))

    def sh(args: Any*) =
      SimpleCommand("sh", CmdArgCtx(args.toVector, s))

    def sha1sum(args: Any*) =
      SimpleCommand("sha1sum", CmdArgCtx(args.toVector, s))

    def sha224sum(args: Any*) =
      SimpleCommand("sha224sum", CmdArgCtx(args.toVector, s))

    def sha256sum(args: Any*) =
      SimpleCommand("sha256sum", CmdArgCtx(args.toVector, s))

    def sha384sum(args: Any*) =
      SimpleCommand("sha384sum", CmdArgCtx(args.toVector, s))

    def sha512sum(args: Any*) =
      SimpleCommand("sha512sum", CmdArgCtx(args.toVector, s))

    def shred(args: Any*) =
      SimpleCommand("shred", CmdArgCtx(args.toVector, s))

    def shuf(args: Any*) =
      SimpleCommand("shuf", CmdArgCtx(args.toVector, s))

    def sleep(args: Any*) =
      SimpleCommand("sleep", CmdArgCtx(args.toVector, s))

    def sort(args: Any*) =
      SimpleCommand("sort", CmdArgCtx(args.toVector, s))

    def split(args: Any*) =
      SimpleCommand("split", CmdArgCtx(args.toVector, s))

    def ssh(args: Any*) =
      SimpleCommand("ssh", CmdArgCtx(args.toVector, s))

    def stat(args: Any*) =
      SimpleCommand("stat", CmdArgCtx(args.toVector, s))

    def stdbuf(args: Any*) =
      SimpleCommand("stdbuf", CmdArgCtx(args.toVector, s))

    def stty(args: Any*) =
      SimpleCommand("stty", CmdArgCtx(args.toVector, s))

    def sum(args: Any*) =
      SimpleCommand("sum", CmdArgCtx(args.toVector, s))

    def sync(args: Any*) =
      SimpleCommand("sync", CmdArgCtx(args.toVector, s))

    def tac(args: Any*) =
      SimpleCommand("tac", CmdArgCtx(args.toVector, s))

    def tail(args: Any*) =
      SimpleCommand("tail", CmdArgCtx(args.toVector, s))

    def tee(args: Any*) =
      SimpleCommand("tee", CmdArgCtx(args.toVector, s))

    def test(args: Any*) =
      SimpleCommand("test", CmdArgCtx(args.toVector, s))

    def timeout(args: Any*) =
      SimpleCommand("timeout", CmdArgCtx(args.toVector, s))

    def touch(args: Any*) =
      SimpleCommand("touch", CmdArgCtx(args.toVector, s))

    def tr(args: Any*) =
      SimpleCommand("tr", CmdArgCtx(args.toVector, s))

    def truncate(args: Any*) =
      SimpleCommand("truncate", CmdArgCtx(args.toVector, s))

    def tsort(args: Any*) =
      SimpleCommand("tsort", CmdArgCtx(args.toVector, s))

    def tty(args: Any*) =
      SimpleCommand("tty", CmdArgCtx(args.toVector, s))

    def uname(args: Any*) =
      SimpleCommand("uname", CmdArgCtx(args.toVector, s))

    def unexpand(args: Any*) =
      SimpleCommand("unexpand", CmdArgCtx(args.toVector, s))

    def uniq(args: Any*) =
      SimpleCommand("uniq", CmdArgCtx(args.toVector, s))

    def unlink(args: Any*) =
      SimpleCommand("unlink", CmdArgCtx(args.toVector, s))

    def uptime(args: Any*) =
      SimpleCommand("uptime", CmdArgCtx(args.toVector, s))

    def users(args: Any*) =
      SimpleCommand("users", CmdArgCtx(args.toVector, s))

    def vdir(args: Any*) =
      SimpleCommand("vdir", CmdArgCtx(args.toVector, s))

    def wc(args: Any*) =
      SimpleCommand("wc", CmdArgCtx(args.toVector, s))

    def who(args: Any*) =
      SimpleCommand("who", CmdArgCtx(args.toVector, s))

    def whoami(args: Any*) =
      SimpleCommand("whoami", CmdArgCtx(args.toVector, s))

    def yes(args: Any*) =
      SimpleCommand("yes", CmdArgCtx(args.toVector, s))

    def zsh(args: Any*) =
      SimpleCommand("zsh", CmdArgCtx(args.toVector, s))
  }

}
