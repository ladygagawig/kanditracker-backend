package app.kandi.backend.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("entries")
public class Entry {
	@Id
	private String id;

	@Indexed
	private String braceletId;

	private String message;
	private String photoFileId;
	private String photoContentType;
	private Instant createdAt = Instant.now();

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getBraceletId() { return braceletId; }
	public void setBraceletId(String braceletId) { this.braceletId = braceletId; }
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	public String getPhotoFileId() { return photoFileId; }
	public void setPhotoFileId(String photoFileId) { this.photoFileId = photoFileId; }
	public String getPhotoContentType() { return photoContentType; }
	public void setPhotoContentType(String photoContentType) { this.photoContentType = photoContentType; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
