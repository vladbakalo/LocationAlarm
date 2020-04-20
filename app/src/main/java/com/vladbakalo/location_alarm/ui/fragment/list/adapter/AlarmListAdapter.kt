package com.vladbakalo.location_alarm.ui.fragment.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vladbakalo.location_alarm.R
import com.vladbakalo.location_alarm.data.models.LocationAlarm
import com.vladbakalo.location_alarm.databinding.ItemLocationAlarmBinding

class AlarmListAdapter(var clickListener: LocationAlarmItemClickListener) :
    RecyclerView.Adapter<AlarmListAdapter.ItemVH>() {

    private var dataList: List<LocationAlarm> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemLocationAlarmBinding>(inflater,
            R.layout.item_location_alarm, parent, false)


        return ItemVH(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val model = dataList[position]
        holder.binding.model = model
        holder.binding.clickListener = clickListener
        holder.binding.executePendingBindings()

        holder.binding.itemLocationAlarmCvRoot.setOnLongClickListener {
            clickListener.onLongItemClick(model)
            return@setOnLongClickListener true
        }
    }

    fun setData(dataList: List<LocationAlarm>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ItemVH(val binding: ItemLocationAlarmBinding) :RecyclerView.ViewHolder(binding.root)

    interface LocationAlarmItemClickListener {
        fun onEnableButtonClick(item: LocationAlarm)

        fun onItemClick(item: LocationAlarm)

        fun onLongItemClick(item: LocationAlarm)
    }
}