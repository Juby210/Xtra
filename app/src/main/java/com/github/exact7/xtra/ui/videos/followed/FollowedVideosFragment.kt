package com.github.exact7.xtra.ui.videos.followed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.exact7.xtra.R
import com.github.exact7.xtra.ui.fragment.RadioButtonDialogFragment
import com.github.exact7.xtra.ui.main.MainViewModel
import com.github.exact7.xtra.ui.videos.BaseVideosFragment
import com.github.exact7.xtra.ui.videos.Sort
import com.github.exact7.xtra.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_videos.*

class FollowedVideosFragment : BaseVideosFragment(), RadioButtonDialogFragment.OnSortOptionChanged {

    private lateinit var viewModel: FollowedVideosViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortBar.setOnClickListener{ FragmentUtils.showRadioButtonDialogFragment(requireActivity(), childFragmentManager, viewModel.sortOptions, viewModel.selectedIndex) }
    }

    override fun initialize() {
        if (isFragmentVisible) {
            super.initialize()
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(FollowedVideosViewModel::class.java)
            binding.viewModel = viewModel
            binding.sortText = viewModel.sortText
            viewModel.list.observe(this, Observer {
                adapter.submitList(it)
            })
            val mainViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
            mainViewModel.user.observe(viewLifecycleOwner, Observer {
                viewModel.setUser(it!!)
            })
        }
    }

    override fun onNetworkRestored() {
        viewModel.retry()
    }

    override fun onChange(index: Int, text: CharSequence, tag: Int?) {
        viewModel.sort(if (tag == R.string.upload_date) Sort.TIME else Sort.VIEWS, index, text)
    }
}