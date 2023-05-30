import React from 'react'
import { fetchFn } from '../NetworkUtils';

function UserLogin() {

  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);

    const username = formData.get("username");
    const password = formData.get("password");

    // 빈 값 입력 방지
    if(!username || !password) {
      alert("아이디 또는 비밀번호가 입력되지 않았습니다.");
      return;
    }

    const dto = {
      username,
      password
    }

    // 로그인 후 내 프로필 화면으로 넘어감
    fetchFn("POST", "http://localhost:8000/user-service/login", dto)
      .then(data => {
        localStorage.setItem("LOGINER", data.result.username);
        localStorage.setItem("TOKEN", data.result.token);
        localStorage.setItem("ROLE", data.result.role);
        window.location.href = `/user/profile/${username}`;
        console.log("콘솔로그", data);
        //window.location.href = `/`;
      })
  }
  return (
    <div>
      <h2>로그인 화면</h2>
      <form action='#' onSubmit={onSubmitHandler}>
        아이디 : <input name='username' /><br />
        비밀번호 : <input name='password' /><br />
        <button>로그인</button>
      </form>
    </div>
  )
}

export default UserLogin