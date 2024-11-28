package ar.edu.utn.dds.k3003.repositories;
import ar.edu.utn.dds.k3003.database.EntityManagerHelper;
import ar.edu.utn.dds.k3003.database.Repository;
import ar.edu.utn.dds.k3003.model.Heladera;

import javax.persistence.EntityManager;
import java.util.*;

public class HeladeraRepository extends Repository <Heladera> {

    public HeladeraRepository() {
        super(Heladera.class);
    }

    public List<Object[]> findAllwithCantViandas() {
        EntityManager em = EntityManagerHelper.getEntityManager();

        String jpql = "SELECT h.id, h.viandas FROM Heladera h";
        try {
            return em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }


    public List<Object[]> findAllwithCantAperturas() {
        EntityManager em = EntityManagerHelper.getEntityManager();

        String jpql = "SELECT h.id, h.cantidadAperturas FROM Heladera h";

        try {
            return  em.createQuery(jpql, Object[].class).getResultList();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

}
