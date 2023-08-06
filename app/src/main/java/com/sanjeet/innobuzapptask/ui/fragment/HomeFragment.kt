package com.sanjeet.innobuzapptest.features.posts.ui.fragment

import android.app.UiAutomation
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.platform.app.InstrumentationRegistry
import com.sanjeet.innobuz.ui.adapter.PostAdapter
import com.sanjeet.innobuzapptask.accessibilityService.MyAccessibilityService
import com.sanjeet.innobuzapptask.databinding.FragmentHomeBinding
import com.sanjeet.innobuzapptask.utils.NetworkResult
import com.sanjeet.innobuzapptest.features.posts.ui.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: PostViewModel by viewModels()

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.rvPost?.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        postAdapter.itemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding?.tvAccessbility?.setOnClickListener {
            isAccessibilityServiceEnabled(requireActivity())
        }
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromLocalDb()
        fetchAllPost()
    }

    private fun fetchAllPost() {

        viewModel.postResponse.observe(requireActivity()) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding?.progressBar?.isVisible = it.isLoading
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(
                        requireActivity(),
                        "API Failure : $it.errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding?.progressBar?.isVisible = false
                }

                is NetworkResult.Success -> {
                    viewModel.savePostIntoLocalDb(it.data)
                    getDataFromLocalDb()
                    binding?.progressBar?.isVisible = true
                }
            }
        }
    }

    private fun getDataFromLocalDb() {
        viewModel.postSaveResponse.observe(requireActivity()) {
            it.let {
                viewModel.allStudent.observe(requireActivity()) {
                    it.let {
                        postAdapter.setContentList(it)
                        binding?.progressBar?.isVisible = false
                    }
                }
            }
        }
    }

    fun enableAccessibilityService(){
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun isAccessibilityServiceEnabled(mContext: Context): Boolean {
        var accessibilityEnabled = 0
        val service: String = mContext.packageName + "/" + MyAccessibilityService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
            Log.v("HomeFragment", "accessibilityEnabled = $accessibilityEnabled")
        } catch (e: Settings.SettingNotFoundException) {
            Log.e("HomeFragment", "Error finding setting, default accessibility to not found: " + e.message)
        }
        val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            Log.v("HomeFragment", "Accessibility Is Enabled")
            val settingValue: String = Settings.Secure.getString(
                mContext.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService = mStringColonSplitter.next()
                    Log.v("HomeFragment", "AccessibilityService :: $accessibilityService $service")
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        Log.v("HomeFragment", "accessibility is switched on!")
                        Toast.makeText(requireActivity(),"Accessibility is already switched on!",Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
            }
        } else {
            Log.v("HomeFragment", "accessibility is disabled")
            enableAccessibilityService()
        }
        return false
    }


}