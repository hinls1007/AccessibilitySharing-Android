package com.accessibilitytraining.android.flow.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.accessibilitytraining.android.databinding.ViewHolderHeaderBinding
import com.accessibilitytraining.android.databinding.ViewHolderRowBinding
import com.accessibilitytraining.android.databinding.ViewHolderRowWithCustomBinding
import com.accessibilitytraining.android.extension.toDateContentDescription
import com.accessibilitytraining.android.helper.asButton
import com.accessibilitytraining.android.helper.asHeading
import com.accessibilitytraining.android.repository.ListDataResponse

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    companion object {
        private const val Header = 0x101
        private const val Row = 0x102
        private const val RowWithCustomView = 0x103
    }

    private val listDataList = mutableListOf<ListData>()
    var listAdapterClickListener: ListAdapterClickListener? = null

    fun setData(data: List<ListData>) {
        listDataList.clear()
        listDataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val listData = listDataList[position]
        return if (listData is ListData.Header) {
            Header
        } else if (listData is ListData.Row && position < 4) {
            Row
        } else {
            //Hardcode logic: Section Header2 row layout use CustomView
            RowWithCustomView
        }
    }

    override fun getItemCount() = listDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            Header -> {
                val itemBinding = ViewHolderHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(itemBinding)
            }
            RowWithCustomView -> {
                val itemBinding = ViewHolderRowWithCustomBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RowWithCustomViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = ViewHolderRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RowViewHolder(itemBinding)
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(listDataList[position])
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun onBindView(listData: ListData)
    }

    class HeaderViewHolder(private val itemBinding: ViewHolderHeaderBinding) :
        ViewHolder(itemBinding.root) {
        override fun onBindView(listData: ListData) {
            (listData as? ListData.Header)?.let {
                itemBinding.tvHeader.apply {
                    text = it.headerTitle
                    asHeading()
                }
            }
        }
    }

    inner class RowViewHolder(private val itemBinding: ViewHolderRowBinding) :
        ViewHolder(itemBinding.root) {
        override fun onBindView(listData: ListData) {
            (listData as? ListData.Row)?.let {
                itemBinding.apply {
                    tvTitle.text = it.title
                    tvAmount.text = it.amount
                    tvDate.apply {
                        text = it.date
                        contentDescription = it.date?.toDateContentDescription()
                    }

                    it.detail?.let { detail ->
                        vRow.asButton()
                        vRow.setOnClickListener { listAdapterClickListener?.onListClick(detail) }
                        ivForward.visibility = View.VISIBLE
                    } ?: run {
                        ivForward.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    inner class RowWithCustomViewHolder(private val itemBinding: ViewHolderRowWithCustomBinding) :
        ViewHolder(itemBinding.root) {
        override fun onBindView(listData: ListData) {
            (listData as? ListData.Row)?.let { rowData ->
                itemBinding.vCustomRowView.apply {
                    rowData.detail?.let {
                        asButton()
                    }
                    setData(
                        title = rowData.title,
                        amount = rowData.amount,
                        date = rowData.date,
                        onClickListener = rowData.detail?.let {
                            { listAdapterClickListener?.onListClick(it) }
                        }
                    )
                }
            }
        }
    }

    interface ListAdapterClickListener {
        fun onListClick(detailModel: ListDataResponse.ListDetailModel)
    }
}