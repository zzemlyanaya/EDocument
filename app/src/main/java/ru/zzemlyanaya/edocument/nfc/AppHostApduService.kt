package ru.zzemlyanaya.edocument.nfc

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log



class AppHostApduService : HostApduService() {

    companion object {
        val TAG = "Host Card Emulator"
        val STATUS_SUCCESS = "9000"
        val STATUS_FAILED = "6F00"
        val CLA_NOT_SUPPORTED = "6E00"
        val INS_NOT_SUPPORTED = "6D00"
        val AID = "A0000002471001"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }



    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        val hexCommandApdu = toHex(commandApdu)
        if (hexCommandApdu.length < MIN_APDU_LENGTH) {
            return hexStringToByteArray(STATUS_FAILED)
        }

        if (hexCommandApdu.substring(0, 2) != DEFAULT_CLA) {
            return hexStringToByteArray(CLA_NOT_SUPPORTED)
        }

        if (hexCommandApdu.substring(2, 4) != SELECT_INS) {
            return hexStringToByteArray(INS_NOT_SUPPORTED)
        }

        return if (hexCommandApdu.substring(10, 24) == AID)  {
            "Иван Иванович Иванов;64 лет;Паспорт 6555 181781".toByteArray()
        } else {
            hexStringToByteArray(STATUS_FAILED)
        }
    }

    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "Deactivated: $reason")
    }

    private val HEX_CHARS = "0123456789ABCDEF"
    fun hexStringToByteArray(data: String) : ByteArray {

        val result = ByteArray(data.length / 2)

        for (i in 0 until data.length step 2) {
            val firstIndex = HEX_CHARS.indexOf(data[i])
            val secondIndex = HEX_CHARS.indexOf(data[i + 1])

            val octet = firstIndex.shl(4).or(secondIndex)
            result.set(i.shr(1), octet.toByte())
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