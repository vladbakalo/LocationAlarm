package com.vladbakalo.location_alarm_list.ui.fragment.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vladbakalo.core.db.models.LocationAlarmDb
import com.vladbakalo.location_alarm_list.R
import com.vladbakalo.location_alarm_list.databinding.ItemLocationAlarmBinding

class AlarmListAdapter(var clickListener: LocationAlarmItemClickListener) :
    RecyclerView.Adapter<AlarmListAdapter.ItemVH>() {

    private var dataList: List<LocationAlarmDb> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationAlarmBinding.inflate(inflater, parent, false)

        return ItemVH(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        val item = dataList[position]

        with(holder.binding){
            itemLocationAlarmTvNameText.text = item.name
            itemLocationAlarmTvAddressText.text = item.address

            itemLocationAlarmCvRoot.setOnClickListener {
                clickListener.onItemClick(item)
            }
            itemLocationAlarmBtnEnabledButton.setOnClickListener {
                item.enabled = !item.enabled

                setEnabledButtonState(holder, item.enabled)
                clickListener.onEnableButtonClick(item, item.enabled)
            }
            itemLocationAlarmCvRoot.setOnLongClickListener {
                clickListener.onLongItemClick(item)
                return@setOnLongClickListener true
            }
        }
    }

    fun setData(dataList: List<LocationAlarmDb>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    private fun setEnabledButtonState(viewHolder: ItemVH, enabled: Boolean){
        val context = viewHolder.itemView.context

        viewHolder.binding.itemLocationAlarmBtnEnabledButton.isEnabled = enabled
        if (enabled){
            viewHolder.binding.itemLocationAlarmBtnEnabledButton.text = context.getString(R.string.enable)
        } else {
            viewHolder.binding.itemLocationAlarmBtnEnabledButton.text = context.getString(R.string.disable)
        }
    }

    class ItemVH(val binding: ItemLocationAlarmBinding) :RecyclerView.ViewHolder(binding.root)

    interface LocationAlarmItemClickListener {
        fun onEnableButtonClick(item: LocationAlarmDb, enabled: Boolean)

        fun onItemClick(item: LocationAlarmDb)

        fun onLongItemClick(item: LocationAlarmDb)
    }
}