package app.kandi.backend.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import app.kandi.backend.dto.BraceletDtos;
import app.kandi.backend.model.Bracelet;
import app.kandi.backend.model.Entry;
import app.kandi.backend.repo.BraceletRepository;
import app.kandi.backend.repo.EntryRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BraceletService {
	private final BraceletRepository braceletRepository;
	private final EntryRepository entryRepository;
	private final PasswordEncoder passwordEncoder;
	private final PhotoStorageService photoStorageService;

	public BraceletService(BraceletRepository braceletRepository,
	                       EntryRepository entryRepository,
	                       PasswordEncoder passwordEncoder,
	                       PhotoStorageService photoStorageService) {
		this.braceletRepository = braceletRepository;
		this.entryRepository = entryRepository;
		this.passwordEncoder = passwordEncoder;
		this.photoStorageService = photoStorageService;
	}

	public Bracelet createBracelet(String mainCode, String secondaryCodeRaw) {
		Bracelet bracelet = new Bracelet();
		bracelet.setMainCode(mainCode);
		bracelet.setSecondaryCodeHash(passwordEncoder.encode(secondaryCodeRaw));
		return braceletRepository.save(bracelet);
	}

	public Optional<Bracelet> findByMainCode(String mainCode) {
		return braceletRepository.findByMainCode(mainCode);
	}

	public List<Entry> getRecentEntriesForBracelet(String braceletId, int limit) {
		return entryRepository.findByBraceletIdOrderByCreatedAtDesc(braceletId, PageRequest.of(0, limit));
	}

	public Entry addEntryToBracelet(Bracelet bracelet, String providedSecondaryCode, String message, MultipartFile photo) throws Exception {
		if (!passwordEncoder.matches(providedSecondaryCode, bracelet.getSecondaryCodeHash())) {
			throw new IllegalArgumentException("Invalid secondary code");
		}

		Entry entry = new Entry();
		entry.setBraceletId(bracelet.getId());
		entry.setMessage(message);

		if (photo != null && !photo.isEmpty()) {
			var fid = photoStorageService.store(photo);
			entry.setPhotoFileId(fid.toHexString());
			entry.setPhotoContentType(photo.getContentType());
		}

		entry.setCreatedAt(Instant.now());
		Entry saved = entryRepository.save(entry);

		bracelet.setEntriesCount(bracelet.getEntriesCount() + 1);
		bracelet.setLastActiveAt(Instant.now());
		braceletRepository.save(bracelet);

		return saved;
	}

	public List<Entry> getFeed(int limit) {
		return entryRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
	}
}
