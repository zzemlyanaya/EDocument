package ru.zzemlyanaya.edocument.data.models

data class Result<T>(
    val error: String?,
    val data: T?
)