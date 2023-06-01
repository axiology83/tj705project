import React, { useEffect, useState } from 'react';
import { fetchFn } from '../NetworkUtils';
import UserComp from './UserComp';
import { Link } from 'react-router-dom';
import { Pagination, Table } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';

// 전체 회원 목록
function UserListPaging({ startDate, endDate, setFn }) {
  const [users, setUsers] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const username = localStorage.getItem("LOGINER");
  const token = localStorage.getItem("TOKEN");
  const pageSize = 20; // 한 화면에 20명씩 보임

  useEffect(() => {
    fetchFn("GET", `http://localhost:8000/user-service/users/${username}`, null, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then(data => {
        setIsAdmin(data.role === 'TYPE2');
      })
      .catch(error => {
        console.log(error)
      });
  }, [username, token]);

  useEffect(() => {
    // 관리자만 접근 가능
    if (isAdmin) {
      // 사용자 목록 가져오는 GET요청 보냄
      fetchFn("GET", `http://localhost:8000/user-service/users/list?page=${currentPage -1}&size=${pageSize}`, null, {
        headers: { Authorization: `Bearer ${token}` },
      })
        .then(data => {
          if (data && data.users) {
            const sortedData = data.users.sort((a, b) =>
              a.username.localeCompare(b.username)); // username 오름차순 정렬
            setUsers(sortedData); // 정렬된 사용자 목록 저장
            setTotalPages(data.totalPages); // 총 페이지 수 저장
          } else {
            setUsers([]);
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }, [isAdmin, token, currentPage, pageSize, startDate, endDate]);

  // Pagination에서 선택한 페이지 번호로 가는 함수
  // ex) 사용자가 1페이지를 선택하면 값은 0으로 설정 됨
  const handleSelect = (eventKey) => {
    setCurrentPage(eventKey);
  };

  // 계산식에 대한 자세한 설명은 UserList에 달아놓음.
  const pages = [...Array(totalPages).keys()].map(i => i + 1);

  const maxPageDisplay = 10;
  const startPage = currentPage - Math.floor(maxPageDisplay / 2);
  const endPage = currentPage + Math.floor(maxPageDisplay / 2);

  let displayPages;

  if (totalPages <= maxPageDisplay) {
    displayPages = pages;

  } else if (startPage <= 0) {
    displayPages = pages.slice(0, maxPageDisplay);

  } else if (endPage >= totalPages) {
    displayPages = pages.slice(totalPages - maxPageDisplay);

  } else {
    displayPages = pages.slice(startPage, endPage + 1);
  }

  return (
    <div>
      <h2>회원목록</h2>
      <br />
      <Link to={`/user/listSerch`}><Button variant="outline-primary">가입일 검색</Button></Link>
      <br />
      <br />
      {isAdmin ? (
        <>
          {/* 표 모양 부트스트랩 코드 사용 */}
          <Table striped bordered hover size="sm">
            <thead>
              <tr>
                <th>Name</th>
                <th>Username</th>
                <th>Create Date</th>
              </tr>
            </thead>
            <tbody>
              {users && users.length > 0 ? (
                users.map(user =>
                  <UserComp key={user.id} user={user} />
                )
              ) : (
                <tr>
                  <td colSpan="3">아직 회원이 존재하지 않습니다.</td>
                </tr>
              )}
            </tbody>
          </Table>
          {/* 페이지네이션 사용. 가운데 정렬 */}
          <Pagination className="justify-content-center">
            {/* 맨앞으로 */}
            <Pagination.First onClick={() => setCurrentPage(1)} />
            {/* 이전 페이지로 */}
            <Pagination.Prev onClick={() => setCurrentPage(prev => prev > 1 ? prev - 1 : prev)} />
            {/* 현재 페이지 표시 */}
            {/* 콘솔창에 key값을 지정해달라는 에러메세지가 뜨는데 key값 빠진 부분 없는거 같은데 왜 그러는지 모르겠음. 일단 보이는데 문제는 없음. */}
            {displayPages.map(value =>
              <Pagination.Item key={value} active={value === currentPage} onClick={() => handleSelect(value)}>
                {value}
              </Pagination.Item>)
            }
            {/* 다음 페이지로 */}
            <Pagination.Next onClick={() => setCurrentPage(prev => prev < totalPages ? prev + 1 : prev)} />
            {/* 맨뒤로 */}
            <Pagination.Last onClick={() => setCurrentPage(totalPages)} />
          </Pagination>
        </>
      ) : (
        <p>관리자만 접근이 가능합니다.</p>
      )}
      <Link to={"/"}><Button variant="outline-dark">HOME</Button></Link>
    </div>
  );
}

export default UserListPaging;

