plugins {
    id("org.springframework.boot")
    kotlin("plugin.jpa") version "1.9.21"
}

dependencies {
    implementation(project(":receipt-core"))
    api(project(":receipt-data"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}

allOpen {
    annotation("com.example.common.JobParameter")
}
