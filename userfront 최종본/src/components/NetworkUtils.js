// 토큰 받아올 수 있고, 403에러 처리할 수 있고, json외 다른 방식의 응답도 받을 수 있음.
export function fetchFn(method, url, dto, additionalOptions = {}) {
  let options = {
    method: method,
    headers: {
      "Content-Type": "application/json",
      ...additionalOptions.headers,
    },
  };

  if (dto) {
    options.body = JSON.stringify(dto);
  }

  return fetch(url, options)
    .then((res) => {
      if (res.status === 403) {
        alert("접근 방식이 잘못되었습니다.");
      }

      if (!res.ok) {
        throw new Error("에러.");
      }
      // 응답의 Content-Type 헤더를 확인하여 JSON 응답인지 판별
      if (res.headers.get('content-type').includes('application/json')) {
        // JSON 응답인 경우
        return res.json();
      } else {
        // JSON 응답이 아닌 경우 (예: text/plain)
        // 어디가 문제인지 정확히 모르겠는데 json형식으로 받아 올 수 없다는 에러메세지 자꾸 떠서 추가함
        return res.text();
      }
    })
    .catch((error) => {
      console.log(error.message);
    });
}