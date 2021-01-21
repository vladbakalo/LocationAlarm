package com.vladbakalo.location_alarm.ui.fragment.distance_alarm_list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladbakalo.location_alarm.common.extentions.getLayoutInflater
import com.vladbakalo.location_alarm.common.helper.validator.TextValidator
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.DistanceRule
import com.vladbakalo.location_alarm.common.helper.validator.rules.text.NotEmptyRule
import com.vladbakalo.location_alarm.data.common.AlarmDistanceData
import com.vladbakalo.location_alarm.databinding.ItemDistanceAlarmBinding

class DistanceAlarmListAdapter(val listener: OnAlarmActionListener) :
    RecyclerView.Adapter<DistanceAlarmListAdapter.ItemVH>() {

    private val distanceDataList: ArrayList<AlarmDistanceData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = parent.getLayoutInflater()
        return ItemVH(ItemDistanceAlarmBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return distanceDataList.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = distanceDataList[position]
        holder.binding.model = item
        holder.binding.actionListener = listener
    }

    fun setDate(data: List<AlarmDistanceData>) {
        distanceDataList.clear()
        distanceDataList.addAll(data)

        notifyDataSetChanged()
    }

    fun validate(recyclerView: RecyclerView): Boolean {
        var isValid = true
        var view: RecyclerView.ViewHolder?
        for (i in 0..itemCount) {
            view = recyclerView.findViewHolderForAdapterPosition(i)

            if (view is ItemVH){
                isValid = view.validator.validate() && isValid
            }
        }
        return isValid
    }

    inner class ItemVH(val binding: ItemDistanceAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val validator = TextValidator(binding.itemAlarmEtDistanceText)

        init {
            validator.addRule(NotEmptyRule())
                .addRule(DistanceRule())
        }
    }

    interface OnAlarmActionListener{
        fun onRemoveClick(item: AlarmDistanceData)
    }
}