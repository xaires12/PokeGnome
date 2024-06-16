package com.example.pokegnomego

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.pokegnomego.databinding.FragmentThirdBinding



class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageviewFirst.setOnClickListener {
            val gnomeId = 1
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewSecond.setOnClickListener {
            val gnomeId = 12
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewThird.setOnClickListener {
            val gnomeId = 6
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewFourth.setOnClickListener {
            val gnomeId = 7
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewFifth.setOnClickListener {
            val gnomeId = 9
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewSixth.setOnClickListener {
            val gnomeId = 15
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
        }
        binding.imageviewSeventh.setOnClickListener {
            val gnomeId = 14
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewEigth.setOnClickListener {
            val gnomeId = 17
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewNineth.setOnClickListener {
            val gnomeId = 8
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewTenth.setOnClickListener {
            val gnomeId = 13
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
        binding.imageviewEleventh.setOnClickListener {
            val gnomeId = 10
            val comFragment = CommentsFragment.newInstance(gnomeId)
            parentFragmentManager.commit {
                replace(R.id.whatever, comFragment)
                addToBackStack(null)
            }
        }
    }

    fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
}