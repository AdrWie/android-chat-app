package com.adrian.surra.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.adrian.surra.R
import com.adrian.surra.model.Message
import com.adrian.surra.services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// Extend Recycler.View to bind user data to a recyclerView.
class MessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    // Creates a new view holder for the recycler view and returns it ("Ex. Each user typing a message")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    // Return the number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        return messages.count()
    }

    // Call by recyclerView to display message data at specific position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context, messages[position])
    }
    // Get reference to each view by id
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView?.findViewById<ImageView>(R.id.messageUserImage)
        val timeStamp = itemView?.findViewById<TextView>(R.id.timestampLabel)
        val userName = itemView?.findViewById<TextView>(R.id.messageUserNameLabel)
        val messageBody = itemView?.findViewById<TextView>(R.id.messageBodyLabel)

        // Set each views data
        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName?.text = message.userName
            timeStamp?.text = returnDateString(message.timeStamp)
            messageBody?.text = message.message
        }

        // Format date string as "Mon, 11:00 AM"
        fun returnDateString(isoString: String) : String {
            // 2017-09-11T01:16:13.876Z
            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            var convertedDate = Date()

            try {
                convertedDate = isoFormatter.parse(isoString)
            } catch(e: ParseException) {
                Log.d("PARSE", "Cannot parse date")
            }

            val outDateString = SimpleDateFormat("E, H:mm a", Locale.getDefault())
            return outDateString.format(convertedDate)
        }
    }
}