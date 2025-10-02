package app.kandi.backend.controller;

import app.kandi.backend.service.PhotoStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
	private final PhotoStorageService photoStorageService;

	public PhotoController(PhotoStorageService photoStorageService) {
		this.photoStorageService = photoStorageService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<InputStreamResource> get(@PathVariable String id) throws Exception {
		var resource = photoStorageService.getResourceById(id);
		if (resource == null) return ResponseEntity.notFound().build();
		String contentType = resource.getContentType() != null ? resource.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;
		return ResponseEntity.ok()
			.header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000, immutable")
			.contentType(MediaType.parseMediaType(contentType))
			.body(new InputStreamResource(resource.getInputStream()));
	}
}
