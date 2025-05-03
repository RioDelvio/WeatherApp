package com.rio.weatherapp.data.mapper

import com.rio.weatherapp.data.local.model.CityDbModel
import com.rio.weatherapp.data.network.dto.CityDto
import com.rio.weatherapp.domain.entity.City

fun City.toCityDbModel(): CityDbModel = CityDbModel(id, name, country)

fun CityDbModel.toEntity(): City = City(id, name, country)

fun CityDto.toEntity(): City = City(id, name, country)

fun List<CityDbModel>.toEntities(): List<City> = map { it.toEntity() }

fun List<CityDto>.toEntities(): List<City> = map { it.toEntity() }