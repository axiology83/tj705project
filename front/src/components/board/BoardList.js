import React, { useEffect, useState } from 'react'
import { fetchFn } from '../../NetworkUtils';
import { Link } from 'react-router-dom';
import BoardComp from './BoardComp';
import BoardSearchingPaging from './BoardSearchingPaging';
import BoardListPaging from './BoardListPaging';

function BoardList() {
    const [keyword, setKeyword] = useState(null);
    const [isSearching, setIsSearching] = useState(false)
    const [pageList, setPageList] = useState([]);
    const [selectAll, setSelectAll] = useState(false);
    const [selectedIds, setSelectedIds] = useState([])

    useEffect(() => {
        fetchFn("GET", "http://localhost:8000/board-service/list?pageNum=0", null)
        .then(data => {
        setPageList(data.result.content)
         })
      }, []); 

      function handleCheckBoxClick(e) {
        const { checked , value} = e.target;
    
        if(checked) {
          setSelectedIds([...selectedIds, Number(value)]);
        }else {
          setSelectedIds(selectedIds.filter(id => id !== Number(value)));
        }
      }
    
      function handleDeleteButtonClick() {
    
        if(window.confirm("선택한 게시글을 삭제하시겠습니까??")) {
          const deleteIds = selectedIds.join(',');
         
          fetchFn("DELETE", `http://localhost:8000/board-service/delete?id=${deleteIds}`, null)
          .then(data => {
            if(data.result === '삭제 성공') {
              setPageList(pageList.filter(board => !selectedIds.includes(board.id)));
              setSelectedIds([])
            } else {
              console.error('데이터 삭제 실패');
              alert('삭제 실패');
            }
          })
          .catch(error => {
            console.error('서버 요청 실패', error);
            alert('글 삭제 중 오류 발생. 다시 시도 요망')
          })
        }else {
    
        }
      }
    
      function handleSelectAllClick() {
        if(selectAll) {
          setSelectedIds([]);
          setSelectAll(false);
        }else {
          const allIds = pageList.map(board => board.id);
          setSelectedIds(allIds);
          setSelectAll(true);
        }
      }

      function onSubmitHandler(e){
        e.preventDefault() 
        // 검색 클릭하면 항상 true로 변경하도록함.
        setIsSearching(true)
        
        const formData = new FormData(e.target)
        setKeyword (formData.get('keyword')) 
        const pageNum = formData.get('pageNum')
        console.log(keyword)
       
        fetchFn("GET", `http://localhost:8000/board-service/search?keyword=${keyword}&pageNum=${pageNum}`, null)
        .then(data => {
          console.log(data)
          setPageList(data.result.content)
          
        
        })
      
      }


  return (
    <>
    
    <div style={{ display : 'flex', flexDirection: 'column', alignItems: 'center'}}>
      
      <div>
        <button onClick={handleSelectAllClick}>
          {selectAll ? '전체 선택 취소' : '전체 선택'}
        </button>
        <button onClick={handleDeleteButtonClick}>선택 삭제</button><br/>
      </div>
      {
        pageList.length > 0 && pageList.map(board => 
          <div key={board.id} style={{display: 'flex', alignItems:'center', marginBottom :'10px'}}>
              <input type='checkbox' value={board.id} checked={selectedIds.includes(board.id)} onChange={handleCheckBoxClick}/>
             <BoardComp board={board}/>
          </div>
          )
      }


    </div>
    
    <form onSubmit={onSubmitHandler}>
      <input name='keyword' />
      <input name="pageNum" type="hidden" value={0} /><button>검색</button>

      </form>
      <Link to={`/board/insert`}><button>등록</button></Link>


       
     <div>
      { isSearching ? <BoardSearchingPaging keyword={keyword} setFn={setPageList}/> : <BoardListPaging setFn={setPageList}/>}
        
        

    </div> 
    </>
  )
}

export default BoardList