import React from "react";
import { fetchFn } from "../../NetworkUtils";


function Login() {
  localStorage.setItem("jwt", null);

  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const username = formData.get("username");
    const password = formData.get("password");
    const dto = { username, password };

    fetchFn("POST", "http://localhost:8000/user-service/login", dto)
    .then(
      (data) => {
        console.log(data)
        localStorage.setItem("jwt", data.result.token);
        window.location.href = "/chattest";
      }
    );
  }

  return (
    <div>
      <form action="#" onSubmit={onSubmitHandler}>
        ID : <input name="username" /> <br />
        PW : <input type="password" name="password" /> <br />
        <button>로긴</button>
      </form>
    </div>
  );
}

export default Login;
