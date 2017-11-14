MEMORY="-Xms64m -Xmx64m -XX:MaxMetaspaceSize=256m"
GC="-XX:+UseG1GC"
GC_LOG="-verbose:gc -Xloggc:./logs/default_gc_logs/gc_pid_%p.log -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=1M"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

java $MEMORY $GC $GC_LOG $DUMP -jar target/Homework04.jar