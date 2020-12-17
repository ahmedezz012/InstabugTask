package com.instabug.task.data.model

import androidx.annotation.StringRes
import com.instabug.task.R

open class Status<T>(
    val statusCode: StatusCode = StatusCode.IDLE,
    var data: T? = null,
    @StringRes
    val message: Int = STATUS_NO_MESSAGE
) {
    companion object {
        const val STATUS_NO_MESSAGE = -1

    }

    class Success<T>(
        data: T? = null,
        msg: Int = STATUS_NO_MESSAGE
    ) : Status<T>(StatusCode.SUCCESS, data, msg)

    class Error<T>(
        message: Int = R.string.someThingWentWrong,
        data: T?
    ) : Status<T>(StatusCode.ERROR, data, message)


    class NoNetwork<T>(
        data: T? = null,
        message: Int = R.string.noInternet
    ) :
        Status<T>(StatusCode.NO_NETWORK, data, message)

    class Idle<T>(data: T? = null) : Status<T>(StatusCode.IDLE, data, STATUS_NO_MESSAGE)

    class OfflineData<T>(data: T) : Status<T>(StatusCode.OFFLINE_DATA, data, STATUS_NO_MESSAGE)
}