package com.sk.autotp.services

import com.sk.autotp.data.Rule

object SmsFilter {

    fun isApplicable(messageBody: String, rule: Rule): Boolean {
        require(rule.enabled) { "Can't forward message for disabled rule" }

        val keywords = rule.keywords.split(',')

        val allKeywordsFound = keywords.all { keyword ->
            messageBody.contains(keyword, true)
        }
        return allKeywordsFound
    }
}