package com.example.tbc_test4.conversations_fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbc_test4.BaseFragment
import com.example.tbc_test4.databinding.FragmentConversationsBinding
import kotlinx.coroutines.launch


class ConversationsFragment : BaseFragment<FragmentConversationsBinding>(FragmentConversationsBinding::inflate) {

    private val viewModel: ConversationsFragmentViewModel by viewModels()
    private lateinit var conversationRecyclerAdapter : ConversationsRecyclerAdapter

    override fun setUp() {
        setUpRv()
        listeners()
        bindObservers()
        viewModel.jsonParse()
    }

    private fun setUpRv(){
        conversationRecyclerAdapter = ConversationsRecyclerAdapter()
        binding.recyclerConversations.layoutManager = LinearLayoutManager(context)
        binding.recyclerConversations.adapter = conversationRecyclerAdapter
    }

    private fun listeners(){
        binding.btnSearch.setOnClickListener {

        }
    }

    private fun bindObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFlow.collect {
                    conversationRecyclerAdapter.submitList(it)
                }
            }
        }
    }
}