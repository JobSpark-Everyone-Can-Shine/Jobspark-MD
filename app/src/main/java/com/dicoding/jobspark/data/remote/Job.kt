package com.dicoding.jobspark.data.remote

import com.google.gson.annotations.SerializedName

data class Job(
    val id: Int,
    val job_name: String,
    val image: String,
    val company_name: String,
    val job_description: String,
    val location: String,
    val position: String,
    val qualification: String,
    val min_experience: String,
    val job_type: String,
    val salary: String,
    val created_at: String
)


data class JobListResponse(
    val status: Int,
    val message: String,
    val data: List<Job>,
    val pagination: Pagination
)

data class JobDetailResponse(
    @SerializedName("data")
    val data: JobData
)

data class JobData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("job_name")
    val jobName: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("company_name")
    val companyName: String,

    @SerializedName("job_description")
    val jobDescription: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("position")
    val position: String,

    @SerializedName("qualification")
    val qualification: String,

    @SerializedName("min_experience")
    val minExperience: String,

    @SerializedName("job_type")
    val jobType: String,

    @SerializedName("salary")
    val salary: String,

    @SerializedName("created_at")
    val createdAt: String
)


data class ApplyJobRequest(
    val jobs_id: Int,
    val resume_id: Int
)

data class ApplyJobResponse(
    val status: String,
    val message: String
)

data class UploadResponse(
    val status: String,
    val message: String,
    val resumeId: Int?
)

data class JobHistory(
    val id: Int,
    val jobTitle: String,
    val companyName: String,
    val status: String
)

data class JobHistoryDetail(
    val id: Int,
    val jobTitle: String,
    val companyName: String,
    val status: String,
    val description: String,
    val dateApplied: String
)

data class Pagination(
    val total_data: Int,
    val total_pages: Int,
    val current_page: Int,
    val limit: Int
)

