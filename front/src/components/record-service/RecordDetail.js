import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { fetchFn } from '../../NetworkUtils';

function RecordDetail() {
  const [records, setRecords]=useState(null);
  const id=useParams().username;

  useEffect(()=>{
    fetchFn("GET", `Http://localhost:8000/record-service/records/${id}`,null)
    .then(data=>{
        setRecords(data.result);
    })
  },[id]);

  return (
    <div>RecordDetail</div>
  )
}

export default RecordDetail