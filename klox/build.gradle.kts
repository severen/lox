import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.20"

  application
}

group = "me.shrike"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))

  implementation("com.ibm.icu:icu4j:72.1")
  testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.20")
}

application {
  mainClass.set("klox.MainKt")
}

tasks.getByName("run", JavaExec::class) {
  standardInput = System.`in`
}

tasks.test {
  useJUnitPlatform()
}

tasks.jar {
  manifest {
    attributes["Main-Class"] = "klox.MainKt"
  }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "18"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "18"
}
