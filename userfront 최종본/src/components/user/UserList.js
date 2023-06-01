import React, { useEffect, useState } from 'react';
import { fetchFn } from '../NetworkUtils';
import UserComp from './UserComp';
import { Table, Pagination } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';

// UserListSerch에서 검색한 가입일로 찾은 회원 목록
function UserList({ startDate, endDate, users: initialUsers }) {
    // users와 setUsers를 그대로 사용하면 무한 렌더링에 걸려서 localUsers를 새로 설정함
    const [localUsers, setLocalUsers] = useState(initialUsers);  // 사용자 목록을 로컬 상태에 저장
    const [isAdmin, setIsAdmin] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const username = localStorage.getItem("LOGINER");
    const token = localStorage.getItem("TOKEN");
    const pageSize = 20;

    useEffect(() => {
        setLocalUsers(initialUsers);
    }, [initialUsers]);

    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/user-service/users/${username}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(data => {
                setIsAdmin(data.role === 'TYPE2'); // 관리자만 접근 가능
            })
            .catch(error => {
                console.log(error);
            });
    }, [username, token]);

    useEffect(() => {
        if (isAdmin) {
            fetchFn("GET", `http://localhost:8000/user-service/users/serch?pageNum=${currentPage - 1}&startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`, null, {
                headers: { Authorization: `Bearer ${token}` },
            })
                .then(data => {
                    if (data) {
                        setLocalUsers(data.result.content);  // 로컬 상태를 업데이트, 가입일 내림차순 정렬됨(백엔드에서 설정해둠)
                        setTotalPages(data.result.totalPages);
                    } else {
                        setLocalUsers([]);
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }, [isAdmin, token, currentPage, pageSize, startDate, endDate]);

    const handleSelect = (eventKey) => {
        setCurrentPage(eventKey);
    };

    // 페이지 바에 페이지 한번에 10개씩 보임
    const maxPageDisplay = 10;
    // maxPageDisplay를 2로 나눈 후 소수점 이하의 값을 버리기 위해 Math.floor() 함수를 사용
    // maxPageDisplay가 홀수일 경우, currentPage를 중심으로 좌우로 동일한 개수의 페이지 번호를 표시
    const startPage = currentPage - Math.floor(maxPageDisplay / 2); // currentPage에서 중심을 기준으로 왼쪽으로 이동한 페이지 번호
    const endPage = currentPage + Math.floor(maxPageDisplay / 2); // currentPage에서 중심을 기준으로 오른쪽으로 이동한 페이지 번호

    // ex) 현재 페이지가 5이고 최대 페이지가 10이면, startPage는 5 - (10 / 2) = 5 - 5 = 0
    // ex) 현재 페이지가 5이고 최대 페이지가 10이면, endPage는 5 + (10 / 2) = 5 + 5 = 10

    let displayPages = [];

    // 총 페이지 수가 최대 표시 페이지 수 이하일 때, 1부터 totalPages까지의 숫자 배열 생성
    if (totalPages <= maxPageDisplay) {
        displayPages = [...Array(totalPages).keys()].map(i => i + 1);

        // 시작 페이지가 1보다 작거나 같을 때, 1부터 maxPageDisplay까지의 숫자 배열 생성
    } else if (startPage <= 1) {
        displayPages = [...Array(maxPageDisplay).keys()].map(i => i + 1);

        // 끝 페이지가 totalPages보다 크거나 같을 때, (totalPages - maxPageDisplay + 1)부터 totalPages까지의 숫자 배열 생성
    } else if (endPage >= totalPages) {
        displayPages = [...Array(maxPageDisplay).keys()].map(i => totalPages - maxPageDisplay + i + 1);
        // 위의 경우에 해당하지 않을 때, startPage부터 startPage + maxPageDisplay - 1까지의 숫자 배열 생성
    } else {
        displayPages = [...Array(maxPageDisplay).keys()].map(i => startPage + i);
    }

    return (
        <div>
            <h2>회원목록</h2>
            {isAdmin ? (
                <>
                    <Table striped bordered hover size="sm">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Username</th>
                                <th>Create Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {localUsers && localUsers.length > 0 ? (
                                localUsers.map(user =>
                                    <UserComp key={user.id} user={user} />
                                )
                            ) : (
                                <tr>
                                    <td colSpan="3">아직 회원이 존재하지 않습니다.</td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                    <Pagination className="justify-content-center">
                        <Pagination.First onClick={() => setCurrentPage(1)} />
                        <Pagination.Prev onClick={() => setCurrentPage(prev => prev > 1 ? prev - 1 : prev)} />
                        {displayPages.map(value =>
                            <Pagination.Item key={value} active={value === currentPage} onClick={() => handleSelect(value)}>
                                {value}
                            </Pagination.Item>
                        )}
                        <Pagination.Next onClick={() => setCurrentPage(prev => prev < totalPages ? prev + 1 : prev)} />
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

export default UserList;
