package app.kandi.backend.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import app.kandi.backend.model.Entry;

public interface EntryRepository extends MongoRepository<Entry, String> {
	List<Entry> findByBraceletIdOrderByCreatedAtDesc(String braceletId, Pageable pageable);
	List<Entry> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
