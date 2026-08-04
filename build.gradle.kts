import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.gradle.ext.runConfigurations
import org.jetbrains.gradle.ext.settings

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.idea.ext) // Added for IntelliJ Run Configuration
}

group = "me.justlime"
version = "2.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc" }
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") { name = "spigotmc-repo" }
    maven("https://oss.sonatype.org/content/groups/public/") { name = "sonatype" }
    maven("https://repo.codemc.org/repository/maven-public/") { name = "codemc" }
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") { name = "extendedclip" }
    maven("https://repo.tcoded.com/releases") { name = "tcoded-releases" }
    maven("https://mvn.wesjd.net/")
}

dependencies {
    // Core
    compileOnly(libs.spigot.api)
    compileOnly(libs.betterteams)
    implementation(libs.folialib)

    // Metrics
    implementation(libs.bstats.bukkit)

    // GUI
    implementation("net.justlime.limeframegui:LimeFrameGUI")
    implementation(libs.anvilgui)
    compileOnly(libs.floodgate.api)

    // Adventuretea
    implementation(libs.adventure.platform.bukkit)
    implementation(libs.adventure.text.minimessage)
    implementation(libs.adventure.text.serializer.plain)
    implementation(libs.adventure.text.serializer.legacy)

    // Placeholder
    compileOnly(libs.placeholderapi)

    // Other
    compileOnly(libs.gson)
}

kotlin {
    jvmToolchain(21)
}

val serverVersion = "1.21.11"

tasks.withType<ShadowJar> {
    archiveClassifier.set("all")

    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }

    // Minimize safely: Exclude libraries that rely heavily on reflection/dynamic loading
    minimize {
        exclude(dependency("net.wesjd:anvilgui:.*"))
        exclude(dependency("com.tcoded:FoliaLib:.*"))
        exclude(dependency("net.justlime.limeframegui:.*"))
        exclude(dependency("net.kyori:.*"))
        exclude(dependency("org.jetbrains.kotlin:.*"))
    }

    // Relocations to avoid classpath conflicts with other plugins
    relocate("net.wesjd.anvilgui", "me.justlime.betterTeamGUI.libs.anvilgui")
    relocate("net.kyori", "me.justlime.betterTeamGUI.libs.kyori")
    relocate("net.justlime.limeframegui", "me.justlime.betterTeamGUI.libs.limeframegui")
    relocate("org.bstats", "me.justlime.betterTeamGUI.libs.bstats")
    relocate("com.tcoded", "me.justlime.betterTeamGUI.libs.tcoded")
    relocate("kotlin", "me.justlime.betterTeamGUI.libs.kotlin") // Critical for Kotlin developers
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

// Task to copy the jar to the server plugins folder
tasks.register<Copy>("copyToServerPlugins") {
    dependsOn("shadowJar")
    from(tasks.named<ShadowJar>("shadowJar").flatMap { it.archiveFile })
    into(layout.projectDirectory.dir("run/$serverVersion/plugins"))
}

// Combined task to build and copy
tasks.register("shadowJarCopy") {
    dependsOn("build", "copyToServerPlugins")
}

// Task to copy common configurations
tasks.register<Copy>("prepareServerConfigs") {
    group = "server"
    description = "Copies config presets from run/common to the server directory"
    from(layout.projectDirectory.dir("run/common"))
    into(layout.projectDirectory.dir("run/$serverVersion"))
}

// Task to safely clean the server while preserving downloaded libraries to prevent re-downloading
tasks.register<Delete>("cleanServer") {
    group = "server"
    description = "Cleans server data but keeps cached libraries and versions to avoid redownloading."
    val runDir = layout.projectDirectory.dir("run/$serverVersion").asFile
    if (runDir.exists()) {
        delete(fileTree(runDir) {
            exclude("libraries/**")
            exclude("versions/**")
            exclude("cache/**")
            exclude("server.jar")
            exclude("eula.txt")
            exclude("server.properties")
            exclude("world/**")
            exclude("crash-reports/**")
            exclude("*.json")
            exclude("*.yml")
        })
    }
}

tasks.register<Exec>("runServer") {
    group = "server"
    description = "Run the Minecraft server"
    dependsOn("copyToServerPlugins")
    workingDir = layout.projectDirectory.dir("run/$serverVersion").asFile
    commandLine("java", "-Xms2G", "-Xmx2G", "-jar", "server.jar", "nogui")
    standardInput = System.`in`
}

// Automatically generate the "MinecraftServer" run button in IntelliJ
idea {
    project {
        settings {
            runConfigurations {
                create("MinecraftServer", org.jetbrains.gradle.ext.Gradle::class.java) {
                    taskNames = listOf("runServer")
                }
                create("Run Server", org.jetbrains.gradle.ext.Gradle::class.java) {
                    taskNames = listOf("runServer")
                }
            }
        }
    }
}