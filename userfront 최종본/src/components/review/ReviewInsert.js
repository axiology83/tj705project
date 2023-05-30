import React, { useEffect, useState } from 'react'
import { fetchFn } from '../NetworkUtils'

// 테스트를 위해 임의로 만들어 둠. 추후 삭제 예정
function ReviewInsert() {
    const [reviews, setReviews] = useState([])

    function onSubmitHandler(e) {
        e.preventDefault()

        const formData = new FormData(e.target)
        const sellerId = formData.get('sellerId')
        const buyerName = formData.get('buyerName')
        const title = formData.get('title')
        const content = formData.get('content')
        const rate = formData.get('rate')
        const count = formData.get('count')

        const dto = {
            sellerId,
            buyerName,
            title,
            content,
            rate
        }
        fetchFn("POST", "http://localhost:8000/review-service/create", dto)
        .then(data => {
            console.log(data)
        })
    }

  return (
    <div>
        <h2>리뷰 작성</h2>
        
        <form action='#' onSubmit={onSubmitHandler}>
            판매자 : <input name='sellerId'/><br/>
            구매자 : <input name='buyerName'/><br/>
            제목 : <input name='title'/><br/>
            내용 : <input name='content'/><br/>
            별점 : <input name='rate'/><br/>
            <button>작성완료</button>
        </form>
        
    </div>
  )
}

export default ReviewInsert