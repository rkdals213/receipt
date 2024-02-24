plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "receipt"
include("receipt-api")
include("receipt-core")
include("receipt-data")
include("receipt-data:jpa")
findProject(":receipt-data:jpa")?.name = "jpa"
include("receipt-batch")
