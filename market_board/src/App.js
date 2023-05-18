import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import BoardDetail from './components/board/BoardDetail';
import BoardInsert from './components/board/BoardInsert';
import BoardUpdate from './components/board/BoardUpdate';
import BoardList from './components/board/BoardList';





function App() {

  return (
    <div>
      <BrowserRouter>
      <div>
    <Routes>
    
      <Route path='/board/insert' Component={BoardInsert} />
      <Route path='/board/detail/:id' Component={BoardDetail}/>
      <Route path='/board/update/:id' Component={BoardUpdate}/>
      <Route path='/board/list/:pageNum' Component={BoardList}/>
      
      
      
      
      
      
      
      
      
      
      
      
    </Routes>
      </div>
      </BrowserRouter>
      
    </div>
  );
}


export default App;
