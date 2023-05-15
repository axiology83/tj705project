package kr.co.tj.userservice.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orderId;
	private String username;
	private String productId;
	private long qty;
	private long unitPrice;
	private long totalPrice;
	private Date createAt;
	private Date updateAt;
}
