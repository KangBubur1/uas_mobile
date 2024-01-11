package com.example.uas_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.uas_mobile.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val bookList = arrayListOf<BookModel>()
        bookList.add(BookModel(R.drawable.batman, "batman"))
        bookList.add(BookModel(R.drawable.seavoyager, "sea voyager"))
        bookList.add(BookModel(R.drawable.harrypotter, "harry potter"))
        bookList.add(BookModel(R.drawable.mazerunner, "maze runner"))
        bookList.add(BookModel(R.drawable.rudy, "rudy"))
        bookList.add(BookModel(R.drawable.monsterhunt, "monster hunter"))
        bookList.add(BookModel(R.drawable.thedragonp, "the dragon princess"))

        val adapter = BookAdapter(bookList)

        binding.apply {
            carouselRecycleview.adapter = adapter
            carouselRecycleview.set3DItem(true)
            carouselRecycleview.setAlpha(true)
            carouselRecycleview.setInfinite(true)
        }

        val todayDateTextView: TextView = view.findViewById(R.id.todayDateTextView)

        // Get current date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Set the text of todayDateTextView
        todayDateTextView.text = currentDate

        val btnShowMore = view.findViewById<TextView>(R.id.showMore)
        btnShowMore.setOnClickListener {
            val homeActivity = activity as? HomeActivity
            homeActivity?.loadFragment(CatalogFragment())

        }

        return view


    }


}