# Bash dsl for scala (bash4s)

Build bash scripts directly in the scala language. The library facilitates:

* Type safety (still `WIP`, but a lot better than raw bash scripts)
* Reusable script parts that safely can be merged into larger scripts
* Testability
* Introspection of your scripts
* Extensible, you could very easily create wrappers to run these scripts on a slurm cluster, 
  in a docker container, on kubernets or in your devops pipeline.

This is much more a PoC than a production ready library, but if you write your tests, you should
still be far better off than running your bash scripts directly.

## Quick start

### Installation and setup

The library has not yet been published to ``maven central``. That is `WIP`, but you can use it as follows:

#### Prerequisities:

* [mill](http://www.lihaoyi.com/mill/), an excellent scala build tool
* [ammonite](https://github.com/lihaoyi/Ammonite), incredibly rich scala script runner
* This only works with scala `2.13`, handled below

Those tools needs at least `jdk8` to be installed as well.

#### Simple installation of prerequisities

This is copied from their respective documentation pages.

ammonite:

```bash
# You need ammonite for scala 2.13
sudo sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.1.0/2.13-2.1.0) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm' && amm
```

mill:

```bash
sudo curl -L https://github.com/lihaoyi/mill/releases/download/0.6.2/0.6.2 > /usr/local/bin/mill && sudo chmod +x /usr/local/bin/mill
```

#### Simple setup of the bash4s library for scripting

You can run this [setup](https://github.com/scalavision/bash4s/setup) (or copy paste the script below) in an `empty` !! directory, to get started.

`CAUTION!!` It will write the script `listFiles` to the current directory! Since it is empty, it really shouldn't do
any harm at all (just so you know ..).

```bash
#!/usr/bin/env bash

set -euo pipefail

echo "cloning repository"
git clone https://github.com/scalavision/bash4s.git

pushd ./bash4s || { echo "unable to enter bash4s folder, was it correctly cloned?"; exit 1; }
echo "building the project, generating fat jar output"
mill bash4s.assembly
popd

echo "copying fat jar into current working directory, ./bash4s.jar"
cp ./bash4s/out/bash4s/assembly/dest/out.jar ./bash4s.jar

echo "generating a simple script"

cat <<EOF > ./myscript.sc
import \$cp.bash4s
import bash4s._

// getting the current working directory from scala
val wd = os.pwd

// will save and run the script ./listFiles in
// current working directory
// You will see 0 printed to the screen
// if the script terminated successfully
val listFiles = ls"-hal \$wd".runAt(wd)
pprint.pprintln(listFiles)
EOF

echo "starting the script in a run-eval loop with the script runner ammonite"
amm -w ./myscript.sc
```

The `bash4s` library will be built into a fat jar and copied to the current working directory.
All you need to run bash4s dsl scripts is inside this library. See the next section on how
to use it.

#### Tips for further exploration

Remember to keep `ammonite` running while you edit your script. It recompiles and reruns your script
every time you save it.

```bash
amm -w <path to my script.sc>
```

Also remember that you can use the `print()` or `printRich()` command to
just print the script content, as it will look when saved to file.

```scala
val wd = os.pwd
ls"-halt $wd".printRich()
```

`WARNING!!` When you run `bash` scripts, they have `nasty` side effects like ``deleting files``
and what not, so be `careful` whenever you use this tool. In the future more guards will probably
be added to avoid `accidents` happening.

### Your first script, and `java.io.tmp` as the default rundir

Open `myscript.sc` from above in your favorite editor of choice ([metals plugin](https://scalameta.org/metals/) with [vscode](https://code.visualstudio.com/download) is a great choice):

You can change it as follows:

```scala
// We add the bash4s.jar to the classpath
// Everything that is needed is inside the library
import $cp.bash4s

// importing the bash4s dsl into scope
import bash4s._

// !! This will write the script named `test.sh` to /tmp/test.sh
// as long as the java "java.io.tmp" variable is available,
// or the script will be saved to current working directory.
ls"-halt .".run("test.sh")
```

And save the file in your editor!

It should compile, run and print : `0` to the screen.

`0` is the `successful` result type, returned by the shell from running the script in the background (also this way of running it is blocking!).

Other good editors supported by `metals` (scala language server) are:

* neovim with coc-metals
* emacs
* atom
* eclipse

### installation, make bash4s available for all future scripts and the repl

One `unfortunate` side effect of adding the `bash4s` classpath to ammonite is that the `exit` command
no long works inside the `ammonite` repl. This is because the `bash` exit command will overwrite it.

To exit the `ammonite` repl, you must use the `exit` method via the `interp` object handle, like this:

```bash
interp.exit
```

If there are other ammonite script commands that doesn't work please file and issue in
the bug tracker and try to use the `interp` object handle.

Ammonite has a ``~/.ammonite/predef.sc`` file you can use to configure a default setup for all of your
ammonite scripts. For instance, you might want to add `bash4s` library as a mandatory part of
all your scripts.

To achieve this, run the [install](./install) script, or copy paste the following into your own script:

```bash
#!/usr/bin/env bash
set -euo pipefail

# check that your assembled fat jar exists, if not build it
[[ -f ./out/bash4s/assembly/dest/out.jar ]] || \
 { echo "you need to build the project using mill bash4s.assembly command"; exit 1; }

# the ammonite path should exist if you already have run ammonite
mkdir -p ~/.ammonite

# copy the library in named as bash4s.jar
cp ./out/bash4s/assembly/dest/out.jar ~/.ammonite/bash4s.jar

# add the library to the classpath of all running instances of ammonite repl
echo "import \$cp.bash4s, bash4s._" >> ~/.ammonite/predef.sc

# add the library to the classpath of all ammonite scripts run by ammonite
echo "import \$cp.bash4s, bash4s._" >> ~/.ammonite/predefScript.sc
```

`REMEMBER`:

* You will need the `bash4s` on the classpath wherever you run your ammonite bash4s scripts.
* Beware that we are using `>>`, this will clobber the ammonite predef scripts if the install script is run multiple times!
  * (Some day I'll add a `sed` command to fix this ..)

### Implicitly naming of your script

If you make a variable point to the script, the variable name will implicitly be used as the name of the script.

```scala
import $cp.bash4s
import bash4s._

val scriptTest = ls"-halt .".run()
```

(Remember to save the scala script file, as it will trigger recompilation and rerun it!)

Then

```bash
cat /tmp/scriptTest
```

yields:

```bash
#!/usr/bin/env bash
ls -halt .
```

### Capture script output, mix with scala code

To capture the output from the script, you can run it like this:

```scala
// capture the current working directory from within scala
val wd = os.pwd
// It will return a Vector[String], with the content that exists in the current
// working directory.
val dirContent = ls"-halt $wd".lines()

// convert the Vector[String] into text separated by newline (\n)
// print to console
println(dirContent.mkString("\n"))
```

In this example we where mixing scala code with our bash command.

```s
The Scala variables inside a bash command will use the `toString()` method.
`wd` is a scala variable in this example.
```

If you put complex objects inside the command it might not work as expected.

Use the `print()` feature to see how your complete script looks like.

Another example, printing the script content from itself:

```scala
// cd into ~/tmp and create the script ~/tmp/test.sc
// then run it from the ~/tmp folder:
// amm -w ./test.sc
val wd = os.pwd
val script = cat"$wd/test.sc".lines()
println(script.mkString("\n"))
```

yields:

```s
val wd = os.pwd

val script = cat"$wd/test.sc".lines()

println(script.mkString("\n"))
```

Note that if you move to some other directory and run the ammonite script from there, like:

```bash
cd /home/scalavision
amm -w ./tmp/test.sc
```

the `os.pwd` command will have some other value, and the `path` to `test.sc` script will be wrong.

You can circumvent that by using the underlying `os-lib` functionality of the `proc()` function:

```scala
val wd = os.pwd
val script = cat"./test.sc".proc().call(
  // we specify the location where the ammonite script will be run from
  cwd = os.Path("/home/scalavision/tmp"
)).out.lines
println(script.mkString("\n"))
```

See more about the `proc` later in this documentation.

### Simple print output debugging

It is very simple to debug your script using the `print()` and `printRich()` commands:

```bash
val wd = os.pwd
val dirContent = ls"-halt $wd".printRich()

// ls -halt <path to wherever your current working directory is pointing to>
```

We print the script to screen instead of running it. A very safe way to investigating that paths are correct etc.

The `printRich()` command will use the excellent [pprint](https://www.lihaoyi.com/PPrint/) library that is
made by `Lihaoyi`, it is also part of ammonite script engine. It will cut the output after specific amount of lines etc.

You can also get a textual representation of the script with the `.txt` method.

```scala
val dirContentScript: String = ls"-halt .".txt
println(dirContentScripts)
```

Yields:

```s
ls -halt .
```

### embrace os-lib, zio-process, zio and the scala and java apis

You can use the [proc](https://github.com/lihaoyi/os-lib#osproccall) method in `os-lib` to reuse all of the rich functionality this library provides:

```scala
// creating a handle to a process description that will be run later.
val myProc = ls"-halt .".proc

// run the script, setting the current working directory to some path
myProc.call(cwd = os.Path("/path/to/somehwere"))
```

The text that is generated by the `bash4s.dsl` is available via the `.txt` function. You can use it to build your own
script runner, or safer version of whats provided here. `zio-process`, the scala api and the java api have
a lot of capabilities that could be useful. Also the `zio` core library would make it possible to make
very complex workflows that is resource safe, retriable, async and the lot.

There is a lot more that should be done to make this part safer and / or more convenient. As for now, ease of
use has been the priority. When creating scripts, you very often just wants something that works there and then.

## Background

It started out as an idea to build a big data processing workflow framework like [nextflow](https://nextflow.io),
[chromwell](https://cromwell.readthedocs.io/en/stable/) or [apache airflow](https://airflow.apache.org/) in order
to build better `bioinformatic` pipelines. However, it turns out that
you do not need a framework to do these things in `scala`. Scala already have
excellent libraries that are a much better fit to build complex pipelines.

To run a bash process in scala, you can use the [os-lib](https://github.com/lihaoyi/os-lib):

```scala
os.proc("ls").call()
```

using `bash4s` you can simply do:

```scala
import bash4s._
ls"-halt .".run
```
