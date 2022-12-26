package si.rsvo.izdelki.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.rsvo.izdelki.lib.izdelkiMetadata;
import si.rsvo.izdelki.models.converters.izdelkiMetadataConverter;
import si.rsvo.izdelki.models.entities.izdelkiMetadataEntity;


@RequestScoped
public class izdelkiMetadataBean {

    private Logger log = Logger.getLogger(izdelkiMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<izdelkiMetadata> getizdelkiMetadata() {

        TypedQuery<izdelkiMetadataEntity> query = em.createNamedQuery(
                "izdelkiMetadataEntity.getAll", izdelkiMetadataEntity.class);

        List<izdelkiMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(izdelkiMetadataConverter::toDto).collect(Collectors.toList());

    }
    /*
    @Timed
    public List<izdelkiMetadata> getIzdelkibyCena(Integer izdelekId) {

        TypedQuery<izdelkiMetadataEntity> query = em.createNamedQuery(
                "izdelkiMetadataEntity.getIzdelkibyCena", izdelkiMetadataEntity.class);
        query.setParameter("izdelekId", izdelekId);

        List<izdelkiMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(izdelkiMetadataConverter::toDto).collect(Collectors.toList());
    }

    @Timed
    public izdelkiMetadata createizdelkiMetadata(izdelkiMetadata izdelkiMetadata) {

        izdelkiMetadataEntity izdelkiMetadataEntity = izdelkiMetadataConverter.toEntity(izdelkiMetadata);

        try {
            beginTx();
            em.persist(izdelkiMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (izdelkiMetadataEntity.getIzdelekId() == null || izdelkiMetadataEntity.getCenoIzdelka() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return izdelkiMetadataConverter.toDto(izdelkiMetadataEntity);
    }

    public boolean deleteizdelkiMetadata(Integer cenaId, Integer izdelekId) {

        TypedQuery<izdelkiMetadataEntity> query = em.createNamedQuery(
                "izdelkiMetadataEntity.getCenoIzdelka", izdelkiMetadataEntity.class);
        query.setParameter("cenaId", cenaId);
        query.setParameter("izdelekId", izdelekId);

        izdelkiMetadataEntity izdelkiMetadata = query.getSingleResult();

        if (izdelkiMetadata != null) {
            try {
                beginTx();
                em.remove(izdelkiMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    /*
    @Timed
    public List<UporabnikoviIzdelkiMetadata> getUporabnikoviIzdelkiMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, UporabnikoviIzdelkiMetadataEntity.class, queryParameters).stream()
                .map(UporabnikoviIzdelkiMetadataConverter::toDto).collect(Collectors.toList());
    }

    public UporabnikoviIzdelkiMetadata getUporabnikoviIzdelkiMetadata(Integer id) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadataEntity = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (uporabnikoviIzdelkiMetadataEntity == null) {
            throw new NotFoundException();
        }

        UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata = UporabnikoviIzdelkiMetadataConverter.toDto(uporabnikoviIzdelkiMetadataEntity);

        return uporabnikoviIzdelkiMetadata;
    }

    public UporabnikoviIzdelkiMetadata createUporabnikoviIzdelkiMetadata(UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadataEntity = UporabnikoviIzdelkiMetadataConverter.toEntity(uporabnikoviIzdelkiMetadata);

        try {
            beginTx();
            em.persist(uporabnikoviIzdelkiMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (uporabnikoviIzdelkiMetadataEntity.getIzdelekId() == null || uporabnikoviIzdelkiMetadataEntity.getUporabnikId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return UporabnikoviIzdelkiMetadataConverter.toDto(uporabnikoviIzdelkiMetadataEntity);
    }

    public UporabnikoviIzdelkiMetadata putUporabnikoviIzdelkiMetadata(Integer id, UporabnikoviIzdelkiMetadata uporabnikoviIzdelkiMetadata) {

        UporabnikoviIzdelkiMetadataEntity c = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        UporabnikoviIzdelkiMetadataEntity updatedUporabnikoviIzdelkiMetadataEntity = UporabnikoviIzdelkiMetadataConverter.toEntity(uporabnikoviIzdelkiMetadata);

        try {
            beginTx();
            updatedUporabnikoviIzdelkiMetadataEntity.setIzdelekId(c.getIzdelekId());
            updatedUporabnikoviIzdelkiMetadataEntity.setUporabnikId(c.getUporabnikId());
            updatedUporabnikoviIzdelkiMetadataEntity = em.merge(updatedUporabnikoviIzdelkiMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return UporabnikoviIzdelkiMetadataConverter.toDto(updatedUporabnikoviIzdelkiMetadataEntity);
    }

    public boolean deleteUporabnikoviIzdelkiMetadata(Integer id) {

        UporabnikoviIzdelkiMetadataEntity uporabnikoviIzdelkiMetadata = em.find(UporabnikoviIzdelkiMetadataEntity.class, id);

        if (uporabnikoviIzdelkiMetadata != null) {
            try {
                beginTx();
                em.remove(uporabnikoviIzdelkiMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

     */

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
