import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { fetchFn } from '../NetworkUtils';
import Button from 'react-bootstrap/Button';
import Dropdown from 'react-bootstrap/Dropdown';

function UserRole() {
    const { username } = useParams();
    const [role, setRole] = useState(''); // 유저 권한 초기화
    const [isAdmin, setIsAdmin] = useState(false);
    const token = localStorage.getItem("TOKEN");
    const loginer = localStorage.getItem("LOGINER");

    useEffect(() => {
        fetchFn('GET', `http://localhost:8000/user-service/users/${loginer}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            setIsAdmin(data.role === 'TYPE2'); // 관리자만 접근 가능
        });
    }, [loginer, token]);

    const submitHandler = () => {
        if (!isAdmin) {
            alert('관리자 권한이 필요합니다.'); // 관리자가 아닐 경우 에러 메세지 띄움
            return;
        }

        // 토큰에서 role값 받아오기
        fetchFn('PUT', `http://localhost:8000/user-service/users/${username}/role`, { role }, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            if (data) {
                try {
                    window.location.href = `/user/detail/${username}`; // 권한 수정 완료시 상세보기란으로 돌아감
                } catch (error) {
                    console.error('에러1', error);
                }
            } else {
                console.error('에러2', data);
            }
        })
    }

    const handleSelect = (e) => {
        setRole(e);
    }

    return (
        <div>
            <h2>권한 설정</h2>
            {isAdmin ? (
                <div className='user'>
                    {/* 드롭다운 부트스트랩 코드 사용. variant = 버튼 색상 */}
                    <Dropdown onSelect={handleSelect}>
                        <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                            {role || "-role 설정-"}
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item eventKey="TYPE1">user</Dropdown.Item>
                            <Dropdown.Item eventKey="TYPE2">admin</Dropdown.Item>
                            <Dropdown.Item eventKey="TYPE3">blocked</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <br />
                    <Button onClick={submitHandler} variant="success" type="submit">설정완료</Button>
                    <Link to={`/user/detail/${username}`}> <Button variant="primary">취소</Button></Link>
                </div>
            ) : (
                <p>관리자만 접근 가능합니다.</p>
            )}
        </div>
    )
}

export default UserRole
