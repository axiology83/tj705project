import React from 'react'
import test1 from '../images/test1.png';
import test2 from '../images/test2.png';
import test3 from '../images/test3.jpg';
import test4 from '../images/test4.jpg';
import test5 from '../images/test5.jpg';
import Card from 'react-bootstrap/Card';
import { Col, Row } from 'react-bootstrap';
import { useEffect } from 'react';
import { useState } from 'react';

function MainEx() {

  const [loginer, setLoginer] = useState(null);

  // LOGINER 세팅
  useEffect(() => {
    const loggedInUser = localStorage.getItem('LOGINER');
    if (loggedInUser) {
      setLoginer(loggedInUser);
    }
  }, []);

  return (
    <div>
      <h2>main화면(임시)</h2>

      <br />
      <br />

      {/* 전체 가운데 맞춤. 가운데 정렬. Row 안에 들어간 Col은 가로 정렬 */}
      <Row className="justify-content-center">

        {/* xs={12} sm={6} md={3} 화면 비율에 따라 카드 정렬이 달라지게 함. */}
        <Col xs={12} sm={6} md={3} className="p-3" style={{ marginRight: '-10rem' }}>
          <Card style={{ width: '12rem' }}>
            <Card.Img variant="top" src={test3} style={{ height: '14rem' }} />
            <Card.Body>
              <Card.Title>User-Insert</Card.Title>
              <Card.Text>회원가입</Card.Text>
            </Card.Body>
            <Card.Link href="/user/insert">바로가기</Card.Link>
          </Card>
        </Col>

        <Col xs={12} sm={6} md={3} className="p-3" style={{ marginRight: '-10rem' }}>
          <Card style={{ width: '12rem' }}>
            <Card.Img variant="top" src={test1} style={{ height: '14rem' }} />
            <Card.Body>
              <Card.Title>User-Login</Card.Title>
              <Card.Text>로그인</Card.Text>
            </Card.Body>
            <Card.Link href="/user/login">바로가기</Card.Link>
          </Card>
        </Col>

        <Col xs={12} sm={6} md={3} className="p-3" style={{ marginRight: '-10rem' }}>
          <Card style={{ width: '12rem' }}>
            <Card.Img variant="top" src={test5} style={{ height: '14rem' }} />
            <Card.Body>
              <Card.Title>User-Profile</Card.Title>
              <Card.Text>프로필</Card.Text>
            </Card.Body>
            <Card.Link href={`/user/profile/${loginer}`}>바로가기</Card.Link>
          </Card>
        </Col>

        <Col xs={12} sm={6} md={3} className="p-3" style={{ marginRight: '-10rem' }}>
          <Card style={{ width: '12rem' }}>
            <Card.Img variant="top" src={test2} style={{ height: '14rem' }} />
            <Card.Body>
              <Card.Title>User-Detail</Card.Title>
              <Card.Text>상세정보</Card.Text>
            </Card.Body>
            <Card.Link href={`/user/detail/${loginer}`}>바로가기</Card.Link>
          </Card>
        </Col>

        <Col xs={12} sm={6} md={3} className="p-3" style={{ marginRight: '-10rem' }}>
          <Card style={{ width: '12rem' }}>
            <Card.Img variant="top" src={test4} style={{ height: '14rem' }} />
            <Card.Body>
              <Card.Title>User-List</Card.Title>
              <Card.Text>회원목록</Card.Text>
            </Card.Body>
            <Card.Link href="/user/listPaging">바로가기</Card.Link>
          </Card>
        </Col>

      </Row>


    </div>
  )
}

export default MainEx