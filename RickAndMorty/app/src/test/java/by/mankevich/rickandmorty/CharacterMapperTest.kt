package by.mankevich.rickandmorty

import by.mankevich.rickandmorty.library.api.response.CharacterResponse
import by.mankevich.rickandmorty.library.api.response.LocationResponse
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.Location
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity
import by.mankevich.rickandmorty.library.db.entity.parseToLocation
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
class CharacterMapperTest {

    private companion object {
        @JvmStatic
        fun provideLocationPositiveData(): Stream<Arguments> {
            val locationResponse1 = LocationResponse(
                "earth", "https://rickandmortyapi.com/api/location/1"
            )

            val locationResponse126 = LocationResponse(
                "Rick's Memories", "https://rickandmortyapi.com/api/location/126"
            )

            val locationExpected1 = Location(1, "earth")
            val locationExpected126 = Location(126, "Rick's Memories")

            return Stream.of(
                Arguments.of(locationExpected1, locationResponse1),
                Arguments.of(locationExpected126, locationResponse126),
            )
        }

        @JvmStatic
        fun provideLocationNegativeData(): Stream<Arguments> {
            val locationResponse1 = LocationResponse(
                "upiter", "https://rickandmortyapi.com/api/location/1"
            )

            val locationResponse126 = LocationResponse(
                "Rick's Memories", "https://rickandmortyapi.com/api/location/126"
            )

            val locationExpected1 = Location(1, "earth")
            val locationExpected126 = Location(10, "Rick's Memories")

            return Stream.of(
                Arguments.of(locationExpected1, locationResponse1),
                Arguments.of(locationExpected126, locationResponse126),
            )
        }

        @JvmStatic
        fun providePositiveData(): Stream<Arguments> {
            val characterResponseVlad = CharacterResponse(
                0,
                "Vlad Mankevich",
                "Alive",
                "Human",
                "Krasavchek",
                "Male",
                LocationResponse("earth", "https://rickandmortyapi.com/api/location/1"),
                LocationResponse("earth", "https://rickandmortyapi.com/api/location/1"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                listOf("https://rickandmortyapi.com/api/episode/1","https://rickandmortyapi.com/api/episode/2")
            )

            val characterResponseBirdperson = CharacterResponse(
                10000,
                "Birdperson",
                "Alive",
                "Alien",
                "Bird-Person",
                "Female",
                LocationResponse("bird world", "https://rickandmortyapi.com/api/location/1000"),
                LocationResponse("upiter", "https://rickandmortyapi.com/api/location/500"),
                "https://rickandmortyapi.com/api/character/avatar/1000.jpeg",
                listOf("https://rickandmortyapi.com/api/episode/1000")
            )

            val characterVladExpected = CharacterEntity(
                0,
                "Vlad Mankevich",
                "Alive",
                "Human",
                "Krasavchek",
                "Male",
                Location(1, "earth"),
                Location(1, "earth"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                listOf(1,2)
            )

            val characterBirdpersonExpected = CharacterEntity(
                10000,
                "Birdperson",
                "Alive",
                "Alien",
                "Bird-Person",
                "Female",
                Location(1000, "bird world"),
                Location(500, "upiter"),
                "https://rickandmortyapi.com/api/character/avatar/1000.jpeg",
                listOf(1000)
            )

            return Stream.of(
                Arguments.of(characterVladExpected, characterResponseVlad),
                Arguments.of(characterBirdpersonExpected, characterResponseBirdperson),
            )
        }

        @JvmStatic
        fun provideNegativeData(): Stream<Arguments> {
            val characterResponseVlad = CharacterResponse(
                0,
                "Vlad ",
                "Alive",
                "Human",
                "Krasavchek",
                "Male",
                LocationResponse("earth", "https://rickandmortyapi.com/api/location/1"),
                LocationResponse("earth", "https://rickandmortyapi.com/api/location/1"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                listOf("https://rickandmortyapi.com/api/episode/1","https://rickandmortyapi.com/api/episode/2")
            )

            val characterResponseBirdperson = CharacterResponse(
                10000,
                "Birdperson",
                "Alive",
                "Alien",
                "Bird-Person",
                "Female",
                LocationResponse("bird world", "https://rickandmortyapi.com/api/location/1000"),
                LocationResponse("upiter", "https://rickandmortyapi.com/api/location/500"),
                "https://rickandmortyapi.com/api/character/avatar/1000.jpeg",
                listOf()
            )

            val characterVladExpected = CharacterEntity(
                0,
                "Vlad Mankevich",
                "Alive",
                "Human",
                "Krasavchek",
                "Male",
                Location(1, "earth"),
                Location(1, "earth"),
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                listOf(1,2)
            )

            val characterBirdpersonExpected = CharacterEntity(
                10000,
                "Birdperson",
                "Alive",
                "Alien",
                "Bird-Person",
                "Female",
                Location(1000, "bird world"),
                Location(500, "upiter"),
                "https://rickandmortyapi.com/api/character/avatar/1000.jpeg",
                listOf(1000)
            )

            return Stream.of(
                Arguments.of(characterVladExpected, characterResponseVlad),
                Arguments.of(characterBirdpersonExpected, characterResponseBirdperson),
            )
        }
    }

    @ParameterizedTest
    @MethodSource("provideLocationPositiveData")
    @Order(1)
    fun testMapToLocationPositive(exp: Location, response: LocationResponse) {
        val act = response.parseToLocation()
        assertEquals(exp, act)
    }

    @ParameterizedTest
    @MethodSource("provideLocationNegativeData")
    @Order(2)
    fun testMapToLocationNegative(exp: Location, response: LocationResponse) {
        val act = response.parseToLocation()
        assertNotEquals(exp, act)
    }

    @ParameterizedTest
    @MethodSource("providePositiveData")
    @Order(10)
    fun testMapToEntityPositive(exp: CharacterEntity, response: CharacterResponse) {
        val act = response.parseToCharacterEntity()
        assertEquals(exp, act)
    }

    @ParameterizedTest
    @MethodSource("provideNegativeData")
    @Order(20)
    fun testMapToEntityNegative(exp: CharacterEntity, response: CharacterResponse) {
        val act = response.parseToCharacterEntity()
        assertNotEquals(exp, act)
    }
}