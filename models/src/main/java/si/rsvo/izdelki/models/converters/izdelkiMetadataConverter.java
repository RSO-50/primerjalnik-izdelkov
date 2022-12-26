package si.rsvo.izdelki.models.converters;

import si.rsvo.izdelki.lib.izdelkiMetadata;
import si.rsvo.izdelki.models.entities.izdelkiMetadataEntity;

public class izdelkiMetadataConverter {

    public static izdelkiMetadata toDto(izdelkiMetadataEntity entity) {

        izdelkiMetadata dto = new izdelkiMetadata();
        dto.setId(entity.getId());
        dto.setCena(entity.getCena());
        dto.setNaziv(entity.getNaziv());
        dto.setTrgovina(entity.getTrgovina());
        dto.setOcena(entity.getOcena());

        return dto;

    }

    public static izdelkiMetadataEntity toEntity(izdelkiMetadata dto) {

        izdelkiMetadataEntity entity = new izdelkiMetadataEntity();
        entity.setId(dto.getId());
        entity.setCena(dto.getCena());
        entity.setNaziv(dto.getNaziv());
        entity.setTrgovina(dto.getTrgovina());
        entity.setOcena(dto.getOcena());

        return entity;

    }

}
