import React, { useEffect, useState } from 'react'
import { fetchFn } from '../../NetworkUtils';
import RecordComp from './RecordComp';

function RecordList() {
  const[records, setRecords]=useState([]);

  useEffect(()=>{
    fetchFn("GET","http://localhost:8000/record-service/records",null)
    .then(data=>{
        setRecords(data.result);
    })
  },[]);

  return (
    <div>
        <h2>레코드 목록</h2>

        {
            records.length>0 && records.map(record=>(
                <RecordComp key={record.id} record={record}/>
            ))
        }
    </div>
  )
}

export default RecordList