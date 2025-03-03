val javaVersion: Int = project.property("javaVersion").toString().toInt()
val liquiBaseVersion: String by project
val postgresVersion: String by project

plugins {
    id("java")
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "hu.bhr"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

tasks.jar{
    enabled = false
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    // implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.liquibase:liquibase-core:$liquiBaseVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    runtimeOnly ("com.h2database:h2:2.3.232")

    // JUnit platform dependencies
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
}