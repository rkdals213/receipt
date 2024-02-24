import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
}


tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}
