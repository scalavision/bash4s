# Bash dsl for scala (bash4s)

Build bash scripts directly in the scala language. The library facilitates:

* Type safety
* Reusable script parts that can be merged into larger scripts
* Testability
* Introspection of your scripts
* Extensible, you could very easily create wrappers to run these scripts on a slurm cluster, docker container, kubernets or devops pipeline.

This is much more a PoC than a production ready library, but if you write your tests, you should
still be far better off than running your bash scripts directly.

## How to Bash4s for people not used to scala

### Installation and setup

This library has not been published to ``maven central`` yet. This is `wip`. To use it:

Install:

* [mill](http://www.lihaoyi.com/mill/) scala build tool
* scala script runner [ammonite](https://github.com/lihaoyi/Ammonite).

Simple way of installing ammonite:

```bash
# You need ammonite for scala 2.13
sudo sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.1.0/2.13-2.1.0) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm' && amm
```

Simple way of installing mill:

```bash
sudo curl -L https://github.com/lihaoyi/mill/releases/download/0.6.2/0.6.2 > /usr/local/bin/mill && sudo chmod +x /usr/local/bin/mill
```

First you need to create the library jar that you will use in your script:

```bash
git clone https://github.com/scalavision/bash4s.git
mill bash4s.assembly
cp out/bash4s/assembly/out.jar ./bash4s.jar
cat <<EOF > myscript.sc
import \$cp.bash4s
import bash4s._
EOF
# This should now work, having bash4s library on your classpath
# inside the script
amm -w myscript.sc
```

### Your first script

Open your editor of choice ([metals plugin](https://scalameta.org/metals/) with [vscode](https://code.visualstudio.com/download) is a good choice) and type:

```scala
// This should already be there
// We add the bash4s.jar to the classpath
import $cp.bash4s

// importing the bash4s dsl into scope
import bash4s._

// !! This will write the script named `test.sh` to /tmp/test.sh
// as long as the java "java.io.tmp" variable is available
// or it will save it to current working directory
ls"-halt .".run("test.sh")
```

And save !

It should compile, run and output: `0`

As a successful result type from the script that was run.

Other good editors supported by metals (scala language server) are:

* neovim with coc-metals
* emacs
* atom
* eclipse

### Implicitly naming of script

If you make a script point to a variable name, the script will be saved with the name of the variable.

```scala
import $cp.bash4s
import bash4s._

val scriptTest = ls"-halt .".run()
```

Then

```bash
cat /tmp/scriptTest
```

yields:

```bash
#!/usr/bin/env bash
ls -halt .
```

### capture script output, mix with scala code

To capture the output from the script, you can run it like this:

```scala
// capture the current working directory in scala
val wd = os.pwd
// It will return a Vector[String] of content in the current
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

Use the `print()` feature to see what actually gets created as arguments.

### simple debugging of the script

You can do a very simple debugging of your script using the `print()` and `printRich()` commands:

```bash
val wd = os.pwd
val dirContent = ls"-halt $wd".printRich()

// ls -halt <path to wherever your current working directory is pointing to>
```

It will print the script to screen instead of running it.

The `printRich()` command will use the excellent [pprint](https://www.lihaoyi.com/PPrint/) library that is made by `lihaoyi` and that is also built into ammonite. It will cut the output after specific amount of lines etc.

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
