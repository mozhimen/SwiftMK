package com.mozhimen.abilityk.zipk

import android.content.res.AssetManager
import android.opengl.ETC1
import android.opengl.ETC1Util.ETC1Texture
import android.util.Log
import com.mozhimen.basick.utilk.context.UtilKApplication
import com.mozhimen.basick.utilk.UtilKString
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * @ClassName ZipKPkmReader
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/28 23:26
 * @Version 1.0
 */
class ZipKPkmReader {
    private val _context = UtilKApplication.instance.get()!!
    private var _path: String? = null
    private var _zipInputStream: ZipInputStream? = null
    private var _manager: AssetManager = _context.assets
    private var _zipEntry: ZipEntry? = null
    private var _headerBuffer: ByteBuffer? = null

    companion object {
        private const val TAG = "ZipKPkmReader>>>>>"
    }

    fun setZipPath(path: String) {
        this._path = path
    }

    fun open(): Boolean {
        if (_path == null && UtilKString.isEmpty(_path)) {
            _zipInputStream = null
            _zipEntry = null
            return false
        }
        return try {
            _zipInputStream = if (_path!!.startsWith("assets/")) {
                val inputStream = _manager.open(_path!!.substring(7))
                ZipInputStream(inputStream)
            } else {
                ZipInputStream(FileInputStream(_path))
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun close() {
        if (_zipInputStream != null) {
            try {
                _zipInputStream!!.closeEntry()
                _zipInputStream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _zipInputStream = null
                _zipEntry = null
            }
        }
        if (_headerBuffer != null) {
            _headerBuffer!!.clear()
            _headerBuffer = null
        }
    }

    fun getNextStream(): InputStream? {
        return if (hasElements()) {
            _zipInputStream
        } else null
    }

    fun getNextTexture(): ETC1Texture? {
        if (hasElements()) {
            try {
                return createTexture(_zipInputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun hasElements(): Boolean {
        try {
            if (_zipInputStream != null) {
                _zipEntry = _zipInputStream!!.nextEntry
                if (_zipEntry != null) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @Throws(IOException::class)
    private fun createTexture(input: InputStream?): ETC1Texture {
        var width = 0
        var height = 0
        val ioBuffer = ByteArray(4096)
        run {
            if (input!!.read(ioBuffer, 0, ETC1.ETC_PKM_HEADER_SIZE) != ETC1.ETC_PKM_HEADER_SIZE) {
                Log.e(TAG, ioBuffer.contentToString())
                throw IOException("Unable to read PKM file header.")
            }
            if (_headerBuffer == null) {
                _headerBuffer = ByteBuffer.allocateDirect(ETC1.ETC_PKM_HEADER_SIZE)
                    .order(ByteOrder.nativeOrder())
            }
            _headerBuffer!!.put(ioBuffer, 0, ETC1.ETC_PKM_HEADER_SIZE).position(0)
            if (!ETC1.isValid(_headerBuffer)) {
                throw IOException("Not a PKM file.")
            }
            width = ETC1.getWidth(_headerBuffer)
            height = ETC1.getHeight(_headerBuffer)
        }
        val encodedSize = ETC1.getEncodedDataSize(width, height)
        val dataBuffer = ByteBuffer.allocateDirect(encodedSize).order(ByteOrder.nativeOrder())
        var len: Int
        while (input!!.read(ioBuffer).also { len = it } != -1) {
            dataBuffer.put(ioBuffer, 0, len)
        }
        dataBuffer.position(0)
        return ETC1Texture(width, height, dataBuffer)
    }
}