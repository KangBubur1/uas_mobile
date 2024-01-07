package com.example.uas_mobile.screen

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.swipebutton_library.OnActiveListener
import com.example.swipebutton_library.SwipeButton
import com.example.uas_mobile.R


class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.isUserInputEnabled = false

        val swipeButton: SwipeButton = view.findViewById(R.id.swipeGetStarted)
        swipeButton.setOnActiveListener(object : OnActiveListener {
            override fun onActive() {
                // Handle navigation to login fragment when swipe is completed
                findNavController().navigate(R.id.action_viewPageFragment_to_homeFragment)
                onBoardingFinished()
            }
        })

        val fsQuotes: TextView = view.findViewById(R.id.fsQuotes)

        val text = fsQuotes.text.toString()
        val words = text.split(" ")

        // Clear any existing text
        fsQuotes.text = null

        words.forEachIndexed { index, word ->
            val space = if (index == 0) "" else " "
            fsQuotes.append("$space$word")
            animateText(fsQuotes, index)
        }


        return view
    }

    private fun animateText(textView: TextView, index: Int) {
        val fadeInAnimator = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f)
        fadeInAnimator.startDelay = (index * 200).toLong() // Adjust delay as needed
        fadeInAnimator.duration = 1000 // Adjust duration as needed
        fadeInAnimator.start()
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}
