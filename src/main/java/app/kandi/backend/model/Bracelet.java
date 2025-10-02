package app.kandi.backend.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("bracelets")
public class Bracelet {
	@Id
	private String id;

	@Indexed(unique = true)
	private String mainCode;

	private String secondaryCodeHash;

	private Instant createdAt = Instant.now();
	private Instant lastActiveAt = Instant.now();

	private long entriesCount = 0;

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getMainCode() { return mainCode; }
	public void setMainCode(String mainCode) { this.mainCode = mainCode; }

	public String getSecondaryCodeHash() { return secondaryCodeHash; }
	public void setSecondaryCodeHash(String secondaryCodeHash) { this.secondaryCodeHash = secondaryCodeHash; }

	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

	public Instant getLastActiveAt() { return lastActiveAt; }
	public void setLastActiveAt(Instant lastActiveAt) { this.lastActiveAt = lastActiveAt; }

	public long getEntriesCount() { return entriesCount; }
	public void setEntriesCount(long entriesCount) { this.entriesCount = entriesCount; }
}
