import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom';
import { fetchFn } from '../NetworkUtils';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

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
                    <form onSubmit={onSubmitHandler}>
                        <Container>
                            {/* 폼 부트스트랩 코드 사용. 가운데 정렬. xs={12} md={6} 로 크기 조절. mb = 아래 간격 조절 */}
                            <Row className="justify-content-md-center">
                                <Col xs={12} md={6}>
                                    <Form.Group className="mb-3" controlId="formUsername">
                                        <Form.Control name='username' type="text" placeholder="아이디" value={username} disabled />
                                    </Form.Group>
                                </Col>
                            </Row>
                        </Container>

                        <Container>
                            <Row className="justify-content-md-center">
                                <Col xs={12} md={6}>
                                    <Form.Group className="mb-3" controlId="formPassword">
                                        <Form.Control name='password' type="password" placeholder="새 비밀번호" />
                                    </Form.Group>
                                </Col>
                            </Row>
                        </Container>
                        
                        <Button variant="danger" type="submit">삭제</Button>
                        {/* 링크를 버튼 모양으로 보이게 하는 법 : 버튼을 링크로 감싸기*/}
                        <Link to={`/user/profile/${username}`}> <Button variant="primary">취소</Button></Link>
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