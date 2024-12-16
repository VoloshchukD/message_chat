plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-websocket:11.0.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.12")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    compileOnly("jakarta.websocket:jakarta.websocket-api:2.1.0")



}