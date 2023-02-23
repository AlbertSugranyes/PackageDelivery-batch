CURRENT_DATE= $(`date '+%Y/%m/%d'`);
LESSON=$(basename $PWD);
mvn clean package;
java -jar ./target/PackageDelivery-batch-02-0.0.1-SNAPSHOT.jar item=shoes run.date=$CURRENT_DATE lesson=$LESSON;
read;