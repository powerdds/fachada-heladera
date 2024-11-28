package ar.edu.utn.dds.k3003.repositories;


import ar.edu.utn.dds.k3003.database.EntityManagerHelper;
import ar.edu.utn.dds.k3003.database.Repository;
import ar.edu.utn.dds.k3003.model.Alerta;
import ar.edu.utn.dds.k3003.model.Temperatura;

import javax.persistence.EntityManager;
import java.util.List;

public class AlertaRepository extends Repository <Alerta> {


    public AlertaRepository() {
        super(Alerta.class);
    }

    public List<Alerta> findAllById(Long idHeladera) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            List<Alerta> listaAlertas = em.createQuery("from Alerta where heladera_id="+idHeladera, Alerta.class).getResultList();
              return listaAlertas;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

}
