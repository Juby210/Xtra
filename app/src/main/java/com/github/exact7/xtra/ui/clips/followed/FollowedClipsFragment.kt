package com.github.exact7.xtra.ui.clips.followed

import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import com.github.exact7.xtra.R
import com.github.exact7.xtra.model.kraken.clip.Clip
import com.github.exact7.xtra.ui.clips.BaseClipsFragment
import com.github.exact7.xtra.ui.clips.ClipsAdapter
import com.github.exact7.xtra.ui.main.MainActivity
import com.github.exact7.xtra.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_clips.*
import kotlinx.android.synthetic.main.sort_bar.*

class FollowedClipsFragment : BaseClipsFragment<FollowedClipsViewModel>() {

    override fun createViewModel(): FollowedClipsViewModel = getViewModel()

    override fun createAdapter(): PagedListAdapter<Clip, *> {
        val activity = requireActivity() as MainActivity
        return ClipsAdapter(activity, activity) {
            lastSelectedItem = it
            showDownloadDialog()
        }
    }

    override fun initialize() {
        super.initialize()
        viewModel.sortText.observe(viewLifecycleOwner, Observer {
            sortText.text = it
        })
        getMainViewModel().user.observe(viewLifecycleOwner, Observer {
            viewModel.setUser(it)
        })
        sortBar.setOnClickListener { FragmentUtils.showRadioButtonDialogFragment(requireContext(), childFragmentManager, viewModel.sortOptions, viewModel.selectedIndex) }
    }

    override fun onChange(index: Int, text: CharSequence, tag: Int?) {
        adapter.submitList(null)
        viewModel.setTrending(tag == R.string.trending, index, text)
    }
}