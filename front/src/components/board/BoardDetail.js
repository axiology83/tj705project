import React, { useState } from 'react'
import {Link, useParams } from 'react-router-dom';
import { fetchFn } from '../../NetworkUtils';

function BoardDetail() {
  const [board, setBoard] = useState(null);
  const id = useParams().id;
  
  useState(() => {
    fetchFn("GET", `http://localhost:8000/board-service/board/${id}`)
    .then(data => {
      console.log(data);
      setBoard(data);
      
    })
  }, [id])

  function deleteDetail() {
    let isOK = window.confirm("삭제 하실건가요?");

    if(isOK) {
      const dto={
        id
      }

      fetchFn("DELETE", "http://localhost:8000/board-service", dto)
      .then(data => {
        window.location.href = "/"
      })
    }
  }



  return (
    <div>
      { board !== null && <div>
      <p>글번호 : {id}</p>
      <p>작성자 : <Link to={`/chatwith/${board.username}`}><input value={board.username} readOnly/></Link> </p>
      <p>제목 : <input value={board.title} readOnly/></p>
      내용 : <p dangerouslySetInnerHTML={{__html: board.content }}></p>
      <p>작성일 : {board.createDate}</p>
      <p>수정일 : {board.updateDate}</p>
      
      
      <button onClick={deleteDetail}>삭제</button>
      
      
      </div>

        
      }
    </div>
  )
}

export default BoardDetail