package de.wulkanat.listeners

import de.wulkanat.Channels
import de.wulkanat.grammar.GrammarChecker
import de.wulkanat.io.GrammarEnforcementStrictness
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class GrammarListener : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val channel = Channels.channels.find { it.id == event.channel.idLong } ?: return
        val result = GrammarChecker.check(event.message.contentRaw) ?: return

        when (channel.mode) {
            GrammarEnforcementStrictness.CLASSIC -> {
                event.message.channel.sendMessage(result.joinToString(prefix = "*", separator = " ")).queue()
            }
            GrammarEnforcementStrictness.IMPERIAL -> {
                event.message.delete().queue()
                event.message.channel.sendMessage(EmbedBuilder()
                    .setTitle("Errors found. Correct them at once.")
                    .setDescription(result.joinToString("\n"))
                    .setColor(Color.RED)
                    .build())
                    .queue()
            }
        }
    }
}