package ar.edu.utn.dds.k3003.repositories;


import ar.edu.utn.dds.k3003.database.EntityManagerHelper;
import ar.edu.utn.dds.k3003.database.Repository;
import ar.edu.utn.dds.k3003.model.RegistroRetiro;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import java.util.List;

public class RetiroRepository extends Repository <RegistroRetiro> {


    public RetiroRepository() {
        super(RegistroRetiro.class);
    }

    public List<RegistroRetiro> findRetirosByHeladeraAndToday(Long heladeraId) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

            return em.createQuery(
                            "SELECT r FROM RegistroRetiro r " +
                                    "WHERE r.heladera.id = :heladeraId " +
                                    "AND r.fechaMedicion BETWEEN :startOfDay AND :endOfDay",
                            RegistroRetiro.class)
                    .setParameter("heladeraId", heladeraId)
                    .setParameter("startOfDay", startOfDay)
                    .setParameter("endOfDay", endOfDay)
                    .getResultList();
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

}
