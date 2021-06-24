package com.accessibilitytraining.android.flow.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.accessibilitytraining.android.R
import com.accessibilitytraining.android.base.BaseFragment
import com.accessibilitytraining.android.databinding.FragmentDetailBinding
import com.accessibilitytraining.android.repository.ListDataResponse

class DetailFragment : BaseFragment(), DetailContract.View {
    private var binding: FragmentDetailBinding? = null
    private val arguments: DetailFragmentArgs by navArgs()
    private val presenter = DetailPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity?.setActionBarData(
            leftButtonIconRes = R.drawable.ic_back,
            leftButtonClickListener = { findNavController().popBackStack() },
            titleRes = R.string.detail_page_title
        )
        binding?.apply {
            ivInfo.setOnClickListener {
                presenter.tooltipOnClick()
            }
            rvImage.apply {
                adapter = DetailAdapter()
                context?.let {
                    val itemDecorator = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
                    ContextCompat.getDrawable(it, R.drawable.list_divider)?.let { drawable ->
                        itemDecorator.setDrawable(drawable)
                        addItemDecoration(itemDecorator)
                    }
                }
            }
        }
        presenter.initDetailModel(arguments.detailModel)
    }

    override fun setDetail(detailModel: DetailData) {
        binding?.apply {
            tvTitle.text = detailModel.title
            tvAmount.text = detailModel.amount
            tvDate.text = detailModel.date
            tvLabel1.text = detailModel.item1
            tvLabel2.text = detailModel.item2
            tvLabel3.text = detailModel.item3
            tvLabel4.text = detailModel.item4
        }
    }

    override fun setImageList(imageModel: List<ListDataResponse.ListImageModel>) {
        (binding?.rvImage?.adapter as DetailAdapter).setData(imageModel)
    }

    override fun routeToTooltip(title: String?, content: String?) {
        findNavController().navigate(
            DetailFragmentDirections.actionShowTooltipDialogFragment(
                tooltipTitle = title.orEmpty(),
                tooltipContent = content.orEmpty()
            )
        )
    }

    override fun presenterShouldAttachView() {
        presenter.attachView(this)
    }

    override fun presenterShouldDetachView() {
        presenter.detachView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
