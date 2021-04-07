package com.melhkptn.besinlerkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.melhkptn.besinlerkitabi.R
import com.melhkptn.besinlerkitabi.databinding.FoodRowBinding
import com.melhkptn.besinlerkitabi.model.Food
import com.melhkptn.besinlerkitabi.util.downloadImage
import com.melhkptn.besinlerkitabi.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_row.view.*

class FoodRecyclerAdapter(private val foodList: ArrayList<Food>) :
    RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() , FoodClickListener {

    class FoodViewHolder(var view: FoodRowBinding) : RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.food_row, parent,false)
        val view =
            DataBindingUtil.inflate<FoodRowBinding>(inflater, R.layout.food_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.food = foodList[position]
        holder.view.listener = this

       /*
        holder.itemView.textViewName.text = foodList[position].name
        holder.itemView.textViewCalorie.text = foodList[position].calorie
        holder.itemView.imageViewRow.downloadImage(
            foodList[position].foodImage,
            holder.itemView.context
        )

        holder.itemView.setOnClickListener {
            val action =
                FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        */
    }

    fun updateFoodList(newFoodList: List<Food>) {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun foodClicked(view: View) {

        val action =
            FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(view.foodUuid.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }


}