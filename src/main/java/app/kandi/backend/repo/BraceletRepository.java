package app.kandi.backend.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.kandi.backend.model.Bracelet;

public interface BraceletRepository extends MongoRepository<Bracelet, String> {
	Optional<Bracelet> findByMainCode(String mainCode);
}
