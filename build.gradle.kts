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
    
    // Test dependencies
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("junit:junit:4.13.2")
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
            packageVersion = "1.1.1"

            windows {
                menuGroup = "ir.xdns"
                shortcut = true
                iconFile.set(project.file("src/main/resources/XDNS.ico"))
                dirChooser = true
                perUserInstall = true
                upgradeUuid = "7b1ac03d-2926-48e8-b340-405cb638bafc"
                console = false
            }
        }
    }
}
