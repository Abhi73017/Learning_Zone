package com.abhishek.learningzone.student_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhishek.learningzone.R
import com.abhishek.learningzone.model.DatabaseCourse
import com.abhishek.learningzone.PdfReader
import kotlinx.android.synthetic.main.learner_course_item_rv.view.*

class courseAdapter(
    var course_items: List<DatabaseCourse>
) : RecyclerView.Adapter<courseAdapter.courseItemViewHolder>() {

    inner class courseItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): courseItemViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.learner_course_item_rv, parent, false)

        return courseItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return course_items.size
    }

    override fun onBindViewHolder(holder: courseItemViewHolder, position: Int) {
        holder.itemView.View_btn.setOnClickListener {
            Intent(it.context, PdfReader::class.java).apply {
                putExtra("downloaduri", course_items[position].downloadUri)
                it.context.startActivity(this)
            }

        }
        holder.itemView.apply {
            filename_Item.text = course_items[position].filename
            downloaduri_text.text = course_items[position].downloadUri

        }


    }
}