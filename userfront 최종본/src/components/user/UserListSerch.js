import React, { useEffect, useState } from 'react'
import { fetchFn } from '../NetworkUtils';
import DateRangePicker from '../DateRangePicker';
import UserList from './UserList';
import Button from 'react-bootstrap/Button';

// 가입일로 회원 검색하기(검색 전엔 목록 안보임)
function UserListSerch() {
    const [tempStartDate, setTempStartDate] = useState(new Date()); // 검색버튼 누르기 전에 결과 반영 안되도록 추가한 변수
    const [tempEndDate, setTempEndDate] = useState(new Date());
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [isAdmin, setIsAdmin] = useState(false);
    const [users, setUsers] = useState([]);
    const username = localStorage.getItem("LOGINER");
    const token = localStorage.getItem("TOKEN");

    useEffect(() => {
        fetchFn("GET", `http://localhost:8000/user-service/users/${username}`, null, {
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(data => {
                setIsAdmin(data.role === 'TYPE2'); // 관리자만 접근 가능
            })
            .catch(error => {
                console.log(error)
            });
    }, [username, token]);

    // startDate와 endDate 입력하기(daterangepicker 이용해서 달력으로 날짜 찍기)
    const handleDateRangeChange = ({ startDate, endDate }) => {
        if (startDate && endDate) {
            setTempStartDate(startDate);
            setTempEndDate(endDate);
        } else {
            console.error("에러");
        }
    }

    // 입력받은 시작일과 마지막일을 기준으로 그 사이에 가입한 회원 데이터 불러오기
    // toISOString() : 날짜 객체를 ISO 8601 형식의 문자열로 변환 ("YYYY-MM-DD T HH:mm:ss.sss Z")
    function handleSearchButtonClick() {
        if (isAdmin) {
            setStartDate(tempStartDate);
            setEndDate(tempEndDate);
            fetchFn("GET", `http://localhost:8000/user-service/users/serch?pageNum=0&startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`, null, {
                headers: { Authorization: `Bearer ${token}` },
            })
                .then(data => {
                    console.log(data.result)
                    setUsers(data.result.content);
                })
                .catch(error => {
                    alert(error.message);
                });
        }
    }

    return (
        <>
            <div>
                {/* DateRangePicker = 달력 띄워주는 것 */}
                <DateRangePicker
                    ranges={[{ startDate: tempStartDate, endDate: tempEndDate, key: 'selection' }]}
                    onChange={handleDateRangeChange}
                />
                <br />
                <Button onClick={handleSearchButtonClick} variant="primary">검색</Button>
            </div>
            <br />
            <div>
                <UserList startDate={startDate} endDate={endDate} users={users} setUsers={setUsers} />
            </div>
        </>
    )
}

export default UserListSerch
