package com.example.recyclerviewdemo

import android.os.Parcel
import android.os.Parcelable

data class Hole(var par: Int, var distance: Int) : Parcelable {

       constructor(parcel: Parcel) : this(
              parcel.readInt(),
              parcel.readInt()
       )


       override fun writeToParcel(parcel: Parcel, flags: Int) {
              parcel.writeInt(par)
              parcel.writeInt(distance)
       }

       override fun describeContents(): Int {
              return 0
       }

       companion object CREATOR : Parcelable.Creator<Hole> {
              override fun createFromParcel(parcel: Parcel): Hole {
                     return Hole(parcel)
              }

              override fun newArray(size: Int): Array<Hole?> {
                     return arrayOfNulls(size)
              }
       }
       constructor() : this(0, 0)
}
