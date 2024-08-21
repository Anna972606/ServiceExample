package com.example.serviceexample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceexample.service.ImageDownloadService
import kotlinx.android.synthetic.main.activity_job_scheduler.buttonAnimal
import kotlinx.android.synthetic.main.activity_job_scheduler.buttonFlower
import kotlinx.android.synthetic.main.activity_job_scheduler.imageViewAnimal
import kotlinx.android.synthetic.main.activity_job_scheduler.imageViewFlower

class JobSchedulerExActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)

        buttonAnimal.setOnClickListener {
            scheduleJob(ANIMAL_IMAGE_ID, ANIMAL_IMAGE_URL)
        }
        buttonFlower.setOnClickListener {
            scheduleJob(FLOWER_IMAGE_ID, FLOWER_IMAGE_URL)
        }

        ListenerLiveData.observe(this) {
            if (it?.first == ANIMAL_IMAGE_ID) {
                imageViewAnimal.setImageBitmap(it.second)
            } else {
                imageViewFlower.setImageBitmap(it?.second)
            }
        }
    }

    private fun scheduleJob(id: Int, imageUrl: String) {
        val jobService = ComponentName(this, ImageDownloadService::class.java)
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val extras = PersistableBundle()
        extras.putInt(ImageDownloadService.KEY_IMAGE_ID, id)
        extras.putString(ImageDownloadService.KEY_IMAGE_URL, imageUrl)

        val jobInfo = JobInfo.Builder(id, jobService)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)  // Set constraints
            .setExtras(extras)
            .build()

        jobScheduler.schedule(jobInfo)
    }

    companion object {
        private const val ANIMAL_IMAGE_ID = 1
        private const val FLOWER_IMAGE_ID = 2
        private const val ANIMAL_IMAGE_URL =
            "https://images.news18.com/ibnlive/uploads/2022/01/untitled-design-2022-01-24t124157.003.jpg"
        private const val FLOWER_IMAGE_URL =
            "https://i.pinimg.com/736x/2e/cf/06/2ecf067a2069128f44d75d25a32e219e.jpg"
    }
}