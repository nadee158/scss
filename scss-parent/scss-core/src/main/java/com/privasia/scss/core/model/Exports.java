/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_EXPORTS")
public class Exports implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_EXPORTS")
    @SequenceGenerator(name = "SEQ_SCSS_EXPORTS", sequenceName = "CRD_CARDID_SEQ")
	@Column(name = "EXP_EXPORTNO_SEQ")
	private Long exportSEQ;
	
	
}
