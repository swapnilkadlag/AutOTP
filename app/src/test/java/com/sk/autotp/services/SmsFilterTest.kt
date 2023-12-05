package com.sk.autotp.services

import com.sk.autotp.data.Contact
import com.sk.autotp.data.Rule
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class SmsFilterTest {

    private fun getRuleFor(keywords: String, enabled: Boolean): Rule {
        return Rule(
            id = UUID.randomUUID(),
            createdAt = Instant.now(),
            enabled = enabled,
            title = "Test Rule",
            keywords = keywords,
            prefix = "",
            notify = false,
            contact = Contact(name = "Name", number = "382749287348"),
        )
    }

    @Test
    fun `isApplicable throws exception for disabled rule`() {
        assertThatThrownBy {
            SmsFilter.isApplicable(
                messageBody = "test message body",
                rule = getRuleFor(
                    enabled = false,
                    keywords = "OTP, test",
                ),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `isApplicable returns true for sms containing all required keywords`() {
        Assertions.assertThat(
            SmsFilter.isApplicable(
                messageBody = "Message containing OTP for Hotstar",
                rule = getRuleFor(
                    enabled = true,
                    keywords = "OTP, Hotstar",
                ),
            )
        ).isTrue
    }

    @Test
    fun `isApplicable returns true for sms containing all required keywords ignoring case`() {
        Assertions.assertThat(
            SmsFilter.isApplicable(
                messageBody = "Message containing otp for Hotstar",
                rule = getRuleFor(
                    enabled = true,
                    keywords = "OTP,hotstar",
                ),
            )
        ).isTrue
    }

    @Test
    fun `isApplicable returns false for sms containing no required keywords`() {
        Assertions.assertThat(
            SmsFilter.isApplicable(
                messageBody = "Hello, just checking on ya",
                rule = getRuleFor(
                    enabled = true,
                    keywords = "OTP, Hotstar",
                ),
            )
        ).isFalse
    }

    @Test
    fun `isApplicable returns false for sms containing some required keywords`() {
        Assertions.assertThat(
            SmsFilter.isApplicable(
                messageBody = "Here is your OTP",
                rule = getRuleFor(
                    enabled = true,
                    keywords = "OTP, Hotstar",
                ),
            )
        ).isFalse
    }
}