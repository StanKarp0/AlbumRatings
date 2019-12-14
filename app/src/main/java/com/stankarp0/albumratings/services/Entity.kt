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

@Keep
data class PerformerProperty(
    val performerId: Int,
    val name: String,
    val average: Double,
    val ratingsCount: Int,
    val albumCount: Int
): Serializable

data class PerformerEmbedded(
    val performers: List<PerformerProperty>?
)

data class PerformerObject(
    val _embedded: PerformerEmbedded
) {

    val performers: List<PerformerProperty>
        get() = this._embedded.performers ?: listOf()

}

@Keep
data class PerformerProperty2(
    val performerId: Int//,
//    val name: String,
//    val average: Double,
//    val ratingsCount: Int,
//    val albumCount: Int
): Serializable

data class PerformerEmbedded2(
    val performers: List<PerformerProperty2>?
)

data class PerformerObject2(
    val _embedded: PerformerEmbedded2
) {

    val performers: List<PerformerProperty2>
        get() = this._embedded.performers ?: listOf()

}

@Keep
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
): Serializable {

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