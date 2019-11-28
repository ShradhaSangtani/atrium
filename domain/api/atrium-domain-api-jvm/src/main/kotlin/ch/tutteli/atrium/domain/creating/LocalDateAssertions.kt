package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.core.polyfills.loadSingleService
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.creating.changers.ExtractedFeaturePostStep
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.MonthDay

/**
 * The access point to an implementation of [LocalDateAssertions].
 *
 * It loads the implementation lazily via [loadSingleService].
 */
val localDateAssertions by lazy { loadSingleService(LocalDateAssertions::class) }

/**
 * Defines the minimum set of assertion functions and builders applicable to [LocalDate],
 * which an implementation of the domain of Atrium has to provide.
 */
interface LocalDateAssertions {
    fun <T: LocalDate> year(assertionContainer: Expect<T>): ExtractedFeaturePostStep<T, Int>

    fun <T: LocalDate> month(assertionContainer: Expect<T>): ExtractedFeaturePostStep<T, Int>

    fun <T: LocalDate> day(assertionContainer: Expect<T>): ExtractedFeaturePostStep<T, Int>

    fun <T: LocalDate> dayOfWeek(assertionContainer: Expect<T>): ExtractedFeaturePostStep<T, DayOfWeek>
}
