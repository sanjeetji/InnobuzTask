package com.sanjeet.innobuzapptest.features.posts.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.sanjeet.innobuzapp.database.entity.PostItem
import com.sanjeet.innobuzapptask.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container!!.removeAllViews()
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding?.imgBack?.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

        args.let {
            val postItem: PostItem = args.postItem
            binding?.tvId?.text = "Id : ${postItem.id}"
            binding?.tvUserId?.text = "User Id : ${postItem.userId}"
            binding?.tvTitle?.text = "Title : ${postItem.title}"
            binding?.tvbody?.text = "Description :  ${postItem.body}"
        }


        return binding?.root!!
    }


}