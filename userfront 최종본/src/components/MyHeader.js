import { useState, useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

// dropdown없는 간단한 형태의 navbar
function ColorSchemesExample() {
    const [loginer, setLoginer] = useState(null);
    
    // LOGINER 세팅
    useEffect(() => {
        const loggedInUser = localStorage.getItem('LOGINER');
        if (loggedInUser) {
            setLoginer(loggedInUser);
        }
    }, []);

    // 로그아웃 함수
    function logout() {
        localStorage.removeItem("TOKEN");
        localStorage.removeItem("LOGINER");
        localStorage.removeItem("jwt");
        window.location.href = `/`;
      }

    return (
        <>
            <Navbar fixed="top" bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/">HOME</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="/user/listPaging">회원목록</Nav.Link>
                        {loginer ? (
                            <>
                                <Nav.Link href={`/user/profile/${loginer}`}>마이페이지</Nav.Link>
                                <Nav.Link onClick={logout}>로그아웃</Nav.Link>
                            </>
                        ) : (
                            <>
                                <Nav.Link href="/user/insert">회원가입</Nav.Link>
                                <Nav.Link href="/user/login">로그인</Nav.Link>
                            </>
                        )}
                    </Nav>
                </Container>
            </Navbar>
            <div style={{ paddingTop: '64px' }}></div>
        </>
    );
}

export default ColorSchemesExample;
