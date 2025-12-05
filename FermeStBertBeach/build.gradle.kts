plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "com.ferme.bertbeach"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    // Tests JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JDBC SQLite
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")
}

javafx {
    version = "25"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("com.ferme.bertbeach.App")

    applicationDefaultJvmArgs = listOf(
        "--module-path",
        configurations.runtimeClasspath.get().asPath,
        "--add-modules",
        "javafx.controls,javafx.fxml"
    )
}

tasks.test {
    useJUnitPlatform()
}
