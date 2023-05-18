import React, { useEffect, useState } from 'react'
import { fetchFn, fetchFn2 } from '../../NetworkUtils';
import { Link, useParams } from 'react-router-dom';

function BoardDetail() {
    const id = useParams().id;
    const [board, setBoard] = useState(null);
    const token = localStorage.getItem("TOKEN");

    // 자세히보기
    useEffect(() => {
        fetchFn2("GET", `http://localhost:9007/api/board/id/${id}`, null, {
            headers: { Authorization: `Bearer ${token}` }})
        .then(data => {
            console.log(data);
            setBoard(data.result);
        })
    }, [id, token])
    // 글 삭제
    function deleteDetail() {
        let isOK = window.confirm("삭제하실래요?");

        if(isOK) {
            const dto={
                id
            }
            fetchFn("DELETE", "http://localhost:9007/api/board", dto)
            .then(data => {
                alert("삭제를 완료했습니다.")
                window.location.href = "/board/list/1"
            })
        }
    }

  return (
    <div>
        <div>
            {board !== null && <>
        <p>글번호 : {id} </p>
        <p>작성자 : {board.username}</p>
        <p>제목 : {board.title}</p> 
        <p dangerouslySetInnerHTML={{__html: board.content }}></p>
        <p>조회수 : {board.readCnt}</p>
        <p>업로드 날짜 : {board.createDate}</p>
        <p>수정된 날짜 : {board.updateDate}</p>

         <Link to={`/board/list/1`}><button>목록</button></Link> | 
        <Link to={`/board/update/${id}`}><button>수정</button></Link> |
        <button onClick={deleteDetail}>삭제</button>
        </> }
        </div>
       

    </div>
  )
}

export default BoardDetail