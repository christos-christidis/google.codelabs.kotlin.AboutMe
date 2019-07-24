package com.kotlin.aboutme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.kotlin.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName = MyName("Christos Christidis")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // SOS: I tested this: changing the bound variable causes a layout update!
        binding.myName = myName

        binding.apply {
            doneButton.setOnClickListener { addNickname(it) }
            nicknameText.setOnClickListener { updateNickname() }
        }
    }

    private fun addNickname(view: View) {
        binding.apply {
            // SOS: this myName refers to binding.myName, NOT the private property defined above!
            // Note the safe-call, it seems that the bound variable is of type MyName?
            myName?.nickname = nicknameEdit.text.toString()
            // SOS: changing a property of the bound variable does NOT cause a layout update (unless
            // that property is LiveData and we've called binding.setLifecycleOwner(myFragment) as we
            // did in dev.essentials/ViewModelDemo). Thus, we have to call binding.invalidateAll
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

        // hide keyboard
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun updateNickname() {
        binding.apply {
            nicknameEdit.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE
            nicknameText.visibility = View.GONE
            nicknameEdit.requestFocus()
        }

        // show keyboard
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.nicknameEdit, 0)
    }
}
