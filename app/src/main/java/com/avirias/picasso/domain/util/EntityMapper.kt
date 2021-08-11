package com.avirias.picasso.domain.util

// E: Entity, D: Domain
interface EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

    fun mapToEntity(domainModel: D): E
}