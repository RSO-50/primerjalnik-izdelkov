package si.rsvo.primerjalnikIzdelkov.services.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.rsvo.primerjalnikIzdelkov.lib.primerjalnikIzdelkovMetadata;
import si.rsvo.primerjalnikIzdelkov.models.converters.primerjalnikIzdelkovMetadataConverter;
import si.rsvo.primerjalnikIzdelkov.models.entities.primerjalnikIzdelkovMetadataEntity;


@RequestScoped
public class primerjalnikIzdelkovMetadataBean {

    private Logger log = Logger.getLogger(primerjalnikIzdelkovMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;

    private String baseUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = "http://20.73.139.35/"; // ingress
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Fallback(fallbackMethod = "getTrgovinaByTrgovinaFallback")
    public Response getTrgovinaByTrgovina(String trgovina) {

        log.info("Calling users service: getting trgovina.");

        try {
            return httpClient
                    .target(baseUrl + "izdelki/v1/izdelki/byTrgovina/" + trgovina)
                    .request().get();
        }
        catch (WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    public List<primerjalnikIzdelkovMetadata> getizdelkiMetadata() {

        TypedQuery<primerjalnikIzdelkovMetadataEntity> query = em.createNamedQuery(
                "primerjalnikIzdelkovMetadataEntity.getAll", primerjalnikIzdelkovMetadataEntity.class);

        List<primerjalnikIzdelkovMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(primerjalnikIzdelkovMetadataConverter::toDto).collect(Collectors.toList());

    }

    public Response getTrgovinaByTrgovinaFallback(String trgovina) {
        return null;
    }
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
