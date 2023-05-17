import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ReviewInsert from "./component/review/ReviewInsert";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Routes>
          <Route path="/" Component={ReviewInsert} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
