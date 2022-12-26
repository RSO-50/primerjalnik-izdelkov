package si.rsvo.primerjalnikIzdelkov.models.converters;

import si.rsvo.primerjalnikIzdelkov.lib.primerjalnikIzdelkovMetadata;
import si.rsvo.primerjalnikIzdelkov.models.entities.primerjalnikIzdelkovMetadataEntity;

public class primerjalnikIzdelkovMetadataConverter {

    public static primerjalnikIzdelkovMetadata toDto(primerjalnikIzdelkovMetadataEntity entity) {

        primerjalnikIzdelkovMetadata dto = new primerjalnikIzdelkovMetadata();
        dto.setId(entity.getId());
        dto.setCena(entity.getCena());
        dto.setNaziv(entity.getNaziv());
        dto.setTrgovina(entity.getTrgovina());
        dto.setOcena(entity.getOcena());

        return dto;

    }

    public static primerjalnikIzdelkovMetadataEntity toEntity(primerjalnikIzdelkovMetadata dto) {

        primerjalnikIzdelkovMetadataEntity entity = new primerjalnikIzdelkovMetadataEntity();
        entity.setId(dto.getId());
        entity.setCena(dto.getCena());
        entity.setNaziv(dto.getNaziv());
        entity.setTrgovina(dto.getTrgovina());
        entity.setOcena(dto.getOcena());

        return entity;

    }

}
