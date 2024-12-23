package com.ferraz.playshow.core.exceptions

class ServerException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)