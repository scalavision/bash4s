# Bash dsl for scala (bash4s)

Build bash scripts directly in the scala language. The library facilitates:

* Type safety
* Reusable script parts that can be merged into larger scripts
* Testability
* Introspection of your scripts
* Extensible, you could very easily create wrappers to run these scripts on a slurm cluster, docker container, kubernets or devops pipeline.

This is much more a PoC than a production ready library, but if you write your tests, you should
still be far better off than running your bash scripts directly.

It started out as an idea to build bash processing workflow frameworks like `nextflow`, `chromwell` or
``apache airflow`` in order to build better `bioinformatic` pipelines. However, it turns you that
you do not need a framework to do these things in `scala`. It already have libraries that are a much
better fit and that you easily can adapt to run your bash scripts.
