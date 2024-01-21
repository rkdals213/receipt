plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "receipt"
include("receipt-adapter")
include("receipt-core")
include("receipt-domain")
include("receipt-persistence")
include("receipt-batch")
