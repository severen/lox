import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.5.31"

  application
}

group = "me.shrike"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test:1.5.31"))
}

application {
  mainClass.set("klox.MainKt")
}

tasks.withType<KotlinCompile> {
  // TODO: Update to 17 when Kotlin supports Java 17.
  kotlinOptions.jvmTarget = "16"
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
