package app.kandi.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class BraceletDtos {
	public static class CreateRequest {
		@NotBlank
		public String mainCode;
		@NotBlank
		public String secondaryCode;
	}

	public static class ViewResponse {
		public String mainCode;
		public long entriesCount;
		public List<EntrySummary> entries;
	}

	public static class EntrySummary {
		public String id;
		public String message;
		public String photoUrl;
		public String createdAt;
	}

	public static class AddEntryResponse {
		public String entryId;
	}
}
