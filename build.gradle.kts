import org.jetbrains.compose.desktop.application.dsl.TargetFormat
plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "ir.xdns"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.windows_x64)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // Add coroutines core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4") // Add coroutines swing for Dispatchers.Main

}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(
                TargetFormat.Msi,
                TargetFormat.Exe,
            )
            packageName = "XDNS"
            packageVersion = "1.0.0"
            windows {
                menuGroup = "ir.xdns" // Group in the Start Menu
                shortcut = true // Enables shortcut creation
                iconFile.set(project.file("src/main/resources/icon.png")) // Path to your icon
            }
        }
    }
}
