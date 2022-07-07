package by.mankevich.rickandmorty

import by.mankevich.rickandmorty.library.api.response.EpisodeResponse
import by.mankevich.rickandmorty.library.api.response.LocationFullResponse
import by.mankevich.rickandmorty.library.db.entity.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@TestMethodOrder(OrderAnnotation::class)
class EpisodeMapperTest {

    private companion object {

        @JvmStatic
        fun providePositiveData(): Stream<Arguments> {
            val episodeResponse1 = EpisodeResponse(
                1,
                "Pilot",
                "December 2, 2013)",
                "S01E01",
                listOf(
                    "https://rickandmortyapi.com/api/character/1",
                    "https://rickandmortyapi.com/api/character/192"
                )
            )

            val episodeResponse51 = EpisodeResponse(
                51,
                "Rickmurai Jack",
                "September 5, 2021",
                "S05E10",
                listOf(
                    "https://rickandmortyapi.com/api/character/815",
                    "https://rickandmortyapi.com/api/character/814"
                )
            )

            val episodeExpected1 = EpisodeEntity(
                1,
                "Pilot",
                "December 2, 2013)",
                "S01E01",
                listOf(1, 192)
            )

            val episodeExpected51 = EpisodeEntity(
                51,
                "Rickmurai Jack",
                "September 5, 2021",
                "S05E10",
                listOf(815, 814)
            )

            return Stream.of(
                Arguments.of(episodeExpected1, episodeResponse1),
                Arguments.of(episodeExpected51, episodeResponse51),
            )
        }

        @JvmStatic
        fun provideNegativeData(): Stream<Arguments> {
            val episodeResponse1 = EpisodeResponse(
                1,
                "qwerty",
                "December 2, 2013)",
                "S01E01",
                listOf(
                    "https://rickandmortyapi.com/api/character/1",
                    "https://rickandmortyapi.com/api/character/192"
                )
            )

            val episodeResponse51 = EpisodeResponse(
                51,
                "Rickmurai Jack",
                "September 5, 2021",
                "S05E10",
                listOf(
                    "https://rickandmortyapi.com/api/character/815",
                    "https://rickandmortyapi.com/api/character/814"
                )
            )

            val episodeExpected1 = EpisodeEntity(
                1,
                "Pilot",
                "December 2, 2013)",
                "S01E01",
                listOf(1, 192)
            )

            val episodeExpected51 = EpisodeEntity(
                51,
                "Rickmurai Jack",
                "September 5, 2021",
                "S5E10",
                listOf(815, 814)
            )

            return Stream.of(
                Arguments.of(episodeExpected1, episodeResponse1),
                Arguments.of(episodeExpected51, episodeResponse51),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("providePositiveData")
    @Order(10)
    fun testMapToEntityPositive(exp: EpisodeEntity, response: EpisodeResponse) {
        val act = response.parseToEpisodeEntity()
        assertEquals(exp, act)
    }

    @ParameterizedTest
    @MethodSource("provideNegativeData")
    @Order(20)
    fun testMapToEntityNegative(exp: EpisodeEntity, response: EpisodeResponse) {
        val act = response.parseToEpisodeEntity()
        assertNotEquals(exp, act)
    }
}