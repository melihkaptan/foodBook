package com.melhkptn.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.melhkptn.besinlerkitabi.R
import com.melhkptn.besinlerkitabi.databinding.FragmentFoodDetailBinding
import com.melhkptn.besinlerkitabi.util.downloadImage
import com.melhkptn.besinlerkitabi.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {

    private var foodId = 0
    private lateinit var viewModel: FoodDetailViewModel
    private lateinit var dataBinding: FragmentFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_detail, container, false)
        //return inflater.inflate(R.layout.fragment_food_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(
                it
            ).foodId
        }

        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoomData(foodId)
        observeData()

    }

    private fun observeData() {
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer {

            it?.let {
                dataBinding.foodDetail = it
                /*
                foodName.text = it.name
                foodCalorie.text = it.calorie
                foodCarbonhidrat.text = it.carbohydrate
                foodFat.text = it.fat
                foodProtein.text = it.protein
                context?.let { context -> imageView.downloadImage(it.foodImage, context) }
                 */
            }
        })
    }
}