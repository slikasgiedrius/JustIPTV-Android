package com.giedrius.iptv.room

import com.giedrius.iptv.data.model.Data
import com.giedrius.iptv.utils.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject constructor(): EntityMapper<DataEntity, Data> {
    override fun mapFromEntity(entity: DataEntity): Data {
        return Data(
            id = entity.id,
            value = entity.value
        )
    }

    override fun mapToEntity(domainModel: Data): DataEntity {
        return DataEntity(
            id = domainModel.id,
            value = domainModel.value
        )
    }

    fun mapFromEntityList(entities: List<DataEntity>): List<Data> {
        return entities.map { mapFromEntity(it) }
    }
}