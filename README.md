
cf env album-service

cf ssh -N -L 63306:10.0.16.78:3306 album-service-migrate -k

mysql -uMIQbApwnNPgdV5S3 -h127.0.0.1 -P63306 -p -e"SHOW DATABASES;"

Enter password: 
+-----------------------------------------+
| Database                                |
+-----------------------------------------+
| cf_43f3ff59_8f80_419f_9b4f_348f0d9d8ffc |
| information_schema                      |
+-----------------------------------------+



import org.flywaydb.gradle.FlywayExtension
import org.flywaydb.gradle.task.FlywayMigrateTask

plugins {
    id "org.flywaydb.flyway" version "4.0.3"
}

apply from: "../../java.gradle"

dependencies {
    compile "mysql:mysql-connector-java:5.1.40"
}

flyway {
    url = "jdbc:mysql://localhost:3306/albums?useSSL=false"
    user = "root"
    outOfOrder = false
}

task cfMigrate(type: FlywayMigrateTask) {
    extension = new FlywayExtension(
            url: 'jdbc:mysql://127.0.0.1:63306/cf_43f3ff59_8f80_419f_9b4f_348f0d9d8ffc',
            user: 'MIQbApwnNPgdV5S3',
            password: 'E4HDQoyiCUxxwUaj',
            baselineOnMigrate: true,
            outOfOrder: false
    )
}

./gradlew cfMigrate