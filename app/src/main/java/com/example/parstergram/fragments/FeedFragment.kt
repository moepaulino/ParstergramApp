package com.example.parstergram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parstergram.MainActivity
import com.example.parstergram.Post
import com.example.parstergram.PostAdapter
import com.example.parstergram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

open class FeedFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This is where we set up our views and click listeners

        postsRecyclerView = view.findViewById(R.id.postRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

         postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }

    // query for all posts in our server
    open fun queryPosts() {

        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post objects
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")

        // only return 20 most recent posts
        query.setLimit(20)

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null) {
                    // something has went wrong
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if(posts != null) {
                        for(post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " , username: " +
                                    post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

    companion object {
        const val TAG = "FeedFragment"
    }
}