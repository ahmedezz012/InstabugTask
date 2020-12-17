package com.instabug.task.ui.words

import android.content.Context
import com.instabug.task.data.local.ILocalDataSource
import com.instabug.task.data.model.Status
import com.instabug.task.data.model.StatusCode
import com.instabug.task.data.model.WordsCount
import com.instabug.task.data.remote.IRemoteDataSource
import com.instabug.task.utils.Utils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WordsRepositoryTest {

    @RelaxedMockK
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var remoteDataSource: IRemoteDataSource

    @RelaxedMockK
    private lateinit var localDataSource: ILocalDataSource

    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkObject(Utils)

        wordsRepository = WordsRepository(context, remoteDataSource, localDataSource)
    }

    @Test
    fun `given no network and no offline data, when call Repo_getWordsList, then return status NO_NETWORK`() {
        //GIVEN
        every { Utils.checkConnection(context) }.answers { false }
        every { localDataSource.selectAll() }.answers { arrayListOf() }

        //WHEN
        val actualWordsListStatus = wordsRepository.getWordsList()

        //THEN
        assertEquals(StatusCode.NO_NETWORK, actualWordsListStatus.statusCode)
    }

    @Test
    fun `given network, when call Repo_getWordsList, then return status Success`() {
        //GIVEN
        every { Utils.checkConnection(context) }.answers { true }
        every { remoteDataSource.callInstaBug() }.answers {
            arrayListOf(
                WordsCount("word1", 2),
                WordsCount("word2", 3)
            )
        }
        //WHEN
        val actualWordsListStatus = wordsRepository.getWordsList()

        //THEN
        assertEquals(StatusCode.SUCCESS, actualWordsListStatus.statusCode)
        assertEquals(actualWordsListStatus.data!!.size, 2)
    }

    @Test
    fun `given network and no data retrieved, when call Repo_getWordsList, then return status Error`() {
        //GIVEN
        every { Utils.checkConnection(context) }.answers { true }
        every { remoteDataSource.callInstaBug() }.answers { arrayListOf() }

        //WHEN
        val actualWordsListStatus = wordsRepository.getWordsList()

        //THEN
        assertEquals(StatusCode.ERROR, actualWordsListStatus.statusCode)
    }

    @Test
    fun `given no network and there is offline data, when call Repo_getWordsList, then return status Offline data`() {
        //GIVEN
        every { Utils.checkConnection(context) }.answers { false }
        every { localDataSource.selectAll() }.answers {
            arrayListOf(
                WordsCount("word1", 2),
                WordsCount("word2", 3)
            )
        }

        //WHEN
        val actualWordsListStatus = wordsRepository.getWordsList()

        //THEN
        assertEquals(StatusCode.OFFLINE_DATA, actualWordsListStatus.statusCode)
        assertEquals(actualWordsListStatus.data!!.size, 2)
    }
}