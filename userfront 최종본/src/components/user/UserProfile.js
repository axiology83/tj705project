import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { fetchFn } from '../NetworkUtils';
import moment from 'moment';

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

    // 회원의 프로필을 불러옴(추후 게시판과 연동해 회원 이름 클릭시 프로필로 이동하게 하는 코드 추가)
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
                                <p>평균 별점 : {averageRate.averageRate} </p>
                                <p>받은 별점 개수 : {averageRate.count} </p>
                            </div>
                        ) : (
                            <div className='rate'>
                                <p>아직 받은 별점이 없습니다.</p>
                            </div>
                        )}
                    </div>

                    <div>
                        {role === 'TYPE2' && (
                        <Link to={"/user/listPaging"}>[ 회원목록 ]</Link>
                        )}
                        {LOGINER === user.username && (
                            <Link to={`/user/update/${username}`}>[ 수정 ]</Link>
                        )}
                        {(LOGINER === user.username || role === 'TYPE2') && (
                            <Link to={`/user/delete/${username}`}>[ 삭제 ]</Link>
                        )}
                    </div>

                    <div className='review'>
                        <h4>상품 리뷰</h4><br />
                        {reviews.map((review) => (
                            <div key={review.id}>
                                {/* 추후 review-service 프론트와 연동 */}
                                <p>제목 : <Link to={`/review/detail/${review.id}`}>{review.title}</Link></p>
                                <p>내용 : {review.content}</p>
                                <p>구매자 : {review.buyerName}</p><br />
                            </div>
                        ))}
                    </div>

                    <div className='board'>
                        <h4>판매&구매글</h4><br />
                        {boards.map((board) => (
                            <div key={board.id}>
                                {/* 추후 board-service 프론트와 연동 */}
                                <p>제목 : <Link to={`/board/detail/${board.id}`}>{board.title}</Link></p>
                                <p>내용 : {board.content}</p>
                                <p>조회수 : {board.readCnt}</p><br />
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>
    )
}

export default UserProfile