package com.example.chatmessangeregmaryn.domain.type

sealed class Either<out L, out R> {
    /** * Представляет левую часть [Either] класса, которая по соглашению является "Failure */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Представляет правую часть [Either] класса, которая по соглашению является "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>  // возвращает булево значение - является ли объект Either типом Righ
    val isLeft get() = this is Left<L> // возвращает булево значение - является ли объект Either типом Left

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(functionLeft: (L) -> Any, functionRight: (R) -> Any): Any = // данный метод выполняет одну
        when (this) {                                                      //из двух функций высшего порядка
            is Left -> functionLeft(a) // если объект Left, то выполняется функция functionLeft
            is Right -> functionRight(b)// если объект Right, то выполняется функция functionRight
        }
}

fun <A, B, C> ((A) -> B).compose(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> {
    return when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }
}

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> {
    return this.flatMap(fn.compose(::right))
}

fun <L, R> Either<L, R>.onNext(fn: (R) -> Unit): Either<L, R> {
    this.flatMap(fn.compose(::right))
    return this
}
