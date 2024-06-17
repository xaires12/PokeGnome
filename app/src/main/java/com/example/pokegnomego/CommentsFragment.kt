package com.example.pokegnomego

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.pokegnomego.network.ApiClient
import com.example.pokegnomego.network.ApiService
import com.example.pokegnomego.models.*
import com.example.pokegnomego.databinding.FragmentCommentsBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CommentsFragment : Fragment() {


    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_GNOME_ID = "gnome_id"

        fun newInstance(gnomeId: Int): CommentsFragment {
            val fragment = CommentsFragment()
            val args = Bundle()
            args.putInt(ARG_GNOME_ID, gnomeId)
            fragment.arguments = args
            return fragment
        }
    }
    private var gnomeId: Int = -1
    private var user_id: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        user_id = sharedPreferences.getInt("userId", -1)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gnomeId = it.getInt(ARG_GNOME_ID)
        }
        loadComments(gnomeId)

        binding.CommentButton.setOnClickListener {
            val coment = binding.commentEditText.text.toString()
            if (coment.isNotBlank()) {
                addNewComment(coment, user_id)
                binding.commentEditText.text.clear()
            }
        }
    }

    private fun loadComments(gnomeId: Int) {
        ApiClient.apiService.getComments(gnomeId).enqueue(object : Callback<List<Comments>> {
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful) {
                    val comments = response.body()
                    if (comments != null) {
                        displayComments(comments)
                    }
                } else {
                    Toast.makeText(requireContext(), "Nie udało się załadować forum", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Toast.makeText(requireContext(), "Nie udało się załadować forum", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayComments(comments: List<Comments>) {
        comments.forEach {
            val commentView = TextView(context).apply {
                text = "Użytkownik: ${it.login}\nKomentarz: ${it.comment}"
                setPadding(16, 16, 16, 16)
            }
            binding.commentsContainer.addView(commentView)
        }
    }

    private fun addNewComment(coment: String,user_id: Int) {
        val apiService = ApiClient.apiService
        val newComment = Comment(coment = coment, user_id = user_id)

        apiService.addComment(gnomeId, newComment).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Dodano komentarz", Toast.LENGTH_SHORT).show()
                    //loadComments(gnomeId)
                } else {
                    Toast.makeText(context, "Nie dodano komentarza", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
//        userId = sharedPreferences.getInt("userId", -1)
//    }
}