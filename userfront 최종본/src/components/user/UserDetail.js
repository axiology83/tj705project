import React, { useEffect, useState } from 'react'
import { fetchFn } from '../NetworkUtils';
import { Link, useParams } from 'react-router-dom';
import moment from 'moment'; // npm install moment
import Button from 'react-bootstrap/Button';

function UserDetail() {
  const username = useParams().username;
  const [user, setUser] = useState(null);
  const LOGINER = localStorage.getItem("LOGINER");
  const token = localStorage.getItem("TOKEN");
  const role = localStorage.getItem("ROLE");

  // 본인이거나 관리자일 경우 상세정보 접근 가능
  useEffect(() => {
    if (LOGINER === username || role === 'TYPE2') {
      fetchFn("GET", `http://localhost:8000/user-service/users/${username}`, null, {
        headers: { Authorization: `Bearer ${token}` },
      }).then((data) => {
        if (data) {
          setUser(data);
        } else {
          console.error("실패1", data);
        }
      })
        .catch((error) => {
          console.error("실패2", error);
        });
    } else {
      alert("접근 권한이 없습니다.") 
      window.location.href = `/`; // 접근 실패시 에러메세지 & 메인화면으로 돌아감
    }
  }, [token, username, LOGINER, role]);

  // 로그아웃 함수
  function logout() {
    localStorage.removeItem("TOKEN");
    localStorage.removeItem("LOGINER");
    localStorage.removeItem("jwt");
    window.location.href = `/`;
  }

  // 블락당한 user는 상세보기 기능 이용 불가
  if (role === 'TYPE3') {
    return (
      <div>
        <p>사이트 이용이 정지되었습니다. 자세한 사항은 관리자에게 문의하세요.</p>
      </div>
    )
  }

  return (
    <div>
      <h2>회원정보 상세보기</h2>
      {user !== null && (
        <>
          <div className='user'>
            <p>아이디 : {user.username}</p>
            <p>이름 : {user.name}</p>
            <p>회원권한 : {user.role}</p>
            <p>가입일 : {moment(user.createAt).format("YYYY-MM-DD HH:mm:ss")}</p>
            <p>갱신일 : {moment(user.updateAt).format("YYYY-MM-DD HH:mm:ss")}</p>
          </div>

          <div>
            {/* 모두에게 보임 */}
            <Link to={"/user/listPaging"}><Button variant="primary">회원목록</Button></Link>
            {/* 본인일때만 보임 */}
            {LOGINER === user.username && (
              <Link to={`/user/update/${username}`}> <Button variant="warning">수정</Button></Link>
            )}
            {/* 본인이거나 관리자이면 보임 */}
            {(LOGINER === user.username || role === 'TYPE2') && (
              <Link to={`/user/delete/${username}`}> <Button variant="danger">삭제</Button></Link>
            )}
            {/* 관리자이면 보임 */}
            {(role === 'TYPE2') && (
              <Link to={`/user/role/${username}`}> <Button variant="success">권한</Button></Link>
            )}
            {/* 본인이 아닐때만 보임 */}
            {LOGINER !== user.username && (
              <Link to={`/user/login`}> <Button variant="dark">로그인</Button></Link>
            )}

          </div>
        </>
      )}
    </div>
  )
}

export default UserDetail