package bash
import domain._

package object bash {

  def Var(implicit name: sourcecode.Name) =
    BashVariable(name.value, BEmpty())

  def bash_#! = ScriptBuilder(Vector())

  def End = END()
  def True = TRUE()
  def False = FALSE()

  def $(op: CommandOp) =
    ScriptBuilder(Vector(SubCommandStart(), op, SubCommandEnd()))

  def time(op: CommandOp) =
    ScriptBuilder(Vector(TimedPipeline(), op))

  implicit class CmdSyntax(s: StringContext) {

    def comm(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("comm", CmdArgCtx(args.toVector, s))))

    def realpath(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("realpath", CmdArgCtx(args.toVector, s)))
      )

    def test(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("test", CmdArgCtx(args.toVector, s))))

    def runcon(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("runcon", CmdArgCtx(args.toVector, s)))
      )

    def dircolors(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("dircolors", CmdArgCtx(args.toVector, s)))
      )

    def du(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("du", CmdArgCtx(args.toVector, s))))

    def uniq(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("uniq", CmdArgCtx(args.toVector, s))))

    def mknod(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("mknod", CmdArgCtx(args.toVector, s))))

    def rmdir(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("rmdir", CmdArgCtx(args.toVector, s))))

    def shred(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("shred", CmdArgCtx(args.toVector, s))))

    def sha256sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("sha256sum", CmdArgCtx(args.toVector, s)))
      )

    def dirname(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("dirname", CmdArgCtx(args.toVector, s)))
      )

    def b2sum(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("b2sum", CmdArgCtx(args.toVector, s))))

    def base64(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("base64", CmdArgCtx(args.toVector, s)))
      )

    def ls(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ls", CmdArgCtx(args.toVector, s))))

    def join(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("join", CmdArgCtx(args.toVector, s))))

    def yes(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("yes", CmdArgCtx(args.toVector, s))))

    def paste(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("paste", CmdArgCtx(args.toVector, s))))

    def sha384sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("sha384sum", CmdArgCtx(args.toVector, s)))
      )

    def touch(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("touch", CmdArgCtx(args.toVector, s))))

    def wc(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("wc", CmdArgCtx(args.toVector, s))))

    def whoami(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("whoami", CmdArgCtx(args.toVector, s)))
      )

    def tee(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tee", CmdArgCtx(args.toVector, s))))

    def head(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("head", CmdArgCtx(args.toVector, s))))

    def chgrp(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("chgrp", CmdArgCtx(args.toVector, s))))

    def cut(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("cut", CmdArgCtx(args.toVector, s))))

    def users(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("users", CmdArgCtx(args.toVector, s))))

    def chcon(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("chcon", CmdArgCtx(args.toVector, s))))

    def stat(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("stat", CmdArgCtx(args.toVector, s))))

    def truncate(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("truncate", CmdArgCtx(args.toVector, s)))
      )

    def mkdir(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("mkdir", CmdArgCtx(args.toVector, s))))

    def sleep(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("sleep", CmdArgCtx(args.toVector, s))))

    def md5sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("md5sum", CmdArgCtx(args.toVector, s)))
      )

    def printenv(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("printenv", CmdArgCtx(args.toVector, s)))
      )

    def csplit(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("csplit", CmdArgCtx(args.toVector, s)))
      )

    def nl(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("nl", CmdArgCtx(args.toVector, s))))

    def pathchk(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("pathchk", CmdArgCtx(args.toVector, s)))
      )

    def basename(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("basename", CmdArgCtx(args.toVector, s)))
      )

    def fold(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("fold", CmdArgCtx(args.toVector, s))))

    def ptx(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ptx", CmdArgCtx(args.toVector, s))))

    def expr(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("expr", CmdArgCtx(args.toVector, s))))

    def nice(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("nice", CmdArgCtx(args.toVector, s))))

    def stty(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("stty", CmdArgCtx(args.toVector, s))))

    def rm(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("rm", CmdArgCtx(args.toVector, s))))

    def tail(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tail", CmdArgCtx(args.toVector, s))))

    def od(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("od", CmdArgCtx(args.toVector, s))))

    def groups(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("groups", CmdArgCtx(args.toVector, s)))
      )

    def sha1sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("sha1sum", CmdArgCtx(args.toVector, s)))
      )

    def logname(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("logname", CmdArgCtx(args.toVector, s)))
      )

    def hostid(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("hostid", CmdArgCtx(args.toVector, s)))
      )

    def factor(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("factor", CmdArgCtx(args.toVector, s)))
      )

    def expand(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("expand", CmdArgCtx(args.toVector, s)))
      )

    def pwd(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("pwd", CmdArgCtx(args.toVector, s))))

    def unexpand(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("unexpand", CmdArgCtx(args.toVector, s)))
      )

    def unlink(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("unlink", CmdArgCtx(args.toVector, s)))
      )

    def ln(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ln", CmdArgCtx(args.toVector, s))))

    def uname(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("uname", CmdArgCtx(args.toVector, s))))

    def dir(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("dir", CmdArgCtx(args.toVector, s))))

    def tac(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tac", CmdArgCtx(args.toVector, s))))

    def tsort(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tsort", CmdArgCtx(args.toVector, s))))

    def id(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("id", CmdArgCtx(args.toVector, s))))

    def tr(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tr", CmdArgCtx(args.toVector, s))))

    def pr(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("pr", CmdArgCtx(args.toVector, s))))

    def printf(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("printf", CmdArgCtx(args.toVector, s)))
      )

    def date(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("date", CmdArgCtx(args.toVector, s))))

    def sha224sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("sha224sum", CmdArgCtx(args.toVector, s)))
      )

    def who(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("who", CmdArgCtx(args.toVector, s))))

    def chroot(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("chroot", CmdArgCtx(args.toVector, s)))
      )

    def split(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("split", CmdArgCtx(args.toVector, s))))

    def readlink(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("readlink", CmdArgCtx(args.toVector, s)))
      )

    def link(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("link", CmdArgCtx(args.toVector, s))))

    def echo(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("echo", CmdArgCtx(args.toVector, s))))

    def sync(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("sync", CmdArgCtx(args.toVector, s))))

    def stdbuf(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("stdbuf", CmdArgCtx(args.toVector, s)))
      )

    def df(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("df", CmdArgCtx(args.toVector, s))))

    def uptime(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("uptime", CmdArgCtx(args.toVector, s)))
      )

    def base32(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("base32", CmdArgCtx(args.toVector, s)))
      )

    def pinky(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("pinky", CmdArgCtx(args.toVector, s))))

    def tty(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("tty", CmdArgCtx(args.toVector, s))))

    def seq(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("seq", CmdArgCtx(args.toVector, s))))

    def vdir(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("vdir", CmdArgCtx(args.toVector, s))))

    def fmt(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("fmt", CmdArgCtx(args.toVector, s))))

    def numfmt(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("numfmt", CmdArgCtx(args.toVector, s)))
      )

    def sort(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("sort", CmdArgCtx(args.toVector, s))))

    def install(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("install", CmdArgCtx(args.toVector, s)))
      )

    def nproc(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("nproc", CmdArgCtx(args.toVector, s))))

    def cksum(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("cksum", CmdArgCtx(args.toVector, s))))

    def cp(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("cp", CmdArgCtx(args.toVector, s))))

    def basenc(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("basenc", CmdArgCtx(args.toVector, s)))
      )

    def mv(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("mv", CmdArgCtx(args.toVector, s))))

    def chown(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("chown", CmdArgCtx(args.toVector, s))))

    def cat(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("cat", CmdArgCtx(args.toVector, s))))

    def kill(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("kill", CmdArgCtx(args.toVector, s))))

    def mktemp(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("mktemp", CmdArgCtx(args.toVector, s)))
      )

    def mkfifo(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("mkfifo", CmdArgCtx(args.toVector, s)))
      )

    def timeout(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("timeout", CmdArgCtx(args.toVector, s)))
      )

    def nohup(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("nohup", CmdArgCtx(args.toVector, s))))

    def sum(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("sum", CmdArgCtx(args.toVector, s))))

    def shuf(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("shuf", CmdArgCtx(args.toVector, s))))

    def env(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("env", CmdArgCtx(args.toVector, s))))

    def sha512sum(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("sha512sum", CmdArgCtx(args.toVector, s)))
      )

    def dd(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("dd", CmdArgCtx(args.toVector, s))))

    def chmod(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("chmod", CmdArgCtx(args.toVector, s))))

    def ssh(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ssh", CmdArgCtx(args.toVector, s))))

    def grep(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("grep", CmdArgCtx(args.toVector, s))))

    def ping(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("ping", CmdArgCtx(args.toVector, s))))

    def ifconfig(args: Any*) =
      ScriptBuilder(
        Vector(SimpleCommand("ifconfig", CmdArgCtx(args.toVector, s)))
      )

    def zsh(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("zsh", CmdArgCtx(args.toVector, s))))

    def rsync(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("rsync", CmdArgCtx(args.toVector, s))))

    def scala(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("scala", CmdArgCtx(args.toVector, s))))

    def py(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("py", CmdArgCtx(args.toVector, s))))

    def R(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("R", CmdArgCtx(args.toVector, s))))

    def bash(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("bash", CmdArgCtx(args.toVector, s))))

    def arp(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("arp", CmdArgCtx(args.toVector, s))))

    def sh(args: Any*) =
      ScriptBuilder(Vector(SimpleCommand("sh", CmdArgCtx(args.toVector, s))))

  }

}
