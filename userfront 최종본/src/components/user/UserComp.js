import moment from 'moment/moment'; // 날짜 예쁘게 보이게 해줌. ("연도-월-일 시간:분:초" 로 설정함)
import React from 'react'
import { Link } from 'react-router-dom';

function UserComp(props) {

  const user = props.user;

  return (
    <>
      <td>{user.name}</td>
      <td><Link to={`/user/detail/${user.username}`}> {user.username}</Link></td>
      <td> {moment(user.createAt).format("YYYY-MM-DD HH:mm:ss")}</td>
    </>
  )
}

export default UserComp