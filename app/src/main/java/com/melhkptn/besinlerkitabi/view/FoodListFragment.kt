package com.melhkptn.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.melhkptn.besinlerkitabi.R
import com.melhkptn.besinlerkitabi.adapter.FoodRecyclerAdapter
import com.melhkptn.besinlerkitabi.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel: FoodListViewModel
    private val recyclerAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = recyclerAdapter

        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.VISIBLE
            textViewError.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModel.refreshDataOnline()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {

        viewModel.foodList.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.visibility = View.VISIBLE
                recyclerAdapter.updateFoodList(it)
            }
        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it)
                textViewError.visibility = View.VISIBLE
            else
                textViewError.visibility = View.GONE
        })

        viewModel.loadingProgressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                recyclerView.visibility = View.GONE
                textViewError.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else
                progressBar.visibility = View.GONE
        })
    }

}