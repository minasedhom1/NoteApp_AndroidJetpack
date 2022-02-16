package com.example.noteappandroidjetpack.data

import java.util.*
import kotlin.collections.ArrayList

class SampleDataProvider {

    companion object {
        private val sampleText1 = "A simple note"
        private val sampleText2 = "A note a\nline feed"
        private val sampleText3 = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nec vestibulum arcu. Nulla a nunc varius, volutpat nibh ac, pellentesque tellus. In ullamcorper convallis dolor in cursus. Donec finibus mauris urna, vitae rhoncus risus pulvinar at. Vivamus quis felis sed dolor pretium laoreet. Sed nec metus arcu. In gravida mauris et massa efficitur aliquet. Vivamus sollicitudin, eros in ultricies sagittis, ipsum lectus lobortis turpis, porta laoreet tortor quam sed est. Proin mauris libero, commodo id dolor in, finibus facilisis ante. Proin a ipsum ultrices, volutpat turpis quis, bibendum dolor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nunc non lacinia magna. Ut a eros quis nisl vulputate volutpat.

            Praesent volutpat bibendum lorem, eget luctus enim ultrices ut. Ut euismod ut turpis in faucibus. Mauris ac blandit metus, a interdum massa. Praesent id maximus nisl. Cras ut orci eros. Cras ultricies orci tellus, vitae gravida ex euismod eget. Sed nec aliquam lectus. Maecenas nisl lectus, vulputate sit amet sagittis vitae, sagittis nec ipsum. Donec aliquet malesuada laoreet. Pellentesque luctus velit eget iaculis ullamcorper. Integer ante lacus, aliquam nec mauris a, lobortis aliquet augue. Praesent in odio a felis ultricies iaculis. Mauris nec felis gravida, eleifend ex nec, condimentum erat.
        """.trimIndent()

        private fun getDate(diff: Long): Date {
            return Date(Date().time + diff)
        }

        fun getNotes() = arrayListOf(
            NoteEntity(getDate(0), sampleText1),
            NoteEntity(getDate(1), sampleText2),
            NoteEntity(getDate(2), sampleText3)
        )

    }

}