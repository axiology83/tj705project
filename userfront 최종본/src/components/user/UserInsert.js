import React from 'react'
import { fetchFn } from '../NetworkUtils';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function UserInsert() {

  // 사용자가 폼을 제출하면 함수 호출
  function onSubmitHandler(e) {
    e.preventDefault();
    const formData = new FormData(e.target);

    const dto = {
      username: formData.get("username"),
      name: formData.get("name"),
      password: formData.get("password"),
      password2: formData.get("password2")
    };

    const username = formData.get("username");
    const name = formData.get("name");
    const password = formData.get("password");
    const password2 = formData.get("password2");

    // 빈 값 입력 방지
    if (!username || !name || !password || !password2) {
      alert("입력되지 않은 란이 있습니다.");
      return;
    }

    // 회원가입 완료 후 로그인 화면으로 넘어감
    fetchFn("POST", "http://localhost:8000/user-service/users", dto)
      .then(data => {
        console.log(data.result);
        window.location.href = `/user/login`;
      });
  }

  return (
    <div>
      <h2>회원가입</h2>

      <Form onSubmit={onSubmitHandler}>
        <br />
        <Container>
          <Row className="justify-content-md-center">
            <Col xs={12} md={6}>
              <Form.Group className="mb-3" controlId="formUsername">
                <Form.Control name='username' type="text" placeholder="닉네임" />
              </Form.Group>
            </Col>
          </Row>
        </Container>

        <Container>
          <Row className="justify-content-md-center">
            <Col xs={12} md={6}>
              <Form.Group className="mb-3" controlId="formName">
                <Form.Control name='name' type="text" placeholder="이름" />
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

        <Container>
          <Row className="justify-content-md-center">
            <Col xs={12} md={6}>
              <Form.Group className="mb-3" controlId="formPassword2">
                <Form.Control name='password2' type="password" placeholder="비밀번호 확인" />
              </Form.Group>
            </Col>
          </Row>
        </Container>

        <Button variant="primary" type="submit">가입완료</Button>
      </Form>
      
    </div>
  )
}

export default UserInsert