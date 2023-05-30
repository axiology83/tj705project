import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { fetchFn } from '../NetworkUtils';

function UserDelete() {
    const username = useParams().username;
    const [isAdmin, setIsAdmin] = useState(false);
    const LOGINER = localStorage.getItem("LOGINER");
    const token = localStorage.getItem("TOKEN");
    const role = localStorage.getItem("ROLE");

    // LOGINER의 권한 확인(관리자면 isAdmin = true로 설정)
    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/user-service/users/${LOGINER}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((data) => {
                setIsAdmin(data.role === 'TYPE2');
            })
            .catch((error) => {
                console.log(error.message);
            });
    }, [LOGINER, token]);

    // 사용자가 폼을 제출하면 함수 호출
    function onSubmitHandler(event) {
        event.preventDefault();

        // 로그인한 사용자가 관리자 or 삭제하려는 사용자가 본인 이 아닐 경우 에러메세지 & 함수 종료
        if (!isAdmin && LOGINER !== username) {
            alert("관리자 또는 해당 회원 본인만 접근 가능합니다.");
            return;
        }

        // 폼에서 입력된 데이터 가져옴
        const formData = new FormData(event.target);
        const password = formData.get("password");

        // 삭제요청을 보낼 때 사용할 데이터 생성
        const dto = {
            username,
            password,
        };

        // 빈 값이 있는지 확인
        if (!password) {
            alert("입력하지 않은 정보가 있습니다.");
            return;
        }

        // 사용자를 삭제하는 DELETE요청 보냄
        fetchFn("DELETE", `http://localhost:8000/user-service/users/${username}`, dto, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((data) => {
                // 요청 성공시 로그아웃(토큰과 사용자 정보 제거) & 메인페이지로 이동
                localStorage.removeItem("TOKEN");
                localStorage.removeItem("LOGINER");
                localStorage.removeItem("jwt");
                window.location.href = `/`;
            });
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
            {isAdmin || LOGINER === username ? (
                <>
                    <h2>회원 탈퇴</h2>
                    <form action="#" onSubmit={onSubmitHandler}>
                        아이디 : <input value={username} readOnly />
                        <br />
                        비밀번호 : <input name="password" />
                        <br />
                        <button>삭제</button>
                    </form>
                </>
            ) : (
                <>
                    <p>관리자 또는 해당 회원 본인만 접근 가능합니다.</p>
                </>
            )}
        </div>
    );
}

export default UserDelete