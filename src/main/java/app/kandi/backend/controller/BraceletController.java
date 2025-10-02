package app.kandi.backend.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import app.kandi.backend.dto.BraceletDtos;
import app.kandi.backend.model.Bracelet;
import app.kandi.backend.model.Entry;
import app.kandi.backend.service.BraceletService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Validated
public class BraceletController {
	private final BraceletService braceletService;

	public BraceletController(BraceletService braceletService) {
		this.braceletService = braceletService;
	}

	@PostMapping("/bracelets")
	public BraceletDtos.ViewResponse create(@Valid @RequestBody BraceletDtos.CreateRequest req) {
		Bracelet b = braceletService.createBracelet(req.mainCode.trim(), req.secondaryCode.trim());
		return toViewResponse(b, List.of());
	}

	@GetMapping("/bracelets/{mainCode}")
	public BraceletDtos.ViewResponse view(@PathVariable String mainCode) {
		Bracelet b = braceletService.findByMainCode(mainCode).orElseThrow(() -> new IllegalArgumentException("Not found"));
		List<Entry> entries = braceletService.getRecentEntriesForBracelet(b.getId(), 25);
		return toViewResponse(b, entries);
	}

	@PostMapping(value = "/bracelets/{mainCode}/entries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public BraceletDtos.AddEntryResponse addEntry(@PathVariable String mainCode,
	                                              @RequestPart("secondaryCode") @NotBlank String secondaryCode,
	                                              @RequestPart("message") @NotBlank String message,
	                                              @RequestPart(name = "photo", required = false) MultipartFile photo) throws Exception {
		Bracelet b = braceletService.findByMainCode(mainCode).orElseThrow(() -> new IllegalArgumentException("Not found"));
		Entry saved = braceletService.addEntryToBracelet(b, secondaryCode.trim(), message.trim(), photo);
		BraceletDtos.AddEntryResponse res = new BraceletDtos.AddEntryResponse();
		res.entryId = saved.getId();
		return res;
	}

	private BraceletDtos.ViewResponse toViewResponse(Bracelet b, List<Entry> entries) {
		BraceletDtos.ViewResponse res = new BraceletDtos.ViewResponse();
		res.mainCode = b.getMainCode();
		res.entriesCount = b.getEntriesCount();
		res.entries = entries.stream().map(e -> {
			BraceletDtos.EntrySummary s = new BraceletDtos.EntrySummary();
			s.id = e.getId();
			s.message = e.getMessage();
			s.photoUrl = e.getPhotoFileId() != null ? "/api/photos/" + e.getPhotoFileId() : null;
			s.createdAt = DateTimeFormatter.ISO_INSTANT.format(e.getCreatedAt());
			return s;
		}).toList();
		return res;
	}
}
