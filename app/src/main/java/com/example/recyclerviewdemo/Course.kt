package com.example.recyclerviewdemo

import android.os.Parcel
import android.os.Parcelable

data class Course(
       val place: String,
       val holes: List<Hole>?,
       val numOfHoles: Int,
       val totalPar: Int,
       val location : String,
       val totalDistance : Int
): Parcelable {
       constructor(parcel: Parcel) : this(
              parcel.readString()!!,
              parcel.createTypedArrayList(Hole.CREATOR)!!,
              parcel.readInt(),
              parcel.readInt(),
              parcel.readString()!!,
              parcel.readInt()
       )

       override fun writeToParcel(parcel: Parcel, flags: Int) {
              parcel.writeString(place)
              parcel.writeTypedList(holes)
              parcel.writeInt(numOfHoles)
              parcel.writeInt(totalPar)
              parcel.writeString(location)
              parcel.writeInt(totalDistance)
       }

       override fun describeContents(): Int {
              return 0
       }

       companion object CREATOR : Parcelable.Creator<Course> {
              override fun createFromParcel(parcel: Parcel): Course {
                     return Course(parcel)
              }

              override fun newArray(size: Int): Array<Course?> {
                     return arrayOfNulls(size)
              }
       }
}
