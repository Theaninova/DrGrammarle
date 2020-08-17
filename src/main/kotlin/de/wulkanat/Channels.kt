package de.wulkanat

import de.wulkanat.io.DiscordChannel
import de.wulkanat.io.SERVERS_FILE
import de.wulkanat.io.TEST_FILE
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import net.dv8tion.jda.api.JDA

object Channels {
    var jda: JDA? = null
    val json = Json(JsonConfiguration.Stable)

    /**
     * List of (ServerID, ChannelID)
     */
    var channels: MutableList<DiscordChannel> = refreshFromDisk()

    fun refreshFromDisk(): MutableList<DiscordChannel> {
        return json.parse(
            DiscordChannel.serializer().list, (if (Admin.testModeEnabled) {
                TEST_FILE
            } else {
                SERVERS_FILE
            }).readText()
        ).toMutableList()
    }

    fun getServerNames(): List<String> {
        if (jda == null)
            return listOf()

        return channels.map {
            val channel = jda!!.getTextChannelById(it.id)
            if (channel == null) {
                Admin.warning("Channel ${it.id} is no longer active!")
                return@map "**${it.id}** *(inactive)*"
            }

            "**${channel.guild.name}**\n#${channel.name} ${it.mode.name}"
        }
    }
}