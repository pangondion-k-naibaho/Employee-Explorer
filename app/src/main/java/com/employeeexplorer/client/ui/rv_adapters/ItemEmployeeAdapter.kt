package com.employeeexplorer.client.ui.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.employeeexplorer.client.R
import com.employeeexplorer.client.data.Constants.TYPE_CONSTANTS.Companion.FULL_TIME
import com.employeeexplorer.client.data.Constants.TYPE_CONSTANTS.Companion.PART_TIME
import com.employeeexplorer.client.data.model.response.EmployeeResponse
import com.employeeexplorer.client.databinding.ItemRvEmployeeLayoutBinding

class ItemEmployeeAdapter(
    var data: MutableList<EmployeeResponse>?,
    private val listener: ItemListener?= null,
    var isMalformed: Boolean = false
):RecyclerView.Adapter<ItemEmployeeAdapter.ItemHolder>() {
    interface ItemListener{
        fun onItemClicked(item: EmployeeResponse)
        fun onItemMalformedClicked()
    }

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemRvEmployeeLayoutBinding.bind(itemView)

        fun bind(item: EmployeeResponse, listener: ItemListener?) = with(itemView){
            binding.apply {
                val employeePhoto = item.photoUrlSmall ?: ContextCompat.getDrawable(itemView.context, R.drawable.sample_photo)

                Glide.with(itemView.context)
                    .load(employeePhoto)
                    .into(ivPhoto)

                tvName.text = item.fullName
                tvEmail.text = item.emailAddress
                tvPhoneNum.text = item.phoneNumber
                tvTeam.text = item.team ?: itemView.context.getString(R.string.tvUnknownTeam)

                val employmentType = when(item.employeeType){
                    FULL_TIME -> "Full Time"
                    PART_TIME -> "Part Time"
                    else -> "Contractor"
                }

                tvType.text = String.format(itemView.context.getString(R.string.tvFormatEmploymentType), employmentType)

                root.setOnClickListener {
                    if(!isMalformed) listener?.onItemClicked(item) else listener?.onItemMalformedClicked()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_employee_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data!!.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data!!.get(position), listener)
    }

    fun addItem(listEmployee: List<EmployeeResponse>){
        val startPosition = data!!.size
        data!!.addAll(listEmployee)
        notifyItemRangeInserted(startPosition, listEmployee.size)
    }

    fun updateItem(listEmployee: List<EmployeeResponse>){
        try {
            if(!listEmployee.isNullOrEmpty()){
                data!!.clear()
                data!!.addAll(listEmployee)
                notifyDataSetChanged()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}