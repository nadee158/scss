package com.privasia.scss.gateout.mongo.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.privasia.scss.gateout.dto.FileDTO;

@Component
public class GridFSRepository {

	private MongoDbFactory dbFactory;

	@Autowired
	public void setDbFactory(MongoDbFactory dbFactory) {
		this.dbFactory = dbFactory;
	}
	

	public GridFSInputFile storeFile(FileDTO fileDTO, DBObject metadata) throws IOException {
		
		GridFS gridFS = null;
		
		if (fileDTO.getCollectionType() == null) {
			return null;
		}
		
		gridFS = new GridFS(dbFactory.getDb(), fileDTO.getCollectionType().getValue());
		GridFSInputFile file = gridFS.createFile(fileDTO.getFileStream(), fileDTO.getFileName().get(), true);
		
		file.setChunkSize(1024);
		file.setMetaData(metadata);
		file.saveChunks();
		file.save();
		
		
		return file;
	}

}
