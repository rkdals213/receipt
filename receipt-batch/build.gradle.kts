dependencies {
    api(project(":receipt-core"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}

allOpen {
    annotation("com.example.common.JobParameter")
}
