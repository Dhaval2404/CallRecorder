package com.github.dhaval2404.callrecorder.home.screen.inbox

import android.telephony.PhoneNumberUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.base.extension.setVisibility
import com.github.dhaval2404.callrecorder.callrecord.CallType
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.util.TimeUtil
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
object InboxBinding {

    private val colors = listOf<Int>(
        R.color.blue_100,
        R.color.pink_100,
        R.color.orange_100,
        R.color.deep_orange_100,
        R.color.indigo_100,
        R.color.teal_100,
        R.color.cyan_100,
        R.color.purple_100,
        R.color.purple_100,
        R.color.green_100,
        R.color.light_blue_100
    ).shuffled()

    @JvmStatic
    @BindingAdapter("randomBackground")
    fun randomBackground(view: View, id: Long) {
        val index = id.toInt() % colors.size
        view.backgroundTintList = ContextCompat.getColorStateList(view.context, colors[abs(index)])
    }

    @JvmStatic
    @BindingAdapter("imgSrc")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("callTypeIcon")
    fun setCallTypeIcon(imageView: ImageView, callType: CallType) {
        val icon = if (callType == CallType.INCOMING_CALL || callType == CallType.MISSED_CALL) {
            R.drawable.baseline_call_received_24
        } else {
            R.drawable.baseline_call_made_24
        }
        imageView.setImageResource(icon)
    }

    @JvmStatic
    @BindingAdapter("contactName")
    fun setContactName(textView: TextView, recording: Recording) {
        if (!recording.name.isNullOrBlank()) {
            textView.setText(recording.name)
        } else {
            setPhoneNumber(textView, recording)
        }
    }

    @JvmStatic
    @BindingAdapter("phoneType")
    fun setPhoneType(textView: TextView, recording: Recording) {
        textView.setText(recording.phoneType ?: "Mobile")
    }

    @JvmStatic
    @BindingAdapter("phoneNumber")
    fun setPhoneNumber(textView: TextView, recording: Recording) {
        textView.text =
            PhoneNumberUtils.formatNumber(recording.phoneNumber, Locale.getDefault().country)
    }

    private val TIME_FORMAT = SimpleDateFormat("hh:mm a", Locale.getDefault())
    private val DATE_FORMAT = SimpleDateFormat("dd MMM", Locale.getDefault())

    @JvmStatic
    @BindingAdapter("callTime")
    fun setCallTime(textView: TextView, recording: Recording) {
        val calendar1 = Calendar.getInstance()
        val isToday = TimeUtil.isSameDay(calendar1, recording.createdAt)
        calendar1.add(Calendar.DAY_OF_MONTH, -1)
        val isYesterday = TimeUtil.isSameDay(calendar1, recording.createdAt)

        val timing = if (isToday || isYesterday) {
            TIME_FORMAT.format(recording.createdAt.time)
        } else {
            DATE_FORMAT.format(recording.createdAt.time)
        }

        textView.text = timing
    }

    @JvmStatic
    @BindingAdapter("callDuration")
    fun setCallDuration(textView: TextView, recording: Recording) {
        val millis = recording.duration
        val seconds = (millis / 1000) % 60
        val minutes = ((millis / 1000) / 60) % 60
        val hours = ((millis / 1000) / 60) / 60

        val duration = when {
            hours > 1 -> "${hours}h ${minutes}m ${seconds}s"
            minutes > 1 -> "${minutes}m ${seconds}s"
            else -> "${seconds}s"
        }

        textView.text = duration
    }

    @JvmStatic
    @BindingAdapter("contactSrc")
    fun setContactImage(imageView: ImageView, recording: Recording) {
        if (!recording.imagePath.isNullOrBlank()) {
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(recording.imagePath)
                .into(imageView)
        } else {
            imageView.setImageResource(0)
        }
    }

    @JvmStatic
    @BindingAdapter("contactAlt")
    fun setContactAlt(textView: TextView, recording: Recording) {
        if (!recording.imagePath.isNullOrBlank()) {
            textView.setVisibility(false)
        } else {
            textView.setVisibility(true)
            val name = recording.name
            val char = if (!name.isNullOrBlank()) {
                name.firstOrNull().toString()
            } else {
                "#"
            }
            textView.text = char
        }
    }
}
