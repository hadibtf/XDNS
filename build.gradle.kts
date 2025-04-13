import org.jetbrains.compose.desktop.application.dsl.TargetFormat
plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "ir.xdns"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.windows_x64)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.1")
    implementation(compose.material)
    implementation(compose.materialIconsExtended)
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
            packageVersion = "1.1.0"

            windows {
                menuGroup = "ir.xdns"
                shortcut = true
                iconFile.set(project.file("src/main/resources/XDNS.ico"))
                dirChooser = true
                perUserInstall = true
                upgradeUuid = "1E2F5448-D3F3-4252-AFE4-BEF456A96ADF"
                console = false
            }
        }
    }
}
