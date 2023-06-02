import React, { useEffect, useMemo, useState } from 'react';
import { useTable } from 'react-table';

import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { fetchFn } from '../../NetworkUtils';

const StyledTableContainer = styled.div`
  display: flex;
  justify-content: center; 
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
  background-color: lightgrey;
`;

const StyledTd = styled.td`
  width: 350px;
  padding: 10px;
  border-bottom: 1px solid #ccc;
`;

const StyledTr = styled.tr`
  
`;

function RecordTable() {
  const [records, setRecords] = useState(null);

  useEffect(() => {
    fetchFn("GET", `http://localhost:8000/record-service/records`, null)
      .then(data => {
        setRecords(data.result);
      });
  }, []);



  const columns = useMemo(() =>{
    const columnData = [
      {
          accessor: 'id',
          Header: '레코드 번호',
          Cell: ({ value ,row }) => (
              <Link to={`/record/detail/${row.original.id}`}>{value}</Link>
          ),
      },
      {
        accessor: 'boardid',
        Header: '글 번호',
      },
      {
          accessor: 'reviewId',
          Header: '리뷰 번호',
      },
      {
        accessor: 'boardTitle',
        Header: '글 제목',
      },
      {
        accessor: 'boardContent',
        Header: '글 내용',
      },
      {
          accessor: 'seller',
          Header: '글 작성자',
      },
      {
        accessor: 'reviewTitle',
        Header: '리뷰 제목',
      },
      {
        accessor: 'reviewContent',
        Header: '리뷰 내용',
      },
      {
          accessor: 'buyer',
          Header: '리뷰 작성자',
      },
    ];
    return columnData;
  }, []);
  const data = useMemo(() => records, [records]);

  
  const Table = ({ columns, data }) => {
    const {
      getTableProps,
      getTableBodyProps,
      headerGroups,
      rows,
      prepareRow,
    } = useTable({ columns, data });

    return (
        <StyledTableContainer>
            <StyledTable {...getTableProps()}>
                <thead>
                {headerGroups.map(header => (
                    <StyledTr {...header.getHeaderGroupProps()}>
                    {header.headers.map(col => (
                        <StyledTh {...col.getHeaderProps()}>{col.render('Header')}</StyledTh>
                    ))}
                    </StyledTr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                    prepareRow(row);
                    return (
                    <StyledTr {...row.getRowProps()}>
                        {row.cells.map(cell => (
                        <StyledTd {...cell.getCellProps()}>{cell.render('Cell')}</StyledTd>
                        ))}
                    </StyledTr>
                    );
                })}
                </tbody>
            </StyledTable>
      </StyledTableContainer>
    );
  };

  return (
    <div>
      <br/>
      {records ? (
        <Table columns={columns} data={data} />
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}

export default RecordTable;
