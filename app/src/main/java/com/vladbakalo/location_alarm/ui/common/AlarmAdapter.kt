package com.vladbakalo.location_alarm.ui.common

import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vladbakalo.location_alarm.common.extentions.getLayoutInflater
import com.vladbakalo.location_alarm.common.helper.validator.TextValidator
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.DistanceRule
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.NotEmptyRule
import com.vladbakalo.location_alarm.databinding.ItemAlarmBinding

class AlarmAdapter(val listener: OnAlarmActionListener): RecyclerView.Adapter<AlarmAdapter.ItemVH>() {

    private val dataList: ArrayList<AlarmData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = parent.getLayoutInflater()
        return ItemVH(ItemAlarmBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = dataList[position]
        holder.binding.model = item
        holder.binding.actionListener = listener
    }

    fun setDate(data: List<AlarmData>){
        dataList.clear()
        dataList.addAll(data)

        notifyDataSetChanged()
    }

    fun validate(recyclerView: RecyclerView): Boolean{
        var isValid = true
        var view: RecyclerView.ViewHolder?
        for (i in 0..itemCount){
            view = recyclerView.findViewHolderForAdapterPosition(i)

            if (view is ItemVH){
                isValid = view.validator.validate() && isValid
            }
        }
        return isValid
    }

    inner class ItemVH(val binding: ItemAlarmBinding): RecyclerView.ViewHolder(binding.root){
        val validator = TextValidator(binding.itemAlarmEtDistanceText)

        init {
            validator.addRule(NotEmptyRule())
                .addRule(DistanceRule())
        }
    }

    interface OnAlarmActionListener{
        fun onRemoveClick(item: AlarmData)
    }
}