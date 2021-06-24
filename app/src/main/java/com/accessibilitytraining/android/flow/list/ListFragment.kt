package com.accessibilitytraining.android.flow.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.accessibilitytraining.android.MainRepository
import com.accessibilitytraining.android.R
import com.accessibilitytraining.android.base.BaseFragment
import com.accessibilitytraining.android.databinding.FragmentListBinding
import com.accessibilitytraining.android.repository.ListDataResponse

class ListFragment : BaseFragment(), ListContract.View {
    private var binding: FragmentListBinding? = null
    private lateinit var presenter: ListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ListPresenter(MainRepository(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity?.setActionBarData(
            leftButtonIconRes = R.drawable.ic_back,
            leftButtonClickListener = { findNavController().popBackStack() },
            titleRes = R.string.list_page_title
        )
        binding?.rvList?.apply {
            adapter = ListAdapter().apply {
                listAdapterClickListener = object : ListAdapter.ListAdapterClickListener {
                    override fun onListClick(detailModel: ListDataResponse.ListDetailModel) {
                        findNavController().navigate(
                            ListFragmentDirections.actionListFragmentToDetailFragment(
                                detailModel = detailModel
                            )
                        )
                    }
                }
            }
        }
        presenter.requestListDataResponse()
    }

    override fun showLoading() {
        binding?.vLoading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding?.vLoading?.visibility = View.GONE
    }

    override fun setList(listData: List<ListData>) {
        binding?.apply {
            vLoading.visibility = View.GONE
            rvList.apply {
                (adapter as ListAdapter).setData(listData)
                visibility = View.VISIBLE
            }
        }
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
