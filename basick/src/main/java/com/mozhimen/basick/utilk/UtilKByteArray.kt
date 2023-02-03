package com.mozhimen.basick.utilk


/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */
object UtilKByteArray {
    /**
     * 合并Bytes
     * @param bytes1 ByteArray
     * @param bytes2 ByteArray
     * @return ByteArray?
     */
    @JvmStatic
    fun joinBytes(bytes1: ByteArray, bytes2: ByteArray): ByteArray? {
        val bytes3 = ByteArray(bytes1.size + bytes2.size)
        System.arraycopy(bytes1, 0, bytes3, 0, bytes1.size)
        System.arraycopy(bytes2, 0, bytes3, bytes1.size, bytes2.size)
        return bytes3
    }

    /**
     * 截取Bytes
     * @param bytes ByteArray
     * @param offset Int
     * @param length Int
     * @return ByteArray
     */
    @JvmStatic
    fun subBytes(bytes: ByteArray, offset: Int, length: Int): ByteArray {
        val bytes1 = ByteArray(length)
        System.arraycopy(bytes, offset, bytes1, 0, length)
        return bytes1
    }

    /**
     * 转HexString
     * @param bytes ByteArray
     * @param size Int
     * @return String
     */
    @JvmStatic
    fun bytes2HexStr(bytes: ByteArray, size: Int): String {
        val sb = StringBuilder()
        for (i in 0 until size) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}