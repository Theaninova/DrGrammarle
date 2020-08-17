package de.wulkanat.io

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class DiscordChannel(
    val id: Long,
    val mode: GrammarEnforcementStrictness
)

@Serializable
enum class GrammarEnforcementStrictness {
    @SerialName("classic") CLASSIC,
    @SerialName("imperial") IMPERIAL
}

@Serializable
data class AdminFile(
    val adminId: Long,
    val token: String
)

val SERVERS_FILE = File("servers.json")
val TEST_FILE = File("test.json")
val ADMIN_FILE = File("admin.json")
