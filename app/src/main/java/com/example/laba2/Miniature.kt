package com.example.laba2

import android.os.Parcel
import android.os.Parcelable

data class Miniature(
    var id: Int,
    var name: String,
    var faction: String,
    var imageUrls: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(faction)
        parcel.writeStringList(imageUrls)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Miniature> {
        override fun createFromParcel(parcel: Parcel): Miniature = Miniature(parcel)
        override fun newArray(size: Int): Array<Miniature?> = arrayOfNulls(size)
    }
}
