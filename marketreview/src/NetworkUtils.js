export function fetchFn(method, url, dto) {
  let options = {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
  };

  if (dto) {
    options.body = JSON.stringify(dto);
  }

  return fetch(url, options)
    .then((res) => {
      if (!res.ok) {
        throw new Error("입력된 정보에 오류가 있습니다.");
      }
      return res.json();
    })
    .catch((error) => {
      alert(error.message);
    });
}
