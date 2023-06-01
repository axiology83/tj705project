import React from 'react'
import { fetchFn } from '../NetworkUtils';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function UserLogin() {

  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);

    const username = formData.get("username");
    const password = formData.get("password");

    // 빈 값 입력 방지
    if (!username || !password) {
      alert("아이디 또는 비밀번호가 입력되지 않았습니다.");
      return;
    }

    const dto = {
      username,
      password
    }

    // 로그인 후 내 프로필 화면으로 넘어감
    fetchFn("POST", "http://localhost:8000/user-service/login", dto)
      .then(data => {
        if (data.result === "오류발생. 다시 시도해주세요.") {
          alert("아이디 또는 비밀번호가 잘못되었습니다. 다시 시도해주세요.");
        } else {
          localStorage.setItem("LOGINER", data.result.username);
          localStorage.setItem("TOKEN", data.result.token);
          localStorage.setItem("ROLE", data.result.role);
          window.location.href = `/user/profile/${username}`;
          console.log("콘솔로그", data);
        }
      }
      )
  }

  return (
    <div>
      <h2>로그인 화면</h2>

      <Form onSubmit={onSubmitHandler}>
        <br />
        <Container>
          <Row className="justify-content-md-center">
            <Col xs={12} md={6}>
              <Form.Group className="mb-3" controlId="formUsername">
                <Form.Control name='username' type="text" placeholder="아이디" />
              </Form.Group>
            </Col>
          </Row>
        </Container>

        <Container>
          <Row className="justify-content-md-center">
            <Col xs={12} md={6}>
              <Form.Group className="mb-3" controlId="formPassword">
                <Form.Control name='password' type="password" placeholder="비밀번호" />
              </Form.Group>
            </Col>
          </Row>
        </Container>

        <Button variant="primary" type="submit">로그인</Button>
      </Form>

    </div>
  )

}

export default UserLogin