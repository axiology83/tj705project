package kr.co.tj.qnaservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tj.qnaservice.dto.ReplyResponse;


@FeignClient(name = "reply-service")
public interface ReplyFeign {
	
	// 게시물 id에 해당하는 댓글 가져오는 feign
	@GetMapping("/reply-service/replies/qna/{id}")
	public List<ReplyResponse> getRepliesById (@PathVariable("id") Long id);

}
