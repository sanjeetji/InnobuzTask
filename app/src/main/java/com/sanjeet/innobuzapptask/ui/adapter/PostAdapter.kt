package com.sanjeet.innobuz.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapptask.databinding.PostItemBinding
import javax.inject.Inject

class PostAdapter @Inject constructor() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList = mutableListOf<PostItem>()

    private var listener: ((PostItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    fun setContentList(postList: List<PostItem>) {
        this.postList = postList.toMutableList()
        notifyDataSetChanged()
    }

    fun itemClickListener(l: (PostItem) -> Unit) {
        listener = l
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val postItem = postList.get(position)
        holder.binding.postTitle.text = postItem.title

        holder.itemView.setOnClickListener {
            listener?.let {
                it(this.postList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.postList.size
    }

    class PostViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

}