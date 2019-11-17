package com.stankarp0.albumratings.services

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class AlbumProperty(
    val albumId: Int,
    val title: String,
    val year: Int,
    val performerId: Int,
    val decade: Int,
    val average: Double,
    val ratingsCount: Int,
    val name: String
): Serializable {

    val header: String
        get() = "${this.name} - ${this.title}(${this.year})"

}

data class AlbumEmbedded(
    val albums: List<AlbumProperty>?
)

data class AlbumObject(
    val _embedded: AlbumEmbedded
) {
    val albums: List<AlbumProperty>
        get() = this._embedded.albums ?: listOf()
}

data class PerformerProperty(
    val performerId: Int,
    val name: String,
    val average: Double,
    val ratingsCount: Int,
    val albumCount: Int
)

data class PerformerEmbedded(
    val albums: List<PerformerProperty>?
)

data class PerformerObject(
    val _embedded: PerformerEmbedded
) {

    val albums: List<PerformerProperty>
        get() = this._embedded.albums ?: listOf()

}

data class RatingProperty(
    val ratingId: Int,
    val date: String,
    val rate: Double,
    val description: String,
    val albumId: Int,
    val performerId: Int,
    val title: String,
    val userName: String,
    val name: String
) {

    val header: String
        get() = "${this.name} - ${this.title}: ${this.rate}"

}

data class RatingEmbedded(
    val ratings: List<RatingProperty>?
)

data class RatingObject(
    val _embedded: RatingEmbedded
) {
    val ratings: List<RatingProperty>
        get() = this._embedded.ratings?: listOf()
}