import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import ChatRoom from './components/chat-service/ChatRoom';
import ChatTest from './components/chat-service/ChatTest';
import Login from './components/auth/Login';
import MemberInsert from './components/user-service/MemberInsert';
import BoardDetail from './components/board/BoardDetail';
import BoardInsert from './components/board/BoardInsert';
import BoardUpdate from './components/board/BoardUpdate';
import BoardList from './components/board/BoardList';
import KakaoMapTest from './KakaoMapTest';
import RecordList from './components/record-service/RecordList';
import RecordDetail from './components/record-service/RecordDetail';


function App() {
  return (
    <div>
      <header>헤더 자리</header>
      <BrowserRouter>
      <div>
      <Routes>
        <Route path="/" Component={KakaoMapTest}/>
        <Route path="/member/signup" Component={MemberInsert}/>
        <Route path="/login" Component={Login}/>
        <Route path="/chatwith/:username" Component={ChatRoom}/>
        <Route path="/chattest" Component={ChatTest}/>
        <Route path='/board/insert' Component={BoardInsert} />
        <Route path='/board/detail/:id' Component={BoardDetail}/>
        <Route path='/board/update/:id' Component={BoardUpdate}/>
        <Route path='/board/list/:pageNum' Component={BoardList}/>
        <Route path='/record' Component={RecordList}/>
        <Route path='/record/detail/:id' Component={RecordDetail}/>
      </Routes>
      </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
