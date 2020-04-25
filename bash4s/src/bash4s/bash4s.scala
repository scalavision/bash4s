package object bash4s {
  import domain._

  type Script = scripts.Script

  type `/dev/stdin` = DevStdIn.type
  type `/dev/stdout` = DevStdOut.type
  type `/dev/stderr` = DevStdErr.type
  type `/dev/fd` = DevFd
  type `/dev/tcp` = DevTcp
  type `/dev/udp` = DevUdp
  type `/dev/null` = DevNull.type
  type `/dev/random` = DevRandom.type

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

  object #! {
    def apply(shebang: String) = SheBang(s"#!${shebang}")
    def `/usr/bin/env`(shebang: String) = SheBang(s"#!/usr/bin/env $shebang")
    def `/bin/bash` = SheBang("#!/bin/bash")
    def `/bin/sh` = SheBang("#!/bin/sh")
    def bash = SheBang("#!/usr/bin/env bash")
    def sh = SheBang("#!/usr/bin/env sh")
  }

  def `[[`(op: CommandOp) =
    CommandListBuilder(Vector(OpenDoubleSquareBracket(), op))

  def `{`(op: CommandOp) =
    CommandListBuilder(Vector(OpenGroupInContext(), op))

  def `(`(op: CommandOp) =
    CommandListBuilder(Vector(OpenSubShellEnv(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  def <(op: CommandOp) = ScriptBuilder(Vector(StdIn(), op))

  object exit {
    def apply(code: Int) =
      SimpleCommand("exit", CmdArgs(Vector(code.toString())))
  }

  def await = bash4s.clitools.AwaitWrapper()

  object For {
    def apply(indexVariable: CommandOp) =
      CFor(Vector(indexVariable))
  }

  def cat(hereStr: HereString) =
    SimpleCommand("cat", hereStr)

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)

  def Arg(b: BashCliArgVariable)(implicit name: sourcecode.Name) =
    BashVariable(name.value, b)

  def ArgOpt(b: BashCliOptArgVariable)(implicit name: sourcecode.Name) =
    BashVariable(name.value, b)

  def Array(implicit name: sourcecode.Name) =
    BashVariable(name.value, UnsetArrayVariable())

  // True if file exists
  def a(op: CommandOp) = CIfIsFile(op)

  // True if file exists and is a block special file
  def b(op: CommandOp) = CIsBlock(op)

  // True if file exists and is a character special file
  def c(op: CommandOp) = CIsCharacter(op)

  // True if file exists and is a directory
  def d(op: CommandOp) = CIsDirectory(op)

  // True if file exists
  def e(op: CommandOp) = CIsFile(op)

  // True if file exists and is a regular file
  def f(op: CommandOp) = CGroupIdBitSet(op)

  // True if file exists and its set-group-id bit is set
  def g(op: CommandOp) = CIsSymbolLink(op)

  // True if file exists and is a symbolic link
  def h(op: CommandOp) = CStickyBitSet(op)

  // True if file exists and its "sticky" bit is set
  def k(op: CommandOp) = CIsNamedPipe(op)

  // True if file exists and is a named pipe (FIFO)
  def p(op: CommandOp) = CIsReadAble(op)

  // True if file exists and is readable
  def r(op: CommandOp) = CIsGreaterThanZero(op)

  // True if file exists and has a size greater than zero
  def s(op: CommandOp) = CFileDescriptorIsOpenAndReferTerminal(op)

  // True if file descriptor fd is open and refers to a terminal
  def t(op: CommandOp) = CUserIdBitSet(op)

  // True if file exists and its set-user-id bit is set
  def u(op: CommandOp) = CIsWritable(op)

  // True if file exists and is writable
  def w(op: CommandOp) = CIsExecutable(op)

  // True if file exists and is executable
  def x(op: CommandOp) = CIsOwnedByEffectiveGroupId(op)

  // True if file exists and is owned by the effective group id
  def G(op: CommandOp) = CIsSymbolicLink(op)

  // True if file exists and is a symbolic link
  def L(op: CommandOp) = CIsModifiedSinceLastRead(op)

  // True if file exists and has been modified since it was last read
  def N(op: CommandOp) = CIsOwnedByEffectiveUserId(op)

  // True if file exists and is owned by the effective user id
  def O(op: CommandOp) = CIsSocket(op)

  def Await(args: String*) = clitools.AwaitWrapper(CmdArgs(args.toVector))
  def Await = clitools.AwaitWrapper()

  def R(args: String*) = clitools.RWrapper(CmdArgs(args.toVector))
  def R = clitools.RWrapper()

  def alias(args: String*) = clitools.AliasWrapper(CmdArgs(args.toVector))
  def alias = clitools.AliasWrapper()

  def antiword(args: String*) = clitools.AntiwordWrapper(CmdArgs(args.toVector))
  def antiword = clitools.AntiwordWrapper()

  def apt(args: String*) = clitools.AptWrapper(CmdArgs(args.toVector))
  def apt = clitools.AptWrapper()

  def apt_cache(args: String*) =
    clitools.Apt_cacheWrapper(CmdArgs(args.toVector))
  def apt_cache = clitools.Apt_cacheWrapper()

  def apt_file(args: String*) = clitools.Apt_fileWrapper(CmdArgs(args.toVector))
  def apt_file = clitools.Apt_fileWrapper()

  def apt_get(args: String*) = clitools.Apt_getWrapper(CmdArgs(args.toVector))
  def apt_get = clitools.Apt_getWrapper()

  def arp(args: String*) = clitools.ArpWrapper(CmdArgs(args.toVector))
  def arp = clitools.ArpWrapper()

  def at(args: String*) = clitools.AtWrapper(CmdArgs(args.toVector))
  def at = clitools.AtWrapper()

  def atq(args: String*) = clitools.AtqWrapper(CmdArgs(args.toVector))
  def atq = clitools.AtqWrapper()

  def atrm(args: String*) = clitools.AtrmWrapper(CmdArgs(args.toVector))
  def atrm = clitools.AtrmWrapper()

  def auditctl(args: String*) = clitools.AuditctlWrapper(CmdArgs(args.toVector))
  def auditctl = clitools.AuditctlWrapper()

  def ausearch(args: String*) = clitools.AusearchWrapper(CmdArgs(args.toVector))
  def ausearch = clitools.AusearchWrapper()

  def awk(args: String*) = clitools.AwkWrapper(CmdArgs(args.toVector))
  def awk = clitools.AwkWrapper()

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

  def bg(args: String*) = clitools.BgWrapper(CmdArgs(args.toVector))
  def bg = clitools.BgWrapper()

  def bgrep(args: String*) = clitools.BgrepWrapper(CmdArgs(args.toVector))
  def bgrep = clitools.BgrepWrapper()

  def bind(args: String*) = clitools.BindWrapper(CmdArgs(args.toVector))
  def bind = clitools.BindWrapper()

  def builtin(args: String*) = clitools.BuiltinWrapper(CmdArgs(args.toVector))
  def builtin = clitools.BuiltinWrapper()

  def bzcat(args: String*) = clitools.BzcatWrapper(CmdArgs(args.toVector))
  def bzcat = clitools.BzcatWrapper()

  def bzgrep(args: String*) = clitools.BzgrepWrapper(CmdArgs(args.toVector))
  def bzgrep = clitools.BzgrepWrapper()

  def bzip2(args: String*) = clitools.Bzip2Wrapper(CmdArgs(args.toVector))
  def bzip2 = clitools.Bzip2Wrapper()

  def bzip2recover(args: String*) =
    clitools.Bzip2recoverWrapper(CmdArgs(args.toVector))
  def bzip2recover = clitools.Bzip2recoverWrapper()

  def bzless(args: String*) = clitools.BzlessWrapper(CmdArgs(args.toVector))
  def bzless = clitools.BzlessWrapper()

  def bzme(args: String*) = clitools.BzmeWrapper(CmdArgs(args.toVector))
  def bzme = clitools.BzmeWrapper()

  def cal(args: String*) = clitools.CalWrapper(CmdArgs(args.toVector))
  def cal = clitools.CalWrapper()

  def caller(args: String*) = clitools.CallerWrapper(CmdArgs(args.toVector))
  def caller = clitools.CallerWrapper()

  def cat(args: String*) = clitools.CatWrapper(CmdArgs(args.toVector))
  def cat = clitools.CatWrapper()

  def ccat(args: String*) = clitools.CcatWrapper(CmdArgs(args.toVector))
  def ccat = clitools.CcatWrapper()

  def cd(args: String*) = clitools.CdWrapper(CmdArgs(args.toVector))
  def cd = clitools.CdWrapper()

  def cfdisk(args: String*) = clitools.CfdiskWrapper(CmdArgs(args.toVector))
  def cfdisk = clitools.CfdiskWrapper()

  def chattr(args: String*) = clitools.ChattrWrapper(CmdArgs(args.toVector))
  def chattr = clitools.ChattrWrapper()

  def chcase(args: String*) = clitools.ChcaseWrapper(CmdArgs(args.toVector))
  def chcase = clitools.ChcaseWrapper()

  def chcon(args: String*) = clitools.ChconWrapper(CmdArgs(args.toVector))
  def chcon = clitools.ChconWrapper()

  def chfn(args: String*) = clitools.ChfnWrapper(CmdArgs(args.toVector))
  def chfn = clitools.ChfnWrapper()

  def chgrp(args: String*) = clitools.ChgrpWrapper(CmdArgs(args.toVector))
  def chgrp = clitools.ChgrpWrapper()

  def chmod(args: String*) = clitools.ChmodWrapper(CmdArgs(args.toVector))
  def chmod = clitools.ChmodWrapper()

  def chown(args: String*) = clitools.ChownWrapper(CmdArgs(args.toVector))
  def chown = clitools.ChownWrapper()

  def chroot(args: String*) = clitools.ChrootWrapper(CmdArgs(args.toVector))
  def chroot = clitools.ChrootWrapper()

  def chsh(args: String*) = clitools.ChshWrapper(CmdArgs(args.toVector))
  def chsh = clitools.ChshWrapper()

  def cksum(args: String*) = clitools.CksumWrapper(CmdArgs(args.toVector))
  def cksum = clitools.CksumWrapper()

  def cmp(args: String*) = clitools.CmpWrapper(CmdArgs(args.toVector))
  def cmp = clitools.CmpWrapper()

  def comm(args: String*) = clitools.CommWrapper(CmdArgs(args.toVector))
  def comm = clitools.CommWrapper()

  def command(args: String*) = clitools.CommandWrapper(CmdArgs(args.toVector))
  def command = clitools.CommandWrapper()

  def configure(args: String*) =
    clitools.ConfigureWrapper(CmdArgs(args.toVector))
  def configure = clitools.ConfigureWrapper()

  def convert(args: String*) = clitools.ConvertWrapper(CmdArgs(args.toVector))
  def convert = clitools.ConvertWrapper()

  def cp(args: String*) = clitools.CpWrapper(CmdArgs(args.toVector))
  def cp = clitools.CpWrapper()

  def cron(args: String*) = clitools.CronWrapper(CmdArgs(args.toVector))
  def cron = clitools.CronWrapper()

  def crontab(args: String*) = clitools.CrontabWrapper(CmdArgs(args.toVector))
  def crontab = clitools.CrontabWrapper()

  def csplit(args: String*) = clitools.CsplitWrapper(CmdArgs(args.toVector))
  def csplit = clitools.CsplitWrapper()

  def curl(args: String*) = clitools.CurlWrapper(CmdArgs(args.toVector))
  def curl = clitools.CurlWrapper()

  def cut(args: String*) = clitools.CutWrapper(CmdArgs(args.toVector))
  def cut = clitools.CutWrapper()

  def date(args: String*) = clitools.DateWrapper(CmdArgs(args.toVector))
  def date = clitools.DateWrapper()

  def dd(args: String*) = clitools.DdWrapper(CmdArgs(args.toVector))
  def dd = clitools.DdWrapper()

  def declare(args: String*) = clitools.DeclareWrapper(CmdArgs(args.toVector))
  def declare = clitools.DeclareWrapper()

  def df(args: String*) = clitools.DfWrapper(CmdArgs(args.toVector))
  def df = clitools.DfWrapper()

  def diff(args: String*) = clitools.DiffWrapper(CmdArgs(args.toVector))
  def diff = clitools.DiffWrapper()

  def diff3(args: String*) = clitools.Diff3Wrapper(CmdArgs(args.toVector))
  def diff3 = clitools.Diff3Wrapper()

  def dig(args: String*) = clitools.DigWrapper(CmdArgs(args.toVector))
  def dig = clitools.DigWrapper()

  def dir(args: String*) = clitools.DirWrapper(CmdArgs(args.toVector))
  def dir = clitools.DirWrapper()

  def dircolors(args: String*) =
    clitools.DircolorsWrapper(CmdArgs(args.toVector))
  def dircolors = clitools.DircolorsWrapper()

  def dirname(args: String*) = clitools.DirnameWrapper(CmdArgs(args.toVector))
  def dirname = clitools.DirnameWrapper()

  def display(args: String*) = clitools.DisplayWrapper(CmdArgs(args.toVector))
  def display = clitools.DisplayWrapper()

  def dlocate(args: String*) = clitools.DlocateWrapper(CmdArgs(args.toVector))
  def dlocate = clitools.DlocateWrapper()

  def dmesg(args: String*) = clitools.DmesgWrapper(CmdArgs(args.toVector))
  def dmesg = clitools.DmesgWrapper()

  def dos2unix(args: String*) = clitools.Dos2unixWrapper(CmdArgs(args.toVector))
  def dos2unix = clitools.Dos2unixWrapper()

  def dosfsck(args: String*) = clitools.DosfsckWrapper(CmdArgs(args.toVector))
  def dosfsck = clitools.DosfsckWrapper()

  def dpkg(args: String*) = clitools.DpkgWrapper(CmdArgs(args.toVector))
  def dpkg = clitools.DpkgWrapper()

  def dpkg_deb(args: String*) = clitools.Dpkg_debWrapper(CmdArgs(args.toVector))
  def dpkg_deb = clitools.Dpkg_debWrapper()

  def du(args: String*) = clitools.DuWrapper(CmdArgs(args.toVector))
  def du = clitools.DuWrapper()

  def echo(args: String*) = clitools.EchoWrapper(CmdArgs(args.toVector))
  def echo = clitools.EchoWrapper()

  def egrep(args: String*) = clitools.EgrepWrapper(CmdArgs(args.toVector))
  def egrep = clitools.EgrepWrapper()

  def eject(args: String*) = clitools.EjectWrapper(CmdArgs(args.toVector))
  def eject = clitools.EjectWrapper()

  def enable(args: String*) = clitools.EnableWrapper(CmdArgs(args.toVector))
  def enable = clitools.EnableWrapper()

  def enscript(args: String*) = clitools.EnscriptWrapper(CmdArgs(args.toVector))
  def enscript = clitools.EnscriptWrapper()

  def env(args: String*) = clitools.EnvWrapper(CmdArgs(args.toVector))
  def env = clitools.EnvWrapper()

  def expand(args: String*) = clitools.ExpandWrapper(CmdArgs(args.toVector))
  def expand = clitools.ExpandWrapper()

  def expr(args: String*) = clitools.ExprWrapper(CmdArgs(args.toVector))
  def expr = clitools.ExprWrapper()

  def factor(args: String*) = clitools.FactorWrapper(CmdArgs(args.toVector))
  def factor = clitools.FactorWrapper()

  def fc(args: String*) = clitools.FcWrapper(CmdArgs(args.toVector))
  def fc = clitools.FcWrapper()

  def fdisk(args: String*) = clitools.FdiskWrapper(CmdArgs(args.toVector))
  def fdisk = clitools.FdiskWrapper()

  def ffmpeg(args: String*) = clitools.FfmpegWrapper(CmdArgs(args.toVector))
  def ffmpeg = clitools.FfmpegWrapper()

  def fg(args: String*) = clitools.FgWrapper(CmdArgs(args.toVector))
  def fg = clitools.FgWrapper()

  def fgrep(args: String*) = clitools.FgrepWrapper(CmdArgs(args.toVector))
  def fgrep = clitools.FgrepWrapper()

  def figlet(args: String*) = clitools.FigletWrapper(CmdArgs(args.toVector))
  def figlet = clitools.FigletWrapper()

  def find(args: String*) = clitools.FindWrapper(CmdArgs(args.toVector))
  def find = clitools.FindWrapper()

  def findsmb(args: String*) = clitools.FindsmbWrapper(CmdArgs(args.toVector))
  def findsmb = clitools.FindsmbWrapper()

  def fmt(args: String*) = clitools.FmtWrapper(CmdArgs(args.toVector))
  def fmt = clitools.FmtWrapper()

  def fold(args: String*) = clitools.FoldWrapper(CmdArgs(args.toVector))
  def fold = clitools.FoldWrapper()

  def fortune(args: String*) = clitools.FortuneWrapper(CmdArgs(args.toVector))
  def fortune = clitools.FortuneWrapper()

  def free(args: String*) = clitools.FreeWrapper(CmdArgs(args.toVector))
  def free = clitools.FreeWrapper()

  def fromdos(args: String*) = clitools.FromdosWrapper(CmdArgs(args.toVector))
  def fromdos = clitools.FromdosWrapper()

  def fsck(args: String*) = clitools.FsckWrapper(CmdArgs(args.toVector))
  def fsck = clitools.FsckWrapper()

  def fzf(args: String*) = clitools.FzfWrapper(CmdArgs(args.toVector))
  def fzf = clitools.FzfWrapper()

  def gcc(args: String*) = clitools.GccWrapper(CmdArgs(args.toVector))
  def gcc = clitools.GccWrapper()

  def getopts(args: String*) = clitools.GetoptsWrapper(CmdArgs(args.toVector))
  def getopts = clitools.GetoptsWrapper()

  def git(args: String*) = clitools.GitWrapper(CmdArgs(args.toVector))
  def git = clitools.GitWrapper()

  def grep(args: String*) = clitools.GrepWrapper(CmdArgs(args.toVector))
  def grep = clitools.GrepWrapper()

  def groups(args: String*) = clitools.GroupsWrapper(CmdArgs(args.toVector))
  def groups = clitools.GroupsWrapper()

  def gzip(args: String*) = clitools.GzipWrapper(CmdArgs(args.toVector))
  def gzip = clitools.GzipWrapper()

  def halt(args: String*) = clitools.HaltWrapper(CmdArgs(args.toVector))
  def halt = clitools.HaltWrapper()

  def hash(args: String*) = clitools.HashWrapper(CmdArgs(args.toVector))
  def hash = clitools.HashWrapper()

  def head(args: String*) = clitools.HeadWrapper(CmdArgs(args.toVector))
  def head = clitools.HeadWrapper()

  def help(args: String*) = clitools.HelpWrapper(CmdArgs(args.toVector))
  def help = clitools.HelpWrapper()

  def history(args: String*) = clitools.HistoryWrapper(CmdArgs(args.toVector))
  def history = clitools.HistoryWrapper()

  def host(args: String*) = clitools.HostWrapper(CmdArgs(args.toVector))
  def host = clitools.HostWrapper()

  def hostid(args: String*) = clitools.HostidWrapper(CmdArgs(args.toVector))
  def hostid = clitools.HostidWrapper()

  def hostname(args: String*) = clitools.HostnameWrapper(CmdArgs(args.toVector))
  def hostname = clitools.HostnameWrapper()

  def htop(args: String*) = clitools.HtopWrapper(CmdArgs(args.toVector))
  def htop = clitools.HtopWrapper()

  def id(args: String*) = clitools.IdWrapper(CmdArgs(args.toVector))
  def id = clitools.IdWrapper()

  def identify(args: String*) = clitools.IdentifyWrapper(CmdArgs(args.toVector))
  def identify = clitools.IdentifyWrapper()

  def ifcfg(args: String*) = clitools.IfcfgWrapper(CmdArgs(args.toVector))
  def ifcfg = clitools.IfcfgWrapper()

  def ifconfig(args: String*) = clitools.IfconfigWrapper(CmdArgs(args.toVector))
  def ifconfig = clitools.IfconfigWrapper()

  def ifdown(args: String*) = clitools.IfdownWrapper(CmdArgs(args.toVector))
  def ifdown = clitools.IfdownWrapper()

  def ifup(args: String*) = clitools.IfupWrapper(CmdArgs(args.toVector))
  def ifup = clitools.IfupWrapper()

  def install(args: String*) = clitools.InstallWrapper(CmdArgs(args.toVector))
  def install = clitools.InstallWrapper()

  def ipconfig(args: String*) = clitools.IpconfigWrapper(CmdArgs(args.toVector))
  def ipconfig = clitools.IpconfigWrapper()

  def jobs(args: String*) = clitools.JobsWrapper(CmdArgs(args.toVector))
  def jobs = clitools.JobsWrapper()

  def join(args: String*) = clitools.JoinWrapper(CmdArgs(args.toVector))
  def join = clitools.JoinWrapper()

  def kill(args: String*) = clitools.KillWrapper(CmdArgs(args.toVector))
  def kill = clitools.KillWrapper()

  def killall(args: String*) = clitools.KillallWrapper(CmdArgs(args.toVector))
  def killall = clitools.KillallWrapper()

  def last(args: String*) = clitools.LastWrapper(CmdArgs(args.toVector))
  def last = clitools.LastWrapper()

  def lastlog(args: String*) = clitools.LastlogWrapper(CmdArgs(args.toVector))
  def lastlog = clitools.LastlogWrapper()

  def less(args: String*) = clitools.LessWrapper(CmdArgs(args.toVector))
  def less = clitools.LessWrapper()

  def let(args: String*) = clitools.LetWrapper(CmdArgs(args.toVector))
  def let = clitools.LetWrapper()

  def link(args: String*) = clitools.LinkWrapper(CmdArgs(args.toVector))
  def link = clitools.LinkWrapper()

  def ln(args: String*) = clitools.LnWrapper(CmdArgs(args.toVector))
  def ln = clitools.LnWrapper()

  def local(args: String*) = clitools.LocalWrapper(CmdArgs(args.toVector))
  def local = clitools.LocalWrapper()

  def locate(args: String*) = clitools.LocateWrapper(CmdArgs(args.toVector))
  def locate = clitools.LocateWrapper()

  def logname(args: String*) = clitools.LognameWrapper(CmdArgs(args.toVector))
  def logname = clitools.LognameWrapper()

  def logout(args: String*) = clitools.LogoutWrapper(CmdArgs(args.toVector))
  def logout = clitools.LogoutWrapper()

  def look(args: String*) = clitools.LookWrapper(CmdArgs(args.toVector))
  def look = clitools.LookWrapper()

  def ls(args: String*) = clitools.LsWrapper(CmdArgs(args.toVector))
  def ls = clitools.LsWrapper()

  def lsattr(args: String*) = clitools.LsattrWrapper(CmdArgs(args.toVector))
  def lsattr = clitools.LsattrWrapper()

  def lsof(args: String*) = clitools.LsofWrapper(CmdArgs(args.toVector))
  def lsof = clitools.LsofWrapper()

  def ltrace(args: String*) = clitools.LtraceWrapper(CmdArgs(args.toVector))
  def ltrace = clitools.LtraceWrapper()

  def make(args: String*) = clitools.MakeWrapper(CmdArgs(args.toVector))
  def make = clitools.MakeWrapper()

  def mapfile(args: String*) = clitools.MapfileWrapper(CmdArgs(args.toVector))
  def mapfile = clitools.MapfileWrapper()

  def mbadblocks(args: String*) =
    clitools.MbadblocksWrapper(CmdArgs(args.toVector))
  def mbadblocks = clitools.MbadblocksWrapper()

  def mcopy(args: String*) = clitools.McopyWrapper(CmdArgs(args.toVector))
  def mcopy = clitools.McopyWrapper()

  def md5sum(args: String*) = clitools.Md5sumWrapper(CmdArgs(args.toVector))
  def md5sum = clitools.Md5sumWrapper()

  def mformat(args: String*) = clitools.MformatWrapper(CmdArgs(args.toVector))
  def mformat = clitools.MformatWrapper()

  def mkdir(args: String*) = clitools.MkdirWrapper(CmdArgs(args.toVector))
  def mkdir = clitools.MkdirWrapper()

  def mkfifo(args: String*) = clitools.MkfifoWrapper(CmdArgs(args.toVector))
  def mkfifo = clitools.MkfifoWrapper()

  def mknod(args: String*) = clitools.MknodWrapper(CmdArgs(args.toVector))
  def mknod = clitools.MknodWrapper()

  def mkpasswd(args: String*) = clitools.MkpasswdWrapper(CmdArgs(args.toVector))
  def mkpasswd = clitools.MkpasswdWrapper()

  def mktemp(args: String*) = clitools.MktempWrapper(CmdArgs(args.toVector))
  def mktemp = clitools.MktempWrapper()

  def mmount(args: String*) = clitools.MmountWrapper(CmdArgs(args.toVector))
  def mmount = clitools.MmountWrapper()

  def mmv(args: String*) = clitools.MmvWrapper(CmdArgs(args.toVector))
  def mmv = clitools.MmvWrapper()

  def mogrify(args: String*) = clitools.MogrifyWrapper(CmdArgs(args.toVector))
  def mogrify = clitools.MogrifyWrapper()

  def more(args: String*) = clitools.MoreWrapper(CmdArgs(args.toVector))
  def more = clitools.MoreWrapper()

  def moun(args: String*) = clitools.MounWrapper(CmdArgs(args.toVector))
  def moun = clitools.MounWrapper()

  def mv(args: String*) = clitools.MvWrapper(CmdArgs(args.toVector))
  def mv = clitools.MvWrapper()

  def netstat(args: String*) = clitools.NetstatWrapper(CmdArgs(args.toVector))
  def netstat = clitools.NetstatWrapper()

  def newgrp(args: String*) = clitools.NewgrpWrapper(CmdArgs(args.toVector))
  def newgrp = clitools.NewgrpWrapper()

  def nice(args: String*) = clitools.NiceWrapper(CmdArgs(args.toVector))
  def nice = clitools.NiceWrapper()

  def nix(args: String*) = clitools.NixWrapper(CmdArgs(args.toVector))
  def nix = clitools.NixWrapper()

  def nix_build(args: String*) =
    clitools.Nix_buildWrapper(CmdArgs(args.toVector))
  def nix_build = clitools.Nix_buildWrapper()

  def nix_env(args: String*) = clitools.Nix_envWrapper(CmdArgs(args.toVector))
  def nix_env = clitools.Nix_envWrapper()

  def nix_shell(args: String*) =
    clitools.Nix_shellWrapper(CmdArgs(args.toVector))
  def nix_shell = clitools.Nix_shellWrapper()

  def nix_store(args: String*) =
    clitools.Nix_storeWrapper(CmdArgs(args.toVector))
  def nix_store = clitools.Nix_storeWrapper()

  def nl(args: String*) = clitools.NlWrapper(CmdArgs(args.toVector))
  def nl = clitools.NlWrapper()

  def nmap(args: String*) = clitools.NmapWrapper(CmdArgs(args.toVector))
  def nmap = clitools.NmapWrapper()

  def nohup(args: String*) = clitools.NohupWrapper(CmdArgs(args.toVector))
  def nohup = clitools.NohupWrapper()

  def nproc(args: String*) = clitools.NprocWrapper(CmdArgs(args.toVector))
  def nproc = clitools.NprocWrapper()

  def numfmt(args: String*) = clitools.NumfmtWrapper(CmdArgs(args.toVector))
  def numfmt = clitools.NumfmtWrapper()

  def numgrep(args: String*) = clitools.NumgrepWrapper(CmdArgs(args.toVector))
  def numgrep = clitools.NumgrepWrapper()

  def od(args: String*) = clitools.OdWrapper(CmdArgs(args.toVector))
  def od = clitools.OdWrapper()

  def passwd(args: String*) = clitools.PasswdWrapper(CmdArgs(args.toVector))
  def passwd = clitools.PasswdWrapper()

  def paste(args: String*) = clitools.PasteWrapper(CmdArgs(args.toVector))
  def paste = clitools.PasteWrapper()

  def pathchk(args: String*) = clitools.PathchkWrapper(CmdArgs(args.toVector))
  def pathchk = clitools.PathchkWrapper()

  def perl(args: String*) = clitools.PerlWrapper(CmdArgs(args.toVector))
  def perl = clitools.PerlWrapper()

  def pgrep(args: String*) = clitools.PgrepWrapper(CmdArgs(args.toVector))
  def pgrep = clitools.PgrepWrapper()

  def ping(args: String*) = clitools.PingWrapper(CmdArgs(args.toVector))
  def ping = clitools.PingWrapper()

  def pinky(args: String*) = clitools.PinkyWrapper(CmdArgs(args.toVector))
  def pinky = clitools.PinkyWrapper()

  def pkill(args: String*) = clitools.PkillWrapper(CmdArgs(args.toVector))
  def pkill = clitools.PkillWrapper()

  def popd(args: String*) = clitools.PopdWrapper(CmdArgs(args.toVector))
  def popd = clitools.PopdWrapper()

  def pr(args: String*) = clitools.PrWrapper(CmdArgs(args.toVector))
  def pr = clitools.PrWrapper()

  def printenv(args: String*) = clitools.PrintenvWrapper(CmdArgs(args.toVector))
  def printenv = clitools.PrintenvWrapper()

  def printf(args: String*) = clitools.PrintfWrapper(CmdArgs(args.toVector))
  def printf = clitools.PrintfWrapper()

  def ps(args: String*) = clitools.PsWrapper(CmdArgs(args.toVector))
  def ps = clitools.PsWrapper()

  def pstree(args: String*) = clitools.PstreeWrapper(CmdArgs(args.toVector))
  def pstree = clitools.PstreeWrapper()

  def ptx(args: String*) = clitools.PtxWrapper(CmdArgs(args.toVector))
  def ptx = clitools.PtxWrapper()

  def pushd(args: String*) = clitools.PushdWrapper(CmdArgs(args.toVector))
  def pushd = clitools.PushdWrapper()

  def pwd(args: String*) = clitools.PwdWrapper(CmdArgs(args.toVector))
  def pwd = clitools.PwdWrapper()

  def py(args: String*) = clitools.PyWrapper(CmdArgs(args.toVector))
  def py = clitools.PyWrapper()

  def read(args: String*) = clitools.ReadWrapper(CmdArgs(args.toVector))
  def read = clitools.ReadWrapper()

  def readarray(args: String*) =
    clitools.ReadarrayWrapper(CmdArgs(args.toVector))
  def readarray = clitools.ReadarrayWrapper()

  def readlink(args: String*) = clitools.ReadlinkWrapper(CmdArgs(args.toVector))
  def readlink = clitools.ReadlinkWrapper()

  def realpath(args: String*) = clitools.RealpathWrapper(CmdArgs(args.toVector))
  def realpath = clitools.RealpathWrapper()

  def reboot(args: String*) = clitools.RebootWrapper(CmdArgs(args.toVector))
  def reboot = clitools.RebootWrapper()

  def recode(args: String*) = clitools.RecodeWrapper(CmdArgs(args.toVector))
  def recode = clitools.RecodeWrapper()

  def rel(args: String*) = clitools.RelWrapper(CmdArgs(args.toVector))
  def rel = clitools.RelWrapper()

  def rename(args: String*) = clitools.RenameWrapper(CmdArgs(args.toVector))
  def rename = clitools.RenameWrapper()

  def renice(args: String*) = clitools.ReniceWrapper(CmdArgs(args.toVector))
  def renice = clitools.ReniceWrapper()

  def rgrep(args: String*) = clitools.RgrepWrapper(CmdArgs(args.toVector))
  def rgrep = clitools.RgrepWrapper()

  def rm(args: String*) = clitools.RmWrapper(CmdArgs(args.toVector))
  def rm = clitools.RmWrapper()

  def rmdir(args: String*) = clitools.RmdirWrapper(CmdArgs(args.toVector))
  def rmdir = clitools.RmdirWrapper()

  def root(args: String*) = clitools.RootWrapper(CmdArgs(args.toVector))
  def root = clitools.RootWrapper()

  def route(args: String*) = clitools.RouteWrapper(CmdArgs(args.toVector))
  def route = clitools.RouteWrapper()

  def rpm(args: String*) = clitools.RpmWrapper(CmdArgs(args.toVector))
  def rpm = clitools.RpmWrapper()

  def rsync(args: String*) = clitools.RsyncWrapper(CmdArgs(args.toVector))
  def rsync = clitools.RsyncWrapper()

  def runcon(args: String*) = clitools.RunconWrapper(CmdArgs(args.toVector))
  def runcon = clitools.RunconWrapper()

  def samba_tool(args: String*) =
    clitools.Samba_toolWrapper(CmdArgs(args.toVector))
  def samba_tool = clitools.Samba_toolWrapper()

  def sc(args: String*) = clitools.ScWrapper(CmdArgs(args.toVector))
  def sc = clitools.ScWrapper()

  def scp(args: String*) = clitools.ScpWrapper(CmdArgs(args.toVector))
  def scp = clitools.ScpWrapper()

  def sdiff(args: String*) = clitools.SdiffWrapper(CmdArgs(args.toVector))
  def sdiff = clitools.SdiffWrapper()

  def sed(args: String*) = clitools.SedWrapper(CmdArgs(args.toVector))
  def sed = clitools.SedWrapper()

  def seq(args: String*) = clitools.SeqWrapper(CmdArgs(args.toVector))
  def seq = clitools.SeqWrapper()

  def service(args: String*) = clitools.ServiceWrapper(CmdArgs(args.toVector))
  def service = clitools.ServiceWrapper()

  def setfacl(args: String*) = clitools.SetfaclWrapper(CmdArgs(args.toVector))
  def setfacl = clitools.SetfaclWrapper()

  def sftp(args: String*) = clitools.SftpWrapper(CmdArgs(args.toVector))
  def sftp = clitools.SftpWrapper()

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

  def shutdown(args: String*) = clitools.ShutdownWrapper(CmdArgs(args.toVector))
  def shutdown = clitools.ShutdownWrapper()

  def skill(args: String*) = clitools.SkillWrapper(CmdArgs(args.toVector))
  def skill = clitools.SkillWrapper()

  def sleep(args: String*) = clitools.SleepWrapper(CmdArgs(args.toVector))
  def sleep = clitools.SleepWrapper()

  def slocate(args: String*) = clitools.SlocateWrapper(CmdArgs(args.toVector))
  def slocate = clitools.SlocateWrapper()

  def smbclient(args: String*) =
    clitools.SmbclientWrapper(CmdArgs(args.toVector))
  def smbclient = clitools.SmbclientWrapper()

  def smbcontrol(args: String*) =
    clitools.SmbcontrolWrapper(CmdArgs(args.toVector))
  def smbcontrol = clitools.SmbcontrolWrapper()

  def smbd(args: String*) = clitools.SmbdWrapper(CmdArgs(args.toVector))
  def smbd = clitools.SmbdWrapper()

  def smbmount(args: String*) = clitools.SmbmountWrapper(CmdArgs(args.toVector))
  def smbmount = clitools.SmbmountWrapper()

  def snice(args: String*) = clitools.SniceWrapper(CmdArgs(args.toVector))
  def snice = clitools.SniceWrapper()

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

  def strace(args: String*) = clitools.StraceWrapper(CmdArgs(args.toVector))
  def strace = clitools.StraceWrapper()

  def stty(args: String*) = clitools.SttyWrapper(CmdArgs(args.toVector))
  def stty = clitools.SttyWrapper()

  def style(args: String*) = clitools.StyleWrapper(CmdArgs(args.toVector))
  def style = clitools.StyleWrapper()

  def su(args: String*) = clitools.SuWrapper(CmdArgs(args.toVector))
  def su = clitools.SuWrapper()

  def suid(args: String*) = clitools.SuidWrapper(CmdArgs(args.toVector))
  def suid = clitools.SuidWrapper()

  def sum(args: String*) = clitools.SumWrapper(CmdArgs(args.toVector))
  def sum = clitools.SumWrapper()

  def sync(args: String*) = clitools.SyncWrapper(CmdArgs(args.toVector))
  def sync = clitools.SyncWrapper()

  def tac(args: String*) = clitools.TacWrapper(CmdArgs(args.toVector))
  def tac = clitools.TacWrapper()

  def tail(args: String*) = clitools.TailWrapper(CmdArgs(args.toVector))
  def tail = clitools.TailWrapper()

  def tar(args: String*) = clitools.TarWrapper(CmdArgs(args.toVector))
  def tar = clitools.TarWrapper()

  def tcpdump(args: String*) = clitools.TcpdumpWrapper(CmdArgs(args.toVector))
  def tcpdump = clitools.TcpdumpWrapper()

  def tee(args: String*) = clitools.TeeWrapper(CmdArgs(args.toVector))
  def tee = clitools.TeeWrapper()

  def test(args: String*) = clitools.TestWrapper(CmdArgs(args.toVector))
  def test = clitools.TestWrapper()

  def time(args: String*) = clitools.TimeWrapper(CmdArgs(args.toVector))
  def time = clitools.TimeWrapper()

  def timeout(args: String*) = clitools.TimeoutWrapper(CmdArgs(args.toVector))
  def timeout = clitools.TimeoutWrapper()

  def top(args: String*) = clitools.TopWrapper(CmdArgs(args.toVector))
  def top = clitools.TopWrapper()

  def touch(args: String*) = clitools.TouchWrapper(CmdArgs(args.toVector))
  def touch = clitools.TouchWrapper()

  def tr(args: String*) = clitools.TrWrapper(CmdArgs(args.toVector))
  def tr = clitools.TrWrapper()

  def tracepath(args: String*) =
    clitools.TracepathWrapper(CmdArgs(args.toVector))
  def tracepath = clitools.TracepathWrapper()

  def traceroute(args: String*) =
    clitools.TracerouteWrapper(CmdArgs(args.toVector))
  def traceroute = clitools.TracerouteWrapper()

  def truncate(args: String*) = clitools.TruncateWrapper(CmdArgs(args.toVector))
  def truncate = clitools.TruncateWrapper()

  def tsort(args: String*) = clitools.TsortWrapper(CmdArgs(args.toVector))
  def tsort = clitools.TsortWrapper()

  def tty(args: String*) = clitools.TtyWrapper(CmdArgs(args.toVector))
  def tty = clitools.TtyWrapper()

  def typeset(args: String*) = clitools.TypesetWrapper(CmdArgs(args.toVector))
  def typeset = clitools.TypesetWrapper()

  def ulimit(args: String*) = clitools.UlimitWrapper(CmdArgs(args.toVector))
  def ulimit = clitools.UlimitWrapper()

  def umask(args: String*) = clitools.UmaskWrapper(CmdArgs(args.toVector))
  def umask = clitools.UmaskWrapper()

  def umount(args: String*) = clitools.UmountWrapper(CmdArgs(args.toVector))
  def umount = clitools.UmountWrapper()

  def unalias(args: String*) = clitools.UnaliasWrapper(CmdArgs(args.toVector))
  def unalias = clitools.UnaliasWrapper()

  def uname(args: String*) = clitools.UnameWrapper(CmdArgs(args.toVector))
  def uname = clitools.UnameWrapper()

  def unexpand(args: String*) = clitools.UnexpandWrapper(CmdArgs(args.toVector))
  def unexpand = clitools.UnexpandWrapper()

  def uniq(args: String*) = clitools.UniqWrapper(CmdArgs(args.toVector))
  def uniq = clitools.UniqWrapper()

  def units(args: String*) = clitools.UnitsWrapper(CmdArgs(args.toVector))
  def units = clitools.UnitsWrapper()

  def unix2dos(args: String*) = clitools.Unix2dosWrapper(CmdArgs(args.toVector))
  def unix2dos = clitools.Unix2dosWrapper()

  def unlink(args: String*) = clitools.UnlinkWrapper(CmdArgs(args.toVector))
  def unlink = clitools.UnlinkWrapper()

  def uptime(args: String*) = clitools.UptimeWrapper(CmdArgs(args.toVector))
  def uptime = clitools.UptimeWrapper()

  def users(args: String*) = clitools.UsersWrapper(CmdArgs(args.toVector))
  def users = clitools.UsersWrapper()

  def vdir(args: String*) = clitools.VdirWrapper(CmdArgs(args.toVector))
  def vdir = clitools.VdirWrapper()

  def vlc(args: String*) = clitools.VlcWrapper(CmdArgs(args.toVector))
  def vlc = clitools.VlcWrapper()

  def w(args: String*) = clitools.WWrapper(CmdArgs(args.toVector))
  def w = clitools.WWrapper()

  def wc(args: String*) = clitools.WcWrapper(CmdArgs(args.toVector))
  def wc = clitools.WcWrapper()

  def wget(args: String*) = clitools.WgetWrapper(CmdArgs(args.toVector))
  def wget = clitools.WgetWrapper()

  def whereis(args: String*) = clitools.WhereisWrapper(CmdArgs(args.toVector))
  def whereis = clitools.WhereisWrapper()

  def whic(args: String*) = clitools.WhicWrapper(CmdArgs(args.toVector))
  def whic = clitools.WhicWrapper()

  def who(args: String*) = clitools.WhoWrapper(CmdArgs(args.toVector))
  def who = clitools.WhoWrapper()

  def whoami(args: String*) = clitools.WhoamiWrapper(CmdArgs(args.toVector))
  def whoami = clitools.WhoamiWrapper()

  def whois(args: String*) = clitools.WhoisWrapper(CmdArgs(args.toVector))
  def whois = clitools.WhoisWrapper()

  def xargs(args: String*) = clitools.XargsWrapper(CmdArgs(args.toVector))
  def xargs = clitools.XargsWrapper()

  def yes(args: String*) = clitools.YesWrapper(CmdArgs(args.toVector))
  def yes = clitools.YesWrapper()

  def zcat(args: String*) = clitools.ZcatWrapper(CmdArgs(args.toVector))
  def zcat = clitools.ZcatWrapper()

  def zcmp(args: String*) = clitools.ZcmpWrapper(CmdArgs(args.toVector))
  def zcmp = clitools.ZcmpWrapper()

  def zdiff(args: String*) = clitools.ZdiffWrapper(CmdArgs(args.toVector))
  def zdiff = clitools.ZdiffWrapper()

  def zgrep(args: String*) = clitools.ZgrepWrapper(CmdArgs(args.toVector))
  def zgrep = clitools.ZgrepWrapper()

  def zipgrep(args: String*) = clitools.ZipgrepWrapper(CmdArgs(args.toVector))
  def zipgrep = clitools.ZipgrepWrapper()

  def zipinfo(args: String*) = clitools.ZipinfoWrapper(CmdArgs(args.toVector))
  def zipinfo = clitools.ZipinfoWrapper()

  def zless(args: String*) = clitools.ZlessWrapper(CmdArgs(args.toVector))
  def zless = clitools.ZlessWrapper()

  def zmore(args: String*) = clitools.ZmoreWrapper(CmdArgs(args.toVector))
  def zmore = clitools.ZmoreWrapper()

  def zsh(args: String*) = clitools.ZshWrapper(CmdArgs(args.toVector))
  def zsh = clitools.ZshWrapper()

  implicit class CmdSyntax(private val s: StringContext) extends AnyVal {

    def $(args: Any*) =
      ParameterExpander(CmdArgCtx(args.toVector, s))

    def __#(args: Any*) =
      CommentLine("# ", CmdArgCtx(args.toVector, s))

    def __##(args: Any*) =
      CommentLine("## ", CmdArgCtx(args.toVector, s))

    def txt(args: Any*) =
      TextVariable(CmdArgCtx(args.toVector, s))

    def array(args: Any*) =
      ArrayVariable(CmdArgCtx(args.toVector, s))

    def m(args: Any*) =
      ArithmeticExpression(CmdArgCtx(args.toVector, s))

    def filePath(args: Any*): FilePath =
      FileConversions.convertToFilePath(s.s(args: _*))

    def relFile(args: Any*): RelPath =
      FileConversions.convertToRelFilePath(s.s(args: _*))

    def fileName(args: Any*): FileName =
      FileConversions.convertToFileName(s.s(args: _*))

    def dirPath(args: Any*): FolderPath =
      FileConversions.convertToFolderPath(s.s(args: _*))

    def /(args: Any*): FolderPath =
      FileConversions.convertToFolderPath("/" + s.s(args: _*))

    def `./`(args: Any*): RelFolderPath =
      FileConversions.convertToRelFolderPath(s.s("./" + args: _*))

    def `../`(args: Any*): RelFolderPath =
      FileConversions.convertToRelFolderPath(s.s("../" + args: _*))

    def Await(args: Any*) =
      SimpleCommand("Await", CmdArgCtx(args.toVector, s))

    def R(args: Any*) =
      SimpleCommand("R", CmdArgCtx(args.toVector, s))

    def alias(args: Any*) =
      SimpleCommand("alias", CmdArgCtx(args.toVector, s))

    def antiword(args: Any*) =
      SimpleCommand("antiword", CmdArgCtx(args.toVector, s))

    def apt(args: Any*) =
      SimpleCommand("apt", CmdArgCtx(args.toVector, s))

    def apt_cache(args: Any*) =
      SimpleCommand("apt_cache", CmdArgCtx(args.toVector, s))

    def apt_file(args: Any*) =
      SimpleCommand("apt_file", CmdArgCtx(args.toVector, s))

    def apt_get(args: Any*) =
      SimpleCommand("apt_get", CmdArgCtx(args.toVector, s))

    def arp(args: Any*) =
      SimpleCommand("arp", CmdArgCtx(args.toVector, s))

    def at(args: Any*) =
      SimpleCommand("at", CmdArgCtx(args.toVector, s))

    def atq(args: Any*) =
      SimpleCommand("atq", CmdArgCtx(args.toVector, s))

    def atrm(args: Any*) =
      SimpleCommand("atrm", CmdArgCtx(args.toVector, s))

    def auditctl(args: Any*) =
      SimpleCommand("auditctl", CmdArgCtx(args.toVector, s))

    def ausearch(args: Any*) =
      SimpleCommand("ausearch", CmdArgCtx(args.toVector, s))

    def awk(args: Any*) =
      SimpleCommand("awk", CmdArgCtx(args.toVector, s))

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

    def bg(args: Any*) =
      SimpleCommand("bg", CmdArgCtx(args.toVector, s))

    def bgrep(args: Any*) =
      SimpleCommand("bgrep", CmdArgCtx(args.toVector, s))

    def bind(args: Any*) =
      SimpleCommand("bind", CmdArgCtx(args.toVector, s))

    def builtin(args: Any*) =
      SimpleCommand("builtin", CmdArgCtx(args.toVector, s))

    def bzcat(args: Any*) =
      SimpleCommand("bzcat", CmdArgCtx(args.toVector, s))

    def bzgrep(args: Any*) =
      SimpleCommand("bzgrep", CmdArgCtx(args.toVector, s))

    def bzip2(args: Any*) =
      SimpleCommand("bzip2", CmdArgCtx(args.toVector, s))

    def bzip2recover(args: Any*) =
      SimpleCommand("bzip2recover", CmdArgCtx(args.toVector, s))

    def bzless(args: Any*) =
      SimpleCommand("bzless", CmdArgCtx(args.toVector, s))

    def bzme(args: Any*) =
      SimpleCommand("bzme", CmdArgCtx(args.toVector, s))

    def cal(args: Any*) =
      SimpleCommand("cal", CmdArgCtx(args.toVector, s))

    def caller(args: Any*) =
      SimpleCommand("caller", CmdArgCtx(args.toVector, s))

    def cat(args: Any*) =
      SimpleCommand("cat", CmdArgCtx(args.toVector, s))

    def ccat(args: Any*) =
      SimpleCommand("ccat", CmdArgCtx(args.toVector, s))

    def cd(args: Any*) =
      SimpleCommand("cd", CmdArgCtx(args.toVector, s))

    def cfdisk(args: Any*) =
      SimpleCommand("cfdisk", CmdArgCtx(args.toVector, s))

    def chattr(args: Any*) =
      SimpleCommand("chattr", CmdArgCtx(args.toVector, s))

    def chcase(args: Any*) =
      SimpleCommand("chcase", CmdArgCtx(args.toVector, s))

    def chcon(args: Any*) =
      SimpleCommand("chcon", CmdArgCtx(args.toVector, s))

    def chfn(args: Any*) =
      SimpleCommand("chfn", CmdArgCtx(args.toVector, s))

    def chgrp(args: Any*) =
      SimpleCommand("chgrp", CmdArgCtx(args.toVector, s))

    def chmod(args: Any*) =
      SimpleCommand("chmod", CmdArgCtx(args.toVector, s))

    def chown(args: Any*) =
      SimpleCommand("chown", CmdArgCtx(args.toVector, s))

    def chroot(args: Any*) =
      SimpleCommand("chroot", CmdArgCtx(args.toVector, s))

    def chsh(args: Any*) =
      SimpleCommand("chsh", CmdArgCtx(args.toVector, s))

    def cksum(args: Any*) =
      SimpleCommand("cksum", CmdArgCtx(args.toVector, s))

    def cmp(args: Any*) =
      SimpleCommand("cmp", CmdArgCtx(args.toVector, s))

    def comm(args: Any*) =
      SimpleCommand("comm", CmdArgCtx(args.toVector, s))

    def command(args: Any*) =
      SimpleCommand("command", CmdArgCtx(args.toVector, s))

    def configure(args: Any*) =
      SimpleCommand("configure", CmdArgCtx(args.toVector, s))

    def convert(args: Any*) =
      SimpleCommand("convert", CmdArgCtx(args.toVector, s))

    def cp(args: Any*) =
      SimpleCommand("cp", CmdArgCtx(args.toVector, s))

    def cron(args: Any*) =
      SimpleCommand("cron", CmdArgCtx(args.toVector, s))

    def crontab(args: Any*) =
      SimpleCommand("crontab", CmdArgCtx(args.toVector, s))

    def csplit(args: Any*) =
      SimpleCommand("csplit", CmdArgCtx(args.toVector, s))

    def curl(args: Any*) =
      SimpleCommand("curl", CmdArgCtx(args.toVector, s))

    def cut(args: Any*) =
      SimpleCommand("cut", CmdArgCtx(args.toVector, s))

    def date(args: Any*) =
      SimpleCommand("date", CmdArgCtx(args.toVector, s))

    def dd(args: Any*) =
      SimpleCommand("dd", CmdArgCtx(args.toVector, s))

    def declare(args: Any*) =
      SimpleCommand("declare", CmdArgCtx(args.toVector, s))

    def df(args: Any*) =
      SimpleCommand("df", CmdArgCtx(args.toVector, s))

    def diff(args: Any*) =
      SimpleCommand("diff", CmdArgCtx(args.toVector, s))

    def diff3(args: Any*) =
      SimpleCommand("diff3", CmdArgCtx(args.toVector, s))

    def dig(args: Any*) =
      SimpleCommand("dig", CmdArgCtx(args.toVector, s))

    def dir(args: Any*) =
      SimpleCommand("dir", CmdArgCtx(args.toVector, s))

    def dircolors(args: Any*) =
      SimpleCommand("dircolors", CmdArgCtx(args.toVector, s))

    def dirname(args: Any*) =
      SimpleCommand("dirname", CmdArgCtx(args.toVector, s))

    def display(args: Any*) =
      SimpleCommand("display", CmdArgCtx(args.toVector, s))

    def dlocate(args: Any*) =
      SimpleCommand("dlocate", CmdArgCtx(args.toVector, s))

    def dmesg(args: Any*) =
      SimpleCommand("dmesg", CmdArgCtx(args.toVector, s))

    def dos2unix(args: Any*) =
      SimpleCommand("dos2unix", CmdArgCtx(args.toVector, s))

    def dosfsck(args: Any*) =
      SimpleCommand("dosfsck", CmdArgCtx(args.toVector, s))

    def dpkg(args: Any*) =
      SimpleCommand("dpkg", CmdArgCtx(args.toVector, s))

    def dpkg_deb(args: Any*) =
      SimpleCommand("dpkg_deb", CmdArgCtx(args.toVector, s))

    def du(args: Any*) =
      SimpleCommand("du", CmdArgCtx(args.toVector, s))

    def echo(args: Any*) =
      SimpleCommand("echo", CmdArgCtx(args.toVector, s))

    def egrep(args: Any*) =
      SimpleCommand("egrep", CmdArgCtx(args.toVector, s))

    def eject(args: Any*) =
      SimpleCommand("eject", CmdArgCtx(args.toVector, s))

    def enable(args: Any*) =
      SimpleCommand("enable", CmdArgCtx(args.toVector, s))

    def enscript(args: Any*) =
      SimpleCommand("enscript", CmdArgCtx(args.toVector, s))

    def env(args: Any*) =
      SimpleCommand("env", CmdArgCtx(args.toVector, s))

    def expand(args: Any*) =
      SimpleCommand("expand", CmdArgCtx(args.toVector, s))

    def expr(args: Any*) =
      SimpleCommand("expr", CmdArgCtx(args.toVector, s))

    def factor(args: Any*) =
      SimpleCommand("factor", CmdArgCtx(args.toVector, s))

    def fc(args: Any*) =
      SimpleCommand("fc", CmdArgCtx(args.toVector, s))

    def fdisk(args: Any*) =
      SimpleCommand("fdisk", CmdArgCtx(args.toVector, s))

    def ffmpeg(args: Any*) =
      SimpleCommand("ffmpeg", CmdArgCtx(args.toVector, s))

    def fg(args: Any*) =
      SimpleCommand("fg", CmdArgCtx(args.toVector, s))

    def fgrep(args: Any*) =
      SimpleCommand("fgrep", CmdArgCtx(args.toVector, s))

    def figlet(args: Any*) =
      SimpleCommand("figlet", CmdArgCtx(args.toVector, s))

    def find(args: Any*) =
      SimpleCommand("find", CmdArgCtx(args.toVector, s))

    def findsmb(args: Any*) =
      SimpleCommand("findsmb", CmdArgCtx(args.toVector, s))

    def fmt(args: Any*) =
      SimpleCommand("fmt", CmdArgCtx(args.toVector, s))

    def fold(args: Any*) =
      SimpleCommand("fold", CmdArgCtx(args.toVector, s))

    def fortune(args: Any*) =
      SimpleCommand("fortune", CmdArgCtx(args.toVector, s))

    def free(args: Any*) =
      SimpleCommand("free", CmdArgCtx(args.toVector, s))

    def fromdos(args: Any*) =
      SimpleCommand("fromdos", CmdArgCtx(args.toVector, s))

    def fsck(args: Any*) =
      SimpleCommand("fsck", CmdArgCtx(args.toVector, s))

    def fzf(args: Any*) =
      SimpleCommand("fzf", CmdArgCtx(args.toVector, s))

    def gcc(args: Any*) =
      SimpleCommand("gcc", CmdArgCtx(args.toVector, s))

    def getopts(args: Any*) =
      SimpleCommand("getopts", CmdArgCtx(args.toVector, s))

    def git(args: Any*) =
      SimpleCommand("git", CmdArgCtx(args.toVector, s))

    def grep(args: Any*) =
      SimpleCommand("grep", CmdArgCtx(args.toVector, s))

    def groups(args: Any*) =
      SimpleCommand("groups", CmdArgCtx(args.toVector, s))

    def gzip(args: Any*) =
      SimpleCommand("gzip", CmdArgCtx(args.toVector, s))

    def halt(args: Any*) =
      SimpleCommand("halt", CmdArgCtx(args.toVector, s))

    def hash(args: Any*) =
      SimpleCommand("hash", CmdArgCtx(args.toVector, s))

    def head(args: Any*) =
      SimpleCommand("head", CmdArgCtx(args.toVector, s))

    def help(args: Any*) =
      SimpleCommand("help", CmdArgCtx(args.toVector, s))

    def history(args: Any*) =
      SimpleCommand("history", CmdArgCtx(args.toVector, s))

    def host(args: Any*) =
      SimpleCommand("host", CmdArgCtx(args.toVector, s))

    def hostid(args: Any*) =
      SimpleCommand("hostid", CmdArgCtx(args.toVector, s))

    def hostname(args: Any*) =
      SimpleCommand("hostname", CmdArgCtx(args.toVector, s))

    def htop(args: Any*) =
      SimpleCommand("htop", CmdArgCtx(args.toVector, s))

    def id(args: Any*) =
      SimpleCommand("id", CmdArgCtx(args.toVector, s))

    def identify(args: Any*) =
      SimpleCommand("identify", CmdArgCtx(args.toVector, s))

    def ifcfg(args: Any*) =
      SimpleCommand("ifcfg", CmdArgCtx(args.toVector, s))

    def ifconfig(args: Any*) =
      SimpleCommand("ifconfig", CmdArgCtx(args.toVector, s))

    def ifdown(args: Any*) =
      SimpleCommand("ifdown", CmdArgCtx(args.toVector, s))

    def ifup(args: Any*) =
      SimpleCommand("ifup", CmdArgCtx(args.toVector, s))

    def install(args: Any*) =
      SimpleCommand("install", CmdArgCtx(args.toVector, s))

    def ipconfig(args: Any*) =
      SimpleCommand("ipconfig", CmdArgCtx(args.toVector, s))

    def jobs(args: Any*) =
      SimpleCommand("jobs", CmdArgCtx(args.toVector, s))

    def join(args: Any*) =
      SimpleCommand("join", CmdArgCtx(args.toVector, s))

    def kill(args: Any*) =
      SimpleCommand("kill", CmdArgCtx(args.toVector, s))

    def killall(args: Any*) =
      SimpleCommand("killall", CmdArgCtx(args.toVector, s))

    def last(args: Any*) =
      SimpleCommand("last", CmdArgCtx(args.toVector, s))

    def lastlog(args: Any*) =
      SimpleCommand("lastlog", CmdArgCtx(args.toVector, s))

    def less(args: Any*) =
      SimpleCommand("less", CmdArgCtx(args.toVector, s))

    def let(args: Any*) =
      SimpleCommand("let", CmdArgCtx(args.toVector, s))

    def link(args: Any*) =
      SimpleCommand("link", CmdArgCtx(args.toVector, s))

    def ln(args: Any*) =
      SimpleCommand("ln", CmdArgCtx(args.toVector, s))

    def local(args: Any*) =
      SimpleCommand("local", CmdArgCtx(args.toVector, s))

    def locate(args: Any*) =
      SimpleCommand("locate", CmdArgCtx(args.toVector, s))

    def logname(args: Any*) =
      SimpleCommand("logname", CmdArgCtx(args.toVector, s))

    def logout(args: Any*) =
      SimpleCommand("logout", CmdArgCtx(args.toVector, s))

    def look(args: Any*) =
      SimpleCommand("look", CmdArgCtx(args.toVector, s))

    def ls(args: Any*) =
      SimpleCommand("ls", CmdArgCtx(args.toVector, s))

    def lsattr(args: Any*) =
      SimpleCommand("lsattr", CmdArgCtx(args.toVector, s))

    def lsof(args: Any*) =
      SimpleCommand("lsof", CmdArgCtx(args.toVector, s))

    def ltrace(args: Any*) =
      SimpleCommand("ltrace", CmdArgCtx(args.toVector, s))

    def make(args: Any*) =
      SimpleCommand("make", CmdArgCtx(args.toVector, s))

    def mapfile(args: Any*) =
      SimpleCommand("mapfile", CmdArgCtx(args.toVector, s))

    def mbadblocks(args: Any*) =
      SimpleCommand("mbadblocks", CmdArgCtx(args.toVector, s))

    def mcopy(args: Any*) =
      SimpleCommand("mcopy", CmdArgCtx(args.toVector, s))

    def md5sum(args: Any*) =
      SimpleCommand("md5sum", CmdArgCtx(args.toVector, s))

    def mformat(args: Any*) =
      SimpleCommand("mformat", CmdArgCtx(args.toVector, s))

    def mkdir(args: Any*) =
      SimpleCommand("mkdir", CmdArgCtx(args.toVector, s))

    def mkfifo(args: Any*) =
      SimpleCommand("mkfifo", CmdArgCtx(args.toVector, s))

    def mknod(args: Any*) =
      SimpleCommand("mknod", CmdArgCtx(args.toVector, s))

    def mkpasswd(args: Any*) =
      SimpleCommand("mkpasswd", CmdArgCtx(args.toVector, s))

    def mktemp(args: Any*) =
      SimpleCommand("mktemp", CmdArgCtx(args.toVector, s))

    def mmount(args: Any*) =
      SimpleCommand("mmount", CmdArgCtx(args.toVector, s))

    def mmv(args: Any*) =
      SimpleCommand("mmv", CmdArgCtx(args.toVector, s))

    def mogrify(args: Any*) =
      SimpleCommand("mogrify", CmdArgCtx(args.toVector, s))

    def more(args: Any*) =
      SimpleCommand("more", CmdArgCtx(args.toVector, s))

    def moun(args: Any*) =
      SimpleCommand("moun", CmdArgCtx(args.toVector, s))

    def mv(args: Any*) =
      SimpleCommand("mv", CmdArgCtx(args.toVector, s))

    def netstat(args: Any*) =
      SimpleCommand("netstat", CmdArgCtx(args.toVector, s))

    def newgrp(args: Any*) =
      SimpleCommand("newgrp", CmdArgCtx(args.toVector, s))

    def nice(args: Any*) =
      SimpleCommand("nice", CmdArgCtx(args.toVector, s))

    def nix(args: Any*) =
      SimpleCommand("nix", CmdArgCtx(args.toVector, s))

    def nix_build(args: Any*) =
      SimpleCommand("nix_build", CmdArgCtx(args.toVector, s))

    def nix_env(args: Any*) =
      SimpleCommand("nix_env", CmdArgCtx(args.toVector, s))

    def nix_shell(args: Any*) =
      SimpleCommand("nix_shell", CmdArgCtx(args.toVector, s))

    def nix_store(args: Any*) =
      SimpleCommand("nix_store", CmdArgCtx(args.toVector, s))

    def nl(args: Any*) =
      SimpleCommand("nl", CmdArgCtx(args.toVector, s))

    def nmap(args: Any*) =
      SimpleCommand("nmap", CmdArgCtx(args.toVector, s))

    def nohup(args: Any*) =
      SimpleCommand("nohup", CmdArgCtx(args.toVector, s))

    def nproc(args: Any*) =
      SimpleCommand("nproc", CmdArgCtx(args.toVector, s))

    def numfmt(args: Any*) =
      SimpleCommand("numfmt", CmdArgCtx(args.toVector, s))

    def numgrep(args: Any*) =
      SimpleCommand("numgrep", CmdArgCtx(args.toVector, s))

    def od(args: Any*) =
      SimpleCommand("od", CmdArgCtx(args.toVector, s))

    def passwd(args: Any*) =
      SimpleCommand("passwd", CmdArgCtx(args.toVector, s))

    def paste(args: Any*) =
      SimpleCommand("paste", CmdArgCtx(args.toVector, s))

    def pathchk(args: Any*) =
      SimpleCommand("pathchk", CmdArgCtx(args.toVector, s))

    def perl(args: Any*) =
      SimpleCommand("perl", CmdArgCtx(args.toVector, s))

    def pgrep(args: Any*) =
      SimpleCommand("pgrep", CmdArgCtx(args.toVector, s))

    def ping(args: Any*) =
      SimpleCommand("ping", CmdArgCtx(args.toVector, s))

    def pinky(args: Any*) =
      SimpleCommand("pinky", CmdArgCtx(args.toVector, s))

    def pkill(args: Any*) =
      SimpleCommand("pkill", CmdArgCtx(args.toVector, s))

    def popd(args: Any*) =
      SimpleCommand("popd", CmdArgCtx(args.toVector, s))

    def pr(args: Any*) =
      SimpleCommand("pr", CmdArgCtx(args.toVector, s))

    def printenv(args: Any*) =
      SimpleCommand("printenv", CmdArgCtx(args.toVector, s))

    def printf(args: Any*) =
      SimpleCommand("printf", CmdArgCtx(args.toVector, s))

    def ps(args: Any*) =
      SimpleCommand("ps", CmdArgCtx(args.toVector, s))

    def pstree(args: Any*) =
      SimpleCommand("pstree", CmdArgCtx(args.toVector, s))

    def ptx(args: Any*) =
      SimpleCommand("ptx", CmdArgCtx(args.toVector, s))

    def pushd(args: Any*) =
      SimpleCommand("pushd", CmdArgCtx(args.toVector, s))

    def pwd(args: Any*) =
      SimpleCommand("pwd", CmdArgCtx(args.toVector, s))

    def py(args: Any*) =
      SimpleCommand("py", CmdArgCtx(args.toVector, s))

    def read(args: Any*) =
      SimpleCommand("read", CmdArgCtx(args.toVector, s))

    def readarray(args: Any*) =
      SimpleCommand("readarray", CmdArgCtx(args.toVector, s))

    def readlink(args: Any*) =
      SimpleCommand("readlink", CmdArgCtx(args.toVector, s))

    def realpath(args: Any*) =
      SimpleCommand("realpath", CmdArgCtx(args.toVector, s))

    def reboot(args: Any*) =
      SimpleCommand("reboot", CmdArgCtx(args.toVector, s))

    def recode(args: Any*) =
      SimpleCommand("recode", CmdArgCtx(args.toVector, s))

    def rel(args: Any*) =
      SimpleCommand("rel", CmdArgCtx(args.toVector, s))

    def rename(args: Any*) =
      SimpleCommand("rename", CmdArgCtx(args.toVector, s))

    def renice(args: Any*) =
      SimpleCommand("renice", CmdArgCtx(args.toVector, s))

    def rgrep(args: Any*) =
      SimpleCommand("rgrep", CmdArgCtx(args.toVector, s))

    def rm(args: Any*) =
      SimpleCommand("rm", CmdArgCtx(args.toVector, s))

    def rmdir(args: Any*) =
      SimpleCommand("rmdir", CmdArgCtx(args.toVector, s))

    def root(args: Any*) =
      SimpleCommand("root", CmdArgCtx(args.toVector, s))

    def route(args: Any*) =
      SimpleCommand("route", CmdArgCtx(args.toVector, s))

    def rpm(args: Any*) =
      SimpleCommand("rpm", CmdArgCtx(args.toVector, s))

    def rsync(args: Any*) =
      SimpleCommand("rsync", CmdArgCtx(args.toVector, s))

    def runcon(args: Any*) =
      SimpleCommand("runcon", CmdArgCtx(args.toVector, s))

    def samba_tool(args: Any*) =
      SimpleCommand("samba_tool", CmdArgCtx(args.toVector, s))

    def sc(args: Any*) =
      SimpleCommand("sc", CmdArgCtx(args.toVector, s))

    def scp(args: Any*) =
      SimpleCommand("scp", CmdArgCtx(args.toVector, s))

    def sdiff(args: Any*) =
      SimpleCommand("sdiff", CmdArgCtx(args.toVector, s))

    def sed(args: Any*) =
      SimpleCommand("sed", CmdArgCtx(args.toVector, s))

    def seq(args: Any*) =
      SimpleCommand("seq", CmdArgCtx(args.toVector, s))

    def service(args: Any*) =
      SimpleCommand("service", CmdArgCtx(args.toVector, s))

    def setfacl(args: Any*) =
      SimpleCommand("setfacl", CmdArgCtx(args.toVector, s))

    def sftp(args: Any*) =
      SimpleCommand("sftp", CmdArgCtx(args.toVector, s))

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

    def shutdown(args: Any*) =
      SimpleCommand("shutdown", CmdArgCtx(args.toVector, s))

    def skill(args: Any*) =
      SimpleCommand("skill", CmdArgCtx(args.toVector, s))

    def sleep(args: Any*) =
      SimpleCommand("sleep", CmdArgCtx(args.toVector, s))

    def slocate(args: Any*) =
      SimpleCommand("slocate", CmdArgCtx(args.toVector, s))

    def smbclient(args: Any*) =
      SimpleCommand("smbclient", CmdArgCtx(args.toVector, s))

    def smbcontrol(args: Any*) =
      SimpleCommand("smbcontrol", CmdArgCtx(args.toVector, s))

    def smbd(args: Any*) =
      SimpleCommand("smbd", CmdArgCtx(args.toVector, s))

    def smbmount(args: Any*) =
      SimpleCommand("smbmount", CmdArgCtx(args.toVector, s))

    def snice(args: Any*) =
      SimpleCommand("snice", CmdArgCtx(args.toVector, s))

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

    def strace(args: Any*) =
      SimpleCommand("strace", CmdArgCtx(args.toVector, s))

    def stty(args: Any*) =
      SimpleCommand("stty", CmdArgCtx(args.toVector, s))

    def style(args: Any*) =
      SimpleCommand("style", CmdArgCtx(args.toVector, s))

    def su(args: Any*) =
      SimpleCommand("su", CmdArgCtx(args.toVector, s))

    def suid(args: Any*) =
      SimpleCommand("suid", CmdArgCtx(args.toVector, s))

    def sum(args: Any*) =
      SimpleCommand("sum", CmdArgCtx(args.toVector, s))

    def sync(args: Any*) =
      SimpleCommand("sync", CmdArgCtx(args.toVector, s))

    def tac(args: Any*) =
      SimpleCommand("tac", CmdArgCtx(args.toVector, s))

    def tail(args: Any*) =
      SimpleCommand("tail", CmdArgCtx(args.toVector, s))

    def tar(args: Any*) =
      SimpleCommand("tar", CmdArgCtx(args.toVector, s))

    def tcpdump(args: Any*) =
      SimpleCommand("tcpdump", CmdArgCtx(args.toVector, s))

    def tee(args: Any*) =
      SimpleCommand("tee", CmdArgCtx(args.toVector, s))

    def test(args: Any*) =
      SimpleCommand("test", CmdArgCtx(args.toVector, s))

    def time(args: Any*) =
      SimpleCommand("time", CmdArgCtx(args.toVector, s))

    def timeout(args: Any*) =
      SimpleCommand("timeout", CmdArgCtx(args.toVector, s))

    def top(args: Any*) =
      SimpleCommand("top", CmdArgCtx(args.toVector, s))

    def touch(args: Any*) =
      SimpleCommand("touch", CmdArgCtx(args.toVector, s))

    def tr(args: Any*) =
      SimpleCommand("tr", CmdArgCtx(args.toVector, s))

    def tracepath(args: Any*) =
      SimpleCommand("tracepath", CmdArgCtx(args.toVector, s))

    def traceroute(args: Any*) =
      SimpleCommand("traceroute", CmdArgCtx(args.toVector, s))

    def truncate(args: Any*) =
      SimpleCommand("truncate", CmdArgCtx(args.toVector, s))

    def tsort(args: Any*) =
      SimpleCommand("tsort", CmdArgCtx(args.toVector, s))

    def tty(args: Any*) =
      SimpleCommand("tty", CmdArgCtx(args.toVector, s))

    def typeset(args: Any*) =
      SimpleCommand("typeset", CmdArgCtx(args.toVector, s))

    def ulimit(args: Any*) =
      SimpleCommand("ulimit", CmdArgCtx(args.toVector, s))

    def umask(args: Any*) =
      SimpleCommand("umask", CmdArgCtx(args.toVector, s))

    def umount(args: Any*) =
      SimpleCommand("umount", CmdArgCtx(args.toVector, s))

    def unalias(args: Any*) =
      SimpleCommand("unalias", CmdArgCtx(args.toVector, s))

    def uname(args: Any*) =
      SimpleCommand("uname", CmdArgCtx(args.toVector, s))

    def unexpand(args: Any*) =
      SimpleCommand("unexpand", CmdArgCtx(args.toVector, s))

    def uniq(args: Any*) =
      SimpleCommand("uniq", CmdArgCtx(args.toVector, s))

    def units(args: Any*) =
      SimpleCommand("units", CmdArgCtx(args.toVector, s))

    def unix2dos(args: Any*) =
      SimpleCommand("unix2dos", CmdArgCtx(args.toVector, s))

    def unlink(args: Any*) =
      SimpleCommand("unlink", CmdArgCtx(args.toVector, s))

    def uptime(args: Any*) =
      SimpleCommand("uptime", CmdArgCtx(args.toVector, s))

    def users(args: Any*) =
      SimpleCommand("users", CmdArgCtx(args.toVector, s))

    def vdir(args: Any*) =
      SimpleCommand("vdir", CmdArgCtx(args.toVector, s))

    def vlc(args: Any*) =
      SimpleCommand("vlc", CmdArgCtx(args.toVector, s))

    def w(args: Any*) =
      SimpleCommand("w", CmdArgCtx(args.toVector, s))

    def wc(args: Any*) =
      SimpleCommand("wc", CmdArgCtx(args.toVector, s))

    def wget(args: Any*) =
      SimpleCommand("wget", CmdArgCtx(args.toVector, s))

    def whereis(args: Any*) =
      SimpleCommand("whereis", CmdArgCtx(args.toVector, s))

    def whic(args: Any*) =
      SimpleCommand("whic", CmdArgCtx(args.toVector, s))

    def who(args: Any*) =
      SimpleCommand("who", CmdArgCtx(args.toVector, s))

    def whoami(args: Any*) =
      SimpleCommand("whoami", CmdArgCtx(args.toVector, s))

    def whois(args: Any*) =
      SimpleCommand("whois", CmdArgCtx(args.toVector, s))

    def xargs(args: Any*) =
      SimpleCommand("xargs", CmdArgCtx(args.toVector, s))

    def yes(args: Any*) =
      SimpleCommand("yes", CmdArgCtx(args.toVector, s))

    def zcat(args: Any*) =
      SimpleCommand("zcat", CmdArgCtx(args.toVector, s))

    def zcmp(args: Any*) =
      SimpleCommand("zcmp", CmdArgCtx(args.toVector, s))

    def zdiff(args: Any*) =
      SimpleCommand("zdiff", CmdArgCtx(args.toVector, s))

    def zgrep(args: Any*) =
      SimpleCommand("zgrep", CmdArgCtx(args.toVector, s))

    def zipgrep(args: Any*) =
      SimpleCommand("zipgrep", CmdArgCtx(args.toVector, s))

    def zipinfo(args: Any*) =
      SimpleCommand("zipinfo", CmdArgCtx(args.toVector, s))

    def zless(args: Any*) =
      SimpleCommand("zless", CmdArgCtx(args.toVector, s))

    def zmore(args: Any*) =
      SimpleCommand("zmore", CmdArgCtx(args.toVector, s))

    def zsh(args: Any*) =
      SimpleCommand("zsh", CmdArgCtx(args.toVector, s))
  }

}
