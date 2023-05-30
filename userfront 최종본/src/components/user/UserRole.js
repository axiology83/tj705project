import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { fetchFn } from '../NetworkUtils';

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

    return (
        <div>
            <h2>권한 설정</h2>
            {isAdmin ? (
                <div>
                    <label>권한 부여:</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value=''>--role 설정--</option>
                        <option value='TYPE1'>user</option>
                        <option value='TYPE2'>admin</option>
                        <option value='TYPE3'>blocked</option>
                    </select>
                    <br/>
                    <br/>
                    <button onClick={submitHandler}>Submit</button>
                </div>
            ) : (
                <p>관리자만 접근 가능합니다.</p>
            )}
        </div>
    )
}

export default UserRole
