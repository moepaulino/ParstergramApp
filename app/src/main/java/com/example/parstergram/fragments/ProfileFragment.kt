package com.example.parstergram.fragments

import android.util.Log
import com.example.parstergram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    override fun queryPosts() {
        // Specify which class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post objects
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")

        // only return 20 most recent posts
        //query.setLimit(20)

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
}