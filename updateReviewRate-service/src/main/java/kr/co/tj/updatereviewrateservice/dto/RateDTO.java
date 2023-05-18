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
public class RateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sellerId;

	private float rate;

	public UpdateReviewRateEntity toReviewEntity() {
		return UpdateReviewRateEntity.builder()
				.sellerId(sellerId)
				.rate(rate)
				.build();
	}

}
