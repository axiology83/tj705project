package kr.co.tj.updatereviewrateservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReviewRateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String sellerId;

	private float rate;
	
	private float averageRate;
	private float minRate;
	private float maxRate;

}
