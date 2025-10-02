package app.kandi.backend.controller;

import java.time.format.DateTimeFormatter;

import app.kandi.backend.dto.BraceletDtos;
import app.kandi.backend.model.Entry;
import app.kandi.backend.service.BraceletService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
	private final BraceletService braceletService;

	public FeedController(BraceletService braceletService) {
		this.braceletService = braceletService;
	}

	@GetMapping
	public java.util.List<BraceletDtos.EntrySummary> latest(@RequestParam(defaultValue = "25") int limit) {
		return braceletService.getFeed(Math.min(Math.max(limit, 1), 100)).stream().map(this::toSummary).toList();
	}

	private BraceletDtos.EntrySummary toSummary(Entry e) {
		BraceletDtos.EntrySummary s = new BraceletDtos.EntrySummary();
		s.id = e.getId();
		s.message = e.getMessage();
		s.photoUrl = e.getPhotoFileId() != null ? "/api/photos/" + e.getPhotoFileId() : null;
		s.createdAt = DateTimeFormatter.ISO_INSTANT.format(e.getCreatedAt());
		return s;
	}
}
