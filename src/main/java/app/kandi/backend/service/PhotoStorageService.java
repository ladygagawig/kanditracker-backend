package app.kandi.backend.service;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoStorageService {
	private final GridFsTemplate gridFsTemplate;

	public PhotoStorageService(GridFsTemplate gridFsTemplate) {
		this.gridFsTemplate = gridFsTemplate;
	}

	public ObjectId store(MultipartFile file) throws Exception {
		try (InputStream is = file.getInputStream()) {
			return gridFsTemplate.store(is, file.getOriginalFilename(), file.getContentType());
		}
	}

	public GridFsResource getResourceById(String id) {
		var file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
		if (file == null) return null;
		return gridFsTemplate.getResource(file);
	}
}
