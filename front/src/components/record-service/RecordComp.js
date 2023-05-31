import React, { useMemo } from 'react'
import { useTable } from 'react-table';

function RecordComp(props) {
  const record=props.record;

  const columnData = [
    {
      accessor: 'record.id',
      Header: 'Email',
    },
    {
      accessor: 'record.cateId',
      Header: 'Wallet ID',
    },
    {
      accessor: 'coin_list',
      Header: 'Wallet Balance',
    },
    {
      accessor: 'created_at',
      Header: 'Created At',
    },
    {
      accessor: 'edited_at',
      Header: 'Edited At',
    },
  ]

  const columns = useMemo(() => columnData, []);

  const Table = ({ columns, data }) => {
    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
      useTable({ columns, data });

  return (
    <TableSheet {...getTableProps()}>
      <TableHead>
        {headerGroups.map(header => (
          // getHeaderGroupProps를 통해 header 배열을 호출한다
          <header {...header.getHeaderGroupProps()}>
            {header.headers.map(col => (
              // getHeaderProps는 각 셀 순서에 맞게 header를 호출한다
              <th {...col.getHeaderProps()}>{col.render('Header')}</th>
            ))}
          </header>
        ))}
      </TableHead>
      <tbody {...getTableBodyProps()}>
        {rows.map(row => {
          prepareRow(row);
          return (
            // getRowProps는 각 row data를 호출해낸다
            <tr {...row.getRowProps()}>
              {row.cells.map(cell => (
                // getCellProps는 각 cell data를 호출해낸다
                <td {...cell.getCellProps()}>{cell.render('Cell')}</td>
              ))}
            </tr>
          );
        })}
      </tbody>
    </TableSheet>
  );
};
}
export default RecordComp