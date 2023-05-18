import React, { useEffect, useState } from 'react'
import { fetchFn } from '../../NetworkUtils';

function BoardSearchingPaging(props) {
    const keyword = props.keyword 
    const [pageStart, setPageStart] = useState(1);
    const [totalPages, setTotalPages] = useState(null);
    const [currentPage, setCurrentPage] = useState(0);

    function onClickHandler(pageNum, keyword) {
        fetchFn("GET", `http://localhost:8000/board-service/search?pageNum=${pageNum-1}&keyword=${keyword}`)
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
    fetchFn("GET", `http://localhost:8000/board-service/search?pageNum=0&keyword=${keyword}`)
    .then(data => {
      setTotalPages(data.result.totalPages)
      setCurrentPage(data.result.number)
        
    })
    
  }

  useEffect(getPageNumInfo, [keyword])



  
  


  return (
    <div>
        {totalPages !== undefined && (<>
          <button onClick={minusPaging} >
            {`<<`}
          </button>

          {pagingArr.map((page) => (
            <button key={page} onClick={() => { onClickHandler(page, keyword); }}
              disabled={currentPage + 1 === page}>{page}</button>
          ))}

          <button onClick={plusPaging}>

          {`>>`}
          </button>
        </>)}
      </div>
    
   
  )
}

export default BoardSearchingPaging