package com.instabug.task.ui.words

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.instabug.task.R
import com.instabug.task.ui.words.WordsFragment.Companion.MAIN_FRAGMENT_TAG

class WordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(R.id.frmContainer, WordsFragment.newInstance(), MAIN_FRAGMENT_TAG)
    }

    private fun replaceFragment(containerId: Int, fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment, fragmentTag)
            .commit()
    }
}