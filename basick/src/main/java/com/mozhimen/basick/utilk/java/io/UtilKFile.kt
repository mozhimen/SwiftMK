package com.mozhimen.basick.utilk.java.io

import android.util.Log
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.util.UtilKDate
import java.io.File
import java.io.FileInputStream
import java.util.Locale

/**
 * @ClassName UtilKFile
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
fun String.getStrFolderPath(): String =
    UtilKFile.getStrFolderPath(this)

@AManifestKRequire(CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE)
object UtilKFile : BaseUtilK() {

    /**
     * 当前小时转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrCurrentHour(locale: Locale = Locale.CHINA): String =
        getStrFileNameForStrDate(CDateFormat.yyyyMMddHH, locale)

    /**
     * 当前时间转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrNowDate(locale: Locale = Locale.CHINA): String =
        getStrFileNameForStrDate(locale = locale)

    /**
     * 时间转文件名
     * @param formatDate String
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrDate(formatDate: String = CDateFormat.yyyyMMddHHmmss, locale: Locale = Locale.CHINA): String =
        UtilKDate.getNowStr(formatDate, locale).replace(" ", "~").replace(":", "-")

    //region # file
    /**
     * 判断是否为文件
     * @param filePathWithName String
     * @return Boolean
     */
    @JvmStatic
    fun isFile(filePathWithName: String): Boolean =
        isFile(filePathWithName.strFilePath2file())

    /**
     * 判断是否为文件
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFile(file: File): Boolean =
        file.exists() && file.isFile

    /**
     * 文件是否存在
     * @param filePathWithName String
     * @return Boolean
     */
    @JvmStatic
    fun isFileExist(filePathWithName: String) =
        isFileExist(filePathWithName.strFilePath2file())

    /**
     * 文件是否存在
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFileExist(file: File): Boolean =
        isFile(file)

    /**
     * 创建文件
     * @param filePathWithName String
     * @return File
     */
    @JvmStatic
    fun createFile(filePathWithName: String): File =
        createFile(filePathWithName.strFilePath2file())

    /**
     * 创建文件
     * @param file File
     * @return File
     */
    @JvmStatic
    fun createFile(file: File): File {
        file.parent?.let { createFolder(it) } ?: throw Exception("don't have parent folder")
        Log.d(TAG, "createFile: file ${file.absolutePath}")
        if (!isFileExist(file))
            file.createNewFile()
        return file
    }

    /**
     * 删除文件
     * @param filePathWithName String
     */
    @JvmStatic
    fun deleteFile(filePathWithName: String): Boolean =
        deleteFile(filePathWithName.strFilePath2file())

    /**
     * 删除文件
     * @param file File
     */
    @JvmStatic
    fun deleteFile(file: File): Boolean =
        if (isFileExist(file)) {
            file.delete().also {
                "deleteFile: file ${file.absolutePath} success".dt(TAG)
            }
        } else false

    /**
     * 批量删除
     * @param files Array<out File>
     */
    fun deleteFiles(vararg files: File) {
        for (file in files)
            deleteFile(file)
    }

    /**
     * 复制文件
     * @param sourceFilePathWithName String
     * @param destFilePathWithName String
     */
    @JvmStatic
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        copyFile(sourceFilePathWithName.strFilePath2file(), destFilePathWithName.strFilePath2file(), isOverwrite)

    /**
     * 复制文件
     * @param sourceFile File
     * @param destFile File
     */
    @JvmStatic
    fun copyFile(sourceFile: File, destFile: File, isOverwrite: Boolean = true): File? =
        if (!isFileExist(sourceFile)) null
        else FileInputStream(sourceFile).use { it.inputStream2file(destFile, isOverwrite) }

    /**
     * 获取文件大小
     * @param filePathWithName String
     * @return Long
     */
    @JvmStatic
    fun getFileSizeAvailable(filePathWithName: String): Long =
        if (filePathWithName.isEmpty()) 0L
        else getFileSizeAvailable(filePathWithName.strFilePath2file())


    /**
     * 获取文件大小
     * @param file File
     * @return Long
     */
    @JvmStatic
    fun getFileSizeAvailable(file: File): Long =
        if (!isFileExist(file)) 0L
        else FileInputStream(file).use { it.getAvailableLong() }

    /**
     * 获取文件大小
     * @param filePathWithName String
     * @return Long
     */
    @JvmStatic
    fun getFileSizeTotal(filePathWithName: String): Long =
        if (filePathWithName.isEmpty()) 0L
        else getFileSizeTotal(filePathWithName.strFilePath2file())

    /**
     * 获取文件大小
     * file.length() 方法返回文件的长度，单位是字节，表示整个文件的大小。而 inputStream.available() 方法返回的是当前输入流中可读取的字节数，它可能小于或等于文件的长度，具体取决于输入流的类型和状态。
     * @param file File
     * @return Long
     */
    @JvmStatic
    fun getFileSizeTotal(file: File): Long =
        if (!isFileExist(file)) 0L
        else file.length()
//endregion

/////////////////////////////////////////////////////////////////////////////////////////

//region # folder
    /**
     * 判断是否是文件夹
     * @param folderPath String
     * @return Boolean
     */
    @JvmStatic
    fun isFolder(folderPath: String): Boolean =
        isFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 判断是否是文件夹
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolder(folder: File): Boolean =
        folder.exists() && folder.isDirectory

    /**
     * 文件夹是否存在
     * @param folderPath String
     * @return Boolean
     */
    @JvmStatic
    fun isFolderExist(folderPath: String): Boolean =
        isFolderExist(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 文件夹是否存在
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolderExist(folder: File): Boolean =
        isFolder(folder)

    /**
     * 创建文件夹
     * @param folderPath String
     * @return File
     */
    @JvmStatic
    fun createFolder(folderPath: String): File =
        createFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 创建文件夹
     * @param folder File
     * @return File
     */
    @JvmStatic
    fun createFolder(folder: File): File {
        if (!isFolderExist(folder)) folder.mkdirs()
        return folder
    }

    /**
     * 删除文件夹
     * @param folderPath String
     * @return Boolean
     */
    @JvmStatic
    fun deleteFolder(folderPath: String): Boolean =
        deleteFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 删除文件夹
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun deleteFolder(folder: File): Boolean {
        if (!isFolderExist(folder)) return false
        val listFiles: Array<File> = folder.listFiles() ?: emptyArray()
        if (listFiles.isNotEmpty()) {
            for (file in listFiles) {
                if (isFolder(file)) { // 判断是否为文件夹
                    deleteFolder(file)
                    file.delete()
                } else
                    deleteFile(file)
            }
        }
        return true
    }

/////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrFolderPath(folderPath: String): String =
        if (!folderPath.endsWith("/")) "$folderPath/" else folderPath
//endregion
}

