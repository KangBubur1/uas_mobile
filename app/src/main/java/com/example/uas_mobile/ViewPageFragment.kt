package com.example.uas_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.swipebutton_library.SwipeButton
import com.example.uas_mobile.screen.FirstScreen
import com.example.uas_mobile.screen.SecondScreen
import com.example.uas_mobile.screen.ThirdScreen




class ViewPageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_page, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)

        // Assign the adapter to the ViewPager2
        viewPager.adapter = adapter



        return view
    }



}