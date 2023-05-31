import React from "react";
import { insertFetchFn} from "../../NetworkUtils";

function MemberInsert() {
  localStorage.setItem("jwt",null);

  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const dto = {
      username: formData.get("username"),
      name: formData.get("name"),
      password: formData.get("password"),
      password2: formData.get("password2"),
    };

insertFetchFn("auth/insert", dto)
.then(data => {
  
  window.location.href = `/login`
})


  }

  return (
    <div>
      <div className="squarediv"><form action="#" onSubmit={onSubmitHandler}>
        <span className="squaretxt1">아이디</span>
        <input name="username" />
        <br />
        <span className="squaretxt1">이 름</span>
        <input name="name" />
        <br />
        <span className="squaretxt1">비 번</span>
        <input name="password" />
        <br />
        <span className="squaretxt1">비번 확인</span>
        <input name="password2" />
        <br />
        <button>확 tot the 인</button>
      </form></div>
    </div>
  );
}

export default MemberInsert;
