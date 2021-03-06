package com.andreacioccarelli.musicdownloader.client

import android.app.Activity
import androidx.annotation.UiThread
import com.andreacioccarelli.musicdownloader.R
import com.andreacioccarelli.musicdownloader.data.enums.FailedConversionError
import com.andreacioccarelli.musicdownloader.data.model.DownloadInfo
import com.andreacioccarelli.musicdownloader.data.serializers.DirectLinkResponse
import com.andreacioccarelli.musicdownloader.ui.gradients.GradientGenerator
import com.tapadoo.alerter.Alerter

/**
 * Designed and Developed by Andrea Cioccarelli
 */

object UiController {

    @UiThread
    fun displayRetrievingVideoInformation(activity: Activity?, downloads: List<DownloadInfo>) {
        if (activity == null) return
        if (activity.isDestroyed || activity.isFinishing) return

        Alerter.create(activity)
                .enableProgress(true)
                .setDismissable(false)
                .setIcon(R.drawable.cube_scan)
                .setTitle("Converting")
                .setText("Retrieving download information for 1 file")
                .setBackgroundDrawable(GradientGenerator.random())
                .enableInfiniteDuration(true)
                .show()
    }

    @UiThread
    fun displayRetrievingListInformation(activity: Activity?, downloads: List<DownloadInfo>) {
        if (activity == null) return
        if (activity.isDestroyed || activity.isFinishing) return

        Alerter.create(activity)
                .enableProgress(true)
                .setDismissable(false)
                .setIcon(R.drawable.cube_scan)
                .setTitle("Converting")
                .setText("Retrieving download information for ${downloads.size} files")
                .setBackgroundDrawable(GradientGenerator.random())
                .enableInfiniteDuration(true)
                .show()
    }

    @UiThread fun displayDownloadStarted(activity: Activity?, downloads: DirectLinkResponse) = displayDownloadStarted(activity, listOf(downloads))
    @UiThread fun displayDownloadStarted(activity: Activity?, downloads: List<DirectLinkResponse>) {
        if (activity == null) return
        if (activity.isDestroyed || activity.isFinishing) return

        when {
            downloads.size == 1 -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.download)
                        .setTitle("Download in progress")
                        .setText(downloads[0].fileName)
                        .setBackgroundDrawable(GradientGenerator.random())
                        .setDuration(7_000)
                        .show()
            }
            downloads.size > 1 -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.download_multiple)
                        .setTitle("Downloads in progress")
                        .setText("For ${downloads.size} files")
                        .setBackgroundDrawable(GradientGenerator.random())
                        .setDuration(7_000)
                        .show()
            }
            else -> throw IllegalStateException()
        }
    }

    @UiThread fun displayError(activity: Activity?, error: FailedConversionError, link: String) = displayError(activity, error, listOf(link))
    @UiThread fun displayError(activity: Activity?, error: FailedConversionError, links: List<String>) {
        if (activity == null) return
        if (activity.isDestroyed || activity.isFinishing) return

        when(error) {
            FailedConversionError.MALFORMED_URL -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.link)
                        .setTitle("Malformed URL")
                        .setText("Cannot process the given URL because it is in an unknown format (${links[0]})")
                        .setBackgroundDrawable(GradientGenerator.make(0F, R.color.Red_800, R.color.Red_A700))
                        .setDuration(7_000)
                        .show()
            }
            FailedConversionError.UNADDRESSABLE_VIDEO -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.cloud_question)
                        .setTitle("Cannot Download Video")
                        .setText("Cannot process the given video, youtube responded with an error")
                        .setBackgroundDrawable(GradientGenerator.make(0F, R.color.Red_800, R.color.Red_A700))
                        .setDuration(7_000)
                        .show()
            }
            FailedConversionError.VIDEO_LENGTH -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.timer)
                        .setTitle("Video Length Error")
                        .setText("Cannot process the given video because its length exceeds 3 hours")
                        .setBackgroundDrawable(GradientGenerator.make(0F, R.color.Red_500, R.color.Red_A400))
                        .setDuration(7_000)
                        .show()
            }

            FailedConversionError.UNKNOWN_ERROR -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.toast_error)
                        .setTitle("Unknown error")
                        .setText("An exception was raised by the server while converting the selected video")
                        .setBackgroundDrawable(GradientGenerator.make(0F, R.color.Red_500, R.color.Red_A400))
                        .setDuration(7_000)
                        .show()
            }

            FailedConversionError.BATCH_FAILED -> {
                Alerter.create(activity)
                        .setIcon(R.drawable.toast_error)
                        .setTitle("Downloading error")
                        .setText("A video in the list can not be converted for length or server issues")
                        .setBackgroundDrawable(GradientGenerator.make(0F, R.color.Red_500, R.color.Red_A400))
                        .setDuration(7_000)
                        .show()
            }
        }
    }
}