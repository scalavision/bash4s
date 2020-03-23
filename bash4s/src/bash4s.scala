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
    CommandListBuilder(Vector(OpenGroupInContext(), op))

  def `(`(op: CommandOp) =
    CommandListBuilder(Vector(OpenSubShellEnv(), op))

  def &&(op: CommandOp) = Vector(And(), op)

  def Do(op: CommandOp) = CDo(op)

  def Then(op: CommandOp) = CThen(op)

  def <(op: CommandOp) = ScriptBuilder(Vector(StdIn(), op))

  object For {
    def apply(indexVariable: CommandOp) =
      CFor(Vector(indexVariable))
  }

  def cat(hereStr: HereString) =
    SimpleCommand("cat", hereStr)

  def Var(implicit name: sourcecode.Name) = BashVariable(name.value)

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

  def Modemmanager(args: String*) =
    clitools.ModemManagerWrapper(CmdArgs(args.toVector))
  def Modemmanager = clitools.ModemManagerWrapper()

  def Networkmanager(args: String*) =
    clitools.NetworkManagerWrapper(CmdArgs(args.toVector))
  def Networkmanager = clitools.NetworkManagerWrapper()

  def R(args: String*) = clitools.RWrapper(CmdArgs(args.toVector))
  def R = clitools.RWrapper()

  def Star(args: String*) = clitools.STARWrapper(CmdArgs(args.toVector))
  def Star = clitools.STARWrapper()

  def Starlong(args: String*) = clitools.STARlongWrapper(CmdArgs(args.toVector))
  def Starlong = clitools.STARlongWrapper()

  def X(args: String*) = clitools.XWrapper(CmdArgs(args.toVector))
  def X = clitools.XWrapper()

  def Xephyr(args: String*) = clitools.XephyrWrapper(CmdArgs(args.toVector))
  def Xephyr = clitools.XephyrWrapper()

  def Xnest(args: String*) = clitools.XnestWrapper(CmdArgs(args.toVector))
  def Xnest = clitools.XnestWrapper()

  def Xorg(args: String*) = clitools.XorgWrapper(CmdArgs(args.toVector))
  def Xorg = clitools.XorgWrapper()

  def Xvfb(args: String*) = clitools.XvfbWrapper(CmdArgs(args.toVector))
  def Xvfb = clitools.XvfbWrapper()

  def aaindexextract(args: String*) =
    clitools.AaindexextractWrapper(CmdArgs(args.toVector))
  def aaindexextract = clitools.AaindexextractWrapper()

  def abiview(args: String*) = clitools.AbiviewWrapper(CmdArgs(args.toVector))
  def abiview = clitools.AbiviewWrapper()

  def accept(args: String*) = clitools.AcceptWrapper(CmdArgs(args.toVector))
  def accept = clitools.AcceptWrapper()

  def accessdb(args: String*) = clitools.AccessdbWrapper(CmdArgs(args.toVector))
  def accessdb = clitools.AccessdbWrapper()

  def acdc(args: String*) = clitools.AcdcWrapper(CmdArgs(args.toVector))
  def acdc = clitools.AcdcWrapper()

  def acdgalaxy(args: String*) =
    clitools.AcdgalaxyWrapper(CmdArgs(args.toVector))
  def acdgalaxy = clitools.AcdgalaxyWrapper()

  def acdlog(args: String*) = clitools.AcdlogWrapper(CmdArgs(args.toVector))
  def acdlog = clitools.AcdlogWrapper()

  def acdpretty(args: String*) =
    clitools.AcdprettyWrapper(CmdArgs(args.toVector))
  def acdpretty = clitools.AcdprettyWrapper()

  def acdtable(args: String*) = clitools.AcdtableWrapper(CmdArgs(args.toVector))
  def acdtable = clitools.AcdtableWrapper()

  def acdtrace(args: String*) = clitools.AcdtraceWrapper(CmdArgs(args.toVector))
  def acdtrace = clitools.AcdtraceWrapper()

  def acdvalid(args: String*) = clitools.AcdvalidWrapper(CmdArgs(args.toVector))
  def acdvalid = clitools.AcdvalidWrapper()

  def ace2sam(args: String*) = clitools.Ace2samWrapper(CmdArgs(args.toVector))
  def ace2sam = clitools.Ace2samWrapper()

  def ack(args: String*) = clitools.AckWrapper(CmdArgs(args.toVector))
  def ack = clitools.AckWrapper()

  def aconnect(args: String*) = clitools.AconnectWrapper(CmdArgs(args.toVector))
  def aconnect = clitools.AconnectWrapper()

  def acroread(args: String*) = clitools.AcroreadWrapper(CmdArgs(args.toVector))
  def acroread = clitools.AcroreadWrapper()

  def acyclic(args: String*) = clitools.AcyclicWrapper(CmdArgs(args.toVector))
  def acyclic = clitools.AcyclicWrapper()

  def addgnupghome(args: String*) =
    clitools.AddgnupghomeWrapper(CmdArgs(args.toVector))
  def addgnupghome = clitools.AddgnupghomeWrapper()

  def addpart(args: String*) = clitools.AddpartWrapper(CmdArgs(args.toVector))
  def addpart = clitools.AddpartWrapper()

  def ag(args: String*) = clitools.AgWrapper(CmdArgs(args.toVector))
  def ag = clitools.AgWrapper()

  def agetty(args: String*) = clitools.AgettyWrapper(CmdArgs(args.toVector))
  def agetty = clitools.AgettyWrapper()

  def alias(args: String*) = clitools.AliasWrapper(CmdArgs(args.toVector))
  def alias = clitools.AliasWrapper()

  def aligncopy(args: String*) =
    clitools.AligncopyWrapper(CmdArgs(args.toVector))
  def aligncopy = clitools.AligncopyWrapper()

  def aligncopypair(args: String*) =
    clitools.AligncopypairWrapper(CmdArgs(args.toVector))
  def aligncopypair = clitools.AligncopypairWrapper()

  def alimask(args: String*) = clitools.AlimaskWrapper(CmdArgs(args.toVector))
  def alimask = clitools.AlimaskWrapper()

  def amidi(args: String*) = clitools.AmidiWrapper(CmdArgs(args.toVector))
  def amidi = clitools.AmidiWrapper()

  def amixer(args: String*) = clitools.AmixerWrapper(CmdArgs(args.toVector))
  def amixer = clitools.AmixerWrapper()

  def amm(args: String*) = clitools.AmmWrapper(CmdArgs(args.toVector))
  def amm = clitools.AmmWrapper()

  def animate(args: String*) = clitools.AnimateWrapper(CmdArgs(args.toVector))
  def animate = clitools.AnimateWrapper()

  def annotatebed(args: String*) =
    clitools.AnnotateBedWrapper(CmdArgs(args.toVector))
  def annotatebed = clitools.AnnotateBedWrapper()

  def ant(args: String*) = clitools.AntWrapper(CmdArgs(args.toVector))
  def ant = clitools.AntWrapper()

  def antigenic(args: String*) =
    clitools.AntigenicWrapper(CmdArgs(args.toVector))
  def antigenic = clitools.AntigenicWrapper()

  def aplay(args: String*) = clitools.AplayWrapper(CmdArgs(args.toVector))
  def aplay = clitools.AplayWrapper()

  def aplaymidi(args: String*) =
    clitools.AplaymidiWrapper(CmdArgs(args.toVector))
  def aplaymidi = clitools.AplaymidiWrapper()

  def applygnupgdefaults(args: String*) =
    clitools.ApplygnupgdefaultsWrapper(CmdArgs(args.toVector))
  def applygnupgdefaults = clitools.ApplygnupgdefaultsWrapper()

  def apropos(args: String*) = clitools.AproposWrapper(CmdArgs(args.toVector))
  def apropos = clitools.AproposWrapper()

  def apvlv(args: String*) = clitools.ApvlvWrapper(CmdArgs(args.toVector))
  def apvlv = clitools.ApvlvWrapper()

  def arecord(args: String*) = clitools.ArecordWrapper(CmdArgs(args.toVector))
  def arecord = clitools.ArecordWrapper()

  def arecordmidi(args: String*) =
    clitools.ArecordmidiWrapper(CmdArgs(args.toVector))
  def arecordmidi = clitools.ArecordmidiWrapper()

  def ark(args: String*) = clitools.ArkWrapper(CmdArgs(args.toVector))
  def ark = clitools.ArkWrapper()

  def arp(args: String*) = clitools.ArpWrapper(CmdArgs(args.toVector))
  def arp = clitools.ArpWrapper()

  def arpd(args: String*) = clitools.ArpdWrapper(CmdArgs(args.toVector))
  def arpd = clitools.ArpdWrapper()

  def arping(args: String*) = clitools.ArpingWrapper(CmdArgs(args.toVector))
  def arping = clitools.ArpingWrapper()

  def arptables(args: String*) =
    clitools.ArptablesWrapper(CmdArgs(args.toVector))
  def arptables = clitools.ArptablesWrapper()

  def arptables_nft(args: String*) =
    clitools.Arptables_nftWrapper(CmdArgs(args.toVector))
  def arptables_nft = clitools.Arptables_nftWrapper()

  def arptables_nft_restore(args: String*) =
    clitools.Arptables_nft_restoreWrapper(CmdArgs(args.toVector))
  def arptables_nft_restore = clitools.Arptables_nft_restoreWrapper()

  def arptables_nft_save(args: String*) =
    clitools.Arptables_nft_saveWrapper(CmdArgs(args.toVector))
  def arptables_nft_save = clitools.Arptables_nft_saveWrapper()

  def arptables_restore(args: String*) =
    clitools.Arptables_restoreWrapper(CmdArgs(args.toVector))
  def arptables_restore = clitools.Arptables_restoreWrapper()

  def arptables_save(args: String*) =
    clitools.Arptables_saveWrapper(CmdArgs(args.toVector))
  def arptables_save = clitools.Arptables_saveWrapper()

  def as(args: String*) = clitools.AsWrapper(CmdArgs(args.toVector))
  def as = clitools.AsWrapper()

  def aseqdump(args: String*) = clitools.AseqdumpWrapper(CmdArgs(args.toVector))
  def aseqdump = clitools.AseqdumpWrapper()

  def aseqnet(args: String*) = clitools.AseqnetWrapper(CmdArgs(args.toVector))
  def aseqnet = clitools.AseqnetWrapper()

  def assemblyget(args: String*) =
    clitools.AssemblygetWrapper(CmdArgs(args.toVector))
  def assemblyget = clitools.AssemblygetWrapper()

  def attr(args: String*) = clitools.AttrWrapper(CmdArgs(args.toVector))
  def attr = clitools.AttrWrapper()

  def awk(args: String*) = clitools.AwkWrapper(CmdArgs(args.toVector))
  def awk = clitools.AwkWrapper()

  def axfer(args: String*) = clitools.AxferWrapper(CmdArgs(args.toVector))
  def axfer = clitools.AxferWrapper()

  def b2sum(args: String*) = clitools.B2sumWrapper(CmdArgs(args.toVector))
  def b2sum = clitools.B2sumWrapper()

  def backtranambig(args: String*) =
    clitools.BacktranambigWrapper(CmdArgs(args.toVector))
  def backtranambig = clitools.BacktranambigWrapper()

  def backtranseq(args: String*) =
    clitools.BacktranseqWrapper(CmdArgs(args.toVector))
  def backtranseq = clitools.BacktranseqWrapper()

  def badblocks(args: String*) =
    clitools.BadblocksWrapper(CmdArgs(args.toVector))
  def badblocks = clitools.BadblocksWrapper()

  def baloo_file(args: String*) =
    clitools.Baloo_fileWrapper(CmdArgs(args.toVector))
  def baloo_file = clitools.Baloo_fileWrapper()

  def baloo_file_extractor(args: String*) =
    clitools.Baloo_file_extractorWrapper(CmdArgs(args.toVector))
  def baloo_file_extractor = clitools.Baloo_file_extractorWrapper()

  def balooctl(args: String*) = clitools.BalooctlWrapper(CmdArgs(args.toVector))
  def balooctl = clitools.BalooctlWrapper()

  def baloosearch(args: String*) =
    clitools.BaloosearchWrapper(CmdArgs(args.toVector))
  def baloosearch = clitools.BaloosearchWrapper()

  def balooshow(args: String*) =
    clitools.BalooshowWrapper(CmdArgs(args.toVector))
  def balooshow = clitools.BalooshowWrapper()

  def bamtobed(args: String*) = clitools.BamToBedWrapper(CmdArgs(args.toVector))
  def bamtobed = clitools.BamToBedWrapper()

  def bamtofastq(args: String*) =
    clitools.BamToFastqWrapper(CmdArgs(args.toVector))
  def bamtofastq = clitools.BamToFastqWrapper()

  def banana(args: String*) = clitools.BananaWrapper(CmdArgs(args.toVector))
  def banana = clitools.BananaWrapper()

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

  def bashbug(args: String*) = clitools.BashbugWrapper(CmdArgs(args.toVector))
  def bashbug = clitools.BashbugWrapper()

  def bcache_super_show(args: String*) =
    clitools.Bcache_super_showWrapper(CmdArgs(args.toVector))
  def bcache_super_show = clitools.Bcache_super_showWrapper()

  def bcftools(args: String*) = clitools.BcftoolsWrapper(CmdArgs(args.toVector))
  def bcftools = clitools.BcftoolsWrapper()

  def bcomps(args: String*) = clitools.BcompsWrapper(CmdArgs(args.toVector))
  def bcomps = clitools.BcompsWrapper()

  def bed12tobed6(args: String*) =
    clitools.Bed12ToBed6Wrapper(CmdArgs(args.toVector))
  def bed12tobed6 = clitools.Bed12ToBed6Wrapper()

  def bedtobam(args: String*) = clitools.BedToBamWrapper(CmdArgs(args.toVector))
  def bedtobam = clitools.BedToBamWrapper()

  def bedtoigv(args: String*) = clitools.BedToIgvWrapper(CmdArgs(args.toVector))
  def bedtoigv = clitools.BedToIgvWrapper()

  def bedpetobam(args: String*) =
    clitools.BedpeToBamWrapper(CmdArgs(args.toVector))
  def bedpetobam = clitools.BedpeToBamWrapper()

  def bedtools(args: String*) = clitools.BedtoolsWrapper(CmdArgs(args.toVector))
  def bedtools = clitools.BedtoolsWrapper()

  def bind(args: String*) = clitools.BindWrapper(CmdArgs(args.toVector))
  def bind = clitools.BindWrapper()

  def biosed(args: String*) = clitools.BiosedWrapper(CmdArgs(args.toVector))
  def biosed = clitools.BiosedWrapper()

  def blkdeactivate(args: String*) =
    clitools.BlkdeactivateWrapper(CmdArgs(args.toVector))
  def blkdeactivate = clitools.BlkdeactivateWrapper()

  def blkdiscard(args: String*) =
    clitools.BlkdiscardWrapper(CmdArgs(args.toVector))
  def blkdiscard = clitools.BlkdiscardWrapper()

  def blkid(args: String*) = clitools.BlkidWrapper(CmdArgs(args.toVector))
  def blkid = clitools.BlkidWrapper()

  def blkzone(args: String*) = clitools.BlkzoneWrapper(CmdArgs(args.toVector))
  def blkzone = clitools.BlkzoneWrapper()

  def blockdev(args: String*) = clitools.BlockdevWrapper(CmdArgs(args.toVector))
  def blockdev = clitools.BlockdevWrapper()

  def bootctl(args: String*) = clitools.BootctlWrapper(CmdArgs(args.toVector))
  def bootctl = clitools.BootctlWrapper()

  def bowtie2(args: String*) = clitools.Bowtie2Wrapper(CmdArgs(args.toVector))
  def bowtie2 = clitools.Bowtie2Wrapper()

  def bowtie2_align_l(args: String*) =
    clitools.Bowtie2_align_lWrapper(CmdArgs(args.toVector))
  def bowtie2_align_l = clitools.Bowtie2_align_lWrapper()

  def bowtie2_align_s(args: String*) =
    clitools.Bowtie2_align_sWrapper(CmdArgs(args.toVector))
  def bowtie2_align_s = clitools.Bowtie2_align_sWrapper()

  def bowtie2_build(args: String*) =
    clitools.Bowtie2_buildWrapper(CmdArgs(args.toVector))
  def bowtie2_build = clitools.Bowtie2_buildWrapper()

  def bowtie2_build_l(args: String*) =
    clitools.Bowtie2_build_lWrapper(CmdArgs(args.toVector))
  def bowtie2_build_l = clitools.Bowtie2_build_lWrapper()

  def bowtie2_build_s(args: String*) =
    clitools.Bowtie2_build_sWrapper(CmdArgs(args.toVector))
  def bowtie2_build_s = clitools.Bowtie2_build_sWrapper()

  def bowtie2_inspect(args: String*) =
    clitools.Bowtie2_inspectWrapper(CmdArgs(args.toVector))
  def bowtie2_inspect = clitools.Bowtie2_inspectWrapper()

  def bowtie2_inspect_l(args: String*) =
    clitools.Bowtie2_inspect_lWrapper(CmdArgs(args.toVector))
  def bowtie2_inspect_l = clitools.Bowtie2_inspect_lWrapper()

  def bowtie2_inspect_s(args: String*) =
    clitools.Bowtie2_inspect_sWrapper(CmdArgs(args.toVector))
  def bowtie2_inspect_s = clitools.Bowtie2_inspect_sWrapper()

  def bq(args: String*) = clitools.BqWrapper(CmdArgs(args.toVector))
  def bq = clitools.BqWrapper()

  def breeze_settings5(args: String*) =
    clitools.Breeze_settings5Wrapper(CmdArgs(args.toVector))
  def breeze_settings5 = clitools.Breeze_settings5Wrapper()

  def bridge(args: String*) = clitools.BridgeWrapper(CmdArgs(args.toVector))
  def bridge = clitools.BridgeWrapper()

  def btwisted(args: String*) = clitools.BtwistedWrapper(CmdArgs(args.toVector))
  def btwisted = clitools.BtwistedWrapper()

  def builtin(args: String*) = clitools.BuiltinWrapper(CmdArgs(args.toVector))
  def builtin = clitools.BuiltinWrapper()

  def bunzip2(args: String*) = clitools.Bunzip2Wrapper(CmdArgs(args.toVector))
  def bunzip2 = clitools.Bunzip2Wrapper()

  def busctl(args: String*) = clitools.BusctlWrapper(CmdArgs(args.toVector))
  def busctl = clitools.BusctlWrapper()

  def bwa(args: String*) = clitools.BwaWrapper(CmdArgs(args.toVector))
  def bwa = clitools.BwaWrapper()

  def bzcat(args: String*) = clitools.BzcatWrapper(CmdArgs(args.toVector))
  def bzcat = clitools.BzcatWrapper()

  def bzcmp(args: String*) = clitools.BzcmpWrapper(CmdArgs(args.toVector))
  def bzcmp = clitools.BzcmpWrapper()

  def bzdiff(args: String*) = clitools.BzdiffWrapper(CmdArgs(args.toVector))
  def bzdiff = clitools.BzdiffWrapper()

  def bzegrep(args: String*) = clitools.BzegrepWrapper(CmdArgs(args.toVector))
  def bzegrep = clitools.BzegrepWrapper()

  def bzfgrep(args: String*) = clitools.BzfgrepWrapper(CmdArgs(args.toVector))
  def bzfgrep = clitools.BzfgrepWrapper()

  def bzgrep(args: String*) = clitools.BzgrepWrapper(CmdArgs(args.toVector))
  def bzgrep = clitools.BzgrepWrapper()

  def bzip2(args: String*) = clitools.Bzip2Wrapper(CmdArgs(args.toVector))
  def bzip2 = clitools.Bzip2Wrapper()

  def bzip2recover(args: String*) =
    clitools.Bzip2recoverWrapper(CmdArgs(args.toVector))
  def bzip2recover = clitools.Bzip2recoverWrapper()

  def bzless(args: String*) = clitools.BzlessWrapper(CmdArgs(args.toVector))
  def bzless = clitools.BzlessWrapper()

  def bzmore(args: String*) = clitools.BzmoreWrapper(CmdArgs(args.toVector))
  def bzmore = clitools.BzmoreWrapper()

  def cachedas(args: String*) = clitools.CachedasWrapper(CmdArgs(args.toVector))
  def cachedas = clitools.CachedasWrapper()

  def cachedbfetch(args: String*) =
    clitools.CachedbfetchWrapper(CmdArgs(args.toVector))
  def cachedbfetch = clitools.CachedbfetchWrapper()

  def cacheebeyesearch(args: String*) =
    clitools.CacheebeyesearchWrapper(CmdArgs(args.toVector))
  def cacheebeyesearch = clitools.CacheebeyesearchWrapper()

  def cacheensembl(args: String*) =
    clitools.CacheensemblWrapper(CmdArgs(args.toVector))
  def cacheensembl = clitools.CacheensemblWrapper()

  def cai(args: String*) = clitools.CaiWrapper(CmdArgs(args.toVector))
  def cai = clitools.CaiWrapper()

  def cal(args: String*) = clitools.CalWrapper(CmdArgs(args.toVector))
  def cal = clitools.CalWrapper()

  def caller(args: String*) = clitools.CallerWrapper(CmdArgs(args.toVector))
  def caller = clitools.CallerWrapper()

  def cancel(args: String*) = clitools.CancelWrapper(CmdArgs(args.toVector))
  def cancel = clitools.CancelWrapper()

  def capsh(args: String*) = clitools.CapshWrapper(CmdArgs(args.toVector))
  def capsh = clitools.CapshWrapper()

  def captoinfo(args: String*) =
    clitools.CaptoinfoWrapper(CmdArgs(args.toVector))
  def captoinfo = clitools.CaptoinfoWrapper()

  def cat(args: String*) = clitools.CatWrapper(CmdArgs(args.toVector))
  def cat = clitools.CatWrapper()

  def catchsegv(args: String*) =
    clitools.CatchsegvWrapper(CmdArgs(args.toVector))
  def catchsegv = clitools.CatchsegvWrapper()

  def catman(args: String*) = clitools.CatmanWrapper(CmdArgs(args.toVector))
  def catman = clitools.CatmanWrapper()

  def cc(args: String*) = clitools.CcWrapper(CmdArgs(args.toVector))
  def cc = clitools.CcWrapper()

  def ccomps(args: String*) = clitools.CcompsWrapper(CmdArgs(args.toVector))
  def ccomps = clitools.CcompsWrapper()

  def cd(args: String*) = clitools.CdWrapper(CmdArgs(args.toVector))
  def cd = clitools.CdWrapper()

  def cfdisk(args: String*) = clitools.CfdiskWrapper(CmdArgs(args.toVector))
  def cfdisk = clitools.CfdiskWrapper()

  def chacl(args: String*) = clitools.ChaclWrapper(CmdArgs(args.toVector))
  def chacl = clitools.ChaclWrapper()

  def chage(args: String*) = clitools.ChageWrapper(CmdArgs(args.toVector))
  def chage = clitools.ChageWrapper()

  def chaos(args: String*) = clitools.ChaosWrapper(CmdArgs(args.toVector))
  def chaos = clitools.ChaosWrapper()

  def charge(args: String*) = clitools.ChargeWrapper(CmdArgs(args.toVector))
  def charge = clitools.ChargeWrapper()

  def chattr(args: String*) = clitools.ChattrWrapper(CmdArgs(args.toVector))
  def chattr = clitools.ChattrWrapper()

  def chcon(args: String*) = clitools.ChconWrapper(CmdArgs(args.toVector))
  def chcon = clitools.ChconWrapper()

  def chcpu(args: String*) = clitools.ChcpuWrapper(CmdArgs(args.toVector))
  def chcpu = clitools.ChcpuWrapper()

  def checkxml5(args: String*) =
    clitools.CheckXML5Wrapper(CmdArgs(args.toVector))
  def checkxml5 = clitools.CheckXML5Wrapper()

  def checktrans(args: String*) =
    clitools.ChecktransWrapper(CmdArgs(args.toVector))
  def checktrans = clitools.ChecktransWrapper()

  def chfn(args: String*) = clitools.ChfnWrapper(CmdArgs(args.toVector))
  def chfn = clitools.ChfnWrapper()

  def chgpasswd(args: String*) =
    clitools.ChgpasswdWrapper(CmdArgs(args.toVector))
  def chgpasswd = clitools.ChgpasswdWrapper()

  def chgrp(args: String*) = clitools.ChgrpWrapper(CmdArgs(args.toVector))
  def chgrp = clitools.ChgrpWrapper()

  def chips(args: String*) = clitools.ChipsWrapper(CmdArgs(args.toVector))
  def chips = clitools.ChipsWrapper()

  def chmem(args: String*) = clitools.ChmemWrapper(CmdArgs(args.toVector))
  def chmem = clitools.ChmemWrapper()

  def chmod(args: String*) = clitools.ChmodWrapper(CmdArgs(args.toVector))
  def chmod = clitools.ChmodWrapper()

  def choom(args: String*) = clitools.ChoomWrapper(CmdArgs(args.toVector))
  def choom = clitools.ChoomWrapper()

  def chown(args: String*) = clitools.ChownWrapper(CmdArgs(args.toVector))
  def chown = clitools.ChownWrapper()

  def chpasswd(args: String*) = clitools.ChpasswdWrapper(CmdArgs(args.toVector))
  def chpasswd = clitools.ChpasswdWrapper()

  def chromedriver(args: String*) =
    clitools.ChromedriverWrapper(CmdArgs(args.toVector))
  def chromedriver = clitools.ChromedriverWrapper()

  def chromium(args: String*) = clitools.ChromiumWrapper(CmdArgs(args.toVector))
  def chromium = clitools.ChromiumWrapper()

  def chromium_browser(args: String*) =
    clitools.Chromium_browserWrapper(CmdArgs(args.toVector))
  def chromium_browser = clitools.Chromium_browserWrapper()

  def chroot(args: String*) = clitools.ChrootWrapper(CmdArgs(args.toVector))
  def chroot = clitools.ChrootWrapper()

  def chrt(args: String*) = clitools.ChrtWrapper(CmdArgs(args.toVector))
  def chrt = clitools.ChrtWrapper()

  def chsh(args: String*) = clitools.ChshWrapper(CmdArgs(args.toVector))
  def chsh = clitools.ChshWrapper()

  def chvt(args: String*) = clitools.ChvtWrapper(CmdArgs(args.toVector))
  def chvt = clitools.ChvtWrapper()

  def cifscreds(args: String*) =
    clitools.CifscredsWrapper(CmdArgs(args.toVector))
  def cifscreds = clitools.CifscredsWrapper()

  def circo(args: String*) = clitools.CircoWrapper(CmdArgs(args.toVector))
  def circo = clitools.CircoWrapper()

  def cirdna(args: String*) = clitools.CirdnaWrapper(CmdArgs(args.toVector))
  def cirdna = clitools.CirdnaWrapper()

  def cisco_decrypt(args: String*) =
    clitools.Cisco_decryptWrapper(CmdArgs(args.toVector))
  def cisco_decrypt = clitools.Cisco_decryptWrapper()

  def cksum(args: String*) = clitools.CksumWrapper(CmdArgs(args.toVector))
  def cksum = clitools.CksumWrapper()

  def clear(args: String*) = clitools.ClearWrapper(CmdArgs(args.toVector))
  def clear = clitools.ClearWrapper()

  def clockdiff(args: String*) =
    clitools.ClockdiffWrapper(CmdArgs(args.toVector))
  def clockdiff = clitools.ClockdiffWrapper()

  def closestbed(args: String*) =
    clitools.ClosestBedWrapper(CmdArgs(args.toVector))
  def closestbed = clitools.ClosestBedWrapper()

  def clrunimap(args: String*) =
    clitools.ClrunimapWrapper(CmdArgs(args.toVector))
  def clrunimap = clitools.ClrunimapWrapper()

  def cluster(args: String*) = clitools.ClusterWrapper(CmdArgs(args.toVector))
  def cluster = clitools.ClusterWrapper()

  def clusterbed(args: String*) =
    clitools.ClusterBedWrapper(CmdArgs(args.toVector))
  def clusterbed = clitools.ClusterBedWrapper()

  def cmp(args: String*) = clitools.CmpWrapper(CmdArgs(args.toVector))
  def cmp = clitools.CmpWrapper()

  def codcmp(args: String*) = clitools.CodcmpWrapper(CmdArgs(args.toVector))
  def codcmp = clitools.CodcmpWrapper()

  def codcopy(args: String*) = clitools.CodcopyWrapper(CmdArgs(args.toVector))
  def codcopy = clitools.CodcopyWrapper()

  def code(args: String*) = clitools.CodeWrapper(CmdArgs(args.toVector))
  def code = clitools.CodeWrapper()

  def coderet(args: String*) = clitools.CoderetWrapper(CmdArgs(args.toVector))
  def coderet = clitools.CoderetWrapper()

  def col(args: String*) = clitools.ColWrapper(CmdArgs(args.toVector))
  def col = clitools.ColWrapper()

  def colcrt(args: String*) = clitools.ColcrtWrapper(CmdArgs(args.toVector))
  def colcrt = clitools.ColcrtWrapper()

  def colrm(args: String*) = clitools.ColrmWrapper(CmdArgs(args.toVector))
  def colrm = clitools.ColrmWrapper()

  def column(args: String*) = clitools.ColumnWrapper(CmdArgs(args.toVector))
  def column = clitools.ColumnWrapper()

  def comm(args: String*) = clitools.CommWrapper(CmdArgs(args.toVector))
  def comm = clitools.CommWrapper()

  def command(args: String*) = clitools.CommandWrapper(CmdArgs(args.toVector))
  def command = clitools.CommandWrapper()

  def command_not_found(args: String*) =
    clitools.Command_not_foundWrapper(CmdArgs(args.toVector))
  def command_not_found = clitools.Command_not_foundWrapper()

  def compare(args: String*) = clitools.CompareWrapper(CmdArgs(args.toVector))
  def compare = clitools.CompareWrapper()

  def compile_et(args: String*) =
    clitools.Compile_etWrapper(CmdArgs(args.toVector))
  def compile_et = clitools.Compile_etWrapper()

  def complementbed(args: String*) =
    clitools.ComplementBedWrapper(CmdArgs(args.toVector))
  def complementbed = clitools.ComplementBedWrapper()

  def composite(args: String*) =
    clitools.CompositeWrapper(CmdArgs(args.toVector))
  def composite = clitools.CompositeWrapper()

  def compseq(args: String*) = clitools.CompseqWrapper(CmdArgs(args.toVector))
  def compseq = clitools.CompseqWrapper()

  def configure_printer(args: String*) =
    clitools.Configure_printerWrapper(CmdArgs(args.toVector))
  def configure_printer = clitools.Configure_printerWrapper()

  def conjure(args: String*) = clitools.ConjureWrapper(CmdArgs(args.toVector))
  def conjure = clitools.ConjureWrapper()

  def cons(args: String*) = clitools.ConsWrapper(CmdArgs(args.toVector))
  def cons = clitools.ConsWrapper()

  def consambig(args: String*) =
    clitools.ConsambigWrapper(CmdArgs(args.toVector))
  def consambig = clitools.ConsambigWrapper()

  def convert(args: String*) = clitools.ConvertWrapper(CmdArgs(args.toVector))
  def convert = clitools.ConvertWrapper()

  def coredumpctl(args: String*) =
    clitools.CoredumpctlWrapper(CmdArgs(args.toVector))
  def coredumpctl = clitools.CoredumpctlWrapper()

  def corelist(args: String*) = clitools.CorelistWrapper(CmdArgs(args.toVector))
  def corelist = clitools.CorelistWrapper()

  def coreutils(args: String*) =
    clitools.CoreutilsWrapper(CmdArgs(args.toVector))
  def coreutils = clitools.CoreutilsWrapper()

  def coveragebed(args: String*) =
    clitools.CoverageBedWrapper(CmdArgs(args.toVector))
  def coveragebed = clitools.CoverageBedWrapper()

  def cp(args: String*) = clitools.CpWrapper(CmdArgs(args.toVector))
  def cp = clitools.CpWrapper()

  def cpan(args: String*) = clitools.CpanWrapper(CmdArgs(args.toVector))
  def cpan = clitools.CpanWrapper()

  def cpgplot(args: String*) = clitools.CpgplotWrapper(CmdArgs(args.toVector))
  def cpgplot = clitools.CpgplotWrapper()

  def cpgreport(args: String*) =
    clitools.CpgreportWrapper(CmdArgs(args.toVector))
  def cpgreport = clitools.CpgreportWrapper()

  def cpio(args: String*) = clitools.CpioWrapper(CmdArgs(args.toVector))
  def cpio = clitools.CpioWrapper()

  def cpp(args: String*) = clitools.CppWrapper(CmdArgs(args.toVector))
  def cpp = clitools.CppWrapper()

  def cpufreq_bench(args: String*) =
    clitools.Cpufreq_benchWrapper(CmdArgs(args.toVector))
  def cpufreq_bench = clitools.Cpufreq_benchWrapper()

  def cpupower(args: String*) = clitools.CpupowerWrapper(CmdArgs(args.toVector))
  def cpupower = clitools.CpupowerWrapper()

  def crda(args: String*) = clitools.CrdaWrapper(CmdArgs(args.toVector))
  def crda = clitools.CrdaWrapper()

  def cryptsetup(args: String*) =
    clitools.CryptsetupWrapper(CmdArgs(args.toVector))
  def cryptsetup = clitools.CryptsetupWrapper()

  def cryptsetup_reencrypt(args: String*) =
    clitools.Cryptsetup_reencryptWrapper(CmdArgs(args.toVector))
  def cryptsetup_reencrypt = clitools.Cryptsetup_reencryptWrapper()

  def csplit(args: String*) = clitools.CsplitWrapper(CmdArgs(args.toVector))
  def csplit = clitools.CsplitWrapper()

  def ctags(args: String*) = clitools.CtagsWrapper(CmdArgs(args.toVector))
  def ctags = clitools.CtagsWrapper()

  def ctrlaltdel(args: String*) =
    clitools.CtrlaltdelWrapper(CmdArgs(args.toVector))
  def ctrlaltdel = clitools.CtrlaltdelWrapper()

  def ctstat(args: String*) = clitools.CtstatWrapper(CmdArgs(args.toVector))
  def ctstat = clitools.CtstatWrapper()

  def cupsaccept(args: String*) =
    clitools.CupsacceptWrapper(CmdArgs(args.toVector))
  def cupsaccept = clitools.CupsacceptWrapper()

  def cupsaddsmb(args: String*) =
    clitools.CupsaddsmbWrapper(CmdArgs(args.toVector))
  def cupsaddsmb = clitools.CupsaddsmbWrapper()

  def cupsctl(args: String*) = clitools.CupsctlWrapper(CmdArgs(args.toVector))
  def cupsctl = clitools.CupsctlWrapper()

  def cupsd(args: String*) = clitools.CupsdWrapper(CmdArgs(args.toVector))
  def cupsd = clitools.CupsdWrapper()

  def cupsdisable(args: String*) =
    clitools.CupsdisableWrapper(CmdArgs(args.toVector))
  def cupsdisable = clitools.CupsdisableWrapper()

  def cupsenable(args: String*) =
    clitools.CupsenableWrapper(CmdArgs(args.toVector))
  def cupsenable = clitools.CupsenableWrapper()

  def cupsfilter(args: String*) =
    clitools.CupsfilterWrapper(CmdArgs(args.toVector))
  def cupsfilter = clitools.CupsfilterWrapper()

  def cupsreject(args: String*) =
    clitools.CupsrejectWrapper(CmdArgs(args.toVector))
  def cupsreject = clitools.CupsrejectWrapper()

  def cupstestdsc(args: String*) =
    clitools.CupstestdscWrapper(CmdArgs(args.toVector))
  def cupstestdsc = clitools.CupstestdscWrapper()

  def cupstestppd(args: String*) =
    clitools.CupstestppdWrapper(CmdArgs(args.toVector))
  def cupstestppd = clitools.CupstestppdWrapper()

  def curl(args: String*) = clitools.CurlWrapper(CmdArgs(args.toVector))
  def curl = clitools.CurlWrapper()

  def cusp(args: String*) = clitools.CuspWrapper(CmdArgs(args.toVector))
  def cusp = clitools.CuspWrapper()

  def cut(args: String*) = clitools.CutWrapper(CmdArgs(args.toVector))
  def cut = clitools.CutWrapper()

  def cutgextract(args: String*) =
    clitools.CutgextractWrapper(CmdArgs(args.toVector))
  def cutgextract = clitools.CutgextractWrapper()

  def cutseq(args: String*) = clitools.CutseqWrapper(CmdArgs(args.toVector))
  def cutseq = clitools.CutseqWrapper()

  def cvlc(args: String*) = clitools.CvlcWrapper(CmdArgs(args.toVector))
  def cvlc = clitools.CvlcWrapper()

  def cvt(args: String*) = clitools.CvtWrapper(CmdArgs(args.toVector))
  def cvt = clitools.CvtWrapper()

  def cvtsudoers(args: String*) =
    clitools.CvtsudoersWrapper(CmdArgs(args.toVector))
  def cvtsudoers = clitools.CvtsudoersWrapper()

  def dan(args: String*) = clitools.DanWrapper(CmdArgs(args.toVector))
  def dan = clitools.DanWrapper()

  def date(args: String*) = clitools.DateWrapper(CmdArgs(args.toVector))
  def date = clitools.DateWrapper()

  def dbiblast(args: String*) = clitools.DbiblastWrapper(CmdArgs(args.toVector))
  def dbiblast = clitools.DbiblastWrapper()

  def dbifasta(args: String*) = clitools.DbifastaWrapper(CmdArgs(args.toVector))
  def dbifasta = clitools.DbifastaWrapper()

  def dbiflat(args: String*) = clitools.DbiflatWrapper(CmdArgs(args.toVector))
  def dbiflat = clitools.DbiflatWrapper()

  def dbigcg(args: String*) = clitools.DbigcgWrapper(CmdArgs(args.toVector))
  def dbigcg = clitools.DbigcgWrapper()

  def dbtell(args: String*) = clitools.DbtellWrapper(CmdArgs(args.toVector))
  def dbtell = clitools.DbtellWrapper()

  def dbus_cleanup_sockets(args: String*) =
    clitools.Dbus_cleanup_socketsWrapper(CmdArgs(args.toVector))
  def dbus_cleanup_sockets = clitools.Dbus_cleanup_socketsWrapper()

  def dbus_daemon(args: String*) =
    clitools.Dbus_daemonWrapper(CmdArgs(args.toVector))
  def dbus_daemon = clitools.Dbus_daemonWrapper()

  def dbus_launch(args: String*) =
    clitools.Dbus_launchWrapper(CmdArgs(args.toVector))
  def dbus_launch = clitools.Dbus_launchWrapper()

  def dbus_monitor(args: String*) =
    clitools.Dbus_monitorWrapper(CmdArgs(args.toVector))
  def dbus_monitor = clitools.Dbus_monitorWrapper()

  def dbus_run_session(args: String*) =
    clitools.Dbus_run_sessionWrapper(CmdArgs(args.toVector))
  def dbus_run_session = clitools.Dbus_run_sessionWrapper()

  def dbus_send(args: String*) =
    clitools.Dbus_sendWrapper(CmdArgs(args.toVector))
  def dbus_send = clitools.Dbus_sendWrapper()

  def dbus_test_tool(args: String*) =
    clitools.Dbus_test_toolWrapper(CmdArgs(args.toVector))
  def dbus_test_tool = clitools.Dbus_test_toolWrapper()

  def dbus_update_activation_environment(args: String*) =
    clitools.Dbus_update_activation_environmentWrapper(CmdArgs(args.toVector))
  def dbus_update_activation_environment =
    clitools.Dbus_update_activation_environmentWrapper()

  def dbus_uuidgen(args: String*) =
    clitools.Dbus_uuidgenWrapper(CmdArgs(args.toVector))
  def dbus_uuidgen = clitools.Dbus_uuidgenWrapper()

  def dbxcompress(args: String*) =
    clitools.DbxcompressWrapper(CmdArgs(args.toVector))
  def dbxcompress = clitools.DbxcompressWrapper()

  def dbxedam(args: String*) = clitools.DbxedamWrapper(CmdArgs(args.toVector))
  def dbxedam = clitools.DbxedamWrapper()

  def dbxfasta(args: String*) = clitools.DbxfastaWrapper(CmdArgs(args.toVector))
  def dbxfasta = clitools.DbxfastaWrapper()

  def dbxflat(args: String*) = clitools.DbxflatWrapper(CmdArgs(args.toVector))
  def dbxflat = clitools.DbxflatWrapper()

  def dbxgcg(args: String*) = clitools.DbxgcgWrapper(CmdArgs(args.toVector))
  def dbxgcg = clitools.DbxgcgWrapper()

  def dbxobo(args: String*) = clitools.DbxoboWrapper(CmdArgs(args.toVector))
  def dbxobo = clitools.DbxoboWrapper()

  def dbxreport(args: String*) =
    clitools.DbxreportWrapper(CmdArgs(args.toVector))
  def dbxreport = clitools.DbxreportWrapper()

  def dbxresource(args: String*) =
    clitools.DbxresourceWrapper(CmdArgs(args.toVector))
  def dbxresource = clitools.DbxresourceWrapper()

  def dbxstat(args: String*) = clitools.DbxstatWrapper(CmdArgs(args.toVector))
  def dbxstat = clitools.DbxstatWrapper()

  def dbxtax(args: String*) = clitools.DbxtaxWrapper(CmdArgs(args.toVector))
  def dbxtax = clitools.DbxtaxWrapper()

  def dbxuncompress(args: String*) =
    clitools.DbxuncompressWrapper(CmdArgs(args.toVector))
  def dbxuncompress = clitools.DbxuncompressWrapper()

  def dd(args: String*) = clitools.DdWrapper(CmdArgs(args.toVector))
  def dd = clitools.DdWrapper()

  def deallocvt(args: String*) =
    clitools.DeallocvtWrapper(CmdArgs(args.toVector))
  def deallocvt = clitools.DeallocvtWrapper()

  def debootstrap(args: String*) =
    clitools.DebootstrapWrapper(CmdArgs(args.toVector))
  def debootstrap = clitools.DebootstrapWrapper()

  def debugfs(args: String*) = clitools.DebugfsWrapper(CmdArgs(args.toVector))
  def debugfs = clitools.DebugfsWrapper()

  def declare(args: String*) = clitools.DeclareWrapper(CmdArgs(args.toVector))
  def declare = clitools.DeclareWrapper()

  def degapseq(args: String*) = clitools.DegapseqWrapper(CmdArgs(args.toVector))
  def degapseq = clitools.DegapseqWrapper()

  def delpart(args: String*) = clitools.DelpartWrapper(CmdArgs(args.toVector))
  def delpart = clitools.DelpartWrapper()

  def density(args: String*) = clitools.DensityWrapper(CmdArgs(args.toVector))
  def density = clitools.DensityWrapper()

  def depmod(args: String*) = clitools.DepmodWrapper(CmdArgs(args.toVector))
  def depmod = clitools.DepmodWrapper()

  def descseq(args: String*) = clitools.DescseqWrapper(CmdArgs(args.toVector))
  def descseq = clitools.DescseqWrapper()

  def desktoptojson(args: String*) =
    clitools.DesktoptojsonWrapper(CmdArgs(args.toVector))
  def desktoptojson = clitools.DesktoptojsonWrapper()

  def df(args: String*) = clitools.DfWrapper(CmdArgs(args.toVector))
  def df = clitools.DfWrapper()

  def dhcpcd(args: String*) = clitools.DhcpcdWrapper(CmdArgs(args.toVector))
  def dhcpcd = clitools.DhcpcdWrapper()

  def dhex(args: String*) = clitools.DhexWrapper(CmdArgs(args.toVector))
  def dhex = clitools.DhexWrapper()

  def diamond(args: String*) = clitools.DiamondWrapper(CmdArgs(args.toVector))
  def diamond = clitools.DiamondWrapper()

  def diff(args: String*) = clitools.DiffWrapper(CmdArgs(args.toVector))
  def diff = clitools.DiffWrapper()

  def diff3(args: String*) = clitools.Diff3Wrapper(CmdArgs(args.toVector))
  def diff3 = clitools.Diff3Wrapper()

  def diffimg(args: String*) = clitools.DiffimgWrapper(CmdArgs(args.toVector))
  def diffimg = clitools.DiffimgWrapper()

  def diffseq(args: String*) = clitools.DiffseqWrapper(CmdArgs(args.toVector))
  def diffseq = clitools.DiffseqWrapper()

  def dijkstra(args: String*) = clitools.DijkstraWrapper(CmdArgs(args.toVector))
  def dijkstra = clitools.DijkstraWrapper()

  def dir(args: String*) = clitools.DirWrapper(CmdArgs(args.toVector))
  def dir = clitools.DirWrapper()

  def dircolors(args: String*) =
    clitools.DircolorsWrapper(CmdArgs(args.toVector))
  def dircolors = clitools.DircolorsWrapper()

  def dirmngr(args: String*) = clitools.DirmngrWrapper(CmdArgs(args.toVector))
  def dirmngr = clitools.DirmngrWrapper()

  def dirmngr_client(args: String*) =
    clitools.Dirmngr_clientWrapper(CmdArgs(args.toVector))
  def dirmngr_client = clitools.Dirmngr_clientWrapper()

  def dirname(args: String*) = clitools.DirnameWrapper(CmdArgs(args.toVector))
  def dirname = clitools.DirnameWrapper()

  def display(args: String*) = clitools.DisplayWrapper(CmdArgs(args.toVector))
  def display = clitools.DisplayWrapper()

  def distmat(args: String*) = clitools.DistmatWrapper(CmdArgs(args.toVector))
  def distmat = clitools.DistmatWrapper()

  def dmesg(args: String*) = clitools.DmesgWrapper(CmdArgs(args.toVector))
  def dmesg = clitools.DmesgWrapper()

  def dmsetup(args: String*) = clitools.DmsetupWrapper(CmdArgs(args.toVector))
  def dmsetup = clitools.DmsetupWrapper()

  def dmstats(args: String*) = clitools.DmstatsWrapper(CmdArgs(args.toVector))
  def dmstats = clitools.DmstatsWrapper()

  def dnsdomainname(args: String*) =
    clitools.DnsdomainnameWrapper(CmdArgs(args.toVector))
  def dnsdomainname = clitools.DnsdomainnameWrapper()

  def docker(args: String*) = clitools.DockerWrapper(CmdArgs(args.toVector))
  def docker = clitools.DockerWrapper()

  def docker_credential_gcloud(args: String*) =
    clitools.Docker_credential_gcloudWrapper(CmdArgs(args.toVector))
  def docker_credential_gcloud = clitools.Docker_credential_gcloudWrapper()

  def dockerd(args: String*) = clitools.DockerdWrapper(CmdArgs(args.toVector))
  def dockerd = clitools.DockerdWrapper()

  def dolphin(args: String*) = clitools.DolphinWrapper(CmdArgs(args.toVector))
  def dolphin = clitools.DolphinWrapper()

  def domainname(args: String*) =
    clitools.DomainnameWrapper(CmdArgs(args.toVector))
  def domainname = clitools.DomainnameWrapper()

  def dosfsck(args: String*) = clitools.DosfsckWrapper(CmdArgs(args.toVector))
  def dosfsck = clitools.DosfsckWrapper()

  def dosfslabel(args: String*) =
    clitools.DosfslabelWrapper(CmdArgs(args.toVector))
  def dosfslabel = clitools.DosfslabelWrapper()

  def dot(args: String*) = clitools.DotWrapper(CmdArgs(args.toVector))
  def dot = clitools.DotWrapper()

  def dot2gxl(args: String*) = clitools.Dot2gxlWrapper(CmdArgs(args.toVector))
  def dot2gxl = clitools.Dot2gxlWrapper()

  def dot_builtins(args: String*) =
    clitools.Dot_builtinsWrapper(CmdArgs(args.toVector))
  def dot_builtins = clitools.Dot_builtinsWrapper()

  def dotmatcher(args: String*) =
    clitools.DotmatcherWrapper(CmdArgs(args.toVector))
  def dotmatcher = clitools.DotmatcherWrapper()

  def dotpath(args: String*) = clitools.DotpathWrapper(CmdArgs(args.toVector))
  def dotpath = clitools.DotpathWrapper()

  def dottup(args: String*) = clitools.DottupWrapper(CmdArgs(args.toVector))
  def dottup = clitools.DottupWrapper()

  def dotty(args: String*) = clitools.DottyWrapper(CmdArgs(args.toVector))
  def dotty = clitools.DottyWrapper()

  def dreg(args: String*) = clitools.DregWrapper(CmdArgs(args.toVector))
  def dreg = clitools.DregWrapper()

  def drfinddata(args: String*) =
    clitools.DrfinddataWrapper(CmdArgs(args.toVector))
  def drfinddata = clitools.DrfinddataWrapper()

  def drfindformat(args: String*) =
    clitools.DrfindformatWrapper(CmdArgs(args.toVector))
  def drfindformat = clitools.DrfindformatWrapper()

  def drfindid(args: String*) = clitools.DrfindidWrapper(CmdArgs(args.toVector))
  def drfindid = clitools.DrfindidWrapper()

  def drfindresource(args: String*) =
    clitools.DrfindresourceWrapper(CmdArgs(args.toVector))
  def drfindresource = clitools.DrfindresourceWrapper()

  def drget(args: String*) = clitools.DrgetWrapper(CmdArgs(args.toVector))
  def drget = clitools.DrgetWrapper()

  def drtext(args: String*) = clitools.DrtextWrapper(CmdArgs(args.toVector))
  def drtext = clitools.DrtextWrapper()

  def du(args: String*) = clitools.DuWrapper(CmdArgs(args.toVector))
  def du = clitools.DuWrapper()

  def dumpe2fs(args: String*) = clitools.Dumpe2fsWrapper(CmdArgs(args.toVector))
  def dumpe2fs = clitools.Dumpe2fsWrapper()

  def dumpkeys(args: String*) = clitools.DumpkeysWrapper(CmdArgs(args.toVector))
  def dumpkeys = clitools.DumpkeysWrapper()

  def dvipdf(args: String*) = clitools.DvipdfWrapper(CmdArgs(args.toVector))
  def dvipdf = clitools.DvipdfWrapper()

  def e2freefrag(args: String*) =
    clitools.E2freefragWrapper(CmdArgs(args.toVector))
  def e2freefrag = clitools.E2freefragWrapper()

  def e2fsck(args: String*) = clitools.E2fsckWrapper(CmdArgs(args.toVector))
  def e2fsck = clitools.E2fsckWrapper()

  def e2image(args: String*) = clitools.E2imageWrapper(CmdArgs(args.toVector))
  def e2image = clitools.E2imageWrapper()

  def e2label(args: String*) = clitools.E2labelWrapper(CmdArgs(args.toVector))
  def e2label = clitools.E2labelWrapper()

  def e2mmpstatus(args: String*) =
    clitools.E2mmpstatusWrapper(CmdArgs(args.toVector))
  def e2mmpstatus = clitools.E2mmpstatusWrapper()

  def e2scrub(args: String*) = clitools.E2scrubWrapper(CmdArgs(args.toVector))
  def e2scrub = clitools.E2scrubWrapper()

  def e2scrub_all(args: String*) =
    clitools.E2scrub_allWrapper(CmdArgs(args.toVector))
  def e2scrub_all = clitools.E2scrub_allWrapper()

  def e2undo(args: String*) = clitools.E2undoWrapper(CmdArgs(args.toVector))
  def e2undo = clitools.E2undoWrapper()

  def e4crypt(args: String*) = clitools.E4cryptWrapper(CmdArgs(args.toVector))
  def e4crypt = clitools.E4cryptWrapper()

  def e4defrag(args: String*) = clitools.E4defragWrapper(CmdArgs(args.toVector))
  def e4defrag = clitools.E4defragWrapper()

  def ebtables(args: String*) = clitools.EbtablesWrapper(CmdArgs(args.toVector))
  def ebtables = clitools.EbtablesWrapper()

  def ebtables_nft(args: String*) =
    clitools.Ebtables_nftWrapper(CmdArgs(args.toVector))
  def ebtables_nft = clitools.Ebtables_nftWrapper()

  def ebtables_nft_restore(args: String*) =
    clitools.Ebtables_nft_restoreWrapper(CmdArgs(args.toVector))
  def ebtables_nft_restore = clitools.Ebtables_nft_restoreWrapper()

  def ebtables_nft_save(args: String*) =
    clitools.Ebtables_nft_saveWrapper(CmdArgs(args.toVector))
  def ebtables_nft_save = clitools.Ebtables_nft_saveWrapper()

  def ebtables_restore(args: String*) =
    clitools.Ebtables_restoreWrapper(CmdArgs(args.toVector))
  def ebtables_restore = clitools.Ebtables_restoreWrapper()

  def ebtables_save(args: String*) =
    clitools.Ebtables_saveWrapper(CmdArgs(args.toVector))
  def ebtables_save = clitools.Ebtables_saveWrapper()

  def echo(args: String*) = clitools.EchoWrapper(CmdArgs(args.toVector))
  def echo = clitools.EchoWrapper()

  def edamdef(args: String*) = clitools.EdamdefWrapper(CmdArgs(args.toVector))
  def edamdef = clitools.EdamdefWrapper()

  def edamhasinput(args: String*) =
    clitools.EdamhasinputWrapper(CmdArgs(args.toVector))
  def edamhasinput = clitools.EdamhasinputWrapper()

  def edamhasoutput(args: String*) =
    clitools.EdamhasoutputWrapper(CmdArgs(args.toVector))
  def edamhasoutput = clitools.EdamhasoutputWrapper()

  def edamisformat(args: String*) =
    clitools.EdamisformatWrapper(CmdArgs(args.toVector))
  def edamisformat = clitools.EdamisformatWrapper()

  def edamisid(args: String*) = clitools.EdamisidWrapper(CmdArgs(args.toVector))
  def edamisid = clitools.EdamisidWrapper()

  def edamname(args: String*) = clitools.EdamnameWrapper(CmdArgs(args.toVector))
  def edamname = clitools.EdamnameWrapper()

  def edgepaint(args: String*) =
    clitools.EdgepaintWrapper(CmdArgs(args.toVector))
  def edgepaint = clitools.EdgepaintWrapper()

  def edialign(args: String*) = clitools.EdialignWrapper(CmdArgs(args.toVector))
  def edialign = clitools.EdialignWrapper()

  def egrep(args: String*) = clitools.EgrepWrapper(CmdArgs(args.toVector))
  def egrep = clitools.EgrepWrapper()

  def einverted(args: String*) =
    clitools.EinvertedWrapper(CmdArgs(args.toVector))
  def einverted = clitools.EinvertedWrapper()

  def eject(args: String*) = clitools.EjectWrapper(CmdArgs(args.toVector))
  def eject = clitools.EjectWrapper()

  def embossdata(args: String*) =
    clitools.EmbossdataWrapper(CmdArgs(args.toVector))
  def embossdata = clitools.EmbossdataWrapper()

  def embossupdate(args: String*) =
    clitools.EmbossupdateWrapper(CmdArgs(args.toVector))
  def embossupdate = clitools.EmbossupdateWrapper()

  def embossversion(args: String*) =
    clitools.EmbossversionWrapper(CmdArgs(args.toVector))
  def embossversion = clitools.EmbossversionWrapper()

  def emma(args: String*) = clitools.EmmaWrapper(CmdArgs(args.toVector))
  def emma = clitools.EmmaWrapper()

  def emowse(args: String*) = clitools.EmowseWrapper(CmdArgs(args.toVector))
  def emowse = clitools.EmowseWrapper()

  def enable(args: String*) = clitools.EnableWrapper(CmdArgs(args.toVector))
  def enable = clitools.EnableWrapper()

  def enc2xs(args: String*) = clitools.Enc2xsWrapper(CmdArgs(args.toVector))
  def enc2xs = clitools.Enc2xsWrapper()

  def encguess(args: String*) = clitools.EncguessWrapper(CmdArgs(args.toVector))
  def encguess = clitools.EncguessWrapper()

  def entret(args: String*) = clitools.EntretWrapper(CmdArgs(args.toVector))
  def entret = clitools.EntretWrapper()

  def env(args: String*) = clitools.EnvWrapper(CmdArgs(args.toVector))
  def env = clitools.EnvWrapper()

  def epestfind(args: String*) =
    clitools.EpestfindWrapper(CmdArgs(args.toVector))
  def epestfind = clitools.EpestfindWrapper()

  def eprimer3(args: String*) = clitools.Eprimer3Wrapper(CmdArgs(args.toVector))
  def eprimer3 = clitools.Eprimer3Wrapper()

  def eprimer32(args: String*) =
    clitools.Eprimer32Wrapper(CmdArgs(args.toVector))
  def eprimer32 = clitools.Eprimer32Wrapper()

  def eps2eps(args: String*) = clitools.Eps2epsWrapper(CmdArgs(args.toVector))
  def eps2eps = clitools.Eps2epsWrapper()

  def equicktandem(args: String*) =
    clitools.EquicktandemWrapper(CmdArgs(args.toVector))
  def equicktandem = clitools.EquicktandemWrapper()

  def esdcompat(args: String*) =
    clitools.EsdcompatWrapper(CmdArgs(args.toVector))
  def esdcompat = clitools.EsdcompatWrapper()

  def est2genome(args: String*) =
    clitools.Est2genomeWrapper(CmdArgs(args.toVector))
  def est2genome = clitools.Est2genomeWrapper()

  def etandem(args: String*) = clitools.EtandemWrapper(CmdArgs(args.toVector))
  def etandem = clitools.EtandemWrapper()

  def expand(args: String*) = clitools.ExpandWrapper(CmdArgs(args.toVector))
  def expand = clitools.ExpandWrapper()

  def expandcols(args: String*) =
    clitools.ExpandColsWrapper(CmdArgs(args.toVector))
  def expandcols = clitools.ExpandColsWrapper()

  def expiry(args: String*) = clitools.ExpiryWrapper(CmdArgs(args.toVector))
  def expiry = clitools.ExpiryWrapper()

  def expr(args: String*) = clitools.ExprWrapper(CmdArgs(args.toVector))
  def expr = clitools.ExprWrapper()

  def extractalign(args: String*) =
    clitools.ExtractalignWrapper(CmdArgs(args.toVector))
  def extractalign = clitools.ExtractalignWrapper()

  def extractfeat(args: String*) =
    clitools.ExtractfeatWrapper(CmdArgs(args.toVector))
  def extractfeat = clitools.ExtractfeatWrapper()

  def extractseq(args: String*) =
    clitools.ExtractseqWrapper(CmdArgs(args.toVector))
  def extractseq = clitools.ExtractseqWrapper()

  def factor(args: String*) = clitools.FactorWrapper(CmdArgs(args.toVector))
  def factor = clitools.FactorWrapper()

  def faillog(args: String*) = clitools.FaillogWrapper(CmdArgs(args.toVector))
  def faillog = clitools.FaillogWrapper()

  def fallocate(args: String*) =
    clitools.FallocateWrapper(CmdArgs(args.toVector))
  def fallocate = clitools.FallocateWrapper()

  def fastafrombed(args: String*) =
    clitools.FastaFromBedWrapper(CmdArgs(args.toVector))
  def fastafrombed = clitools.FastaFromBedWrapper()

  def fatlabel(args: String*) = clitools.FatlabelWrapper(CmdArgs(args.toVector))
  def fatlabel = clitools.FatlabelWrapper()

  def fc_cache(args: String*) = clitools.Fc_cacheWrapper(CmdArgs(args.toVector))
  def fc_cache = clitools.Fc_cacheWrapper()

  def fc_cat(args: String*) = clitools.Fc_catWrapper(CmdArgs(args.toVector))
  def fc_cat = clitools.Fc_catWrapper()

  def fc_list(args: String*) = clitools.Fc_listWrapper(CmdArgs(args.toVector))
  def fc_list = clitools.Fc_listWrapper()

  def fc_match(args: String*) = clitools.Fc_matchWrapper(CmdArgs(args.toVector))
  def fc_match = clitools.Fc_matchWrapper()

  def fc_pattern(args: String*) =
    clitools.Fc_patternWrapper(CmdArgs(args.toVector))
  def fc_pattern = clitools.Fc_patternWrapper()

  def fc_query(args: String*) = clitools.Fc_queryWrapper(CmdArgs(args.toVector))
  def fc_query = clitools.Fc_queryWrapper()

  def fc_scan(args: String*) = clitools.Fc_scanWrapper(CmdArgs(args.toVector))
  def fc_scan = clitools.Fc_scanWrapper()

  def fc_validate(args: String*) =
    clitools.Fc_validateWrapper(CmdArgs(args.toVector))
  def fc_validate = clitools.Fc_validateWrapper()

  def fdformat(args: String*) = clitools.FdformatWrapper(CmdArgs(args.toVector))
  def fdformat = clitools.FdformatWrapper()

  def fdisk(args: String*) = clitools.FdiskWrapper(CmdArgs(args.toVector))
  def fdisk = clitools.FdiskWrapper()

  def fdp(args: String*) = clitools.FdpWrapper(CmdArgs(args.toVector))
  def fdp = clitools.FdpWrapper()

  def featcopy(args: String*) = clitools.FeatcopyWrapper(CmdArgs(args.toVector))
  def featcopy = clitools.FeatcopyWrapper()

  def featmerge(args: String*) =
    clitools.FeatmergeWrapper(CmdArgs(args.toVector))
  def featmerge = clitools.FeatmergeWrapper()

  def featreport(args: String*) =
    clitools.FeatreportWrapper(CmdArgs(args.toVector))
  def featreport = clitools.FeatreportWrapper()

  def feattext(args: String*) = clitools.FeattextWrapper(CmdArgs(args.toVector))
  def feattext = clitools.FeattextWrapper()

  def fgconsole(args: String*) =
    clitools.FgconsoleWrapper(CmdArgs(args.toVector))
  def fgconsole = clitools.FgconsoleWrapper()

  def fgrep(args: String*) = clitools.FgrepWrapper(CmdArgs(args.toVector))
  def fgrep = clitools.FgrepWrapper()

  def file_roller(args: String*) =
    clitools.File_rollerWrapper(CmdArgs(args.toVector))
  def file_roller = clitools.File_rollerWrapper()

  def filefrag(args: String*) = clitools.FilefragWrapper(CmdArgs(args.toVector))
  def filefrag = clitools.FilefragWrapper()

  def filezilla(args: String*) =
    clitools.FilezillaWrapper(CmdArgs(args.toVector))
  def filezilla = clitools.FilezillaWrapper()

  def fill_aa(args: String*) = clitools.Fill_aaWrapper(CmdArgs(args.toVector))
  def fill_aa = clitools.Fill_aaWrapper()

  def fill_an_ac(args: String*) =
    clitools.Fill_an_acWrapper(CmdArgs(args.toVector))
  def fill_an_ac = clitools.Fill_an_acWrapper()

  def fill_fs(args: String*) = clitools.Fill_fsWrapper(CmdArgs(args.toVector))
  def fill_fs = clitools.Fill_fsWrapper()

  def fill_ref_md5(args: String*) =
    clitools.Fill_ref_md5Wrapper(CmdArgs(args.toVector))
  def fill_ref_md5 = clitools.Fill_ref_md5Wrapper()

  def fincore(args: String*) = clitools.FincoreWrapper(CmdArgs(args.toVector))
  def fincore = clitools.FincoreWrapper()

  def find(args: String*) = clitools.FindWrapper(CmdArgs(args.toVector))
  def find = clitools.FindWrapper()

  def findfs(args: String*) = clitools.FindfsWrapper(CmdArgs(args.toVector))
  def findfs = clitools.FindfsWrapper()

  def findkm(args: String*) = clitools.FindkmWrapper(CmdArgs(args.toVector))
  def findkm = clitools.FindkmWrapper()

  def findmnt(args: String*) = clitools.FindmntWrapper(CmdArgs(args.toVector))
  def findmnt = clitools.FindmntWrapper()

  def firefox(args: String*) = clitools.FirefoxWrapper(CmdArgs(args.toVector))
  def firefox = clitools.FirefoxWrapper()

  def flankbed(args: String*) = clitools.FlankBedWrapper(CmdArgs(args.toVector))
  def flankbed = clitools.FlankBedWrapper()

  def flock(args: String*) = clitools.FlockWrapper(CmdArgs(args.toVector))
  def flock = clitools.FlockWrapper()

  def fmt(args: String*) = clitools.FmtWrapper(CmdArgs(args.toVector))
  def fmt = clitools.FmtWrapper()

  def fold(args: String*) = clitools.FoldWrapper(CmdArgs(args.toVector))
  def fold = clitools.FoldWrapper()

  def freak(args: String*) = clitools.FreakWrapper(CmdArgs(args.toVector))
  def freak = clitools.FreakWrapper()

  def free(args: String*) = clitools.FreeWrapper(CmdArgs(args.toVector))
  def free = clitools.FreeWrapper()

  def fsadm(args: String*) = clitools.FsadmWrapper(CmdArgs(args.toVector))
  def fsadm = clitools.FsadmWrapper()

  def fsck(args: String*) = clitools.FsckWrapper(CmdArgs(args.toVector))
  def fsck = clitools.FsckWrapper()

  def fsfreeze(args: String*) = clitools.FsfreezeWrapper(CmdArgs(args.toVector))
  def fsfreeze = clitools.FsfreezeWrapper()

  def fstrim(args: String*) = clitools.FstrimWrapper(CmdArgs(args.toVector))
  def fstrim = clitools.FstrimWrapper()

  def funzip(args: String*) = clitools.FunzipWrapper(CmdArgs(args.toVector))
  def funzip = clitools.FunzipWrapper()

  def fusermount(args: String*) =
    clitools.FusermountWrapper(CmdArgs(args.toVector))
  def fusermount = clitools.FusermountWrapper()

  def fusermount3(args: String*) =
    clitools.Fusermount3Wrapper(CmdArgs(args.toVector))
  def fusermount3 = clitools.Fusermount3Wrapper()

  def fuzznuc(args: String*) = clitools.FuzznucWrapper(CmdArgs(args.toVector))
  def fuzznuc = clitools.FuzznucWrapper()

  def fuzzpro(args: String*) = clitools.FuzzproWrapper(CmdArgs(args.toVector))
  def fuzzpro = clitools.FuzzproWrapper()

  def fuzztran(args: String*) = clitools.FuzztranWrapper(CmdArgs(args.toVector))
  def fuzztran = clitools.FuzztranWrapper()

  def fzf(args: String*) = clitools.FzfWrapper(CmdArgs(args.toVector))
  def fzf = clitools.FzfWrapper()

  def fzf_share(args: String*) =
    clitools.Fzf_shareWrapper(CmdArgs(args.toVector))
  def fzf_share = clitools.Fzf_shareWrapper()

  def fzf_tmux(args: String*) = clitools.Fzf_tmuxWrapper(CmdArgs(args.toVector))
  def fzf_tmux = clitools.Fzf_tmuxWrapper()

  def fzputtygen(args: String*) =
    clitools.FzputtygenWrapper(CmdArgs(args.toVector))
  def fzputtygen = clitools.FzputtygenWrapper()

  def fzsftp(args: String*) = clitools.FzsftpWrapper(CmdArgs(args.toVector))
  def fzsftp = clitools.FzsftpWrapper()

  def garnier(args: String*) = clitools.GarnierWrapper(CmdArgs(args.toVector))
  def garnier = clitools.GarnierWrapper()

  def gawk(args: String*) = clitools.GawkWrapper(CmdArgs(args.toVector))
  def gawk = clitools.GawkWrapper()

  def gc(args: String*) = clitools.GcWrapper(CmdArgs(args.toVector))
  def gc = clitools.GcWrapper()

  def gcc(args: String*) = clitools.GccWrapper(CmdArgs(args.toVector))
  def gcc = clitools.GccWrapper()

  def gcloud(args: String*) = clitools.GcloudWrapper(CmdArgs(args.toVector))
  def gcloud = clitools.GcloudWrapper()

  def geecee(args: String*) = clitools.GeeceeWrapper(CmdArgs(args.toVector))
  def geecee = clitools.GeeceeWrapper()

  def gencat(args: String*) = clitools.GencatWrapper(CmdArgs(args.toVector))
  def gencat = clitools.GencatWrapper()

  def genl(args: String*) = clitools.GenlWrapper(CmdArgs(args.toVector))
  def genl = clitools.GenlWrapper()

  def genomecoveragebed(args: String*) =
    clitools.GenomeCoverageBedWrapper(CmdArgs(args.toVector))
  def genomecoveragebed = clitools.GenomeCoverageBedWrapper()

  def gentrigrams(args: String*) =
    clitools.GentrigramsWrapper(CmdArgs(args.toVector))
  def gentrigrams = clitools.GentrigramsWrapper()

  def getoverlap(args: String*) =
    clitools.GetOverlapWrapper(CmdArgs(args.toVector))
  def getoverlap = clitools.GetOverlapWrapper()

  def getcap(args: String*) = clitools.GetcapWrapper(CmdArgs(args.toVector))
  def getcap = clitools.GetcapWrapper()

  def getconf(args: String*) = clitools.GetconfWrapper(CmdArgs(args.toVector))
  def getconf = clitools.GetconfWrapper()

  def getent(args: String*) = clitools.GetentWrapper(CmdArgs(args.toVector))
  def getent = clitools.GetentWrapper()

  def getfacl(args: String*) = clitools.GetfaclWrapper(CmdArgs(args.toVector))
  def getfacl = clitools.GetfaclWrapper()

  def getfattr(args: String*) = clitools.GetfattrWrapper(CmdArgs(args.toVector))
  def getfattr = clitools.GetfattrWrapper()

  def getkeycodes(args: String*) =
    clitools.GetkeycodesWrapper(CmdArgs(args.toVector))
  def getkeycodes = clitools.GetkeycodesWrapper()

  def getopt(args: String*) = clitools.GetoptWrapper(CmdArgs(args.toVector))
  def getopt = clitools.GetoptWrapper()

  def getorf(args: String*) = clitools.GetorfWrapper(CmdArgs(args.toVector))
  def getorf = clitools.GetorfWrapper()

  def getpcaps(args: String*) = clitools.GetpcapsWrapper(CmdArgs(args.toVector))
  def getpcaps = clitools.GetpcapsWrapper()

  def getunimap(args: String*) =
    clitools.GetunimapWrapper(CmdArgs(args.toVector))
  def getunimap = clitools.GetunimapWrapper()

  def git(args: String*) = clitools.GitWrapper(CmdArgs(args.toVector))
  def git = clitools.GitWrapper()

  def git_credential_netrc(args: String*) =
    clitools.Git_credential_netrcWrapper(CmdArgs(args.toVector))
  def git_credential_netrc = clitools.Git_credential_netrcWrapper()

  def git_cvsserver(args: String*) =
    clitools.Git_cvsserverWrapper(CmdArgs(args.toVector))
  def git_cvsserver = clitools.Git_cvsserverWrapper()

  def git_http_backend(args: String*) =
    clitools.Git_http_backendWrapper(CmdArgs(args.toVector))
  def git_http_backend = clitools.Git_http_backendWrapper()

  def git_lfs(args: String*) = clitools.Git_lfsWrapper(CmdArgs(args.toVector))
  def git_lfs = clitools.Git_lfsWrapper()

  def git_lfs_test_server_api(args: String*) =
    clitools.Git_lfs_test_server_apiWrapper(CmdArgs(args.toVector))
  def git_lfs_test_server_api = clitools.Git_lfs_test_server_apiWrapper()

  def git_receive_pack(args: String*) =
    clitools.Git_receive_packWrapper(CmdArgs(args.toVector))
  def git_receive_pack = clitools.Git_receive_packWrapper()

  def git_shell(args: String*) =
    clitools.Git_shellWrapper(CmdArgs(args.toVector))
  def git_shell = clitools.Git_shellWrapper()

  def git_upload_archive(args: String*) =
    clitools.Git_upload_archiveWrapper(CmdArgs(args.toVector))
  def git_upload_archive = clitools.Git_upload_archiveWrapper()

  def git_upload_pack(args: String*) =
    clitools.Git_upload_packWrapper(CmdArgs(args.toVector))
  def git_upload_pack = clitools.Git_upload_packWrapper()

  def gmenudbusmenuproxy(args: String*) =
    clitools.GmenudbusmenuproxyWrapper(CmdArgs(args.toVector))
  def gmenudbusmenuproxy = clitools.GmenudbusmenuproxyWrapper()

  def gml2gv(args: String*) = clitools.Gml2gvWrapper(CmdArgs(args.toVector))
  def gml2gv = clitools.Gml2gvWrapper()

  def gmplayer(args: String*) = clitools.GmplayerWrapper(CmdArgs(args.toVector))
  def gmplayer = clitools.GmplayerWrapper()

  def godef(args: String*) = clitools.GodefWrapper(CmdArgs(args.toVector))
  def godef = clitools.GodefWrapper()

  def goname(args: String*) = clitools.GonameWrapper(CmdArgs(args.toVector))
  def goname = clitools.GonameWrapper()

  def google_chrome_stable(args: String*) =
    clitools.Google_chrome_stableWrapper(CmdArgs(args.toVector))
  def google_chrome_stable = clitools.Google_chrome_stableWrapper()

  def gparted(args: String*) = clitools.GpartedWrapper(CmdArgs(args.toVector))
  def gparted = clitools.GpartedWrapper()

  def gpartedbin(args: String*) =
    clitools.GpartedbinWrapper(CmdArgs(args.toVector))
  def gpartedbin = clitools.GpartedbinWrapper()

  def gpasswd(args: String*) = clitools.GpasswdWrapper(CmdArgs(args.toVector))
  def gpasswd = clitools.GpasswdWrapper()

  def gpg(args: String*) = clitools.GpgWrapper(CmdArgs(args.toVector))
  def gpg = clitools.GpgWrapper()

  def gpg2(args: String*) = clitools.Gpg2Wrapper(CmdArgs(args.toVector))
  def gpg2 = clitools.Gpg2Wrapper()

  def gpg_agent(args: String*) =
    clitools.Gpg_agentWrapper(CmdArgs(args.toVector))
  def gpg_agent = clitools.Gpg_agentWrapper()

  def gpg_connect_agent(args: String*) =
    clitools.Gpg_connect_agentWrapper(CmdArgs(args.toVector))
  def gpg_connect_agent = clitools.Gpg_connect_agentWrapper()

  def gpg_wks_server(args: String*) =
    clitools.Gpg_wks_serverWrapper(CmdArgs(args.toVector))
  def gpg_wks_server = clitools.Gpg_wks_serverWrapper()

  def gpgconf(args: String*) = clitools.GpgconfWrapper(CmdArgs(args.toVector))
  def gpgconf = clitools.GpgconfWrapper()

  def gpgparsemail(args: String*) =
    clitools.GpgparsemailWrapper(CmdArgs(args.toVector))
  def gpgparsemail = clitools.GpgparsemailWrapper()

  def gpgscm(args: String*) = clitools.GpgscmWrapper(CmdArgs(args.toVector))
  def gpgscm = clitools.GpgscmWrapper()

  def gpgsm(args: String*) = clitools.GpgsmWrapper(CmdArgs(args.toVector))
  def gpgsm = clitools.GpgsmWrapper()

  def gpgtar(args: String*) = clitools.GpgtarWrapper(CmdArgs(args.toVector))
  def gpgtar = clitools.GpgtarWrapper()

  def gpgv(args: String*) = clitools.GpgvWrapper(CmdArgs(args.toVector))
  def gpgv = clitools.GpgvWrapper()

  def gradle(args: String*) = clitools.GradleWrapper(CmdArgs(args.toVector))
  def gradle = clitools.GradleWrapper()

  def graphml2gv(args: String*) =
    clitools.Graphml2gvWrapper(CmdArgs(args.toVector))
  def graphml2gv = clitools.Graphml2gvWrapper()

  def grep(args: String*) = clitools.GrepWrapper(CmdArgs(args.toVector))
  def grep = clitools.GrepWrapper()

  def groupby(args: String*) = clitools.GroupByWrapper(CmdArgs(args.toVector))
  def groupby = clitools.GroupByWrapper()

  def groupadd(args: String*) = clitools.GroupaddWrapper(CmdArgs(args.toVector))
  def groupadd = clitools.GroupaddWrapper()

  def groupdel(args: String*) = clitools.GroupdelWrapper(CmdArgs(args.toVector))
  def groupdel = clitools.GroupdelWrapper()

  def groupmems(args: String*) =
    clitools.GroupmemsWrapper(CmdArgs(args.toVector))
  def groupmems = clitools.GroupmemsWrapper()

  def groupmod(args: String*) = clitools.GroupmodWrapper(CmdArgs(args.toVector))
  def groupmod = clitools.GroupmodWrapper()

  def groups(args: String*) = clitools.GroupsWrapper(CmdArgs(args.toVector))
  def groups = clitools.GroupsWrapper()

  def grpck(args: String*) = clitools.GrpckWrapper(CmdArgs(args.toVector))
  def grpck = clitools.GrpckWrapper()

  def grpconv(args: String*) = clitools.GrpconvWrapper(CmdArgs(args.toVector))
  def grpconv = clitools.GrpconvWrapper()

  def grpunconv(args: String*) =
    clitools.GrpunconvWrapper(CmdArgs(args.toVector))
  def grpunconv = clitools.GrpunconvWrapper()

  def gs(args: String*) = clitools.GsWrapper(CmdArgs(args.toVector))
  def gs = clitools.GsWrapper()

  def gsbj(args: String*) = clitools.GsbjWrapper(CmdArgs(args.toVector))
  def gsbj = clitools.GsbjWrapper()

  def gsc(args: String*) = clitools.GscWrapper(CmdArgs(args.toVector))
  def gsc = clitools.GscWrapper()

  def gsdj(args: String*) = clitools.GsdjWrapper(CmdArgs(args.toVector))
  def gsdj = clitools.GsdjWrapper()

  def gsdj500(args: String*) = clitools.Gsdj500Wrapper(CmdArgs(args.toVector))
  def gsdj500 = clitools.Gsdj500Wrapper()

  def gslj(args: String*) = clitools.GsljWrapper(CmdArgs(args.toVector))
  def gslj = clitools.GsljWrapper()

  def gslp(args: String*) = clitools.GslpWrapper(CmdArgs(args.toVector))
  def gslp = clitools.GslpWrapper()

  def gsnd(args: String*) = clitools.GsndWrapper(CmdArgs(args.toVector))
  def gsnd = clitools.GsndWrapper()

  def gss_client(args: String*) =
    clitools.Gss_clientWrapper(CmdArgs(args.toVector))
  def gss_client = clitools.Gss_clientWrapper()

  def gss_server(args: String*) =
    clitools.Gss_serverWrapper(CmdArgs(args.toVector))
  def gss_server = clitools.Gss_serverWrapper()

  def gsutil(args: String*) = clitools.GsutilWrapper(CmdArgs(args.toVector))
  def gsutil = clitools.GsutilWrapper()

  def gsx(args: String*) = clitools.GsxWrapper(CmdArgs(args.toVector))
  def gsx = clitools.GsxWrapper()

  def gtf(args: String*) = clitools.GtfWrapper(CmdArgs(args.toVector))
  def gtf = clitools.GtfWrapper()

  def gunzip(args: String*) = clitools.GunzipWrapper(CmdArgs(args.toVector))
  def gunzip = clitools.GunzipWrapper()

  def gv2gml(args: String*) = clitools.Gv2gmlWrapper(CmdArgs(args.toVector))
  def gv2gml = clitools.Gv2gmlWrapper()

  def gv2gxl(args: String*) = clitools.Gv2gxlWrapper(CmdArgs(args.toVector))
  def gv2gxl = clitools.Gv2gxlWrapper()

  def gvcolor(args: String*) = clitools.GvcolorWrapper(CmdArgs(args.toVector))
  def gvcolor = clitools.GvcolorWrapper()

  def gvgen(args: String*) = clitools.GvgenWrapper(CmdArgs(args.toVector))
  def gvgen = clitools.GvgenWrapper()

  def gvmap(args: String*) = clitools.GvmapWrapper(CmdArgs(args.toVector))
  def gvmap = clitools.GvmapWrapper()

  def gvpack(args: String*) = clitools.GvpackWrapper(CmdArgs(args.toVector))
  def gvpack = clitools.GvpackWrapper()

  def gvpr(args: String*) = clitools.GvprWrapper(CmdArgs(args.toVector))
  def gvpr = clitools.GvprWrapper()

  def gxl2dot(args: String*) = clitools.Gxl2dotWrapper(CmdArgs(args.toVector))
  def gxl2dot = clitools.Gxl2dotWrapper()

  def gxl2gv(args: String*) = clitools.Gxl2gvWrapper(CmdArgs(args.toVector))
  def gxl2gv = clitools.Gxl2gvWrapper()

  def gzexe(args: String*) = clitools.GzexeWrapper(CmdArgs(args.toVector))
  def gzexe = clitools.GzexeWrapper()

  def gzip(args: String*) = clitools.GzipWrapper(CmdArgs(args.toVector))
  def gzip = clitools.GzipWrapper()

  def gzrecover(args: String*) =
    clitools.GzrecoverWrapper(CmdArgs(args.toVector))
  def gzrecover = clitools.GzrecoverWrapper()

  def h2ph(args: String*) = clitools.H2phWrapper(CmdArgs(args.toVector))
  def h2ph = clitools.H2phWrapper()

  def h2xs(args: String*) = clitools.H2xsWrapper(CmdArgs(args.toVector))
  def h2xs = clitools.H2xsWrapper()

  def halt(args: String*) = clitools.HaltWrapper(CmdArgs(args.toVector))
  def halt = clitools.HaltWrapper()

  def head(args: String*) = clitools.HeadWrapper(CmdArgs(args.toVector))
  def head = clitools.HeadWrapper()

  def helixturnhelix(args: String*) =
    clitools.HelixturnhelixWrapper(CmdArgs(args.toVector))
  def helixturnhelix = clitools.HelixturnhelixWrapper()

  def help(args: String*) = clitools.HelpWrapper(CmdArgs(args.toVector))
  def help = clitools.HelpWrapper()

  def hexdump(args: String*) = clitools.HexdumpWrapper(CmdArgs(args.toVector))
  def hexdump = clitools.HexdumpWrapper()

  def hg(args: String*) = clitools.HgWrapper(CmdArgs(args.toVector))
  def hg = clitools.HgWrapper()

  def hmmalign(args: String*) = clitools.HmmalignWrapper(CmdArgs(args.toVector))
  def hmmalign = clitools.HmmalignWrapper()

  def hmmbuild(args: String*) = clitools.HmmbuildWrapper(CmdArgs(args.toVector))
  def hmmbuild = clitools.HmmbuildWrapper()

  def hmmconvert(args: String*) =
    clitools.HmmconvertWrapper(CmdArgs(args.toVector))
  def hmmconvert = clitools.HmmconvertWrapper()

  def hmmemit(args: String*) = clitools.HmmemitWrapper(CmdArgs(args.toVector))
  def hmmemit = clitools.HmmemitWrapper()

  def hmmfetch(args: String*) = clitools.HmmfetchWrapper(CmdArgs(args.toVector))
  def hmmfetch = clitools.HmmfetchWrapper()

  def hmmlogo(args: String*) = clitools.HmmlogoWrapper(CmdArgs(args.toVector))
  def hmmlogo = clitools.HmmlogoWrapper()

  def hmmpgmd(args: String*) = clitools.HmmpgmdWrapper(CmdArgs(args.toVector))
  def hmmpgmd = clitools.HmmpgmdWrapper()

  def hmmpress(args: String*) = clitools.HmmpressWrapper(CmdArgs(args.toVector))
  def hmmpress = clitools.HmmpressWrapper()

  def hmmscan(args: String*) = clitools.HmmscanWrapper(CmdArgs(args.toVector))
  def hmmscan = clitools.HmmscanWrapper()

  def hmmsearch(args: String*) =
    clitools.HmmsearchWrapper(CmdArgs(args.toVector))
  def hmmsearch = clitools.HmmsearchWrapper()

  def hmmsim(args: String*) = clitools.HmmsimWrapper(CmdArgs(args.toVector))
  def hmmsim = clitools.HmmsimWrapper()

  def hmmstat(args: String*) = clitools.HmmstatWrapper(CmdArgs(args.toVector))
  def hmmstat = clitools.HmmstatWrapper()

  def hmoment(args: String*) = clitools.HmomentWrapper(CmdArgs(args.toVector))
  def hmoment = clitools.HmomentWrapper()

  def host(args: String*) = clitools.HostWrapper(CmdArgs(args.toVector))
  def host = clitools.HostWrapper()

  def hostid(args: String*) = clitools.HostidWrapper(CmdArgs(args.toVector))
  def hostid = clitools.HostidWrapper()

  def hostname(args: String*) = clitools.HostnameWrapper(CmdArgs(args.toVector))
  def hostname = clitools.HostnameWrapper()

  def hostnamectl(args: String*) =
    clitools.HostnamectlWrapper(CmdArgs(args.toVector))
  def hostnamectl = clitools.HostnamectlWrapper()

  def http(args: String*) = clitools.HttpWrapper(CmdArgs(args.toVector))
  def http = clitools.HttpWrapper()

  def hwclock(args: String*) = clitools.HwclockWrapper(CmdArgs(args.toVector))
  def hwclock = clitools.HwclockWrapper()

  def i386(args: String*) = clitools.I386Wrapper(CmdArgs(args.toVector))
  def i386 = clitools.I386Wrapper()

  def iceauth(args: String*) = clitools.IceauthWrapper(CmdArgs(args.toVector))
  def iceauth = clitools.IceauthWrapper()

  def iconv(args: String*) = clitools.IconvWrapper(CmdArgs(args.toVector))
  def iconv = clitools.IconvWrapper()

  def iconvconfig(args: String*) =
    clitools.IconvconfigWrapper(CmdArgs(args.toVector))
  def iconvconfig = clitools.IconvconfigWrapper()

  def id(args: String*) = clitools.IdWrapper(CmdArgs(args.toVector))
  def id = clitools.IdWrapper()

  def idea_ultimate(args: String*) =
    clitools.Idea_ultimateWrapper(CmdArgs(args.toVector))
  def idea_ultimate = clitools.Idea_ultimateWrapper()

  def identify(args: String*) = clitools.IdentifyWrapper(CmdArgs(args.toVector))
  def identify = clitools.IdentifyWrapper()

  def iecset(args: String*) = clitools.IecsetWrapper(CmdArgs(args.toVector))
  def iecset = clitools.IecsetWrapper()

  def iep(args: String*) = clitools.IepWrapper(CmdArgs(args.toVector))
  def iep = clitools.IepWrapper()

  def ifcfg(args: String*) = clitools.IfcfgWrapper(CmdArgs(args.toVector))
  def ifcfg = clitools.IfcfgWrapper()

  def ifconfig(args: String*) = clitools.IfconfigWrapper(CmdArgs(args.toVector))
  def ifconfig = clitools.IfconfigWrapper()

  def ifstat(args: String*) = clitools.IfstatWrapper(CmdArgs(args.toVector))
  def ifstat = clitools.IfstatWrapper()

  def igv(args: String*) = clitools.IgvWrapper(CmdArgs(args.toVector))
  def igv = clitools.IgvWrapper()

  def info(args: String*) = clitools.InfoWrapper(CmdArgs(args.toVector))
  def info = clitools.InfoWrapper()

  def infoalign(args: String*) =
    clitools.InfoalignWrapper(CmdArgs(args.toVector))
  def infoalign = clitools.InfoalignWrapper()

  def infoassembly(args: String*) =
    clitools.InfoassemblyWrapper(CmdArgs(args.toVector))
  def infoassembly = clitools.InfoassemblyWrapper()

  def infobase(args: String*) = clitools.InfobaseWrapper(CmdArgs(args.toVector))
  def infobase = clitools.InfobaseWrapper()

  def inforesidue(args: String*) =
    clitools.InforesidueWrapper(CmdArgs(args.toVector))
  def inforesidue = clitools.InforesidueWrapper()

  def infoseq(args: String*) = clitools.InfoseqWrapper(CmdArgs(args.toVector))
  def infoseq = clitools.InfoseqWrapper()

  def infotocap(args: String*) =
    clitools.InfotocapWrapper(CmdArgs(args.toVector))
  def infotocap = clitools.InfotocapWrapper()

  def init(args: String*) = clitools.InitWrapper(CmdArgs(args.toVector))
  def init = clitools.InitWrapper()

  def insmod(args: String*) = clitools.InsmodWrapper(CmdArgs(args.toVector))
  def insmod = clitools.InsmodWrapper()

  def install(args: String*) = clitools.InstallWrapper(CmdArgs(args.toVector))
  def install = clitools.InstallWrapper()

  def install_info(args: String*) =
    clitools.Install_infoWrapper(CmdArgs(args.toVector))
  def install_info = clitools.Install_infoWrapper()

  def install_printerdriver(args: String*) =
    clitools.Install_printerdriverWrapper(CmdArgs(args.toVector))
  def install_printerdriver = clitools.Install_printerdriverWrapper()

  def instmodsh(args: String*) =
    clitools.InstmodshWrapper(CmdArgs(args.toVector))
  def instmodsh = clitools.InstmodshWrapper()

  def integritysetup(args: String*) =
    clitools.IntegritysetupWrapper(CmdArgs(args.toVector))
  def integritysetup = clitools.IntegritysetupWrapper()

  def intersectbed(args: String*) =
    clitools.IntersectBedWrapper(CmdArgs(args.toVector))
  def intersectbed = clitools.IntersectBedWrapper()

  def ionice(args: String*) = clitools.IoniceWrapper(CmdArgs(args.toVector))
  def ionice = clitools.IoniceWrapper()

  def ip(args: String*) = clitools.IpWrapper(CmdArgs(args.toVector))
  def ip = clitools.IpWrapper()

  def ip6tables(args: String*) =
    clitools.Ip6tablesWrapper(CmdArgs(args.toVector))
  def ip6tables = clitools.Ip6tablesWrapper()

  def ip6tables_legacy(args: String*) =
    clitools.Ip6tables_legacyWrapper(CmdArgs(args.toVector))
  def ip6tables_legacy = clitools.Ip6tables_legacyWrapper()

  def ip6tables_legacy_restore(args: String*) =
    clitools.Ip6tables_legacy_restoreWrapper(CmdArgs(args.toVector))
  def ip6tables_legacy_restore = clitools.Ip6tables_legacy_restoreWrapper()

  def ip6tables_legacy_save(args: String*) =
    clitools.Ip6tables_legacy_saveWrapper(CmdArgs(args.toVector))
  def ip6tables_legacy_save = clitools.Ip6tables_legacy_saveWrapper()

  def ip6tables_nft(args: String*) =
    clitools.Ip6tables_nftWrapper(CmdArgs(args.toVector))
  def ip6tables_nft = clitools.Ip6tables_nftWrapper()

  def ip6tables_nft_restore(args: String*) =
    clitools.Ip6tables_nft_restoreWrapper(CmdArgs(args.toVector))
  def ip6tables_nft_restore = clitools.Ip6tables_nft_restoreWrapper()

  def ip6tables_nft_save(args: String*) =
    clitools.Ip6tables_nft_saveWrapper(CmdArgs(args.toVector))
  def ip6tables_nft_save = clitools.Ip6tables_nft_saveWrapper()

  def ip6tables_restore(args: String*) =
    clitools.Ip6tables_restoreWrapper(CmdArgs(args.toVector))
  def ip6tables_restore = clitools.Ip6tables_restoreWrapper()

  def ip6tables_restore_translate(args: String*) =
    clitools.Ip6tables_restore_translateWrapper(CmdArgs(args.toVector))
  def ip6tables_restore_translate =
    clitools.Ip6tables_restore_translateWrapper()

  def ip6tables_save(args: String*) =
    clitools.Ip6tables_saveWrapper(CmdArgs(args.toVector))
  def ip6tables_save = clitools.Ip6tables_saveWrapper()

  def ip6tables_translate(args: String*) =
    clitools.Ip6tables_translateWrapper(CmdArgs(args.toVector))
  def ip6tables_translate = clitools.Ip6tables_translateWrapper()

  def ipcmk(args: String*) = clitools.IpcmkWrapper(CmdArgs(args.toVector))
  def ipcmk = clitools.IpcmkWrapper()

  def ipcrm(args: String*) = clitools.IpcrmWrapper(CmdArgs(args.toVector))
  def ipcrm = clitools.IpcrmWrapper()

  def ipcs(args: String*) = clitools.IpcsWrapper(CmdArgs(args.toVector))
  def ipcs = clitools.IpcsWrapper()

  def ippfind(args: String*) = clitools.IppfindWrapper(CmdArgs(args.toVector))
  def ippfind = clitools.IppfindWrapper()

  def ipptool(args: String*) = clitools.IpptoolWrapper(CmdArgs(args.toVector))
  def ipptool = clitools.IpptoolWrapper()

  def iptables(args: String*) = clitools.IptablesWrapper(CmdArgs(args.toVector))
  def iptables = clitools.IptablesWrapper()

  def iptables_legacy(args: String*) =
    clitools.Iptables_legacyWrapper(CmdArgs(args.toVector))
  def iptables_legacy = clitools.Iptables_legacyWrapper()

  def iptables_legacy_restore(args: String*) =
    clitools.Iptables_legacy_restoreWrapper(CmdArgs(args.toVector))
  def iptables_legacy_restore = clitools.Iptables_legacy_restoreWrapper()

  def iptables_legacy_save(args: String*) =
    clitools.Iptables_legacy_saveWrapper(CmdArgs(args.toVector))
  def iptables_legacy_save = clitools.Iptables_legacy_saveWrapper()

  def iptables_nft(args: String*) =
    clitools.Iptables_nftWrapper(CmdArgs(args.toVector))
  def iptables_nft = clitools.Iptables_nftWrapper()

  def iptables_nft_restore(args: String*) =
    clitools.Iptables_nft_restoreWrapper(CmdArgs(args.toVector))
  def iptables_nft_restore = clitools.Iptables_nft_restoreWrapper()

  def iptables_nft_save(args: String*) =
    clitools.Iptables_nft_saveWrapper(CmdArgs(args.toVector))
  def iptables_nft_save = clitools.Iptables_nft_saveWrapper()

  def iptables_restore(args: String*) =
    clitools.Iptables_restoreWrapper(CmdArgs(args.toVector))
  def iptables_restore = clitools.Iptables_restoreWrapper()

  def iptables_restore_translate(args: String*) =
    clitools.Iptables_restore_translateWrapper(CmdArgs(args.toVector))
  def iptables_restore_translate = clitools.Iptables_restore_translateWrapper()

  def iptables_save(args: String*) =
    clitools.Iptables_saveWrapper(CmdArgs(args.toVector))
  def iptables_save = clitools.Iptables_saveWrapper()

  def iptables_translate(args: String*) =
    clitools.Iptables_translateWrapper(CmdArgs(args.toVector))
  def iptables_translate = clitools.Iptables_translateWrapper()

  def iptables_xml(args: String*) =
    clitools.Iptables_xmlWrapper(CmdArgs(args.toVector))
  def iptables_xml = clitools.Iptables_xmlWrapper()

  def isochore(args: String*) = clitools.IsochoreWrapper(CmdArgs(args.toVector))
  def isochore = clitools.IsochoreWrapper()

  def isosize(args: String*) = clitools.IsosizeWrapper(CmdArgs(args.toVector))
  def isosize = clitools.IsosizeWrapper()

  def jackhmmer(args: String*) =
    clitools.JackhmmerWrapper(CmdArgs(args.toVector))
  def jackhmmer = clitools.JackhmmerWrapper()

  def jaspextract(args: String*) =
    clitools.JaspextractWrapper(CmdArgs(args.toVector))
  def jaspextract = clitools.JaspextractWrapper()

  def jaspscan(args: String*) = clitools.JaspscanWrapper(CmdArgs(args.toVector))
  def jaspscan = clitools.JaspscanWrapper()

  def jembossctl(args: String*) =
    clitools.JembossctlWrapper(CmdArgs(args.toVector))
  def jembossctl = clitools.JembossctlWrapper()

  def jigdo_file(args: String*) =
    clitools.Jigdo_fileWrapper(CmdArgs(args.toVector))
  def jigdo_file = clitools.Jigdo_fileWrapper()

  def jigdo_lite(args: String*) =
    clitools.Jigdo_liteWrapper(CmdArgs(args.toVector))
  def jigdo_lite = clitools.Jigdo_liteWrapper()

  def jigdo_mirror(args: String*) =
    clitools.Jigdo_mirrorWrapper(CmdArgs(args.toVector))
  def jigdo_mirror = clitools.Jigdo_mirrorWrapper()

  def jmtpfs(args: String*) = clitools.JmtpfsWrapper(CmdArgs(args.toVector))
  def jmtpfs = clitools.JmtpfsWrapper()

  def join(args: String*) = clitools.JoinWrapper(CmdArgs(args.toVector))
  def join = clitools.JoinWrapper()

  def journalctl(args: String*) =
    clitools.JournalctlWrapper(CmdArgs(args.toVector))
  def journalctl = clitools.JournalctlWrapper()

  def jq(args: String*) = clitools.JqWrapper(CmdArgs(args.toVector))
  def jq = clitools.JqWrapper()

  def json_pp(args: String*) = clitools.Json_ppWrapper(CmdArgs(args.toVector))
  def json_pp = clitools.Json_ppWrapper()

  def jupyter_bundlerextension(args: String*) =
    clitools.Jupyter_bundlerextensionWrapper(CmdArgs(args.toVector))
  def jupyter_bundlerextension = clitools.Jupyter_bundlerextensionWrapper()

  def jupyter_nbextension(args: String*) =
    clitools.Jupyter_nbextensionWrapper(CmdArgs(args.toVector))
  def jupyter_nbextension = clitools.Jupyter_nbextensionWrapper()

  def jupyter_notebook(args: String*) =
    clitools.Jupyter_notebookWrapper(CmdArgs(args.toVector))
  def jupyter_notebook = clitools.Jupyter_notebookWrapper()

  def jupyter_serverextension(args: String*) =
    clitools.Jupyter_serverextensionWrapper(CmdArgs(args.toVector))
  def jupyter_serverextension = clitools.Jupyter_serverextensionWrapper()

  def k5srvutil(args: String*) =
    clitools.K5srvutilWrapper(CmdArgs(args.toVector))
  def k5srvutil = clitools.K5srvutilWrapper()

  def kaccess(args: String*) = clitools.KaccessWrapper(CmdArgs(args.toVector))
  def kaccess = clitools.KaccessWrapper()

  def kactivities_cli(args: String*) =
    clitools.Kactivities_cliWrapper(CmdArgs(args.toVector))
  def kactivities_cli = clitools.Kactivities_cliWrapper()

  def kadmin(args: String*) = clitools.KadminWrapper(CmdArgs(args.toVector))
  def kadmin = clitools.KadminWrapper()

  def kadmind(args: String*) = clitools.KadmindWrapper(CmdArgs(args.toVector))
  def kadmind = clitools.KadmindWrapper()

  def kallisto(args: String*) = clitools.KallistoWrapper(CmdArgs(args.toVector))
  def kallisto = clitools.KallistoWrapper()

  def kapplymousetheme(args: String*) =
    clitools.KapplymousethemeWrapper(CmdArgs(args.toVector))
  def kapplymousetheme = clitools.KapplymousethemeWrapper()

  def kate(args: String*) = clitools.KateWrapper(CmdArgs(args.toVector))
  def kate = clitools.KateWrapper()

  def kbd_mode(args: String*) = clitools.Kbd_modeWrapper(CmdArgs(args.toVector))
  def kbd_mode = clitools.Kbd_modeWrapper()

  def kbdinfo(args: String*) = clitools.KbdinfoWrapper(CmdArgs(args.toVector))
  def kbdinfo = clitools.KbdinfoWrapper()

  def kbdrate(args: String*) = clitools.KbdrateWrapper(CmdArgs(args.toVector))
  def kbdrate = clitools.KbdrateWrapper()

  def kbroadcastnotification(args: String*) =
    clitools.KbroadcastnotificationWrapper(CmdArgs(args.toVector))
  def kbroadcastnotification = clitools.KbroadcastnotificationWrapper()

  def kbuildsycoca5(args: String*) =
    clitools.Kbuildsycoca5Wrapper(CmdArgs(args.toVector))
  def kbuildsycoca5 = clitools.Kbuildsycoca5Wrapper()

  def kbxutil(args: String*) = clitools.KbxutilWrapper(CmdArgs(args.toVector))
  def kbxutil = clitools.KbxutilWrapper()

  def kcheckrunning(args: String*) =
    clitools.KcheckrunningWrapper(CmdArgs(args.toVector))
  def kcheckrunning = clitools.KcheckrunningWrapper()

  def kcm_touchpad_list_devices(args: String*) =
    clitools.Kcm_touchpad_list_devicesWrapper(CmdArgs(args.toVector))
  def kcm_touchpad_list_devices = clitools.Kcm_touchpad_list_devicesWrapper()

  def kcminit(args: String*) = clitools.KcminitWrapper(CmdArgs(args.toVector))
  def kcminit = clitools.KcminitWrapper()

  def kcminit_startup(args: String*) =
    clitools.Kcminit_startupWrapper(CmdArgs(args.toVector))
  def kcminit_startup = clitools.Kcminit_startupWrapper()

  def kcmshell5(args: String*) =
    clitools.Kcmshell5Wrapper(CmdArgs(args.toVector))
  def kcmshell5 = clitools.Kcmshell5Wrapper()

  def kcolorschemeeditor(args: String*) =
    clitools.KcolorschemeeditorWrapper(CmdArgs(args.toVector))
  def kcolorschemeeditor = clitools.KcolorschemeeditorWrapper()

  def kcookiejar5(args: String*) =
    clitools.Kcookiejar5Wrapper(CmdArgs(args.toVector))
  def kcookiejar5 = clitools.Kcookiejar5Wrapper()

  def kdb5_util(args: String*) =
    clitools.Kdb5_utilWrapper(CmdArgs(args.toVector))
  def kdb5_util = clitools.Kdb5_utilWrapper()

  def kde_add_printer(args: String*) =
    clitools.Kde_add_printerWrapper(CmdArgs(args.toVector))
  def kde_add_printer = clitools.Kde_add_printerWrapper()

  def kde_open5(args: String*) =
    clitools.Kde_open5Wrapper(CmdArgs(args.toVector))
  def kde_open5 = clitools.Kde_open5Wrapper()

  def kde_print_queue(args: String*) =
    clitools.Kde_print_queueWrapper(CmdArgs(args.toVector))
  def kde_print_queue = clitools.Kde_print_queueWrapper()

  def kdecp5(args: String*) = clitools.Kdecp5Wrapper(CmdArgs(args.toVector))
  def kdecp5 = clitools.Kdecp5Wrapper()

  def kded5(args: String*) = clitools.Kded5Wrapper(CmdArgs(args.toVector))
  def kded5 = clitools.Kded5Wrapper()

  def kdeinit5(args: String*) = clitools.Kdeinit5Wrapper(CmdArgs(args.toVector))
  def kdeinit5 = clitools.Kdeinit5Wrapper()

  def kdeinit5_shutdown(args: String*) =
    clitools.Kdeinit5_shutdownWrapper(CmdArgs(args.toVector))
  def kdeinit5_shutdown = clitools.Kdeinit5_shutdownWrapper()

  def kdeinit5_wrapper(args: String*) =
    clitools.Kdeinit5_wrapperWrapper(CmdArgs(args.toVector))
  def kdeinit5_wrapper = clitools.Kdeinit5_wrapperWrapper()

  def kdemv5(args: String*) = clitools.Kdemv5Wrapper(CmdArgs(args.toVector))
  def kdemv5 = clitools.Kdemv5Wrapper()

  def kdestroy(args: String*) = clitools.KdestroyWrapper(CmdArgs(args.toVector))
  def kdestroy = clitools.KdestroyWrapper()

  def kdostartupconfig5(args: String*) =
    clitools.Kdostartupconfig5Wrapper(CmdArgs(args.toVector))
  def kdostartupconfig5 = clitools.Kdostartupconfig5Wrapper()

  def keditfiletype5(args: String*) =
    clitools.Keditfiletype5Wrapper(CmdArgs(args.toVector))
  def keditfiletype5 = clitools.Keditfiletype5Wrapper()

  def kexec(args: String*) = clitools.KexecWrapper(CmdArgs(args.toVector))
  def kexec = clitools.KexecWrapper()

  def kfontinst(args: String*) =
    clitools.KfontinstWrapper(CmdArgs(args.toVector))
  def kfontinst = clitools.KfontinstWrapper()

  def kfontview(args: String*) =
    clitools.KfontviewWrapper(CmdArgs(args.toVector))
  def kfontview = clitools.KfontviewWrapper()

  def kglobalaccel5(args: String*) =
    clitools.Kglobalaccel5Wrapper(CmdArgs(args.toVector))
  def kglobalaccel5 = clitools.Kglobalaccel5Wrapper()

  def khelpcenter(args: String*) =
    clitools.KhelpcenterWrapper(CmdArgs(args.toVector))
  def khelpcenter = clitools.KhelpcenterWrapper()

  def kiconfinder5(args: String*) =
    clitools.Kiconfinder5Wrapper(CmdArgs(args.toVector))
  def kiconfinder5 = clitools.Kiconfinder5Wrapper()

  def kill(args: String*) = clitools.KillWrapper(CmdArgs(args.toVector))
  def kill = clitools.KillWrapper()

  def kinfocenter(args: String*) =
    clitools.KinfocenterWrapper(CmdArgs(args.toVector))
  def kinfocenter = clitools.KinfocenterWrapper()

  def kinit(args: String*) = clitools.KinitWrapper(CmdArgs(args.toVector))
  def kinit = clitools.KinitWrapper()

  def kioclient5(args: String*) =
    clitools.Kioclient5Wrapper(CmdArgs(args.toVector))
  def kioclient5 = clitools.Kioclient5Wrapper()

  def klipper(args: String*) = clitools.KlipperWrapper(CmdArgs(args.toVector))
  def klipper = clitools.KlipperWrapper()

  def klist(args: String*) = clitools.KlistWrapper(CmdArgs(args.toVector))
  def klist = clitools.KlistWrapper()

  def kmenuedit(args: String*) =
    clitools.KmenueditWrapper(CmdArgs(args.toVector))
  def kmenuedit = clitools.KmenueditWrapper()

  def kmimetypefinder5(args: String*) =
    clitools.Kmimetypefinder5Wrapper(CmdArgs(args.toVector))
  def kmimetypefinder5 = clitools.Kmimetypefinder5Wrapper()

  def kmod(args: String*) = clitools.KmodWrapper(CmdArgs(args.toVector))
  def kmod = clitools.KmodWrapper()

  def knetattach(args: String*) =
    clitools.KnetattachWrapper(CmdArgs(args.toVector))
  def knetattach = clitools.KnetattachWrapper()

  def koi8rxterm(args: String*) =
    clitools.Koi8rxtermWrapper(CmdArgs(args.toVector))
  def koi8rxterm = clitools.Koi8rxtermWrapper()

  def konsole(args: String*) = clitools.KonsoleWrapper(CmdArgs(args.toVector))
  def konsole = clitools.KonsoleWrapper()

  def konsoleprofile(args: String*) =
    clitools.KonsoleprofileWrapper(CmdArgs(args.toVector))
  def konsoleprofile = clitools.KonsoleprofileWrapper()

  def kpackagelauncherqml(args: String*) =
    clitools.KpackagelauncherqmlWrapper(CmdArgs(args.toVector))
  def kpackagelauncherqml = clitools.KpackagelauncherqmlWrapper()

  def kpackagetool5(args: String*) =
    clitools.Kpackagetool5Wrapper(CmdArgs(args.toVector))
  def kpackagetool5 = clitools.Kpackagetool5Wrapper()

  def kpasswd(args: String*) = clitools.KpasswdWrapper(CmdArgs(args.toVector))
  def kpasswd = clitools.KpasswdWrapper()

  def kprop(args: String*) = clitools.KpropWrapper(CmdArgs(args.toVector))
  def kprop = clitools.KpropWrapper()

  def kpropd(args: String*) = clitools.KpropdWrapper(CmdArgs(args.toVector))
  def kpropd = clitools.KpropdWrapper()

  def kproplog(args: String*) = clitools.KproplogWrapper(CmdArgs(args.toVector))
  def kproplog = clitools.KproplogWrapper()

  def kquitapp5(args: String*) =
    clitools.Kquitapp5Wrapper(CmdArgs(args.toVector))
  def kquitapp5 = clitools.Kquitapp5Wrapper()

  def krb5_send_pr(args: String*) =
    clitools.Krb5_send_prWrapper(CmdArgs(args.toVector))
  def krb5_send_pr = clitools.Krb5_send_prWrapper()

  def krb5kdc(args: String*) = clitools.Krb5kdcWrapper(CmdArgs(args.toVector))
  def krb5kdc = clitools.Krb5kdcWrapper()

  def krdb(args: String*) = clitools.KrdbWrapper(CmdArgs(args.toVector))
  def krdb = clitools.KrdbWrapper()

  def kreadconfig5(args: String*) =
    clitools.Kreadconfig5Wrapper(CmdArgs(args.toVector))
  def kreadconfig5 = clitools.Kreadconfig5Wrapper()

  def krita(args: String*) = clitools.KritaWrapper(CmdArgs(args.toVector))
  def krita = clitools.KritaWrapper()

  def kritarunner(args: String*) =
    clitools.KritarunnerWrapper(CmdArgs(args.toVector))
  def kritarunner = clitools.KritarunnerWrapper()

  def krunner(args: String*) = clitools.KrunnerWrapper(CmdArgs(args.toVector))
  def krunner = clitools.KrunnerWrapper()

  def kscreen_console(args: String*) =
    clitools.Kscreen_consoleWrapper(CmdArgs(args.toVector))
  def kscreen_console = clitools.Kscreen_consoleWrapper()

  def kscreen_doctor(args: String*) =
    clitools.Kscreen_doctorWrapper(CmdArgs(args.toVector))
  def kscreen_doctor = clitools.Kscreen_doctorWrapper()

  def kshell5(args: String*) = clitools.Kshell5Wrapper(CmdArgs(args.toVector))
  def kshell5 = clitools.Kshell5Wrapper()

  def ksmserver(args: String*) =
    clitools.KsmserverWrapper(CmdArgs(args.toVector))
  def ksmserver = clitools.KsmserverWrapper()

  def ksplashqml(args: String*) =
    clitools.KsplashqmlWrapper(CmdArgs(args.toVector))
  def ksplashqml = clitools.KsplashqmlWrapper()

  def kstart5(args: String*) = clitools.Kstart5Wrapper(CmdArgs(args.toVector))
  def kstart5 = clitools.Kstart5Wrapper()

  def kstartupconfig5(args: String*) =
    clitools.Kstartupconfig5Wrapper(CmdArgs(args.toVector))
  def kstartupconfig5 = clitools.Kstartupconfig5Wrapper()

  def ksu(args: String*) = clitools.KsuWrapper(CmdArgs(args.toVector))
  def ksu = clitools.KsuWrapper()

  def ksvgtopng5(args: String*) =
    clitools.Ksvgtopng5Wrapper(CmdArgs(args.toVector))
  def ksvgtopng5 = clitools.Ksvgtopng5Wrapper()

  def kswitch(args: String*) = clitools.KswitchWrapper(CmdArgs(args.toVector))
  def kswitch = clitools.KswitchWrapper()

  def ksysguard(args: String*) =
    clitools.KsysguardWrapper(CmdArgs(args.toVector))
  def ksysguard = clitools.KsysguardWrapper()

  def ksysguardd(args: String*) =
    clitools.KsysguarddWrapper(CmdArgs(args.toVector))
  def ksysguardd = clitools.KsysguarddWrapper()

  def ktelnetservice5(args: String*) =
    clitools.Ktelnetservice5Wrapper(CmdArgs(args.toVector))
  def ktelnetservice5 = clitools.Ktelnetservice5Wrapper()

  def ktraderclient5(args: String*) =
    clitools.Ktraderclient5Wrapper(CmdArgs(args.toVector))
  def ktraderclient5 = clitools.Ktraderclient5Wrapper()

  def ktrash5(args: String*) = clitools.Ktrash5Wrapper(CmdArgs(args.toVector))
  def ktrash5 = clitools.Ktrash5Wrapper()

  def ktutil(args: String*) = clitools.KtutilWrapper(CmdArgs(args.toVector))
  def ktutil = clitools.KtutilWrapper()

  def kvno(args: String*) = clitools.KvnoWrapper(CmdArgs(args.toVector))
  def kvno = clitools.KvnoWrapper()

  def kwallet_query(args: String*) =
    clitools.Kwallet_queryWrapper(CmdArgs(args.toVector))
  def kwallet_query = clitools.Kwallet_queryWrapper()

  def kwalletd5(args: String*) =
    clitools.Kwalletd5Wrapper(CmdArgs(args.toVector))
  def kwalletd5 = clitools.Kwalletd5Wrapper()

  def kwalletmanager5(args: String*) =
    clitools.Kwalletmanager5Wrapper(CmdArgs(args.toVector))
  def kwalletmanager5 = clitools.Kwalletmanager5Wrapper()

  def kwin_wayland(args: String*) =
    clitools.Kwin_waylandWrapper(CmdArgs(args.toVector))
  def kwin_wayland = clitools.Kwin_waylandWrapper()

  def kwin_x11(args: String*) = clitools.Kwin_x11Wrapper(CmdArgs(args.toVector))
  def kwin_x11 = clitools.Kwin_x11Wrapper()

  def kwrapper5(args: String*) =
    clitools.Kwrapper5Wrapper(CmdArgs(args.toVector))
  def kwrapper5 = clitools.Kwrapper5Wrapper()

  def kwrite(args: String*) = clitools.KwriteWrapper(CmdArgs(args.toVector))
  def kwrite = clitools.KwriteWrapper()

  def kwriteconfig5(args: String*) =
    clitools.Kwriteconfig5Wrapper(CmdArgs(args.toVector))
  def kwriteconfig5 = clitools.Kwriteconfig5Wrapper()

  def kwrited(args: String*) = clitools.KwritedWrapper(CmdArgs(args.toVector))
  def kwrited = clitools.KwritedWrapper()

  def last(args: String*) = clitools.LastWrapper(CmdArgs(args.toVector))
  def last = clitools.LastWrapper()

  def lastb(args: String*) = clitools.LastbWrapper(CmdArgs(args.toVector))
  def lastb = clitools.LastbWrapper()

  def lastlog(args: String*) = clitools.LastlogWrapper(CmdArgs(args.toVector))
  def lastlog = clitools.LastlogWrapper()

  def ld(args: String*) = clitools.LdWrapper(CmdArgs(args.toVector))
  def ld = clitools.LdWrapper()

  def ldattach(args: String*) = clitools.LdattachWrapper(CmdArgs(args.toVector))
  def ldattach = clitools.LdattachWrapper()

  def ldconfig(args: String*) = clitools.LdconfigWrapper(CmdArgs(args.toVector))
  def ldconfig = clitools.LdconfigWrapper()

  def ldd(args: String*) = clitools.LddWrapper(CmdArgs(args.toVector))
  def ldd = clitools.LddWrapper()

  def lefty(args: String*) = clitools.LeftyWrapper(CmdArgs(args.toVector))
  def lefty = clitools.LeftyWrapper()

  def less(args: String*) = clitools.LessWrapper(CmdArgs(args.toVector))
  def less = clitools.LessWrapper()

  def lessecho(args: String*) = clitools.LessechoWrapper(CmdArgs(args.toVector))
  def lessecho = clitools.LessechoWrapper()

  def lesskey(args: String*) = clitools.LesskeyWrapper(CmdArgs(args.toVector))
  def lesskey = clitools.LesskeyWrapper()

  def let(args: String*) = clitools.LetWrapper(CmdArgs(args.toVector))
  def let = clitools.LetWrapper()

  def lexgrog(args: String*) = clitools.LexgrogWrapper(CmdArgs(args.toVector))
  def lexgrog = clitools.LexgrogWrapper()

  def libnetcfg(args: String*) =
    clitools.LibnetcfgWrapper(CmdArgs(args.toVector))
  def libnetcfg = clitools.LibnetcfgWrapper()

  def libreoffice(args: String*) =
    clitools.LibreofficeWrapper(CmdArgs(args.toVector))
  def libreoffice = clitools.LibreofficeWrapper()

  def libwmf_config(args: String*) =
    clitools.Libwmf_configWrapper(CmdArgs(args.toVector))
  def libwmf_config = clitools.Libwmf_configWrapper()

  def libwmf_fontmap(args: String*) =
    clitools.Libwmf_fontmapWrapper(CmdArgs(args.toVector))
  def libwmf_fontmap = clitools.Libwmf_fontmapWrapper()

  def light(args: String*) = clitools.LightWrapper(CmdArgs(args.toVector))
  def light = clitools.LightWrapper()

  def lindna(args: String*) = clitools.LindnaWrapper(CmdArgs(args.toVector))
  def lindna = clitools.LindnaWrapper()

  def link(args: String*) = clitools.LinkWrapper(CmdArgs(args.toVector))
  def link = clitools.LinkWrapper()

  def linksbed(args: String*) = clitools.LinksBedWrapper(CmdArgs(args.toVector))
  def linksbed = clitools.LinksBedWrapper()

  def linux32(args: String*) = clitools.Linux32Wrapper(CmdArgs(args.toVector))
  def linux32 = clitools.Linux32Wrapper()

  def linux64(args: String*) = clitools.Linux64Wrapper(CmdArgs(args.toVector))
  def linux64 = clitools.Linux64Wrapper()

  def listor(args: String*) = clitools.ListorWrapper(CmdArgs(args.toVector))
  def listor = clitools.ListorWrapper()

  def ln(args: String*) = clitools.LnWrapper(CmdArgs(args.toVector))
  def ln = clitools.LnWrapper()

  def lneato(args: String*) = clitools.LneatoWrapper(CmdArgs(args.toVector))
  def lneato = clitools.LneatoWrapper()

  def lnstat(args: String*) = clitools.LnstatWrapper(CmdArgs(args.toVector))
  def lnstat = clitools.LnstatWrapper()

  def loadkeys(args: String*) = clitools.LoadkeysWrapper(CmdArgs(args.toVector))
  def loadkeys = clitools.LoadkeysWrapper()

  def loadunimap(args: String*) =
    clitools.LoadunimapWrapper(CmdArgs(args.toVector))
  def loadunimap = clitools.LoadunimapWrapper()

  def local(args: String*) = clitools.LocalWrapper(CmdArgs(args.toVector))
  def local = clitools.LocalWrapper()

  def locale(args: String*) = clitools.LocaleWrapper(CmdArgs(args.toVector))
  def locale = clitools.LocaleWrapper()

  def localectl(args: String*) =
    clitools.LocalectlWrapper(CmdArgs(args.toVector))
  def localectl = clitools.LocalectlWrapper()

  def localedef(args: String*) =
    clitools.LocaledefWrapper(CmdArgs(args.toVector))
  def localedef = clitools.LocaledefWrapper()

  def locate(args: String*) = clitools.LocateWrapper(CmdArgs(args.toVector))
  def locate = clitools.LocateWrapper()

  def logger(args: String*) = clitools.LoggerWrapper(CmdArgs(args.toVector))
  def logger = clitools.LoggerWrapper()

  def login(args: String*) = clitools.LoginWrapper(CmdArgs(args.toVector))
  def login = clitools.LoginWrapper()

  def loginctl(args: String*) = clitools.LoginctlWrapper(CmdArgs(args.toVector))
  def loginctl = clitools.LoginctlWrapper()

  def logname(args: String*) = clitools.LognameWrapper(CmdArgs(args.toVector))
  def logname = clitools.LognameWrapper()

  def logout(args: String*) = clitools.LogoutWrapper(CmdArgs(args.toVector))
  def logout = clitools.LogoutWrapper()

  def logoutd(args: String*) = clitools.LogoutdWrapper(CmdArgs(args.toVector))
  def logoutd = clitools.LogoutdWrapper()

  def logsave(args: String*) = clitools.LogsaveWrapper(CmdArgs(args.toVector))
  def logsave = clitools.LogsaveWrapper()

  def look(args: String*) = clitools.LookWrapper(CmdArgs(args.toVector))
  def look = clitools.LookWrapper()

  def lookandfeeltool(args: String*) =
    clitools.LookandfeeltoolWrapper(CmdArgs(args.toVector))
  def lookandfeeltool = clitools.LookandfeeltoolWrapper()

  def losetup(args: String*) = clitools.LosetupWrapper(CmdArgs(args.toVector))
  def losetup = clitools.LosetupWrapper()

  def lp(args: String*) = clitools.LpWrapper(CmdArgs(args.toVector))
  def lp = clitools.LpWrapper()

  def lpadmin(args: String*) = clitools.LpadminWrapper(CmdArgs(args.toVector))
  def lpadmin = clitools.LpadminWrapper()

  def lpc(args: String*) = clitools.LpcWrapper(CmdArgs(args.toVector))
  def lpc = clitools.LpcWrapper()

  def lpinfo(args: String*) = clitools.LpinfoWrapper(CmdArgs(args.toVector))
  def lpinfo = clitools.LpinfoWrapper()

  def lpmove(args: String*) = clitools.LpmoveWrapper(CmdArgs(args.toVector))
  def lpmove = clitools.LpmoveWrapper()

  def lpoptions(args: String*) =
    clitools.LpoptionsWrapper(CmdArgs(args.toVector))
  def lpoptions = clitools.LpoptionsWrapper()

  def lpq(args: String*) = clitools.LpqWrapper(CmdArgs(args.toVector))
  def lpq = clitools.LpqWrapper()

  def lpr(args: String*) = clitools.LprWrapper(CmdArgs(args.toVector))
  def lpr = clitools.LprWrapper()

  def lprm(args: String*) = clitools.LprmWrapper(CmdArgs(args.toVector))
  def lprm = clitools.LprmWrapper()

  def lpstat(args: String*) = clitools.LpstatWrapper(CmdArgs(args.toVector))
  def lpstat = clitools.LpstatWrapper()

  def ls(args: String*) = clitools.LsWrapper(CmdArgs(args.toVector))
  def ls = clitools.LsWrapper()

  def lsattr(args: String*) = clitools.LsattrWrapper(CmdArgs(args.toVector))
  def lsattr = clitools.LsattrWrapper()

  def lsblk(args: String*) = clitools.LsblkWrapper(CmdArgs(args.toVector))
  def lsblk = clitools.LsblkWrapper()

  def lscpu(args: String*) = clitools.LscpuWrapper(CmdArgs(args.toVector))
  def lscpu = clitools.LscpuWrapper()

  def lsipc(args: String*) = clitools.LsipcWrapper(CmdArgs(args.toVector))
  def lsipc = clitools.LsipcWrapper()

  def lslocks(args: String*) = clitools.LslocksWrapper(CmdArgs(args.toVector))
  def lslocks = clitools.LslocksWrapper()

  def lslogins(args: String*) = clitools.LsloginsWrapper(CmdArgs(args.toVector))
  def lslogins = clitools.LsloginsWrapper()

  def lsmem(args: String*) = clitools.LsmemWrapper(CmdArgs(args.toVector))
  def lsmem = clitools.LsmemWrapper()

  def lsmod(args: String*) = clitools.LsmodWrapper(CmdArgs(args.toVector))
  def lsmod = clitools.LsmodWrapper()

  def lsns(args: String*) = clitools.LsnsWrapper(CmdArgs(args.toVector))
  def lsns = clitools.LsnsWrapper()

  def lsof(args: String*) = clitools.LsofWrapper(CmdArgs(args.toVector))
  def lsof = clitools.LsofWrapper()

  def lvchange(args: String*) = clitools.LvchangeWrapper(CmdArgs(args.toVector))
  def lvchange = clitools.LvchangeWrapper()

  def lvconvert(args: String*) =
    clitools.LvconvertWrapper(CmdArgs(args.toVector))
  def lvconvert = clitools.LvconvertWrapper()

  def lvcreate(args: String*) = clitools.LvcreateWrapper(CmdArgs(args.toVector))
  def lvcreate = clitools.LvcreateWrapper()

  def lvdisplay(args: String*) =
    clitools.LvdisplayWrapper(CmdArgs(args.toVector))
  def lvdisplay = clitools.LvdisplayWrapper()

  def lvextend(args: String*) = clitools.LvextendWrapper(CmdArgs(args.toVector))
  def lvextend = clitools.LvextendWrapper()

  def lvm(args: String*) = clitools.LvmWrapper(CmdArgs(args.toVector))
  def lvm = clitools.LvmWrapper()

  def lvmconfig(args: String*) =
    clitools.LvmconfigWrapper(CmdArgs(args.toVector))
  def lvmconfig = clitools.LvmconfigWrapper()

  def lvmdiskscan(args: String*) =
    clitools.LvmdiskscanWrapper(CmdArgs(args.toVector))
  def lvmdiskscan = clitools.LvmdiskscanWrapper()

  def lvmdump(args: String*) = clitools.LvmdumpWrapper(CmdArgs(args.toVector))
  def lvmdump = clitools.LvmdumpWrapper()

  def lvmsadc(args: String*) = clitools.LvmsadcWrapper(CmdArgs(args.toVector))
  def lvmsadc = clitools.LvmsadcWrapper()

  def lvmsar(args: String*) = clitools.LvmsarWrapper(CmdArgs(args.toVector))
  def lvmsar = clitools.LvmsarWrapper()

  def lvreduce(args: String*) = clitools.LvreduceWrapper(CmdArgs(args.toVector))
  def lvreduce = clitools.LvreduceWrapper()

  def lvremove(args: String*) = clitools.LvremoveWrapper(CmdArgs(args.toVector))
  def lvremove = clitools.LvremoveWrapper()

  def lvrename(args: String*) = clitools.LvrenameWrapper(CmdArgs(args.toVector))
  def lvrename = clitools.LvrenameWrapper()

  def lvresize(args: String*) = clitools.LvresizeWrapper(CmdArgs(args.toVector))
  def lvresize = clitools.LvresizeWrapper()

  def lvs(args: String*) = clitools.LvsWrapper(CmdArgs(args.toVector))
  def lvs = clitools.LvsWrapper()

  def lvscan(args: String*) = clitools.LvscanWrapper(CmdArgs(args.toVector))
  def lvscan = clitools.LvscanWrapper()

  def lzcat(args: String*) = clitools.LzcatWrapper(CmdArgs(args.toVector))
  def lzcat = clitools.LzcatWrapper()

  def lzcmp(args: String*) = clitools.LzcmpWrapper(CmdArgs(args.toVector))
  def lzcmp = clitools.LzcmpWrapper()

  def lzdiff(args: String*) = clitools.LzdiffWrapper(CmdArgs(args.toVector))
  def lzdiff = clitools.LzdiffWrapper()

  def lzegrep(args: String*) = clitools.LzegrepWrapper(CmdArgs(args.toVector))
  def lzegrep = clitools.LzegrepWrapper()

  def lzfgrep(args: String*) = clitools.LzfgrepWrapper(CmdArgs(args.toVector))
  def lzfgrep = clitools.LzfgrepWrapper()

  def lzgrep(args: String*) = clitools.LzgrepWrapper(CmdArgs(args.toVector))
  def lzgrep = clitools.LzgrepWrapper()

  def lzless(args: String*) = clitools.LzlessWrapper(CmdArgs(args.toVector))
  def lzless = clitools.LzlessWrapper()

  def lzma(args: String*) = clitools.LzmaWrapper(CmdArgs(args.toVector))
  def lzma = clitools.LzmaWrapper()

  def lzmadec(args: String*) = clitools.LzmadecWrapper(CmdArgs(args.toVector))
  def lzmadec = clitools.LzmadecWrapper()

  def lzmainfo(args: String*) = clitools.LzmainfoWrapper(CmdArgs(args.toVector))
  def lzmainfo = clitools.LzmainfoWrapper()

  def lzmore(args: String*) = clitools.LzmoreWrapper(CmdArgs(args.toVector))
  def lzmore = clitools.LzmoreWrapper()

  def machinectl(args: String*) =
    clitools.MachinectlWrapper(CmdArgs(args.toVector))
  def machinectl = clitools.MachinectlWrapper()

  def make_bcache(args: String*) =
    clitools.Make_bcacheWrapper(CmdArgs(args.toVector))
  def make_bcache = clitools.Make_bcacheWrapper()

  def makedb(args: String*) = clitools.MakedbWrapper(CmdArgs(args.toVector))
  def makedb = clitools.MakedbWrapper()

  def makehmmerdb(args: String*) =
    clitools.MakehmmerdbWrapper(CmdArgs(args.toVector))
  def makehmmerdb = clitools.MakehmmerdbWrapper()

  def makeinfo(args: String*) = clitools.MakeinfoWrapper(CmdArgs(args.toVector))
  def makeinfo = clitools.MakeinfoWrapper()

  def makenucseq(args: String*) =
    clitools.MakenucseqWrapper(CmdArgs(args.toVector))
  def makenucseq = clitools.MakenucseqWrapper()

  def makeprotseq(args: String*) =
    clitools.MakeprotseqWrapper(CmdArgs(args.toVector))
  def makeprotseq = clitools.MakeprotseqWrapper()

  def man(args: String*) = clitools.ManWrapper(CmdArgs(args.toVector))
  def man = clitools.ManWrapper()

  def mandb(args: String*) = clitools.MandbWrapper(CmdArgs(args.toVector))
  def mandb = clitools.MandbWrapper()

  def manpath(args: String*) = clitools.ManpathWrapper(CmdArgs(args.toVector))
  def manpath = clitools.ManpathWrapper()

  def mapbed(args: String*) = clitools.MapBedWrapper(CmdArgs(args.toVector))
  def mapbed = clitools.MapBedWrapper()

  def mapfile(args: String*) = clitools.MapfileWrapper(CmdArgs(args.toVector))
  def mapfile = clitools.MapfileWrapper()

  def mapscrn(args: String*) = clitools.MapscrnWrapper(CmdArgs(args.toVector))
  def mapscrn = clitools.MapscrnWrapper()

  def maq2sam_long(args: String*) =
    clitools.Maq2sam_longWrapper(CmdArgs(args.toVector))
  def maq2sam_long = clitools.Maq2sam_longWrapper()

  def maq2sam_short(args: String*) =
    clitools.Maq2sam_shortWrapper(CmdArgs(args.toVector))
  def maq2sam_short = clitools.Maq2sam_shortWrapper()

  def marscan(args: String*) = clitools.MarscanWrapper(CmdArgs(args.toVector))
  def marscan = clitools.MarscanWrapper()

  def maskfastafrombed(args: String*) =
    clitools.MaskFastaFromBedWrapper(CmdArgs(args.toVector))
  def maskfastafrombed = clitools.MaskFastaFromBedWrapper()

  def maskambignuc(args: String*) =
    clitools.MaskambignucWrapper(CmdArgs(args.toVector))
  def maskambignuc = clitools.MaskambignucWrapper()

  def maskambigprot(args: String*) =
    clitools.MaskambigprotWrapper(CmdArgs(args.toVector))
  def maskambigprot = clitools.MaskambigprotWrapper()

  def maskfeat(args: String*) = clitools.MaskfeatWrapper(CmdArgs(args.toVector))
  def maskfeat = clitools.MaskfeatWrapper()

  def maskseq(args: String*) = clitools.MaskseqWrapper(CmdArgs(args.toVector))
  def maskseq = clitools.MaskseqWrapper()

  def matcher(args: String*) = clitools.MatcherWrapper(CmdArgs(args.toVector))
  def matcher = clitools.MatcherWrapper()

  def mb(args: String*) = clitools.MbWrapper(CmdArgs(args.toVector))
  def mb = clitools.MbWrapper()

  def mcookie(args: String*) = clitools.McookieWrapper(CmdArgs(args.toVector))
  def mcookie = clitools.McookieWrapper()

  def md5fa(args: String*) = clitools.Md5faWrapper(CmdArgs(args.toVector))
  def md5fa = clitools.Md5faWrapper()

  def md5sum(args: String*) = clitools.Md5sumWrapper(CmdArgs(args.toVector))
  def md5sum = clitools.Md5sumWrapper()

  def md5sum_lite(args: String*) =
    clitools.Md5sum_liteWrapper(CmdArgs(args.toVector))
  def md5sum_lite = clitools.Md5sum_liteWrapper()

  def mdadm(args: String*) = clitools.MdadmWrapper(CmdArgs(args.toVector))
  def mdadm = clitools.MdadmWrapper()

  def mdmon(args: String*) = clitools.MdmonWrapper(CmdArgs(args.toVector))
  def mdmon = clitools.MdmonWrapper()

  def megamerger(args: String*) =
    clitools.MegamergerWrapper(CmdArgs(args.toVector))
  def megamerger = clitools.MegamergerWrapper()

  def meinproc5(args: String*) =
    clitools.Meinproc5Wrapper(CmdArgs(args.toVector))
  def meinproc5 = clitools.Meinproc5Wrapper()

  def meld(args: String*) = clitools.MeldWrapper(CmdArgs(args.toVector))
  def meld = clitools.MeldWrapper()

  def mencoder(args: String*) = clitools.MencoderWrapper(CmdArgs(args.toVector))
  def mencoder = clitools.MencoderWrapper()

  def mergebed(args: String*) = clitools.MergeBedWrapper(CmdArgs(args.toVector))
  def mergebed = clitools.MergeBedWrapper()

  def merger(args: String*) = clitools.MergerWrapper(CmdArgs(args.toVector))
  def merger = clitools.MergerWrapper()

  def mesg(args: String*) = clitools.MesgWrapper(CmdArgs(args.toVector))
  def mesg = clitools.MesgWrapper()

  def mk_cmds(args: String*) = clitools.Mk_cmdsWrapper(CmdArgs(args.toVector))
  def mk_cmds = clitools.Mk_cmdsWrapper()

  def mkdir(args: String*) = clitools.MkdirWrapper(CmdArgs(args.toVector))
  def mkdir = clitools.MkdirWrapper()

  def mkdosfs(args: String*) = clitools.MkdosfsWrapper(CmdArgs(args.toVector))
  def mkdosfs = clitools.MkdosfsWrapper()

  def mke2fs(args: String*) = clitools.Mke2fsWrapper(CmdArgs(args.toVector))
  def mke2fs = clitools.Mke2fsWrapper()

  def mkfifo(args: String*) = clitools.MkfifoWrapper(CmdArgs(args.toVector))
  def mkfifo = clitools.MkfifoWrapper()

  def mkfs(args: String*) = clitools.MkfsWrapper(CmdArgs(args.toVector))
  def mkfs = clitools.MkfsWrapper()

  def mkhomedir_helper(args: String*) =
    clitools.Mkhomedir_helperWrapper(CmdArgs(args.toVector))
  def mkhomedir_helper = clitools.Mkhomedir_helperWrapper()

  def mknod(args: String*) = clitools.MknodWrapper(CmdArgs(args.toVector))
  def mknod = clitools.MknodWrapper()

  def mkpasswd(args: String*) = clitools.MkpasswdWrapper(CmdArgs(args.toVector))
  def mkpasswd = clitools.MkpasswdWrapper()

  def mkswap(args: String*) = clitools.MkswapWrapper(CmdArgs(args.toVector))
  def mkswap = clitools.MkswapWrapper()

  def mktemp(args: String*) = clitools.MktempWrapper(CmdArgs(args.toVector))
  def mktemp = clitools.MktempWrapper()

  def mm2gv(args: String*) = clitools.Mm2gvWrapper(CmdArgs(args.toVector))
  def mm2gv = clitools.Mm2gvWrapper()

  def mmcli(args: String*) = clitools.MmcliWrapper(CmdArgs(args.toVector))
  def mmcli = clitools.MmcliWrapper()

  def modinfo(args: String*) = clitools.ModinfoWrapper(CmdArgs(args.toVector))
  def modinfo = clitools.ModinfoWrapper()

  def modprobe(args: String*) = clitools.ModprobeWrapper(CmdArgs(args.toVector))
  def modprobe = clitools.ModprobeWrapper()

  def mogrify(args: String*) = clitools.MogrifyWrapper(CmdArgs(args.toVector))
  def mogrify = clitools.MogrifyWrapper()

  def montage(args: String*) = clitools.MontageWrapper(CmdArgs(args.toVector))
  def montage = clitools.MontageWrapper()

  def more(args: String*) = clitools.MoreWrapper(CmdArgs(args.toVector))
  def more = clitools.MoreWrapper()

  def mosdepth(args: String*) = clitools.MosdepthWrapper(CmdArgs(args.toVector))
  def mosdepth = clitools.MosdepthWrapper()

  def mount(args: String*) = clitools.MountWrapper(CmdArgs(args.toVector))
  def mount = clitools.MountWrapper()

  def mountpoint(args: String*) =
    clitools.MountpointWrapper(CmdArgs(args.toVector))
  def mountpoint = clitools.MountpointWrapper()

  def mplayer(args: String*) = clitools.MplayerWrapper(CmdArgs(args.toVector))
  def mplayer = clitools.MplayerWrapper()

  def msbar(args: String*) = clitools.MsbarWrapper(CmdArgs(args.toVector))
  def msbar = clitools.MsbarWrapper()

  def mtpfs(args: String*) = clitools.MtpfsWrapper(CmdArgs(args.toVector))
  def mtpfs = clitools.MtpfsWrapper()

  def multibamcov(args: String*) =
    clitools.MultiBamCovWrapper(CmdArgs(args.toVector))
  def multibamcov = clitools.MultiBamCovWrapper()

  def multiintersectbed(args: String*) =
    clitools.MultiIntersectBedWrapper(CmdArgs(args.toVector))
  def multiintersectbed = clitools.MultiIntersectBedWrapper()

  def muscle(args: String*) = clitools.MuscleWrapper(CmdArgs(args.toVector))
  def muscle = clitools.MuscleWrapper()

  def mv(args: String*) = clitools.MvWrapper(CmdArgs(args.toVector))
  def mv = clitools.MvWrapper()

  def mvn(args: String*) = clitools.MvnWrapper(CmdArgs(args.toVector))
  def mvn = clitools.MvnWrapper()

  def mwcontam(args: String*) = clitools.MwcontamWrapper(CmdArgs(args.toVector))
  def mwcontam = clitools.MwcontamWrapper()

  def mwfilter(args: String*) = clitools.MwfilterWrapper(CmdArgs(args.toVector))
  def mwfilter = clitools.MwfilterWrapper()

  def namei(args: String*) = clitools.NameiWrapper(CmdArgs(args.toVector))
  def namei = clitools.NameiWrapper()

  def nameif(args: String*) = clitools.NameifWrapper(CmdArgs(args.toVector))
  def nameif = clitools.NameifWrapper()

  def nano(args: String*) = clitools.NanoWrapper(CmdArgs(args.toVector))
  def nano = clitools.NanoWrapper()

  def nc(args: String*) = clitools.NcWrapper(CmdArgs(args.toVector))
  def nc = clitools.NcWrapper()

  def neato(args: String*) = clitools.NeatoWrapper(CmdArgs(args.toVector))
  def neato = clitools.NeatoWrapper()

  def needle(args: String*) = clitools.NeedleWrapper(CmdArgs(args.toVector))
  def needle = clitools.NeedleWrapper()

  def needleall(args: String*) =
    clitools.NeedleallWrapper(CmdArgs(args.toVector))
  def needleall = clitools.NeedleallWrapper()

  def netbeans(args: String*) = clitools.NetbeansWrapper(CmdArgs(args.toVector))
  def netbeans = clitools.NetbeansWrapper()

  def netstat(args: String*) = clitools.NetstatWrapper(CmdArgs(args.toVector))
  def netstat = clitools.NetstatWrapper()

  def networkctl(args: String*) =
    clitools.NetworkctlWrapper(CmdArgs(args.toVector))
  def networkctl = clitools.NetworkctlWrapper()

  def newcpgreport(args: String*) =
    clitools.NewcpgreportWrapper(CmdArgs(args.toVector))
  def newcpgreport = clitools.NewcpgreportWrapper()

  def newcpgseek(args: String*) =
    clitools.NewcpgseekWrapper(CmdArgs(args.toVector))
  def newcpgseek = clitools.NewcpgseekWrapper()

  def newgidmap(args: String*) =
    clitools.NewgidmapWrapper(CmdArgs(args.toVector))
  def newgidmap = clitools.NewgidmapWrapper()

  def newgrp(args: String*) = clitools.NewgrpWrapper(CmdArgs(args.toVector))
  def newgrp = clitools.NewgrpWrapper()

  def newseq(args: String*) = clitools.NewseqWrapper(CmdArgs(args.toVector))
  def newseq = clitools.NewseqWrapper()

  def newuidmap(args: String*) =
    clitools.NewuidmapWrapper(CmdArgs(args.toVector))
  def newuidmap = clitools.NewuidmapWrapper()

  def newusers(args: String*) = clitools.NewusersWrapper(CmdArgs(args.toVector))
  def newusers = clitools.NewusersWrapper()

  def nfbpf_compile(args: String*) =
    clitools.Nfbpf_compileWrapper(CmdArgs(args.toVector))
  def nfbpf_compile = clitools.Nfbpf_compileWrapper()

  def nfnl_osf(args: String*) = clitools.Nfnl_osfWrapper(CmdArgs(args.toVector))
  def nfnl_osf = clitools.Nfnl_osfWrapper()

  def nhmmer(args: String*) = clitools.NhmmerWrapper(CmdArgs(args.toVector))
  def nhmmer = clitools.NhmmerWrapper()

  def nhmmscan(args: String*) = clitools.NhmmscanWrapper(CmdArgs(args.toVector))
  def nhmmscan = clitools.NhmmscanWrapper()

  def nice(args: String*) = clitools.NiceWrapper(CmdArgs(args.toVector))
  def nice = clitools.NiceWrapper()

  def ninfod(args: String*) = clitools.NinfodWrapper(CmdArgs(args.toVector))
  def ninfod = clitools.NinfodWrapper()

  def nisdomainname(args: String*) =
    clitools.NisdomainnameWrapper(CmdArgs(args.toVector))
  def nisdomainname = clitools.NisdomainnameWrapper()

  def nix(args: String*) = clitools.NixWrapper(CmdArgs(args.toVector))
  def nix = clitools.NixWrapper()

  def nix_build(args: String*) =
    clitools.Nix_buildWrapper(CmdArgs(args.toVector))
  def nix_build = clitools.Nix_buildWrapper()

  def nix_channel(args: String*) =
    clitools.Nix_channelWrapper(CmdArgs(args.toVector))
  def nix_channel = clitools.Nix_channelWrapper()

  def nix_collect_garbage(args: String*) =
    clitools.Nix_collect_garbageWrapper(CmdArgs(args.toVector))
  def nix_collect_garbage = clitools.Nix_collect_garbageWrapper()

  def nix_copy_closure(args: String*) =
    clitools.Nix_copy_closureWrapper(CmdArgs(args.toVector))
  def nix_copy_closure = clitools.Nix_copy_closureWrapper()

  def nix_daemon(args: String*) =
    clitools.Nix_daemonWrapper(CmdArgs(args.toVector))
  def nix_daemon = clitools.Nix_daemonWrapper()

  def nix_env(args: String*) = clitools.Nix_envWrapper(CmdArgs(args.toVector))
  def nix_env = clitools.Nix_envWrapper()

  def nix_hash(args: String*) = clitools.Nix_hashWrapper(CmdArgs(args.toVector))
  def nix_hash = clitools.Nix_hashWrapper()

  def nix_info(args: String*) = clitools.Nix_infoWrapper(CmdArgs(args.toVector))
  def nix_info = clitools.Nix_infoWrapper()

  def nix_instantiate(args: String*) =
    clitools.Nix_instantiateWrapper(CmdArgs(args.toVector))
  def nix_instantiate = clitools.Nix_instantiateWrapper()

  def nix_prefetch_url(args: String*) =
    clitools.Nix_prefetch_urlWrapper(CmdArgs(args.toVector))
  def nix_prefetch_url = clitools.Nix_prefetch_urlWrapper()

  def nix_shell(args: String*) =
    clitools.Nix_shellWrapper(CmdArgs(args.toVector))
  def nix_shell = clitools.Nix_shellWrapper()

  def nix_store(args: String*) =
    clitools.Nix_storeWrapper(CmdArgs(args.toVector))
  def nix_store = clitools.Nix_storeWrapper()

  def nixos_build_vms(args: String*) =
    clitools.Nixos_build_vmsWrapper(CmdArgs(args.toVector))
  def nixos_build_vms = clitools.Nixos_build_vmsWrapper()

  def nixos_container(args: String*) =
    clitools.Nixos_containerWrapper(CmdArgs(args.toVector))
  def nixos_container = clitools.Nixos_containerWrapper()

  def nixos_enter(args: String*) =
    clitools.Nixos_enterWrapper(CmdArgs(args.toVector))
  def nixos_enter = clitools.Nixos_enterWrapper()

  def nixos_generate_config(args: String*) =
    clitools.Nixos_generate_configWrapper(CmdArgs(args.toVector))
  def nixos_generate_config = clitools.Nixos_generate_configWrapper()

  def nixos_help(args: String*) =
    clitools.Nixos_helpWrapper(CmdArgs(args.toVector))
  def nixos_help = clitools.Nixos_helpWrapper()

  def nixos_install(args: String*) =
    clitools.Nixos_installWrapper(CmdArgs(args.toVector))
  def nixos_install = clitools.Nixos_installWrapper()

  def nixos_option(args: String*) =
    clitools.Nixos_optionWrapper(CmdArgs(args.toVector))
  def nixos_option = clitools.Nixos_optionWrapper()

  def nixos_rebuild(args: String*) =
    clitools.Nixos_rebuildWrapper(CmdArgs(args.toVector))
  def nixos_rebuild = clitools.Nixos_rebuildWrapper()

  def nixos_version(args: String*) =
    clitools.Nixos_versionWrapper(CmdArgs(args.toVector))
  def nixos_version = clitools.Nixos_versionWrapper()

  def nl(args: String*) = clitools.NlWrapper(CmdArgs(args.toVector))
  def nl = clitools.NlWrapper()

  def nm_applet(args: String*) =
    clitools.Nm_appletWrapper(CmdArgs(args.toVector))
  def nm_applet = clitools.Nm_appletWrapper()

  def nm_connection_editor(args: String*) =
    clitools.Nm_connection_editorWrapper(CmdArgs(args.toVector))
  def nm_connection_editor = clitools.Nm_connection_editorWrapper()

  def nm_online(args: String*) =
    clitools.Nm_onlineWrapper(CmdArgs(args.toVector))
  def nm_online = clitools.Nm_onlineWrapper()

  def nmcli(args: String*) = clitools.NmcliWrapper(CmdArgs(args.toVector))
  def nmcli = clitools.NmcliWrapper()

  def nmtui(args: String*) = clitools.NmtuiWrapper(CmdArgs(args.toVector))
  def nmtui = clitools.NmtuiWrapper()

  def nmtui_connect(args: String*) =
    clitools.Nmtui_connectWrapper(CmdArgs(args.toVector))
  def nmtui_connect = clitools.Nmtui_connectWrapper()

  def nmtui_edit(args: String*) =
    clitools.Nmtui_editWrapper(CmdArgs(args.toVector))
  def nmtui_edit = clitools.Nmtui_editWrapper()

  def nmtui_hostname(args: String*) =
    clitools.Nmtui_hostnameWrapper(CmdArgs(args.toVector))
  def nmtui_hostname = clitools.Nmtui_hostnameWrapper()

  def node(args: String*) = clitools.NodeWrapper(CmdArgs(args.toVector))
  def node = clitools.NodeWrapper()

  def nohtml(args: String*) = clitools.NohtmlWrapper(CmdArgs(args.toVector))
  def nohtml = clitools.NohtmlWrapper()

  def nohup(args: String*) = clitools.NohupWrapper(CmdArgs(args.toVector))
  def nohup = clitools.NohupWrapper()

  def nologin(args: String*) = clitools.NologinWrapper(CmdArgs(args.toVector))
  def nologin = clitools.NologinWrapper()

  def nop(args: String*) = clitools.NopWrapper(CmdArgs(args.toVector))
  def nop = clitools.NopWrapper()

  def noreturn(args: String*) = clitools.NoreturnWrapper(CmdArgs(args.toVector))
  def noreturn = clitools.NoreturnWrapper()

  def nospace(args: String*) = clitools.NospaceWrapper(CmdArgs(args.toVector))
  def nospace = clitools.NospaceWrapper()

  def notab(args: String*) = clitools.NotabWrapper(CmdArgs(args.toVector))
  def notab = clitools.NotabWrapper()

  def notseq(args: String*) = clitools.NotseqWrapper(CmdArgs(args.toVector))
  def notseq = clitools.NotseqWrapper()

  def npm(args: String*) = clitools.NpmWrapper(CmdArgs(args.toVector))
  def npm = clitools.NpmWrapper()

  def nproc(args: String*) = clitools.NprocWrapper(CmdArgs(args.toVector))
  def nproc = clitools.NprocWrapper()

  def npx(args: String*) = clitools.NpxWrapper(CmdArgs(args.toVector))
  def npx = clitools.NpxWrapper()

  def nscd(args: String*) = clitools.NscdWrapper(CmdArgs(args.toVector))
  def nscd = clitools.NscdWrapper()

  def nsenter(args: String*) = clitools.NsenterWrapper(CmdArgs(args.toVector))
  def nsenter = clitools.NsenterWrapper()

  def nstat(args: String*) = clitools.NstatWrapper(CmdArgs(args.toVector))
  def nstat = clitools.NstatWrapper()

  def nthseq(args: String*) = clitools.NthseqWrapper(CmdArgs(args.toVector))
  def nthseq = clitools.NthseqWrapper()

  def nthseqset(args: String*) =
    clitools.NthseqsetWrapper(CmdArgs(args.toVector))
  def nthseqset = clitools.NthseqsetWrapper()

  def nucbed(args: String*) = clitools.NucBedWrapper(CmdArgs(args.toVector))
  def nucbed = clitools.NucBedWrapper()

  def numfmt(args: String*) = clitools.NumfmtWrapper(CmdArgs(args.toVector))
  def numfmt = clitools.NumfmtWrapper()

  def nvim(args: String*) = clitools.NvimWrapper(CmdArgs(args.toVector))
  def nvim = clitools.NvimWrapper()

  def nvim_python(args: String*) =
    clitools.Nvim_pythonWrapper(CmdArgs(args.toVector))
  def nvim_python = clitools.Nvim_pythonWrapper()

  def nvim_python3(args: String*) =
    clitools.Nvim_python3Wrapper(CmdArgs(args.toVector))
  def nvim_python3 = clitools.Nvim_python3Wrapper()

  def nvim_ruby(args: String*) =
    clitools.Nvim_rubyWrapper(CmdArgs(args.toVector))
  def nvim_ruby = clitools.Nvim_rubyWrapper()

  def nvlc(args: String*) = clitools.NvlcWrapper(CmdArgs(args.toVector))
  def nvlc = clitools.NvlcWrapper()

  def octanol(args: String*) = clitools.OctanolWrapper(CmdArgs(args.toVector))
  def octanol = clitools.OctanolWrapper()

  def od(args: String*) = clitools.OdWrapper(CmdArgs(args.toVector))
  def od = clitools.OdWrapper()

  def oddcomp(args: String*) = clitools.OddcompWrapper(CmdArgs(args.toVector))
  def oddcomp = clitools.OddcompWrapper()

  def ontocount(args: String*) =
    clitools.OntocountWrapper(CmdArgs(args.toVector))
  def ontocount = clitools.OntocountWrapper()

  def ontoget(args: String*) = clitools.OntogetWrapper(CmdArgs(args.toVector))
  def ontoget = clitools.OntogetWrapper()

  def ontogetcommon(args: String*) =
    clitools.OntogetcommonWrapper(CmdArgs(args.toVector))
  def ontogetcommon = clitools.OntogetcommonWrapper()

  def ontogetdown(args: String*) =
    clitools.OntogetdownWrapper(CmdArgs(args.toVector))
  def ontogetdown = clitools.OntogetdownWrapper()

  def ontogetobsolete(args: String*) =
    clitools.OntogetobsoleteWrapper(CmdArgs(args.toVector))
  def ontogetobsolete = clitools.OntogetobsoleteWrapper()

  def ontogetroot(args: String*) =
    clitools.OntogetrootWrapper(CmdArgs(args.toVector))
  def ontogetroot = clitools.OntogetrootWrapper()

  def ontogetsibs(args: String*) =
    clitools.OntogetsibsWrapper(CmdArgs(args.toVector))
  def ontogetsibs = clitools.OntogetsibsWrapper()

  def ontogetup(args: String*) =
    clitools.OntogetupWrapper(CmdArgs(args.toVector))
  def ontogetup = clitools.OntogetupWrapper()

  def ontoisobsolete(args: String*) =
    clitools.OntoisobsoleteWrapper(CmdArgs(args.toVector))
  def ontoisobsolete = clitools.OntoisobsoleteWrapper()

  def ontotext(args: String*) = clitools.OntotextWrapper(CmdArgs(args.toVector))
  def ontotext = clitools.OntotextWrapper()

  def openvt(args: String*) = clitools.OpenvtWrapper(CmdArgs(args.toVector))
  def openvt = clitools.OpenvtWrapper()

  def osage(args: String*) = clitools.OsageWrapper(CmdArgs(args.toVector))
  def osage = clitools.OsageWrapper()

  def outpsfheader(args: String*) =
    clitools.OutpsfheaderWrapper(CmdArgs(args.toVector))
  def outpsfheader = clitools.OutpsfheaderWrapper()

  def oxygen_demo5(args: String*) =
    clitools.Oxygen_demo5Wrapper(CmdArgs(args.toVector))
  def oxygen_demo5 = clitools.Oxygen_demo5Wrapper()

  def oxygen_settings5(args: String*) =
    clitools.Oxygen_settings5Wrapper(CmdArgs(args.toVector))
  def oxygen_settings5 = clitools.Oxygen_settings5Wrapper()

  def pacat(args: String*) = clitools.PacatWrapper(CmdArgs(args.toVector))
  def pacat = clitools.PacatWrapper()

  def pacmd(args: String*) = clitools.PacmdWrapper(CmdArgs(args.toVector))
  def pacmd = clitools.PacmdWrapper()

  def pactl(args: String*) = clitools.PactlWrapper(CmdArgs(args.toVector))
  def pactl = clitools.PactlWrapper()

  def padsp(args: String*) = clitools.PadspWrapper(CmdArgs(args.toVector))
  def padsp = clitools.PadspWrapper()

  def pairtobed(args: String*) =
    clitools.PairToBedWrapper(CmdArgs(args.toVector))
  def pairtobed = clitools.PairToBedWrapper()

  def pairtopair(args: String*) =
    clitools.PairToPairWrapper(CmdArgs(args.toVector))
  def pairtopair = clitools.PairToPairWrapper()

  def palindrome(args: String*) =
    clitools.PalindromeWrapper(CmdArgs(args.toVector))
  def palindrome = clitools.PalindromeWrapper()

  def pam_tally(args: String*) =
    clitools.Pam_tallyWrapper(CmdArgs(args.toVector))
  def pam_tally = clitools.Pam_tallyWrapper()

  def pam_tally2(args: String*) =
    clitools.Pam_tally2Wrapper(CmdArgs(args.toVector))
  def pam_tally2 = clitools.Pam_tally2Wrapper()

  def pam_timestamp_check(args: String*) =
    clitools.Pam_timestamp_checkWrapper(CmdArgs(args.toVector))
  def pam_timestamp_check = clitools.Pam_timestamp_checkWrapper()

  def pamon(args: String*) = clitools.PamonWrapper(CmdArgs(args.toVector))
  def pamon = clitools.PamonWrapper()

  def paplay(args: String*) = clitools.PaplayWrapper(CmdArgs(args.toVector))
  def paplay = clitools.PaplayWrapper()

  def parec(args: String*) = clitools.ParecWrapper(CmdArgs(args.toVector))
  def parec = clitools.ParecWrapper()

  def parecord(args: String*) = clitools.ParecordWrapper(CmdArgs(args.toVector))
  def parecord = clitools.ParecordWrapper()

  def parsetrigrams(args: String*) =
    clitools.ParsetrigramsWrapper(CmdArgs(args.toVector))
  def parsetrigrams = clitools.ParsetrigramsWrapper()

  def partx(args: String*) = clitools.PartxWrapper(CmdArgs(args.toVector))
  def partx = clitools.PartxWrapper()

  def passwd(args: String*) = clitools.PasswdWrapper(CmdArgs(args.toVector))
  def passwd = clitools.PasswdWrapper()

  def paste(args: String*) = clitools.PasteWrapper(CmdArgs(args.toVector))
  def paste = clitools.PasteWrapper()

  def pasteseq(args: String*) = clitools.PasteseqWrapper(CmdArgs(args.toVector))
  def pasteseq = clitools.PasteseqWrapper()

  def pasuspender(args: String*) =
    clitools.PasuspenderWrapper(CmdArgs(args.toVector))
  def pasuspender = clitools.PasuspenderWrapper()

  def patch(args: String*) = clitools.PatchWrapper(CmdArgs(args.toVector))
  def patch = clitools.PatchWrapper()

  def patchwork(args: String*) =
    clitools.PatchworkWrapper(CmdArgs(args.toVector))
  def patchwork = clitools.PatchworkWrapper()

  def pathchk(args: String*) = clitools.PathchkWrapper(CmdArgs(args.toVector))
  def pathchk = clitools.PathchkWrapper()

  def patmatdb(args: String*) = clitools.PatmatdbWrapper(CmdArgs(args.toVector))
  def patmatdb = clitools.PatmatdbWrapper()

  def patmatmotifs(args: String*) =
    clitools.PatmatmotifsWrapper(CmdArgs(args.toVector))
  def patmatmotifs = clitools.PatmatmotifsWrapper()

  def pcf2vpnc(args: String*) = clitools.Pcf2vpncWrapper(CmdArgs(args.toVector))
  def pcf2vpnc = clitools.Pcf2vpncWrapper()

  def pcprofiledump(args: String*) =
    clitools.PcprofiledumpWrapper(CmdArgs(args.toVector))
  def pcprofiledump = clitools.PcprofiledumpWrapper()

  def pdf2dsc(args: String*) = clitools.Pdf2dscWrapper(CmdArgs(args.toVector))
  def pdf2dsc = clitools.Pdf2dscWrapper()

  def pdf2ps(args: String*) = clitools.Pdf2psWrapper(CmdArgs(args.toVector))
  def pdf2ps = clitools.Pdf2psWrapper()

  def pdftexi2dvi(args: String*) =
    clitools.Pdftexi2dviWrapper(CmdArgs(args.toVector))
  def pdftexi2dvi = clitools.Pdftexi2dviWrapper()

  def pepcoil(args: String*) = clitools.PepcoilWrapper(CmdArgs(args.toVector))
  def pepcoil = clitools.PepcoilWrapper()

  def pepdigest(args: String*) =
    clitools.PepdigestWrapper(CmdArgs(args.toVector))
  def pepdigest = clitools.PepdigestWrapper()

  def pepinfo(args: String*) = clitools.PepinfoWrapper(CmdArgs(args.toVector))
  def pepinfo = clitools.PepinfoWrapper()

  def pepnet(args: String*) = clitools.PepnetWrapper(CmdArgs(args.toVector))
  def pepnet = clitools.PepnetWrapper()

  def pepstats(args: String*) = clitools.PepstatsWrapper(CmdArgs(args.toVector))
  def pepstats = clitools.PepstatsWrapper()

  def pepwheel(args: String*) = clitools.PepwheelWrapper(CmdArgs(args.toVector))
  def pepwheel = clitools.PepwheelWrapper()

  def pepwindow(args: String*) =
    clitools.PepwindowWrapper(CmdArgs(args.toVector))
  def pepwindow = clitools.PepwindowWrapper()

  def pepwindowall(args: String*) =
    clitools.PepwindowallWrapper(CmdArgs(args.toVector))
  def pepwindowall = clitools.PepwindowallWrapper()

  def perl(args: String*) = clitools.PerlWrapper(CmdArgs(args.toVector))
  def perl = clitools.PerlWrapper()

  def perlbug(args: String*) = clitools.PerlbugWrapper(CmdArgs(args.toVector))
  def perlbug = clitools.PerlbugWrapper()

  def perldoc(args: String*) = clitools.PerldocWrapper(CmdArgs(args.toVector))
  def perldoc = clitools.PerldocWrapper()

  def perlivp(args: String*) = clitools.PerlivpWrapper(CmdArgs(args.toVector))
  def perlivp = clitools.PerlivpWrapper()

  def perlthanks(args: String*) =
    clitools.PerlthanksWrapper(CmdArgs(args.toVector))
  def perlthanks = clitools.PerlthanksWrapper()

  def pf2afm(args: String*) = clitools.Pf2afmWrapper(CmdArgs(args.toVector))
  def pf2afm = clitools.Pf2afmWrapper()

  def pfbtopfa(args: String*) = clitools.PfbtopfaWrapper(CmdArgs(args.toVector))
  def pfbtopfa = clitools.PfbtopfaWrapper()

  def pgrep(args: String*) = clitools.PgrepWrapper(CmdArgs(args.toVector))
  def pgrep = clitools.PgrepWrapper()

  def phmmer(args: String*) = clitools.PhmmerWrapper(CmdArgs(args.toVector))
  def phmmer = clitools.PhmmerWrapper()

  def picard(args: String*) = clitools.PicardWrapper(CmdArgs(args.toVector))
  def picard = clitools.PicardWrapper()

  def piconv(args: String*) = clitools.PiconvWrapper(CmdArgs(args.toVector))
  def piconv = clitools.PiconvWrapper()

  def pidof(args: String*) = clitools.PidofWrapper(CmdArgs(args.toVector))
  def pidof = clitools.PidofWrapper()

  def ping(args: String*) = clitools.PingWrapper(CmdArgs(args.toVector))
  def ping = clitools.PingWrapper()

  def pinky(args: String*) = clitools.PinkyWrapper(CmdArgs(args.toVector))
  def pinky = clitools.PinkyWrapper()

  def pivot_root(args: String*) =
    clitools.Pivot_rootWrapper(CmdArgs(args.toVector))
  def pivot_root = clitools.Pivot_rootWrapper()

  def pk_example_frobnicate(args: String*) =
    clitools.Pk_example_frobnicateWrapper(CmdArgs(args.toVector))
  def pk_example_frobnicate = clitools.Pk_example_frobnicateWrapper()

  def pkaction(args: String*) = clitools.PkactionWrapper(CmdArgs(args.toVector))
  def pkaction = clitools.PkactionWrapper()

  def pkcheck(args: String*) = clitools.PkcheckWrapper(CmdArgs(args.toVector))
  def pkcheck = clitools.PkcheckWrapper()

  def pkexec(args: String*) = clitools.PkexecWrapper(CmdArgs(args.toVector))
  def pkexec = clitools.PkexecWrapper()

  def pkill(args: String*) = clitools.PkillWrapper(CmdArgs(args.toVector))
  def pkill = clitools.PkillWrapper()

  def pkttyagent(args: String*) =
    clitools.PkttyagentWrapper(CmdArgs(args.toVector))
  def pkttyagent = clitools.PkttyagentWrapper()

  def pl2pm(args: String*) = clitools.Pl2pmWrapper(CmdArgs(args.toVector))
  def pl2pm = clitools.Pl2pmWrapper()

  def plasma_waitforname(args: String*) =
    clitools.Plasma_waitfornameWrapper(CmdArgs(args.toVector))
  def plasma_waitforname = clitools.Plasma_waitfornameWrapper()

  def plasmapkg2(args: String*) =
    clitools.Plasmapkg2Wrapper(CmdArgs(args.toVector))
  def plasmapkg2 = clitools.Plasmapkg2Wrapper()

  def plasmashell(args: String*) =
    clitools.PlasmashellWrapper(CmdArgs(args.toVector))
  def plasmashell = clitools.PlasmashellWrapper()

  def plasmawindowed(args: String*) =
    clitools.PlasmawindowedWrapper(CmdArgs(args.toVector))
  def plasmawindowed = clitools.PlasmawindowedWrapper()

  def platypus(args: String*) = clitools.PlatypusWrapper(CmdArgs(args.toVector))
  def platypus = clitools.PlatypusWrapper()

  def pldd(args: String*) = clitools.PlddWrapper(CmdArgs(args.toVector))
  def pldd = clitools.PlddWrapper()

  def plink(args: String*) = clitools.PlinkWrapper(CmdArgs(args.toVector))
  def plink = clitools.PlinkWrapper()

  def plipconfig(args: String*) =
    clitools.PlipconfigWrapper(CmdArgs(args.toVector))
  def plipconfig = clitools.PlipconfigWrapper()

  def plot_bamstats(args: String*) =
    clitools.Plot_bamstatsWrapper(CmdArgs(args.toVector))
  def plot_bamstats = clitools.Plot_bamstatsWrapper()

  def plot_vcfstats(args: String*) =
    clitools.Plot_vcfstatsWrapper(CmdArgs(args.toVector))
  def plot_vcfstats = clitools.Plot_vcfstatsWrapper()

  def plotcon(args: String*) = clitools.PlotconWrapper(CmdArgs(args.toVector))
  def plotcon = clitools.PlotconWrapper()

  def plotorf(args: String*) = clitools.PlotorfWrapper(CmdArgs(args.toVector))
  def plotorf = clitools.PlotorfWrapper()

  def pmap(args: String*) = clitools.PmapWrapper(CmdArgs(args.toVector))
  def pmap = clitools.PmapWrapper()

  def pod2html(args: String*) = clitools.Pod2htmlWrapper(CmdArgs(args.toVector))
  def pod2html = clitools.Pod2htmlWrapper()

  def pod2man(args: String*) = clitools.Pod2manWrapper(CmdArgs(args.toVector))
  def pod2man = clitools.Pod2manWrapper()

  def pod2texi(args: String*) = clitools.Pod2texiWrapper(CmdArgs(args.toVector))
  def pod2texi = clitools.Pod2texiWrapper()

  def pod2text(args: String*) = clitools.Pod2textWrapper(CmdArgs(args.toVector))
  def pod2text = clitools.Pod2textWrapper()

  def pod2usage(args: String*) =
    clitools.Pod2usageWrapper(CmdArgs(args.toVector))
  def pod2usage = clitools.Pod2usageWrapper()

  def podchecker(args: String*) =
    clitools.PodcheckerWrapper(CmdArgs(args.toVector))
  def podchecker = clitools.PodcheckerWrapper()

  def podselect(args: String*) =
    clitools.PodselectWrapper(CmdArgs(args.toVector))
  def podselect = clitools.PodselectWrapper()

  def polydot(args: String*) = clitools.PolydotWrapper(CmdArgs(args.toVector))
  def polydot = clitools.PolydotWrapper()

  def portablectl(args: String*) =
    clitools.PortablectlWrapper(CmdArgs(args.toVector))
  def portablectl = clitools.PortablectlWrapper()

  def poweroff(args: String*) = clitools.PoweroffWrapper(CmdArgs(args.toVector))
  def poweroff = clitools.PoweroffWrapper()

  def ppdc(args: String*) = clitools.PpdcWrapper(CmdArgs(args.toVector))
  def ppdc = clitools.PpdcWrapper()

  def ppdhtml(args: String*) = clitools.PpdhtmlWrapper(CmdArgs(args.toVector))
  def ppdhtml = clitools.PpdhtmlWrapper()

  def ppdi(args: String*) = clitools.PpdiWrapper(CmdArgs(args.toVector))
  def ppdi = clitools.PpdiWrapper()

  def ppdmerge(args: String*) = clitools.PpdmergeWrapper(CmdArgs(args.toVector))
  def ppdmerge = clitools.PpdmergeWrapper()

  def ppdpo(args: String*) = clitools.PpdpoWrapper(CmdArgs(args.toVector))
  def ppdpo = clitools.PpdpoWrapper()

  def pphs(args: String*) = clitools.PphsWrapper(CmdArgs(args.toVector))
  def pphs = clitools.PphsWrapper()

  def pr(args: String*) = clitools.PrWrapper(CmdArgs(args.toVector))
  def pr = clitools.PrWrapper()

  def preg(args: String*) = clitools.PregWrapper(CmdArgs(args.toVector))
  def preg = clitools.PregWrapper()

  def prettyplot(args: String*) =
    clitools.PrettyplotWrapper(CmdArgs(args.toVector))
  def prettyplot = clitools.PrettyplotWrapper()

  def prettyseq(args: String*) =
    clitools.PrettyseqWrapper(CmdArgs(args.toVector))
  def prettyseq = clitools.PrettyseqWrapper()

  def primersearch(args: String*) =
    clitools.PrimersearchWrapper(CmdArgs(args.toVector))
  def primersearch = clitools.PrimersearchWrapper()

  def printafm(args: String*) = clitools.PrintafmWrapper(CmdArgs(args.toVector))
  def printafm = clitools.PrintafmWrapper()

  def printenv(args: String*) = clitools.PrintenvWrapper(CmdArgs(args.toVector))
  def printenv = clitools.PrintenvWrapper()

  def printf(args: String*) = clitools.PrintfWrapper(CmdArgs(args.toVector))
  def printf = clitools.PrintfWrapper()

  def printsextract(args: String*) =
    clitools.PrintsextractWrapper(CmdArgs(args.toVector))
  def printsextract = clitools.PrintsextractWrapper()

  def prlimit(args: String*) = clitools.PrlimitWrapper(CmdArgs(args.toVector))
  def prlimit = clitools.PrlimitWrapper()

  def profit(args: String*) = clitools.ProfitWrapper(CmdArgs(args.toVector))
  def profit = clitools.ProfitWrapper()

  def prophecy(args: String*) = clitools.ProphecyWrapper(CmdArgs(args.toVector))
  def prophecy = clitools.ProphecyWrapper()

  def prophet(args: String*) = clitools.ProphetWrapper(CmdArgs(args.toVector))
  def prophet = clitools.ProphetWrapper()

  def prosextract(args: String*) =
    clitools.ProsextractWrapper(CmdArgs(args.toVector))
  def prosextract = clitools.ProsextractWrapper()

  def protocoltojson(args: String*) =
    clitools.ProtocoltojsonWrapper(CmdArgs(args.toVector))
  def protocoltojson = clitools.ProtocoltojsonWrapper()

  def prove(args: String*) = clitools.ProveWrapper(CmdArgs(args.toVector))
  def prove = clitools.ProveWrapper()

  def prune(args: String*) = clitools.PruneWrapper(CmdArgs(args.toVector))
  def prune = clitools.PruneWrapper()

  def ps(args: String*) = clitools.PsWrapper(CmdArgs(args.toVector))
  def ps = clitools.PsWrapper()

  def ps2ascii(args: String*) = clitools.Ps2asciiWrapper(CmdArgs(args.toVector))
  def ps2ascii = clitools.Ps2asciiWrapper()

  def ps2epsi(args: String*) = clitools.Ps2epsiWrapper(CmdArgs(args.toVector))
  def ps2epsi = clitools.Ps2epsiWrapper()

  def ps2pdf(args: String*) = clitools.Ps2pdfWrapper(CmdArgs(args.toVector))
  def ps2pdf = clitools.Ps2pdfWrapper()

  def ps2pdf12(args: String*) = clitools.Ps2pdf12Wrapper(CmdArgs(args.toVector))
  def ps2pdf12 = clitools.Ps2pdf12Wrapper()

  def ps2pdf13(args: String*) = clitools.Ps2pdf13Wrapper(CmdArgs(args.toVector))
  def ps2pdf13 = clitools.Ps2pdf13Wrapper()

  def ps2pdf14(args: String*) = clitools.Ps2pdf14Wrapper(CmdArgs(args.toVector))
  def ps2pdf14 = clitools.Ps2pdf14Wrapper()

  def ps2pdfwr(args: String*) = clitools.Ps2pdfwrWrapper(CmdArgs(args.toVector))
  def ps2pdfwr = clitools.Ps2pdfwrWrapper()

  def ps2ps(args: String*) = clitools.Ps2psWrapper(CmdArgs(args.toVector))
  def ps2ps = clitools.Ps2psWrapper()

  def ps2ps2(args: String*) = clitools.Ps2ps2Wrapper(CmdArgs(args.toVector))
  def ps2ps2 = clitools.Ps2ps2Wrapper()

  def pscan(args: String*) = clitools.PscanWrapper(CmdArgs(args.toVector))
  def pscan = clitools.PscanWrapper()

  def psfaddtable(args: String*) =
    clitools.PsfaddtableWrapper(CmdArgs(args.toVector))
  def psfaddtable = clitools.PsfaddtableWrapper()

  def psfgettable(args: String*) =
    clitools.PsfgettableWrapper(CmdArgs(args.toVector))
  def psfgettable = clitools.PsfgettableWrapper()

  def psfstriptable(args: String*) =
    clitools.PsfstriptableWrapper(CmdArgs(args.toVector))
  def psfstriptable = clitools.PsfstriptableWrapper()

  def psfxtable(args: String*) =
    clitools.PsfxtableWrapper(CmdArgs(args.toVector))
  def psfxtable = clitools.PsfxtableWrapper()

  def psiphi(args: String*) = clitools.PsiphiWrapper(CmdArgs(args.toVector))
  def psiphi = clitools.PsiphiWrapper()

  def ptar(args: String*) = clitools.PtarWrapper(CmdArgs(args.toVector))
  def ptar = clitools.PtarWrapper()

  def ptardiff(args: String*) = clitools.PtardiffWrapper(CmdArgs(args.toVector))
  def ptardiff = clitools.PtardiffWrapper()

  def ptargrep(args: String*) = clitools.PtargrepWrapper(CmdArgs(args.toVector))
  def ptargrep = clitools.PtargrepWrapper()

  def ptx(args: String*) = clitools.PtxWrapper(CmdArgs(args.toVector))
  def ptx = clitools.PtxWrapper()

  def pulseaudio(args: String*) =
    clitools.PulseaudioWrapper(CmdArgs(args.toVector))
  def pulseaudio = clitools.PulseaudioWrapper()

  def pvchange(args: String*) = clitools.PvchangeWrapper(CmdArgs(args.toVector))
  def pvchange = clitools.PvchangeWrapper()

  def pvck(args: String*) = clitools.PvckWrapper(CmdArgs(args.toVector))
  def pvck = clitools.PvckWrapper()

  def pvcreate(args: String*) = clitools.PvcreateWrapper(CmdArgs(args.toVector))
  def pvcreate = clitools.PvcreateWrapper()

  def pvdisplay(args: String*) =
    clitools.PvdisplayWrapper(CmdArgs(args.toVector))
  def pvdisplay = clitools.PvdisplayWrapper()

  def pvmove(args: String*) = clitools.PvmoveWrapper(CmdArgs(args.toVector))
  def pvmove = clitools.PvmoveWrapper()

  def pvremove(args: String*) = clitools.PvremoveWrapper(CmdArgs(args.toVector))
  def pvremove = clitools.PvremoveWrapper()

  def pvresize(args: String*) = clitools.PvresizeWrapper(CmdArgs(args.toVector))
  def pvresize = clitools.PvresizeWrapper()

  def pvs(args: String*) = clitools.PvsWrapper(CmdArgs(args.toVector))
  def pvs = clitools.PvsWrapper()

  def pvscan(args: String*) = clitools.PvscanWrapper(CmdArgs(args.toVector))
  def pvscan = clitools.PvscanWrapper()

  def pwck(args: String*) = clitools.PwckWrapper(CmdArgs(args.toVector))
  def pwck = clitools.PwckWrapper()

  def pwconv(args: String*) = clitools.PwconvWrapper(CmdArgs(args.toVector))
  def pwconv = clitools.PwconvWrapper()

  def pwd(args: String*) = clitools.PwdWrapper(CmdArgs(args.toVector))
  def pwd = clitools.PwdWrapper()

  def pwdx(args: String*) = clitools.PwdxWrapper(CmdArgs(args.toVector))
  def pwdx = clitools.PwdxWrapper()

  def pwunconv(args: String*) = clitools.PwunconvWrapper(CmdArgs(args.toVector))
  def pwunconv = clitools.PwunconvWrapper()

  def py(args: String*) = clitools.PyWrapper(CmdArgs(args.toVector))
  def py = clitools.PyWrapper()

  def qpaeq(args: String*) = clitools.QpaeqWrapper(CmdArgs(args.toVector))
  def qpaeq = clitools.QpaeqWrapper()

  def qvlc(args: String*) = clitools.QvlcWrapper(CmdArgs(args.toVector))
  def qvlc = clitools.QvlcWrapper()

  def randombed(args: String*) =
    clitools.RandomBedWrapper(CmdArgs(args.toVector))
  def randombed = clitools.RandomBedWrapper()

  def rarp(args: String*) = clitools.RarpWrapper(CmdArgs(args.toVector))
  def rarp = clitools.RarpWrapper()

  def rarpd(args: String*) = clitools.RarpdWrapper(CmdArgs(args.toVector))
  def rarpd = clitools.RarpdWrapper()

  def raw(args: String*) = clitools.RawWrapper(CmdArgs(args.toVector))
  def raw = clitools.RawWrapper()

  def rdisc(args: String*) = clitools.RdiscWrapper(CmdArgs(args.toVector))
  def rdisc = clitools.RdiscWrapper()

  def read(args: String*) = clitools.ReadWrapper(CmdArgs(args.toVector))
  def read = clitools.ReadWrapper()

  def readarray(args: String*) =
    clitools.ReadarrayWrapper(CmdArgs(args.toVector))
  def readarray = clitools.ReadarrayWrapper()

  def readlink(args: String*) = clitools.ReadlinkWrapper(CmdArgs(args.toVector))
  def readlink = clitools.ReadlinkWrapper()

  def readprofile(args: String*) =
    clitools.ReadprofileWrapper(CmdArgs(args.toVector))
  def readprofile = clitools.ReadprofileWrapper()

  def realpath(args: String*) = clitools.RealpathWrapper(CmdArgs(args.toVector))
  def realpath = clitools.RealpathWrapper()

  def rebaseextract(args: String*) =
    clitools.RebaseextractWrapper(CmdArgs(args.toVector))
  def rebaseextract = clitools.RebaseextractWrapper()

  def reboot(args: String*) = clitools.RebootWrapper(CmdArgs(args.toVector))
  def reboot = clitools.RebootWrapper()

  def recoder(args: String*) = clitools.RecoderWrapper(CmdArgs(args.toVector))
  def recoder = clitools.RecoderWrapper()

  def redata(args: String*) = clitools.RedataWrapper(CmdArgs(args.toVector))
  def redata = clitools.RedataWrapper()

  def redshift(args: String*) = clitools.RedshiftWrapper(CmdArgs(args.toVector))
  def redshift = clitools.RedshiftWrapper()

  def redshift_gtk(args: String*) =
    clitools.Redshift_gtkWrapper(CmdArgs(args.toVector))
  def redshift_gtk = clitools.Redshift_gtkWrapper()

  def refseqget(args: String*) =
    clitools.RefseqgetWrapper(CmdArgs(args.toVector))
  def refseqget = clitools.RefseqgetWrapper()

  def regdbdump(args: String*) =
    clitools.RegdbdumpWrapper(CmdArgs(args.toVector))
  def regdbdump = clitools.RegdbdumpWrapper()

  def reject(args: String*) = clitools.RejectWrapper(CmdArgs(args.toVector))
  def reject = clitools.RejectWrapper()

  def remap(args: String*) = clitools.RemapWrapper(CmdArgs(args.toVector))
  def remap = clitools.RemapWrapper()

  def rename(args: String*) = clitools.RenameWrapper(CmdArgs(args.toVector))
  def rename = clitools.RenameWrapper()

  def renice(args: String*) = clitools.ReniceWrapper(CmdArgs(args.toVector))
  def renice = clitools.ReniceWrapper()

  def reset(args: String*) = clitools.ResetWrapper(CmdArgs(args.toVector))
  def reset = clitools.ResetWrapper()

  def resize(args: String*) = clitools.ResizeWrapper(CmdArgs(args.toVector))
  def resize = clitools.ResizeWrapper()

  def resize2fs(args: String*) =
    clitools.Resize2fsWrapper(CmdArgs(args.toVector))
  def resize2fs = clitools.Resize2fsWrapper()

  def resizecons(args: String*) =
    clitools.ResizeconsWrapper(CmdArgs(args.toVector))
  def resizecons = clitools.ResizeconsWrapper()

  def resizepart(args: String*) =
    clitools.ResizepartWrapper(CmdArgs(args.toVector))
  def resizepart = clitools.ResizepartWrapper()

  def resolvconf(args: String*) =
    clitools.ResolvconfWrapper(CmdArgs(args.toVector))
  def resolvconf = clitools.ResolvconfWrapper()

  def resolvectl(args: String*) =
    clitools.ResolvectlWrapper(CmdArgs(args.toVector))
  def resolvectl = clitools.ResolvectlWrapper()

  def restover(args: String*) = clitools.RestoverWrapper(CmdArgs(args.toVector))
  def restover = clitools.RestoverWrapper()

  def restrict(args: String*) = clitools.RestrictWrapper(CmdArgs(args.toVector))
  def restrict = clitools.RestrictWrapper()

  def rev(args: String*) = clitools.RevWrapper(CmdArgs(args.toVector))
  def rev = clitools.RevWrapper()

  def revseq(args: String*) = clitools.RevseqWrapper(CmdArgs(args.toVector))
  def revseq = clitools.RevseqWrapper()

  def rfkill(args: String*) = clitools.RfkillWrapper(CmdArgs(args.toVector))
  def rfkill = clitools.RfkillWrapper()

  def rm(args: String*) = clitools.RmWrapper(CmdArgs(args.toVector))
  def rm = clitools.RmWrapper()

  def rmdir(args: String*) = clitools.RmdirWrapper(CmdArgs(args.toVector))
  def rmdir = clitools.RmdirWrapper()

  def rmmod(args: String*) = clitools.RmmodWrapper(CmdArgs(args.toVector))
  def rmmod = clitools.RmmodWrapper()

  def rnano(args: String*) = clitools.RnanoWrapper(CmdArgs(args.toVector))
  def rnano = clitools.RnanoWrapper()

  def route(args: String*) = clitools.RouteWrapper(CmdArgs(args.toVector))
  def route = clitools.RouteWrapper()

  def routef(args: String*) = clitools.RoutefWrapper(CmdArgs(args.toVector))
  def routef = clitools.RoutefWrapper()

  def routel(args: String*) = clitools.RoutelWrapper(CmdArgs(args.toVector))
  def routel = clitools.RoutelWrapper()

  def rpcgen(args: String*) = clitools.RpcgenWrapper(CmdArgs(args.toVector))
  def rpcgen = clitools.RpcgenWrapper()

  def rsvg_convert(args: String*) =
    clitools.Rsvg_convertWrapper(CmdArgs(args.toVector))
  def rsvg_convert = clitools.Rsvg_convertWrapper()

  def rsync(args: String*) = clitools.RsyncWrapper(CmdArgs(args.toVector))
  def rsync = clitools.RsyncWrapper()

  def rtacct(args: String*) = clitools.RtacctWrapper(CmdArgs(args.toVector))
  def rtacct = clitools.RtacctWrapper()

  def rtcwake(args: String*) = clitools.RtcwakeWrapper(CmdArgs(args.toVector))
  def rtcwake = clitools.RtcwakeWrapper()

  def rtkitctl(args: String*) = clitools.RtkitctlWrapper(CmdArgs(args.toVector))
  def rtkitctl = clitools.RtkitctlWrapper()

  def rtmon(args: String*) = clitools.RtmonWrapper(CmdArgs(args.toVector))
  def rtmon = clitools.RtmonWrapper()

  def rtpr(args: String*) = clitools.RtprWrapper(CmdArgs(args.toVector))
  def rtpr = clitools.RtprWrapper()

  def rtstat(args: String*) = clitools.RtstatWrapper(CmdArgs(args.toVector))
  def rtstat = clitools.RtstatWrapper()

  def run_singularity(args: String*) =
    clitools.Run_singularityWrapper(CmdArgs(args.toVector))
  def run_singularity = clitools.Run_singularityWrapper()

  def runcon(args: String*) = clitools.RunconWrapper(CmdArgs(args.toVector))
  def runcon = clitools.RunconWrapper()

  def runlevel(args: String*) = clitools.RunlevelWrapper(CmdArgs(args.toVector))
  def runlevel = clitools.RunlevelWrapper()

  def runuser(args: String*) = clitools.RunuserWrapper(CmdArgs(args.toVector))
  def runuser = clitools.RunuserWrapper()

  def rvlc(args: String*) = clitools.RvlcWrapper(CmdArgs(args.toVector))
  def rvlc = clitools.RvlcWrapper()

  def samtools(args: String*) = clitools.SamtoolsWrapper(CmdArgs(args.toVector))
  def samtools = clitools.SamtoolsWrapper()

  def sbase(args: String*) = clitools.SbaseWrapper(CmdArgs(args.toVector))
  def sbase = clitools.SbaseWrapper()

  def sbt(args: String*) = clitools.SbtWrapper(CmdArgs(args.toVector))
  def sbt = clitools.SbtWrapper()

  def scala(args: String*) = clitools.ScalaWrapper(CmdArgs(args.toVector))
  def scala = clitools.ScalaWrapper()

  def scalc(args: String*) = clitools.ScalcWrapper(CmdArgs(args.toVector))
  def scalc = clitools.ScalcWrapper()

  def sccmap(args: String*) = clitools.SccmapWrapper(CmdArgs(args.toVector))
  def sccmap = clitools.SccmapWrapper()

  def sclient(args: String*) = clitools.SclientWrapper(CmdArgs(args.toVector))
  def sclient = clitools.SclientWrapper()

  def scp(args: String*) = clitools.ScpWrapper(CmdArgs(args.toVector))
  def scp = clitools.ScpWrapper()

  def scp_dbus_service(args: String*) =
    clitools.Scp_dbus_serviceWrapper(CmdArgs(args.toVector))
  def scp_dbus_service = clitools.Scp_dbus_serviceWrapper()

  def screendump(args: String*) =
    clitools.ScreendumpWrapper(CmdArgs(args.toVector))
  def screendump = clitools.ScreendumpWrapper()

  def script(args: String*) = clitools.ScriptWrapper(CmdArgs(args.toVector))
  def script = clitools.ScriptWrapper()

  def scriptreplay(args: String*) =
    clitools.ScriptreplayWrapper(CmdArgs(args.toVector))
  def scriptreplay = clitools.ScriptreplayWrapper()

  def sddm(args: String*) = clitools.SddmWrapper(CmdArgs(args.toVector))
  def sddm = clitools.SddmWrapper()

  def sddm_greeter(args: String*) =
    clitools.Sddm_greeterWrapper(CmdArgs(args.toVector))
  def sddm_greeter = clitools.Sddm_greeterWrapper()

  def sdiff(args: String*) = clitools.SdiffWrapper(CmdArgs(args.toVector))
  def sdiff = clitools.SdiffWrapper()

  def sdraw(args: String*) = clitools.SdrawWrapper(CmdArgs(args.toVector))
  def sdraw = clitools.SdrawWrapper()

  def secret_tool(args: String*) =
    clitools.Secret_toolWrapper(CmdArgs(args.toVector))
  def secret_tool = clitools.Secret_toolWrapper()

  def sed(args: String*) = clitools.SedWrapper(CmdArgs(args.toVector))
  def sed = clitools.SedWrapper()

  def seealso(args: String*) = clitools.SeealsoWrapper(CmdArgs(args.toVector))
  def seealso = clitools.SeealsoWrapper()

  def seq(args: String*) = clitools.SeqWrapper(CmdArgs(args.toVector))
  def seq = clitools.SeqWrapper()

  def seqcount(args: String*) = clitools.SeqcountWrapper(CmdArgs(args.toVector))
  def seqcount = clitools.SeqcountWrapper()

  def seqmatchall(args: String*) =
    clitools.SeqmatchallWrapper(CmdArgs(args.toVector))
  def seqmatchall = clitools.SeqmatchallWrapper()

  def seqret(args: String*) = clitools.SeqretWrapper(CmdArgs(args.toVector))
  def seqret = clitools.SeqretWrapper()

  def seqretsetall(args: String*) =
    clitools.SeqretsetallWrapper(CmdArgs(args.toVector))
  def seqretsetall = clitools.SeqretsetallWrapper()

  def seqretsplit(args: String*) =
    clitools.SeqretsplitWrapper(CmdArgs(args.toVector))
  def seqretsplit = clitools.SeqretsplitWrapper()

  def seqxref(args: String*) = clitools.SeqxrefWrapper(CmdArgs(args.toVector))
  def seqxref = clitools.SeqxrefWrapper()

  def seqxrefget(args: String*) =
    clitools.SeqxrefgetWrapper(CmdArgs(args.toVector))
  def seqxrefget = clitools.SeqxrefgetWrapper()

  def servertell(args: String*) =
    clitools.ServertellWrapper(CmdArgs(args.toVector))
  def servertell = clitools.ServertellWrapper()

  def servicemenuinstaller(args: String*) =
    clitools.ServicemenuinstallerWrapper(CmdArgs(args.toVector))
  def servicemenuinstaller = clitools.ServicemenuinstallerWrapper()

  def setarch(args: String*) = clitools.SetarchWrapper(CmdArgs(args.toVector))
  def setarch = clitools.SetarchWrapper()

  def setcap(args: String*) = clitools.SetcapWrapper(CmdArgs(args.toVector))
  def setcap = clitools.SetcapWrapper()

  def setfacl(args: String*) = clitools.SetfaclWrapper(CmdArgs(args.toVector))
  def setfacl = clitools.SetfaclWrapper()

  def setfattr(args: String*) = clitools.SetfattrWrapper(CmdArgs(args.toVector))
  def setfattr = clitools.SetfattrWrapper()

  def setfont(args: String*) = clitools.SetfontWrapper(CmdArgs(args.toVector))
  def setfont = clitools.SetfontWrapper()

  def setkeycodes(args: String*) =
    clitools.SetkeycodesWrapper(CmdArgs(args.toVector))
  def setkeycodes = clitools.SetkeycodesWrapper()

  def setleds(args: String*) = clitools.SetledsWrapper(CmdArgs(args.toVector))
  def setleds = clitools.SetledsWrapper()

  def setlogcons(args: String*) =
    clitools.SetlogconsWrapper(CmdArgs(args.toVector))
  def setlogcons = clitools.SetlogconsWrapper()

  def setmetamode(args: String*) =
    clitools.SetmetamodeWrapper(CmdArgs(args.toVector))
  def setmetamode = clitools.SetmetamodeWrapper()

  def setpalette(args: String*) =
    clitools.SetpaletteWrapper(CmdArgs(args.toVector))
  def setpalette = clitools.SetpaletteWrapper()

  def setsid(args: String*) = clitools.SetsidWrapper(CmdArgs(args.toVector))
  def setsid = clitools.SetsidWrapper()

  def setterm(args: String*) = clitools.SettermWrapper(CmdArgs(args.toVector))
  def setterm = clitools.SettermWrapper()

  def setvesablank(args: String*) =
    clitools.SetvesablankWrapper(CmdArgs(args.toVector))
  def setvesablank = clitools.SetvesablankWrapper()

  def setvtrgb(args: String*) = clitools.SetvtrgbWrapper(CmdArgs(args.toVector))
  def setvtrgb = clitools.SetvtrgbWrapper()

  def setxkbmap(args: String*) =
    clitools.SetxkbmapWrapper(CmdArgs(args.toVector))
  def setxkbmap = clitools.SetxkbmapWrapper()

  def sfdisk(args: String*) = clitools.SfdiskWrapper(CmdArgs(args.toVector))
  def sfdisk = clitools.SfdiskWrapper()

  def sfdp(args: String*) = clitools.SfdpWrapper(CmdArgs(args.toVector))
  def sfdp = clitools.SfdpWrapper()

  def sftp(args: String*) = clitools.SftpWrapper(CmdArgs(args.toVector))
  def sftp = clitools.SftpWrapper()

  def sg(args: String*) = clitools.SgWrapper(CmdArgs(args.toVector))
  def sg = clitools.SgWrapper()

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

  def shasum(args: String*) = clitools.ShasumWrapper(CmdArgs(args.toVector))
  def shasum = clitools.ShasumWrapper()

  def shellcheck(args: String*) =
    clitools.ShellcheckWrapper(CmdArgs(args.toVector))
  def shellcheck = clitools.ShellcheckWrapper()

  def shfmt(args: String*) = clitools.ShfmtWrapper(CmdArgs(args.toVector))
  def shfmt = clitools.ShfmtWrapper()

  def shiftbed(args: String*) = clitools.ShiftBedWrapper(CmdArgs(args.toVector))
  def shiftbed = clitools.ShiftBedWrapper()

  def showalign(args: String*) =
    clitools.ShowalignWrapper(CmdArgs(args.toVector))
  def showalign = clitools.ShowalignWrapper()

  def showconsolefont(args: String*) =
    clitools.ShowconsolefontWrapper(CmdArgs(args.toVector))
  def showconsolefont = clitools.ShowconsolefontWrapper()

  def showdb(args: String*) = clitools.ShowdbWrapper(CmdArgs(args.toVector))
  def showdb = clitools.ShowdbWrapper()

  def showfeat(args: String*) = clitools.ShowfeatWrapper(CmdArgs(args.toVector))
  def showfeat = clitools.ShowfeatWrapper()

  def showkey(args: String*) = clitools.ShowkeyWrapper(CmdArgs(args.toVector))
  def showkey = clitools.ShowkeyWrapper()

  def showorf(args: String*) = clitools.ShoworfWrapper(CmdArgs(args.toVector))
  def showorf = clitools.ShoworfWrapper()

  def showpep(args: String*) = clitools.ShowpepWrapper(CmdArgs(args.toVector))
  def showpep = clitools.ShowpepWrapper()

  def showseq(args: String*) = clitools.ShowseqWrapper(CmdArgs(args.toVector))
  def showseq = clitools.ShowseqWrapper()

  def showserver(args: String*) =
    clitools.ShowserverWrapper(CmdArgs(args.toVector))
  def showserver = clitools.ShowserverWrapper()

  def shred(args: String*) = clitools.ShredWrapper(CmdArgs(args.toVector))
  def shred = clitools.ShredWrapper()

  def shuf(args: String*) = clitools.ShufWrapper(CmdArgs(args.toVector))
  def shuf = clitools.ShufWrapper()

  def shufflebed(args: String*) =
    clitools.ShuffleBedWrapper(CmdArgs(args.toVector))
  def shufflebed = clitools.ShuffleBedWrapper()

  def shuffleseq(args: String*) =
    clitools.ShuffleseqWrapper(CmdArgs(args.toVector))
  def shuffleseq = clitools.ShuffleseqWrapper()

  def shutdown(args: String*) = clitools.ShutdownWrapper(CmdArgs(args.toVector))
  def shutdown = clitools.ShutdownWrapper()

  def sigcleave(args: String*) =
    clitools.SigcleaveWrapper(CmdArgs(args.toVector))
  def sigcleave = clitools.SigcleaveWrapper()

  def silent(args: String*) = clitools.SilentWrapper(CmdArgs(args.toVector))
  def silent = clitools.SilentWrapper()

  def sim_client(args: String*) =
    clitools.Sim_clientWrapper(CmdArgs(args.toVector))
  def sim_client = clitools.Sim_clientWrapper()

  def sim_server(args: String*) =
    clitools.Sim_serverWrapper(CmdArgs(args.toVector))
  def sim_server = clitools.Sim_serverWrapper()

  def simpress(args: String*) = clitools.SimpressWrapper(CmdArgs(args.toVector))
  def simpress = clitools.SimpressWrapper()

  def singularity(args: String*) =
    clitools.SingularityWrapper(CmdArgs(args.toVector))
  def singularity = clitools.SingularityWrapper()

  def sirna(args: String*) = clitools.SirnaWrapper(CmdArgs(args.toVector))
  def sirna = clitools.SirnaWrapper()

  def sixpack(args: String*) = clitools.SixpackWrapper(CmdArgs(args.toVector))
  def sixpack = clitools.SixpackWrapper()

  def sizeseq(args: String*) = clitools.SizeseqWrapper(CmdArgs(args.toVector))
  def sizeseq = clitools.SizeseqWrapper()

  def skipredundant(args: String*) =
    clitools.SkipredundantWrapper(CmdArgs(args.toVector))
  def skipredundant = clitools.SkipredundantWrapper()

  def skipseq(args: String*) = clitools.SkipseqWrapper(CmdArgs(args.toVector))
  def skipseq = clitools.SkipseqWrapper()

  def slabtop(args: String*) = clitools.SlabtopWrapper(CmdArgs(args.toVector))
  def slabtop = clitools.SlabtopWrapper()

  def slack(args: String*) = clitools.SlackWrapper(CmdArgs(args.toVector))
  def slack = clitools.SlackWrapper()

  def slattach(args: String*) = clitools.SlattachWrapper(CmdArgs(args.toVector))
  def slattach = clitools.SlattachWrapper()

  def sleep(args: String*) = clitools.SleepWrapper(CmdArgs(args.toVector))
  def sleep = clitools.SleepWrapper()

  def sln(args: String*) = clitools.SlnWrapper(CmdArgs(args.toVector))
  def sln = clitools.SlnWrapper()

  def slopbed(args: String*) = clitools.SlopBedWrapper(CmdArgs(args.toVector))
  def slopbed = clitools.SlopBedWrapper()

  def smath(args: String*) = clitools.SmathWrapper(CmdArgs(args.toVector))
  def smath = clitools.SmathWrapper()

  def smbinfo(args: String*) = clitools.SmbinfoWrapper(CmdArgs(args.toVector))
  def smbinfo = clitools.SmbinfoWrapper()

  def snpeff(args: String*) = clitools.SnpeffWrapper(CmdArgs(args.toVector))
  def snpeff = clitools.SnpeffWrapper()

  def snpsift(args: String*) = clitools.SnpsiftWrapper(CmdArgs(args.toVector))
  def snpsift = clitools.SnpsiftWrapper()

  def soffice(args: String*) = clitools.SofficeWrapper(CmdArgs(args.toVector))
  def soffice = clitools.SofficeWrapper()

  def solid_action_desktop_gen(args: String*) =
    clitools.Solid_action_desktop_genWrapper(CmdArgs(args.toVector))
  def solid_action_desktop_gen = clitools.Solid_action_desktop_genWrapper()

  def solid_hardware5(args: String*) =
    clitools.Solid_hardware5Wrapper(CmdArgs(args.toVector))
  def solid_hardware5 = clitools.Solid_hardware5Wrapper()

  def sort(args: String*) = clitools.SortWrapper(CmdArgs(args.toVector))
  def sort = clitools.SortWrapper()

  def sortbed(args: String*) = clitools.SortBedWrapper(CmdArgs(args.toVector))
  def sortbed = clitools.SortBedWrapper()

  def sotruss(args: String*) = clitools.SotrussWrapper(CmdArgs(args.toVector))
  def sotruss = clitools.SotrussWrapper()

  def spawn_console(args: String*) =
    clitools.Spawn_consoleWrapper(CmdArgs(args.toVector))
  def spawn_console = clitools.Spawn_consoleWrapper()

  def spawn_login(args: String*) =
    clitools.Spawn_loginWrapper(CmdArgs(args.toVector))
  def spawn_login = clitools.Spawn_loginWrapper()

  def speaker_test(args: String*) =
    clitools.Speaker_testWrapper(CmdArgs(args.toVector))
  def speaker_test = clitools.Speaker_testWrapper()

  def spectacle(args: String*) =
    clitools.SpectacleWrapper(CmdArgs(args.toVector))
  def spectacle = clitools.SpectacleWrapper()

  def splain(args: String*) = clitools.SplainWrapper(CmdArgs(args.toVector))
  def splain = clitools.SplainWrapper()

  def split(args: String*) = clitools.SplitWrapper(CmdArgs(args.toVector))
  def split = clitools.SplitWrapper()

  def splitsource(args: String*) =
    clitools.SplitsourceWrapper(CmdArgs(args.toVector))
  def splitsource = clitools.SplitsourceWrapper()

  def splitter(args: String*) = clitools.SplitterWrapper(CmdArgs(args.toVector))
  def splitter = clitools.SplitterWrapper()

  def spotify(args: String*) = clitools.SpotifyWrapper(CmdArgs(args.toVector))
  def spotify = clitools.SpotifyWrapper()

  def sprof(args: String*) = clitools.SprofWrapper(CmdArgs(args.toVector))
  def sprof = clitools.SprofWrapper()

  def ss(args: String*) = clitools.SsWrapper(CmdArgs(args.toVector))
  def ss = clitools.SsWrapper()

  def sserver(args: String*) = clitools.SserverWrapper(CmdArgs(args.toVector))
  def sserver = clitools.SserverWrapper()

  def ssh(args: String*) = clitools.SshWrapper(CmdArgs(args.toVector))
  def ssh = clitools.SshWrapper()

  def ssh_add(args: String*) = clitools.Ssh_addWrapper(CmdArgs(args.toVector))
  def ssh_add = clitools.Ssh_addWrapper()

  def ssh_agent(args: String*) =
    clitools.Ssh_agentWrapper(CmdArgs(args.toVector))
  def ssh_agent = clitools.Ssh_agentWrapper()

  def ssh_copy_id(args: String*) =
    clitools.Ssh_copy_idWrapper(CmdArgs(args.toVector))
  def ssh_copy_id = clitools.Ssh_copy_idWrapper()

  def ssh_keygen(args: String*) =
    clitools.Ssh_keygenWrapper(CmdArgs(args.toVector))
  def ssh_keygen = clitools.Ssh_keygenWrapper()

  def ssh_keyscan(args: String*) =
    clitools.Ssh_keyscanWrapper(CmdArgs(args.toVector))
  def ssh_keyscan = clitools.Ssh_keyscanWrapper()

  def sshd(args: String*) = clitools.SshdWrapper(CmdArgs(args.toVector))
  def sshd = clitools.SshdWrapper()

  def startkde(args: String*) = clitools.StartkdeWrapper(CmdArgs(args.toVector))
  def startkde = clitools.StartkdeWrapper()

  def startplasmacompositor(args: String*) =
    clitools.StartplasmacompositorWrapper(CmdArgs(args.toVector))
  def startplasmacompositor = clitools.StartplasmacompositorWrapper()

  def stat(args: String*) = clitools.StatWrapper(CmdArgs(args.toVector))
  def stat = clitools.StatWrapper()

  def stdbuf(args: String*) = clitools.StdbufWrapper(CmdArgs(args.toVector))
  def stdbuf = clitools.StdbufWrapper()

  def strace(args: String*) = clitools.StraceWrapper(CmdArgs(args.toVector))
  def strace = clitools.StraceWrapper()

  def strace_graph(args: String*) =
    clitools.Strace_graphWrapper(CmdArgs(args.toVector))
  def strace_graph = clitools.Strace_graphWrapper()

  def strace_log_merge(args: String*) =
    clitools.Strace_log_mergeWrapper(CmdArgs(args.toVector))
  def strace_log_merge = clitools.Strace_log_mergeWrapper()

  def stream(args: String*) = clitools.StreamWrapper(CmdArgs(args.toVector))
  def stream = clitools.StreamWrapper()

  def stretcher(args: String*) =
    clitools.StretcherWrapper(CmdArgs(args.toVector))
  def stretcher = clitools.StretcherWrapper()

  def stssearch(args: String*) =
    clitools.StssearchWrapper(CmdArgs(args.toVector))
  def stssearch = clitools.StssearchWrapper()

  def stty(args: String*) = clitools.SttyWrapper(CmdArgs(args.toVector))
  def stty = clitools.SttyWrapper()

  def su(args: String*) = clitools.SuWrapper(CmdArgs(args.toVector))
  def su = clitools.SuWrapper()

  def subtractbed(args: String*) =
    clitools.SubtractBedWrapper(CmdArgs(args.toVector))
  def subtractbed = clitools.SubtractBedWrapper()

  def sudo(args: String*) = clitools.SudoWrapper(CmdArgs(args.toVector))
  def sudo = clitools.SudoWrapper()

  def sudoedit(args: String*) = clitools.SudoeditWrapper(CmdArgs(args.toVector))
  def sudoedit = clitools.SudoeditWrapper()

  def sudoreplay(args: String*) =
    clitools.SudoreplayWrapper(CmdArgs(args.toVector))
  def sudoreplay = clitools.SudoreplayWrapper()

  def sulogin(args: String*) = clitools.SuloginWrapper(CmdArgs(args.toVector))
  def sulogin = clitools.SuloginWrapper()

  def sum(args: String*) = clitools.SumWrapper(CmdArgs(args.toVector))
  def sum = clitools.SumWrapper()

  def supermatcher(args: String*) =
    clitools.SupermatcherWrapper(CmdArgs(args.toVector))
  def supermatcher = clitools.SupermatcherWrapper()

  def svn(args: String*) = clitools.SvnWrapper(CmdArgs(args.toVector))
  def svn = clitools.SvnWrapper()

  def svnadmin(args: String*) = clitools.SvnadminWrapper(CmdArgs(args.toVector))
  def svnadmin = clitools.SvnadminWrapper()

  def svnbench(args: String*) = clitools.SvnbenchWrapper(CmdArgs(args.toVector))
  def svnbench = clitools.SvnbenchWrapper()

  def svndumpfilter(args: String*) =
    clitools.SvndumpfilterWrapper(CmdArgs(args.toVector))
  def svndumpfilter = clitools.SvndumpfilterWrapper()

  def svnfsfs(args: String*) = clitools.SvnfsfsWrapper(CmdArgs(args.toVector))
  def svnfsfs = clitools.SvnfsfsWrapper()

  def svnlook(args: String*) = clitools.SvnlookWrapper(CmdArgs(args.toVector))
  def svnlook = clitools.SvnlookWrapper()

  def svnmucc(args: String*) = clitools.SvnmuccWrapper(CmdArgs(args.toVector))
  def svnmucc = clitools.SvnmuccWrapper()

  def svnrdump(args: String*) = clitools.SvnrdumpWrapper(CmdArgs(args.toVector))
  def svnrdump = clitools.SvnrdumpWrapper()

  def svnserve(args: String*) = clitools.SvnserveWrapper(CmdArgs(args.toVector))
  def svnserve = clitools.SvnserveWrapper()

  def svnsync(args: String*) = clitools.SvnsyncWrapper(CmdArgs(args.toVector))
  def svnsync = clitools.SvnsyncWrapper()

  def svnversion(args: String*) =
    clitools.SvnversionWrapper(CmdArgs(args.toVector))
  def svnversion = clitools.SvnversionWrapper()

  def swaplabel(args: String*) =
    clitools.SwaplabelWrapper(CmdArgs(args.toVector))
  def swaplabel = clitools.SwaplabelWrapper()

  def swapoff(args: String*) = clitools.SwapoffWrapper(CmdArgs(args.toVector))
  def swapoff = clitools.SwapoffWrapper()

  def swapon(args: String*) = clitools.SwaponWrapper(CmdArgs(args.toVector))
  def swapon = clitools.SwaponWrapper()

  def switch_root(args: String*) =
    clitools.Switch_rootWrapper(CmdArgs(args.toVector))
  def switch_root = clitools.Switch_rootWrapper()

  def swriter(args: String*) = clitools.SwriterWrapper(CmdArgs(args.toVector))
  def swriter = clitools.SwriterWrapper()

  def syco(args: String*) = clitools.SycoWrapper(CmdArgs(args.toVector))
  def syco = clitools.SycoWrapper()

  def sync(args: String*) = clitools.SyncWrapper(CmdArgs(args.toVector))
  def sync = clitools.SyncWrapper()

  def sysctl(args: String*) = clitools.SysctlWrapper(CmdArgs(args.toVector))
  def sysctl = clitools.SysctlWrapper()

  def system_config_printer(args: String*) =
    clitools.System_config_printerWrapper(CmdArgs(args.toVector))
  def system_config_printer = clitools.System_config_printerWrapper()

  def system_config_printer_applet(args: String*) =
    clitools.System_config_printer_appletWrapper(CmdArgs(args.toVector))
  def system_config_printer_applet =
    clitools.System_config_printer_appletWrapper()

  def systemctl(args: String*) =
    clitools.SystemctlWrapper(CmdArgs(args.toVector))
  def systemctl = clitools.SystemctlWrapper()

  def systemd_analyze(args: String*) =
    clitools.Systemd_analyzeWrapper(CmdArgs(args.toVector))
  def systemd_analyze = clitools.Systemd_analyzeWrapper()

  def systemd_ask_password(args: String*) =
    clitools.Systemd_ask_passwordWrapper(CmdArgs(args.toVector))
  def systemd_ask_password = clitools.Systemd_ask_passwordWrapper()

  def systemd_cat(args: String*) =
    clitools.Systemd_catWrapper(CmdArgs(args.toVector))
  def systemd_cat = clitools.Systemd_catWrapper()

  def systemd_cgls(args: String*) =
    clitools.Systemd_cglsWrapper(CmdArgs(args.toVector))
  def systemd_cgls = clitools.Systemd_cglsWrapper()

  def systemd_cgtop(args: String*) =
    clitools.Systemd_cgtopWrapper(CmdArgs(args.toVector))
  def systemd_cgtop = clitools.Systemd_cgtopWrapper()

  def systemd_delta(args: String*) =
    clitools.Systemd_deltaWrapper(CmdArgs(args.toVector))
  def systemd_delta = clitools.Systemd_deltaWrapper()

  def systemd_detect_virt(args: String*) =
    clitools.Systemd_detect_virtWrapper(CmdArgs(args.toVector))
  def systemd_detect_virt = clitools.Systemd_detect_virtWrapper()

  def systemd_escape(args: String*) =
    clitools.Systemd_escapeWrapper(CmdArgs(args.toVector))
  def systemd_escape = clitools.Systemd_escapeWrapper()

  def systemd_hwdb(args: String*) =
    clitools.Systemd_hwdbWrapper(CmdArgs(args.toVector))
  def systemd_hwdb = clitools.Systemd_hwdbWrapper()

  def systemd_id128(args: String*) =
    clitools.Systemd_id128Wrapper(CmdArgs(args.toVector))
  def systemd_id128 = clitools.Systemd_id128Wrapper()

  def systemd_inhibit(args: String*) =
    clitools.Systemd_inhibitWrapper(CmdArgs(args.toVector))
  def systemd_inhibit = clitools.Systemd_inhibitWrapper()

  def systemd_machine_id_setup(args: String*) =
    clitools.Systemd_machine_id_setupWrapper(CmdArgs(args.toVector))
  def systemd_machine_id_setup = clitools.Systemd_machine_id_setupWrapper()

  def systemd_mount(args: String*) =
    clitools.Systemd_mountWrapper(CmdArgs(args.toVector))
  def systemd_mount = clitools.Systemd_mountWrapper()

  def systemd_notify(args: String*) =
    clitools.Systemd_notifyWrapper(CmdArgs(args.toVector))
  def systemd_notify = clitools.Systemd_notifyWrapper()

  def systemd_nspawn(args: String*) =
    clitools.Systemd_nspawnWrapper(CmdArgs(args.toVector))
  def systemd_nspawn = clitools.Systemd_nspawnWrapper()

  def systemd_path(args: String*) =
    clitools.Systemd_pathWrapper(CmdArgs(args.toVector))
  def systemd_path = clitools.Systemd_pathWrapper()

  def systemd_resolve(args: String*) =
    clitools.Systemd_resolveWrapper(CmdArgs(args.toVector))
  def systemd_resolve = clitools.Systemd_resolveWrapper()

  def systemd_run(args: String*) =
    clitools.Systemd_runWrapper(CmdArgs(args.toVector))
  def systemd_run = clitools.Systemd_runWrapper()

  def systemd_socket_activate(args: String*) =
    clitools.Systemd_socket_activateWrapper(CmdArgs(args.toVector))
  def systemd_socket_activate = clitools.Systemd_socket_activateWrapper()

  def systemd_stdio_bridge(args: String*) =
    clitools.Systemd_stdio_bridgeWrapper(CmdArgs(args.toVector))
  def systemd_stdio_bridge = clitools.Systemd_stdio_bridgeWrapper()

  def systemd_tmpfiles(args: String*) =
    clitools.Systemd_tmpfilesWrapper(CmdArgs(args.toVector))
  def systemd_tmpfiles = clitools.Systemd_tmpfilesWrapper()

  def systemd_tty_ask_password_agent(args: String*) =
    clitools.Systemd_tty_ask_password_agentWrapper(CmdArgs(args.toVector))
  def systemd_tty_ask_password_agent =
    clitools.Systemd_tty_ask_password_agentWrapper()

  def systemd_umount(args: String*) =
    clitools.Systemd_umountWrapper(CmdArgs(args.toVector))
  def systemd_umount = clitools.Systemd_umountWrapper()

  def systemmonitor(args: String*) =
    clitools.SystemmonitorWrapper(CmdArgs(args.toVector))
  def systemmonitor = clitools.SystemmonitorWrapper()

  def systemsettings5(args: String*) =
    clitools.Systemsettings5Wrapper(CmdArgs(args.toVector))
  def systemsettings5 = clitools.Systemsettings5Wrapper()

  def tabs(args: String*) = clitools.TabsWrapper(CmdArgs(args.toVector))
  def tabs = clitools.TabsWrapper()

  def tac(args: String*) = clitools.TacWrapper(CmdArgs(args.toVector))
  def tac = clitools.TacWrapper()

  def tagbam(args: String*) = clitools.TagBamWrapper(CmdArgs(args.toVector))
  def tagbam = clitools.TagBamWrapper()

  def tail(args: String*) = clitools.TailWrapper(CmdArgs(args.toVector))
  def tail = clitools.TailWrapper()

  def tar(args: String*) = clitools.TarWrapper(CmdArgs(args.toVector))
  def tar = clitools.TarWrapper()

  def taskset(args: String*) = clitools.TasksetWrapper(CmdArgs(args.toVector))
  def taskset = clitools.TasksetWrapper()

  def taxget(args: String*) = clitools.TaxgetWrapper(CmdArgs(args.toVector))
  def taxget = clitools.TaxgetWrapper()

  def taxgetdown(args: String*) =
    clitools.TaxgetdownWrapper(CmdArgs(args.toVector))
  def taxgetdown = clitools.TaxgetdownWrapper()

  def taxgetrank(args: String*) =
    clitools.TaxgetrankWrapper(CmdArgs(args.toVector))
  def taxgetrank = clitools.TaxgetrankWrapper()

  def taxgetspecies(args: String*) =
    clitools.TaxgetspeciesWrapper(CmdArgs(args.toVector))
  def taxgetspecies = clitools.TaxgetspeciesWrapper()

  def taxgetup(args: String*) = clitools.TaxgetupWrapper(CmdArgs(args.toVector))
  def taxgetup = clitools.TaxgetupWrapper()

  def tc(args: String*) = clitools.TcWrapper(CmdArgs(args.toVector))
  def tc = clitools.TcWrapper()

  def tcode(args: String*) = clitools.TcodeWrapper(CmdArgs(args.toVector))
  def tcode = clitools.TcodeWrapper()

  def tee(args: String*) = clitools.TeeWrapper(CmdArgs(args.toVector))
  def tee = clitools.TeeWrapper()

  def telinit(args: String*) = clitools.TelinitWrapper(CmdArgs(args.toVector))
  def telinit = clitools.TelinitWrapper()

  def test(args: String*) = clitools.TestWrapper(CmdArgs(args.toVector))
  def test = clitools.TestWrapper()

  def texi2any(args: String*) = clitools.Texi2anyWrapper(CmdArgs(args.toVector))
  def texi2any = clitools.Texi2anyWrapper()

  def texi2dvi(args: String*) = clitools.Texi2dviWrapper(CmdArgs(args.toVector))
  def texi2dvi = clitools.Texi2dviWrapper()

  def texi2pdf(args: String*) = clitools.Texi2pdfWrapper(CmdArgs(args.toVector))
  def texi2pdf = clitools.Texi2pdfWrapper()

  def texindex(args: String*) = clitools.TexindexWrapper(CmdArgs(args.toVector))
  def texindex = clitools.TexindexWrapper()

  def textget(args: String*) = clitools.TextgetWrapper(CmdArgs(args.toVector))
  def textget = clitools.TextgetWrapper()

  def textsearch(args: String*) =
    clitools.TextsearchWrapper(CmdArgs(args.toVector))
  def textsearch = clitools.TextsearchWrapper()

  def tfextract(args: String*) =
    clitools.TfextractWrapper(CmdArgs(args.toVector))
  def tfextract = clitools.TfextractWrapper()

  def tfm(args: String*) = clitools.TfmWrapper(CmdArgs(args.toVector))
  def tfm = clitools.TfmWrapper()

  def tfscan(args: String*) = clitools.TfscanWrapper(CmdArgs(args.toVector))
  def tfscan = clitools.TfscanWrapper()

  def tftpd(args: String*) = clitools.TftpdWrapper(CmdArgs(args.toVector))
  def tftpd = clitools.TftpdWrapper()

  def tic(args: String*) = clitools.TicWrapper(CmdArgs(args.toVector))
  def tic = clitools.TicWrapper()

  def time(args: String*) = clitools.TimeWrapper(CmdArgs(args.toVector))
  def time = clitools.TimeWrapper()

  def timedatectl(args: String*) =
    clitools.TimedatectlWrapper(CmdArgs(args.toVector))
  def timedatectl = clitools.TimedatectlWrapper()

  def timeout(args: String*) = clitools.TimeoutWrapper(CmdArgs(args.toVector))
  def timeout = clitools.TimeoutWrapper()

  def tload(args: String*) = clitools.TloadWrapper(CmdArgs(args.toVector))
  def tload = clitools.TloadWrapper()

  def tmap(args: String*) = clitools.TmapWrapper(CmdArgs(args.toVector))
  def tmap = clitools.TmapWrapper()

  def top(args: String*) = clitools.TopWrapper(CmdArgs(args.toVector))
  def top = clitools.TopWrapper()

  def touch(args: String*) = clitools.TouchWrapper(CmdArgs(args.toVector))
  def touch = clitools.TouchWrapper()

  def tput(args: String*) = clitools.TputWrapper(CmdArgs(args.toVector))
  def tput = clitools.TputWrapper()

  def tr(args: String*) = clitools.TrWrapper(CmdArgs(args.toVector))
  def tr = clitools.TrWrapper()

  def tracepath(args: String*) =
    clitools.TracepathWrapper(CmdArgs(args.toVector))
  def tracepath = clitools.TracepathWrapper()

  def traceroute6(args: String*) =
    clitools.Traceroute6Wrapper(CmdArgs(args.toVector))
  def traceroute6 = clitools.Traceroute6Wrapper()

  def tranalign(args: String*) =
    clitools.TranalignWrapper(CmdArgs(args.toVector))
  def tranalign = clitools.TranalignWrapper()

  def transeq(args: String*) = clitools.TranseqWrapper(CmdArgs(args.toVector))
  def transeq = clitools.TranseqWrapper()

  def tred(args: String*) = clitools.TredWrapper(CmdArgs(args.toVector))
  def tred = clitools.TredWrapper()

  def tree(args: String*) = clitools.TreeWrapper(CmdArgs(args.toVector))
  def tree = clitools.TreeWrapper()

  def trimest(args: String*) = clitools.TrimestWrapper(CmdArgs(args.toVector))
  def trimest = clitools.TrimestWrapper()

  def trimseq(args: String*) = clitools.TrimseqWrapper(CmdArgs(args.toVector))
  def trimseq = clitools.TrimseqWrapper()

  def trimspace(args: String*) =
    clitools.TrimspaceWrapper(CmdArgs(args.toVector))
  def trimspace = clitools.TrimspaceWrapper()

  def truncate(args: String*) = clitools.TruncateWrapper(CmdArgs(args.toVector))
  def truncate = clitools.TruncateWrapper()

  def tset(args: String*) = clitools.TsetWrapper(CmdArgs(args.toVector))
  def tset = clitools.TsetWrapper()

  def tsort(args: String*) = clitools.TsortWrapper(CmdArgs(args.toVector))
  def tsort = clitools.TsortWrapper()

  def tty(args: String*) = clitools.TtyWrapper(CmdArgs(args.toVector))
  def tty = clitools.TtyWrapper()

  def tune2fs(args: String*) = clitools.Tune2fsWrapper(CmdArgs(args.toVector))
  def tune2fs = clitools.Tune2fsWrapper()

  def twofeat(args: String*) = clitools.TwofeatWrapper(CmdArgs(args.toVector))
  def twofeat = clitools.TwofeatWrapper()

  def twopi(args: String*) = clitools.TwopiWrapper(CmdArgs(args.toVector))
  def twopi = clitools.TwopiWrapper()

  def typeset(args: String*) = clitools.TypesetWrapper(CmdArgs(args.toVector))
  def typeset = clitools.TypesetWrapper()

  def tzselect(args: String*) = clitools.TzselectWrapper(CmdArgs(args.toVector))
  def tzselect = clitools.TzselectWrapper()

  def udevadm(args: String*) = clitools.UdevadmWrapper(CmdArgs(args.toVector))
  def udevadm = clitools.UdevadmWrapper()

  def udisksctl(args: String*) =
    clitools.UdisksctlWrapper(CmdArgs(args.toVector))
  def udisksctl = clitools.UdisksctlWrapper()

  def ul(args: String*) = clitools.UlWrapper(CmdArgs(args.toVector))
  def ul = clitools.UlWrapper()

  def ulimit(args: String*) = clitools.UlimitWrapper(CmdArgs(args.toVector))
  def ulimit = clitools.UlimitWrapper()

  def ulockmgr_server(args: String*) =
    clitools.Ulockmgr_serverWrapper(CmdArgs(args.toVector))
  def ulockmgr_server = clitools.Ulockmgr_serverWrapper()

  def umount(args: String*) = clitools.UmountWrapper(CmdArgs(args.toVector))
  def umount = clitools.UmountWrapper()

  def unalias(args: String*) = clitools.UnaliasWrapper(CmdArgs(args.toVector))
  def unalias = clitools.UnaliasWrapper()

  def uname(args: String*) = clitools.UnameWrapper(CmdArgs(args.toVector))
  def uname = clitools.UnameWrapper()

  def uname26(args: String*) = clitools.Uname26Wrapper(CmdArgs(args.toVector))
  def uname26 = clitools.Uname26Wrapper()

  def uncompress(args: String*) =
    clitools.UncompressWrapper(CmdArgs(args.toVector))
  def uncompress = clitools.UncompressWrapper()

  def unexpand(args: String*) = clitools.UnexpandWrapper(CmdArgs(args.toVector))
  def unexpand = clitools.UnexpandWrapper()

  def unflatten(args: String*) =
    clitools.UnflattenWrapper(CmdArgs(args.toVector))
  def unflatten = clitools.UnflattenWrapper()

  def unicode_start(args: String*) =
    clitools.Unicode_startWrapper(CmdArgs(args.toVector))
  def unicode_start = clitools.Unicode_startWrapper()

  def unicode_stop(args: String*) =
    clitools.Unicode_stopWrapper(CmdArgs(args.toVector))
  def unicode_stop = clitools.Unicode_stopWrapper()

  def union(args: String*) = clitools.UnionWrapper(CmdArgs(args.toVector))
  def union = clitools.UnionWrapper()

  def unionbedgraphs(args: String*) =
    clitools.UnionBedGraphsWrapper(CmdArgs(args.toVector))
  def unionbedgraphs = clitools.UnionBedGraphsWrapper()

  def uniq(args: String*) = clitools.UniqWrapper(CmdArgs(args.toVector))
  def uniq = clitools.UniqWrapper()

  def unix_chkpwd(args: String*) =
    clitools.Unix_chkpwdWrapper(CmdArgs(args.toVector))
  def unix_chkpwd = clitools.Unix_chkpwdWrapper()

  def unix_update(args: String*) =
    clitools.Unix_updateWrapper(CmdArgs(args.toVector))
  def unix_update = clitools.Unix_updateWrapper()

  def unlink(args: String*) = clitools.UnlinkWrapper(CmdArgs(args.toVector))
  def unlink = clitools.UnlinkWrapper()

  def unlzma(args: String*) = clitools.UnlzmaWrapper(CmdArgs(args.toVector))
  def unlzma = clitools.UnlzmaWrapper()

  def unshare(args: String*) = clitools.UnshareWrapper(CmdArgs(args.toVector))
  def unshare = clitools.UnshareWrapper()

  def unxz(args: String*) = clitools.UnxzWrapper(CmdArgs(args.toVector))
  def unxz = clitools.UnxzWrapper()

  def unzip(args: String*) = clitools.UnzipWrapper(CmdArgs(args.toVector))
  def unzip = clitools.UnzipWrapper()

  def unzipsfx(args: String*) = clitools.UnzipsfxWrapper(CmdArgs(args.toVector))
  def unzipsfx = clitools.UnzipsfxWrapper()

  def update_mime_database(args: String*) =
    clitools.Update_mime_databaseWrapper(CmdArgs(args.toVector))
  def update_mime_database = clitools.Update_mime_databaseWrapper()

  def updatedb(args: String*) = clitools.UpdatedbWrapper(CmdArgs(args.toVector))
  def updatedb = clitools.UpdatedbWrapper()

  def upower(args: String*) = clitools.UpowerWrapper(CmdArgs(args.toVector))
  def upower = clitools.UpowerWrapper()

  def uptime(args: String*) = clitools.UptimeWrapper(CmdArgs(args.toVector))
  def uptime = clitools.UptimeWrapper()

  def urlget(args: String*) = clitools.UrlgetWrapper(CmdArgs(args.toVector))
  def urlget = clitools.UrlgetWrapper()

  def useradd(args: String*) = clitools.UseraddWrapper(CmdArgs(args.toVector))
  def useradd = clitools.UseraddWrapper()

  def userdel(args: String*) = clitools.UserdelWrapper(CmdArgs(args.toVector))
  def userdel = clitools.UserdelWrapper()

  def usermod(args: String*) = clitools.UsermodWrapper(CmdArgs(args.toVector))
  def usermod = clitools.UsermodWrapper()

  def users(args: String*) = clitools.UsersWrapper(CmdArgs(args.toVector))
  def users = clitools.UsersWrapper()

  def utmpdump(args: String*) = clitools.UtmpdumpWrapper(CmdArgs(args.toVector))
  def utmpdump = clitools.UtmpdumpWrapper()

  def uuclient(args: String*) = clitools.UuclientWrapper(CmdArgs(args.toVector))
  def uuclient = clitools.UuclientWrapper()

  def uuidd(args: String*) = clitools.UuiddWrapper(CmdArgs(args.toVector))
  def uuidd = clitools.UuiddWrapper()

  def uuidgen(args: String*) = clitools.UuidgenWrapper(CmdArgs(args.toVector))
  def uuidgen = clitools.UuidgenWrapper()

  def uuidparse(args: String*) =
    clitools.UuidparseWrapper(CmdArgs(args.toVector))
  def uuidparse = clitools.UuidparseWrapper()

  def uuserver(args: String*) = clitools.UuserverWrapper(CmdArgs(args.toVector))
  def uuserver = clitools.UuserverWrapper()

  def uxterm(args: String*) = clitools.UxtermWrapper(CmdArgs(args.toVector))
  def uxterm = clitools.UxtermWrapper()

  def variationget(args: String*) =
    clitools.VariationgetWrapper(CmdArgs(args.toVector))
  def variationget = clitools.VariationgetWrapper()

  def varscan(args: String*) = clitools.VarscanWrapper(CmdArgs(args.toVector))
  def varscan = clitools.VarscanWrapper()

  def vcf_annotate(args: String*) =
    clitools.Vcf_annotateWrapper(CmdArgs(args.toVector))
  def vcf_annotate = clitools.Vcf_annotateWrapper()

  def vcf_compare(args: String*) =
    clitools.Vcf_compareWrapper(CmdArgs(args.toVector))
  def vcf_compare = clitools.Vcf_compareWrapper()

  def vcf_concat(args: String*) =
    clitools.Vcf_concatWrapper(CmdArgs(args.toVector))
  def vcf_concat = clitools.Vcf_concatWrapper()

  def vcf_consensus(args: String*) =
    clitools.Vcf_consensusWrapper(CmdArgs(args.toVector))
  def vcf_consensus = clitools.Vcf_consensusWrapper()

  def vcf_contrast(args: String*) =
    clitools.Vcf_contrastWrapper(CmdArgs(args.toVector))
  def vcf_contrast = clitools.Vcf_contrastWrapper()

  def vcf_convert(args: String*) =
    clitools.Vcf_convertWrapper(CmdArgs(args.toVector))
  def vcf_convert = clitools.Vcf_convertWrapper()

  def vcf_fix_newlines(args: String*) =
    clitools.Vcf_fix_newlinesWrapper(CmdArgs(args.toVector))
  def vcf_fix_newlines = clitools.Vcf_fix_newlinesWrapper()

  def vcf_fix_ploidy(args: String*) =
    clitools.Vcf_fix_ploidyWrapper(CmdArgs(args.toVector))
  def vcf_fix_ploidy = clitools.Vcf_fix_ploidyWrapper()

  def vcf_indel_stats(args: String*) =
    clitools.Vcf_indel_statsWrapper(CmdArgs(args.toVector))
  def vcf_indel_stats = clitools.Vcf_indel_statsWrapper()

  def vcf_isec(args: String*) = clitools.Vcf_isecWrapper(CmdArgs(args.toVector))
  def vcf_isec = clitools.Vcf_isecWrapper()

  def vcf_merge(args: String*) =
    clitools.Vcf_mergeWrapper(CmdArgs(args.toVector))
  def vcf_merge = clitools.Vcf_mergeWrapper()

  def vcf_phased_join(args: String*) =
    clitools.Vcf_phased_joinWrapper(CmdArgs(args.toVector))
  def vcf_phased_join = clitools.Vcf_phased_joinWrapper()

  def vcf_query(args: String*) =
    clitools.Vcf_queryWrapper(CmdArgs(args.toVector))
  def vcf_query = clitools.Vcf_queryWrapper()

  def vcf_shuffle_cols(args: String*) =
    clitools.Vcf_shuffle_colsWrapper(CmdArgs(args.toVector))
  def vcf_shuffle_cols = clitools.Vcf_shuffle_colsWrapper()

  def vcf_sort(args: String*) = clitools.Vcf_sortWrapper(CmdArgs(args.toVector))
  def vcf_sort = clitools.Vcf_sortWrapper()

  def vcf_stats(args: String*) =
    clitools.Vcf_statsWrapper(CmdArgs(args.toVector))
  def vcf_stats = clitools.Vcf_statsWrapper()

  def vcf_subset(args: String*) =
    clitools.Vcf_subsetWrapper(CmdArgs(args.toVector))
  def vcf_subset = clitools.Vcf_subsetWrapper()

  def vcf_to_tab(args: String*) =
    clitools.Vcf_to_tabWrapper(CmdArgs(args.toVector))
  def vcf_to_tab = clitools.Vcf_to_tabWrapper()

  def vcf_tstv(args: String*) = clitools.Vcf_tstvWrapper(CmdArgs(args.toVector))
  def vcf_tstv = clitools.Vcf_tstvWrapper()

  def vcf_validator(args: String*) =
    clitools.Vcf_validatorWrapper(CmdArgs(args.toVector))
  def vcf_validator = clitools.Vcf_validatorWrapper()

  def vcftools(args: String*) = clitools.VcftoolsWrapper(CmdArgs(args.toVector))
  def vcftools = clitools.VcftoolsWrapper()

  def vdir(args: String*) = clitools.VdirWrapper(CmdArgs(args.toVector))
  def vdir = clitools.VdirWrapper()

  def vectorstrip(args: String*) =
    clitools.VectorstripWrapper(CmdArgs(args.toVector))
  def vectorstrip = clitools.VectorstripWrapper()

  def veritysetup(args: String*) =
    clitools.VeritysetupWrapper(CmdArgs(args.toVector))
  def veritysetup = clitools.VeritysetupWrapper()

  def vgcfgbackup(args: String*) =
    clitools.VgcfgbackupWrapper(CmdArgs(args.toVector))
  def vgcfgbackup = clitools.VgcfgbackupWrapper()

  def vgcfgrestore(args: String*) =
    clitools.VgcfgrestoreWrapper(CmdArgs(args.toVector))
  def vgcfgrestore = clitools.VgcfgrestoreWrapper()

  def vgchange(args: String*) = clitools.VgchangeWrapper(CmdArgs(args.toVector))
  def vgchange = clitools.VgchangeWrapper()

  def vgck(args: String*) = clitools.VgckWrapper(CmdArgs(args.toVector))
  def vgck = clitools.VgckWrapper()

  def vgconvert(args: String*) =
    clitools.VgconvertWrapper(CmdArgs(args.toVector))
  def vgconvert = clitools.VgconvertWrapper()

  def vgcreate(args: String*) = clitools.VgcreateWrapper(CmdArgs(args.toVector))
  def vgcreate = clitools.VgcreateWrapper()

  def vgdisplay(args: String*) =
    clitools.VgdisplayWrapper(CmdArgs(args.toVector))
  def vgdisplay = clitools.VgdisplayWrapper()

  def vgexport(args: String*) = clitools.VgexportWrapper(CmdArgs(args.toVector))
  def vgexport = clitools.VgexportWrapper()

  def vgextend(args: String*) = clitools.VgextendWrapper(CmdArgs(args.toVector))
  def vgextend = clitools.VgextendWrapper()

  def vgimport(args: String*) = clitools.VgimportWrapper(CmdArgs(args.toVector))
  def vgimport = clitools.VgimportWrapper()

  def vgimportclone(args: String*) =
    clitools.VgimportcloneWrapper(CmdArgs(args.toVector))
  def vgimportclone = clitools.VgimportcloneWrapper()

  def vgmerge(args: String*) = clitools.VgmergeWrapper(CmdArgs(args.toVector))
  def vgmerge = clitools.VgmergeWrapper()

  def vgmknodes(args: String*) =
    clitools.VgmknodesWrapper(CmdArgs(args.toVector))
  def vgmknodes = clitools.VgmknodesWrapper()

  def vgreduce(args: String*) = clitools.VgreduceWrapper(CmdArgs(args.toVector))
  def vgreduce = clitools.VgreduceWrapper()

  def vgremove(args: String*) = clitools.VgremoveWrapper(CmdArgs(args.toVector))
  def vgremove = clitools.VgremoveWrapper()

  def vgrename(args: String*) = clitools.VgrenameWrapper(CmdArgs(args.toVector))
  def vgrename = clitools.VgrenameWrapper()

  def vgs(args: String*) = clitools.VgsWrapper(CmdArgs(args.toVector))
  def vgs = clitools.VgsWrapper()

  def vgscan(args: String*) = clitools.VgscanWrapper(CmdArgs(args.toVector))
  def vgscan = clitools.VgscanWrapper()

  def vgsplit(args: String*) = clitools.VgsplitWrapper(CmdArgs(args.toVector))
  def vgsplit = clitools.VgsplitWrapper()

  def vigr(args: String*) = clitools.VigrWrapper(CmdArgs(args.toVector))
  def vigr = clitools.VigrWrapper()

  def vimdot(args: String*) = clitools.VimdotWrapper(CmdArgs(args.toVector))
  def vimdot = clitools.VimdotWrapper()

  def vipw(args: String*) = clitools.VipwWrapper(CmdArgs(args.toVector))
  def vipw = clitools.VipwWrapper()

  def visudo(args: String*) = clitools.VisudoWrapper(CmdArgs(args.toVector))
  def visudo = clitools.VisudoWrapper()

  def vlc(args: String*) = clitools.VlcWrapper(CmdArgs(args.toVector))
  def vlc = clitools.VlcWrapper()

  def vlc_wrapper(args: String*) =
    clitools.Vlc_wrapperWrapper(CmdArgs(args.toVector))
  def vlc_wrapper = clitools.Vlc_wrapperWrapper()

  def vlock(args: String*) = clitools.VlockWrapper(CmdArgs(args.toVector))
  def vlock = clitools.VlockWrapper()

  def vmcore_dmesg(args: String*) =
    clitools.Vmcore_dmesgWrapper(CmdArgs(args.toVector))
  def vmcore_dmesg = clitools.Vmcore_dmesgWrapper()

  def vmstat(args: String*) = clitools.VmstatWrapper(CmdArgs(args.toVector))
  def vmstat = clitools.VmstatWrapper()

  def vpnc(args: String*) = clitools.VpncWrapper(CmdArgs(args.toVector))
  def vpnc = clitools.VpncWrapper()

  def vpnc_disconnect(args: String*) =
    clitools.Vpnc_disconnectWrapper(CmdArgs(args.toVector))
  def vpnc_disconnect = clitools.Vpnc_disconnectWrapper()

  def w(args: String*) = clitools.WWrapper(CmdArgs(args.toVector))
  def w = clitools.WWrapper()

  def wall(args: String*) = clitools.WallWrapper(CmdArgs(args.toVector))
  def wall = clitools.WallWrapper()

  def watch(args: String*) = clitools.WatchWrapper(CmdArgs(args.toVector))
  def watch = clitools.WatchWrapper()

  def watchgnupg(args: String*) =
    clitools.WatchgnupgWrapper(CmdArgs(args.toVector))
  def watchgnupg = clitools.WatchgnupgWrapper()

  def watchman(args: String*) = clitools.WatchmanWrapper(CmdArgs(args.toVector))
  def watchman = clitools.WatchmanWrapper()

  def water(args: String*) = clitools.WaterWrapper(CmdArgs(args.toVector))
  def water = clitools.WaterWrapper()

  def wc(args: String*) = clitools.WcWrapper(CmdArgs(args.toVector))
  def wc = clitools.WcWrapper()

  def wdctl(args: String*) = clitools.WdctlWrapper(CmdArgs(args.toVector))
  def wdctl = clitools.WdctlWrapper()

  def webpack(args: String*) = clitools.WebpackWrapper(CmdArgs(args.toVector))
  def webpack = clitools.WebpackWrapper()

  def wget(args: String*) = clitools.WgetWrapper(CmdArgs(args.toVector))
  def wget = clitools.WgetWrapper()

  def wgsim(args: String*) = clitools.WgsimWrapper(CmdArgs(args.toVector))
  def wgsim = clitools.WgsimWrapper()

  def whatis(args: String*) = clitools.WhatisWrapper(CmdArgs(args.toVector))
  def whatis = clitools.WhatisWrapper()

  def whereis(args: String*) = clitools.WhereisWrapper(CmdArgs(args.toVector))
  def whereis = clitools.WhereisWrapper()

  def which(args: String*) = clitools.WhichWrapper(CmdArgs(args.toVector))
  def which = clitools.WhichWrapper()

  def whichdb(args: String*) = clitools.WhichdbWrapper(CmdArgs(args.toVector))
  def whichdb = clitools.WhichdbWrapper()

  def who(args: String*) = clitools.WhoWrapper(CmdArgs(args.toVector))
  def who = clitools.WhoWrapper()

  def whoami(args: String*) = clitools.WhoamiWrapper(CmdArgs(args.toVector))
  def whoami = clitools.WhoamiWrapper()

  def windowbed(args: String*) =
    clitools.WindowBedWrapper(CmdArgs(args.toVector))
  def windowbed = clitools.WindowBedWrapper()

  def windowmaker(args: String*) =
    clitools.WindowMakerWrapper(CmdArgs(args.toVector))
  def windowmaker = clitools.WindowMakerWrapper()

  def wipefs(args: String*) = clitools.WipefsWrapper(CmdArgs(args.toVector))
  def wipefs = clitools.WipefsWrapper()

  def wmf2eps(args: String*) = clitools.Wmf2epsWrapper(CmdArgs(args.toVector))
  def wmf2eps = clitools.Wmf2epsWrapper()

  def wmf2fig(args: String*) = clitools.Wmf2figWrapper(CmdArgs(args.toVector))
  def wmf2fig = clitools.Wmf2figWrapper()

  def wmf2gd(args: String*) = clitools.Wmf2gdWrapper(CmdArgs(args.toVector))
  def wmf2gd = clitools.Wmf2gdWrapper()

  def wmf2svg(args: String*) = clitools.Wmf2svgWrapper(CmdArgs(args.toVector))
  def wmf2svg = clitools.Wmf2svgWrapper()

  def wmf2x(args: String*) = clitools.Wmf2xWrapper(CmdArgs(args.toVector))
  def wmf2x = clitools.Wmf2xWrapper()

  def wobble(args: String*) = clitools.WobbleWrapper(CmdArgs(args.toVector))
  def wobble = clitools.WobbleWrapper()

  def wordcount(args: String*) =
    clitools.WordcountWrapper(CmdArgs(args.toVector))
  def wordcount = clitools.WordcountWrapper()

  def wordfinder(args: String*) =
    clitools.WordfinderWrapper(CmdArgs(args.toVector))
  def wordfinder = clitools.WordfinderWrapper()

  def wordmatch(args: String*) =
    clitools.WordmatchWrapper(CmdArgs(args.toVector))
  def wordmatch = clitools.WordmatchWrapper()

  def wossdata(args: String*) = clitools.WossdataWrapper(CmdArgs(args.toVector))
  def wossdata = clitools.WossdataWrapper()

  def wossinput(args: String*) =
    clitools.WossinputWrapper(CmdArgs(args.toVector))
  def wossinput = clitools.WossinputWrapper()

  def wossname(args: String*) = clitools.WossnameWrapper(CmdArgs(args.toVector))
  def wossname = clitools.WossnameWrapper()

  def wossoperation(args: String*) =
    clitools.WossoperationWrapper(CmdArgs(args.toVector))
  def wossoperation = clitools.WossoperationWrapper()

  def wossoutput(args: String*) =
    clitools.WossoutputWrapper(CmdArgs(args.toVector))
  def wossoutput = clitools.WossoutputWrapper()

  def wossparam(args: String*) =
    clitools.WossparamWrapper(CmdArgs(args.toVector))
  def wossparam = clitools.WossparamWrapper()

  def wosstopic(args: String*) =
    clitools.WosstopicWrapper(CmdArgs(args.toVector))
  def wosstopic = clitools.WosstopicWrapper()

  def wpa_cli(args: String*) = clitools.Wpa_cliWrapper(CmdArgs(args.toVector))
  def wpa_cli = clitools.Wpa_cliWrapper()

  def wpa_passphrase(args: String*) =
    clitools.Wpa_passphraseWrapper(CmdArgs(args.toVector))
  def wpa_passphrase = clitools.Wpa_passphraseWrapper()

  def wpa_supplicant(args: String*) =
    clitools.Wpa_supplicantWrapper(CmdArgs(args.toVector))
  def wpa_supplicant = clitools.Wpa_supplicantWrapper()

  def write(args: String*) = clitools.WriteWrapper(CmdArgs(args.toVector))
  def write = clitools.WriteWrapper()

  def wxhexeditor(args: String*) =
    clitools.WxHexEditorWrapper(CmdArgs(args.toVector))
  def wxhexeditor = clitools.WxHexEditorWrapper()

  def x86_64(args: String*) = clitools.X86_64Wrapper(CmdArgs(args.toVector))
  def x86_64 = clitools.X86_64Wrapper()

  def xargs(args: String*) = clitools.XargsWrapper(CmdArgs(args.toVector))
  def xargs = clitools.XargsWrapper()

  def xauth(args: String*) = clitools.XauthWrapper(CmdArgs(args.toVector))
  def xauth = clitools.XauthWrapper()

  def xclip(args: String*) = clitools.XclipWrapper(CmdArgs(args.toVector))
  def xclip = clitools.XclipWrapper()

  def xclip_copyfile(args: String*) =
    clitools.Xclip_copyfileWrapper(CmdArgs(args.toVector))
  def xclip_copyfile = clitools.Xclip_copyfileWrapper()

  def xclip_cutfile(args: String*) =
    clitools.Xclip_cutfileWrapper(CmdArgs(args.toVector))
  def xclip_cutfile = clitools.Xclip_cutfileWrapper()

  def xclip_pastefile(args: String*) =
    clitools.Xclip_pastefileWrapper(CmdArgs(args.toVector))
  def xclip_pastefile = clitools.Xclip_pastefileWrapper()

  def xdg_desktop_icon(args: String*) =
    clitools.Xdg_desktop_iconWrapper(CmdArgs(args.toVector))
  def xdg_desktop_icon = clitools.Xdg_desktop_iconWrapper()

  def xdg_desktop_menu(args: String*) =
    clitools.Xdg_desktop_menuWrapper(CmdArgs(args.toVector))
  def xdg_desktop_menu = clitools.Xdg_desktop_menuWrapper()

  def xdg_email(args: String*) =
    clitools.Xdg_emailWrapper(CmdArgs(args.toVector))
  def xdg_email = clitools.Xdg_emailWrapper()

  def xdg_icon_resource(args: String*) =
    clitools.Xdg_icon_resourceWrapper(CmdArgs(args.toVector))
  def xdg_icon_resource = clitools.Xdg_icon_resourceWrapper()

  def xdg_mime(args: String*) = clitools.Xdg_mimeWrapper(CmdArgs(args.toVector))
  def xdg_mime = clitools.Xdg_mimeWrapper()

  def xdg_open(args: String*) = clitools.Xdg_openWrapper(CmdArgs(args.toVector))
  def xdg_open = clitools.Xdg_openWrapper()

  def xdg_screensaver(args: String*) =
    clitools.Xdg_screensaverWrapper(CmdArgs(args.toVector))
  def xdg_screensaver = clitools.Xdg_screensaverWrapper()

  def xdg_settings(args: String*) =
    clitools.Xdg_settingsWrapper(CmdArgs(args.toVector))
  def xdg_settings = clitools.Xdg_settingsWrapper()

  def xdg_user_dir(args: String*) =
    clitools.Xdg_user_dirWrapper(CmdArgs(args.toVector))
  def xdg_user_dir = clitools.Xdg_user_dirWrapper()

  def xdg_user_dirs_update(args: String*) =
    clitools.Xdg_user_dirs_updateWrapper(CmdArgs(args.toVector))
  def xdg_user_dirs_update = clitools.Xdg_user_dirs_updateWrapper()

  def xembedsniproxy(args: String*) =
    clitools.XembedsniproxyWrapper(CmdArgs(args.toVector))
  def xembedsniproxy = clitools.XembedsniproxyWrapper()

  def xinput(args: String*) = clitools.XinputWrapper(CmdArgs(args.toVector))
  def xinput = clitools.XinputWrapper()

  def xlsclients(args: String*) =
    clitools.XlsclientsWrapper(CmdArgs(args.toVector))
  def xlsclients = clitools.XlsclientsWrapper()

  def xmlget(args: String*) = clitools.XmlgetWrapper(CmdArgs(args.toVector))
  def xmlget = clitools.XmlgetWrapper()

  def xmltext(args: String*) = clitools.XmltextWrapper(CmdArgs(args.toVector))
  def xmltext = clitools.XmltextWrapper()

  def xprop(args: String*) = clitools.XpropWrapper(CmdArgs(args.toVector))
  def xprop = clitools.XpropWrapper()

  def xrandr(args: String*) = clitools.XrandrWrapper(CmdArgs(args.toVector))
  def xrandr = clitools.XrandrWrapper()

  def xrdb(args: String*) = clitools.XrdbWrapper(CmdArgs(args.toVector))
  def xrdb = clitools.XrdbWrapper()

  def xset(args: String*) = clitools.XsetWrapper(CmdArgs(args.toVector))
  def xset = clitools.XsetWrapper()

  def xsetroot(args: String*) = clitools.XsetrootWrapper(CmdArgs(args.toVector))
  def xsetroot = clitools.XsetrootWrapper()

  def xsubpp(args: String*) = clitools.XsubppWrapper(CmdArgs(args.toVector))
  def xsubpp = clitools.XsubppWrapper()

  def xtables_legacy_multi(args: String*) =
    clitools.Xtables_legacy_multiWrapper(CmdArgs(args.toVector))
  def xtables_legacy_multi = clitools.Xtables_legacy_multiWrapper()

  def xtables_monitor(args: String*) =
    clitools.Xtables_monitorWrapper(CmdArgs(args.toVector))
  def xtables_monitor = clitools.Xtables_monitorWrapper()

  def xtables_nft_multi(args: String*) =
    clitools.Xtables_nft_multiWrapper(CmdArgs(args.toVector))
  def xtables_nft_multi = clitools.Xtables_nft_multiWrapper()

  def xterm(args: String*) = clitools.XtermWrapper(CmdArgs(args.toVector))
  def xterm = clitools.XtermWrapper()

  def xtrace(args: String*) = clitools.XtraceWrapper(CmdArgs(args.toVector))
  def xtrace = clitools.XtraceWrapper()

  def xz(args: String*) = clitools.XzWrapper(CmdArgs(args.toVector))
  def xz = clitools.XzWrapper()

  def xzcat(args: String*) = clitools.XzcatWrapper(CmdArgs(args.toVector))
  def xzcat = clitools.XzcatWrapper()

  def xzcmp(args: String*) = clitools.XzcmpWrapper(CmdArgs(args.toVector))
  def xzcmp = clitools.XzcmpWrapper()

  def xzdec(args: String*) = clitools.XzdecWrapper(CmdArgs(args.toVector))
  def xzdec = clitools.XzdecWrapper()

  def xzdiff(args: String*) = clitools.XzdiffWrapper(CmdArgs(args.toVector))
  def xzdiff = clitools.XzdiffWrapper()

  def xzegrep(args: String*) = clitools.XzegrepWrapper(CmdArgs(args.toVector))
  def xzegrep = clitools.XzegrepWrapper()

  def xzfgrep(args: String*) = clitools.XzfgrepWrapper(CmdArgs(args.toVector))
  def xzfgrep = clitools.XzfgrepWrapper()

  def xzgrep(args: String*) = clitools.XzgrepWrapper(CmdArgs(args.toVector))
  def xzgrep = clitools.XzgrepWrapper()

  def xzless(args: String*) = clitools.XzlessWrapper(CmdArgs(args.toVector))
  def xzless = clitools.XzlessWrapper()

  def xzmore(args: String*) = clitools.XzmoreWrapper(CmdArgs(args.toVector))
  def xzmore = clitools.XzmoreWrapper()

  def yank(args: String*) = clitools.YankWrapper(CmdArgs(args.toVector))
  def yank = clitools.YankWrapper()

  def yarn(args: String*) = clitools.YarnWrapper(CmdArgs(args.toVector))
  def yarn = clitools.YarnWrapper()

  def yarnpkg(args: String*) = clitools.YarnpkgWrapper(CmdArgs(args.toVector))
  def yarnpkg = clitools.YarnpkgWrapper()

  def yes(args: String*) = clitools.YesWrapper(CmdArgs(args.toVector))
  def yes = clitools.YesWrapper()

  def ypdomainname(args: String*) =
    clitools.YpdomainnameWrapper(CmdArgs(args.toVector))
  def ypdomainname = clitools.YpdomainnameWrapper()

  def zcat(args: String*) = clitools.ZcatWrapper(CmdArgs(args.toVector))
  def zcat = clitools.ZcatWrapper()

  def zcmp(args: String*) = clitools.ZcmpWrapper(CmdArgs(args.toVector))
  def zcmp = clitools.ZcmpWrapper()

  def zdiff(args: String*) = clitools.ZdiffWrapper(CmdArgs(args.toVector))
  def zdiff = clitools.ZdiffWrapper()

  def zdump(args: String*) = clitools.ZdumpWrapper(CmdArgs(args.toVector))
  def zdump = clitools.ZdumpWrapper()

  def zegrep(args: String*) = clitools.ZegrepWrapper(CmdArgs(args.toVector))
  def zegrep = clitools.ZegrepWrapper()

  def zfgrep(args: String*) = clitools.ZfgrepWrapper(CmdArgs(args.toVector))
  def zfgrep = clitools.ZfgrepWrapper()

  def zforce(args: String*) = clitools.ZforceWrapper(CmdArgs(args.toVector))
  def zforce = clitools.ZforceWrapper()

  def zgrep(args: String*) = clitools.ZgrepWrapper(CmdArgs(args.toVector))
  def zgrep = clitools.ZgrepWrapper()

  def zic(args: String*) = clitools.ZicWrapper(CmdArgs(args.toVector))
  def zic = clitools.ZicWrapper()

  def zipdetails(args: String*) =
    clitools.ZipdetailsWrapper(CmdArgs(args.toVector))
  def zipdetails = clitools.ZipdetailsWrapper()

  def zipgrep(args: String*) = clitools.ZipgrepWrapper(CmdArgs(args.toVector))
  def zipgrep = clitools.ZipgrepWrapper()

  def zipinfo(args: String*) = clitools.ZipinfoWrapper(CmdArgs(args.toVector))
  def zipinfo = clitools.ZipinfoWrapper()

  def zless(args: String*) = clitools.ZlessWrapper(CmdArgs(args.toVector))
  def zless = clitools.ZlessWrapper()

  def zmore(args: String*) = clitools.ZmoreWrapper(CmdArgs(args.toVector))
  def zmore = clitools.ZmoreWrapper()

  def znew(args: String*) = clitools.ZnewWrapper(CmdArgs(args.toVector))
  def znew = clitools.ZnewWrapper()

  def zramctl(args: String*) = clitools.ZramctlWrapper(CmdArgs(args.toVector))
  def zramctl = clitools.ZramctlWrapper()

  def zsh(args: String*) = clitools.ZshWrapper(CmdArgs(args.toVector))
  def zsh = clitools.ZshWrapper()

  implicit class CmdSyntax(s: StringContext) {

    def txt(args: Any*) =
      TextVariable(CmdArgCtx(args.toVector, s))

    def array(args: Any*) =
      ArrayVariable(CmdArgCtx(args.toVector, s))

    def file(args: Any*): FilePath =
      FileConversions.convertToFilePath(s.s(args: _*))

    def fileName(args: Any*): FileName =
      FileConversions.convertToFileName(s.s(args: _*))

    def dirPath(args: Any*): FolderPath =
      FileConversions.convertToFolderPath(s.s(args: _*))

    def ModemManager(args: Any*) =
      SimpleCommand("ModemManager", CmdArgCtx(args.toVector, s))

    def NetworkManager(args: Any*) =
      SimpleCommand("NetworkManager", CmdArgCtx(args.toVector, s))

    def R(args: Any*) =
      SimpleCommand("R", CmdArgCtx(args.toVector, s))

    def STAR(args: Any*) =
      SimpleCommand("STAR", CmdArgCtx(args.toVector, s))

    def STARlong(args: Any*) =
      SimpleCommand("STARlong", CmdArgCtx(args.toVector, s))

    def X(args: Any*) =
      SimpleCommand("X", CmdArgCtx(args.toVector, s))

    def Xephyr(args: Any*) =
      SimpleCommand("Xephyr", CmdArgCtx(args.toVector, s))

    def Xnest(args: Any*) =
      SimpleCommand("Xnest", CmdArgCtx(args.toVector, s))

    def Xorg(args: Any*) =
      SimpleCommand("Xorg", CmdArgCtx(args.toVector, s))

    def Xvfb(args: Any*) =
      SimpleCommand("Xvfb", CmdArgCtx(args.toVector, s))

    def aaindexextract(args: Any*) =
      SimpleCommand("aaindexextract", CmdArgCtx(args.toVector, s))

    def abiview(args: Any*) =
      SimpleCommand("abiview", CmdArgCtx(args.toVector, s))

    def accept(args: Any*) =
      SimpleCommand("accept", CmdArgCtx(args.toVector, s))

    def accessdb(args: Any*) =
      SimpleCommand("accessdb", CmdArgCtx(args.toVector, s))

    def acdc(args: Any*) =
      SimpleCommand("acdc", CmdArgCtx(args.toVector, s))

    def acdgalaxy(args: Any*) =
      SimpleCommand("acdgalaxy", CmdArgCtx(args.toVector, s))

    def acdlog(args: Any*) =
      SimpleCommand("acdlog", CmdArgCtx(args.toVector, s))

    def acdpretty(args: Any*) =
      SimpleCommand("acdpretty", CmdArgCtx(args.toVector, s))

    def acdtable(args: Any*) =
      SimpleCommand("acdtable", CmdArgCtx(args.toVector, s))

    def acdtrace(args: Any*) =
      SimpleCommand("acdtrace", CmdArgCtx(args.toVector, s))

    def acdvalid(args: Any*) =
      SimpleCommand("acdvalid", CmdArgCtx(args.toVector, s))

    def ace2sam(args: Any*) =
      SimpleCommand("ace2sam", CmdArgCtx(args.toVector, s))

    def ack(args: Any*) =
      SimpleCommand("ack", CmdArgCtx(args.toVector, s))

    def aconnect(args: Any*) =
      SimpleCommand("aconnect", CmdArgCtx(args.toVector, s))

    def acroread(args: Any*) =
      SimpleCommand("acroread", CmdArgCtx(args.toVector, s))

    def acyclic(args: Any*) =
      SimpleCommand("acyclic", CmdArgCtx(args.toVector, s))

    def addgnupghome(args: Any*) =
      SimpleCommand("addgnupghome", CmdArgCtx(args.toVector, s))

    def addpart(args: Any*) =
      SimpleCommand("addpart", CmdArgCtx(args.toVector, s))

    def ag(args: Any*) =
      SimpleCommand("ag", CmdArgCtx(args.toVector, s))

    def agetty(args: Any*) =
      SimpleCommand("agetty", CmdArgCtx(args.toVector, s))

    def alias(args: Any*) =
      SimpleCommand("alias", CmdArgCtx(args.toVector, s))

    def aligncopy(args: Any*) =
      SimpleCommand("aligncopy", CmdArgCtx(args.toVector, s))

    def aligncopypair(args: Any*) =
      SimpleCommand("aligncopypair", CmdArgCtx(args.toVector, s))

    def alimask(args: Any*) =
      SimpleCommand("alimask", CmdArgCtx(args.toVector, s))

    def amidi(args: Any*) =
      SimpleCommand("amidi", CmdArgCtx(args.toVector, s))

    def amixer(args: Any*) =
      SimpleCommand("amixer", CmdArgCtx(args.toVector, s))

    def amm(args: Any*) =
      SimpleCommand("amm", CmdArgCtx(args.toVector, s))

    def animate(args: Any*) =
      SimpleCommand("animate", CmdArgCtx(args.toVector, s))

    def annotateBed(args: Any*) =
      SimpleCommand("annotateBed", CmdArgCtx(args.toVector, s))

    def ant(args: Any*) =
      SimpleCommand("ant", CmdArgCtx(args.toVector, s))

    def antigenic(args: Any*) =
      SimpleCommand("antigenic", CmdArgCtx(args.toVector, s))

    def aplay(args: Any*) =
      SimpleCommand("aplay", CmdArgCtx(args.toVector, s))

    def aplaymidi(args: Any*) =
      SimpleCommand("aplaymidi", CmdArgCtx(args.toVector, s))

    def applygnupgdefaults(args: Any*) =
      SimpleCommand("applygnupgdefaults", CmdArgCtx(args.toVector, s))

    def apropos(args: Any*) =
      SimpleCommand("apropos", CmdArgCtx(args.toVector, s))

    def apvlv(args: Any*) =
      SimpleCommand("apvlv", CmdArgCtx(args.toVector, s))

    def arecord(args: Any*) =
      SimpleCommand("arecord", CmdArgCtx(args.toVector, s))

    def arecordmidi(args: Any*) =
      SimpleCommand("arecordmidi", CmdArgCtx(args.toVector, s))

    def ark(args: Any*) =
      SimpleCommand("ark", CmdArgCtx(args.toVector, s))

    def arp(args: Any*) =
      SimpleCommand("arp", CmdArgCtx(args.toVector, s))

    def arpd(args: Any*) =
      SimpleCommand("arpd", CmdArgCtx(args.toVector, s))

    def arping(args: Any*) =
      SimpleCommand("arping", CmdArgCtx(args.toVector, s))

    def arptables(args: Any*) =
      SimpleCommand("arptables", CmdArgCtx(args.toVector, s))

    def arptables_nft(args: Any*) =
      SimpleCommand("arptables_nft", CmdArgCtx(args.toVector, s))

    def arptables_nft_restore(args: Any*) =
      SimpleCommand("arptables_nft_restore", CmdArgCtx(args.toVector, s))

    def arptables_nft_save(args: Any*) =
      SimpleCommand("arptables_nft_save", CmdArgCtx(args.toVector, s))

    def arptables_restore(args: Any*) =
      SimpleCommand("arptables_restore", CmdArgCtx(args.toVector, s))

    def arptables_save(args: Any*) =
      SimpleCommand("arptables_save", CmdArgCtx(args.toVector, s))

    def as(args: Any*) =
      SimpleCommand("as", CmdArgCtx(args.toVector, s))

    def aseqdump(args: Any*) =
      SimpleCommand("aseqdump", CmdArgCtx(args.toVector, s))

    def aseqnet(args: Any*) =
      SimpleCommand("aseqnet", CmdArgCtx(args.toVector, s))

    def assemblyget(args: Any*) =
      SimpleCommand("assemblyget", CmdArgCtx(args.toVector, s))

    def attr(args: Any*) =
      SimpleCommand("attr", CmdArgCtx(args.toVector, s))

    def awk(args: Any*) =
      SimpleCommand("awk", CmdArgCtx(args.toVector, s))

    def axfer(args: Any*) =
      SimpleCommand("axfer", CmdArgCtx(args.toVector, s))

    def b2sum(args: Any*) =
      SimpleCommand("b2sum", CmdArgCtx(args.toVector, s))

    def backtranambig(args: Any*) =
      SimpleCommand("backtranambig", CmdArgCtx(args.toVector, s))

    def backtranseq(args: Any*) =
      SimpleCommand("backtranseq", CmdArgCtx(args.toVector, s))

    def badblocks(args: Any*) =
      SimpleCommand("badblocks", CmdArgCtx(args.toVector, s))

    def baloo_file(args: Any*) =
      SimpleCommand("baloo_file", CmdArgCtx(args.toVector, s))

    def baloo_file_extractor(args: Any*) =
      SimpleCommand("baloo_file_extractor", CmdArgCtx(args.toVector, s))

    def balooctl(args: Any*) =
      SimpleCommand("balooctl", CmdArgCtx(args.toVector, s))

    def baloosearch(args: Any*) =
      SimpleCommand("baloosearch", CmdArgCtx(args.toVector, s))

    def balooshow(args: Any*) =
      SimpleCommand("balooshow", CmdArgCtx(args.toVector, s))

    def bamToBed(args: Any*) =
      SimpleCommand("bamToBed", CmdArgCtx(args.toVector, s))

    def bamToFastq(args: Any*) =
      SimpleCommand("bamToFastq", CmdArgCtx(args.toVector, s))

    def banana(args: Any*) =
      SimpleCommand("banana", CmdArgCtx(args.toVector, s))

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

    def bashbug(args: Any*) =
      SimpleCommand("bashbug", CmdArgCtx(args.toVector, s))

    def bcache_super_show(args: Any*) =
      SimpleCommand("bcache_super_show", CmdArgCtx(args.toVector, s))

    def bcftools(args: Any*) =
      SimpleCommand("bcftools", CmdArgCtx(args.toVector, s))

    def bcomps(args: Any*) =
      SimpleCommand("bcomps", CmdArgCtx(args.toVector, s))

    def bed12ToBed6(args: Any*) =
      SimpleCommand("bed12ToBed6", CmdArgCtx(args.toVector, s))

    def bedToBam(args: Any*) =
      SimpleCommand("bedToBam", CmdArgCtx(args.toVector, s))

    def bedToIgv(args: Any*) =
      SimpleCommand("bedToIgv", CmdArgCtx(args.toVector, s))

    def bedpeToBam(args: Any*) =
      SimpleCommand("bedpeToBam", CmdArgCtx(args.toVector, s))

    def bedtools(args: Any*) =
      SimpleCommand("bedtools", CmdArgCtx(args.toVector, s))

    def bind(args: Any*) =
      SimpleCommand("bind", CmdArgCtx(args.toVector, s))

    def biosed(args: Any*) =
      SimpleCommand("biosed", CmdArgCtx(args.toVector, s))

    def blkdeactivate(args: Any*) =
      SimpleCommand("blkdeactivate", CmdArgCtx(args.toVector, s))

    def blkdiscard(args: Any*) =
      SimpleCommand("blkdiscard", CmdArgCtx(args.toVector, s))

    def blkid(args: Any*) =
      SimpleCommand("blkid", CmdArgCtx(args.toVector, s))

    def blkzone(args: Any*) =
      SimpleCommand("blkzone", CmdArgCtx(args.toVector, s))

    def blockdev(args: Any*) =
      SimpleCommand("blockdev", CmdArgCtx(args.toVector, s))

    def bootctl(args: Any*) =
      SimpleCommand("bootctl", CmdArgCtx(args.toVector, s))

    def bowtie2(args: Any*) =
      SimpleCommand("bowtie2", CmdArgCtx(args.toVector, s))

    def bowtie2_align_l(args: Any*) =
      SimpleCommand("bowtie2_align_l", CmdArgCtx(args.toVector, s))

    def bowtie2_align_s(args: Any*) =
      SimpleCommand("bowtie2_align_s", CmdArgCtx(args.toVector, s))

    def bowtie2_build(args: Any*) =
      SimpleCommand("bowtie2_build", CmdArgCtx(args.toVector, s))

    def bowtie2_build_l(args: Any*) =
      SimpleCommand("bowtie2_build_l", CmdArgCtx(args.toVector, s))

    def bowtie2_build_s(args: Any*) =
      SimpleCommand("bowtie2_build_s", CmdArgCtx(args.toVector, s))

    def bowtie2_inspect(args: Any*) =
      SimpleCommand("bowtie2_inspect", CmdArgCtx(args.toVector, s))

    def bowtie2_inspect_l(args: Any*) =
      SimpleCommand("bowtie2_inspect_l", CmdArgCtx(args.toVector, s))

    def bowtie2_inspect_s(args: Any*) =
      SimpleCommand("bowtie2_inspect_s", CmdArgCtx(args.toVector, s))

    def bq(args: Any*) =
      SimpleCommand("bq", CmdArgCtx(args.toVector, s))

    def breeze_settings5(args: Any*) =
      SimpleCommand("breeze_settings5", CmdArgCtx(args.toVector, s))

    def bridge(args: Any*) =
      SimpleCommand("bridge", CmdArgCtx(args.toVector, s))

    def btwisted(args: Any*) =
      SimpleCommand("btwisted", CmdArgCtx(args.toVector, s))

    def builtin(args: Any*) =
      SimpleCommand("builtin", CmdArgCtx(args.toVector, s))

    def bunzip2(args: Any*) =
      SimpleCommand("bunzip2", CmdArgCtx(args.toVector, s))

    def busctl(args: Any*) =
      SimpleCommand("busctl", CmdArgCtx(args.toVector, s))

    def bwa(args: Any*) =
      SimpleCommand("bwa", CmdArgCtx(args.toVector, s))

    def bzcat(args: Any*) =
      SimpleCommand("bzcat", CmdArgCtx(args.toVector, s))

    def bzcmp(args: Any*) =
      SimpleCommand("bzcmp", CmdArgCtx(args.toVector, s))

    def bzdiff(args: Any*) =
      SimpleCommand("bzdiff", CmdArgCtx(args.toVector, s))

    def bzegrep(args: Any*) =
      SimpleCommand("bzegrep", CmdArgCtx(args.toVector, s))

    def bzfgrep(args: Any*) =
      SimpleCommand("bzfgrep", CmdArgCtx(args.toVector, s))

    def bzgrep(args: Any*) =
      SimpleCommand("bzgrep", CmdArgCtx(args.toVector, s))

    def bzip2(args: Any*) =
      SimpleCommand("bzip2", CmdArgCtx(args.toVector, s))

    def bzip2recover(args: Any*) =
      SimpleCommand("bzip2recover", CmdArgCtx(args.toVector, s))

    def bzless(args: Any*) =
      SimpleCommand("bzless", CmdArgCtx(args.toVector, s))

    def bzmore(args: Any*) =
      SimpleCommand("bzmore", CmdArgCtx(args.toVector, s))

    def cachedas(args: Any*) =
      SimpleCommand("cachedas", CmdArgCtx(args.toVector, s))

    def cachedbfetch(args: Any*) =
      SimpleCommand("cachedbfetch", CmdArgCtx(args.toVector, s))

    def cacheebeyesearch(args: Any*) =
      SimpleCommand("cacheebeyesearch", CmdArgCtx(args.toVector, s))

    def cacheensembl(args: Any*) =
      SimpleCommand("cacheensembl", CmdArgCtx(args.toVector, s))

    def cai(args: Any*) =
      SimpleCommand("cai", CmdArgCtx(args.toVector, s))

    def cal(args: Any*) =
      SimpleCommand("cal", CmdArgCtx(args.toVector, s))

    def caller(args: Any*) =
      SimpleCommand("caller", CmdArgCtx(args.toVector, s))

    def cancel(args: Any*) =
      SimpleCommand("cancel", CmdArgCtx(args.toVector, s))

    def capsh(args: Any*) =
      SimpleCommand("capsh", CmdArgCtx(args.toVector, s))

    def captoinfo(args: Any*) =
      SimpleCommand("captoinfo", CmdArgCtx(args.toVector, s))

    def cat(args: Any*) =
      SimpleCommand("cat", CmdArgCtx(args.toVector, s))

    def catchsegv(args: Any*) =
      SimpleCommand("catchsegv", CmdArgCtx(args.toVector, s))

    def catman(args: Any*) =
      SimpleCommand("catman", CmdArgCtx(args.toVector, s))

    def cc(args: Any*) =
      SimpleCommand("cc", CmdArgCtx(args.toVector, s))

    def ccomps(args: Any*) =
      SimpleCommand("ccomps", CmdArgCtx(args.toVector, s))

    def cd(args: Any*) =
      SimpleCommand("cd", CmdArgCtx(args.toVector, s))

    def cfdisk(args: Any*) =
      SimpleCommand("cfdisk", CmdArgCtx(args.toVector, s))

    def chacl(args: Any*) =
      SimpleCommand("chacl", CmdArgCtx(args.toVector, s))

    def chage(args: Any*) =
      SimpleCommand("chage", CmdArgCtx(args.toVector, s))

    def chaos(args: Any*) =
      SimpleCommand("chaos", CmdArgCtx(args.toVector, s))

    def charge(args: Any*) =
      SimpleCommand("charge", CmdArgCtx(args.toVector, s))

    def chattr(args: Any*) =
      SimpleCommand("chattr", CmdArgCtx(args.toVector, s))

    def chcon(args: Any*) =
      SimpleCommand("chcon", CmdArgCtx(args.toVector, s))

    def chcpu(args: Any*) =
      SimpleCommand("chcpu", CmdArgCtx(args.toVector, s))

    def checkXML5(args: Any*) =
      SimpleCommand("checkXML5", CmdArgCtx(args.toVector, s))

    def checktrans(args: Any*) =
      SimpleCommand("checktrans", CmdArgCtx(args.toVector, s))

    def chfn(args: Any*) =
      SimpleCommand("chfn", CmdArgCtx(args.toVector, s))

    def chgpasswd(args: Any*) =
      SimpleCommand("chgpasswd", CmdArgCtx(args.toVector, s))

    def chgrp(args: Any*) =
      SimpleCommand("chgrp", CmdArgCtx(args.toVector, s))

    def chips(args: Any*) =
      SimpleCommand("chips", CmdArgCtx(args.toVector, s))

    def chmem(args: Any*) =
      SimpleCommand("chmem", CmdArgCtx(args.toVector, s))

    def chmod(args: Any*) =
      SimpleCommand("chmod", CmdArgCtx(args.toVector, s))

    def choom(args: Any*) =
      SimpleCommand("choom", CmdArgCtx(args.toVector, s))

    def chown(args: Any*) =
      SimpleCommand("chown", CmdArgCtx(args.toVector, s))

    def chpasswd(args: Any*) =
      SimpleCommand("chpasswd", CmdArgCtx(args.toVector, s))

    def chromedriver(args: Any*) =
      SimpleCommand("chromedriver", CmdArgCtx(args.toVector, s))

    def chromium(args: Any*) =
      SimpleCommand("chromium", CmdArgCtx(args.toVector, s))

    def chromium_browser(args: Any*) =
      SimpleCommand("chromium_browser", CmdArgCtx(args.toVector, s))

    def chroot(args: Any*) =
      SimpleCommand("chroot", CmdArgCtx(args.toVector, s))

    def chrt(args: Any*) =
      SimpleCommand("chrt", CmdArgCtx(args.toVector, s))

    def chsh(args: Any*) =
      SimpleCommand("chsh", CmdArgCtx(args.toVector, s))

    def chvt(args: Any*) =
      SimpleCommand("chvt", CmdArgCtx(args.toVector, s))

    def cifscreds(args: Any*) =
      SimpleCommand("cifscreds", CmdArgCtx(args.toVector, s))

    def circo(args: Any*) =
      SimpleCommand("circo", CmdArgCtx(args.toVector, s))

    def cirdna(args: Any*) =
      SimpleCommand("cirdna", CmdArgCtx(args.toVector, s))

    def cisco_decrypt(args: Any*) =
      SimpleCommand("cisco_decrypt", CmdArgCtx(args.toVector, s))

    def cksum(args: Any*) =
      SimpleCommand("cksum", CmdArgCtx(args.toVector, s))

    def clear(args: Any*) =
      SimpleCommand("clear", CmdArgCtx(args.toVector, s))

    def clockdiff(args: Any*) =
      SimpleCommand("clockdiff", CmdArgCtx(args.toVector, s))

    def closestBed(args: Any*) =
      SimpleCommand("closestBed", CmdArgCtx(args.toVector, s))

    def clrunimap(args: Any*) =
      SimpleCommand("clrunimap", CmdArgCtx(args.toVector, s))

    def cluster(args: Any*) =
      SimpleCommand("cluster", CmdArgCtx(args.toVector, s))

    def clusterBed(args: Any*) =
      SimpleCommand("clusterBed", CmdArgCtx(args.toVector, s))

    def cmp(args: Any*) =
      SimpleCommand("cmp", CmdArgCtx(args.toVector, s))

    def codcmp(args: Any*) =
      SimpleCommand("codcmp", CmdArgCtx(args.toVector, s))

    def codcopy(args: Any*) =
      SimpleCommand("codcopy", CmdArgCtx(args.toVector, s))

    def code(args: Any*) =
      SimpleCommand("code", CmdArgCtx(args.toVector, s))

    def coderet(args: Any*) =
      SimpleCommand("coderet", CmdArgCtx(args.toVector, s))

    def col(args: Any*) =
      SimpleCommand("col", CmdArgCtx(args.toVector, s))

    def colcrt(args: Any*) =
      SimpleCommand("colcrt", CmdArgCtx(args.toVector, s))

    def colrm(args: Any*) =
      SimpleCommand("colrm", CmdArgCtx(args.toVector, s))

    def column(args: Any*) =
      SimpleCommand("column", CmdArgCtx(args.toVector, s))

    def comm(args: Any*) =
      SimpleCommand("comm", CmdArgCtx(args.toVector, s))

    def command(args: Any*) =
      SimpleCommand("command", CmdArgCtx(args.toVector, s))

    def command_not_found(args: Any*) =
      SimpleCommand("command_not_found", CmdArgCtx(args.toVector, s))

    def compare(args: Any*) =
      SimpleCommand("compare", CmdArgCtx(args.toVector, s))

    def compile_et(args: Any*) =
      SimpleCommand("compile_et", CmdArgCtx(args.toVector, s))

    def complementBed(args: Any*) =
      SimpleCommand("complementBed", CmdArgCtx(args.toVector, s))

    def composite(args: Any*) =
      SimpleCommand("composite", CmdArgCtx(args.toVector, s))

    def compseq(args: Any*) =
      SimpleCommand("compseq", CmdArgCtx(args.toVector, s))

    def configure_printer(args: Any*) =
      SimpleCommand("configure_printer", CmdArgCtx(args.toVector, s))

    def conjure(args: Any*) =
      SimpleCommand("conjure", CmdArgCtx(args.toVector, s))

    def cons(args: Any*) =
      SimpleCommand("cons", CmdArgCtx(args.toVector, s))

    def consambig(args: Any*) =
      SimpleCommand("consambig", CmdArgCtx(args.toVector, s))

    def convert(args: Any*) =
      SimpleCommand("convert", CmdArgCtx(args.toVector, s))

    def coredumpctl(args: Any*) =
      SimpleCommand("coredumpctl", CmdArgCtx(args.toVector, s))

    def corelist(args: Any*) =
      SimpleCommand("corelist", CmdArgCtx(args.toVector, s))

    def coreutils(args: Any*) =
      SimpleCommand("coreutils", CmdArgCtx(args.toVector, s))

    def coverageBed(args: Any*) =
      SimpleCommand("coverageBed", CmdArgCtx(args.toVector, s))

    def cp(args: Any*) =
      SimpleCommand("cp", CmdArgCtx(args.toVector, s))

    def cpan(args: Any*) =
      SimpleCommand("cpan", CmdArgCtx(args.toVector, s))

    def cpgplot(args: Any*) =
      SimpleCommand("cpgplot", CmdArgCtx(args.toVector, s))

    def cpgreport(args: Any*) =
      SimpleCommand("cpgreport", CmdArgCtx(args.toVector, s))

    def cpio(args: Any*) =
      SimpleCommand("cpio", CmdArgCtx(args.toVector, s))

    def cpp(args: Any*) =
      SimpleCommand("cpp", CmdArgCtx(args.toVector, s))

    def cpufreq_bench(args: Any*) =
      SimpleCommand("cpufreq_bench", CmdArgCtx(args.toVector, s))

    def cpupower(args: Any*) =
      SimpleCommand("cpupower", CmdArgCtx(args.toVector, s))

    def crda(args: Any*) =
      SimpleCommand("crda", CmdArgCtx(args.toVector, s))

    def cryptsetup(args: Any*) =
      SimpleCommand("cryptsetup", CmdArgCtx(args.toVector, s))

    def cryptsetup_reencrypt(args: Any*) =
      SimpleCommand("cryptsetup_reencrypt", CmdArgCtx(args.toVector, s))

    def csplit(args: Any*) =
      SimpleCommand("csplit", CmdArgCtx(args.toVector, s))

    def ctags(args: Any*) =
      SimpleCommand("ctags", CmdArgCtx(args.toVector, s))

    def ctrlaltdel(args: Any*) =
      SimpleCommand("ctrlaltdel", CmdArgCtx(args.toVector, s))

    def ctstat(args: Any*) =
      SimpleCommand("ctstat", CmdArgCtx(args.toVector, s))

    def cupsaccept(args: Any*) =
      SimpleCommand("cupsaccept", CmdArgCtx(args.toVector, s))

    def cupsaddsmb(args: Any*) =
      SimpleCommand("cupsaddsmb", CmdArgCtx(args.toVector, s))

    def cupsctl(args: Any*) =
      SimpleCommand("cupsctl", CmdArgCtx(args.toVector, s))

    def cupsd(args: Any*) =
      SimpleCommand("cupsd", CmdArgCtx(args.toVector, s))

    def cupsdisable(args: Any*) =
      SimpleCommand("cupsdisable", CmdArgCtx(args.toVector, s))

    def cupsenable(args: Any*) =
      SimpleCommand("cupsenable", CmdArgCtx(args.toVector, s))

    def cupsfilter(args: Any*) =
      SimpleCommand("cupsfilter", CmdArgCtx(args.toVector, s))

    def cupsreject(args: Any*) =
      SimpleCommand("cupsreject", CmdArgCtx(args.toVector, s))

    def cupstestdsc(args: Any*) =
      SimpleCommand("cupstestdsc", CmdArgCtx(args.toVector, s))

    def cupstestppd(args: Any*) =
      SimpleCommand("cupstestppd", CmdArgCtx(args.toVector, s))

    def curl(args: Any*) =
      SimpleCommand("curl", CmdArgCtx(args.toVector, s))

    def cusp(args: Any*) =
      SimpleCommand("cusp", CmdArgCtx(args.toVector, s))

    def cut(args: Any*) =
      SimpleCommand("cut", CmdArgCtx(args.toVector, s))

    def cutgextract(args: Any*) =
      SimpleCommand("cutgextract", CmdArgCtx(args.toVector, s))

    def cutseq(args: Any*) =
      SimpleCommand("cutseq", CmdArgCtx(args.toVector, s))

    def cvlc(args: Any*) =
      SimpleCommand("cvlc", CmdArgCtx(args.toVector, s))

    def cvt(args: Any*) =
      SimpleCommand("cvt", CmdArgCtx(args.toVector, s))

    def cvtsudoers(args: Any*) =
      SimpleCommand("cvtsudoers", CmdArgCtx(args.toVector, s))

    def dan(args: Any*) =
      SimpleCommand("dan", CmdArgCtx(args.toVector, s))

    def date(args: Any*) =
      SimpleCommand("date", CmdArgCtx(args.toVector, s))

    def dbiblast(args: Any*) =
      SimpleCommand("dbiblast", CmdArgCtx(args.toVector, s))

    def dbifasta(args: Any*) =
      SimpleCommand("dbifasta", CmdArgCtx(args.toVector, s))

    def dbiflat(args: Any*) =
      SimpleCommand("dbiflat", CmdArgCtx(args.toVector, s))

    def dbigcg(args: Any*) =
      SimpleCommand("dbigcg", CmdArgCtx(args.toVector, s))

    def dbtell(args: Any*) =
      SimpleCommand("dbtell", CmdArgCtx(args.toVector, s))

    def dbus_cleanup_sockets(args: Any*) =
      SimpleCommand("dbus_cleanup_sockets", CmdArgCtx(args.toVector, s))

    def dbus_daemon(args: Any*) =
      SimpleCommand("dbus_daemon", CmdArgCtx(args.toVector, s))

    def dbus_launch(args: Any*) =
      SimpleCommand("dbus_launch", CmdArgCtx(args.toVector, s))

    def dbus_monitor(args: Any*) =
      SimpleCommand("dbus_monitor", CmdArgCtx(args.toVector, s))

    def dbus_run_session(args: Any*) =
      SimpleCommand("dbus_run_session", CmdArgCtx(args.toVector, s))

    def dbus_send(args: Any*) =
      SimpleCommand("dbus_send", CmdArgCtx(args.toVector, s))

    def dbus_test_tool(args: Any*) =
      SimpleCommand("dbus_test_tool", CmdArgCtx(args.toVector, s))

    def dbus_update_activation_environment(args: Any*) =
      SimpleCommand(
        "dbus_update_activation_environment",
        CmdArgCtx(args.toVector, s)
      )

    def dbus_uuidgen(args: Any*) =
      SimpleCommand("dbus_uuidgen", CmdArgCtx(args.toVector, s))

    def dbxcompress(args: Any*) =
      SimpleCommand("dbxcompress", CmdArgCtx(args.toVector, s))

    def dbxedam(args: Any*) =
      SimpleCommand("dbxedam", CmdArgCtx(args.toVector, s))

    def dbxfasta(args: Any*) =
      SimpleCommand("dbxfasta", CmdArgCtx(args.toVector, s))

    def dbxflat(args: Any*) =
      SimpleCommand("dbxflat", CmdArgCtx(args.toVector, s))

    def dbxgcg(args: Any*) =
      SimpleCommand("dbxgcg", CmdArgCtx(args.toVector, s))

    def dbxobo(args: Any*) =
      SimpleCommand("dbxobo", CmdArgCtx(args.toVector, s))

    def dbxreport(args: Any*) =
      SimpleCommand("dbxreport", CmdArgCtx(args.toVector, s))

    def dbxresource(args: Any*) =
      SimpleCommand("dbxresource", CmdArgCtx(args.toVector, s))

    def dbxstat(args: Any*) =
      SimpleCommand("dbxstat", CmdArgCtx(args.toVector, s))

    def dbxtax(args: Any*) =
      SimpleCommand("dbxtax", CmdArgCtx(args.toVector, s))

    def dbxuncompress(args: Any*) =
      SimpleCommand("dbxuncompress", CmdArgCtx(args.toVector, s))

    def dd(args: Any*) =
      SimpleCommand("dd", CmdArgCtx(args.toVector, s))

    def deallocvt(args: Any*) =
      SimpleCommand("deallocvt", CmdArgCtx(args.toVector, s))

    def debootstrap(args: Any*) =
      SimpleCommand("debootstrap", CmdArgCtx(args.toVector, s))

    def debugfs(args: Any*) =
      SimpleCommand("debugfs", CmdArgCtx(args.toVector, s))

    def declare(args: Any*) =
      SimpleCommand("declare", CmdArgCtx(args.toVector, s))

    def degapseq(args: Any*) =
      SimpleCommand("degapseq", CmdArgCtx(args.toVector, s))

    def delpart(args: Any*) =
      SimpleCommand("delpart", CmdArgCtx(args.toVector, s))

    def density(args: Any*) =
      SimpleCommand("density", CmdArgCtx(args.toVector, s))

    def depmod(args: Any*) =
      SimpleCommand("depmod", CmdArgCtx(args.toVector, s))

    def descseq(args: Any*) =
      SimpleCommand("descseq", CmdArgCtx(args.toVector, s))

    def desktoptojson(args: Any*) =
      SimpleCommand("desktoptojson", CmdArgCtx(args.toVector, s))

    def df(args: Any*) =
      SimpleCommand("df", CmdArgCtx(args.toVector, s))

    def dhcpcd(args: Any*) =
      SimpleCommand("dhcpcd", CmdArgCtx(args.toVector, s))

    def dhex(args: Any*) =
      SimpleCommand("dhex", CmdArgCtx(args.toVector, s))

    def diamond(args: Any*) =
      SimpleCommand("diamond", CmdArgCtx(args.toVector, s))

    def diff(args: Any*) =
      SimpleCommand("diff", CmdArgCtx(args.toVector, s))

    def diff3(args: Any*) =
      SimpleCommand("diff3", CmdArgCtx(args.toVector, s))

    def diffimg(args: Any*) =
      SimpleCommand("diffimg", CmdArgCtx(args.toVector, s))

    def diffseq(args: Any*) =
      SimpleCommand("diffseq", CmdArgCtx(args.toVector, s))

    def dijkstra(args: Any*) =
      SimpleCommand("dijkstra", CmdArgCtx(args.toVector, s))

    def dir(args: Any*) =
      SimpleCommand("dir", CmdArgCtx(args.toVector, s))

    def dircolors(args: Any*) =
      SimpleCommand("dircolors", CmdArgCtx(args.toVector, s))

    def dirmngr(args: Any*) =
      SimpleCommand("dirmngr", CmdArgCtx(args.toVector, s))

    def dirmngr_client(args: Any*) =
      SimpleCommand("dirmngr_client", CmdArgCtx(args.toVector, s))

    def dirname(args: Any*) =
      SimpleCommand("dirname", CmdArgCtx(args.toVector, s))

    def display(args: Any*) =
      SimpleCommand("display", CmdArgCtx(args.toVector, s))

    def distmat(args: Any*) =
      SimpleCommand("distmat", CmdArgCtx(args.toVector, s))

    def dmesg(args: Any*) =
      SimpleCommand("dmesg", CmdArgCtx(args.toVector, s))

    def dmsetup(args: Any*) =
      SimpleCommand("dmsetup", CmdArgCtx(args.toVector, s))

    def dmstats(args: Any*) =
      SimpleCommand("dmstats", CmdArgCtx(args.toVector, s))

    def dnsdomainname(args: Any*) =
      SimpleCommand("dnsdomainname", CmdArgCtx(args.toVector, s))

    def docker(args: Any*) =
      SimpleCommand("docker", CmdArgCtx(args.toVector, s))

    def docker_credential_gcloud(args: Any*) =
      SimpleCommand("docker_credential_gcloud", CmdArgCtx(args.toVector, s))

    def dockerd(args: Any*) =
      SimpleCommand("dockerd", CmdArgCtx(args.toVector, s))

    def dolphin(args: Any*) =
      SimpleCommand("dolphin", CmdArgCtx(args.toVector, s))

    def domainname(args: Any*) =
      SimpleCommand("domainname", CmdArgCtx(args.toVector, s))

    def dosfsck(args: Any*) =
      SimpleCommand("dosfsck", CmdArgCtx(args.toVector, s))

    def dosfslabel(args: Any*) =
      SimpleCommand("dosfslabel", CmdArgCtx(args.toVector, s))

    def dot(args: Any*) =
      SimpleCommand("dot", CmdArgCtx(args.toVector, s))

    def dot2gxl(args: Any*) =
      SimpleCommand("dot2gxl", CmdArgCtx(args.toVector, s))

    def dot_builtins(args: Any*) =
      SimpleCommand("dot_builtins", CmdArgCtx(args.toVector, s))

    def dotmatcher(args: Any*) =
      SimpleCommand("dotmatcher", CmdArgCtx(args.toVector, s))

    def dotpath(args: Any*) =
      SimpleCommand("dotpath", CmdArgCtx(args.toVector, s))

    def dottup(args: Any*) =
      SimpleCommand("dottup", CmdArgCtx(args.toVector, s))

    def dotty(args: Any*) =
      SimpleCommand("dotty", CmdArgCtx(args.toVector, s))

    def dreg(args: Any*) =
      SimpleCommand("dreg", CmdArgCtx(args.toVector, s))

    def drfinddata(args: Any*) =
      SimpleCommand("drfinddata", CmdArgCtx(args.toVector, s))

    def drfindformat(args: Any*) =
      SimpleCommand("drfindformat", CmdArgCtx(args.toVector, s))

    def drfindid(args: Any*) =
      SimpleCommand("drfindid", CmdArgCtx(args.toVector, s))

    def drfindresource(args: Any*) =
      SimpleCommand("drfindresource", CmdArgCtx(args.toVector, s))

    def drget(args: Any*) =
      SimpleCommand("drget", CmdArgCtx(args.toVector, s))

    def drtext(args: Any*) =
      SimpleCommand("drtext", CmdArgCtx(args.toVector, s))

    def du(args: Any*) =
      SimpleCommand("du", CmdArgCtx(args.toVector, s))

    def dumpe2fs(args: Any*) =
      SimpleCommand("dumpe2fs", CmdArgCtx(args.toVector, s))

    def dumpkeys(args: Any*) =
      SimpleCommand("dumpkeys", CmdArgCtx(args.toVector, s))

    def dvipdf(args: Any*) =
      SimpleCommand("dvipdf", CmdArgCtx(args.toVector, s))

    def e2freefrag(args: Any*) =
      SimpleCommand("e2freefrag", CmdArgCtx(args.toVector, s))

    def e2fsck(args: Any*) =
      SimpleCommand("e2fsck", CmdArgCtx(args.toVector, s))

    def e2image(args: Any*) =
      SimpleCommand("e2image", CmdArgCtx(args.toVector, s))

    def e2label(args: Any*) =
      SimpleCommand("e2label", CmdArgCtx(args.toVector, s))

    def e2mmpstatus(args: Any*) =
      SimpleCommand("e2mmpstatus", CmdArgCtx(args.toVector, s))

    def e2scrub(args: Any*) =
      SimpleCommand("e2scrub", CmdArgCtx(args.toVector, s))

    def e2scrub_all(args: Any*) =
      SimpleCommand("e2scrub_all", CmdArgCtx(args.toVector, s))

    def e2undo(args: Any*) =
      SimpleCommand("e2undo", CmdArgCtx(args.toVector, s))

    def e4crypt(args: Any*) =
      SimpleCommand("e4crypt", CmdArgCtx(args.toVector, s))

    def e4defrag(args: Any*) =
      SimpleCommand("e4defrag", CmdArgCtx(args.toVector, s))

    def ebtables(args: Any*) =
      SimpleCommand("ebtables", CmdArgCtx(args.toVector, s))

    def ebtables_nft(args: Any*) =
      SimpleCommand("ebtables_nft", CmdArgCtx(args.toVector, s))

    def ebtables_nft_restore(args: Any*) =
      SimpleCommand("ebtables_nft_restore", CmdArgCtx(args.toVector, s))

    def ebtables_nft_save(args: Any*) =
      SimpleCommand("ebtables_nft_save", CmdArgCtx(args.toVector, s))

    def ebtables_restore(args: Any*) =
      SimpleCommand("ebtables_restore", CmdArgCtx(args.toVector, s))

    def ebtables_save(args: Any*) =
      SimpleCommand("ebtables_save", CmdArgCtx(args.toVector, s))

    def echo(args: Any*) =
      SimpleCommand("echo", CmdArgCtx(args.toVector, s))

    def edamdef(args: Any*) =
      SimpleCommand("edamdef", CmdArgCtx(args.toVector, s))

    def edamhasinput(args: Any*) =
      SimpleCommand("edamhasinput", CmdArgCtx(args.toVector, s))

    def edamhasoutput(args: Any*) =
      SimpleCommand("edamhasoutput", CmdArgCtx(args.toVector, s))

    def edamisformat(args: Any*) =
      SimpleCommand("edamisformat", CmdArgCtx(args.toVector, s))

    def edamisid(args: Any*) =
      SimpleCommand("edamisid", CmdArgCtx(args.toVector, s))

    def edamname(args: Any*) =
      SimpleCommand("edamname", CmdArgCtx(args.toVector, s))

    def edgepaint(args: Any*) =
      SimpleCommand("edgepaint", CmdArgCtx(args.toVector, s))

    def edialign(args: Any*) =
      SimpleCommand("edialign", CmdArgCtx(args.toVector, s))

    def egrep(args: Any*) =
      SimpleCommand("egrep", CmdArgCtx(args.toVector, s))

    def einverted(args: Any*) =
      SimpleCommand("einverted", CmdArgCtx(args.toVector, s))

    def eject(args: Any*) =
      SimpleCommand("eject", CmdArgCtx(args.toVector, s))

    def embossdata(args: Any*) =
      SimpleCommand("embossdata", CmdArgCtx(args.toVector, s))

    def embossupdate(args: Any*) =
      SimpleCommand("embossupdate", CmdArgCtx(args.toVector, s))

    def embossversion(args: Any*) =
      SimpleCommand("embossversion", CmdArgCtx(args.toVector, s))

    def emma(args: Any*) =
      SimpleCommand("emma", CmdArgCtx(args.toVector, s))

    def emowse(args: Any*) =
      SimpleCommand("emowse", CmdArgCtx(args.toVector, s))

    def enable(args: Any*) =
      SimpleCommand("enable", CmdArgCtx(args.toVector, s))

    def enc2xs(args: Any*) =
      SimpleCommand("enc2xs", CmdArgCtx(args.toVector, s))

    def encguess(args: Any*) =
      SimpleCommand("encguess", CmdArgCtx(args.toVector, s))

    def entret(args: Any*) =
      SimpleCommand("entret", CmdArgCtx(args.toVector, s))

    def env(args: Any*) =
      SimpleCommand("env", CmdArgCtx(args.toVector, s))

    def epestfind(args: Any*) =
      SimpleCommand("epestfind", CmdArgCtx(args.toVector, s))

    def eprimer3(args: Any*) =
      SimpleCommand("eprimer3", CmdArgCtx(args.toVector, s))

    def eprimer32(args: Any*) =
      SimpleCommand("eprimer32", CmdArgCtx(args.toVector, s))

    def eps2eps(args: Any*) =
      SimpleCommand("eps2eps", CmdArgCtx(args.toVector, s))

    def equicktandem(args: Any*) =
      SimpleCommand("equicktandem", CmdArgCtx(args.toVector, s))

    def esdcompat(args: Any*) =
      SimpleCommand("esdcompat", CmdArgCtx(args.toVector, s))

    def est2genome(args: Any*) =
      SimpleCommand("est2genome", CmdArgCtx(args.toVector, s))

    def etandem(args: Any*) =
      SimpleCommand("etandem", CmdArgCtx(args.toVector, s))

    def expand(args: Any*) =
      SimpleCommand("expand", CmdArgCtx(args.toVector, s))

    def expandCols(args: Any*) =
      SimpleCommand("expandCols", CmdArgCtx(args.toVector, s))

    def expiry(args: Any*) =
      SimpleCommand("expiry", CmdArgCtx(args.toVector, s))

    def expr(args: Any*) =
      SimpleCommand("expr", CmdArgCtx(args.toVector, s))

    def extractalign(args: Any*) =
      SimpleCommand("extractalign", CmdArgCtx(args.toVector, s))

    def extractfeat(args: Any*) =
      SimpleCommand("extractfeat", CmdArgCtx(args.toVector, s))

    def extractseq(args: Any*) =
      SimpleCommand("extractseq", CmdArgCtx(args.toVector, s))

    def factor(args: Any*) =
      SimpleCommand("factor", CmdArgCtx(args.toVector, s))

    def faillog(args: Any*) =
      SimpleCommand("faillog", CmdArgCtx(args.toVector, s))

    def fallocate(args: Any*) =
      SimpleCommand("fallocate", CmdArgCtx(args.toVector, s))

    def fastaFromBed(args: Any*) =
      SimpleCommand("fastaFromBed", CmdArgCtx(args.toVector, s))

    def fatlabel(args: Any*) =
      SimpleCommand("fatlabel", CmdArgCtx(args.toVector, s))

    def fc_cache(args: Any*) =
      SimpleCommand("fc_cache", CmdArgCtx(args.toVector, s))

    def fc_cat(args: Any*) =
      SimpleCommand("fc_cat", CmdArgCtx(args.toVector, s))

    def fc_list(args: Any*) =
      SimpleCommand("fc_list", CmdArgCtx(args.toVector, s))

    def fc_match(args: Any*) =
      SimpleCommand("fc_match", CmdArgCtx(args.toVector, s))

    def fc_pattern(args: Any*) =
      SimpleCommand("fc_pattern", CmdArgCtx(args.toVector, s))

    def fc_query(args: Any*) =
      SimpleCommand("fc_query", CmdArgCtx(args.toVector, s))

    def fc_scan(args: Any*) =
      SimpleCommand("fc_scan", CmdArgCtx(args.toVector, s))

    def fc_validate(args: Any*) =
      SimpleCommand("fc_validate", CmdArgCtx(args.toVector, s))

    def fdformat(args: Any*) =
      SimpleCommand("fdformat", CmdArgCtx(args.toVector, s))

    def fdisk(args: Any*) =
      SimpleCommand("fdisk", CmdArgCtx(args.toVector, s))

    def fdp(args: Any*) =
      SimpleCommand("fdp", CmdArgCtx(args.toVector, s))

    def featcopy(args: Any*) =
      SimpleCommand("featcopy", CmdArgCtx(args.toVector, s))

    def featmerge(args: Any*) =
      SimpleCommand("featmerge", CmdArgCtx(args.toVector, s))

    def featreport(args: Any*) =
      SimpleCommand("featreport", CmdArgCtx(args.toVector, s))

    def feattext(args: Any*) =
      SimpleCommand("feattext", CmdArgCtx(args.toVector, s))

    def fgconsole(args: Any*) =
      SimpleCommand("fgconsole", CmdArgCtx(args.toVector, s))

    def fgrep(args: Any*) =
      SimpleCommand("fgrep", CmdArgCtx(args.toVector, s))

    def file_roller(args: Any*) =
      SimpleCommand("file_roller", CmdArgCtx(args.toVector, s))

    def filefrag(args: Any*) =
      SimpleCommand("filefrag", CmdArgCtx(args.toVector, s))

    def filezilla(args: Any*) =
      SimpleCommand("filezilla", CmdArgCtx(args.toVector, s))

    def fill_aa(args: Any*) =
      SimpleCommand("fill_aa", CmdArgCtx(args.toVector, s))

    def fill_an_ac(args: Any*) =
      SimpleCommand("fill_an_ac", CmdArgCtx(args.toVector, s))

    def fill_fs(args: Any*) =
      SimpleCommand("fill_fs", CmdArgCtx(args.toVector, s))

    def fill_ref_md5(args: Any*) =
      SimpleCommand("fill_ref_md5", CmdArgCtx(args.toVector, s))

    def fincore(args: Any*) =
      SimpleCommand("fincore", CmdArgCtx(args.toVector, s))

    def find(args: Any*) =
      SimpleCommand("find", CmdArgCtx(args.toVector, s))

    def findfs(args: Any*) =
      SimpleCommand("findfs", CmdArgCtx(args.toVector, s))

    def findkm(args: Any*) =
      SimpleCommand("findkm", CmdArgCtx(args.toVector, s))

    def findmnt(args: Any*) =
      SimpleCommand("findmnt", CmdArgCtx(args.toVector, s))

    def firefox(args: Any*) =
      SimpleCommand("firefox", CmdArgCtx(args.toVector, s))

    def flankBed(args: Any*) =
      SimpleCommand("flankBed", CmdArgCtx(args.toVector, s))

    def flock(args: Any*) =
      SimpleCommand("flock", CmdArgCtx(args.toVector, s))

    def fmt(args: Any*) =
      SimpleCommand("fmt", CmdArgCtx(args.toVector, s))

    def fold(args: Any*) =
      SimpleCommand("fold", CmdArgCtx(args.toVector, s))

    def freak(args: Any*) =
      SimpleCommand("freak", CmdArgCtx(args.toVector, s))

    def free(args: Any*) =
      SimpleCommand("free", CmdArgCtx(args.toVector, s))

    def fsadm(args: Any*) =
      SimpleCommand("fsadm", CmdArgCtx(args.toVector, s))

    def fsck(args: Any*) =
      SimpleCommand("fsck", CmdArgCtx(args.toVector, s))

    def fsfreeze(args: Any*) =
      SimpleCommand("fsfreeze", CmdArgCtx(args.toVector, s))

    def fstrim(args: Any*) =
      SimpleCommand("fstrim", CmdArgCtx(args.toVector, s))

    def funzip(args: Any*) =
      SimpleCommand("funzip", CmdArgCtx(args.toVector, s))

    def fusermount(args: Any*) =
      SimpleCommand("fusermount", CmdArgCtx(args.toVector, s))

    def fusermount3(args: Any*) =
      SimpleCommand("fusermount3", CmdArgCtx(args.toVector, s))

    def fuzznuc(args: Any*) =
      SimpleCommand("fuzznuc", CmdArgCtx(args.toVector, s))

    def fuzzpro(args: Any*) =
      SimpleCommand("fuzzpro", CmdArgCtx(args.toVector, s))

    def fuzztran(args: Any*) =
      SimpleCommand("fuzztran", CmdArgCtx(args.toVector, s))

    def fzf(args: Any*) =
      SimpleCommand("fzf", CmdArgCtx(args.toVector, s))

    def fzf_share(args: Any*) =
      SimpleCommand("fzf_share", CmdArgCtx(args.toVector, s))

    def fzf_tmux(args: Any*) =
      SimpleCommand("fzf_tmux", CmdArgCtx(args.toVector, s))

    def fzputtygen(args: Any*) =
      SimpleCommand("fzputtygen", CmdArgCtx(args.toVector, s))

    def fzsftp(args: Any*) =
      SimpleCommand("fzsftp", CmdArgCtx(args.toVector, s))

    def garnier(args: Any*) =
      SimpleCommand("garnier", CmdArgCtx(args.toVector, s))

    def gawk(args: Any*) =
      SimpleCommand("gawk", CmdArgCtx(args.toVector, s))

    def gc(args: Any*) =
      SimpleCommand("gc", CmdArgCtx(args.toVector, s))

    def gcc(args: Any*) =
      SimpleCommand("gcc", CmdArgCtx(args.toVector, s))

    def gcloud(args: Any*) =
      SimpleCommand("gcloud", CmdArgCtx(args.toVector, s))

    def geecee(args: Any*) =
      SimpleCommand("geecee", CmdArgCtx(args.toVector, s))

    def gencat(args: Any*) =
      SimpleCommand("gencat", CmdArgCtx(args.toVector, s))

    def genl(args: Any*) =
      SimpleCommand("genl", CmdArgCtx(args.toVector, s))

    def genomeCoverageBed(args: Any*) =
      SimpleCommand("genomeCoverageBed", CmdArgCtx(args.toVector, s))

    def gentrigrams(args: Any*) =
      SimpleCommand("gentrigrams", CmdArgCtx(args.toVector, s))

    def getOverlap(args: Any*) =
      SimpleCommand("getOverlap", CmdArgCtx(args.toVector, s))

    def getcap(args: Any*) =
      SimpleCommand("getcap", CmdArgCtx(args.toVector, s))

    def getconf(args: Any*) =
      SimpleCommand("getconf", CmdArgCtx(args.toVector, s))

    def getent(args: Any*) =
      SimpleCommand("getent", CmdArgCtx(args.toVector, s))

    def getfacl(args: Any*) =
      SimpleCommand("getfacl", CmdArgCtx(args.toVector, s))

    def getfattr(args: Any*) =
      SimpleCommand("getfattr", CmdArgCtx(args.toVector, s))

    def getkeycodes(args: Any*) =
      SimpleCommand("getkeycodes", CmdArgCtx(args.toVector, s))

    def getopt(args: Any*) =
      SimpleCommand("getopt", CmdArgCtx(args.toVector, s))

    def getorf(args: Any*) =
      SimpleCommand("getorf", CmdArgCtx(args.toVector, s))

    def getpcaps(args: Any*) =
      SimpleCommand("getpcaps", CmdArgCtx(args.toVector, s))

    def getunimap(args: Any*) =
      SimpleCommand("getunimap", CmdArgCtx(args.toVector, s))

    def git(args: Any*) =
      SimpleCommand("git", CmdArgCtx(args.toVector, s))

    def git_credential_netrc(args: Any*) =
      SimpleCommand("git_credential_netrc", CmdArgCtx(args.toVector, s))

    def git_cvsserver(args: Any*) =
      SimpleCommand("git_cvsserver", CmdArgCtx(args.toVector, s))

    def git_http_backend(args: Any*) =
      SimpleCommand("git_http_backend", CmdArgCtx(args.toVector, s))

    def git_lfs(args: Any*) =
      SimpleCommand("git_lfs", CmdArgCtx(args.toVector, s))

    def git_lfs_test_server_api(args: Any*) =
      SimpleCommand("git_lfs_test_server_api", CmdArgCtx(args.toVector, s))

    def git_receive_pack(args: Any*) =
      SimpleCommand("git_receive_pack", CmdArgCtx(args.toVector, s))

    def git_shell(args: Any*) =
      SimpleCommand("git_shell", CmdArgCtx(args.toVector, s))

    def git_upload_archive(args: Any*) =
      SimpleCommand("git_upload_archive", CmdArgCtx(args.toVector, s))

    def git_upload_pack(args: Any*) =
      SimpleCommand("git_upload_pack", CmdArgCtx(args.toVector, s))

    def gmenudbusmenuproxy(args: Any*) =
      SimpleCommand("gmenudbusmenuproxy", CmdArgCtx(args.toVector, s))

    def gml2gv(args: Any*) =
      SimpleCommand("gml2gv", CmdArgCtx(args.toVector, s))

    def gmplayer(args: Any*) =
      SimpleCommand("gmplayer", CmdArgCtx(args.toVector, s))

    def godef(args: Any*) =
      SimpleCommand("godef", CmdArgCtx(args.toVector, s))

    def goname(args: Any*) =
      SimpleCommand("goname", CmdArgCtx(args.toVector, s))

    def google_chrome_stable(args: Any*) =
      SimpleCommand("google_chrome_stable", CmdArgCtx(args.toVector, s))

    def gparted(args: Any*) =
      SimpleCommand("gparted", CmdArgCtx(args.toVector, s))

    def gpartedbin(args: Any*) =
      SimpleCommand("gpartedbin", CmdArgCtx(args.toVector, s))

    def gpasswd(args: Any*) =
      SimpleCommand("gpasswd", CmdArgCtx(args.toVector, s))

    def gpg(args: Any*) =
      SimpleCommand("gpg", CmdArgCtx(args.toVector, s))

    def gpg2(args: Any*) =
      SimpleCommand("gpg2", CmdArgCtx(args.toVector, s))

    def gpg_agent(args: Any*) =
      SimpleCommand("gpg_agent", CmdArgCtx(args.toVector, s))

    def gpg_connect_agent(args: Any*) =
      SimpleCommand("gpg_connect_agent", CmdArgCtx(args.toVector, s))

    def gpg_wks_server(args: Any*) =
      SimpleCommand("gpg_wks_server", CmdArgCtx(args.toVector, s))

    def gpgconf(args: Any*) =
      SimpleCommand("gpgconf", CmdArgCtx(args.toVector, s))

    def gpgparsemail(args: Any*) =
      SimpleCommand("gpgparsemail", CmdArgCtx(args.toVector, s))

    def gpgscm(args: Any*) =
      SimpleCommand("gpgscm", CmdArgCtx(args.toVector, s))

    def gpgsm(args: Any*) =
      SimpleCommand("gpgsm", CmdArgCtx(args.toVector, s))

    def gpgtar(args: Any*) =
      SimpleCommand("gpgtar", CmdArgCtx(args.toVector, s))

    def gpgv(args: Any*) =
      SimpleCommand("gpgv", CmdArgCtx(args.toVector, s))

    def gradle(args: Any*) =
      SimpleCommand("gradle", CmdArgCtx(args.toVector, s))

    def graphml2gv(args: Any*) =
      SimpleCommand("graphml2gv", CmdArgCtx(args.toVector, s))

    def grep(args: Any*) =
      SimpleCommand("grep", CmdArgCtx(args.toVector, s))

    def groupBy(args: Any*) =
      SimpleCommand("groupBy", CmdArgCtx(args.toVector, s))

    def groupadd(args: Any*) =
      SimpleCommand("groupadd", CmdArgCtx(args.toVector, s))

    def groupdel(args: Any*) =
      SimpleCommand("groupdel", CmdArgCtx(args.toVector, s))

    def groupmems(args: Any*) =
      SimpleCommand("groupmems", CmdArgCtx(args.toVector, s))

    def groupmod(args: Any*) =
      SimpleCommand("groupmod", CmdArgCtx(args.toVector, s))

    def groups(args: Any*) =
      SimpleCommand("groups", CmdArgCtx(args.toVector, s))

    def grpck(args: Any*) =
      SimpleCommand("grpck", CmdArgCtx(args.toVector, s))

    def grpconv(args: Any*) =
      SimpleCommand("grpconv", CmdArgCtx(args.toVector, s))

    def grpunconv(args: Any*) =
      SimpleCommand("grpunconv", CmdArgCtx(args.toVector, s))

    def gs(args: Any*) =
      SimpleCommand("gs", CmdArgCtx(args.toVector, s))

    def gsbj(args: Any*) =
      SimpleCommand("gsbj", CmdArgCtx(args.toVector, s))

    def gsc(args: Any*) =
      SimpleCommand("gsc", CmdArgCtx(args.toVector, s))

    def gsdj(args: Any*) =
      SimpleCommand("gsdj", CmdArgCtx(args.toVector, s))

    def gsdj500(args: Any*) =
      SimpleCommand("gsdj500", CmdArgCtx(args.toVector, s))

    def gslj(args: Any*) =
      SimpleCommand("gslj", CmdArgCtx(args.toVector, s))

    def gslp(args: Any*) =
      SimpleCommand("gslp", CmdArgCtx(args.toVector, s))

    def gsnd(args: Any*) =
      SimpleCommand("gsnd", CmdArgCtx(args.toVector, s))

    def gss_client(args: Any*) =
      SimpleCommand("gss_client", CmdArgCtx(args.toVector, s))

    def gss_server(args: Any*) =
      SimpleCommand("gss_server", CmdArgCtx(args.toVector, s))

    def gsutil(args: Any*) =
      SimpleCommand("gsutil", CmdArgCtx(args.toVector, s))

    def gsx(args: Any*) =
      SimpleCommand("gsx", CmdArgCtx(args.toVector, s))

    def gtf(args: Any*) =
      SimpleCommand("gtf", CmdArgCtx(args.toVector, s))

    def gunzip(args: Any*) =
      SimpleCommand("gunzip", CmdArgCtx(args.toVector, s))

    def gv2gml(args: Any*) =
      SimpleCommand("gv2gml", CmdArgCtx(args.toVector, s))

    def gv2gxl(args: Any*) =
      SimpleCommand("gv2gxl", CmdArgCtx(args.toVector, s))

    def gvcolor(args: Any*) =
      SimpleCommand("gvcolor", CmdArgCtx(args.toVector, s))

    def gvgen(args: Any*) =
      SimpleCommand("gvgen", CmdArgCtx(args.toVector, s))

    def gvmap(args: Any*) =
      SimpleCommand("gvmap", CmdArgCtx(args.toVector, s))

    def gvpack(args: Any*) =
      SimpleCommand("gvpack", CmdArgCtx(args.toVector, s))

    def gvpr(args: Any*) =
      SimpleCommand("gvpr", CmdArgCtx(args.toVector, s))

    def gxl2dot(args: Any*) =
      SimpleCommand("gxl2dot", CmdArgCtx(args.toVector, s))

    def gxl2gv(args: Any*) =
      SimpleCommand("gxl2gv", CmdArgCtx(args.toVector, s))

    def gzexe(args: Any*) =
      SimpleCommand("gzexe", CmdArgCtx(args.toVector, s))

    def gzip(args: Any*) =
      SimpleCommand("gzip", CmdArgCtx(args.toVector, s))

    def gzrecover(args: Any*) =
      SimpleCommand("gzrecover", CmdArgCtx(args.toVector, s))

    def h2ph(args: Any*) =
      SimpleCommand("h2ph", CmdArgCtx(args.toVector, s))

    def h2xs(args: Any*) =
      SimpleCommand("h2xs", CmdArgCtx(args.toVector, s))

    def halt(args: Any*) =
      SimpleCommand("halt", CmdArgCtx(args.toVector, s))

    def head(args: Any*) =
      SimpleCommand("head", CmdArgCtx(args.toVector, s))

    def helixturnhelix(args: Any*) =
      SimpleCommand("helixturnhelix", CmdArgCtx(args.toVector, s))

    def help(args: Any*) =
      SimpleCommand("help", CmdArgCtx(args.toVector, s))

    def hexdump(args: Any*) =
      SimpleCommand("hexdump", CmdArgCtx(args.toVector, s))

    def hg(args: Any*) =
      SimpleCommand("hg", CmdArgCtx(args.toVector, s))

    def hmmalign(args: Any*) =
      SimpleCommand("hmmalign", CmdArgCtx(args.toVector, s))

    def hmmbuild(args: Any*) =
      SimpleCommand("hmmbuild", CmdArgCtx(args.toVector, s))

    def hmmconvert(args: Any*) =
      SimpleCommand("hmmconvert", CmdArgCtx(args.toVector, s))

    def hmmemit(args: Any*) =
      SimpleCommand("hmmemit", CmdArgCtx(args.toVector, s))

    def hmmfetch(args: Any*) =
      SimpleCommand("hmmfetch", CmdArgCtx(args.toVector, s))

    def hmmlogo(args: Any*) =
      SimpleCommand("hmmlogo", CmdArgCtx(args.toVector, s))

    def hmmpgmd(args: Any*) =
      SimpleCommand("hmmpgmd", CmdArgCtx(args.toVector, s))

    def hmmpress(args: Any*) =
      SimpleCommand("hmmpress", CmdArgCtx(args.toVector, s))

    def hmmscan(args: Any*) =
      SimpleCommand("hmmscan", CmdArgCtx(args.toVector, s))

    def hmmsearch(args: Any*) =
      SimpleCommand("hmmsearch", CmdArgCtx(args.toVector, s))

    def hmmsim(args: Any*) =
      SimpleCommand("hmmsim", CmdArgCtx(args.toVector, s))

    def hmmstat(args: Any*) =
      SimpleCommand("hmmstat", CmdArgCtx(args.toVector, s))

    def hmoment(args: Any*) =
      SimpleCommand("hmoment", CmdArgCtx(args.toVector, s))

    def host(args: Any*) =
      SimpleCommand("host", CmdArgCtx(args.toVector, s))

    def hostid(args: Any*) =
      SimpleCommand("hostid", CmdArgCtx(args.toVector, s))

    def hostname(args: Any*) =
      SimpleCommand("hostname", CmdArgCtx(args.toVector, s))

    def hostnamectl(args: Any*) =
      SimpleCommand("hostnamectl", CmdArgCtx(args.toVector, s))

    def http(args: Any*) =
      SimpleCommand("http", CmdArgCtx(args.toVector, s))

    def hwclock(args: Any*) =
      SimpleCommand("hwclock", CmdArgCtx(args.toVector, s))

    def i386(args: Any*) =
      SimpleCommand("i386", CmdArgCtx(args.toVector, s))

    def iceauth(args: Any*) =
      SimpleCommand("iceauth", CmdArgCtx(args.toVector, s))

    def iconv(args: Any*) =
      SimpleCommand("iconv", CmdArgCtx(args.toVector, s))

    def iconvconfig(args: Any*) =
      SimpleCommand("iconvconfig", CmdArgCtx(args.toVector, s))

    def id(args: Any*) =
      SimpleCommand("id", CmdArgCtx(args.toVector, s))

    def idea_ultimate(args: Any*) =
      SimpleCommand("idea_ultimate", CmdArgCtx(args.toVector, s))

    def identify(args: Any*) =
      SimpleCommand("identify", CmdArgCtx(args.toVector, s))

    def iecset(args: Any*) =
      SimpleCommand("iecset", CmdArgCtx(args.toVector, s))

    def iep(args: Any*) =
      SimpleCommand("iep", CmdArgCtx(args.toVector, s))

    def ifcfg(args: Any*) =
      SimpleCommand("ifcfg", CmdArgCtx(args.toVector, s))

    def ifconfig(args: Any*) =
      SimpleCommand("ifconfig", CmdArgCtx(args.toVector, s))

    def ifstat(args: Any*) =
      SimpleCommand("ifstat", CmdArgCtx(args.toVector, s))

    def igv(args: Any*) =
      SimpleCommand("igv", CmdArgCtx(args.toVector, s))

    def info(args: Any*) =
      SimpleCommand("info", CmdArgCtx(args.toVector, s))

    def infoalign(args: Any*) =
      SimpleCommand("infoalign", CmdArgCtx(args.toVector, s))

    def infoassembly(args: Any*) =
      SimpleCommand("infoassembly", CmdArgCtx(args.toVector, s))

    def infobase(args: Any*) =
      SimpleCommand("infobase", CmdArgCtx(args.toVector, s))

    def inforesidue(args: Any*) =
      SimpleCommand("inforesidue", CmdArgCtx(args.toVector, s))

    def infoseq(args: Any*) =
      SimpleCommand("infoseq", CmdArgCtx(args.toVector, s))

    def infotocap(args: Any*) =
      SimpleCommand("infotocap", CmdArgCtx(args.toVector, s))

    def init(args: Any*) =
      SimpleCommand("init", CmdArgCtx(args.toVector, s))

    def insmod(args: Any*) =
      SimpleCommand("insmod", CmdArgCtx(args.toVector, s))

    def install(args: Any*) =
      SimpleCommand("install", CmdArgCtx(args.toVector, s))

    def install_info(args: Any*) =
      SimpleCommand("install_info", CmdArgCtx(args.toVector, s))

    def install_printerdriver(args: Any*) =
      SimpleCommand("install_printerdriver", CmdArgCtx(args.toVector, s))

    def instmodsh(args: Any*) =
      SimpleCommand("instmodsh", CmdArgCtx(args.toVector, s))

    def integritysetup(args: Any*) =
      SimpleCommand("integritysetup", CmdArgCtx(args.toVector, s))

    def intersectBed(args: Any*) =
      SimpleCommand("intersectBed", CmdArgCtx(args.toVector, s))

    def ionice(args: Any*) =
      SimpleCommand("ionice", CmdArgCtx(args.toVector, s))

    def ip(args: Any*) =
      SimpleCommand("ip", CmdArgCtx(args.toVector, s))

    def ip6tables(args: Any*) =
      SimpleCommand("ip6tables", CmdArgCtx(args.toVector, s))

    def ip6tables_legacy(args: Any*) =
      SimpleCommand("ip6tables_legacy", CmdArgCtx(args.toVector, s))

    def ip6tables_legacy_restore(args: Any*) =
      SimpleCommand("ip6tables_legacy_restore", CmdArgCtx(args.toVector, s))

    def ip6tables_legacy_save(args: Any*) =
      SimpleCommand("ip6tables_legacy_save", CmdArgCtx(args.toVector, s))

    def ip6tables_nft(args: Any*) =
      SimpleCommand("ip6tables_nft", CmdArgCtx(args.toVector, s))

    def ip6tables_nft_restore(args: Any*) =
      SimpleCommand("ip6tables_nft_restore", CmdArgCtx(args.toVector, s))

    def ip6tables_nft_save(args: Any*) =
      SimpleCommand("ip6tables_nft_save", CmdArgCtx(args.toVector, s))

    def ip6tables_restore(args: Any*) =
      SimpleCommand("ip6tables_restore", CmdArgCtx(args.toVector, s))

    def ip6tables_restore_translate(args: Any*) =
      SimpleCommand("ip6tables_restore_translate", CmdArgCtx(args.toVector, s))

    def ip6tables_save(args: Any*) =
      SimpleCommand("ip6tables_save", CmdArgCtx(args.toVector, s))

    def ip6tables_translate(args: Any*) =
      SimpleCommand("ip6tables_translate", CmdArgCtx(args.toVector, s))

    def ipcmk(args: Any*) =
      SimpleCommand("ipcmk", CmdArgCtx(args.toVector, s))

    def ipcrm(args: Any*) =
      SimpleCommand("ipcrm", CmdArgCtx(args.toVector, s))

    def ipcs(args: Any*) =
      SimpleCommand("ipcs", CmdArgCtx(args.toVector, s))

    def ippfind(args: Any*) =
      SimpleCommand("ippfind", CmdArgCtx(args.toVector, s))

    def ipptool(args: Any*) =
      SimpleCommand("ipptool", CmdArgCtx(args.toVector, s))

    def iptables(args: Any*) =
      SimpleCommand("iptables", CmdArgCtx(args.toVector, s))

    def iptables_legacy(args: Any*) =
      SimpleCommand("iptables_legacy", CmdArgCtx(args.toVector, s))

    def iptables_legacy_restore(args: Any*) =
      SimpleCommand("iptables_legacy_restore", CmdArgCtx(args.toVector, s))

    def iptables_legacy_save(args: Any*) =
      SimpleCommand("iptables_legacy_save", CmdArgCtx(args.toVector, s))

    def iptables_nft(args: Any*) =
      SimpleCommand("iptables_nft", CmdArgCtx(args.toVector, s))

    def iptables_nft_restore(args: Any*) =
      SimpleCommand("iptables_nft_restore", CmdArgCtx(args.toVector, s))

    def iptables_nft_save(args: Any*) =
      SimpleCommand("iptables_nft_save", CmdArgCtx(args.toVector, s))

    def iptables_restore(args: Any*) =
      SimpleCommand("iptables_restore", CmdArgCtx(args.toVector, s))

    def iptables_restore_translate(args: Any*) =
      SimpleCommand("iptables_restore_translate", CmdArgCtx(args.toVector, s))

    def iptables_save(args: Any*) =
      SimpleCommand("iptables_save", CmdArgCtx(args.toVector, s))

    def iptables_translate(args: Any*) =
      SimpleCommand("iptables_translate", CmdArgCtx(args.toVector, s))

    def iptables_xml(args: Any*) =
      SimpleCommand("iptables_xml", CmdArgCtx(args.toVector, s))

    def isochore(args: Any*) =
      SimpleCommand("isochore", CmdArgCtx(args.toVector, s))

    def isosize(args: Any*) =
      SimpleCommand("isosize", CmdArgCtx(args.toVector, s))

    def jackhmmer(args: Any*) =
      SimpleCommand("jackhmmer", CmdArgCtx(args.toVector, s))

    def jaspextract(args: Any*) =
      SimpleCommand("jaspextract", CmdArgCtx(args.toVector, s))

    def jaspscan(args: Any*) =
      SimpleCommand("jaspscan", CmdArgCtx(args.toVector, s))

    def jembossctl(args: Any*) =
      SimpleCommand("jembossctl", CmdArgCtx(args.toVector, s))

    def jigdo_file(args: Any*) =
      SimpleCommand("jigdo_file", CmdArgCtx(args.toVector, s))

    def jigdo_lite(args: Any*) =
      SimpleCommand("jigdo_lite", CmdArgCtx(args.toVector, s))

    def jigdo_mirror(args: Any*) =
      SimpleCommand("jigdo_mirror", CmdArgCtx(args.toVector, s))

    def jmtpfs(args: Any*) =
      SimpleCommand("jmtpfs", CmdArgCtx(args.toVector, s))

    def join(args: Any*) =
      SimpleCommand("join", CmdArgCtx(args.toVector, s))

    def journalctl(args: Any*) =
      SimpleCommand("journalctl", CmdArgCtx(args.toVector, s))

    def jq(args: Any*) =
      SimpleCommand("jq", CmdArgCtx(args.toVector, s))

    def json_pp(args: Any*) =
      SimpleCommand("json_pp", CmdArgCtx(args.toVector, s))

    def jupyter_bundlerextension(args: Any*) =
      SimpleCommand("jupyter_bundlerextension", CmdArgCtx(args.toVector, s))

    def jupyter_nbextension(args: Any*) =
      SimpleCommand("jupyter_nbextension", CmdArgCtx(args.toVector, s))

    def jupyter_notebook(args: Any*) =
      SimpleCommand("jupyter_notebook", CmdArgCtx(args.toVector, s))

    def jupyter_serverextension(args: Any*) =
      SimpleCommand("jupyter_serverextension", CmdArgCtx(args.toVector, s))

    def k5srvutil(args: Any*) =
      SimpleCommand("k5srvutil", CmdArgCtx(args.toVector, s))

    def kaccess(args: Any*) =
      SimpleCommand("kaccess", CmdArgCtx(args.toVector, s))

    def kactivities_cli(args: Any*) =
      SimpleCommand("kactivities_cli", CmdArgCtx(args.toVector, s))

    def kadmin(args: Any*) =
      SimpleCommand("kadmin", CmdArgCtx(args.toVector, s))

    def kadmind(args: Any*) =
      SimpleCommand("kadmind", CmdArgCtx(args.toVector, s))

    def kallisto(args: Any*) =
      SimpleCommand("kallisto", CmdArgCtx(args.toVector, s))

    def kapplymousetheme(args: Any*) =
      SimpleCommand("kapplymousetheme", CmdArgCtx(args.toVector, s))

    def kate(args: Any*) =
      SimpleCommand("kate", CmdArgCtx(args.toVector, s))

    def kbd_mode(args: Any*) =
      SimpleCommand("kbd_mode", CmdArgCtx(args.toVector, s))

    def kbdinfo(args: Any*) =
      SimpleCommand("kbdinfo", CmdArgCtx(args.toVector, s))

    def kbdrate(args: Any*) =
      SimpleCommand("kbdrate", CmdArgCtx(args.toVector, s))

    def kbroadcastnotification(args: Any*) =
      SimpleCommand("kbroadcastnotification", CmdArgCtx(args.toVector, s))

    def kbuildsycoca5(args: Any*) =
      SimpleCommand("kbuildsycoca5", CmdArgCtx(args.toVector, s))

    def kbxutil(args: Any*) =
      SimpleCommand("kbxutil", CmdArgCtx(args.toVector, s))

    def kcheckrunning(args: Any*) =
      SimpleCommand("kcheckrunning", CmdArgCtx(args.toVector, s))

    def kcm_touchpad_list_devices(args: Any*) =
      SimpleCommand("kcm_touchpad_list_devices", CmdArgCtx(args.toVector, s))

    def kcminit(args: Any*) =
      SimpleCommand("kcminit", CmdArgCtx(args.toVector, s))

    def kcminit_startup(args: Any*) =
      SimpleCommand("kcminit_startup", CmdArgCtx(args.toVector, s))

    def kcmshell5(args: Any*) =
      SimpleCommand("kcmshell5", CmdArgCtx(args.toVector, s))

    def kcolorschemeeditor(args: Any*) =
      SimpleCommand("kcolorschemeeditor", CmdArgCtx(args.toVector, s))

    def kcookiejar5(args: Any*) =
      SimpleCommand("kcookiejar5", CmdArgCtx(args.toVector, s))

    def kdb5_util(args: Any*) =
      SimpleCommand("kdb5_util", CmdArgCtx(args.toVector, s))

    def kde_add_printer(args: Any*) =
      SimpleCommand("kde_add_printer", CmdArgCtx(args.toVector, s))

    def kde_open5(args: Any*) =
      SimpleCommand("kde_open5", CmdArgCtx(args.toVector, s))

    def kde_print_queue(args: Any*) =
      SimpleCommand("kde_print_queue", CmdArgCtx(args.toVector, s))

    def kdecp5(args: Any*) =
      SimpleCommand("kdecp5", CmdArgCtx(args.toVector, s))

    def kded5(args: Any*) =
      SimpleCommand("kded5", CmdArgCtx(args.toVector, s))

    def kdeinit5(args: Any*) =
      SimpleCommand("kdeinit5", CmdArgCtx(args.toVector, s))

    def kdeinit5_shutdown(args: Any*) =
      SimpleCommand("kdeinit5_shutdown", CmdArgCtx(args.toVector, s))

    def kdeinit5_wrapper(args: Any*) =
      SimpleCommand("kdeinit5_wrapper", CmdArgCtx(args.toVector, s))

    def kdemv5(args: Any*) =
      SimpleCommand("kdemv5", CmdArgCtx(args.toVector, s))

    def kdestroy(args: Any*) =
      SimpleCommand("kdestroy", CmdArgCtx(args.toVector, s))

    def kdostartupconfig5(args: Any*) =
      SimpleCommand("kdostartupconfig5", CmdArgCtx(args.toVector, s))

    def keditfiletype5(args: Any*) =
      SimpleCommand("keditfiletype5", CmdArgCtx(args.toVector, s))

    def kexec(args: Any*) =
      SimpleCommand("kexec", CmdArgCtx(args.toVector, s))

    def kfontinst(args: Any*) =
      SimpleCommand("kfontinst", CmdArgCtx(args.toVector, s))

    def kfontview(args: Any*) =
      SimpleCommand("kfontview", CmdArgCtx(args.toVector, s))

    def kglobalaccel5(args: Any*) =
      SimpleCommand("kglobalaccel5", CmdArgCtx(args.toVector, s))

    def khelpcenter(args: Any*) =
      SimpleCommand("khelpcenter", CmdArgCtx(args.toVector, s))

    def kiconfinder5(args: Any*) =
      SimpleCommand("kiconfinder5", CmdArgCtx(args.toVector, s))

    def kill(args: Any*) =
      SimpleCommand("kill", CmdArgCtx(args.toVector, s))

    def kinfocenter(args: Any*) =
      SimpleCommand("kinfocenter", CmdArgCtx(args.toVector, s))

    def kinit(args: Any*) =
      SimpleCommand("kinit", CmdArgCtx(args.toVector, s))

    def kioclient5(args: Any*) =
      SimpleCommand("kioclient5", CmdArgCtx(args.toVector, s))

    def klipper(args: Any*) =
      SimpleCommand("klipper", CmdArgCtx(args.toVector, s))

    def klist(args: Any*) =
      SimpleCommand("klist", CmdArgCtx(args.toVector, s))

    def kmenuedit(args: Any*) =
      SimpleCommand("kmenuedit", CmdArgCtx(args.toVector, s))

    def kmimetypefinder5(args: Any*) =
      SimpleCommand("kmimetypefinder5", CmdArgCtx(args.toVector, s))

    def kmod(args: Any*) =
      SimpleCommand("kmod", CmdArgCtx(args.toVector, s))

    def knetattach(args: Any*) =
      SimpleCommand("knetattach", CmdArgCtx(args.toVector, s))

    def koi8rxterm(args: Any*) =
      SimpleCommand("koi8rxterm", CmdArgCtx(args.toVector, s))

    def konsole(args: Any*) =
      SimpleCommand("konsole", CmdArgCtx(args.toVector, s))

    def konsoleprofile(args: Any*) =
      SimpleCommand("konsoleprofile", CmdArgCtx(args.toVector, s))

    def kpackagelauncherqml(args: Any*) =
      SimpleCommand("kpackagelauncherqml", CmdArgCtx(args.toVector, s))

    def kpackagetool5(args: Any*) =
      SimpleCommand("kpackagetool5", CmdArgCtx(args.toVector, s))

    def kpasswd(args: Any*) =
      SimpleCommand("kpasswd", CmdArgCtx(args.toVector, s))

    def kprop(args: Any*) =
      SimpleCommand("kprop", CmdArgCtx(args.toVector, s))

    def kpropd(args: Any*) =
      SimpleCommand("kpropd", CmdArgCtx(args.toVector, s))

    def kproplog(args: Any*) =
      SimpleCommand("kproplog", CmdArgCtx(args.toVector, s))

    def kquitapp5(args: Any*) =
      SimpleCommand("kquitapp5", CmdArgCtx(args.toVector, s))

    def krb5_send_pr(args: Any*) =
      SimpleCommand("krb5_send_pr", CmdArgCtx(args.toVector, s))

    def krb5kdc(args: Any*) =
      SimpleCommand("krb5kdc", CmdArgCtx(args.toVector, s))

    def krdb(args: Any*) =
      SimpleCommand("krdb", CmdArgCtx(args.toVector, s))

    def kreadconfig5(args: Any*) =
      SimpleCommand("kreadconfig5", CmdArgCtx(args.toVector, s))

    def krita(args: Any*) =
      SimpleCommand("krita", CmdArgCtx(args.toVector, s))

    def kritarunner(args: Any*) =
      SimpleCommand("kritarunner", CmdArgCtx(args.toVector, s))

    def krunner(args: Any*) =
      SimpleCommand("krunner", CmdArgCtx(args.toVector, s))

    def kscreen_console(args: Any*) =
      SimpleCommand("kscreen_console", CmdArgCtx(args.toVector, s))

    def kscreen_doctor(args: Any*) =
      SimpleCommand("kscreen_doctor", CmdArgCtx(args.toVector, s))

    def kshell5(args: Any*) =
      SimpleCommand("kshell5", CmdArgCtx(args.toVector, s))

    def ksmserver(args: Any*) =
      SimpleCommand("ksmserver", CmdArgCtx(args.toVector, s))

    def ksplashqml(args: Any*) =
      SimpleCommand("ksplashqml", CmdArgCtx(args.toVector, s))

    def kstart5(args: Any*) =
      SimpleCommand("kstart5", CmdArgCtx(args.toVector, s))

    def kstartupconfig5(args: Any*) =
      SimpleCommand("kstartupconfig5", CmdArgCtx(args.toVector, s))

    def ksu(args: Any*) =
      SimpleCommand("ksu", CmdArgCtx(args.toVector, s))

    def ksvgtopng5(args: Any*) =
      SimpleCommand("ksvgtopng5", CmdArgCtx(args.toVector, s))

    def kswitch(args: Any*) =
      SimpleCommand("kswitch", CmdArgCtx(args.toVector, s))

    def ksysguard(args: Any*) =
      SimpleCommand("ksysguard", CmdArgCtx(args.toVector, s))

    def ksysguardd(args: Any*) =
      SimpleCommand("ksysguardd", CmdArgCtx(args.toVector, s))

    def ktelnetservice5(args: Any*) =
      SimpleCommand("ktelnetservice5", CmdArgCtx(args.toVector, s))

    def ktraderclient5(args: Any*) =
      SimpleCommand("ktraderclient5", CmdArgCtx(args.toVector, s))

    def ktrash5(args: Any*) =
      SimpleCommand("ktrash5", CmdArgCtx(args.toVector, s))

    def ktutil(args: Any*) =
      SimpleCommand("ktutil", CmdArgCtx(args.toVector, s))

    def kvno(args: Any*) =
      SimpleCommand("kvno", CmdArgCtx(args.toVector, s))

    def kwallet_query(args: Any*) =
      SimpleCommand("kwallet_query", CmdArgCtx(args.toVector, s))

    def kwalletd5(args: Any*) =
      SimpleCommand("kwalletd5", CmdArgCtx(args.toVector, s))

    def kwalletmanager5(args: Any*) =
      SimpleCommand("kwalletmanager5", CmdArgCtx(args.toVector, s))

    def kwin_wayland(args: Any*) =
      SimpleCommand("kwin_wayland", CmdArgCtx(args.toVector, s))

    def kwin_x11(args: Any*) =
      SimpleCommand("kwin_x11", CmdArgCtx(args.toVector, s))

    def kwrapper5(args: Any*) =
      SimpleCommand("kwrapper5", CmdArgCtx(args.toVector, s))

    def kwrite(args: Any*) =
      SimpleCommand("kwrite", CmdArgCtx(args.toVector, s))

    def kwriteconfig5(args: Any*) =
      SimpleCommand("kwriteconfig5", CmdArgCtx(args.toVector, s))

    def kwrited(args: Any*) =
      SimpleCommand("kwrited", CmdArgCtx(args.toVector, s))

    def last(args: Any*) =
      SimpleCommand("last", CmdArgCtx(args.toVector, s))

    def lastb(args: Any*) =
      SimpleCommand("lastb", CmdArgCtx(args.toVector, s))

    def lastlog(args: Any*) =
      SimpleCommand("lastlog", CmdArgCtx(args.toVector, s))

    def ld(args: Any*) =
      SimpleCommand("ld", CmdArgCtx(args.toVector, s))

    def ldattach(args: Any*) =
      SimpleCommand("ldattach", CmdArgCtx(args.toVector, s))

    def ldconfig(args: Any*) =
      SimpleCommand("ldconfig", CmdArgCtx(args.toVector, s))

    def ldd(args: Any*) =
      SimpleCommand("ldd", CmdArgCtx(args.toVector, s))

    def lefty(args: Any*) =
      SimpleCommand("lefty", CmdArgCtx(args.toVector, s))

    def less(args: Any*) =
      SimpleCommand("less", CmdArgCtx(args.toVector, s))

    def lessecho(args: Any*) =
      SimpleCommand("lessecho", CmdArgCtx(args.toVector, s))

    def lesskey(args: Any*) =
      SimpleCommand("lesskey", CmdArgCtx(args.toVector, s))

    def let(args: Any*) =
      SimpleCommand("let", CmdArgCtx(args.toVector, s))

    def lexgrog(args: Any*) =
      SimpleCommand("lexgrog", CmdArgCtx(args.toVector, s))

    def libnetcfg(args: Any*) =
      SimpleCommand("libnetcfg", CmdArgCtx(args.toVector, s))

    def libreoffice(args: Any*) =
      SimpleCommand("libreoffice", CmdArgCtx(args.toVector, s))

    def libwmf_config(args: Any*) =
      SimpleCommand("libwmf_config", CmdArgCtx(args.toVector, s))

    def libwmf_fontmap(args: Any*) =
      SimpleCommand("libwmf_fontmap", CmdArgCtx(args.toVector, s))

    def light(args: Any*) =
      SimpleCommand("light", CmdArgCtx(args.toVector, s))

    def lindna(args: Any*) =
      SimpleCommand("lindna", CmdArgCtx(args.toVector, s))

    def link(args: Any*) =
      SimpleCommand("link", CmdArgCtx(args.toVector, s))

    def linksBed(args: Any*) =
      SimpleCommand("linksBed", CmdArgCtx(args.toVector, s))

    def linux32(args: Any*) =
      SimpleCommand("linux32", CmdArgCtx(args.toVector, s))

    def linux64(args: Any*) =
      SimpleCommand("linux64", CmdArgCtx(args.toVector, s))

    def listor(args: Any*) =
      SimpleCommand("listor", CmdArgCtx(args.toVector, s))

    def ln(args: Any*) =
      SimpleCommand("ln", CmdArgCtx(args.toVector, s))

    def lneato(args: Any*) =
      SimpleCommand("lneato", CmdArgCtx(args.toVector, s))

    def lnstat(args: Any*) =
      SimpleCommand("lnstat", CmdArgCtx(args.toVector, s))

    def loadkeys(args: Any*) =
      SimpleCommand("loadkeys", CmdArgCtx(args.toVector, s))

    def loadunimap(args: Any*) =
      SimpleCommand("loadunimap", CmdArgCtx(args.toVector, s))

    def local(args: Any*) =
      SimpleCommand("local", CmdArgCtx(args.toVector, s))

    def locale(args: Any*) =
      SimpleCommand("locale", CmdArgCtx(args.toVector, s))

    def localectl(args: Any*) =
      SimpleCommand("localectl", CmdArgCtx(args.toVector, s))

    def localedef(args: Any*) =
      SimpleCommand("localedef", CmdArgCtx(args.toVector, s))

    def locate(args: Any*) =
      SimpleCommand("locate", CmdArgCtx(args.toVector, s))

    def logger(args: Any*) =
      SimpleCommand("logger", CmdArgCtx(args.toVector, s))

    def login(args: Any*) =
      SimpleCommand("login", CmdArgCtx(args.toVector, s))

    def loginctl(args: Any*) =
      SimpleCommand("loginctl", CmdArgCtx(args.toVector, s))

    def logname(args: Any*) =
      SimpleCommand("logname", CmdArgCtx(args.toVector, s))

    def logout(args: Any*) =
      SimpleCommand("logout", CmdArgCtx(args.toVector, s))

    def logoutd(args: Any*) =
      SimpleCommand("logoutd", CmdArgCtx(args.toVector, s))

    def logsave(args: Any*) =
      SimpleCommand("logsave", CmdArgCtx(args.toVector, s))

    def look(args: Any*) =
      SimpleCommand("look", CmdArgCtx(args.toVector, s))

    def lookandfeeltool(args: Any*) =
      SimpleCommand("lookandfeeltool", CmdArgCtx(args.toVector, s))

    def losetup(args: Any*) =
      SimpleCommand("losetup", CmdArgCtx(args.toVector, s))

    def lp(args: Any*) =
      SimpleCommand("lp", CmdArgCtx(args.toVector, s))

    def lpadmin(args: Any*) =
      SimpleCommand("lpadmin", CmdArgCtx(args.toVector, s))

    def lpc(args: Any*) =
      SimpleCommand("lpc", CmdArgCtx(args.toVector, s))

    def lpinfo(args: Any*) =
      SimpleCommand("lpinfo", CmdArgCtx(args.toVector, s))

    def lpmove(args: Any*) =
      SimpleCommand("lpmove", CmdArgCtx(args.toVector, s))

    def lpoptions(args: Any*) =
      SimpleCommand("lpoptions", CmdArgCtx(args.toVector, s))

    def lpq(args: Any*) =
      SimpleCommand("lpq", CmdArgCtx(args.toVector, s))

    def lpr(args: Any*) =
      SimpleCommand("lpr", CmdArgCtx(args.toVector, s))

    def lprm(args: Any*) =
      SimpleCommand("lprm", CmdArgCtx(args.toVector, s))

    def lpstat(args: Any*) =
      SimpleCommand("lpstat", CmdArgCtx(args.toVector, s))

    def ls(args: Any*) =
      SimpleCommand("ls", CmdArgCtx(args.toVector, s))

    def lsattr(args: Any*) =
      SimpleCommand("lsattr", CmdArgCtx(args.toVector, s))

    def lsblk(args: Any*) =
      SimpleCommand("lsblk", CmdArgCtx(args.toVector, s))

    def lscpu(args: Any*) =
      SimpleCommand("lscpu", CmdArgCtx(args.toVector, s))

    def lsipc(args: Any*) =
      SimpleCommand("lsipc", CmdArgCtx(args.toVector, s))

    def lslocks(args: Any*) =
      SimpleCommand("lslocks", CmdArgCtx(args.toVector, s))

    def lslogins(args: Any*) =
      SimpleCommand("lslogins", CmdArgCtx(args.toVector, s))

    def lsmem(args: Any*) =
      SimpleCommand("lsmem", CmdArgCtx(args.toVector, s))

    def lsmod(args: Any*) =
      SimpleCommand("lsmod", CmdArgCtx(args.toVector, s))

    def lsns(args: Any*) =
      SimpleCommand("lsns", CmdArgCtx(args.toVector, s))

    def lsof(args: Any*) =
      SimpleCommand("lsof", CmdArgCtx(args.toVector, s))

    def lvchange(args: Any*) =
      SimpleCommand("lvchange", CmdArgCtx(args.toVector, s))

    def lvconvert(args: Any*) =
      SimpleCommand("lvconvert", CmdArgCtx(args.toVector, s))

    def lvcreate(args: Any*) =
      SimpleCommand("lvcreate", CmdArgCtx(args.toVector, s))

    def lvdisplay(args: Any*) =
      SimpleCommand("lvdisplay", CmdArgCtx(args.toVector, s))

    def lvextend(args: Any*) =
      SimpleCommand("lvextend", CmdArgCtx(args.toVector, s))

    def lvm(args: Any*) =
      SimpleCommand("lvm", CmdArgCtx(args.toVector, s))

    def lvmconfig(args: Any*) =
      SimpleCommand("lvmconfig", CmdArgCtx(args.toVector, s))

    def lvmdiskscan(args: Any*) =
      SimpleCommand("lvmdiskscan", CmdArgCtx(args.toVector, s))

    def lvmdump(args: Any*) =
      SimpleCommand("lvmdump", CmdArgCtx(args.toVector, s))

    def lvmsadc(args: Any*) =
      SimpleCommand("lvmsadc", CmdArgCtx(args.toVector, s))

    def lvmsar(args: Any*) =
      SimpleCommand("lvmsar", CmdArgCtx(args.toVector, s))

    def lvreduce(args: Any*) =
      SimpleCommand("lvreduce", CmdArgCtx(args.toVector, s))

    def lvremove(args: Any*) =
      SimpleCommand("lvremove", CmdArgCtx(args.toVector, s))

    def lvrename(args: Any*) =
      SimpleCommand("lvrename", CmdArgCtx(args.toVector, s))

    def lvresize(args: Any*) =
      SimpleCommand("lvresize", CmdArgCtx(args.toVector, s))

    def lvs(args: Any*) =
      SimpleCommand("lvs", CmdArgCtx(args.toVector, s))

    def lvscan(args: Any*) =
      SimpleCommand("lvscan", CmdArgCtx(args.toVector, s))

    def lzcat(args: Any*) =
      SimpleCommand("lzcat", CmdArgCtx(args.toVector, s))

    def lzcmp(args: Any*) =
      SimpleCommand("lzcmp", CmdArgCtx(args.toVector, s))

    def lzdiff(args: Any*) =
      SimpleCommand("lzdiff", CmdArgCtx(args.toVector, s))

    def lzegrep(args: Any*) =
      SimpleCommand("lzegrep", CmdArgCtx(args.toVector, s))

    def lzfgrep(args: Any*) =
      SimpleCommand("lzfgrep", CmdArgCtx(args.toVector, s))

    def lzgrep(args: Any*) =
      SimpleCommand("lzgrep", CmdArgCtx(args.toVector, s))

    def lzless(args: Any*) =
      SimpleCommand("lzless", CmdArgCtx(args.toVector, s))

    def lzma(args: Any*) =
      SimpleCommand("lzma", CmdArgCtx(args.toVector, s))

    def lzmadec(args: Any*) =
      SimpleCommand("lzmadec", CmdArgCtx(args.toVector, s))

    def lzmainfo(args: Any*) =
      SimpleCommand("lzmainfo", CmdArgCtx(args.toVector, s))

    def lzmore(args: Any*) =
      SimpleCommand("lzmore", CmdArgCtx(args.toVector, s))

    def machinectl(args: Any*) =
      SimpleCommand("machinectl", CmdArgCtx(args.toVector, s))

    def make_bcache(args: Any*) =
      SimpleCommand("make_bcache", CmdArgCtx(args.toVector, s))

    def makedb(args: Any*) =
      SimpleCommand("makedb", CmdArgCtx(args.toVector, s))

    def makehmmerdb(args: Any*) =
      SimpleCommand("makehmmerdb", CmdArgCtx(args.toVector, s))

    def makeinfo(args: Any*) =
      SimpleCommand("makeinfo", CmdArgCtx(args.toVector, s))

    def makenucseq(args: Any*) =
      SimpleCommand("makenucseq", CmdArgCtx(args.toVector, s))

    def makeprotseq(args: Any*) =
      SimpleCommand("makeprotseq", CmdArgCtx(args.toVector, s))

    def man(args: Any*) =
      SimpleCommand("man", CmdArgCtx(args.toVector, s))

    def mandb(args: Any*) =
      SimpleCommand("mandb", CmdArgCtx(args.toVector, s))

    def manpath(args: Any*) =
      SimpleCommand("manpath", CmdArgCtx(args.toVector, s))

    def mapBed(args: Any*) =
      SimpleCommand("mapBed", CmdArgCtx(args.toVector, s))

    def mapfile(args: Any*) =
      SimpleCommand("mapfile", CmdArgCtx(args.toVector, s))

    def mapscrn(args: Any*) =
      SimpleCommand("mapscrn", CmdArgCtx(args.toVector, s))

    def maq2sam_long(args: Any*) =
      SimpleCommand("maq2sam_long", CmdArgCtx(args.toVector, s))

    def maq2sam_short(args: Any*) =
      SimpleCommand("maq2sam_short", CmdArgCtx(args.toVector, s))

    def marscan(args: Any*) =
      SimpleCommand("marscan", CmdArgCtx(args.toVector, s))

    def maskFastaFromBed(args: Any*) =
      SimpleCommand("maskFastaFromBed", CmdArgCtx(args.toVector, s))

    def maskambignuc(args: Any*) =
      SimpleCommand("maskambignuc", CmdArgCtx(args.toVector, s))

    def maskambigprot(args: Any*) =
      SimpleCommand("maskambigprot", CmdArgCtx(args.toVector, s))

    def maskfeat(args: Any*) =
      SimpleCommand("maskfeat", CmdArgCtx(args.toVector, s))

    def maskseq(args: Any*) =
      SimpleCommand("maskseq", CmdArgCtx(args.toVector, s))

    def matcher(args: Any*) =
      SimpleCommand("matcher", CmdArgCtx(args.toVector, s))

    def mb(args: Any*) =
      SimpleCommand("mb", CmdArgCtx(args.toVector, s))

    def mcookie(args: Any*) =
      SimpleCommand("mcookie", CmdArgCtx(args.toVector, s))

    def md5fa(args: Any*) =
      SimpleCommand("md5fa", CmdArgCtx(args.toVector, s))

    def md5sum(args: Any*) =
      SimpleCommand("md5sum", CmdArgCtx(args.toVector, s))

    def md5sum_lite(args: Any*) =
      SimpleCommand("md5sum_lite", CmdArgCtx(args.toVector, s))

    def mdadm(args: Any*) =
      SimpleCommand("mdadm", CmdArgCtx(args.toVector, s))

    def mdmon(args: Any*) =
      SimpleCommand("mdmon", CmdArgCtx(args.toVector, s))

    def megamerger(args: Any*) =
      SimpleCommand("megamerger", CmdArgCtx(args.toVector, s))

    def meinproc5(args: Any*) =
      SimpleCommand("meinproc5", CmdArgCtx(args.toVector, s))

    def meld(args: Any*) =
      SimpleCommand("meld", CmdArgCtx(args.toVector, s))

    def mencoder(args: Any*) =
      SimpleCommand("mencoder", CmdArgCtx(args.toVector, s))

    def mergeBed(args: Any*) =
      SimpleCommand("mergeBed", CmdArgCtx(args.toVector, s))

    def merger(args: Any*) =
      SimpleCommand("merger", CmdArgCtx(args.toVector, s))

    def mesg(args: Any*) =
      SimpleCommand("mesg", CmdArgCtx(args.toVector, s))

    def mk_cmds(args: Any*) =
      SimpleCommand("mk_cmds", CmdArgCtx(args.toVector, s))

    def mkdir(args: Any*) =
      SimpleCommand("mkdir", CmdArgCtx(args.toVector, s))

    def mkdosfs(args: Any*) =
      SimpleCommand("mkdosfs", CmdArgCtx(args.toVector, s))

    def mke2fs(args: Any*) =
      SimpleCommand("mke2fs", CmdArgCtx(args.toVector, s))

    def mkfifo(args: Any*) =
      SimpleCommand("mkfifo", CmdArgCtx(args.toVector, s))

    def mkfs(args: Any*) =
      SimpleCommand("mkfs", CmdArgCtx(args.toVector, s))

    def mkhomedir_helper(args: Any*) =
      SimpleCommand("mkhomedir_helper", CmdArgCtx(args.toVector, s))

    def mknod(args: Any*) =
      SimpleCommand("mknod", CmdArgCtx(args.toVector, s))

    def mkpasswd(args: Any*) =
      SimpleCommand("mkpasswd", CmdArgCtx(args.toVector, s))

    def mkswap(args: Any*) =
      SimpleCommand("mkswap", CmdArgCtx(args.toVector, s))

    def mktemp(args: Any*) =
      SimpleCommand("mktemp", CmdArgCtx(args.toVector, s))

    def mm2gv(args: Any*) =
      SimpleCommand("mm2gv", CmdArgCtx(args.toVector, s))

    def mmcli(args: Any*) =
      SimpleCommand("mmcli", CmdArgCtx(args.toVector, s))

    def modinfo(args: Any*) =
      SimpleCommand("modinfo", CmdArgCtx(args.toVector, s))

    def modprobe(args: Any*) =
      SimpleCommand("modprobe", CmdArgCtx(args.toVector, s))

    def mogrify(args: Any*) =
      SimpleCommand("mogrify", CmdArgCtx(args.toVector, s))

    def montage(args: Any*) =
      SimpleCommand("montage", CmdArgCtx(args.toVector, s))

    def more(args: Any*) =
      SimpleCommand("more", CmdArgCtx(args.toVector, s))

    def mosdepth(args: Any*) =
      SimpleCommand("mosdepth", CmdArgCtx(args.toVector, s))

    def mount(args: Any*) =
      SimpleCommand("mount", CmdArgCtx(args.toVector, s))

    def mountpoint(args: Any*) =
      SimpleCommand("mountpoint", CmdArgCtx(args.toVector, s))

    def mplayer(args: Any*) =
      SimpleCommand("mplayer", CmdArgCtx(args.toVector, s))

    def msbar(args: Any*) =
      SimpleCommand("msbar", CmdArgCtx(args.toVector, s))

    def mtpfs(args: Any*) =
      SimpleCommand("mtpfs", CmdArgCtx(args.toVector, s))

    def multiBamCov(args: Any*) =
      SimpleCommand("multiBamCov", CmdArgCtx(args.toVector, s))

    def multiIntersectBed(args: Any*) =
      SimpleCommand("multiIntersectBed", CmdArgCtx(args.toVector, s))

    def muscle(args: Any*) =
      SimpleCommand("muscle", CmdArgCtx(args.toVector, s))

    def mv(args: Any*) =
      SimpleCommand("mv", CmdArgCtx(args.toVector, s))

    def mvn(args: Any*) =
      SimpleCommand("mvn", CmdArgCtx(args.toVector, s))

    def mwcontam(args: Any*) =
      SimpleCommand("mwcontam", CmdArgCtx(args.toVector, s))

    def mwfilter(args: Any*) =
      SimpleCommand("mwfilter", CmdArgCtx(args.toVector, s))

    def namei(args: Any*) =
      SimpleCommand("namei", CmdArgCtx(args.toVector, s))

    def nameif(args: Any*) =
      SimpleCommand("nameif", CmdArgCtx(args.toVector, s))

    def nano(args: Any*) =
      SimpleCommand("nano", CmdArgCtx(args.toVector, s))

    def nc(args: Any*) =
      SimpleCommand("nc", CmdArgCtx(args.toVector, s))

    def neato(args: Any*) =
      SimpleCommand("neato", CmdArgCtx(args.toVector, s))

    def needle(args: Any*) =
      SimpleCommand("needle", CmdArgCtx(args.toVector, s))

    def needleall(args: Any*) =
      SimpleCommand("needleall", CmdArgCtx(args.toVector, s))

    def netbeans(args: Any*) =
      SimpleCommand("netbeans", CmdArgCtx(args.toVector, s))

    def netstat(args: Any*) =
      SimpleCommand("netstat", CmdArgCtx(args.toVector, s))

    def networkctl(args: Any*) =
      SimpleCommand("networkctl", CmdArgCtx(args.toVector, s))

    def newcpgreport(args: Any*) =
      SimpleCommand("newcpgreport", CmdArgCtx(args.toVector, s))

    def newcpgseek(args: Any*) =
      SimpleCommand("newcpgseek", CmdArgCtx(args.toVector, s))

    def newgidmap(args: Any*) =
      SimpleCommand("newgidmap", CmdArgCtx(args.toVector, s))

    def newgrp(args: Any*) =
      SimpleCommand("newgrp", CmdArgCtx(args.toVector, s))

    def newseq(args: Any*) =
      SimpleCommand("newseq", CmdArgCtx(args.toVector, s))

    def newuidmap(args: Any*) =
      SimpleCommand("newuidmap", CmdArgCtx(args.toVector, s))

    def newusers(args: Any*) =
      SimpleCommand("newusers", CmdArgCtx(args.toVector, s))

    def nfbpf_compile(args: Any*) =
      SimpleCommand("nfbpf_compile", CmdArgCtx(args.toVector, s))

    def nfnl_osf(args: Any*) =
      SimpleCommand("nfnl_osf", CmdArgCtx(args.toVector, s))

    def nhmmer(args: Any*) =
      SimpleCommand("nhmmer", CmdArgCtx(args.toVector, s))

    def nhmmscan(args: Any*) =
      SimpleCommand("nhmmscan", CmdArgCtx(args.toVector, s))

    def nice(args: Any*) =
      SimpleCommand("nice", CmdArgCtx(args.toVector, s))

    def ninfod(args: Any*) =
      SimpleCommand("ninfod", CmdArgCtx(args.toVector, s))

    def nisdomainname(args: Any*) =
      SimpleCommand("nisdomainname", CmdArgCtx(args.toVector, s))

    def nix(args: Any*) =
      SimpleCommand("nix", CmdArgCtx(args.toVector, s))

    def nix_build(args: Any*) =
      SimpleCommand("nix_build", CmdArgCtx(args.toVector, s))

    def nix_channel(args: Any*) =
      SimpleCommand("nix_channel", CmdArgCtx(args.toVector, s))

    def nix_collect_garbage(args: Any*) =
      SimpleCommand("nix_collect_garbage", CmdArgCtx(args.toVector, s))

    def nix_copy_closure(args: Any*) =
      SimpleCommand("nix_copy_closure", CmdArgCtx(args.toVector, s))

    def nix_daemon(args: Any*) =
      SimpleCommand("nix_daemon", CmdArgCtx(args.toVector, s))

    def nix_env(args: Any*) =
      SimpleCommand("nix_env", CmdArgCtx(args.toVector, s))

    def nix_hash(args: Any*) =
      SimpleCommand("nix_hash", CmdArgCtx(args.toVector, s))

    def nix_info(args: Any*) =
      SimpleCommand("nix_info", CmdArgCtx(args.toVector, s))

    def nix_instantiate(args: Any*) =
      SimpleCommand("nix_instantiate", CmdArgCtx(args.toVector, s))

    def nix_prefetch_url(args: Any*) =
      SimpleCommand("nix_prefetch_url", CmdArgCtx(args.toVector, s))

    def nix_shell(args: Any*) =
      SimpleCommand("nix_shell", CmdArgCtx(args.toVector, s))

    def nix_store(args: Any*) =
      SimpleCommand("nix_store", CmdArgCtx(args.toVector, s))

    def nixos_build_vms(args: Any*) =
      SimpleCommand("nixos_build_vms", CmdArgCtx(args.toVector, s))

    def nixos_container(args: Any*) =
      SimpleCommand("nixos_container", CmdArgCtx(args.toVector, s))

    def nixos_enter(args: Any*) =
      SimpleCommand("nixos_enter", CmdArgCtx(args.toVector, s))

    def nixos_generate_config(args: Any*) =
      SimpleCommand("nixos_generate_config", CmdArgCtx(args.toVector, s))

    def nixos_help(args: Any*) =
      SimpleCommand("nixos_help", CmdArgCtx(args.toVector, s))

    def nixos_install(args: Any*) =
      SimpleCommand("nixos_install", CmdArgCtx(args.toVector, s))

    def nixos_option(args: Any*) =
      SimpleCommand("nixos_option", CmdArgCtx(args.toVector, s))

    def nixos_rebuild(args: Any*) =
      SimpleCommand("nixos_rebuild", CmdArgCtx(args.toVector, s))

    def nixos_version(args: Any*) =
      SimpleCommand("nixos_version", CmdArgCtx(args.toVector, s))

    def nl(args: Any*) =
      SimpleCommand("nl", CmdArgCtx(args.toVector, s))

    def nm_applet(args: Any*) =
      SimpleCommand("nm_applet", CmdArgCtx(args.toVector, s))

    def nm_connection_editor(args: Any*) =
      SimpleCommand("nm_connection_editor", CmdArgCtx(args.toVector, s))

    def nm_online(args: Any*) =
      SimpleCommand("nm_online", CmdArgCtx(args.toVector, s))

    def nmcli(args: Any*) =
      SimpleCommand("nmcli", CmdArgCtx(args.toVector, s))

    def nmtui(args: Any*) =
      SimpleCommand("nmtui", CmdArgCtx(args.toVector, s))

    def nmtui_connect(args: Any*) =
      SimpleCommand("nmtui_connect", CmdArgCtx(args.toVector, s))

    def nmtui_edit(args: Any*) =
      SimpleCommand("nmtui_edit", CmdArgCtx(args.toVector, s))

    def nmtui_hostname(args: Any*) =
      SimpleCommand("nmtui_hostname", CmdArgCtx(args.toVector, s))

    def node(args: Any*) =
      SimpleCommand("node", CmdArgCtx(args.toVector, s))

    def nohtml(args: Any*) =
      SimpleCommand("nohtml", CmdArgCtx(args.toVector, s))

    def nohup(args: Any*) =
      SimpleCommand("nohup", CmdArgCtx(args.toVector, s))

    def nologin(args: Any*) =
      SimpleCommand("nologin", CmdArgCtx(args.toVector, s))

    def nop(args: Any*) =
      SimpleCommand("nop", CmdArgCtx(args.toVector, s))

    def noreturn(args: Any*) =
      SimpleCommand("noreturn", CmdArgCtx(args.toVector, s))

    def nospace(args: Any*) =
      SimpleCommand("nospace", CmdArgCtx(args.toVector, s))

    def notab(args: Any*) =
      SimpleCommand("notab", CmdArgCtx(args.toVector, s))

    def notseq(args: Any*) =
      SimpleCommand("notseq", CmdArgCtx(args.toVector, s))

    def npm(args: Any*) =
      SimpleCommand("npm", CmdArgCtx(args.toVector, s))

    def nproc(args: Any*) =
      SimpleCommand("nproc", CmdArgCtx(args.toVector, s))

    def npx(args: Any*) =
      SimpleCommand("npx", CmdArgCtx(args.toVector, s))

    def nscd(args: Any*) =
      SimpleCommand("nscd", CmdArgCtx(args.toVector, s))

    def nsenter(args: Any*) =
      SimpleCommand("nsenter", CmdArgCtx(args.toVector, s))

    def nstat(args: Any*) =
      SimpleCommand("nstat", CmdArgCtx(args.toVector, s))

    def nthseq(args: Any*) =
      SimpleCommand("nthseq", CmdArgCtx(args.toVector, s))

    def nthseqset(args: Any*) =
      SimpleCommand("nthseqset", CmdArgCtx(args.toVector, s))

    def nucBed(args: Any*) =
      SimpleCommand("nucBed", CmdArgCtx(args.toVector, s))

    def numfmt(args: Any*) =
      SimpleCommand("numfmt", CmdArgCtx(args.toVector, s))

    def nvim(args: Any*) =
      SimpleCommand("nvim", CmdArgCtx(args.toVector, s))

    def nvim_python(args: Any*) =
      SimpleCommand("nvim_python", CmdArgCtx(args.toVector, s))

    def nvim_python3(args: Any*) =
      SimpleCommand("nvim_python3", CmdArgCtx(args.toVector, s))

    def nvim_ruby(args: Any*) =
      SimpleCommand("nvim_ruby", CmdArgCtx(args.toVector, s))

    def nvlc(args: Any*) =
      SimpleCommand("nvlc", CmdArgCtx(args.toVector, s))

    def octanol(args: Any*) =
      SimpleCommand("octanol", CmdArgCtx(args.toVector, s))

    def od(args: Any*) =
      SimpleCommand("od", CmdArgCtx(args.toVector, s))

    def oddcomp(args: Any*) =
      SimpleCommand("oddcomp", CmdArgCtx(args.toVector, s))

    def ontocount(args: Any*) =
      SimpleCommand("ontocount", CmdArgCtx(args.toVector, s))

    def ontoget(args: Any*) =
      SimpleCommand("ontoget", CmdArgCtx(args.toVector, s))

    def ontogetcommon(args: Any*) =
      SimpleCommand("ontogetcommon", CmdArgCtx(args.toVector, s))

    def ontogetdown(args: Any*) =
      SimpleCommand("ontogetdown", CmdArgCtx(args.toVector, s))

    def ontogetobsolete(args: Any*) =
      SimpleCommand("ontogetobsolete", CmdArgCtx(args.toVector, s))

    def ontogetroot(args: Any*) =
      SimpleCommand("ontogetroot", CmdArgCtx(args.toVector, s))

    def ontogetsibs(args: Any*) =
      SimpleCommand("ontogetsibs", CmdArgCtx(args.toVector, s))

    def ontogetup(args: Any*) =
      SimpleCommand("ontogetup", CmdArgCtx(args.toVector, s))

    def ontoisobsolete(args: Any*) =
      SimpleCommand("ontoisobsolete", CmdArgCtx(args.toVector, s))

    def ontotext(args: Any*) =
      SimpleCommand("ontotext", CmdArgCtx(args.toVector, s))

    def openvt(args: Any*) =
      SimpleCommand("openvt", CmdArgCtx(args.toVector, s))

    def osage(args: Any*) =
      SimpleCommand("osage", CmdArgCtx(args.toVector, s))

    def outpsfheader(args: Any*) =
      SimpleCommand("outpsfheader", CmdArgCtx(args.toVector, s))

    def oxygen_demo5(args: Any*) =
      SimpleCommand("oxygen_demo5", CmdArgCtx(args.toVector, s))

    def oxygen_settings5(args: Any*) =
      SimpleCommand("oxygen_settings5", CmdArgCtx(args.toVector, s))

    def pacat(args: Any*) =
      SimpleCommand("pacat", CmdArgCtx(args.toVector, s))

    def pacmd(args: Any*) =
      SimpleCommand("pacmd", CmdArgCtx(args.toVector, s))

    def pactl(args: Any*) =
      SimpleCommand("pactl", CmdArgCtx(args.toVector, s))

    def padsp(args: Any*) =
      SimpleCommand("padsp", CmdArgCtx(args.toVector, s))

    def pairToBed(args: Any*) =
      SimpleCommand("pairToBed", CmdArgCtx(args.toVector, s))

    def pairToPair(args: Any*) =
      SimpleCommand("pairToPair", CmdArgCtx(args.toVector, s))

    def palindrome(args: Any*) =
      SimpleCommand("palindrome", CmdArgCtx(args.toVector, s))

    def pam_tally(args: Any*) =
      SimpleCommand("pam_tally", CmdArgCtx(args.toVector, s))

    def pam_tally2(args: Any*) =
      SimpleCommand("pam_tally2", CmdArgCtx(args.toVector, s))

    def pam_timestamp_check(args: Any*) =
      SimpleCommand("pam_timestamp_check", CmdArgCtx(args.toVector, s))

    def pamon(args: Any*) =
      SimpleCommand("pamon", CmdArgCtx(args.toVector, s))

    def paplay(args: Any*) =
      SimpleCommand("paplay", CmdArgCtx(args.toVector, s))

    def parec(args: Any*) =
      SimpleCommand("parec", CmdArgCtx(args.toVector, s))

    def parecord(args: Any*) =
      SimpleCommand("parecord", CmdArgCtx(args.toVector, s))

    def parsetrigrams(args: Any*) =
      SimpleCommand("parsetrigrams", CmdArgCtx(args.toVector, s))

    def partx(args: Any*) =
      SimpleCommand("partx", CmdArgCtx(args.toVector, s))

    def passwd(args: Any*) =
      SimpleCommand("passwd", CmdArgCtx(args.toVector, s))

    def paste(args: Any*) =
      SimpleCommand("paste", CmdArgCtx(args.toVector, s))

    def pasteseq(args: Any*) =
      SimpleCommand("pasteseq", CmdArgCtx(args.toVector, s))

    def pasuspender(args: Any*) =
      SimpleCommand("pasuspender", CmdArgCtx(args.toVector, s))

    def patch(args: Any*) =
      SimpleCommand("patch", CmdArgCtx(args.toVector, s))

    def patchwork(args: Any*) =
      SimpleCommand("patchwork", CmdArgCtx(args.toVector, s))

    def pathchk(args: Any*) =
      SimpleCommand("pathchk", CmdArgCtx(args.toVector, s))

    def patmatdb(args: Any*) =
      SimpleCommand("patmatdb", CmdArgCtx(args.toVector, s))

    def patmatmotifs(args: Any*) =
      SimpleCommand("patmatmotifs", CmdArgCtx(args.toVector, s))

    def pcf2vpnc(args: Any*) =
      SimpleCommand("pcf2vpnc", CmdArgCtx(args.toVector, s))

    def pcprofiledump(args: Any*) =
      SimpleCommand("pcprofiledump", CmdArgCtx(args.toVector, s))

    def pdf2dsc(args: Any*) =
      SimpleCommand("pdf2dsc", CmdArgCtx(args.toVector, s))

    def pdf2ps(args: Any*) =
      SimpleCommand("pdf2ps", CmdArgCtx(args.toVector, s))

    def pdftexi2dvi(args: Any*) =
      SimpleCommand("pdftexi2dvi", CmdArgCtx(args.toVector, s))

    def pepcoil(args: Any*) =
      SimpleCommand("pepcoil", CmdArgCtx(args.toVector, s))

    def pepdigest(args: Any*) =
      SimpleCommand("pepdigest", CmdArgCtx(args.toVector, s))

    def pepinfo(args: Any*) =
      SimpleCommand("pepinfo", CmdArgCtx(args.toVector, s))

    def pepnet(args: Any*) =
      SimpleCommand("pepnet", CmdArgCtx(args.toVector, s))

    def pepstats(args: Any*) =
      SimpleCommand("pepstats", CmdArgCtx(args.toVector, s))

    def pepwheel(args: Any*) =
      SimpleCommand("pepwheel", CmdArgCtx(args.toVector, s))

    def pepwindow(args: Any*) =
      SimpleCommand("pepwindow", CmdArgCtx(args.toVector, s))

    def pepwindowall(args: Any*) =
      SimpleCommand("pepwindowall", CmdArgCtx(args.toVector, s))

    def perl(args: Any*) =
      SimpleCommand("perl", CmdArgCtx(args.toVector, s))

    def perlbug(args: Any*) =
      SimpleCommand("perlbug", CmdArgCtx(args.toVector, s))

    def perldoc(args: Any*) =
      SimpleCommand("perldoc", CmdArgCtx(args.toVector, s))

    def perlivp(args: Any*) =
      SimpleCommand("perlivp", CmdArgCtx(args.toVector, s))

    def perlthanks(args: Any*) =
      SimpleCommand("perlthanks", CmdArgCtx(args.toVector, s))

    def pf2afm(args: Any*) =
      SimpleCommand("pf2afm", CmdArgCtx(args.toVector, s))

    def pfbtopfa(args: Any*) =
      SimpleCommand("pfbtopfa", CmdArgCtx(args.toVector, s))

    def pgrep(args: Any*) =
      SimpleCommand("pgrep", CmdArgCtx(args.toVector, s))

    def phmmer(args: Any*) =
      SimpleCommand("phmmer", CmdArgCtx(args.toVector, s))

    def picard(args: Any*) =
      SimpleCommand("picard", CmdArgCtx(args.toVector, s))

    def piconv(args: Any*) =
      SimpleCommand("piconv", CmdArgCtx(args.toVector, s))

    def pidof(args: Any*) =
      SimpleCommand("pidof", CmdArgCtx(args.toVector, s))

    def ping(args: Any*) =
      SimpleCommand("ping", CmdArgCtx(args.toVector, s))

    def pinky(args: Any*) =
      SimpleCommand("pinky", CmdArgCtx(args.toVector, s))

    def pivot_root(args: Any*) =
      SimpleCommand("pivot_root", CmdArgCtx(args.toVector, s))

    def pk_example_frobnicate(args: Any*) =
      SimpleCommand("pk_example_frobnicate", CmdArgCtx(args.toVector, s))

    def pkaction(args: Any*) =
      SimpleCommand("pkaction", CmdArgCtx(args.toVector, s))

    def pkcheck(args: Any*) =
      SimpleCommand("pkcheck", CmdArgCtx(args.toVector, s))

    def pkexec(args: Any*) =
      SimpleCommand("pkexec", CmdArgCtx(args.toVector, s))

    def pkill(args: Any*) =
      SimpleCommand("pkill", CmdArgCtx(args.toVector, s))

    def pkttyagent(args: Any*) =
      SimpleCommand("pkttyagent", CmdArgCtx(args.toVector, s))

    def pl2pm(args: Any*) =
      SimpleCommand("pl2pm", CmdArgCtx(args.toVector, s))

    def plasma_waitforname(args: Any*) =
      SimpleCommand("plasma_waitforname", CmdArgCtx(args.toVector, s))

    def plasmapkg2(args: Any*) =
      SimpleCommand("plasmapkg2", CmdArgCtx(args.toVector, s))

    def plasmashell(args: Any*) =
      SimpleCommand("plasmashell", CmdArgCtx(args.toVector, s))

    def plasmawindowed(args: Any*) =
      SimpleCommand("plasmawindowed", CmdArgCtx(args.toVector, s))

    def platypus(args: Any*) =
      SimpleCommand("platypus", CmdArgCtx(args.toVector, s))

    def pldd(args: Any*) =
      SimpleCommand("pldd", CmdArgCtx(args.toVector, s))

    def plink(args: Any*) =
      SimpleCommand("plink", CmdArgCtx(args.toVector, s))

    def plipconfig(args: Any*) =
      SimpleCommand("plipconfig", CmdArgCtx(args.toVector, s))

    def plot_bamstats(args: Any*) =
      SimpleCommand("plot_bamstats", CmdArgCtx(args.toVector, s))

    def plot_vcfstats(args: Any*) =
      SimpleCommand("plot_vcfstats", CmdArgCtx(args.toVector, s))

    def plotcon(args: Any*) =
      SimpleCommand("plotcon", CmdArgCtx(args.toVector, s))

    def plotorf(args: Any*) =
      SimpleCommand("plotorf", CmdArgCtx(args.toVector, s))

    def pmap(args: Any*) =
      SimpleCommand("pmap", CmdArgCtx(args.toVector, s))

    def pod2html(args: Any*) =
      SimpleCommand("pod2html", CmdArgCtx(args.toVector, s))

    def pod2man(args: Any*) =
      SimpleCommand("pod2man", CmdArgCtx(args.toVector, s))

    def pod2texi(args: Any*) =
      SimpleCommand("pod2texi", CmdArgCtx(args.toVector, s))

    def pod2text(args: Any*) =
      SimpleCommand("pod2text", CmdArgCtx(args.toVector, s))

    def pod2usage(args: Any*) =
      SimpleCommand("pod2usage", CmdArgCtx(args.toVector, s))

    def podchecker(args: Any*) =
      SimpleCommand("podchecker", CmdArgCtx(args.toVector, s))

    def podselect(args: Any*) =
      SimpleCommand("podselect", CmdArgCtx(args.toVector, s))

    def polydot(args: Any*) =
      SimpleCommand("polydot", CmdArgCtx(args.toVector, s))

    def portablectl(args: Any*) =
      SimpleCommand("portablectl", CmdArgCtx(args.toVector, s))

    def poweroff(args: Any*) =
      SimpleCommand("poweroff", CmdArgCtx(args.toVector, s))

    def ppdc(args: Any*) =
      SimpleCommand("ppdc", CmdArgCtx(args.toVector, s))

    def ppdhtml(args: Any*) =
      SimpleCommand("ppdhtml", CmdArgCtx(args.toVector, s))

    def ppdi(args: Any*) =
      SimpleCommand("ppdi", CmdArgCtx(args.toVector, s))

    def ppdmerge(args: Any*) =
      SimpleCommand("ppdmerge", CmdArgCtx(args.toVector, s))

    def ppdpo(args: Any*) =
      SimpleCommand("ppdpo", CmdArgCtx(args.toVector, s))

    def pphs(args: Any*) =
      SimpleCommand("pphs", CmdArgCtx(args.toVector, s))

    def pr(args: Any*) =
      SimpleCommand("pr", CmdArgCtx(args.toVector, s))

    def preg(args: Any*) =
      SimpleCommand("preg", CmdArgCtx(args.toVector, s))

    def prettyplot(args: Any*) =
      SimpleCommand("prettyplot", CmdArgCtx(args.toVector, s))

    def prettyseq(args: Any*) =
      SimpleCommand("prettyseq", CmdArgCtx(args.toVector, s))

    def primersearch(args: Any*) =
      SimpleCommand("primersearch", CmdArgCtx(args.toVector, s))

    def printafm(args: Any*) =
      SimpleCommand("printafm", CmdArgCtx(args.toVector, s))

    def printenv(args: Any*) =
      SimpleCommand("printenv", CmdArgCtx(args.toVector, s))

    def printf(args: Any*) =
      SimpleCommand("printf", CmdArgCtx(args.toVector, s))

    def printsextract(args: Any*) =
      SimpleCommand("printsextract", CmdArgCtx(args.toVector, s))

    def prlimit(args: Any*) =
      SimpleCommand("prlimit", CmdArgCtx(args.toVector, s))

    def profit(args: Any*) =
      SimpleCommand("profit", CmdArgCtx(args.toVector, s))

    def prophecy(args: Any*) =
      SimpleCommand("prophecy", CmdArgCtx(args.toVector, s))

    def prophet(args: Any*) =
      SimpleCommand("prophet", CmdArgCtx(args.toVector, s))

    def prosextract(args: Any*) =
      SimpleCommand("prosextract", CmdArgCtx(args.toVector, s))

    def protocoltojson(args: Any*) =
      SimpleCommand("protocoltojson", CmdArgCtx(args.toVector, s))

    def prove(args: Any*) =
      SimpleCommand("prove", CmdArgCtx(args.toVector, s))

    def prune(args: Any*) =
      SimpleCommand("prune", CmdArgCtx(args.toVector, s))

    def ps(args: Any*) =
      SimpleCommand("ps", CmdArgCtx(args.toVector, s))

    def ps2ascii(args: Any*) =
      SimpleCommand("ps2ascii", CmdArgCtx(args.toVector, s))

    def ps2epsi(args: Any*) =
      SimpleCommand("ps2epsi", CmdArgCtx(args.toVector, s))

    def ps2pdf(args: Any*) =
      SimpleCommand("ps2pdf", CmdArgCtx(args.toVector, s))

    def ps2pdf12(args: Any*) =
      SimpleCommand("ps2pdf12", CmdArgCtx(args.toVector, s))

    def ps2pdf13(args: Any*) =
      SimpleCommand("ps2pdf13", CmdArgCtx(args.toVector, s))

    def ps2pdf14(args: Any*) =
      SimpleCommand("ps2pdf14", CmdArgCtx(args.toVector, s))

    def ps2pdfwr(args: Any*) =
      SimpleCommand("ps2pdfwr", CmdArgCtx(args.toVector, s))

    def ps2ps(args: Any*) =
      SimpleCommand("ps2ps", CmdArgCtx(args.toVector, s))

    def ps2ps2(args: Any*) =
      SimpleCommand("ps2ps2", CmdArgCtx(args.toVector, s))

    def pscan(args: Any*) =
      SimpleCommand("pscan", CmdArgCtx(args.toVector, s))

    def psfaddtable(args: Any*) =
      SimpleCommand("psfaddtable", CmdArgCtx(args.toVector, s))

    def psfgettable(args: Any*) =
      SimpleCommand("psfgettable", CmdArgCtx(args.toVector, s))

    def psfstriptable(args: Any*) =
      SimpleCommand("psfstriptable", CmdArgCtx(args.toVector, s))

    def psfxtable(args: Any*) =
      SimpleCommand("psfxtable", CmdArgCtx(args.toVector, s))

    def psiphi(args: Any*) =
      SimpleCommand("psiphi", CmdArgCtx(args.toVector, s))

    def ptar(args: Any*) =
      SimpleCommand("ptar", CmdArgCtx(args.toVector, s))

    def ptardiff(args: Any*) =
      SimpleCommand("ptardiff", CmdArgCtx(args.toVector, s))

    def ptargrep(args: Any*) =
      SimpleCommand("ptargrep", CmdArgCtx(args.toVector, s))

    def ptx(args: Any*) =
      SimpleCommand("ptx", CmdArgCtx(args.toVector, s))

    def pulseaudio(args: Any*) =
      SimpleCommand("pulseaudio", CmdArgCtx(args.toVector, s))

    def pvchange(args: Any*) =
      SimpleCommand("pvchange", CmdArgCtx(args.toVector, s))

    def pvck(args: Any*) =
      SimpleCommand("pvck", CmdArgCtx(args.toVector, s))

    def pvcreate(args: Any*) =
      SimpleCommand("pvcreate", CmdArgCtx(args.toVector, s))

    def pvdisplay(args: Any*) =
      SimpleCommand("pvdisplay", CmdArgCtx(args.toVector, s))

    def pvmove(args: Any*) =
      SimpleCommand("pvmove", CmdArgCtx(args.toVector, s))

    def pvremove(args: Any*) =
      SimpleCommand("pvremove", CmdArgCtx(args.toVector, s))

    def pvresize(args: Any*) =
      SimpleCommand("pvresize", CmdArgCtx(args.toVector, s))

    def pvs(args: Any*) =
      SimpleCommand("pvs", CmdArgCtx(args.toVector, s))

    def pvscan(args: Any*) =
      SimpleCommand("pvscan", CmdArgCtx(args.toVector, s))

    def pwck(args: Any*) =
      SimpleCommand("pwck", CmdArgCtx(args.toVector, s))

    def pwconv(args: Any*) =
      SimpleCommand("pwconv", CmdArgCtx(args.toVector, s))

    def pwd(args: Any*) =
      SimpleCommand("pwd", CmdArgCtx(args.toVector, s))

    def pwdx(args: Any*) =
      SimpleCommand("pwdx", CmdArgCtx(args.toVector, s))

    def pwunconv(args: Any*) =
      SimpleCommand("pwunconv", CmdArgCtx(args.toVector, s))

    def py(args: Any*) =
      SimpleCommand("py", CmdArgCtx(args.toVector, s))

    def qpaeq(args: Any*) =
      SimpleCommand("qpaeq", CmdArgCtx(args.toVector, s))

    def qvlc(args: Any*) =
      SimpleCommand("qvlc", CmdArgCtx(args.toVector, s))

    def randomBed(args: Any*) =
      SimpleCommand("randomBed", CmdArgCtx(args.toVector, s))

    def rarp(args: Any*) =
      SimpleCommand("rarp", CmdArgCtx(args.toVector, s))

    def rarpd(args: Any*) =
      SimpleCommand("rarpd", CmdArgCtx(args.toVector, s))

    def raw(args: Any*) =
      SimpleCommand("raw", CmdArgCtx(args.toVector, s))

    def rdisc(args: Any*) =
      SimpleCommand("rdisc", CmdArgCtx(args.toVector, s))

    def read(args: Any*) =
      SimpleCommand("read", CmdArgCtx(args.toVector, s))

    def readarray(args: Any*) =
      SimpleCommand("readarray", CmdArgCtx(args.toVector, s))

    def readlink(args: Any*) =
      SimpleCommand("readlink", CmdArgCtx(args.toVector, s))

    def readprofile(args: Any*) =
      SimpleCommand("readprofile", CmdArgCtx(args.toVector, s))

    def realpath(args: Any*) =
      SimpleCommand("realpath", CmdArgCtx(args.toVector, s))

    def rebaseextract(args: Any*) =
      SimpleCommand("rebaseextract", CmdArgCtx(args.toVector, s))

    def reboot(args: Any*) =
      SimpleCommand("reboot", CmdArgCtx(args.toVector, s))

    def recoder(args: Any*) =
      SimpleCommand("recoder", CmdArgCtx(args.toVector, s))

    def redata(args: Any*) =
      SimpleCommand("redata", CmdArgCtx(args.toVector, s))

    def redshift(args: Any*) =
      SimpleCommand("redshift", CmdArgCtx(args.toVector, s))

    def redshift_gtk(args: Any*) =
      SimpleCommand("redshift_gtk", CmdArgCtx(args.toVector, s))

    def refseqget(args: Any*) =
      SimpleCommand("refseqget", CmdArgCtx(args.toVector, s))

    def regdbdump(args: Any*) =
      SimpleCommand("regdbdump", CmdArgCtx(args.toVector, s))

    def reject(args: Any*) =
      SimpleCommand("reject", CmdArgCtx(args.toVector, s))

    def remap(args: Any*) =
      SimpleCommand("remap", CmdArgCtx(args.toVector, s))

    def rename(args: Any*) =
      SimpleCommand("rename", CmdArgCtx(args.toVector, s))

    def renice(args: Any*) =
      SimpleCommand("renice", CmdArgCtx(args.toVector, s))

    def reset(args: Any*) =
      SimpleCommand("reset", CmdArgCtx(args.toVector, s))

    def resize(args: Any*) =
      SimpleCommand("resize", CmdArgCtx(args.toVector, s))

    def resize2fs(args: Any*) =
      SimpleCommand("resize2fs", CmdArgCtx(args.toVector, s))

    def resizecons(args: Any*) =
      SimpleCommand("resizecons", CmdArgCtx(args.toVector, s))

    def resizepart(args: Any*) =
      SimpleCommand("resizepart", CmdArgCtx(args.toVector, s))

    def resolvconf(args: Any*) =
      SimpleCommand("resolvconf", CmdArgCtx(args.toVector, s))

    def resolvectl(args: Any*) =
      SimpleCommand("resolvectl", CmdArgCtx(args.toVector, s))

    def restover(args: Any*) =
      SimpleCommand("restover", CmdArgCtx(args.toVector, s))

    def restrict(args: Any*) =
      SimpleCommand("restrict", CmdArgCtx(args.toVector, s))

    def rev(args: Any*) =
      SimpleCommand("rev", CmdArgCtx(args.toVector, s))

    def revseq(args: Any*) =
      SimpleCommand("revseq", CmdArgCtx(args.toVector, s))

    def rfkill(args: Any*) =
      SimpleCommand("rfkill", CmdArgCtx(args.toVector, s))

    def rm(args: Any*) =
      SimpleCommand("rm", CmdArgCtx(args.toVector, s))

    def rmdir(args: Any*) =
      SimpleCommand("rmdir", CmdArgCtx(args.toVector, s))

    def rmmod(args: Any*) =
      SimpleCommand("rmmod", CmdArgCtx(args.toVector, s))

    def rnano(args: Any*) =
      SimpleCommand("rnano", CmdArgCtx(args.toVector, s))

    def route(args: Any*) =
      SimpleCommand("route", CmdArgCtx(args.toVector, s))

    def routef(args: Any*) =
      SimpleCommand("routef", CmdArgCtx(args.toVector, s))

    def routel(args: Any*) =
      SimpleCommand("routel", CmdArgCtx(args.toVector, s))

    def rpcgen(args: Any*) =
      SimpleCommand("rpcgen", CmdArgCtx(args.toVector, s))

    def rsvg_convert(args: Any*) =
      SimpleCommand("rsvg_convert", CmdArgCtx(args.toVector, s))

    def rsync(args: Any*) =
      SimpleCommand("rsync", CmdArgCtx(args.toVector, s))

    def rtacct(args: Any*) =
      SimpleCommand("rtacct", CmdArgCtx(args.toVector, s))

    def rtcwake(args: Any*) =
      SimpleCommand("rtcwake", CmdArgCtx(args.toVector, s))

    def rtkitctl(args: Any*) =
      SimpleCommand("rtkitctl", CmdArgCtx(args.toVector, s))

    def rtmon(args: Any*) =
      SimpleCommand("rtmon", CmdArgCtx(args.toVector, s))

    def rtpr(args: Any*) =
      SimpleCommand("rtpr", CmdArgCtx(args.toVector, s))

    def rtstat(args: Any*) =
      SimpleCommand("rtstat", CmdArgCtx(args.toVector, s))

    def run_singularity(args: Any*) =
      SimpleCommand("run_singularity", CmdArgCtx(args.toVector, s))

    def runcon(args: Any*) =
      SimpleCommand("runcon", CmdArgCtx(args.toVector, s))

    def runlevel(args: Any*) =
      SimpleCommand("runlevel", CmdArgCtx(args.toVector, s))

    def runuser(args: Any*) =
      SimpleCommand("runuser", CmdArgCtx(args.toVector, s))

    def rvlc(args: Any*) =
      SimpleCommand("rvlc", CmdArgCtx(args.toVector, s))

    def samtools(args: Any*) =
      SimpleCommand("samtools", CmdArgCtx(args.toVector, s))

    def sbase(args: Any*) =
      SimpleCommand("sbase", CmdArgCtx(args.toVector, s))

    def sbt(args: Any*) =
      SimpleCommand("sbt", CmdArgCtx(args.toVector, s))

    def scala(args: Any*) =
      SimpleCommand("scala", CmdArgCtx(args.toVector, s))

    def scalc(args: Any*) =
      SimpleCommand("scalc", CmdArgCtx(args.toVector, s))

    def sccmap(args: Any*) =
      SimpleCommand("sccmap", CmdArgCtx(args.toVector, s))

    def sclient(args: Any*) =
      SimpleCommand("sclient", CmdArgCtx(args.toVector, s))

    def scp(args: Any*) =
      SimpleCommand("scp", CmdArgCtx(args.toVector, s))

    def scp_dbus_service(args: Any*) =
      SimpleCommand("scp_dbus_service", CmdArgCtx(args.toVector, s))

    def screendump(args: Any*) =
      SimpleCommand("screendump", CmdArgCtx(args.toVector, s))

    def script(args: Any*) =
      SimpleCommand("script", CmdArgCtx(args.toVector, s))

    def scriptreplay(args: Any*) =
      SimpleCommand("scriptreplay", CmdArgCtx(args.toVector, s))

    def sddm(args: Any*) =
      SimpleCommand("sddm", CmdArgCtx(args.toVector, s))

    def sddm_greeter(args: Any*) =
      SimpleCommand("sddm_greeter", CmdArgCtx(args.toVector, s))

    def sdiff(args: Any*) =
      SimpleCommand("sdiff", CmdArgCtx(args.toVector, s))

    def sdraw(args: Any*) =
      SimpleCommand("sdraw", CmdArgCtx(args.toVector, s))

    def secret_tool(args: Any*) =
      SimpleCommand("secret_tool", CmdArgCtx(args.toVector, s))

    def sed(args: Any*) =
      SimpleCommand("sed", CmdArgCtx(args.toVector, s))

    def seealso(args: Any*) =
      SimpleCommand("seealso", CmdArgCtx(args.toVector, s))

    def seq(args: Any*) =
      SimpleCommand("seq", CmdArgCtx(args.toVector, s))

    def seqcount(args: Any*) =
      SimpleCommand("seqcount", CmdArgCtx(args.toVector, s))

    def seqmatchall(args: Any*) =
      SimpleCommand("seqmatchall", CmdArgCtx(args.toVector, s))

    def seqret(args: Any*) =
      SimpleCommand("seqret", CmdArgCtx(args.toVector, s))

    def seqretsetall(args: Any*) =
      SimpleCommand("seqretsetall", CmdArgCtx(args.toVector, s))

    def seqretsplit(args: Any*) =
      SimpleCommand("seqretsplit", CmdArgCtx(args.toVector, s))

    def seqxref(args: Any*) =
      SimpleCommand("seqxref", CmdArgCtx(args.toVector, s))

    def seqxrefget(args: Any*) =
      SimpleCommand("seqxrefget", CmdArgCtx(args.toVector, s))

    def servertell(args: Any*) =
      SimpleCommand("servertell", CmdArgCtx(args.toVector, s))

    def servicemenuinstaller(args: Any*) =
      SimpleCommand("servicemenuinstaller", CmdArgCtx(args.toVector, s))

    def setarch(args: Any*) =
      SimpleCommand("setarch", CmdArgCtx(args.toVector, s))

    def setcap(args: Any*) =
      SimpleCommand("setcap", CmdArgCtx(args.toVector, s))

    def setfacl(args: Any*) =
      SimpleCommand("setfacl", CmdArgCtx(args.toVector, s))

    def setfattr(args: Any*) =
      SimpleCommand("setfattr", CmdArgCtx(args.toVector, s))

    def setfont(args: Any*) =
      SimpleCommand("setfont", CmdArgCtx(args.toVector, s))

    def setkeycodes(args: Any*) =
      SimpleCommand("setkeycodes", CmdArgCtx(args.toVector, s))

    def setleds(args: Any*) =
      SimpleCommand("setleds", CmdArgCtx(args.toVector, s))

    def setlogcons(args: Any*) =
      SimpleCommand("setlogcons", CmdArgCtx(args.toVector, s))

    def setmetamode(args: Any*) =
      SimpleCommand("setmetamode", CmdArgCtx(args.toVector, s))

    def setpalette(args: Any*) =
      SimpleCommand("setpalette", CmdArgCtx(args.toVector, s))

    def setsid(args: Any*) =
      SimpleCommand("setsid", CmdArgCtx(args.toVector, s))

    def setterm(args: Any*) =
      SimpleCommand("setterm", CmdArgCtx(args.toVector, s))

    def setvesablank(args: Any*) =
      SimpleCommand("setvesablank", CmdArgCtx(args.toVector, s))

    def setvtrgb(args: Any*) =
      SimpleCommand("setvtrgb", CmdArgCtx(args.toVector, s))

    def setxkbmap(args: Any*) =
      SimpleCommand("setxkbmap", CmdArgCtx(args.toVector, s))

    def sfdisk(args: Any*) =
      SimpleCommand("sfdisk", CmdArgCtx(args.toVector, s))

    def sfdp(args: Any*) =
      SimpleCommand("sfdp", CmdArgCtx(args.toVector, s))

    def sftp(args: Any*) =
      SimpleCommand("sftp", CmdArgCtx(args.toVector, s))

    def sg(args: Any*) =
      SimpleCommand("sg", CmdArgCtx(args.toVector, s))

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

    def shasum(args: Any*) =
      SimpleCommand("shasum", CmdArgCtx(args.toVector, s))

    def shellcheck(args: Any*) =
      SimpleCommand("shellcheck", CmdArgCtx(args.toVector, s))

    def shfmt(args: Any*) =
      SimpleCommand("shfmt", CmdArgCtx(args.toVector, s))

    def shiftBed(args: Any*) =
      SimpleCommand("shiftBed", CmdArgCtx(args.toVector, s))

    def showalign(args: Any*) =
      SimpleCommand("showalign", CmdArgCtx(args.toVector, s))

    def showconsolefont(args: Any*) =
      SimpleCommand("showconsolefont", CmdArgCtx(args.toVector, s))

    def showdb(args: Any*) =
      SimpleCommand("showdb", CmdArgCtx(args.toVector, s))

    def showfeat(args: Any*) =
      SimpleCommand("showfeat", CmdArgCtx(args.toVector, s))

    def showkey(args: Any*) =
      SimpleCommand("showkey", CmdArgCtx(args.toVector, s))

    def showorf(args: Any*) =
      SimpleCommand("showorf", CmdArgCtx(args.toVector, s))

    def showpep(args: Any*) =
      SimpleCommand("showpep", CmdArgCtx(args.toVector, s))

    def showseq(args: Any*) =
      SimpleCommand("showseq", CmdArgCtx(args.toVector, s))

    def showserver(args: Any*) =
      SimpleCommand("showserver", CmdArgCtx(args.toVector, s))

    def shred(args: Any*) =
      SimpleCommand("shred", CmdArgCtx(args.toVector, s))

    def shuf(args: Any*) =
      SimpleCommand("shuf", CmdArgCtx(args.toVector, s))

    def shuffleBed(args: Any*) =
      SimpleCommand("shuffleBed", CmdArgCtx(args.toVector, s))

    def shuffleseq(args: Any*) =
      SimpleCommand("shuffleseq", CmdArgCtx(args.toVector, s))

    def shutdown(args: Any*) =
      SimpleCommand("shutdown", CmdArgCtx(args.toVector, s))

    def sigcleave(args: Any*) =
      SimpleCommand("sigcleave", CmdArgCtx(args.toVector, s))

    def silent(args: Any*) =
      SimpleCommand("silent", CmdArgCtx(args.toVector, s))

    def sim_client(args: Any*) =
      SimpleCommand("sim_client", CmdArgCtx(args.toVector, s))

    def sim_server(args: Any*) =
      SimpleCommand("sim_server", CmdArgCtx(args.toVector, s))

    def simpress(args: Any*) =
      SimpleCommand("simpress", CmdArgCtx(args.toVector, s))

    def singularity(args: Any*) =
      SimpleCommand("singularity", CmdArgCtx(args.toVector, s))

    def sirna(args: Any*) =
      SimpleCommand("sirna", CmdArgCtx(args.toVector, s))

    def sixpack(args: Any*) =
      SimpleCommand("sixpack", CmdArgCtx(args.toVector, s))

    def sizeseq(args: Any*) =
      SimpleCommand("sizeseq", CmdArgCtx(args.toVector, s))

    def skipredundant(args: Any*) =
      SimpleCommand("skipredundant", CmdArgCtx(args.toVector, s))

    def skipseq(args: Any*) =
      SimpleCommand("skipseq", CmdArgCtx(args.toVector, s))

    def slabtop(args: Any*) =
      SimpleCommand("slabtop", CmdArgCtx(args.toVector, s))

    def slack(args: Any*) =
      SimpleCommand("slack", CmdArgCtx(args.toVector, s))

    def slattach(args: Any*) =
      SimpleCommand("slattach", CmdArgCtx(args.toVector, s))

    def sleep(args: Any*) =
      SimpleCommand("sleep", CmdArgCtx(args.toVector, s))

    def sln(args: Any*) =
      SimpleCommand("sln", CmdArgCtx(args.toVector, s))

    def slopBed(args: Any*) =
      SimpleCommand("slopBed", CmdArgCtx(args.toVector, s))

    def smath(args: Any*) =
      SimpleCommand("smath", CmdArgCtx(args.toVector, s))

    def smbinfo(args: Any*) =
      SimpleCommand("smbinfo", CmdArgCtx(args.toVector, s))

    def snpeff(args: Any*) =
      SimpleCommand("snpeff", CmdArgCtx(args.toVector, s))

    def snpsift(args: Any*) =
      SimpleCommand("snpsift", CmdArgCtx(args.toVector, s))

    def soffice(args: Any*) =
      SimpleCommand("soffice", CmdArgCtx(args.toVector, s))

    def solid_action_desktop_gen(args: Any*) =
      SimpleCommand("solid_action_desktop_gen", CmdArgCtx(args.toVector, s))

    def solid_hardware5(args: Any*) =
      SimpleCommand("solid_hardware5", CmdArgCtx(args.toVector, s))

    def sort(args: Any*) =
      SimpleCommand("sort", CmdArgCtx(args.toVector, s))

    def sortBed(args: Any*) =
      SimpleCommand("sortBed", CmdArgCtx(args.toVector, s))

    def sotruss(args: Any*) =
      SimpleCommand("sotruss", CmdArgCtx(args.toVector, s))

    def spawn_console(args: Any*) =
      SimpleCommand("spawn_console", CmdArgCtx(args.toVector, s))

    def spawn_login(args: Any*) =
      SimpleCommand("spawn_login", CmdArgCtx(args.toVector, s))

    def speaker_test(args: Any*) =
      SimpleCommand("speaker_test", CmdArgCtx(args.toVector, s))

    def spectacle(args: Any*) =
      SimpleCommand("spectacle", CmdArgCtx(args.toVector, s))

    def splain(args: Any*) =
      SimpleCommand("splain", CmdArgCtx(args.toVector, s))

    def split(args: Any*) =
      SimpleCommand("split", CmdArgCtx(args.toVector, s))

    def splitsource(args: Any*) =
      SimpleCommand("splitsource", CmdArgCtx(args.toVector, s))

    def splitter(args: Any*) =
      SimpleCommand("splitter", CmdArgCtx(args.toVector, s))

    def spotify(args: Any*) =
      SimpleCommand("spotify", CmdArgCtx(args.toVector, s))

    def sprof(args: Any*) =
      SimpleCommand("sprof", CmdArgCtx(args.toVector, s))

    def ss(args: Any*) =
      SimpleCommand("ss", CmdArgCtx(args.toVector, s))

    def sserver(args: Any*) =
      SimpleCommand("sserver", CmdArgCtx(args.toVector, s))

    def ssh(args: Any*) =
      SimpleCommand("ssh", CmdArgCtx(args.toVector, s))

    def ssh_add(args: Any*) =
      SimpleCommand("ssh_add", CmdArgCtx(args.toVector, s))

    def ssh_agent(args: Any*) =
      SimpleCommand("ssh_agent", CmdArgCtx(args.toVector, s))

    def ssh_copy_id(args: Any*) =
      SimpleCommand("ssh_copy_id", CmdArgCtx(args.toVector, s))

    def ssh_keygen(args: Any*) =
      SimpleCommand("ssh_keygen", CmdArgCtx(args.toVector, s))

    def ssh_keyscan(args: Any*) =
      SimpleCommand("ssh_keyscan", CmdArgCtx(args.toVector, s))

    def sshd(args: Any*) =
      SimpleCommand("sshd", CmdArgCtx(args.toVector, s))

    def startkde(args: Any*) =
      SimpleCommand("startkde", CmdArgCtx(args.toVector, s))

    def startplasmacompositor(args: Any*) =
      SimpleCommand("startplasmacompositor", CmdArgCtx(args.toVector, s))

    def stat(args: Any*) =
      SimpleCommand("stat", CmdArgCtx(args.toVector, s))

    def stdbuf(args: Any*) =
      SimpleCommand("stdbuf", CmdArgCtx(args.toVector, s))

    def strace(args: Any*) =
      SimpleCommand("strace", CmdArgCtx(args.toVector, s))

    def strace_graph(args: Any*) =
      SimpleCommand("strace_graph", CmdArgCtx(args.toVector, s))

    def strace_log_merge(args: Any*) =
      SimpleCommand("strace_log_merge", CmdArgCtx(args.toVector, s))

    def stream(args: Any*) =
      SimpleCommand("stream", CmdArgCtx(args.toVector, s))

    def stretcher(args: Any*) =
      SimpleCommand("stretcher", CmdArgCtx(args.toVector, s))

    def stssearch(args: Any*) =
      SimpleCommand("stssearch", CmdArgCtx(args.toVector, s))

    def stty(args: Any*) =
      SimpleCommand("stty", CmdArgCtx(args.toVector, s))

    def su(args: Any*) =
      SimpleCommand("su", CmdArgCtx(args.toVector, s))

    def subtractBed(args: Any*) =
      SimpleCommand("subtractBed", CmdArgCtx(args.toVector, s))

    def sudo(args: Any*) =
      SimpleCommand("sudo", CmdArgCtx(args.toVector, s))

    def sudoedit(args: Any*) =
      SimpleCommand("sudoedit", CmdArgCtx(args.toVector, s))

    def sudoreplay(args: Any*) =
      SimpleCommand("sudoreplay", CmdArgCtx(args.toVector, s))

    def sulogin(args: Any*) =
      SimpleCommand("sulogin", CmdArgCtx(args.toVector, s))

    def sum(args: Any*) =
      SimpleCommand("sum", CmdArgCtx(args.toVector, s))

    def supermatcher(args: Any*) =
      SimpleCommand("supermatcher", CmdArgCtx(args.toVector, s))

    def svn(args: Any*) =
      SimpleCommand("svn", CmdArgCtx(args.toVector, s))

    def svnadmin(args: Any*) =
      SimpleCommand("svnadmin", CmdArgCtx(args.toVector, s))

    def svnbench(args: Any*) =
      SimpleCommand("svnbench", CmdArgCtx(args.toVector, s))

    def svndumpfilter(args: Any*) =
      SimpleCommand("svndumpfilter", CmdArgCtx(args.toVector, s))

    def svnfsfs(args: Any*) =
      SimpleCommand("svnfsfs", CmdArgCtx(args.toVector, s))

    def svnlook(args: Any*) =
      SimpleCommand("svnlook", CmdArgCtx(args.toVector, s))

    def svnmucc(args: Any*) =
      SimpleCommand("svnmucc", CmdArgCtx(args.toVector, s))

    def svnrdump(args: Any*) =
      SimpleCommand("svnrdump", CmdArgCtx(args.toVector, s))

    def svnserve(args: Any*) =
      SimpleCommand("svnserve", CmdArgCtx(args.toVector, s))

    def svnsync(args: Any*) =
      SimpleCommand("svnsync", CmdArgCtx(args.toVector, s))

    def svnversion(args: Any*) =
      SimpleCommand("svnversion", CmdArgCtx(args.toVector, s))

    def swaplabel(args: Any*) =
      SimpleCommand("swaplabel", CmdArgCtx(args.toVector, s))

    def swapoff(args: Any*) =
      SimpleCommand("swapoff", CmdArgCtx(args.toVector, s))

    def swapon(args: Any*) =
      SimpleCommand("swapon", CmdArgCtx(args.toVector, s))

    def switch_root(args: Any*) =
      SimpleCommand("switch_root", CmdArgCtx(args.toVector, s))

    def swriter(args: Any*) =
      SimpleCommand("swriter", CmdArgCtx(args.toVector, s))

    def syco(args: Any*) =
      SimpleCommand("syco", CmdArgCtx(args.toVector, s))

    def sync(args: Any*) =
      SimpleCommand("sync", CmdArgCtx(args.toVector, s))

    def sysctl(args: Any*) =
      SimpleCommand("sysctl", CmdArgCtx(args.toVector, s))

    def system_config_printer(args: Any*) =
      SimpleCommand("system_config_printer", CmdArgCtx(args.toVector, s))

    def system_config_printer_applet(args: Any*) =
      SimpleCommand("system_config_printer_applet", CmdArgCtx(args.toVector, s))

    def systemctl(args: Any*) =
      SimpleCommand("systemctl", CmdArgCtx(args.toVector, s))

    def systemd_analyze(args: Any*) =
      SimpleCommand("systemd_analyze", CmdArgCtx(args.toVector, s))

    def systemd_ask_password(args: Any*) =
      SimpleCommand("systemd_ask_password", CmdArgCtx(args.toVector, s))

    def systemd_cat(args: Any*) =
      SimpleCommand("systemd_cat", CmdArgCtx(args.toVector, s))

    def systemd_cgls(args: Any*) =
      SimpleCommand("systemd_cgls", CmdArgCtx(args.toVector, s))

    def systemd_cgtop(args: Any*) =
      SimpleCommand("systemd_cgtop", CmdArgCtx(args.toVector, s))

    def systemd_delta(args: Any*) =
      SimpleCommand("systemd_delta", CmdArgCtx(args.toVector, s))

    def systemd_detect_virt(args: Any*) =
      SimpleCommand("systemd_detect_virt", CmdArgCtx(args.toVector, s))

    def systemd_escape(args: Any*) =
      SimpleCommand("systemd_escape", CmdArgCtx(args.toVector, s))

    def systemd_hwdb(args: Any*) =
      SimpleCommand("systemd_hwdb", CmdArgCtx(args.toVector, s))

    def systemd_id128(args: Any*) =
      SimpleCommand("systemd_id128", CmdArgCtx(args.toVector, s))

    def systemd_inhibit(args: Any*) =
      SimpleCommand("systemd_inhibit", CmdArgCtx(args.toVector, s))

    def systemd_machine_id_setup(args: Any*) =
      SimpleCommand("systemd_machine_id_setup", CmdArgCtx(args.toVector, s))

    def systemd_mount(args: Any*) =
      SimpleCommand("systemd_mount", CmdArgCtx(args.toVector, s))

    def systemd_notify(args: Any*) =
      SimpleCommand("systemd_notify", CmdArgCtx(args.toVector, s))

    def systemd_nspawn(args: Any*) =
      SimpleCommand("systemd_nspawn", CmdArgCtx(args.toVector, s))

    def systemd_path(args: Any*) =
      SimpleCommand("systemd_path", CmdArgCtx(args.toVector, s))

    def systemd_resolve(args: Any*) =
      SimpleCommand("systemd_resolve", CmdArgCtx(args.toVector, s))

    def systemd_run(args: Any*) =
      SimpleCommand("systemd_run", CmdArgCtx(args.toVector, s))

    def systemd_socket_activate(args: Any*) =
      SimpleCommand("systemd_socket_activate", CmdArgCtx(args.toVector, s))

    def systemd_stdio_bridge(args: Any*) =
      SimpleCommand("systemd_stdio_bridge", CmdArgCtx(args.toVector, s))

    def systemd_tmpfiles(args: Any*) =
      SimpleCommand("systemd_tmpfiles", CmdArgCtx(args.toVector, s))

    def systemd_tty_ask_password_agent(args: Any*) =
      SimpleCommand(
        "systemd_tty_ask_password_agent",
        CmdArgCtx(args.toVector, s)
      )

    def systemd_umount(args: Any*) =
      SimpleCommand("systemd_umount", CmdArgCtx(args.toVector, s))

    def systemmonitor(args: Any*) =
      SimpleCommand("systemmonitor", CmdArgCtx(args.toVector, s))

    def systemsettings5(args: Any*) =
      SimpleCommand("systemsettings5", CmdArgCtx(args.toVector, s))

    def tabs(args: Any*) =
      SimpleCommand("tabs", CmdArgCtx(args.toVector, s))

    def tac(args: Any*) =
      SimpleCommand("tac", CmdArgCtx(args.toVector, s))

    def tagBam(args: Any*) =
      SimpleCommand("tagBam", CmdArgCtx(args.toVector, s))

    def tail(args: Any*) =
      SimpleCommand("tail", CmdArgCtx(args.toVector, s))

    def tar(args: Any*) =
      SimpleCommand("tar", CmdArgCtx(args.toVector, s))

    def taskset(args: Any*) =
      SimpleCommand("taskset", CmdArgCtx(args.toVector, s))

    def taxget(args: Any*) =
      SimpleCommand("taxget", CmdArgCtx(args.toVector, s))

    def taxgetdown(args: Any*) =
      SimpleCommand("taxgetdown", CmdArgCtx(args.toVector, s))

    def taxgetrank(args: Any*) =
      SimpleCommand("taxgetrank", CmdArgCtx(args.toVector, s))

    def taxgetspecies(args: Any*) =
      SimpleCommand("taxgetspecies", CmdArgCtx(args.toVector, s))

    def taxgetup(args: Any*) =
      SimpleCommand("taxgetup", CmdArgCtx(args.toVector, s))

    def tc(args: Any*) =
      SimpleCommand("tc", CmdArgCtx(args.toVector, s))

    def tcode(args: Any*) =
      SimpleCommand("tcode", CmdArgCtx(args.toVector, s))

    def tee(args: Any*) =
      SimpleCommand("tee", CmdArgCtx(args.toVector, s))

    def telinit(args: Any*) =
      SimpleCommand("telinit", CmdArgCtx(args.toVector, s))

    def test(args: Any*) =
      SimpleCommand("test", CmdArgCtx(args.toVector, s))

    def texi2any(args: Any*) =
      SimpleCommand("texi2any", CmdArgCtx(args.toVector, s))

    def texi2dvi(args: Any*) =
      SimpleCommand("texi2dvi", CmdArgCtx(args.toVector, s))

    def texi2pdf(args: Any*) =
      SimpleCommand("texi2pdf", CmdArgCtx(args.toVector, s))

    def texindex(args: Any*) =
      SimpleCommand("texindex", CmdArgCtx(args.toVector, s))

    def textget(args: Any*) =
      SimpleCommand("textget", CmdArgCtx(args.toVector, s))

    def textsearch(args: Any*) =
      SimpleCommand("textsearch", CmdArgCtx(args.toVector, s))

    def tfextract(args: Any*) =
      SimpleCommand("tfextract", CmdArgCtx(args.toVector, s))

    def tfm(args: Any*) =
      SimpleCommand("tfm", CmdArgCtx(args.toVector, s))

    def tfscan(args: Any*) =
      SimpleCommand("tfscan", CmdArgCtx(args.toVector, s))

    def tftpd(args: Any*) =
      SimpleCommand("tftpd", CmdArgCtx(args.toVector, s))

    def tic(args: Any*) =
      SimpleCommand("tic", CmdArgCtx(args.toVector, s))

    def time(args: Any*) =
      SimpleCommand("time", CmdArgCtx(args.toVector, s))

    def timedatectl(args: Any*) =
      SimpleCommand("timedatectl", CmdArgCtx(args.toVector, s))

    def timeout(args: Any*) =
      SimpleCommand("timeout", CmdArgCtx(args.toVector, s))

    def tload(args: Any*) =
      SimpleCommand("tload", CmdArgCtx(args.toVector, s))

    def tmap(args: Any*) =
      SimpleCommand("tmap", CmdArgCtx(args.toVector, s))

    def top(args: Any*) =
      SimpleCommand("top", CmdArgCtx(args.toVector, s))

    def touch(args: Any*) =
      SimpleCommand("touch", CmdArgCtx(args.toVector, s))

    def tput(args: Any*) =
      SimpleCommand("tput", CmdArgCtx(args.toVector, s))

    def tr(args: Any*) =
      SimpleCommand("tr", CmdArgCtx(args.toVector, s))

    def tracepath(args: Any*) =
      SimpleCommand("tracepath", CmdArgCtx(args.toVector, s))

    def traceroute6(args: Any*) =
      SimpleCommand("traceroute6", CmdArgCtx(args.toVector, s))

    def tranalign(args: Any*) =
      SimpleCommand("tranalign", CmdArgCtx(args.toVector, s))

    def transeq(args: Any*) =
      SimpleCommand("transeq", CmdArgCtx(args.toVector, s))

    def tred(args: Any*) =
      SimpleCommand("tred", CmdArgCtx(args.toVector, s))

    def tree(args: Any*) =
      SimpleCommand("tree", CmdArgCtx(args.toVector, s))

    def trimest(args: Any*) =
      SimpleCommand("trimest", CmdArgCtx(args.toVector, s))

    def trimseq(args: Any*) =
      SimpleCommand("trimseq", CmdArgCtx(args.toVector, s))

    def trimspace(args: Any*) =
      SimpleCommand("trimspace", CmdArgCtx(args.toVector, s))

    def truncate(args: Any*) =
      SimpleCommand("truncate", CmdArgCtx(args.toVector, s))

    def tset(args: Any*) =
      SimpleCommand("tset", CmdArgCtx(args.toVector, s))

    def tsort(args: Any*) =
      SimpleCommand("tsort", CmdArgCtx(args.toVector, s))

    def tty(args: Any*) =
      SimpleCommand("tty", CmdArgCtx(args.toVector, s))

    def tune2fs(args: Any*) =
      SimpleCommand("tune2fs", CmdArgCtx(args.toVector, s))

    def twofeat(args: Any*) =
      SimpleCommand("twofeat", CmdArgCtx(args.toVector, s))

    def twopi(args: Any*) =
      SimpleCommand("twopi", CmdArgCtx(args.toVector, s))

    def typeset(args: Any*) =
      SimpleCommand("typeset", CmdArgCtx(args.toVector, s))

    def tzselect(args: Any*) =
      SimpleCommand("tzselect", CmdArgCtx(args.toVector, s))

    def udevadm(args: Any*) =
      SimpleCommand("udevadm", CmdArgCtx(args.toVector, s))

    def udisksctl(args: Any*) =
      SimpleCommand("udisksctl", CmdArgCtx(args.toVector, s))

    def ul(args: Any*) =
      SimpleCommand("ul", CmdArgCtx(args.toVector, s))

    def ulimit(args: Any*) =
      SimpleCommand("ulimit", CmdArgCtx(args.toVector, s))

    def ulockmgr_server(args: Any*) =
      SimpleCommand("ulockmgr_server", CmdArgCtx(args.toVector, s))

    def umount(args: Any*) =
      SimpleCommand("umount", CmdArgCtx(args.toVector, s))

    def unalias(args: Any*) =
      SimpleCommand("unalias", CmdArgCtx(args.toVector, s))

    def uname(args: Any*) =
      SimpleCommand("uname", CmdArgCtx(args.toVector, s))

    def uname26(args: Any*) =
      SimpleCommand("uname26", CmdArgCtx(args.toVector, s))

    def uncompress(args: Any*) =
      SimpleCommand("uncompress", CmdArgCtx(args.toVector, s))

    def unexpand(args: Any*) =
      SimpleCommand("unexpand", CmdArgCtx(args.toVector, s))

    def unflatten(args: Any*) =
      SimpleCommand("unflatten", CmdArgCtx(args.toVector, s))

    def unicode_start(args: Any*) =
      SimpleCommand("unicode_start", CmdArgCtx(args.toVector, s))

    def unicode_stop(args: Any*) =
      SimpleCommand("unicode_stop", CmdArgCtx(args.toVector, s))

    def union(args: Any*) =
      SimpleCommand("union", CmdArgCtx(args.toVector, s))

    def unionBedGraphs(args: Any*) =
      SimpleCommand("unionBedGraphs", CmdArgCtx(args.toVector, s))

    def uniq(args: Any*) =
      SimpleCommand("uniq", CmdArgCtx(args.toVector, s))

    def unix_chkpwd(args: Any*) =
      SimpleCommand("unix_chkpwd", CmdArgCtx(args.toVector, s))

    def unix_update(args: Any*) =
      SimpleCommand("unix_update", CmdArgCtx(args.toVector, s))

    def unlink(args: Any*) =
      SimpleCommand("unlink", CmdArgCtx(args.toVector, s))

    def unlzma(args: Any*) =
      SimpleCommand("unlzma", CmdArgCtx(args.toVector, s))

    def unshare(args: Any*) =
      SimpleCommand("unshare", CmdArgCtx(args.toVector, s))

    def unxz(args: Any*) =
      SimpleCommand("unxz", CmdArgCtx(args.toVector, s))

    def unzip(args: Any*) =
      SimpleCommand("unzip", CmdArgCtx(args.toVector, s))

    def unzipsfx(args: Any*) =
      SimpleCommand("unzipsfx", CmdArgCtx(args.toVector, s))

    def update_mime_database(args: Any*) =
      SimpleCommand("update_mime_database", CmdArgCtx(args.toVector, s))

    def updatedb(args: Any*) =
      SimpleCommand("updatedb", CmdArgCtx(args.toVector, s))

    def upower(args: Any*) =
      SimpleCommand("upower", CmdArgCtx(args.toVector, s))

    def uptime(args: Any*) =
      SimpleCommand("uptime", CmdArgCtx(args.toVector, s))

    def urlget(args: Any*) =
      SimpleCommand("urlget", CmdArgCtx(args.toVector, s))

    def useradd(args: Any*) =
      SimpleCommand("useradd", CmdArgCtx(args.toVector, s))

    def userdel(args: Any*) =
      SimpleCommand("userdel", CmdArgCtx(args.toVector, s))

    def usermod(args: Any*) =
      SimpleCommand("usermod", CmdArgCtx(args.toVector, s))

    def users(args: Any*) =
      SimpleCommand("users", CmdArgCtx(args.toVector, s))

    def utmpdump(args: Any*) =
      SimpleCommand("utmpdump", CmdArgCtx(args.toVector, s))

    def uuclient(args: Any*) =
      SimpleCommand("uuclient", CmdArgCtx(args.toVector, s))

    def uuidd(args: Any*) =
      SimpleCommand("uuidd", CmdArgCtx(args.toVector, s))

    def uuidgen(args: Any*) =
      SimpleCommand("uuidgen", CmdArgCtx(args.toVector, s))

    def uuidparse(args: Any*) =
      SimpleCommand("uuidparse", CmdArgCtx(args.toVector, s))

    def uuserver(args: Any*) =
      SimpleCommand("uuserver", CmdArgCtx(args.toVector, s))

    def uxterm(args: Any*) =
      SimpleCommand("uxterm", CmdArgCtx(args.toVector, s))

    def variationget(args: Any*) =
      SimpleCommand("variationget", CmdArgCtx(args.toVector, s))

    def varscan(args: Any*) =
      SimpleCommand("varscan", CmdArgCtx(args.toVector, s))

    def vcf_annotate(args: Any*) =
      SimpleCommand("vcf_annotate", CmdArgCtx(args.toVector, s))

    def vcf_compare(args: Any*) =
      SimpleCommand("vcf_compare", CmdArgCtx(args.toVector, s))

    def vcf_concat(args: Any*) =
      SimpleCommand("vcf_concat", CmdArgCtx(args.toVector, s))

    def vcf_consensus(args: Any*) =
      SimpleCommand("vcf_consensus", CmdArgCtx(args.toVector, s))

    def vcf_contrast(args: Any*) =
      SimpleCommand("vcf_contrast", CmdArgCtx(args.toVector, s))

    def vcf_convert(args: Any*) =
      SimpleCommand("vcf_convert", CmdArgCtx(args.toVector, s))

    def vcf_fix_newlines(args: Any*) =
      SimpleCommand("vcf_fix_newlines", CmdArgCtx(args.toVector, s))

    def vcf_fix_ploidy(args: Any*) =
      SimpleCommand("vcf_fix_ploidy", CmdArgCtx(args.toVector, s))

    def vcf_indel_stats(args: Any*) =
      SimpleCommand("vcf_indel_stats", CmdArgCtx(args.toVector, s))

    def vcf_isec(args: Any*) =
      SimpleCommand("vcf_isec", CmdArgCtx(args.toVector, s))

    def vcf_merge(args: Any*) =
      SimpleCommand("vcf_merge", CmdArgCtx(args.toVector, s))

    def vcf_phased_join(args: Any*) =
      SimpleCommand("vcf_phased_join", CmdArgCtx(args.toVector, s))

    def vcf_query(args: Any*) =
      SimpleCommand("vcf_query", CmdArgCtx(args.toVector, s))

    def vcf_shuffle_cols(args: Any*) =
      SimpleCommand("vcf_shuffle_cols", CmdArgCtx(args.toVector, s))

    def vcf_sort(args: Any*) =
      SimpleCommand("vcf_sort", CmdArgCtx(args.toVector, s))

    def vcf_stats(args: Any*) =
      SimpleCommand("vcf_stats", CmdArgCtx(args.toVector, s))

    def vcf_subset(args: Any*) =
      SimpleCommand("vcf_subset", CmdArgCtx(args.toVector, s))

    def vcf_to_tab(args: Any*) =
      SimpleCommand("vcf_to_tab", CmdArgCtx(args.toVector, s))

    def vcf_tstv(args: Any*) =
      SimpleCommand("vcf_tstv", CmdArgCtx(args.toVector, s))

    def vcf_validator(args: Any*) =
      SimpleCommand("vcf_validator", CmdArgCtx(args.toVector, s))

    def vcftools(args: Any*) =
      SimpleCommand("vcftools", CmdArgCtx(args.toVector, s))

    def vdir(args: Any*) =
      SimpleCommand("vdir", CmdArgCtx(args.toVector, s))

    def vectorstrip(args: Any*) =
      SimpleCommand("vectorstrip", CmdArgCtx(args.toVector, s))

    def veritysetup(args: Any*) =
      SimpleCommand("veritysetup", CmdArgCtx(args.toVector, s))

    def vgcfgbackup(args: Any*) =
      SimpleCommand("vgcfgbackup", CmdArgCtx(args.toVector, s))

    def vgcfgrestore(args: Any*) =
      SimpleCommand("vgcfgrestore", CmdArgCtx(args.toVector, s))

    def vgchange(args: Any*) =
      SimpleCommand("vgchange", CmdArgCtx(args.toVector, s))

    def vgck(args: Any*) =
      SimpleCommand("vgck", CmdArgCtx(args.toVector, s))

    def vgconvert(args: Any*) =
      SimpleCommand("vgconvert", CmdArgCtx(args.toVector, s))

    def vgcreate(args: Any*) =
      SimpleCommand("vgcreate", CmdArgCtx(args.toVector, s))

    def vgdisplay(args: Any*) =
      SimpleCommand("vgdisplay", CmdArgCtx(args.toVector, s))

    def vgexport(args: Any*) =
      SimpleCommand("vgexport", CmdArgCtx(args.toVector, s))

    def vgextend(args: Any*) =
      SimpleCommand("vgextend", CmdArgCtx(args.toVector, s))

    def vgimport(args: Any*) =
      SimpleCommand("vgimport", CmdArgCtx(args.toVector, s))

    def vgimportclone(args: Any*) =
      SimpleCommand("vgimportclone", CmdArgCtx(args.toVector, s))

    def vgmerge(args: Any*) =
      SimpleCommand("vgmerge", CmdArgCtx(args.toVector, s))

    def vgmknodes(args: Any*) =
      SimpleCommand("vgmknodes", CmdArgCtx(args.toVector, s))

    def vgreduce(args: Any*) =
      SimpleCommand("vgreduce", CmdArgCtx(args.toVector, s))

    def vgremove(args: Any*) =
      SimpleCommand("vgremove", CmdArgCtx(args.toVector, s))

    def vgrename(args: Any*) =
      SimpleCommand("vgrename", CmdArgCtx(args.toVector, s))

    def vgs(args: Any*) =
      SimpleCommand("vgs", CmdArgCtx(args.toVector, s))

    def vgscan(args: Any*) =
      SimpleCommand("vgscan", CmdArgCtx(args.toVector, s))

    def vgsplit(args: Any*) =
      SimpleCommand("vgsplit", CmdArgCtx(args.toVector, s))

    def vigr(args: Any*) =
      SimpleCommand("vigr", CmdArgCtx(args.toVector, s))

    def vimdot(args: Any*) =
      SimpleCommand("vimdot", CmdArgCtx(args.toVector, s))

    def vipw(args: Any*) =
      SimpleCommand("vipw", CmdArgCtx(args.toVector, s))

    def visudo(args: Any*) =
      SimpleCommand("visudo", CmdArgCtx(args.toVector, s))

    def vlc(args: Any*) =
      SimpleCommand("vlc", CmdArgCtx(args.toVector, s))

    def vlc_wrapper(args: Any*) =
      SimpleCommand("vlc_wrapper", CmdArgCtx(args.toVector, s))

    def vlock(args: Any*) =
      SimpleCommand("vlock", CmdArgCtx(args.toVector, s))

    def vmcore_dmesg(args: Any*) =
      SimpleCommand("vmcore_dmesg", CmdArgCtx(args.toVector, s))

    def vmstat(args: Any*) =
      SimpleCommand("vmstat", CmdArgCtx(args.toVector, s))

    def vpnc(args: Any*) =
      SimpleCommand("vpnc", CmdArgCtx(args.toVector, s))

    def vpnc_disconnect(args: Any*) =
      SimpleCommand("vpnc_disconnect", CmdArgCtx(args.toVector, s))

    def w(args: Any*) =
      SimpleCommand("w", CmdArgCtx(args.toVector, s))

    def wall(args: Any*) =
      SimpleCommand("wall", CmdArgCtx(args.toVector, s))

    def watch(args: Any*) =
      SimpleCommand("watch", CmdArgCtx(args.toVector, s))

    def watchgnupg(args: Any*) =
      SimpleCommand("watchgnupg", CmdArgCtx(args.toVector, s))

    def watchman(args: Any*) =
      SimpleCommand("watchman", CmdArgCtx(args.toVector, s))

    def water(args: Any*) =
      SimpleCommand("water", CmdArgCtx(args.toVector, s))

    def wc(args: Any*) =
      SimpleCommand("wc", CmdArgCtx(args.toVector, s))

    def wdctl(args: Any*) =
      SimpleCommand("wdctl", CmdArgCtx(args.toVector, s))

    def webpack(args: Any*) =
      SimpleCommand("webpack", CmdArgCtx(args.toVector, s))

    def wget(args: Any*) =
      SimpleCommand("wget", CmdArgCtx(args.toVector, s))

    def wgsim(args: Any*) =
      SimpleCommand("wgsim", CmdArgCtx(args.toVector, s))

    def whatis(args: Any*) =
      SimpleCommand("whatis", CmdArgCtx(args.toVector, s))

    def whereis(args: Any*) =
      SimpleCommand("whereis", CmdArgCtx(args.toVector, s))

    def which(args: Any*) =
      SimpleCommand("which", CmdArgCtx(args.toVector, s))

    def whichdb(args: Any*) =
      SimpleCommand("whichdb", CmdArgCtx(args.toVector, s))

    def who(args: Any*) =
      SimpleCommand("who", CmdArgCtx(args.toVector, s))

    def whoami(args: Any*) =
      SimpleCommand("whoami", CmdArgCtx(args.toVector, s))

    def windowBed(args: Any*) =
      SimpleCommand("windowBed", CmdArgCtx(args.toVector, s))

    def windowMaker(args: Any*) =
      SimpleCommand("windowMaker", CmdArgCtx(args.toVector, s))

    def wipefs(args: Any*) =
      SimpleCommand("wipefs", CmdArgCtx(args.toVector, s))

    def wmf2eps(args: Any*) =
      SimpleCommand("wmf2eps", CmdArgCtx(args.toVector, s))

    def wmf2fig(args: Any*) =
      SimpleCommand("wmf2fig", CmdArgCtx(args.toVector, s))

    def wmf2gd(args: Any*) =
      SimpleCommand("wmf2gd", CmdArgCtx(args.toVector, s))

    def wmf2svg(args: Any*) =
      SimpleCommand("wmf2svg", CmdArgCtx(args.toVector, s))

    def wmf2x(args: Any*) =
      SimpleCommand("wmf2x", CmdArgCtx(args.toVector, s))

    def wobble(args: Any*) =
      SimpleCommand("wobble", CmdArgCtx(args.toVector, s))

    def wordcount(args: Any*) =
      SimpleCommand("wordcount", CmdArgCtx(args.toVector, s))

    def wordfinder(args: Any*) =
      SimpleCommand("wordfinder", CmdArgCtx(args.toVector, s))

    def wordmatch(args: Any*) =
      SimpleCommand("wordmatch", CmdArgCtx(args.toVector, s))

    def wossdata(args: Any*) =
      SimpleCommand("wossdata", CmdArgCtx(args.toVector, s))

    def wossinput(args: Any*) =
      SimpleCommand("wossinput", CmdArgCtx(args.toVector, s))

    def wossname(args: Any*) =
      SimpleCommand("wossname", CmdArgCtx(args.toVector, s))

    def wossoperation(args: Any*) =
      SimpleCommand("wossoperation", CmdArgCtx(args.toVector, s))

    def wossoutput(args: Any*) =
      SimpleCommand("wossoutput", CmdArgCtx(args.toVector, s))

    def wossparam(args: Any*) =
      SimpleCommand("wossparam", CmdArgCtx(args.toVector, s))

    def wosstopic(args: Any*) =
      SimpleCommand("wosstopic", CmdArgCtx(args.toVector, s))

    def wpa_cli(args: Any*) =
      SimpleCommand("wpa_cli", CmdArgCtx(args.toVector, s))

    def wpa_passphrase(args: Any*) =
      SimpleCommand("wpa_passphrase", CmdArgCtx(args.toVector, s))

    def wpa_supplicant(args: Any*) =
      SimpleCommand("wpa_supplicant", CmdArgCtx(args.toVector, s))

    def write(args: Any*) =
      SimpleCommand("write", CmdArgCtx(args.toVector, s))

    def wxHexEditor(args: Any*) =
      SimpleCommand("wxHexEditor", CmdArgCtx(args.toVector, s))

    def x86_64(args: Any*) =
      SimpleCommand("x86_64", CmdArgCtx(args.toVector, s))

    def xargs(args: Any*) =
      SimpleCommand("xargs", CmdArgCtx(args.toVector, s))

    def xauth(args: Any*) =
      SimpleCommand("xauth", CmdArgCtx(args.toVector, s))

    def xclip(args: Any*) =
      SimpleCommand("xclip", CmdArgCtx(args.toVector, s))

    def xclip_copyfile(args: Any*) =
      SimpleCommand("xclip_copyfile", CmdArgCtx(args.toVector, s))

    def xclip_cutfile(args: Any*) =
      SimpleCommand("xclip_cutfile", CmdArgCtx(args.toVector, s))

    def xclip_pastefile(args: Any*) =
      SimpleCommand("xclip_pastefile", CmdArgCtx(args.toVector, s))

    def xdg_desktop_icon(args: Any*) =
      SimpleCommand("xdg_desktop_icon", CmdArgCtx(args.toVector, s))

    def xdg_desktop_menu(args: Any*) =
      SimpleCommand("xdg_desktop_menu", CmdArgCtx(args.toVector, s))

    def xdg_email(args: Any*) =
      SimpleCommand("xdg_email", CmdArgCtx(args.toVector, s))

    def xdg_icon_resource(args: Any*) =
      SimpleCommand("xdg_icon_resource", CmdArgCtx(args.toVector, s))

    def xdg_mime(args: Any*) =
      SimpleCommand("xdg_mime", CmdArgCtx(args.toVector, s))

    def xdg_open(args: Any*) =
      SimpleCommand("xdg_open", CmdArgCtx(args.toVector, s))

    def xdg_screensaver(args: Any*) =
      SimpleCommand("xdg_screensaver", CmdArgCtx(args.toVector, s))

    def xdg_settings(args: Any*) =
      SimpleCommand("xdg_settings", CmdArgCtx(args.toVector, s))

    def xdg_user_dir(args: Any*) =
      SimpleCommand("xdg_user_dir", CmdArgCtx(args.toVector, s))

    def xdg_user_dirs_update(args: Any*) =
      SimpleCommand("xdg_user_dirs_update", CmdArgCtx(args.toVector, s))

    def xembedsniproxy(args: Any*) =
      SimpleCommand("xembedsniproxy", CmdArgCtx(args.toVector, s))

    def xinput(args: Any*) =
      SimpleCommand("xinput", CmdArgCtx(args.toVector, s))

    def xlsclients(args: Any*) =
      SimpleCommand("xlsclients", CmdArgCtx(args.toVector, s))

    def xmlget(args: Any*) =
      SimpleCommand("xmlget", CmdArgCtx(args.toVector, s))

    def xmltext(args: Any*) =
      SimpleCommand("xmltext", CmdArgCtx(args.toVector, s))

    def xprop(args: Any*) =
      SimpleCommand("xprop", CmdArgCtx(args.toVector, s))

    def xrandr(args: Any*) =
      SimpleCommand("xrandr", CmdArgCtx(args.toVector, s))

    def xrdb(args: Any*) =
      SimpleCommand("xrdb", CmdArgCtx(args.toVector, s))

    def xset(args: Any*) =
      SimpleCommand("xset", CmdArgCtx(args.toVector, s))

    def xsetroot(args: Any*) =
      SimpleCommand("xsetroot", CmdArgCtx(args.toVector, s))

    def xsubpp(args: Any*) =
      SimpleCommand("xsubpp", CmdArgCtx(args.toVector, s))

    def xtables_legacy_multi(args: Any*) =
      SimpleCommand("xtables_legacy_multi", CmdArgCtx(args.toVector, s))

    def xtables_monitor(args: Any*) =
      SimpleCommand("xtables_monitor", CmdArgCtx(args.toVector, s))

    def xtables_nft_multi(args: Any*) =
      SimpleCommand("xtables_nft_multi", CmdArgCtx(args.toVector, s))

    def xterm(args: Any*) =
      SimpleCommand("xterm", CmdArgCtx(args.toVector, s))

    def xtrace(args: Any*) =
      SimpleCommand("xtrace", CmdArgCtx(args.toVector, s))

    def xz(args: Any*) =
      SimpleCommand("xz", CmdArgCtx(args.toVector, s))

    def xzcat(args: Any*) =
      SimpleCommand("xzcat", CmdArgCtx(args.toVector, s))

    def xzcmp(args: Any*) =
      SimpleCommand("xzcmp", CmdArgCtx(args.toVector, s))

    def xzdec(args: Any*) =
      SimpleCommand("xzdec", CmdArgCtx(args.toVector, s))

    def xzdiff(args: Any*) =
      SimpleCommand("xzdiff", CmdArgCtx(args.toVector, s))

    def xzegrep(args: Any*) =
      SimpleCommand("xzegrep", CmdArgCtx(args.toVector, s))

    def xzfgrep(args: Any*) =
      SimpleCommand("xzfgrep", CmdArgCtx(args.toVector, s))

    def xzgrep(args: Any*) =
      SimpleCommand("xzgrep", CmdArgCtx(args.toVector, s))

    def xzless(args: Any*) =
      SimpleCommand("xzless", CmdArgCtx(args.toVector, s))

    def xzmore(args: Any*) =
      SimpleCommand("xzmore", CmdArgCtx(args.toVector, s))

    def yank(args: Any*) =
      SimpleCommand("yank", CmdArgCtx(args.toVector, s))

    def yarn(args: Any*) =
      SimpleCommand("yarn", CmdArgCtx(args.toVector, s))

    def yarnpkg(args: Any*) =
      SimpleCommand("yarnpkg", CmdArgCtx(args.toVector, s))

    def yes(args: Any*) =
      SimpleCommand("yes", CmdArgCtx(args.toVector, s))

    def ypdomainname(args: Any*) =
      SimpleCommand("ypdomainname", CmdArgCtx(args.toVector, s))

    def zcat(args: Any*) =
      SimpleCommand("zcat", CmdArgCtx(args.toVector, s))

    def zcmp(args: Any*) =
      SimpleCommand("zcmp", CmdArgCtx(args.toVector, s))

    def zdiff(args: Any*) =
      SimpleCommand("zdiff", CmdArgCtx(args.toVector, s))

    def zdump(args: Any*) =
      SimpleCommand("zdump", CmdArgCtx(args.toVector, s))

    def zegrep(args: Any*) =
      SimpleCommand("zegrep", CmdArgCtx(args.toVector, s))

    def zfgrep(args: Any*) =
      SimpleCommand("zfgrep", CmdArgCtx(args.toVector, s))

    def zforce(args: Any*) =
      SimpleCommand("zforce", CmdArgCtx(args.toVector, s))

    def zgrep(args: Any*) =
      SimpleCommand("zgrep", CmdArgCtx(args.toVector, s))

    def zic(args: Any*) =
      SimpleCommand("zic", CmdArgCtx(args.toVector, s))

    def zipdetails(args: Any*) =
      SimpleCommand("zipdetails", CmdArgCtx(args.toVector, s))

    def zipgrep(args: Any*) =
      SimpleCommand("zipgrep", CmdArgCtx(args.toVector, s))

    def zipinfo(args: Any*) =
      SimpleCommand("zipinfo", CmdArgCtx(args.toVector, s))

    def zless(args: Any*) =
      SimpleCommand("zless", CmdArgCtx(args.toVector, s))

    def zmore(args: Any*) =
      SimpleCommand("zmore", CmdArgCtx(args.toVector, s))

    def znew(args: Any*) =
      SimpleCommand("znew", CmdArgCtx(args.toVector, s))

    def zramctl(args: Any*) =
      SimpleCommand("zramctl", CmdArgCtx(args.toVector, s))

    def zsh(args: Any*) =
      SimpleCommand("zsh", CmdArgCtx(args.toVector, s))
  }

}
