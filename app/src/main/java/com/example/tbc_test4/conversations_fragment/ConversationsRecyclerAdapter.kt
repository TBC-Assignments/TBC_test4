package com.example.tbc_test4.conversations_fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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
        parent: ViewGroup, viewType: Int
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

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind() {
            val convo = currentList[adapterPosition]
            with(binding) {
                tvName.text = convo.owner
                tvLastActive.text = convo.lastActive
                when (convo.lastMessageType) {
                    Conversation.TYPE.TEXT -> {
                        if (convo.unreadMessages > 0) {
                            tvLastMessage.setTextColor(
                                root.resources.getColor(
                                    R.color.white, root.resources.newTheme()
                                )
                            )
                        }
                        tvLastMessage.text = convo.lastMessage
                    }

                    Conversation.TYPE.FILE -> {
                        if (convo.unreadMessages > 0) {
                            tvLastMessage.setTextColor(
                                root.resources.getColor(
                                    R.color.white, root.resources.newTheme()
                                )
                            )
                        }
                        tvLastMessage.setCompoundDrawablesRelative(
                            root.resources.getDrawable(
                                R.drawable.ic_attachment_file, root.resources.newTheme()
                            ), null, null, null
                        )
                        tvLastMessage.setText(R.string.attachment)
                    }

                    Conversation.TYPE.VOICE -> {
                        if (convo.unreadMessages > 0) {
                            tvLastMessage.setTextColor(
                                root.resources.getColor(
                                    R.color.white, root.resources.newTheme()
                                )
                            )
                        }
                        tvLastMessage.setCompoundDrawablesRelative(
                            root.resources.getDrawable(
                                R.drawable.ic_voice_file, root.resources.newTheme()
                            ), null, null, null
                        )
                        tvLastMessage.setText(R.string.voice)
                    }
                }
                when (convo.isTyping) {
                    false -> {
                        when (convo.unreadMessages) {
                            0 -> {
                                tvUnreadAmount.text = ""
                                tvUnreadAmount.setBackgroundResource(0)
                            }

                            else -> {
                                tvUnreadAmount.text = convo.unreadMessages.toString()
                                tvUnreadAmount.setTextColor(
                                    root.resources.getColor(
                                        R.color.white, root.resources.newTheme()
                                    )
                                )
                                tvUnreadAmount.textSize = 14f
                                tvUnreadAmount.setBackgroundResource(R.drawable.background_unread_amount)
                            }
                        }
                    }

                    true -> {
                        tvUnreadAmount.text = ".."
                        tvUnreadAmount.setTextColor(
                            root.resources.getColor(
                                R.color.search_btn_gradient_one, root.resources.newTheme()
                            )
                        )
                        tvUnreadAmount.setBackgroundResource(0)
                        tvUnreadAmount.textSize = 18f
                    }
                }
                Glide.with(binding.root.context).load(convo.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_profile).into(ivProfileIcon)
            }
        }
    }
}