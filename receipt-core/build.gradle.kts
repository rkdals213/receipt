dependencies {
    api(project(":receipt-persistence"))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.0")
    implementation("org.springframework.boot:spring-boot-starter")
}
