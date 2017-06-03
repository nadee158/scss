package com.privasia.scss.gateout.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Optional;


import com.privasia.scss.common.enums.CollectionType;


public class FileDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Optional<Long> oddSeq1;

	private Optional<Long> oddSeq2;
	
	private Optional<Long> gatePassNo1;

	private Optional<Long> gatePassNo2;

	private Optional<Long> exportNoSeq1;

	private Optional<Long> exportNoSeq2;
	
	private String trxType;

	private Optional<String> fileName;

	private byte[] file;

	private CollectionType collectionType;

	private InputStream fileStream;

	private long folderSize;

	private long fileSize;
	
	public Optional<Long> getOddSeq1() {
		return oddSeq1;
	}

	public void setOddSeq1(Optional<Long> oddSeq1) {
		this.oddSeq1 = oddSeq1;
	}

	public Optional<Long> getOddSeq2() {
		return oddSeq2;
	}

	public void setOddSeq2(Optional<Long> oddSeq2) {
		this.oddSeq2 = oddSeq2;
	}

	public Optional<Long> getGatePassNo1() {
		return gatePassNo1;
	}

	public void setGatePassNo1(Optional<Long> gatePassNo1) {
		this.gatePassNo1 = gatePassNo1;
	}

	public Optional<Long> getGatePassNo2() {
		return gatePassNo2;
	}

	public void setGatePassNo2(Optional<Long> gatePassNo2) {
		this.gatePassNo2 = gatePassNo2;
	}

	public Optional<Long> getExportNoSeq1() {
		return exportNoSeq1;
	}

	public void setExportNoSeq1(Optional<Long> exportNoSeq1) {
		this.exportNoSeq1 = exportNoSeq1;
	}

	public Optional<Long> getExportNoSeq2() {
		return exportNoSeq2;
	}

	public void setExportNoSeq2(Optional<Long> exportNoSeq2) {
		this.exportNoSeq2 = exportNoSeq2;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public long getFolderSize() {
		return folderSize;
	}

	public void setFolderSize(long folderSize) {
		this.folderSize = folderSize;
	}

	public Optional<String> getFileName() {
		return fileName;
	}

	public void setFileName(Optional<String> fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

}
