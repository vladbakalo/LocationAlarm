package com.vladbakalo.create_location_alarm.ui.fragment.alarm_create.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladbakalo.core.common.extentions.getLayoutInflater
import com.vladbakalo.core.common.helper.validator.TextValidator
import com.vladbakalo.core.common.helper.validator.rules.text.DistanceRule
import com.vladbakalo.core.common.helper.validator.rules.text.NotEmptyRule
import com.vladbakalo.core.data.models.AlarmDistance
import com.vladbakalo.create_location_alarm.databinding.ItemDistanceAlarmBinding

class DistanceAlarmListAdapter(val listener: OnAlarmActionListener) :
    RecyclerView.Adapter<DistanceAlarmListAdapter.ItemVH>() {

    private val distanceDataList: ArrayList<AlarmDistance> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val layoutInflater = parent.getLayoutInflater()
        return ItemVH(ItemDistanceAlarmBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return distanceDataList.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = distanceDataList[position]
        holder.binding.itemAlarmEtDistanceText.setText(item.notifyDistanceMeters)
        holder.binding.itemAlarmEtDistanceText.setTextChangeListener {
            item.notifyDistanceMeters = it
        }

        holder.binding.itemAlarmSwitchEnable.isEnabled = item.enabled
        holder.binding.itemAlarmSwitchEnable.setOnCheckedChangeListener { compoundButton, enabled ->
            item.enabled = enabled
            listener.onEnabledStateChanged(item, enabled)
        }

        holder.binding.itemAlarmIvRemoveButton.setOnClickListener {
            listener.onRemoveClick(item)
        }
    }

    fun setDate(data: List<AlarmDistance>) {
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
            validator.addRule(NotEmptyRule(itemView.context))
                .addRule(DistanceRule(itemView.context))
        }
    }

    interface OnAlarmActionListener{
        fun onRemoveClick(item: AlarmDistance)

        fun onEnabledStateChanged(item: AlarmDistance, enabled: Boolean)
    }
}