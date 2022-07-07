package by.mankevich.rickandmorty

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
class LocationMapperTest {

    private companion object {

        @JvmStatic
        fun providePositiveData(): Stream<Arguments> {
            val locationFullResponse47 = LocationFullResponse(
                47,
                "Pluto",
                "Dwarf planet (Celestial Dwarf)",
                "Replacement Dimension",
                listOf(
                    "https://rickandmortyapi.com/api/character/1",
                    "https://rickandmortyapi.com/api/character/192"
                )
            )

            val locationFullResponse126 = LocationFullResponse(
                126,
                "Rick's Memories",
                "Memory",
                "",
                listOf(
                    "https://rickandmortyapi.com/api/character/815",
                    "https://rickandmortyapi.com/api/character/814"
                )
            )

            val locationExpected47 = LocationEntity(
                47,
                "Pluto",
                "Dwarf planet (Celestial Dwarf)",
                "Replacement Dimension",
                listOf(1, 192)
            )

            val locationExpected126 = LocationEntity(
                126,
                "Rick's Memories",
                "Memory",
                "",
                listOf(815, 814)
            )

            return Stream.of(
                Arguments.of(locationExpected47, locationFullResponse47),
                Arguments.of(locationExpected126, locationFullResponse126),
            )
        }

        @JvmStatic
        fun provideNegativeData(): Stream<Arguments> {
            val locationFullResponse47 = LocationFullResponse(
                47,
                "Earth",
                "Dwarf planet (Celestial Dwarf)",
                "Replacement Dimension",
                listOf(
                    "https://rickandmortyapi.com/api/character/1",
                    "https://rickandmortyapi.com/api/character/192"
                )
            )

            val locationFullResponse126 = LocationFullResponse(
                126,
                "Rick's Memories",
                "Memory",
                "",
                listOf(
                    "https://rickandmortyapi.com/api/character/815",
                    "https://rickandmortyapi.com/api/character/814"
                )
            )

            val locationExpected47 = LocationEntity(
                47,
                "Pluto",
                "Dwarf planet (Celestial Dwarf)",
                "Replacement Dimension",
                listOf(1, 192)
            )

            val locationExpected126 = LocationEntity(
                126,
                "Rick's Memories",
                "Memory",
                "",
                listOf(815)
            )

            return Stream.of(
                Arguments.of(locationExpected47, locationFullResponse47),
                Arguments.of(locationExpected126, locationFullResponse126),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("providePositiveData")
    @Order(10)
    fun testMapToEntityPositive(exp: LocationEntity, response: LocationFullResponse) {
        val act = response.parseToLocationEntity()
        assertEquals(exp, act)
    }

    @ParameterizedTest
    @MethodSource("provideNegativeData")
    @Order(20)
    fun testMapToEntityNegative(exp: LocationEntity, response: LocationFullResponse) {
        val act = response.parseToLocationEntity()
        assertNotEquals(exp, act)
    }
}