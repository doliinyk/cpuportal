plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "ua.lpnu.denysoliinyk"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories.mavenCentral()

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    compileOnly("org.projectlombok:lombok")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    testImplementation("com.h2database:h2")
    testImplementation("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        html.required.set(true)
    }

    dependsOn(tasks.test)
}

afterEvaluate {
    tasks.jacocoTestReport {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "ua/lpnu/denysoliinyk/cpuportal/mapper",
                    "ua/lpnu/denysoliinyk/cpuportal/repository"
                )
            }
        }))
    }
}