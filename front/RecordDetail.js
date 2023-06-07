import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom';
import { fetchFn } from '../../NetworkUtils';
import styled from 'styled-components';
const StyledTableContainer = styled.div`
  display: flex;
  justify-content: center; 
  padding: 70px;
`;

const StyledTable = styled.table`
  text-align: center;
  margin : 20px 10px;
  font-size:small;
`;

const StyledTh = styled.th`
  width: 150px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border-bottom: 1px solid #ccc;
`;

const StyledTd = styled.td`
  width: 350px;
  padding: 10px;
  border-bottom: 1px solid #ccc;
`;

const StyledTr = styled.tr`
  
`;

const StyledButton = styled.button`
  border: none;
  padding: 15px 30px;
  border-radius: 15px;
  font-weight: 600;
  background-color: lightgrey;
  margin:auto;
  display:block;
  text-decoration: none;
`;

function RecordDetail() {
  const [records, setRecords]=useState(null);
  const id=useParams().id;

  useEffect(()=>{
    fetchFn("GET", `Http://localhost:8000/record-service/records/${id}`,null)
    .then(data=>{
        setRecords(data.result);
    })
  },[id]);
  
  return (
    <div>

        { records !== null && 
        <div>
          <StyledTableContainer>
            <StyledTable>
              <thead/>
              <tbody>
                <StyledTr>
                  <StyledTh>레코드 번호</StyledTh>
                  <StyledTd>{records.id}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>글 번호</StyledTh>
                  <StyledTd>{records.boardid}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>글 작성자</StyledTh>
                    <StyledTd>{records.seller}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>글 제목</StyledTh>
                  <StyledTd>{records.boardTitle}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>글 내용</StyledTh>
                  <StyledTd>{records.boardContent}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>글 작성일</StyledTh>
                  <StyledTd>{records.boardCreateDate}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>리뷰 번호</StyledTh>
                  <StyledTd>{records.reviewId}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>리뷰 작성자</StyledTh>
                  <StyledTd>{records.buyer}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>리뷰 제목</StyledTh>
                  <StyledTd>{records.reviewTitle}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>리뷰 내용</StyledTh>
                  <StyledTd>{records.reviewContent}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>리뷰 작성일</StyledTh>
                  <StyledTd>{records.reviewCreateDate}</StyledTd>
                </StyledTr>
                <StyledTr>
                  <StyledTh>별점</StyledTh>
                  <StyledTd>{records.rate}</StyledTd>
                </StyledTr>
              </tbody>
            </StyledTable>
          </StyledTableContainer>
          <Link to={`/record`}><StyledButton>목록으로</StyledButton></Link>
        </div>
      }
      
    </div>
  )
}

export default RecordDetail