@file:Suppress("SpellCheckingInspection")

import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.resourcefactory.paper.paperPluginYaml
import xyz.jpenilla.runtask.service.DownloadsAPIService
import xyz.jpenilla.runtask.service.DownloadsAPIService.Companion.registerIfAbsent

plugins {
    kotlin("jvm")
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.runPaper)
    alias(libs.plugins.resourceFactory)
}

group = "cn.xor7.xiaohei"
version = "2.2.0-xiaofanforfolia"

// Folia-only build: runServer uses Folia with Photographer (Foliaphotographer), not upstream Leaves
fun leavesDownloadApiService() = registerIfAbsent(project) {
    downloadsEndpoint = "https://api.leavesmc.org/v2/"
    downloadProjectName = "leaves"
    buildServiceName = "leaves-download-service"
}

val pluginYaml = paperPluginYaml {
    name = "ISeeYou (Folia)"
    main = "cn.xor7.xiaohei.icu.ISeeYouPlugin"
    authors.add("xiaofan")
    description = "Record players in .mcpr format. Folia-only build: requires Folia with Photographer API (e.g. Foliaphotographer). Does not support original Leaves."
    website = "https://github.com/MC-XiaoHei/ISeeYou"
    foliaSupported = true
    apiVersion = "1.21"
    dependencies {
        server("CommandAPI", load = PaperPluginYaml.Load.BEFORE, required = false)
    }
}

val runServerPlugins = runPaper.downloadPluginsSpec {
    modrinth("commandapi", "epl0dnHR")
    modrinth("luckperms", "v5.5.0-bukkit")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.leavesmc.org/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.grim.ac/snapshots")
    flatDir {
        dirs("libs")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.configurateHocon)
    implementation(libs.configurateKotlin)
    compileOnly(libs.commandApiCore)
    compileOnly(libs.commandApiKotlin)

    compileOnly(files("libs/ThemisAPI_0.15.3.jar"))
    compileOnly(files("libs/Matrix_7.15.7.jar"))
    compileOnly(files("libs/VulcanAPI.jar"))
    compileOnly(files("libs/LightAntiCheat.jar"))
    compileOnly(files("libs/SpartanAPI.jar"))
    compileOnly(libs.negativityApi)
    compileOnly(libs.grimApi)

    compileOnly(libs.paperApi)
}

sourceSets {
    main {
        resourceFactory {
            factories(pluginYaml.resourceFactory())
        }
    }
}

tasks {
    runServer {
        downloadsApiService.set(leavesDownloadApiService())
        downloadPlugins.from(runServerPlugins)
        minecraftVersion("1.21.4")
        systemProperty("file.encoding", Charsets.UTF_8.name())
    }

    withType<JavaCompile>().configureEach {
        options.encoding = Charsets.UTF_8.name()
        options.forkOptions.memoryMaximumSize = "6g"
        options.release.set(21)
    }

    shadowJar {
        archiveFileName = "${project.name}-${version}.jar"
    }

    build {
        dependsOn(shadowJar)
    }
}

val targetJavaVersion = 21
java {
    sourceCompatibility = JavaVersion.toVersion(targetJavaVersion)
    targetCompatibility = JavaVersion.toVersion(targetJavaVersion)
}

kotlin {
    compilerOptions.optIn.add("kotlin.io.path.ExperimentalPathApi")
    jvmToolchain(targetJavaVersion)
}