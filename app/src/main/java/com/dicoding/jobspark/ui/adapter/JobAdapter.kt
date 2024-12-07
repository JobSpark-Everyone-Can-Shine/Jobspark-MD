package com.dicoding.jobspark.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.jobspark.R
import com.dicoding.jobspark.data.remote.Job
import com.dicoding.jobspark.ui.activity.JobDescriptionActivity
import com.dicoding.jobspark.ui.activity.UploadActivity

class JobAdapter(private var jobs: List<Job>, private val isSimplified: Boolean) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.job_title)
        val company: TextView = itemView.findViewById(R.id.company_location)
        val jobType: TextView? = itemView.findViewById(R.id.job_type)
        val jobIcon: ImageView = itemView.findViewById(R.id.job_icon)
        val applyButton: Button? = itemView.findViewById(R.id.apply_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val layoutRes = if (isSimplified) {
            R.layout.item_job_card_simplified
        } else {
            R.layout.item_job_card
        }

        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        holder.title.text = job.job_name
        holder.company.text = "${job.company_name} • ${job.location}"

        Glide.with(holder.itemView.context)
            .load(job.image)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.jobIcon)

        if (!isSimplified) {
            holder.jobType?.text = job.job_type

            holder.applyButton?.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, UploadActivity::class.java).apply {
                    putExtra("job_id", job.id)
                }
                context.startActivity(intent)
            }

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, JobDescriptionActivity::class.java).apply {
                    putExtra("job_id", job.id)
                    putExtra("job_name", job.job_name)
                }
                context.startActivity(intent)
            }
        } else {
            // Handle the simplified layout without applyButton
            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, JobDescriptionActivity::class.java).apply {
                    putExtra("job_id", job.id)
                    putExtra("job_name", job.job_name)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    fun updateData(newJobs: List<Job>) {
        jobs = newJobs
        notifyDataSetChanged()
    }
}
