# Bash dsl for scala (bash4s)

Build bash scripts directly in the scala language. The library facilitates:

* Type safety (still `WIP`, but a lot better than raw bash scripts)
* Reusable script parts that can be merged into larger scripts
* Testability
* Introspection of your scripts
* Extensible, you could very easily create wrappers to run these scripts on a slurm cluster, 
  in a docker container, on kubernets or in your devops pipeline.

This is much more a PoC than a production ready library, but if you write your tests, you should
still be far better off than running your bash scripts directly.

## How to

### Installation and setup

The library has not been published to ``maven central`` yet. This is `WIP`, to use it,

Install:

* [mill](http://www.lihaoyi.com/mill/), an excellent scala build tool
* [ammonite](https://github.com/lihaoyi/Ammonite), incredibly rich scala script runner
* This only works with scala `2.13`

Simple way of installing ammonite:

```bash
# You need ammonite for scala 2.13
sudo sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.1.0/2.13-2.1.0) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm' && amm
```

Simple way of installing mill:

```bash
sudo curl -L https://github.com/lihaoyi/mill/releases/download/0.6.2/0.6.2 > /usr/local/bin/mill && sudo chmod +x /usr/local/bin/mill
```

You can run this [setup](https://github.com/scalavision/bash4s/setup) in an `empty` !! directory to get started:

`CAUTION!!` It will write the script `listFiles` to the current directory! Since it is empty, it really shouldn't do
any harm at all (just so you know ..).

```bash
#!/usr/bin/env bash

set -euo pipefail

git clone https://github.com/scalavision/bash4s.git

pushd ./bash4s || { echo "unable to enter bash4s folder, was it correclty cloned?"; exit 1; }
mill bash4s.assembly
popd

cp ./bash4s/out/bash4s/assembly/dest/out.jar ./bash4s.jar

cat <<EOF > ./myscript.sc
import \$cp.bash4s
import bash4s._

// getting the current working directory
val wd = os.pwd

// will save and run the script ./listFiles in
// current working directory
// You will see 0 printed to the screen
// if the script terminated successfully
val listFiles = ls"-hal \$wd".runAt(wd)
EOF

# The ammonite script runner will watch the file and rerun everytime it is saved
amm -w ./myscript.sc
```

The `bash4s` library will be built into a fat jar and copied to the current working directory.
All you need to run bash4s dsl scripts is inside this library. See the next section on how
to use it.

Remember to keep `ammonite` running while you edit your script. It recompiles and reruns your script
every time you save it. Also remember that you can use the `print()` or `printRich()` command to
just print the script content, as it will look when saved to file.

At last a `warning` !!! When you run `bash` scripts, they have `nasty` side effects like deleting files
and what not, so be `careful` whenever you use this tool. In the future more guards will probably
be added to avoid `accidents` happening.

```bash
amm -w <path to my script.sc>
```

### Your first script, and `java.io.tmp` as the default rundir

Open `myscript.sc` from above in your favorite editor of choice ([metals plugin](https://scalameta.org/metals/) with [vscode](https://code.visualstudio.com/download) is a good choice):

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

`0` is the `successful` result type, returned by the shell from running the script in the background (this way of running it is blocking!).

Other good editors supported by `metals` (scala language server) are:

* neovim with coc-metals
* emacs
* atom
* eclipse

### Implicitly naming of script

If you make a variable point to the script, the variable name will implicitly be used as the name of the script.

```scala
import $cp.bash4s
import bash4s._

val scriptTest = ls"-halt .".run()
```

(Remember to save the file!)

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
// capture the current working directory in scala
val wd = os.pwd
// It will return a Vector[String], with the content that exists in the current
// working directory.
val dirContent = ls"-halt $wd".lines()
// rich printing the content to the console
pprint.pprintln(dirContent)
```

In this example we where mixing scala code with our bash command.

```s
The Scala variables inside a bash command will use the `toString()` method.
```

So if you put complex objects inside the command it might not work as expected.

Use the `print()` feature to see what actually gets created as arguments. The built in
library and dsl should handle this correctly, if not, issue a bug request.

### Simple debug output of the script

It is very simple to debug your script using the `print()` and `printRich()` commands:

```bash
val wd = os.pwd
val dirContent = ls"-halt $wd".printRich()

// ls -halt <path to wherever your current working directory is pointing to>
```

It will print the script to screen instead of running it.

The `printRich()` command will use the excellent [pprint](https://www.lihaoyi.com/PPrint/) library that is
made by `Lihaoyi`, it is also part of ammonite script engine. It will cut the output after specific amount of lines etc.

You can also get a textual representation of the script with the `.txt` method.

```scala
val dirContentScript = ls"-halt $wd".txt
```

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
