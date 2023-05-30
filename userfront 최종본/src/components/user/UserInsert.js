import React from 'react'
import { fetchFn } from '../NetworkUtils';

function UserInsert() {

  // 사용자가 폼을 제출하면 함수 호출
  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);

    const dto = {
      username: formData.get("username"),
      name: formData.get("name"),
      password: formData.get("password"),
      password2: formData.get("password2")
    };

    const username = formData.get("username");
    const name = formData.get("name");
    const password = formData.get("password");
    const password2 = formData.get("password2");

    // 빈 값 입력 방지
    if (!username || !name || !password || !password2) {
      alert("입력되지 않은 란이 있습니다.");
      return;
    }

    // 회원가입 완료 후 로그인 화면으로 넘어감
    fetchFn("POST", "http://localhost:8000/user-service/users", dto)
      .then(data => {
        console.log(data.result);
        window.location.href = `/user/login`;
      });
  }

  return (
    <div>
      <h2>회원가입</h2>

      <form action="#" onSubmit={onSubmitHandler}>
        닉네임: <input name='username' /><br />
        이름: <input name='name' /><br />
        비밀번호: <input name='password' /><br />
        비밀번호 확인: <input name='password2' /><br />
        <button>가입완료</button>
      </form>
    </div>
  )
}

export default UserInsert