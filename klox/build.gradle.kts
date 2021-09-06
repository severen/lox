import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.5.30"

  application
}

group = "me.shrike"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test"))
}

application {
  mainClass.set("klox.MainKt")
}

tasks.withType<KotlinCompile> {
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
