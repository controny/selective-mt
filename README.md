# selective-mt

A selective mutation testing tool for Java.

## Installation & Running

```bash
mvn clean install
# Generate mutants
mvn exec:java -Dexec.mainClass="com.vvs.App"
# Test and generate report
mvn exec:java -Dexec.mainClass="com.vvs.ReportGenerator"
```