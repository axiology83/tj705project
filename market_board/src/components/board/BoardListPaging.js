import React, { useEffect, useState } from 'react'
import { fetchFn } from '../../NetworkUtils';

function BoardListPaging(props) {
    const [pageStart, setPageStart] = useState(1);
    const [totalPages, setTotalPages] = useState(null);
    const [currentPage, setCurrentPage] = useState(0);

    function onClickHandler(pageNum) {
    fetchFn("GET", `http://localhost:8000/board-service/list?pageNum=${pageNum-1}`)
    .then(data => {
            
        props.setFn(data.result.content)
        setCurrentPage(data.result.number)
        setTotalPages(data.result.totalPages)
            
          })
      }
    
      let pagingArr = new Array();
      if (totalPages !== undefined) {
        for (let i = pageStart; i < pageStart + 10 && i <= totalPages; i++) {
          pagingArr.push(i);
        }
      }
    
      function plusPaging() {
        if (pageStart + 10 < totalPages) {
          setPageStart(pageStart + 10)
        }
      }
      
      function minusPaging() {
        if (pageStart !== 1) {
          setPageStart(pageStart -10);
        }
      }
      
      function getPageNumInfo() {
        fetchFn("GET", `http://localhost:8000/board-service/list?pageNum=0`)
        .then(data => {
          setTotalPages(data.result.totalPages)
        })
        
      }
    
      useEffect(getPageNumInfo, [])
    
    
    
      
      
    

  return (
    <div>
         {totalPages !== undefined && (<>
          <button onClick={minusPaging} >
            {`<<`}
          </button>

          {pagingArr.map((pageNum) => (
            <button key={pageNum} onClick={() => { onClickHandler(pageNum); }}
              disabled={currentPage + 1 === pageNum}>{pageNum}</button>
          ))}

          <button onClick={plusPaging}>

          {`>>`}
          </button>
        </>)}
    </div>
  )
}

export default BoardListPaging