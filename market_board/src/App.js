import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import BoardInsert from './components/board/BoardInsert';





function App() {

  return (
    <div>
      <BrowserRouter>
      <div>
    <Routes>
    
      <Route path='/' Component={BoardInsert} />
      
      
      
      
      
      
      
      
      
      
      
      
      
    </Routes>
      </div>
      </BrowserRouter>
      
    </div>
  );
}


export default App;
