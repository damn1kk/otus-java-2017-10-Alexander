MEMORY="-Xms64m -Xmx64m -XX:MaxMetaspaceSize=256m"
GC="-XX:+UseConcMarkSweepGC"
GC_LOG="-verbose:gc -Xloggc:./logs/default_gc_logs/gc_pid_%p.log -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=1M"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

#GC="-XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark -XX:+UseParNewGC"

java $MEMORY $GC $GC_LOG $DUMP -jar target/Homework04.jar