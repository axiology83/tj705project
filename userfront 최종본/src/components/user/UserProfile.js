import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { fetchFn } from '../NetworkUtils';
import moment from 'moment';
import Button from 'react-bootstrap/Button';
import ShowAverageRate from './ShowAverageRate';
import Table from 'react-bootstrap/Table';
import ShowRate from './ShowRate';

// 등록된 별점이 없을 경우 콘솔창에 에러메세지가 뜸.
// 별점을 등록하면 에러메세지가 사라지니 무시해도 됨.
function UserProfile() {
    const username = useParams().username;
    const sellerId = useParams().username;
    const [user, setUser] = useState(null);
    const [averageRate, setAverageRate] = useState(null);
    const LOGINER = localStorage.getItem("LOGINER");
    const token = localStorage.getItem("TOKEN");
    const role = localStorage.getItem("ROLE");
    const [reviews, setReviews] = useState([]);
    const [boards, setBoards] = useState([]);
    const [showAllReviews, setShowAllReviews] = useState(false); // 리뷰 펼치기 상태
    const [showAllBoards, setShowAllBoards] = useState(false); // 보드 펼치기 상태

    // 회원의 프로필을 불러옴
    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/user-service/profile/${username}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            if (data) {
                setUser(data);
            } else {
                console.error("실패1", data);
            }
        })
            .catch((error) => {
                console.error("실패2", error);
            });

        // 별점 불러오는 메서드  
        fetchFn("GET", `http://localhost:8000/updatereviewrate-service/rate/${sellerId}/stats`, null, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            if (data) {
                setAverageRate(data);
            } else {
                console.error("에러1", data);
            }
        })
            .catch((error) => {
                console.error("에러2", error);
            });

        // Review 불러오기
        fetchFn("GET", `http://localhost:8000/review-service/reviews/user/${sellerId}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            if (data) {
                setReviews(data);
            } else {
                console.error("리뷰 불러오기 실패", data);
            }
        })
            .catch((error) => {
                console.error("리뷰 불러오기 에러", error);
            });

        // Board 불러오기
        fetchFn("GET", `http://localhost:8000/board-service/boards/user/${username}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        }).then((data) => {
            if (data) {
                setBoards(data);
            } else {
                console.error("보드 불러오기 실패", data);
            }
        })
            .catch((error) => {
                console.error("보드 불러오기 에러", error);
            });

    }, [token, username, sellerId]);

    const handleClickToggleReviews = () => {
        setShowAllReviews(!showAllReviews); // 리뷰 펼치기 상태 토글
    };

    const handleClickToggleBoards = () => {
        setShowAllBoards(!showAllBoards); // 보드 펼치기 상태 토글
    };

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
            <h2>회원 프로필</h2>
            {user !== null && (
                <>
                    <div className='user'>
                        <p>아이디 : {user.username}</p>
                        <p>이름 : {user.name}</p>
                        <p>가입일 : {moment(user.createAt).format("YYYY-MM-DD HH:mm:ss")}</p>
                        {averageRate !== null ? (
                            <div className='rate'>
                                {/* ShowAverageRate부분을 <p>로 감싸면 <div> 요소가 <p> 요소의 하위 요소로 사용될 수 없다는 에러가 뜸 */}
                                [ 평균 별점 ] <ShowAverageRate averageRate={averageRate.averageRate} />
                                <p>별점 받은 횟수 : {averageRate.count} 번</p>
                            </div>
                        ) : (
                            <div className='rate'>
                                <p>아직 받은 별점이 없습니다.</p>
                            </div>
                        )}
                        <div>
                            {role === 'TYPE2' && (
                                <Link to={"/user/listPaging"}><Button variant="primary">회원목록</Button></Link>
                            )}
                            {LOGINER === user.username && (
                                <Link to={`/user/update/${username}`}> <Button variant="warning">수정</Button></Link>
                            )}
                            {(LOGINER === user.username || role === 'TYPE2') && (
                                <Link to={`/user/delete/${username}`}> <Button variant="danger">삭제</Button></Link>
                            )}
                        </div>
                    </div>

                    {/* 전체 가운데 맞춤 */}
                    <div className='user-review' style={{ textAlign: 'center' }}>
                        <h4>상품 리뷰</h4>
                        <br />
                        {/* 표 모양 부트스트랩 코드 사용 */}
                        <Table striped bordered hover size="sm">
                            <thead>
                                <tr>
                                    {/* 비율로 칸 넓이 조정 */}
                                    <th style={{ width: '35%' }}>제목</th>
                                    <th>구매자</th>
                                    <th style={{ width: '45%' }}>별점</th>
                                </tr>
                            </thead>
                            <tbody>
                                {/* 처음 보일때 한번에 5개의 데이터만 보임. 토글을 열면 전체 데이터 보임. */}
                                {reviews.slice(0, showAllReviews ? reviews.length : 5).map((review) => (
                                    <tr key={review.id}>
                                        {/* 추후 review-front와 합칠때 조정. 양옆위아래 가운데 맞춤 */}
                                        <td style={{ verticalAlign: 'middle' }}><Link to={`/review/detail/${review.id}`}>{review.title}</Link></td>
                                        <td style={{ verticalAlign: 'middle' }}><Link to={`/user/profile/${review.buyerName}`}>{review.buyerName}</Link></td>
                                        <td style={{ verticalAlign: 'middle' }}><ShowRate rate={review.rate} /></td>
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                        {/* 버튼 부트스트랩 코드 사용. 토글 열고 닫기 가능 */}
                        <Button onClick={handleClickToggleReviews} variant="outline-dark">{showAllReviews ? '닫기' : '전체보기'}</Button>
                    </div>

                    <div className='user-board' style={{ textAlign: 'center' }}>
                        <h4>게시글</h4>
                        <br />
                        <Table striped bordered hover size="sm">
                            <thead>
                                <tr>
                                    <th>제목</th>
                                    <th>카테고리</th>
                                    <th>조회수</th>
                                </tr>
                            </thead>
                            <tbody>
                                {boards.slice(0, showAllBoards ? boards.length : 5).map((board) => (
                                    <tr key={board.id}>
                                        {/* 추후 board-front와 합칠때 조정  */}
                                        <td style={{ verticalAlign: 'middle' }}><Link to={`/board/detail/${board.id}`}>{board.title}</Link></td>
                                        <td style={{ verticalAlign: 'middle' }}>{board.cateName}</td>
                                        <td style={{ verticalAlign: 'middle' }}>{board.readCnt}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </Table>
                        <Button onClick={handleClickToggleBoards} variant="outline-dark">{showAllBoards ? '닫기' : '전체보기'}</Button>
                    </div>

                </>
            )}
        </div>
    )
}

export default UserProfile