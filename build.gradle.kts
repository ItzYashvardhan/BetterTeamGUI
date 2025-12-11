import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.21"
    id("com.gradleup.shadow") version "8.3.6"
}

group = "me.justlime"
version = "2.1"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") { name = "spigotmc-repo" }
    maven("https://oss.sonatype.org/content/groups/public/") { name = "sonatype" }
    maven("https://repo.codemc.org/repository/maven-public/") { name = "codemc" }
    maven("https://jitpack.io")
    maven("https://repo.opencollab.dev/main/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/"){ name = "extendedclip" }

}

dependencies {
    //Core
    compileOnly("org.spigotmc:spigot-api:1.16.1-R0.1-SNAPSHOT")
    compileOnly("com.github.booksaw:BetterTeams:4.15.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    //Metrics
    implementation("org.bstats:bstats-bukkit:3.1.0")

    //GUI
//    implementation("com.github.ItzYashvardhan:LimeFrameGUI:a26eec125f")
    implementation("net.justlime.limeframegui:LimeFrameGUI")
    implementation("net.wesjd:anvilgui:1.10.10-SNAPSHOT")
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
    jvmToolchain(8)
}

tasks.withType<ShadowJar>{
    manifest {
        attributes["paperweight-mappings-namespace"] = "spigot"
    }
    minimize {
        exclude(dependency("net.wesjd:anvilgui"))
    }
    relocate("net.wesjd.anvilgui", "me.justlime.betterTeamGUI.libs.anvilgui")
    relocate("net.kyori", "me.justlime.betterTeamGUI.libs.kyori")
    relocate("net.justlime.limeframegui", "me.justlime.betterTeamGUI.libs.limeframegui")
    relocate("org.bstats", "me.justlime.betterTeamGUI.libs.bstats")

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
    dependsOn("shadowJar")  // Ensure shadowJar completes before copying
    from(layout.buildDirectory.dir("libs/${project.name}-${project.version}-all.jar"))  // Use layout.buildDirectory
    into("E:/Minecraft/servers/Development/PaperMC-1.21.10/plugins")
//    into("E:/Minecraft/servers/PaperMc-1.20.4/plugins")
}

// Combined task to build and copy
tasks.register("shadowJarCopy") {
    dependsOn("build", "copyToServerPlugins")
}
