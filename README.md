# graalvm-experiments

This are some experiments with performance of GraalVM and HotSpot JVM.

You can configure location of SDKs in "run.sh" according your local environment. This script will generate "optimized" and "unoptimized" inputs for simple java program. "Optimzitaion" of is related to the performance of this java program based on given input. The script will then execute compiled java program with optimized and unoptimized input under GraalVM and HotSpot JVM (based on configuration).

The results might be surprising. GraalVM performs better than HotSpot JVM when is used "optimized" input but results are reverted with "unoptimized" input. With "unoptimized" input is performance of GraalVM worse. On my machine is the difference roughly 5% but the results are consistent across multiple executions. Lastly is class file compiled to native executable and executed with "unoptimized" input. The performance is the worst. The executions takes (unbelievably) approximately three-times more time.

I didn't have time yet for proper investigation of what may be the reason of such unexpected performance.

This code is based on work of Chris Seaton: https://github.com/chrisseaton/graalvm-ten-things