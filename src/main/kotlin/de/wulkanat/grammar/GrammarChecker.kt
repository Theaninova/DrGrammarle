package de.wulkanat.grammar

import org.languagetool.JLanguageTool
import org.languagetool.language.AmericanEnglish

object GrammarChecker {
    val languageTool = JLanguageTool(AmericanEnglish())

    fun check(message: String): List<String>? {
        val matches = languageTool.check(message)
        if (matches.size == 0) {
            return null
        }

        return matches.map {
            it.suggestedReplacements.first()
        }
    }
}