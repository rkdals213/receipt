dependencies {
    implementation(project(":receipt-core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:3.2.3")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
