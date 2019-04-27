package com.github.exact7.xtra.ui.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.exact7.xtra.R
import com.github.exact7.xtra.model.chat.Emote
import com.github.exact7.xtra.ui.streams.EmotesAdapter
import com.github.exact7.xtra.ui.view.GridAutofitLayoutManager
import com.github.exact7.xtra.util.convertDpToPixels


class EmotesFragment : Fragment() {

    companion object {
        fun newInstance(emotes: List<Emote>) = EmotesFragment().apply { arguments = bundleOf("list" to emotes) }
    }

    private val editText by lazy { (requireView().parent.parent as ViewGroup).findViewById<EditText>(R.id.editText) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_emotes, container, false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        (view as RecyclerView).apply {
            adapter = EmotesAdapter(requireArguments().getSerializable("list") as List<Emote>) { editText.text.append(it.name).append(' ') }
            layoutManager = GridAutofitLayoutManager(context, context.convertDpToPixels(50f))
        }
    }
}