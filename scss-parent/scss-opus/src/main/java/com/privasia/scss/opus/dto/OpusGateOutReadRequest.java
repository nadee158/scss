package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OpusGateOutReadRequest extends OpusBaseGateReadRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gateOUTDateTime = StringUtils.EMPTY;// 20161130112233"

	private String containerNo1ImportWHODD = StringUtils.EMPTY;// EPLA0000002"
	private String containerNo2ImportWHODD = StringUtils.EMPTY;// EPLA0000002"

	public String getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(String gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}

	public String getContainerNo1ImportWHODD() {
		return containerNo1ImportWHODD;
	}

	public void setContainerNo1ImportWHODD(String containerNo1ImportWHODD) {
		this.containerNo1ImportWHODD = containerNo1ImportWHODD;
	}

	public String getContainerNo2ImportWHODD() {
		return containerNo2ImportWHODD;
	}

	public void setContainerNo2ImportWHODD(String containerNo2ImportWHODD) {
		this.containerNo2ImportWHODD = containerNo2ImportWHODD;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
