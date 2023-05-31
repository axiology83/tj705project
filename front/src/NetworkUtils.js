export function fetchFn(method, url, dto) {
  let options = {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
  };

  const token = localStorage.getItem("jwt");
  if (token !== null && token.length > 0) {
    options.headers.Authorization = "Bearer " + token;
  }

  if (dto) {
    options.body = JSON.stringify(dto);
  }

  return fetch(url, options)
    .then((res) => {
      if (res.status === 403) {
        window.location.href = "/login";
      }

      if (!res.ok) {
        throw new Error("작동 실패!");
      }
      
      return res.json();
    })
    .catch((error) => {
      alert(error.message);
    });
}

export function memberInsertFetchFn(dto) {
  return fetchFn("POST", "http://localhost:9005/api/member", dto).then(
    (data) => {
      console.log(data);
      window.location.href = `/member/detail/${data.result.username}`;
    }
  );
}

export function insertFetchFn(servicename, dto) {
  return fetchFn("POST", `http://localhost:9005/api/${servicename}`, dto).then(
    (data) => {
      console.log(data);
      if (servicename === "auth") {
        servicename = "member";
      }

      let what = data.result.id;

      if (servicename === "member" || servicename === "auth/insert") {
        servicename = "member";
        what = data.result.username;
      }

      window.location.href = `/${servicename}/detail/${what}`;
    }
  );
}
