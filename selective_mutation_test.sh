# clean cache
rm src/main/java/com/vvs/sample/Mutant*.java
mvn exec:java -Dexec.mainClass="com.vvs.App" -Dexec.args="1"
mvn exec:java -Dexec.mainClass="com.vvs.ReportGenerator"