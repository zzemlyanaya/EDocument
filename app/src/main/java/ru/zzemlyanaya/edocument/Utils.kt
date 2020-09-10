package ru.zzemlyanaya.edocument

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

object Const {
    const val TAG = "Host Card Emulator"
    const val STATUS_SUCCESS = "9000"
    const val STATUS_FAILED = "6F00"
    const val CLA_NOT_SUPPORTED = "6E00"
    const val INS_NOT_SUPPORTED = "6D00"
    const val AID = "A0000002471001"
    const val SELECT_INS = "A4"
    const val DEFAULT_CLA = "00"
    const val MIN_APDU_LENGTH = 12

    const val PASSPORT_FLAG = 0
}

object NFCUtil{
    private const val HEX_CHARS = "0123456789ABCDEF"
    fun hexStringToByteArray(data: String) : ByteArray {

        val result = ByteArray(data.length / 2)

        for (i in data.indices step 2) {
            val firstIndex = HEX_CHARS.indexOf(data[i])
            val secondIndex = HEX_CHARS.indexOf(data[i + 1])

            val octet = firstIndex.shl(4).or(secondIndex)
            result[i.shr(1)] = octet.toByte()
        }

        return result
    }

    private val HEX_CHARS_ARRAY = "0123456789ABCDEF".toCharArray()
    fun toHex(byteArray: ByteArray) : String {
        val result = StringBuffer()

        byteArray.forEach {
            val octet = it.toInt()
            val firstIndex = (octet and 0xF0).ushr(4)
            val secondIndex = octet and 0x0F
            result.append(HEX_CHARS_ARRAY[firstIndex])
            result.append(HEX_CHARS_ARRAY[secondIndex])
        }

        return result.toString()
    }
}
