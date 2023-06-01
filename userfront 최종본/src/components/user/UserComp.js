import moment from 'moment/moment'; // 날짜 예쁘게 보이게 해줌. ("연도-월-일 시간:분:초" 로 설정함)
import React from 'react'
import { Link } from 'react-router-dom';

function UserComp(props) {

  const user = props.user;

  return (
    <tr key={user.id}>
      <td><span>{user.name}</span></td>
      <td><span><Link to={`/user/detail/${user.username}`}> {user.username}</Link></span></td>
      <td><span>{moment(user.createAt).format("YYYY-MM-DD HH:mm:ss")}</span></td>
    </tr>
  )
}

export default UserComp