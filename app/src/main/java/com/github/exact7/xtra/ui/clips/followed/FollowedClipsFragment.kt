package com.github.exact7.xtra.ui.clips.followed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.exact7.xtra.R
import com.github.exact7.xtra.ui.clips.BaseClipsFragment
import com.github.exact7.xtra.ui.main.MainViewModel
import com.github.exact7.xtra.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_clips.*

class FollowedClipsFragment : BaseClipsFragment() {

    private lateinit var viewModel: FollowedClipsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sortBar.setOnClickListener { FragmentUtils.showRadioButtonDialogFragment(requireActivity(), childFragmentManager, viewModel.sortOptions, viewModel.selectedIndex) }
    }

    override fun initialize() {
        if (isFragmentVisible) {
            super.initialize()
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(FollowedClipsViewModel::class.java)
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
        viewModel.setTrending(tag == R.string.trending, index, text)
    }
}