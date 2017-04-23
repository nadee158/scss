package com.privasia.scss.gateout.dto;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Optional;

import com.privasia.scss.common.enums.CollectionType;
import com.privasia.scss.common.enums.TransactionType;

public class FileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Optional<Long> oddImpSeq1;

	private Optional<Long> oddImpSeq2;
	
	private Optional<Long> oddExpSeq1;

	private Optional<Long> oddExpSeq2;

	private Optional<Long> gatePassNo1;

	private Optional<Long> gatePassNo2;

	private Optional<Long> exportNoSeq1;

	private Optional<Long> exportNoSeq2;

	private TransactionType trxType;

	private String fileName;

	private byte[] file;

	private CollectionType collectionType;

	private InputStream cameraImage;

	private long folderSize;

	private long fileSize;

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public InputStream getCameraImage() {
		return cameraImage;
	}

	public void setCameraImage(InputStream cameraImage) {
		this.cameraImage = cameraImage;
	}

	public long getFolderSize() {
		return folderSize;
	}

	public void setFolderSize(long folderSize) {
		this.folderSize = folderSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Optional<Long> getOddImpSeq1() {
		return oddImpSeq1;
	}

	public void setOddImpSeq1(Optional<Long> oddImpSeq1) {
		this.oddImpSeq1 = oddImpSeq1;
	}

	public Optional<Long> getOddImpSeq2() {
		return oddImpSeq2;
	}

	public void setOddImpSeq2(Optional<Long> oddImpSeq2) {
		this.oddImpSeq2 = oddImpSeq2;
	}

	public Optional<Long> getOddExpSeq1() {
		return oddExpSeq1;
	}

	public void setOddExpSeq1(Optional<Long> oddExpSeq1) {
		this.oddExpSeq1 = oddExpSeq1;
	}

	public Optional<Long> getOddExpSeq2() {
		return oddExpSeq2;
	}

	public void setOddExpSeq2(Optional<Long> oddExpSeq2) {
		this.oddExpSeq2 = oddExpSeq2;
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

	public TransactionType getTrxType() {
		return trxType;
	}

	public void setTrxType(TransactionType trxType) {
		this.trxType = trxType;
	}

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

}
