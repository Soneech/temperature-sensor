package org.soneech.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.soneech.exception.MeasurementException;
import org.soneech.model.Measurement;
import org.soneech.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final int batchSize = 1000;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, EntityManagerFactory entityManagerFactory) {
        this.measurementRepository = measurementRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findById(Long id) {
        Optional<Measurement> foundMeasurement = measurementRepository.findById(id);
        if (foundMeasurement.isEmpty())
            throw new MeasurementException("измерение с таким id не найдено", HttpStatus.NOT_FOUND);

        return foundMeasurement.get();
    }

    @Transactional
    public Measurement save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        return measurementRepository.save(measurement);
    }

    public int getRainyDaysCount() {
        List<Measurement> measurements = measurementRepository.getMeasurementByRaining(true);
        return measurements.size();
    }

    @Transactional
    public void saveAll(List<Measurement> measurements) {  // batch update
        var start = System.currentTimeMillis();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try (entityManager) {
            entityTransaction.begin();
            for (int i = 0; i < measurements.size(); i++) {
                if (i > 0 && batchSize % i == 0) {
                    entityTransaction.commit();
                    entityTransaction.begin();

                    entityManager.clear();
                }
                measurements.get(i).setCreatedAt(LocalDateTime.now());
                entityManager.persist(measurements.get(i));
            }
            entityTransaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        }

        var end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
    }
}
