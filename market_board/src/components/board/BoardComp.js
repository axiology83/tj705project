import React, { useEffect, useState } from 'react'
import { fetchFn } from '../../NetworkUtils';
import { Link } from 'react-router-dom';

function BoardComp(props) {
    const board = props.board;
    const [names, setname] = useState(null);

    useEffect(() => {
        fetchFn(
          "GET", `http://localhost:8000/category-service/category/${board.cid}`, null)
          .then((data) => {
          setname(data.name);
        });
      });

  return (
    <div>
        <div>
      {names !== null &&
          <span>[{names}]</span>     } 
      <span>{board.id}</span> |
      <span><Link to={`/board/detail/${board.id}`}> {board.title}</Link></span> |
      <span> {board.username}</span> |
      <span> {board.readCnt}</span> |
      
    </div>
    </div>
  )
}

export default BoardComp