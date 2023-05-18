import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import BoardDetail from './components/board/BoardDetail';
import BoardInsert from './components/board/BoardInsert';





function App() {

  return (
    <div>
      <BrowserRouter>
      <div>
    <Routes>
    
      <Route path='/' Component={BoardInsert} />
      <Route path='/detail/:id' Component={BoardDetail}/>
      
      
      
      
      
      
      
      
      
      
      
      
    </Routes>
      </div>
      </BrowserRouter>
      
    </div>
  );
}


export default App;
