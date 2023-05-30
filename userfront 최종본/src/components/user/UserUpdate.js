import React, { useEffect, useState } from 'react'
import { fetchFn } from '../NetworkUtils';
import { useParams } from 'react-router-dom';

function UserUpdate() {
    const { username } = useParams();
    const [user, setUser] = useState(null);
    const [loginer, setLoginer] = useState(false);
    const token = localStorage.getItem("TOKEN");
    const role = localStorage.getItem("ROLE");

    // 로그인한 본인의 프로필만 수정 가능
    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/user-service/users/${username}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((data) => {
                setUser(data);
                if (localStorage.getItem("LOGINER") === username) {
                    setLoginer(true);
                }
            })
            .catch((error) => {
                console.log("업데이트 에러");
            });
    }, [token, username]);

    function onSubmitHandler(e) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);

        const username = user.username;
        const name = formData.get("name");
        const orgPassword = formData.get("orgPassword"); //기존비번
        const password = formData.get("password"); //바꾼비번
        const password2 = formData.get("password2"); //바꾼비번 확인

        // 빈 값이 있는지 확인
        if (!name || !orgPassword) {
            alert("이름과 비밀번호는 필수사항 입니다.");
            return;
        }

        if (password !== password2) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        const dto = {
            username,
            name,
            orgPassword,
            password,
            password2,
        };

        fetchFn("PUT", `http://localhost:8000/user-service/users/${username}`, dto, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then((data) => {
                window.location.href = `/user/profile/${username}`; // 수정 완료시 프로필로 돌아감
            });
    }

    function onInputHandler(e) {
        const val = e.target.value;

        const newUser = { ...user, [e.target.name]: val };
        setUser(newUser);
    }

    // 블락당한 user는 상세보기 기능 이용 불가.
    if (role === 'TYPE3') {
        return (
            <div>
                <p>사이트 이용이 정지되었습니다. 자세한 사항은 관리자에게 문의하세요.</p>
            </div>
        )
    }

    return (
        <div>
            {loginer ? (
                <>
                    <h2>프로필 수정</h2>
                    {user !== null && (
                        <>
                            <form action="#" onSubmit={onSubmitHandler}>
                                아이디 : <input value={user.username} disabled />
                                <br />
                                닉네임 :{" "}
                                <input
                                    name="name"
                                    value={user.name}
                                    onInput={onInputHandler}
                                />
                                <br />
                                기존 비밀번호 : <input name="orgPassword" />
                                <br />
                                새 비밀번호 : <input name="password" />
                                <br />
                                비밀번호 확인 : <input name="password2" />
                                <br />
                                <button>수정 완료</button>
                            </form>
                        </>
                    )}
                </>
            ) : (
                <p>본인 프로필만 수정이 가능합니다.</p>
            )}
        </div>
    );
}

export default UserUpdate