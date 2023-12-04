package com.example.tbc_test4.conversations_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbc_test4.R
import com.example.tbc_test4.databinding.ConversationsRecyclerItemBinding

class ConversationsRecyclerAdapter :
    ListAdapter<Conversation, ConversationsRecyclerAdapter.ConversationViewHolder>(
        ConversationDiffUtil()
    ) {

    class ConversationDiffUtil : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationsRecyclerAdapter.ConversationViewHolder {
        return ConversationViewHolder(
            ConversationsRecyclerItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bind()
    }

    inner class ConversationViewHolder(private val binding: ConversationsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val convo = currentList[adapterPosition]
        fun bind() {
            with(binding) {
                tvName.text = convo.owner
                tvLastActive.text = convo.lastActive
                when (convo.lastMessageType) {
                    Conversation.TYPE.TEXT -> {
                        tvLastMessage.text = convo.lastMessage
                    }

                    Conversation.TYPE.FILE -> {
                        tvLastMessage.setText(R.string.attachment)
                    }

                    Conversation.TYPE.VOICE -> {
                        tvLastMessage.setText(R.string.voice)
                    }
                }
                Glide.with(itemView.context).load(convo.image).into(ivProfileIcon)
                tvUnreadAmount.text = convo.unreadMessages.toString()
            }
        }
    }
}