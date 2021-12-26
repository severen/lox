plugins {
  kotlin("jvm") version "1.7.10"

  application
}

group = "me.shrike"
version = "0.1.0"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(kotlin("test:1.7.10"))
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
