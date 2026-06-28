import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.9" // Added for IntelliJ Run Configuration
}

group = "me.justlime"
version = "2.4"
val serverVersion = "26.1.2"

repositories {
    mavenCentral()
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
    //Core
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
    compileOnly("com.github.booksaw:BetterTeams:5.1.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.tcoded:FoliaLib:0.5.1")

    //Metrics
    implementation("org.bstats:bstats-bukkit:3.1.0")

    //GUI
//    implementation("com.github.ItzYashvardhan:LimeFrameGUI:VERSION")
    implementation("net.justlime.limeframegui:LimeFrameGUI")
    implementation("net.wesjd:anvilgui:1.10.13-SNAPSHOT")
    compileOnly("org.geysermc.floodgate:api:2.2.3-SNAPSHOT")

//     Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.4.1")
    implementation("net.kyori:adventure-text-minimessage:4.24.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.24.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.24.0")

    //Placeholder
    compileOnly("me.clip:placeholderapi:2.11.6")

    //Other
    compileOnly("com.google.code.gson:gson:2.10.1")

}

kotlin {
    jvmToolchain(21)
}

tasks.withType<ShadowJar> {
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
    minimize {
        exclude(dependency("net.wesjd:anvilgui"))
        exclude(dependency("com.tcoded:FoliaLib"))
    }
    relocate("net.wesjd.anvilgui", "me.justlime.betterTeamGUI.libs.anvilgui")
    relocate("net.kyori", "me.justlime.betterTeamGUI.libs.kyori")
    relocate("net.justlime.limeframegui", "me.justlime.betterTeamGUI.libs.limeframegui")
    relocate("org.bstats", "me.justlime.betterTeamGUI.libs.bstats")
    relocate("com.tcoded", "me.justlime.betterTeamGUI.libs.tcoded")

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
    dependsOn("copyToServerPlugins", "prepareServerConfigs")
    workingDir = layout.projectDirectory.dir("run/$serverVersion").asFile
    commandLine("java", "-Xms2G", "-Xmx2G", "-jar", "server.jar", "nogui")
    standardInput = System.`in`
}

// Automatically generate the "MinecraftServer" run button in IntelliJ
//idea {
//    project {
//        settings {
//            runConfigurations {
//                create("MinecraftServer", org.jetbrains.gradle.ext.Gradle::class.java) {
//                    taskNames = listOf("runServer")
//                }
//                create("Run Server", org.jetbrains.gradle.ext.Gradle::class.java) {
//                    taskNames = listOf("cleanServer", "runServer")
//                }
//            }
//        }
//    }
//}