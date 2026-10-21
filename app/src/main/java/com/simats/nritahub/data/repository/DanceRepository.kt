package com.simats.nritahub.data.repository

import com.simats.nritahub.data.model.DanceClass

class DanceRepository {
    fun getSampleDanceClasses(): List<DanceClass> {
        return listOf(
            DanceClass("1", "Bharatanatyam Adavus", "Sri Kanchi Kalamandir", "Beginner", 45),
            DanceClass("2", "Abhinaya & Expressions", "Sri Kanchi Kalamandir", "Intermediate", 60)
        )
    }
}
