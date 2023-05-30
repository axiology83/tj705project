import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import MainEx from './components/MainEx';
import MyHeader from './components/MyHeader';
import UserInsert from './components/user/UserInsert';
import UserLogin from './components/user/UserLogin';
import UserDetail from './components/user/UserDetail';
import UserUpdate from './components/user/UserUpdate';
import UserDelete from './components/user/UserDelete';
import UserProfile from './components/user/UserProfile';
import UserListPaging from './components/user/UserListPaging';
import ReviewInsert from './components/review/ReviewInsert';
import UserRole from './components/user/UserRole';
import UserListSerch from './components/user/UserListSerch';

function App() {

  return (

    <div className="App">

      <header>
        <MyHeader />
      </header>


      <BrowserRouter>
        <div>

          <Routes>

            <Route path="/" element={<MainEx />} />

            <Route path="/user/login" element={<UserLogin />} />
            <Route path="/user/insert" element={<UserInsert />} />
            <Route path="/user/listPaging" element={<UserListPaging />} />
            <Route path="/user/listSerch" element={<UserListSerch />} />
            <Route path="/user/detail/:username" element={<UserDetail />} />
            <Route path="/user/update/:username" element={<UserUpdate />} />
            <Route path="/user/role/:username" element={<UserRole />} />
            <Route path="/user/delete/:username" element={<UserDelete />} />
            <Route path="/user/profile/:username" element={<UserProfile />} />

            <Route path="/review/insert" element={<ReviewInsert />} />

          </Routes>

        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
