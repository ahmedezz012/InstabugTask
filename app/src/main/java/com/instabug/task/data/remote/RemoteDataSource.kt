package com.instabug.task.data.remote

import com.instabug.task.data.model.WordsCount
import com.instabug.task.utils.Constants.Network.CHAR_SET
import com.instabug.task.utils.Constants.Network.INSTA_BUG_URL
import com.instabug.task.utils.Constants.Network.WORDS_REGEX
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.regex.Matcher
import java.util.regex.Pattern


class RemoteDataSource : IRemoteDataSource {
    override fun callInstaBug(): ArrayList<WordsCount>? {
        val content = StringBuilder()
        try {
            val url = URL(INSTA_BUG_URL)
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(
                    InputStreamReader(
                        inputStream,
                        Charset.forName(CHAR_SET)
                    )
                )
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    content.append(line).append("\n")
                }
                return getListOfWordsCount(content.toString())
            } else {
                throw IOException(httpURLConnection.responseMessage)
            }
        } catch (stackOverflowError: StackOverflowError) {
            stackOverflowError.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } catch (error: Error) {
            error.printStackTrace()
        }
        return arrayListOf()
    }

    private fun getListOfWordsCount(htmlResponse: String): ArrayList<WordsCount> {
        val map = getEveryWordAndItsCount(htmlResponse)
        return convertHashMapToArrayList(map)
    }

    private fun getEveryWordAndItsCount(htmlResponse: String): HashMap<String, Int> {
        val map: HashMap<String, Int> = hashMapOf()
        val regex = WORDS_REGEX
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(htmlResponse)
        while (matcher.find()) {
            if (map.containsKey(matcher.group().toString())) {
                var count: Int = map[matcher.group().toString()] ?: 1
                count++
                map[matcher.group().toString()] = count
            } else {
                map[matcher.group().toString()] = 1
            }
        }
        return map
    }

    private fun convertHashMapToArrayList(map: HashMap<String, Int>): ArrayList<WordsCount> {
        val arrayList = arrayListOf<WordsCount>()
        for ((key, value) in map) {
            arrayList.add(WordsCount(key, value))
        }
        return arrayList
    }
}