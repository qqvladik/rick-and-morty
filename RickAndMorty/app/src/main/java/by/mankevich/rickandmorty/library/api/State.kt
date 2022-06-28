package by.mankevich.rickandmorty.library.api

sealed class State<T> {
    class Loading<T> : State<T>()
    class Data<T>(val data: T) : State<T>()
    class Error<T>(val error: Exception) : State<T>()
}