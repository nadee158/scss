/**
 * 
 */
package com.privasia.scss.core.modelmap.config;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.KioskHLTCheckStatus;
import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;

/**
 * @author Janaka
 *
 */

public class ModelMapEnumConverter {

	public static Converter<KioskLockStatus, String> convertKioskLockStatusToString() {

		return new Converter<KioskLockStatus, String>() {
			@Override
			public String convert(MappingContext<KioskLockStatus, String> context) { 
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case ACTIVE:
						return KioskLockStatus.ACTIVE.getValue();
					case LOCK:
						return KioskLockStatus.LOCK.getValue();
					case COMPLETE:
						return KioskLockStatus.COMPLETE.getValue();
					case RELEASED:
						return KioskLockStatus.RELEASED.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static Converter<String, KioskLockStatus> convertStringToKioskLockStatus() {

		return new Converter<String, KioskLockStatus>() {
			@Override
			public KioskLockStatus convert(MappingContext<String, KioskLockStatus> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case "A":
						return KioskLockStatus.ACTIVE;
					case "L":
						return KioskLockStatus.LOCK;
					case "C":
						return KioskLockStatus.COMPLETE;
					case "R":
						return KioskLockStatus.RELEASED;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static Converter<TransactionType, String> convertTransactionTypeToString() {

		return new Converter<TransactionType, String>() {
			@Override
			public String convert(MappingContext<TransactionType, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case IMPORT:
						return TransactionType.IMPORT.getValue();
					case EXPORT:
						return TransactionType.EXPORT.getValue();
					case IMPORT_EXPORT:
						return TransactionType.IMPORT_EXPORT.getValue();
					case ODD_EXPORT:
						return TransactionType.ODD_EXPORT.getValue();
					case ODD_IMPORT:
						return TransactionType.ODD_IMPORT.getValue();
					case ODD_IMPORT_EXPORT:
						return TransactionType.ODD_IMPORT_EXPORT.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};

	}

	public static Converter<String, TransactionType> convertStringToTransactionType() {

		return new Converter<String, TransactionType>() {
			@Override
			public TransactionType convert(MappingContext<String, TransactionType> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case "I":
						return TransactionType.IMPORT;
					case "E":
						return TransactionType.EXPORT;
					case "IE":
						return TransactionType.IMPORT_EXPORT;
					case "OE":
						return TransactionType.ODD_EXPORT;
					case "OI":
						return TransactionType.ODD_IMPORT;
					case "OIE":
						return TransactionType.ODD_IMPORT_EXPORT;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};

	}

	public static Converter<RecordStatus, String> convertRecordStatusToString() {

		return new Converter<RecordStatus, String>() {
			@Override
			public String convert(MappingContext<RecordStatus, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case ACTIVE:
						return RecordStatus.ACTIVE.getValue();
					case INACTIVE:
						return RecordStatus.INACTIVE.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};

	}

	public static Converter<ClientType, String> convertClientTypeToString() {

		return new Converter<ClientType, String>() {
			@Override
			public String convert(MappingContext<ClientType, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case GATE_IN:
						return ClientType.GATE_IN.getValue();
					case CCC:
						return ClientType.CCC.getValue();
					case GREEN_GATE:
						return ClientType.GREEN_GATE.getValue();
					case OTHERS:
						return ClientType.OTHERS.getValue();
					case SPV:
						return ClientType.SPV.getValue();
					case SECOND_GBOOTH:
						return ClientType.SECOND_GBOOTH.getValue();
					case GATE_OUT:
						return ClientType.GATE_OUT.getValue();
					case SECOND_GKIOSK:
						return ClientType.SECOND_GKIOSK.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};

	}

	public static Converter<KioskHLTCheckStatus, String> convertKioskHLTCheckStatusToString() {

		return new Converter<KioskHLTCheckStatus, String>() {
			@Override
			public String convert(MappingContext<KioskHLTCheckStatus, String> context) {
				if (context.getSource() == null)
					return null;
				switch (context.getSource()) {
				case CARD_READER_DOWN:
					return KioskHLTCheckStatus.CARD_READER_DOWN.getValue();
				case PC_DOWN:
					return KioskHLTCheckStatus.PC_DOWN.getValue();
				case INTERCOM_DOWN:
					return KioskHLTCheckStatus.INTERCOM_DOWN.getValue();
				case PRINTER_DOWN: 
					return KioskHLTCheckStatus.PRINTER_DOWN.getValue();
				case CAMERA_DOWN:
					return KioskHLTCheckStatus.CAMERA_DOWN.getValue();
				case OK:
					return KioskHLTCheckStatus.OK.getValue();
				default:
					return null;
				}
			}
		};

	}

	public static Converter<String, KioskHLTCheckStatus> convertStringToKioskHLTCheckStatus() {

		return new Converter<String, KioskHLTCheckStatus>() {
			@Override
			public KioskHLTCheckStatus convert(MappingContext<String, KioskHLTCheckStatus> context) {
				if (StringUtils.isBlank(context.getSource()))
					return null;
				switch (context.getSource()) {
				case "Card Reader Down":
					return KioskHLTCheckStatus.CARD_READER_DOWN;
				case "PC Down":
					return KioskHLTCheckStatus.PC_DOWN;
				case "Intercom Down":
					return KioskHLTCheckStatus.INTERCOM_DOWN;
				case "Printer Down":
					return KioskHLTCheckStatus.PRINTER_DOWN;
				case "Camera Down":
					return KioskHLTCheckStatus.CAMERA_DOWN;
				case "OK":
					return KioskHLTCheckStatus.OK;
				default:
					return null;
				}
			}
		};

	}

	public static Converter<ContainerSize, String> convertContainerSizeToString() {

		return new Converter<ContainerSize, String>() {
			@Override
			public String convert(MappingContext<ContainerSize, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case SIZE_20:
						return ContainerSize.SIZE_20.getValue();
					case SIZE_40:
						return ContainerSize.SIZE_40.getValue();
					case SIZE_45:
						return ContainerSize.SIZE_45.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static Converter<String, ContainerSize> convertStringToContainerSize() {

		return new Converter<String, ContainerSize>() {
			@Override
			public ContainerSize convert(MappingContext<String, ContainerSize> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case "20":
						return ContainerSize.SIZE_20;
					case "40":
						return ContainerSize.SIZE_40;
					case "45":
						return ContainerSize.SIZE_45;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static Converter<ContainerFullEmptyType, String> convertContainerFullEmptyTypeToString() {

		return new Converter<ContainerFullEmptyType, String>() {
			@Override
			public String convert(MappingContext<ContainerFullEmptyType, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case FULL:
						return ContainerFullEmptyType.FULL.getValue();
					case EMPTY:
						return ContainerFullEmptyType.EMPTY.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

	public static Converter<String, ContainerFullEmptyType> convertStringToContainerFullEmptyType() {

		return new Converter<String, ContainerFullEmptyType>() {
			@Override
			public ContainerFullEmptyType convert(MappingContext<String, ContainerFullEmptyType> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case "F":
						return ContainerFullEmptyType.FULL;
					case "E":
						return ContainerFullEmptyType.EMPTY;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<String, ImpExpFlagStatus> convertStringToImpExpFlagStatus() {

		return new Converter<String, ImpExpFlagStatus>() {
			@Override
			public ImpExpFlagStatus convert(MappingContext<String, ImpExpFlagStatus> context) {
				if (StringUtils.isNotEmpty(context.getSource())) {
					switch (context.getSource()) {
					case "I":
						return ImpExpFlagStatus.IMPORT;
					case "E":
						return ImpExpFlagStatus.EXPORT;
					case "IE":
						return ImpExpFlagStatus.IMPORT_EXPORT;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<ImpExpFlagStatus, String> convertImpExpFlagStatusToString() {

		return new Converter<ImpExpFlagStatus, String>() {
			@Override
			public String convert(MappingContext<ImpExpFlagStatus, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case IMPORT:
						return ImpExpFlagStatus.IMPORT.getValue();
					case EXPORT:
						return ImpExpFlagStatus.EXPORT.getValue();
					case IMPORT_EXPORT:
						return ImpExpFlagStatus.IMPORT_EXPORT.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<TransactionStatus, String> convertTransactionStatusToString() {

		return new Converter<TransactionStatus, String>() {
			@Override
			public String convert(MappingContext<TransactionStatus, String> context) {
				if (context.getSource() != null) {
					switch (context.getSource()) {
					case APPROVED:
						return TransactionStatus.APPROVED.getValue();
					case REJECT:
						return TransactionStatus.REJECT.getValue();
					case COMPLETE:
						return TransactionStatus.COMPLETE.getValue();
					case INPROGRESS:
						return TransactionStatus.INPROGRESS.getValue();
					case NEW:
						return TransactionStatus.NEW.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<String, TransactionStatus> convertStringToTransactionStatus() {

		return new Converter<String, TransactionStatus>() {
			@Override
			public TransactionStatus convert(MappingContext<String, TransactionStatus> context) {
				if (StringUtils.isNotBlank(context.getSource())) {
					switch (context.getSource()) {
					case "A":
						return TransactionStatus.APPROVED;
					case "R":
						return TransactionStatus.REJECT;
					case "C":
						return TransactionStatus.COMPLETE;
					case "I":
						return TransactionStatus.INPROGRESS;
					case "N":
						return TransactionStatus.NEW;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<String, GateInOutStatus> convertStringToGateInOutStatus() {

		return new Converter<String, GateInOutStatus>() {
			@Override
			public GateInOutStatus convert(MappingContext<String, GateInOutStatus> context) {
				if (StringUtils.isNotBlank(context.getSource())) {
					switch (context.getSource()) {
					case "IN":
						return GateInOutStatus.IN;
					case "OUT":
						return GateInOutStatus.OUT;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<GateInOutStatus, String> convertGateInOutStatusToString() {

		return new Converter<GateInOutStatus, String>() {
			@Override
			public String convert(MappingContext<GateInOutStatus, String> context) {
				if (context.getSource()!= null) {
					switch (context.getSource()) {
					case IN:
						return GateInOutStatus.IN.getValue();
					case OUT:
						return GateInOutStatus.OUT.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<String, GatePassStatus> convertStringToGatePassStatus() {

		return new Converter<String, GatePassStatus>() {
			@Override
			public GatePassStatus convert(MappingContext<String, GatePassStatus> context) {
				if (StringUtils.isNotBlank(context.getSource())) {
					switch (context.getSource()) {
					case "ACTIVE":
						return GatePassStatus.ACTIVE;
					case "CANCEL":
						return GatePassStatus.CANCEL;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<GatePassStatus, String> convertGatePassStatusToString() {

		return new Converter<GatePassStatus, String>() {
			@Override
			public String convert(MappingContext<GatePassStatus, String> context) {
				if (context.getSource()!= null) {
					switch (context.getSource()) {
					case ACTIVE:
						return GatePassStatus.ACTIVE.getValue();
					case CANCEL:
						return GatePassStatus.CANCEL.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<String, ContainerPosition> convertStringToContainerPosition() {

		return new Converter<String, ContainerPosition>() {
			@Override
			public ContainerPosition convert(MappingContext<String, ContainerPosition> context) {
				if (StringUtils.isNotBlank(context.getSource())) {
					switch (context.getSource()) {
					case "AFTER":
						return ContainerPosition.AFTER;
					case "FRONT":
						return ContainerPosition.FRONT;
					case "MIDDLE":
						return ContainerPosition.MIDDLE;
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}
	
	public static Converter<ContainerPosition, String> convertContainerPositionToString() {

		return new Converter<ContainerPosition, String>() {
			@Override
			public String convert(MappingContext<ContainerPosition, String> context) {
				if (context.getSource()!= null) {
					switch (context.getSource()) {
					case AFTER:
						return ContainerPosition.AFTER.getValue();
					case FRONT:
						return ContainerPosition.FRONT.getValue();
					case MIDDLE:
						return ContainerPosition.MIDDLE.getValue();
					default:
						return null;
					}
				} else {
					return null;
				}
			}
		};
	}

}
