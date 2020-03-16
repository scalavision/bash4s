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

  def `{`(op: CommandOp) = 
    CommandListBuilder(Vector(OpenCommandList(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  object For {
    def apply(indexVariable: CommandOp) = 
      CFor(Vector(indexVariable))
  }

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)

  def R(args: String*) = clitools.RWrapper(CmdArgs(args.toVector))
  def R = clitools.RWrapper()

  def arp(args: String*) = clitools.ArpWrapper(CmdArgs(args.toVector))
  def arp = clitools.ArpWrapper()

  def b2sum(args: String*) = clitools.B2sumWrapper(CmdArgs(args.toVector))
  def b2sum = clitools.B2sumWrapper()

  def base32(args: String*) = clitools.Base32Wrapper(CmdArgs(args.toVector))
  def base32 = clitools.Base32Wrapper()

  def base64(args: String*) = clitools.Base64Wrapper(CmdArgs(args.toVector))
  def base64 = clitools.Base64Wrapper()

  def basename(args: String*) = clitools.BasenameWrapper(CmdArgs(args.toVector))
  def basename = clitools.BasenameWrapper()

  def basenc(args: String*) = clitools.BasencWrapper(CmdArgs(args.toVector))
  def basenc = clitools.BasencWrapper()

  def bash(args: String*) = clitools.BashWrapper(CmdArgs(args.toVector))
  def bash = clitools.BashWrapper()

  def cat(args: String*) = clitools.CatWrapper(CmdArgs(args.toVector))
  def cat = clitools.CatWrapper()

  def chcon(args: String*) = clitools.ChconWrapper(CmdArgs(args.toVector))
  def chcon = clitools.ChconWrapper()

  def chgrp(args: String*) = clitools.ChgrpWrapper(CmdArgs(args.toVector))
  def chgrp = clitools.ChgrpWrapper()

  def chmod(args: String*) = clitools.ChmodWrapper(CmdArgs(args.toVector))
  def chmod = clitools.ChmodWrapper()

  def chown(args: String*) = clitools.ChownWrapper(CmdArgs(args.toVector))
  def chown = clitools.ChownWrapper()

  def chroot(args: String*) = clitools.ChrootWrapper(CmdArgs(args.toVector))
  def chroot = clitools.ChrootWrapper()

  def cksum(args: String*) = clitools.CksumWrapper(CmdArgs(args.toVector))
  def cksum = clitools.CksumWrapper()

  def comm(args: String*) = clitools.CommWrapper(CmdArgs(args.toVector))
  def comm = clitools.CommWrapper()

  def cp(args: String*) = clitools.CpWrapper(CmdArgs(args.toVector))
  def cp = clitools.CpWrapper()

  def csplit(args: String*) = clitools.CsplitWrapper(CmdArgs(args.toVector))
  def csplit = clitools.CsplitWrapper()

  def cut(args: String*) = clitools.CutWrapper(CmdArgs(args.toVector))
  def cut = clitools.CutWrapper()

  def date(args: String*) = clitools.DateWrapper(CmdArgs(args.toVector))
  def date = clitools.DateWrapper()

  def dd(args: String*) = clitools.DdWrapper(CmdArgs(args.toVector))
  def dd = clitools.DdWrapper()

  def df(args: String*) = clitools.DfWrapper(CmdArgs(args.toVector))
  def df = clitools.DfWrapper()

  def dir(args: String*) = clitools.DirWrapper(CmdArgs(args.toVector))
  def dir = clitools.DirWrapper()

  def dircolors(args: String*) =
    clitools.DircolorsWrapper(CmdArgs(args.toVector))
  def dircolors = clitools.DircolorsWrapper()

  def dirname(args: String*) = clitools.DirnameWrapper(CmdArgs(args.toVector))
  def dirname = clitools.DirnameWrapper()

  def du(args: String*) = clitools.DuWrapper(CmdArgs(args.toVector))
  def du = clitools.DuWrapper()

  def echo(args: String*) = clitools.EchoWrapper(CmdArgs(args.toVector))
  def echo = clitools.EchoWrapper()

  def env(args: String*) = clitools.EnvWrapper(CmdArgs(args.toVector))
  def env = clitools.EnvWrapper()

  def expand(args: String*) = clitools.ExpandWrapper(CmdArgs(args.toVector))
  def expand = clitools.ExpandWrapper()

  def expr(args: String*) = clitools.ExprWrapper(CmdArgs(args.toVector))
  def expr = clitools.ExprWrapper()

  def factor(args: String*) = clitools.FactorWrapper(CmdArgs(args.toVector))
  def factor = clitools.FactorWrapper()

  def fmt(args: String*) = clitools.FmtWrapper(CmdArgs(args.toVector))
  def fmt = clitools.FmtWrapper()

  def fold(args: String*) = clitools.FoldWrapper(CmdArgs(args.toVector))
  def fold = clitools.FoldWrapper()

  def grep(args: String*) = clitools.GrepWrapper(CmdArgs(args.toVector))
  def grep = clitools.GrepWrapper()

  def groups(args: String*) = clitools.GroupsWrapper(CmdArgs(args.toVector))
  def groups = clitools.GroupsWrapper()

  def head(args: String*) = clitools.HeadWrapper(CmdArgs(args.toVector))
  def head = clitools.HeadWrapper()

  def hostid(args: String*) = clitools.HostidWrapper(CmdArgs(args.toVector))
  def hostid = clitools.HostidWrapper()

  def id(args: String*) = clitools.IdWrapper(CmdArgs(args.toVector))
  def id = clitools.IdWrapper()

  def ifconfig(args: String*) = clitools.IfconfigWrapper(CmdArgs(args.toVector))
  def ifconfig = clitools.IfconfigWrapper()

  def install(args: String*) = clitools.InstallWrapper(CmdArgs(args.toVector))
  def install = clitools.InstallWrapper()

  def join(args: String*) = clitools.JoinWrapper(CmdArgs(args.toVector))
  def join = clitools.JoinWrapper()

  def kill(args: String*) = clitools.KillWrapper(CmdArgs(args.toVector))
  def kill = clitools.KillWrapper()

  def link(args: String*) = clitools.LinkWrapper(CmdArgs(args.toVector))
  def link = clitools.LinkWrapper()

  def ln(args: String*) = clitools.LnWrapper(CmdArgs(args.toVector))
  def ln = clitools.LnWrapper()

  def logname(args: String*) = clitools.LognameWrapper(CmdArgs(args.toVector))
  def logname = clitools.LognameWrapper()

  def ls(args: String*) = clitools.LsWrapper(CmdArgs(args.toVector))
  def ls = clitools.LsWrapper()

  def md5sum(args: String*) = clitools.Md5sumWrapper(CmdArgs(args.toVector))
  def md5sum = clitools.Md5sumWrapper()

  def mkdir(args: String*) = clitools.MkdirWrapper(CmdArgs(args.toVector))
  def mkdir = clitools.MkdirWrapper()

  def mkfifo(args: String*) = clitools.MkfifoWrapper(CmdArgs(args.toVector))
  def mkfifo = clitools.MkfifoWrapper()

  def mknod(args: String*) = clitools.MknodWrapper(CmdArgs(args.toVector))
  def mknod = clitools.MknodWrapper()

  def mktemp(args: String*) = clitools.MktempWrapper(CmdArgs(args.toVector))
  def mktemp = clitools.MktempWrapper()

  def mv(args: String*) = clitools.MvWrapper(CmdArgs(args.toVector))
  def mv = clitools.MvWrapper()

  def nice(args: String*) = clitools.NiceWrapper(CmdArgs(args.toVector))
  def nice = clitools.NiceWrapper()

  def nl(args: String*) = clitools.NlWrapper(CmdArgs(args.toVector))
  def nl = clitools.NlWrapper()

  def nohup(args: String*) = clitools.NohupWrapper(CmdArgs(args.toVector))
  def nohup = clitools.NohupWrapper()

  def nproc(args: String*) = clitools.NprocWrapper(CmdArgs(args.toVector))
  def nproc = clitools.NprocWrapper()

  def numfmt(args: String*) = clitools.NumfmtWrapper(CmdArgs(args.toVector))
  def numfmt = clitools.NumfmtWrapper()

  def od(args: String*) = clitools.OdWrapper(CmdArgs(args.toVector))
  def od = clitools.OdWrapper()

  def paste(args: String*) = clitools.PasteWrapper(CmdArgs(args.toVector))
  def paste = clitools.PasteWrapper()

  def pathchk(args: String*) = clitools.PathchkWrapper(CmdArgs(args.toVector))
  def pathchk = clitools.PathchkWrapper()

  def ping(args: String*) = clitools.PingWrapper(CmdArgs(args.toVector))
  def ping = clitools.PingWrapper()

  def pinky(args: String*) = clitools.PinkyWrapper(CmdArgs(args.toVector))
  def pinky = clitools.PinkyWrapper()

  def pr(args: String*) = clitools.PrWrapper(CmdArgs(args.toVector))
  def pr = clitools.PrWrapper()

  def printenv(args: String*) = clitools.PrintenvWrapper(CmdArgs(args.toVector))
  def printenv = clitools.PrintenvWrapper()

  def printf(args: String*) = clitools.PrintfWrapper(CmdArgs(args.toVector))
  def printf = clitools.PrintfWrapper()

  def ptx(args: String*) = clitools.PtxWrapper(CmdArgs(args.toVector))
  def ptx = clitools.PtxWrapper()

  def pwd(args: String*) = clitools.PwdWrapper(CmdArgs(args.toVector))
  def pwd = clitools.PwdWrapper()

  def py(args: String*) = clitools.PyWrapper(CmdArgs(args.toVector))
  def py = clitools.PyWrapper()

  def readlink(args: String*) = clitools.ReadlinkWrapper(CmdArgs(args.toVector))
  def readlink = clitools.ReadlinkWrapper()

  def realpath(args: String*) = clitools.RealpathWrapper(CmdArgs(args.toVector))
  def realpath = clitools.RealpathWrapper()

  def rm(args: String*) = clitools.RmWrapper(CmdArgs(args.toVector))
  def rm = clitools.RmWrapper()

  def rmdir(args: String*) = clitools.RmdirWrapper(CmdArgs(args.toVector))
  def rmdir = clitools.RmdirWrapper()

  def rsync(args: String*) = clitools.RsyncWrapper(CmdArgs(args.toVector))
  def rsync = clitools.RsyncWrapper()

  def runcon(args: String*) = clitools.RunconWrapper(CmdArgs(args.toVector))
  def runcon = clitools.RunconWrapper()

  def scala(args: String*) = clitools.ScalaWrapper(CmdArgs(args.toVector))
  def scala = clitools.ScalaWrapper()

  def seq(args: String*) = clitools.SeqWrapper(CmdArgs(args.toVector))
  def seq = clitools.SeqWrapper()

  def sh(args: String*) = clitools.ShWrapper(CmdArgs(args.toVector))
  def sh = clitools.ShWrapper()

  def sha1sum(args: String*) = clitools.Sha1sumWrapper(CmdArgs(args.toVector))
  def sha1sum = clitools.Sha1sumWrapper()

  def sha224sum(args: String*) =
    clitools.Sha224sumWrapper(CmdArgs(args.toVector))
  def sha224sum = clitools.Sha224sumWrapper()

  def sha256sum(args: String*) =
    clitools.Sha256sumWrapper(CmdArgs(args.toVector))
  def sha256sum = clitools.Sha256sumWrapper()

  def sha384sum(args: String*) =
    clitools.Sha384sumWrapper(CmdArgs(args.toVector))
  def sha384sum = clitools.Sha384sumWrapper()

  def sha512sum(args: String*) =
    clitools.Sha512sumWrapper(CmdArgs(args.toVector))
  def sha512sum = clitools.Sha512sumWrapper()

  def shred(args: String*) = clitools.ShredWrapper(CmdArgs(args.toVector))
  def shred = clitools.ShredWrapper()

  def shuf(args: String*) = clitools.ShufWrapper(CmdArgs(args.toVector))
  def shuf = clitools.ShufWrapper()

  def sleep(args: String*) = clitools.SleepWrapper(CmdArgs(args.toVector))
  def sleep = clitools.SleepWrapper()

  def sort(args: String*) = clitools.SortWrapper(CmdArgs(args.toVector))
  def sort = clitools.SortWrapper()

  def split(args: String*) = clitools.SplitWrapper(CmdArgs(args.toVector))
  def split = clitools.SplitWrapper()

  def ssh(args: String*) = clitools.SshWrapper(CmdArgs(args.toVector))
  def ssh = clitools.SshWrapper()

  def stat(args: String*) = clitools.StatWrapper(CmdArgs(args.toVector))
  def stat = clitools.StatWrapper()

  def stdbuf(args: String*) = clitools.StdbufWrapper(CmdArgs(args.toVector))
  def stdbuf = clitools.StdbufWrapper()

  def stty(args: String*) = clitools.SttyWrapper(CmdArgs(args.toVector))
  def stty = clitools.SttyWrapper()

  def sum(args: String*) = clitools.SumWrapper(CmdArgs(args.toVector))
  def sum = clitools.SumWrapper()

  def sync(args: String*) = clitools.SyncWrapper(CmdArgs(args.toVector))
  def sync = clitools.SyncWrapper()

  def tac(args: String*) = clitools.TacWrapper(CmdArgs(args.toVector))
  def tac = clitools.TacWrapper()

  def tail(args: String*) = clitools.TailWrapper(CmdArgs(args.toVector))
  def tail = clitools.TailWrapper()

  def tee(args: String*) = clitools.TeeWrapper(CmdArgs(args.toVector))
  def tee = clitools.TeeWrapper()

  def test(args: String*) = clitools.TestWrapper(CmdArgs(args.toVector))
  def test = clitools.TestWrapper()

  def timeout(args: String*) = clitools.TimeoutWrapper(CmdArgs(args.toVector))
  def timeout = clitools.TimeoutWrapper()

  def touch(args: String*) = clitools.TouchWrapper(CmdArgs(args.toVector))
  def touch = clitools.TouchWrapper()

  def tr(args: String*) = clitools.TrWrapper(CmdArgs(args.toVector))
  def tr = clitools.TrWrapper()

  def truncate(args: String*) = clitools.TruncateWrapper(CmdArgs(args.toVector))
  def truncate = clitools.TruncateWrapper()

  def tsort(args: String*) = clitools.TsortWrapper(CmdArgs(args.toVector))
  def tsort = clitools.TsortWrapper()

  def tty(args: String*) = clitools.TtyWrapper(CmdArgs(args.toVector))
  def tty = clitools.TtyWrapper()

  def uname(args: String*) = clitools.UnameWrapper(CmdArgs(args.toVector))
  def uname = clitools.UnameWrapper()

  def unexpand(args: String*) = clitools.UnexpandWrapper(CmdArgs(args.toVector))
  def unexpand = clitools.UnexpandWrapper()

  def uniq(args: String*) = clitools.UniqWrapper(CmdArgs(args.toVector))
  def uniq = clitools.UniqWrapper()

  def unlink(args: String*) = clitools.UnlinkWrapper(CmdArgs(args.toVector))
  def unlink = clitools.UnlinkWrapper()

  def uptime(args: String*) = clitools.UptimeWrapper(CmdArgs(args.toVector))
  def uptime = clitools.UptimeWrapper()

  def users(args: String*) = clitools.UsersWrapper(CmdArgs(args.toVector))
  def users = clitools.UsersWrapper()

  def vdir(args: String*) = clitools.VdirWrapper(CmdArgs(args.toVector))
  def vdir = clitools.VdirWrapper()

  def wc(args: String*) = clitools.WcWrapper(CmdArgs(args.toVector))
  def wc = clitools.WcWrapper()

  def who(args: String*) = clitools.WhoWrapper(CmdArgs(args.toVector))
  def who = clitools.WhoWrapper()

  def whoami(args: String*) = clitools.WhoamiWrapper(CmdArgs(args.toVector))
  def whoami = clitools.WhoamiWrapper()

  def yes(args: String*) = clitools.YesWrapper(CmdArgs(args.toVector))
  def yes = clitools.YesWrapper()

  def zsh(args: String*) = clitools.ZshWrapper(CmdArgs(args.toVector))
  def zsh = clitools.ZshWrapper()

  implicit class CmdSyntax(s: StringContext) {

    def $(args: Any*) =
      LocalizationString(CmdArgCtx(args.toVector, s))
    
    def $$(args: Any*) =
      AnsiCQuoted(CmdArgCtx(args.toVector, s))

    def txt(args: Any*) =
      TextVariable(CmdArgCtx(args.toVector, s))

    def array(args: Any*) =
      ArrayVariable(CmdArgCtx(args.toVector, s))

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
