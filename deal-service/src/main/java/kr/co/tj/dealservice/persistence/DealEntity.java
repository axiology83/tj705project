package kr.co.tj.dealservice.persistence;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "deal")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "id-uuid")
	@GenericGenerator(strategy = "uuid", name = "id-uuid")
	private String id;
	
	private String seller;
	private String buyer;
	private String productName;
	private Long price;
	
	private Date createAt;
	private Date updateAt;
	private Date finishAt;
	
	@Enumerated(EnumType.STRING)
	private State State;
	

}
