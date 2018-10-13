package com.exact.xtra.ui.clips

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import com.exact.xtra.R
import com.exact.xtra.model.User
import com.exact.xtra.util.C
import com.exact.xtra.util.FragmentUtils
import kotlinx.android.synthetic.main.fragment_clips.*

class FollowedClipsFragment : BaseClipsFragment() {

    private companion object {
        val sortOptions = listOf(R.string.trending, R.string.view_count)
        const val DEFAULT_INDEX = 1
        const val TAG = "FollowedClips"
    }

    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments!!.getParcelable(C.USER)!!
        sortBar.setOnClickListener { FragmentUtils.showRadioButtonDialogFragment(requireActivity(), childFragmentManager, sortOptions, DEFAULT_INDEX, TAG) }
    }

    override fun initializeViewModel() {
        val index = requireActivity().getSharedPreferences(C.USER_PREFS, MODE_PRIVATE).getInt(TAG, DEFAULT_INDEX)
        viewModel.sortText.postValue(getString(sortOptions[index]))
        viewModel.trending = index == 0
    }

    override fun loadData(override: Boolean) {
        viewModel.loadFollowedClips(userToken = user.token, reload = override)
    }

    override fun onChange(index: Int, text: CharSequence, tag: Int?) {
        viewModel.trending = tag == R.string.trending
        viewModel.sortText.postValue(text)
        loadData(true)
    }
}